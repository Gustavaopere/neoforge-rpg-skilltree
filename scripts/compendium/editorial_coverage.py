#!/usr/bin/env python3
from __future__ import annotations

import argparse
from collections import defaultdict
import json
from pathlib import Path
import sys
from typing import Any

from editorial_backlog import BacklogError, normalize_error_coverage_entry
from editorial_corpus import EditorialCorpusError, KINDS, load_corpus, read_json

SCHEMA = 1
LANGUAGE = "pt_br"


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Report Stage 10.10 editorial coverage by namespace.")
    parser.add_argument("corpus", type=Path, help="root directory containing pt-BR editorial JSON packages")
    parser.add_argument("backlog", type=Path, help="Stage 10.10 editorial-backlog.json")
    parser.add_argument("--coverage", type=Path, required=True, help="Stage 10.02 coverage-report.json")
    parser.add_argument(
        "--json",
        type=Path,
        default=Path("generated/compendium/editorial-coverage.json"),
        help="output JSON coverage report",
    )
    parser.add_argument(
        "--markdown",
        type=Path,
        default=Path("generated/compendium/editorial-coverage.md"),
        help="output Markdown coverage report",
    )
    return parser.parse_args(argv)


def validate_backlog(payload: Any, source: Path) -> dict[str, Any]:
    if not isinstance(payload, dict):
        raise EditorialCorpusError(f"editorial backlog must be an object: {source}")
    if payload.get("schema") != SCHEMA:
        raise EditorialCorpusError(f"editorial backlog schema must be {SCHEMA}: {source}")
    if payload.get("language") != LANGUAGE:
        raise EditorialCorpusError(f"editorial backlog language must be {LANGUAGE}: {source}")
    entries = payload.get("entries")
    if not isinstance(entries, list):
        raise EditorialCorpusError("editorial backlog entries must be an array")
    return payload


def coverage_backlog_rows(payload: Any) -> dict[str, dict[str, str]]:
    if not isinstance(payload, dict) or payload.get("schema") != SCHEMA:
        raise EditorialCorpusError(f"coverage report schema must be {SCHEMA}")
    entries = payload.get("entries")
    if not isinstance(entries, list):
        raise EditorialCorpusError("coverage report entries must be an array")

    rows: dict[str, dict[str, str]] = {}
    for index, raw in enumerate(entries):
        if not isinstance(raw, dict):
            raise EditorialCorpusError(f"coverage entry {index} must be an object")
        coverage_state = raw.get("coverage_state")
        if not isinstance(coverage_state, str) or not coverage_state.strip():
            raise EditorialCorpusError(f"coverage entry {index} is missing coverage_state")
        coverage_state = coverage_state.strip()
        inventory_key = raw.get("inventory_key")
        if not isinstance(inventory_key, str) or not inventory_key.strip():
            raise EditorialCorpusError(f"coverage entry {index} is missing inventory_key")

        if coverage_state == "ERROR":
            try:
                normalized = normalize_error_coverage_entry(raw, index)
            except BacklogError as exc:
                raise EditorialCorpusError(str(exc)) from exc
            kind = normalized["kind"]
            resource_location = normalized["resource_location"]
            namespace = normalized["namespace"]
            entry_id = f"{kind}:{resource_location}"
            expected = {
                "source_mod": namespace,
                "kind": kind,
                "coverage": "ERROR",
            }
        else:
            kind = raw.get("kind")
            resource_location = raw.get("resource_location")
            namespace = raw.get("namespace")
            if kind not in KINDS:
                raise EditorialCorpusError(f"coverage entry {index} has unsupported kind: {kind!r}")
            if not isinstance(resource_location, str) or resource_location.strip().count(":") != 1:
                raise EditorialCorpusError(f"coverage entry {index} has invalid resource_location")
            resource_location = resource_location.strip()
            expected_namespace = resource_location.split(":", 1)[0]
            if namespace != expected_namespace:
                raise EditorialCorpusError(
                    f"coverage entry {index} namespace mismatch: expected {expected_namespace!r}, got {namespace!r}"
                )
            entry_id = f"{kind}:{resource_location}"
            expected = {
                "source_mod": expected_namespace,
                "kind": kind,
                "coverage": coverage_state,
            }

        if entry_id in rows:
            raise EditorialCorpusError(f"duplicate current coverage entry: {entry_id}")
        rows[entry_id] = expected
    return rows


def empty_namespace_totals() -> dict[str, int]:
    return {
        "expected": 0,
        "reviewed": 0,
        "draft": 0,
        "missing": 0,
        "blocked": 0,
        "ignored": 0,
        "optional_or_legacy": 0,
    }


def build_report(corpus, backlog: dict[str, Any], coverage_payload: dict[str, Any]) -> dict[str, Any]:
    corpus_by_id = corpus.by_id()
    expected_backlog = coverage_backlog_rows(coverage_payload)
    namespaces: defaultdict[str, dict[str, int]] = defaultdict(empty_namespace_totals)
    totals = empty_namespace_totals()
    backlog_ids: set[str] = set()

    for index, raw in enumerate(backlog["entries"]):
        if not isinstance(raw, dict):
            raise EditorialCorpusError(f"editorial backlog entry {index} must be an object")
        entry_id = raw.get("entry_id")
        source_mod = raw.get("source_mod")
        kind = raw.get("kind")
        coverage_state = raw.get("coverage")
        if not isinstance(entry_id, str) or not entry_id.strip():
            raise EditorialCorpusError(f"editorial backlog entry {index} is missing entry_id")
        if not isinstance(source_mod, str) or not source_mod.strip():
            raise EditorialCorpusError(f"editorial backlog entry {index} is missing source_mod")
        if not isinstance(kind, str) or not kind.strip():
            raise EditorialCorpusError(f"editorial backlog entry {index} is missing kind")
        if not isinstance(coverage_state, str) or not coverage_state.strip():
            raise EditorialCorpusError(f"editorial backlog entry {index} is missing coverage")
        entry_id = entry_id.strip()
        source_mod = source_mod.strip()
        kind = kind.strip()
        coverage_state = coverage_state.strip()
        if entry_id in backlog_ids:
            raise EditorialCorpusError(f"duplicate editorial backlog entry: {entry_id}")
        backlog_ids.add(entry_id)

        expected = expected_backlog.get(entry_id)
        if expected is None:
            raise EditorialCorpusError(
                f"editorial backlog entry {entry_id} is absent from the current coverage report"
            )
        actual = {"source_mod": source_mod, "kind": kind, "coverage": coverage_state}
        if actual != expected:
            raise EditorialCorpusError(
                f"editorial backlog entry {entry_id} disagrees with current coverage: expected {expected}, got {actual}"
            )

        ns = namespaces[source_mod]
        if coverage_state == "ERROR":
            ns["blocked"] += 1
            totals["blocked"] += 1
            continue
        if coverage_state == "IGNORED":
            ns["ignored"] += 1
            totals["ignored"] += 1
            continue

        ns["expected"] += 1
        totals["expected"] += 1
        editorial = corpus_by_id.get(entry_id)
        if editorial is None:
            ns["missing"] += 1
            totals["missing"] += 1
        elif editorial.review_status == "REVIEWED":
            ns["reviewed"] += 1
            totals["reviewed"] += 1
        else:
            ns["draft"] += 1
            totals["draft"] += 1

    missing_backlog = sorted(set(expected_backlog) - backlog_ids)
    if missing_backlog:
        raise EditorialCorpusError(
            "editorial backlog is missing current coverage entries: " + ", ".join(missing_backlog)
        )

    for editorial in corpus.entries:
        if editorial.entry_id in backlog_ids:
            continue
        if editorial.availability in {"OPTIONAL", "LEGACY"}:
            namespaces[editorial.namespace]["optional_or_legacy"] += 1
            totals["optional_or_legacy"] += 1
        else:
            raise EditorialCorpusError(
                f"runtime editorial entry {editorial.entry_id} is absent from the current editorial backlog"
            )

    return {
        "schema": SCHEMA,
        "language": LANGUAGE,
        "coverage_runtime_fingerprint_sha256": coverage_payload.get("runtime_fingerprint_sha256"),
        "totals": dict(totals),
        "namespaces": {namespace: namespaces[namespace] for namespace in sorted(namespaces)},
    }


def render_markdown(report: dict[str, Any]) -> str:
    totals = report["totals"]
    lines = [
        "# Compêndio Natural — Cobertura editorial pt-BR",
        "",
        f"- Esperadas: **{totals['expected']}**",
        f"- Revisadas: **{totals['reviewed']}**",
        f"- Em rascunho: **{totals['draft']}**",
        f"- Ausentes: **{totals['missing']}**",
        f"- Bloqueadas por erro técnico: **{totals['blocked']}**",
        f"- Ignoradas com política explícita: **{totals['ignored']}**",
        f"- Opcionais/legadas fora do runtime atual: **{totals['optional_or_legacy']}**",
        "",
        "| namespace | esperadas | revisadas | rascunho | ausentes | bloqueadas | ignoradas | opcionais/legadas |",
        "| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |",
    ]
    for namespace, row in report["namespaces"].items():
        lines.append(
            f"| `{namespace}` | {row['expected']} | {row['reviewed']} | {row['draft']} | "
            f"{row['missing']} | {row['blocked']} | {row['ignored']} | {row['optional_or_legacy']} |"
        )
    lines.append("")
    return "\n".join(lines)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    try:
        coverage_payload = read_json(args.coverage)
        corpus = load_corpus(args.corpus, coverage_payload, release=False, allow_empty=True)
        backlog = validate_backlog(read_json(args.backlog), args.backlog)
        report = build_report(corpus, backlog, coverage_payload)
        args.json.parent.mkdir(parents=True, exist_ok=True)
        args.markdown.parent.mkdir(parents=True, exist_ok=True)
        args.json.write_text(json.dumps(report, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
        args.markdown.write_text(render_markdown(report), encoding="utf-8")
    except EditorialCorpusError as exc:
        print(f"Editorial coverage: FAIL: {exc}", file=sys.stderr)
        return 1
    except OSError as exc:
        print(f"Editorial coverage: FAIL: {exc}", file=sys.stderr)
        return 1

    print(
        "Editorial coverage: PASS "
        f"({report['totals']['reviewed']} reviewed, {report['totals']['draft']} draft, "
        f"{report['totals']['missing']} missing, {report['totals']['blocked']} blocked)"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
