#!/usr/bin/env python3
"""Deterministic continuity auditor for the spoiler-safe narrative corpus.

The default console output deliberately exposes only aggregate counts and rule
codes. Editors can opt into detailed paths/IDs with --reveal.
"""
from __future__ import annotations

import argparse
import re
import sys
import unicodedata
from collections import Counter, defaultdict
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable, TextIO

ID_PREFIXES = (
    "HIST",
    "ARC",
    "NPC",
    "QST",
    "FAC",
    "SET",
    "LOC",
    "EVT",
    "EVD",
    "RUM",
    "DOC",
    "END",
)
ID_PATTERN = rf"(?:{'|'.join(ID_PREFIXES)})-\d{{4}}"
ID_RE = re.compile(rf"\b({ID_PATTERN})\b")
HEADING_ID_RE = re.compile(rf"^#\s+({ID_PATTERN})(?:\s+[—–-]\s+|\s+)(.+?)\s*$", re.MULTILINE)
FILENAME_ID_RE = re.compile(rf"^({ID_PATTERN})(?:[-_.]|$)")
SECTION_RE = re.compile(r"^##\s+(.+?)\s*$", re.MULTILINE)
PLACEHOLDER_RE = re.compile(
    r"\b(?:TODO|TBD)\b|ID\s+ainda\s+a\s+definir|\ba\s+definir\b|\bconsolida(?:ç|c)[aã]o\s+pendente\b",
    re.IGNORECASE,
)

RECOMMENDED_SECTIONS: dict[str, tuple[str, ...]] = {
    "HIST": ("Estado editorial",),
    "ARC": (
        "Estado editorial",
        "Premissa",
        "Participantes",
        "Conflito",
        "Possíveis entradas",
        "SIM / NÃO / ANTES / DEPOIS",
        "Consequências",
    ),
    "NPC": (
        "Estado editorial",
        "Papel",
        "Personalidade",
        "Objetivos",
        "Medos",
        "Crenças/ideologia",
        "Segredos",
        "Knowledge inicial",
        "Relações",
        "Agenda/autonomia",
        "Discovery channels",
        "Vida, morte e retorno",
        "Invariantes",
    ),
    "QST": (
        "Estado editorial",
        "Premissa player-safe",
        "Participantes",
        "Availability",
        "Discovery",
        "Engagement",
        "Resolution",
        "Entry points alternativos",
        "SIM / NÃO / ANTES / DEPOIS",
        "Progressão autônoma",
        "Invalidation/expiry",
        "Descoberta retrospectiva",
        "Journal visibility",
        "Evidências/knowledge",
        "Recompensas/consequências",
        "Providers",
        "Idempotência/deduplicação",
        "Anti-soft-lock",
    ),
    "FAC": ("Estado editorial", "Objetivos", "Ideologia", "Relações"),
    "SET": ("Estado editorial", "Governo", "Leis", "Instituições"),
    "LOC": ("Estado editorial", "Função narrativa", "Descoberta"),
    "EVT": ("Estado editorial", "Causas", "Consequências", "Testemunhas"),
    "EVD": ("Estado editorial", "Origem", "Quem pode conhecer"),
    "RUM": ("Estado editorial", "Origem", "Confiabilidade"),
    "DOC": ("Estado editorial", "Origem", "Knowledge"),
    "END": ("Estado editorial", "Pré-condições", "Consequências"),
}

ORPHAN_CHECK_PREFIXES = {"ARC", "NPC", "QST", "FAC", "SET", "LOC", "EVT", "EVD", "RUM", "DOC"}


@dataclass(frozen=True)
class Finding:
    severity: str
    code: str
    message: str
    path: Path | None = None
    entry_id: str | None = None
    related_id: str | None = None
    line: int | None = None


@dataclass(frozen=True)
class Entry:
    entry_id: str
    prefix: str
    title: str
    path: Path
    text: str
    sections: frozenset[str]
    references: tuple[tuple[str, int], ...]


@dataclass
class AuditReport:
    entries: list[Entry]
    findings: list[Finding]

    @property
    def errors(self) -> list[Finding]:
        return [finding for finding in self.findings if finding.severity == "error"]

    @property
    def warnings(self) -> list[Finding]:
        return [finding for finding in self.findings if finding.severity == "warning"]


def normalize_heading(value: str) -> str:
    value = unicodedata.normalize("NFKD", value)
    value = "".join(char for char in value if not unicodedata.combining(char))
    value = value.casefold().strip()
    value = re.sub(r"\s+", " ", value)
    return value


def line_number(text: str, offset: int) -> int:
    return text.count("\n", 0, offset) + 1


def _iter_markdown_files(root: Path) -> Iterable[Path]:
    if not root.exists():
        return ()
    return (
        path
        for path in sorted(root.rglob("*.md"))
        if "templates" not in path.relative_to(root).parts
    )


def _parse_entry(path: Path, root: Path) -> tuple[Entry | None, list[Finding]]:
    findings: list[Finding] = []
    text = path.read_text(encoding="utf-8")
    heading = HEADING_ID_RE.search(text)
    filename_match = FILENAME_ID_RE.match(path.name)

    if heading is None:
        if filename_match:
            findings.append(
                Finding(
                    "error",
                    "MISSING_HEADING_ID",
                    "File name declares a narrative ID but the first-level heading does not.",
                    path.relative_to(root),
                    filename_match.group(1),
                )
            )
        return None, findings

    entry_id = heading.group(1)
    prefix = entry_id.split("-", 1)[0]
    title = heading.group(2).strip()

    if filename_match and filename_match.group(1) != entry_id:
        findings.append(
            Finding(
                "error",
                "FILENAME_ID_MISMATCH",
                "The stable ID in the file name differs from the H1 stable ID.",
                path.relative_to(root),
                entry_id,
                filename_match.group(1),
                line_number(text, heading.start()),
            )
        )

    sections = frozenset(normalize_heading(match.group(1)) for match in SECTION_RE.finditer(text))
    references: list[tuple[str, int]] = []
    for match in ID_RE.finditer(text):
        ref_id = match.group(1)
        if ref_id == entry_id:
            continue
        references.append((ref_id, line_number(text, match.start())))

    entry = Entry(
        entry_id=entry_id,
        prefix=prefix,
        title=title,
        path=path.relative_to(root),
        text=text,
        sections=sections,
        references=tuple(references),
    )
    return entry, findings


def audit_tree(root: Path | str) -> AuditReport:
    root = Path(root)
    entries: list[Entry] = []
    findings: list[Finding] = []

    for path in _iter_markdown_files(root):
        entry, parse_findings = _parse_entry(path, root)
        findings.extend(parse_findings)
        if entry is not None:
            entries.append(entry)

    by_id: dict[str, list[Entry]] = defaultdict(list)
    for entry in entries:
        by_id[entry.entry_id].append(entry)

    for entry_id, duplicates in sorted(by_id.items()):
        if len(duplicates) <= 1:
            continue
        for duplicate in duplicates:
            findings.append(
                Finding(
                    "error",
                    "DUPLICATE_ID",
                    "The same stable narrative ID is declared by more than one entry.",
                    duplicate.path,
                    entry_id,
                )
            )

    known_ids = set(by_id)
    inbound: Counter[str] = Counter()
    unresolved_seen: set[tuple[Path, str, int]] = set()
    for entry in entries:
        for ref_id, line in entry.references:
            if ref_id in known_ids:
                inbound[ref_id] += 1
                continue
            key = (entry.path, ref_id, line)
            if key in unresolved_seen:
                continue
            unresolved_seen.add(key)
            findings.append(
                Finding(
                    "error",
                    "UNRESOLVED_REFERENCE",
                    "An explicit narrative ID reference has no matching canonical entry.",
                    entry.path,
                    entry.entry_id,
                    ref_id,
                    line,
                )
            )

    for entry in entries:
        for recommended in RECOMMENDED_SECTIONS.get(entry.prefix, ("Estado editorial",)):
            if normalize_heading(recommended) in entry.sections:
                continue
            findings.append(
                Finding(
                    "warning",
                    "MISSING_RECOMMENDED_SECTION",
                    f"Recommended section is absent: {recommended}.",
                    entry.path,
                    entry.entry_id,
                )
            )

        placeholder = PLACEHOLDER_RE.search(entry.text)
        if placeholder:
            findings.append(
                Finding(
                    "warning",
                    "PLACEHOLDER_MARKER",
                    "Editorial placeholder or unresolved consolidation marker remains in the entry.",
                    entry.path,
                    entry.entry_id,
                    line=line_number(entry.text, placeholder.start()),
                )
            )

        if entry.prefix in ORPHAN_CHECK_PREFIXES and inbound[entry.entry_id] == 0:
            findings.append(
                Finding(
                    "warning",
                    "ORPHAN_ENTRY",
                    "This entry has no inbound reference from another stable narrative entry.",
                    entry.path,
                    entry.entry_id,
                )
            )

        if entry.prefix == "NPC" and normalize_heading("Objetivos") not in entry.sections:
            findings.append(
                Finding(
                    "warning",
                    "NPC_MOTIVATION_GAP",
                    "NPC has no explicit Objectives section; motivation should be reviewed.",
                    entry.path,
                    entry.entry_id,
                )
            )

        if entry.prefix == "QST":
            if normalize_heading("Anti-soft-lock") not in entry.sections:
                findings.append(
                    Finding(
                        "warning",
                        "QUEST_SOFT_LOCK_REVIEW",
                        "Quest has no explicit anti-soft-lock section.",
                        entry.path,
                        entry.entry_id,
                    )
                )
            if normalize_heading("Idempotência/deduplicação") not in entry.sections:
                findings.append(
                    Finding(
                        "warning",
                        "QUEST_IDEMPOTENCY_REVIEW",
                        "Quest has no explicit reward/consequence deduplication section.",
                        entry.path,
                        entry.entry_id,
                    )
                )
            if normalize_heading("Entry points alternativos") not in entry.sections:
                findings.append(
                    Finding(
                        "warning",
                        "QUEST_ENTRY_POINT_REVIEW",
                        "Quest has no explicit alternate-entry section.",
                        entry.path,
                        entry.entry_id,
                    )
                )

    findings.sort(
        key=lambda finding: (
            0 if finding.severity == "error" else 1,
            finding.code,
            str(finding.path or ""),
            finding.line or 0,
        )
    )
    return AuditReport(entries=entries, findings=findings)


def render_report(report: AuditReport, *, stream: TextIO = sys.stdout, reveal: bool = False) -> None:
    print(
        f"Narrative audit: {len(report.entries)} entries, "
        f"{len(report.errors)} errors, {len(report.warnings)} warnings.",
        file=stream,
    )

    if not report.findings:
        print("Narrative continuity checks passed.", file=stream)
        return

    if not reveal:
        for severity, findings in (("ERROR", report.errors), ("WARNING", report.warnings)):
            counts = Counter(finding.code for finding in findings)
            for code, count in sorted(counts.items()):
                print(f"{severity} {code}: {count}", file=stream)
        print("Details hidden by spoiler-safe mode. Use --reveal for editorial debugging.", file=stream)
        return

    for finding in report.findings:
        location = str(finding.path) if finding.path else "<corpus>"
        if finding.line is not None:
            location = f"{location}:{finding.line}"
        context = []
        if finding.entry_id:
            context.append(f"entry={finding.entry_id}")
        if finding.related_id:
            context.append(f"related={finding.related_id}")
        suffix = f" ({', '.join(context)})" if context else ""
        print(
            f"{finding.severity.upper()} {finding.code} {location}{suffix} - {finding.message}",
            file=stream,
        )


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Audit the canonical narrative corpus.")
    parser.add_argument("--root", default="historia", help="Narrative root directory (default: historia).")
    parser.add_argument(
        "--reveal",
        action="store_true",
        help="Reveal entry IDs, file paths and detailed findings for editorial debugging.",
    )
    parser.add_argument(
        "--strict-warnings",
        action="store_true",
        help="Return non-zero when warnings exist; CI intentionally does not use this by default.",
    )
    args = parser.parse_args(argv)

    report = audit_tree(Path(args.root))
    render_report(report, reveal=args.reveal)
    if report.errors:
        return 1
    if args.strict_warnings and report.warnings:
        return 2
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
