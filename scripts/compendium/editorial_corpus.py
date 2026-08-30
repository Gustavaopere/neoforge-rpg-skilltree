#!/usr/bin/env python3
from __future__ import annotations

from dataclasses import dataclass
import json
from pathlib import Path
import re
from typing import Any

SCHEMA = 1
LANGUAGE = "pt_br"
KINDS = {"ENTITY", "FLORA", "TREE", "CROP", "BIOME", "STRUCTURE", "DIMENSION"}
REVIEW_STATUSES = {"DRAFT", "REVIEWED"}
AVAILABILITY = {"RUNTIME", "OPTIONAL", "LEGACY"}
SOURCE_TYPES = {
    "RUNTIME",
    "DATAPACK",
    "OFFICIAL_DOCS",
    "OFFICIAL_CODE",
    "OFFICIAL_CHANGELOG",
    "VERIFIED_COMMUNITY",
}
PLACEHOLDER_RE = re.compile(r"\b(?:TODO|TBD|FIXME|PLACEHOLDER)\b", re.IGNORECASE)
ENTRY_ID_RE = re.compile(
    r"^(ENTITY|FLORA|TREE|CROP|BIOME|STRUCTURE|DIMENSION):"
    r"([a-z0-9_.-]+):([a-z0-9_./-]+)$"
)
SECTION_KEY_RE = re.compile(r"^[a-z0-9_][a-z0-9_.-]*$")


class EditorialCorpusError(ValueError):
    pass


@dataclass(frozen=True)
class EditorialEntry:
    entry_id: str
    namespace: str
    kind: str
    title: str
    summary: dict[str, Any]
    sections: dict[str, dict[str, Any]]
    references: tuple[str, ...]
    review_status: str
    availability: str
    availability_reason: str | None
    source_file: str


@dataclass(frozen=True)
class EditorialCorpus:
    entries: tuple[EditorialEntry, ...]
    files: tuple[str, ...]

    def by_id(self) -> dict[str, EditorialEntry]:
        return {entry.entry_id: entry for entry in self.entries}


def read_json(path: Path) -> Any:
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except OSError as exc:
        raise EditorialCorpusError(f"cannot read {path}: {exc}") from exc
    except json.JSONDecodeError as exc:
        raise EditorialCorpusError(
            f"invalid JSON in {path}: line {exc.lineno}, column {exc.colno}: {exc.msg}"
        ) from exc


def require_non_blank(value: Any, label: str) -> str:
    if not isinstance(value, str) or not value.strip():
        raise EditorialCorpusError(f"{label} must be a non-blank string")
    return value.strip()


def reject_placeholder(text: str, label: str) -> None:
    if PLACEHOLDER_RE.search(text):
        raise EditorialCorpusError(f"{label} contains a forbidden placeholder")


def reject_source_placeholder(text: str, label: str) -> None:
    if text == "..." or PLACEHOLDER_RE.search(text):
        raise EditorialCorpusError(f"{label} contains a forbidden placeholder")


def parse_entry_id(value: Any, label: str = "entry_id") -> tuple[str, str, str]:
    text = require_non_blank(value, label)
    match = ENTRY_ID_RE.fullmatch(text)
    if match is None:
        raise EditorialCorpusError(
            f"{label} must be KIND:namespace:path using a supported Compendium kind: {text!r}"
        )
    kind, namespace, path = match.groups()
    return kind, namespace, f"{namespace}:{path}"


def coverage_runtime_ids(payload: Any, source: Path) -> set[str]:
    if not isinstance(payload, dict) or payload.get("schema") != SCHEMA:
        raise EditorialCorpusError(f"coverage report schema must be {SCHEMA}: {source}")
    entries = payload.get("entries")
    if not isinstance(entries, list):
        raise EditorialCorpusError(f"coverage report entries must be an array: {source}")
    result: set[str] = set()
    for raw in entries:
        if not isinstance(raw, dict):
            continue
        kind = raw.get("kind")
        resource_location = raw.get("resource_location")
        if kind not in KINDS or not isinstance(resource_location, str):
            continue
        resource_location = resource_location.strip()
        if resource_location.count(":") != 1:
            continue
        namespace, path = resource_location.split(":", 1)
        candidate = f"{kind}:{namespace}:{path}"
        try:
            parse_entry_id(candidate, "coverage entry id")
        except EditorialCorpusError:
            continue
        result.add(candidate)
    return result


def validate_source(raw: Any, label: str) -> dict[str, str]:
    if not isinstance(raw, dict):
        raise EditorialCorpusError(f"{label} source must be an object")
    source_type = require_non_blank(raw.get("type"), f"{label}.type")
    if source_type not in SOURCE_TYPES:
        raise EditorialCorpusError(
            f"{label}.type has unsupported source type {source_type!r}; expected one of {sorted(SOURCE_TYPES)}"
        )
    ref = require_non_blank(raw.get("ref"), f"{label}.ref")
    reject_source_placeholder(ref, f"{label}.ref")
    result = {"type": source_type, "ref": ref}
    note = raw.get("note")
    if note is not None:
        note_text = require_non_blank(note, f"{label}.note")
        reject_placeholder(note_text, f"{label}.note")
        result["note"] = note_text
    return result


def validate_prose_block(raw: Any, label: str) -> dict[str, Any]:
    if not isinstance(raw, dict):
        raise EditorialCorpusError(f"{label} must be an object containing text and sources")
    text = require_non_blank(raw.get("text"), f"{label}.text")
    reject_placeholder(text, f"{label}.text")
    raw_sources = raw.get("sources")
    if not isinstance(raw_sources, list) or not raw_sources:
        raise EditorialCorpusError(f"{label}.sources must contain at least one explicit source")
    sources = [validate_source(item, f"{label}.sources[{index}]") for index, item in enumerate(raw_sources)]
    return {"text": text, "sources": sources}


def validate_entry(
    raw: Any,
    *,
    package_namespace: str,
    package_kind: str,
    runtime_ids: set[str],
    source_file: str,
    index: int,
) -> EditorialEntry:
    label = f"{source_file} entries[{index}]"
    if not isinstance(raw, dict):
        raise EditorialCorpusError(f"{label} must be an object")
    kind, namespace, _ = parse_entry_id(raw.get("entry_id"), f"{label}.entry_id")
    entry_id = require_non_blank(raw.get("entry_id"), f"{label}.entry_id")
    if namespace != package_namespace:
        raise EditorialCorpusError(
            f"{label} namespace mismatch: package={package_namespace!r}, entry={namespace!r}"
        )
    if kind != package_kind:
        raise EditorialCorpusError(f"{label} kind mismatch: package={package_kind!r}, entry={kind!r}")

    title = require_non_blank(raw.get("title"), f"{label}.title")
    reject_placeholder(title, f"{label}.title")
    summary = validate_prose_block(raw.get("summary"), f"{label}.summary")

    raw_sections = raw.get("sections", {})
    if not isinstance(raw_sections, dict):
        raise EditorialCorpusError(f"{label}.sections must be an object")
    sections: dict[str, dict[str, Any]] = {}
    for section_name, section in raw_sections.items():
        name = require_non_blank(section_name, f"{label}.section name")
        if SECTION_KEY_RE.fullmatch(name) is None:
            raise EditorialCorpusError(f"{label}.sections has invalid section key {name!r}")
        sections[name] = validate_prose_block(section, f"{label}.sections.{name}")

    raw_references = raw.get("references", [])
    if not isinstance(raw_references, list):
        raise EditorialCorpusError(f"{label}.references must be an array")
    references: list[str] = []
    for ref_index, reference in enumerate(raw_references):
        parse_entry_id(reference, f"{label}.references[{ref_index}]")
        references.append(require_non_blank(reference, f"{label}.references[{ref_index}]"))

    review_status = require_non_blank(raw.get("review_status"), f"{label}.review_status")
    if review_status not in REVIEW_STATUSES:
        raise EditorialCorpusError(
            f"{label}.review_status must be one of {sorted(REVIEW_STATUSES)}, got {review_status!r}"
        )
    availability = require_non_blank(raw.get("availability"), f"{label}.availability")
    if availability not in AVAILABILITY:
        raise EditorialCorpusError(
            f"{label}.availability must be one of {sorted(AVAILABILITY)}, got {availability!r}"
        )
    availability_reason: str | None = None
    if availability in {"OPTIONAL", "LEGACY"}:
        if entry_id in runtime_ids:
            raise EditorialCorpusError(
                f"{label} is present in the current runtime coverage and must use availability=RUNTIME"
            )
        availability_reason = require_non_blank(raw.get("availability_reason"), f"{label}.availability_reason")
        reject_placeholder(availability_reason, f"{label}.availability_reason")
    elif entry_id not in runtime_ids:
        raise EditorialCorpusError(
            f"{label} is absent from the current runtime coverage; mark it OPTIONAL/LEGACY with a reason or remove it"
        )

    return EditorialEntry(
        entry_id=entry_id,
        namespace=namespace,
        kind=kind,
        title=title,
        summary=summary,
        sections=sections,
        references=tuple(references),
        review_status=review_status,
        availability=availability,
        availability_reason=availability_reason,
        source_file=source_file,
    )


def load_corpus(
    corpus_root: Path,
    coverage_payload: Any,
    *,
    release: bool = False,
    allow_empty: bool = False,
) -> EditorialCorpus:
    if not corpus_root.is_dir():
        raise EditorialCorpusError(f"editorial corpus directory does not exist: {corpus_root}")
    runtime_ids = coverage_runtime_ids(coverage_payload, Path("coverage-report.json"))
    files = tuple(sorted(path for path in corpus_root.rglob("*.json") if path.is_file()))
    if not files:
        if allow_empty:
            return EditorialCorpus((), ())
        raise EditorialCorpusError(f"editorial corpus contains no JSON packages: {corpus_root}")

    entries: list[EditorialEntry] = []
    seen: dict[str, str] = {}
    for path in files:
        relative_path = path.relative_to(corpus_root)
        relative = relative_path.as_posix()
        payload = read_json(path)
        if not isinstance(payload, dict):
            raise EditorialCorpusError(f"{relative} must contain a JSON object")
        if payload.get("schema") != SCHEMA:
            raise EditorialCorpusError(f"{relative} schema must be {SCHEMA}")
        if payload.get("language") != LANGUAGE:
            raise EditorialCorpusError(f"{relative} language must be {LANGUAGE}")
        namespace = require_non_blank(payload.get("namespace"), f"{relative}.namespace")
        directory_namespace = relative_path.parts[0] if len(relative_path.parts) > 1 else None
        if directory_namespace != namespace:
            raise EditorialCorpusError(
                f"{relative} directory namespace mismatch: directory={directory_namespace!r}, declared={namespace!r}"
            )
        kind = require_non_blank(payload.get("kind"), f"{relative}.kind")
        if kind not in KINDS:
            raise EditorialCorpusError(f"{relative}.kind must be one of {sorted(KINDS)}, got {kind!r}")
        raw_entries = payload.get("entries")
        if not isinstance(raw_entries, list):
            raise EditorialCorpusError(f"{relative}.entries must be an array")
        for index, raw in enumerate(raw_entries):
            item = validate_entry(
                raw,
                package_namespace=namespace,
                package_kind=kind,
                runtime_ids=runtime_ids,
                source_file=relative,
                index=index,
            )
            previous_file = seen.get(item.entry_id)
            if previous_file is not None:
                raise EditorialCorpusError(
                    f"duplicate editorial entry {item.entry_id!r}: {previous_file} and {relative}"
                )
            seen[item.entry_id] = relative
            entries.append(item)

    known_ids = runtime_ids | set(seen)
    for item in entries:
        for reference in item.references:
            if reference not in known_ids:
                raise EditorialCorpusError(
                    f"{item.source_file} entry {item.entry_id} has unresolved reference {reference!r}"
                )
        if release and item.review_status != "REVIEWED":
            raise EditorialCorpusError(
                f"release corpus requires REVIEWED entries; {item.entry_id} is {item.review_status}"
            )

    entries.sort(key=lambda item: item.entry_id)
    return EditorialCorpus(tuple(entries), tuple(path.relative_to(corpus_root).as_posix() for path in files))
