#!/usr/bin/env python3
"""Build the Stage 10.10 pt-BR editorial backlog from a Stage 10.02 coverage report.

This tool schedules editorial work only. It never generates encyclopedia prose or promotes
unverified gameplay claims. Priority overrides are explicit, reasoned operator input.
"""
from __future__ import annotations

import argparse
from collections import Counter
import json
from pathlib import Path
import sys
from typing import Any

SCHEMA = 1
LANGUAGE = "pt_br"
FORBIDDEN_NAMESPACES = {"tfc", "terrafirmacraft"}
COVERAGE_STATES = {"AUTO", "CURATED", "ADAPTER", "IGNORED", "ERROR"}
STATUS_FIELDS = (
    "ptbr_name_status",
    "summary_status",
    "full_description_status",
    "source_status",
    "review_status",
)
ALLOWED_STATUSES = {"PENDING", "IN_PROGRESS", "COMPLETE", "BLOCKED", "NOT_REQUIRED"}
REQUIRED_COVERAGE_FIELDS = (
    "kind",
    "resource_location",
    "namespace",
    "coverage_state",
    "inventory_key",
)


class BacklogError(ValueError):
    pass


def reject_forbidden_namespace(namespace: str, label: str) -> None:
    if namespace in FORBIDDEN_NAMESPACES:
        raise BacklogError(
            f"{label} uses permanently excluded provider namespace {namespace!r}; "
            "remove it from Stage 10.10 editorial scope"
        )


def read_json(path: Path) -> Any:
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except OSError as exc:
        raise BacklogError(f"cannot read {path}: {exc}") from exc
    except json.JSONDecodeError as exc:
        raise BacklogError(
            f"invalid JSON in {path}: line {exc.lineno}, column {exc.colno}: {exc.msg}"
        ) from exc


def validate_coverage(payload: Any, source: Path) -> dict[str, Any]:
    if not isinstance(payload, dict):
        raise BacklogError(f"coverage report must be an object: {source}")
    if payload.get("schema") != SCHEMA:
        raise BacklogError(f"coverage report schema must be {SCHEMA}: {source}")
    entries = payload.get("entries")
    if not isinstance(entries, list):
        raise BacklogError("coverage report entries must be an array")
    return payload


def normalize_error_coverage_entry(raw: dict[str, Any], index: int) -> dict[str, Any]:
    inventory_key = raw.get("inventory_key")
    if not isinstance(inventory_key, str) or not inventory_key.strip():
        raise BacklogError(f"coverage ERROR entry {index} is missing inventory_key")
    inventory_key = inventory_key.strip()
    if inventory_key.count("|") != 1:
        raise BacklogError(f"coverage ERROR entry {index} has invalid inventory_key: {inventory_key!r}")
    kind, target = (part.strip() for part in inventory_key.split("|", 1))
    if not kind or not target:
        raise BacklogError(f"coverage ERROR entry {index} has invalid inventory_key: {inventory_key!r}")

    reason = raw.get("coverage_reason")
    if not isinstance(reason, str) or not reason.strip():
        raise BacklogError(f"coverage ERROR entry {index} requires a non-empty coverage_reason")

    namespace = "__invalid__"
    if target.count(":") == 1 and not target.startswith(":") and not target.endswith(":"):
        namespace = target.split(":", 1)[0]
    reject_forbidden_namespace(namespace, f"coverage ERROR entry {index}")

    entry = dict(raw)
    entry.update(
        {
            "kind": kind,
            "resource_location": target,
            "namespace": namespace,
            "coverage_state": "ERROR",
            "coverage_reason": reason.strip(),
            "inventory_key": inventory_key,
            "present_at_runtime": True,
        }
    )
    return entry


def validate_coverage_entry(raw: Any, index: int) -> dict[str, Any]:
    if not isinstance(raw, dict):
        raise BacklogError(f"coverage entry {index} must be an object")

    coverage_raw = raw.get("coverage_state")
    if not isinstance(coverage_raw, str) or not coverage_raw.strip():
        raise BacklogError(f"coverage entry {index} is missing metadata: coverage_state")
    coverage = coverage_raw.strip()
    if coverage not in COVERAGE_STATES:
        raise BacklogError(f"coverage entry {index} has unknown state: {coverage!r}")
    if coverage == "ERROR":
        return normalize_error_coverage_entry(raw, index)

    missing = [
        field
        for field in REQUIRED_COVERAGE_FIELDS
        if not isinstance(raw.get(field), str) or not raw[field].strip()
    ]
    if missing:
        raise BacklogError(f"coverage entry {index} is missing metadata: {', '.join(missing)}")
    entry = dict(raw)
    for field in REQUIRED_COVERAGE_FIELDS:
        entry[field] = entry[field].strip()
    resource_location = entry["resource_location"]
    if resource_location.count(":") != 1 or resource_location.startswith(":") or resource_location.endswith(":"):
        raise BacklogError(f"coverage entry {index} has invalid resource_location: {resource_location!r}")
    namespace = resource_location.split(":", 1)[0]
    if entry["namespace"] != namespace:
        raise BacklogError(
            f"coverage entry {index} namespace mismatch: expected {namespace!r}, got {entry['namespace']!r}"
        )
    reject_forbidden_namespace(namespace, f"coverage entry {index}")
    expected_inventory_key = f"{entry['kind']}|{resource_location}"
    if entry["inventory_key"] != expected_inventory_key:
        raise BacklogError(
            f"coverage entry {index} inventory key mismatch: expected {expected_inventory_key!r}"
        )
    if entry.get("present_at_runtime") is not True:
        raise BacklogError(f"coverage entry {index} must declare present_at_runtime=true")
    return entry


def load_priority_overrides(path: Path | None) -> dict[str, dict[str, Any]]:
    if path is None:
        return {}
    payload = read_json(path)
    if not isinstance(payload, dict) or payload.get("schema") != SCHEMA:
        raise BacklogError(f"priority override schema must be {SCHEMA}")
    raw_overrides = payload.get("overrides", {})
    if not isinstance(raw_overrides, dict):
        raise BacklogError("priority overrides must be an object keyed by inventory key")

    result: dict[str, dict[str, Any]] = {}
    for key, raw in raw_overrides.items():
        if not isinstance(key, str) or not key.strip() or not isinstance(raw, dict):
            raise BacklogError("priority override entries must be named objects")
        priority = raw.get("priority")
        reason = raw.get("reason")
        if isinstance(priority, bool) or not isinstance(priority, int) or priority < 0 or priority > 999:
            raise BacklogError(f"priority override for {key} must be an integer from 0 to 999")
        if not isinstance(reason, str) or not reason.strip():
            raise BacklogError(f"priority override for {key} requires a non-empty reason")
        result[key.strip()] = {"priority": priority, "reason": reason.strip()}
    return result


def validate_previous(payload: Any, source: Path) -> dict[str, Any]:
    if not isinstance(payload, dict):
        raise BacklogError(f"previous backlog must be an object: {source}")
    if payload.get("schema") != SCHEMA:
        raise BacklogError(f"previous backlog schema must be {SCHEMA}: {source}")
    if payload.get("language") != LANGUAGE:
        raise BacklogError(f"previous backlog language must be {LANGUAGE}: {source}")
    for field in ("entries", "orphaned_entries"):
        if not isinstance(payload.get(field, []), list):
            raise BacklogError(f"previous backlog {field} must be an array")
    return payload


def previous_rows(payload: dict[str, Any] | None) -> dict[str, dict[str, Any]]:
    if payload is None:
        return {}
    rows: dict[str, dict[str, Any]] = {}
    for raw in [*payload.get("entries", []), *payload.get("orphaned_entries", [])]:
        if not isinstance(raw, dict):
            raise BacklogError("previous backlog rows must be objects")
        entry_id = raw.get("entry_id")
        if not isinstance(entry_id, str) or not entry_id.strip():
            raise BacklogError("previous backlog row is missing entry_id")
        entry_id = entry_id.strip()
        if entry_id in rows:
            raise BacklogError(f"duplicate previous backlog entry: {entry_id}")
        for field in STATUS_FIELDS:
            value = raw.get(field)
            if value not in ALLOWED_STATUSES:
                raise BacklogError(f"previous backlog {entry_id} has invalid {field}: {value!r}")
        rows[entry_id] = dict(raw)
    return rows


def default_priority(entry: dict[str, Any]) -> int:
    coverage = entry["coverage_state"]
    namespace = entry["namespace"]
    kind = entry["kind"]
    if coverage == "ERROR":
        return 0
    if coverage == "IGNORED":
        return 999
    if namespace == "minecraft":
        return 10
    if kind in {"BIOME", "DIMENSION"}:
        return 50
    if kind == "STRUCTURE":
        return 60
    return 70


def initial_statuses(coverage: str) -> dict[str, str]:
    if coverage == "ERROR":
        value = "BLOCKED"
    elif coverage == "IGNORED":
        value = "NOT_REQUIRED"
    else:
        value = "PENDING"
    return {field: value for field in STATUS_FIELDS}


def preserve_statuses(row: dict[str, Any], previous: dict[str, Any] | None) -> None:
    if previous is None or row["coverage"] in {"ERROR", "IGNORED"}:
        return
    for field in STATUS_FIELDS:
        value = previous.get(field)
        if value in ALLOWED_STATUSES and value not in {"BLOCKED", "NOT_REQUIRED"}:
            row[field] = value


def make_active_row(
    entry: dict[str, Any],
    previous: dict[str, Any] | None,
    override: dict[str, Any] | None,
) -> dict[str, Any]:
    entry_id = f"{entry['kind']}:{entry['resource_location']}"
    priority = default_priority(entry)
    priority_reason = "deterministic Stage 10.10 default"
    if override is not None and entry["coverage_state"] not in {"ERROR", "IGNORED"}:
        priority = override["priority"]
        priority_reason = override["reason"]

    row: dict[str, Any] = {
        "entry_id": entry_id,
        "source_mod": entry["namespace"],
        "kind": entry["kind"],
        "coverage": entry["coverage_state"],
        "priority": priority,
        "priority_reason": priority_reason,
        **initial_statuses(entry["coverage_state"]),
        "present_at_runtime": True,
    }
    preserve_statuses(row, previous)
    return row


def make_orphan(row: dict[str, Any]) -> dict[str, Any]:
    orphan = dict(row)
    orphan["present_at_runtime"] = False
    return orphan


def build_backlog(
    coverage: dict[str, Any],
    previous: dict[str, Any] | None = None,
    priority_overrides: dict[str, dict[str, Any]] | None = None,
) -> dict[str, Any]:
    priority_overrides = priority_overrides or {}
    previous_map = previous_rows(previous)
    active: list[dict[str, Any]] = []
    active_ids: set[str] = set()
    inventory_keys: set[str] = set()

    for index, raw in enumerate(coverage["entries"]):
        entry = validate_coverage_entry(raw, index)
        inventory_key = entry["inventory_key"]
        entry_id = f"{entry['kind']}:{entry['resource_location']}"
        if entry_id in active_ids:
            raise BacklogError(f"duplicate current backlog entry: {entry_id}")
        active_ids.add(entry_id)
        inventory_keys.add(inventory_key)
        active.append(
            make_active_row(
                entry,
                previous_map.get(entry_id),
                priority_overrides.get(inventory_key),
            )
        )

    absent_overrides = sorted(set(priority_overrides) - inventory_keys)
    if absent_overrides:
        raise BacklogError(
            "priority overrides reference entries absent from current runtime: " + ", ".join(absent_overrides)
        )

    orphaned = [
        make_orphan(row)
        for entry_id, row in previous_map.items()
        if entry_id not in active_ids
    ]
    active.sort(key=lambda row: (row["priority"], row["entry_id"]))
    orphaned.sort(key=lambda row: row["entry_id"])

    status_counts = Counter(row["review_status"] for row in active)
    return {
        "schema": SCHEMA,
        "language": LANGUAGE,
        "coverage_runtime_fingerprint_sha256": coverage.get("runtime_fingerprint_sha256"),
        "entry_count": len(active),
        "orphaned_entry_count": len(orphaned),
        "entries": active,
        "orphaned_entries": orphaned,
        "review_status_totals": {
            status: status_counts.get(status, 0)
            for status in sorted(ALLOWED_STATUSES)
        },
    }


def render_markdown(payload: dict[str, Any]) -> str:
    lines = [
        "# Compêndio Natural — Backlog editorial pt-BR",
        "",
        f"- Entradas ativas: **{payload['entry_count']}**",
        f"- Entradas órfãs preservadas: **{payload['orphaned_entry_count']}**",
        "- Este artefato agenda revisão editorial; ele não gera descrições ou fatos automaticamente.",
        "",
        "## Fila ativa",
        "",
        "| prioridade | entrada | mod | tipo | cobertura | nome pt-BR | resumo | descrição | fontes | revisão |",
        "| ---: | --- | --- | --- | --- | --- | --- | --- | --- | --- |",
    ]
    for row in payload["entries"]:
        lines.append(
            "| {priority} | `{entry_id}` | `{source_mod}` | {kind} | {coverage} | {ptbr_name_status} | {summary_status} | {full_description_status} | {source_status} | {review_status} |".format(
                **{key: str(value).replace("|", "\\|") for key, value in row.items()}
            )
        )

    if payload["orphaned_entries"]:
        lines.extend([
            "",
            "## Entradas órfãs",
            "",
            "Conteúdo ausente do runtime atual é preservado para impedir perda silenciosa de trabalho editorial.",
            "",
        ])
        for row in payload["orphaned_entries"]:
            lines.append(f"- `{row['entry_id']}` — revisão `{row['review_status']}`")
    lines.append("")
    return "\n".join(lines)


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("coverage", type=Path, help="Stage 10.02 coverage-report.json")
    parser.add_argument(
        "--json",
        type=Path,
        default=Path("generated/compendium/editorial-backlog.json"),
        help="output JSON backlog",
    )
    parser.add_argument(
        "--markdown",
        type=Path,
        default=Path("generated/compendium/editorial-backlog.md"),
        help="output Markdown backlog",
    )
    parser.add_argument("--previous", type=Path, default=None, help="previous editorial backlog")
    parser.add_argument(
        "--priority-overrides",
        type=Path,
        default=None,
        help="optional explicit priority overrides with reasons",
    )
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    try:
        coverage = validate_coverage(read_json(args.coverage), args.coverage)
        previous = validate_previous(read_json(args.previous), args.previous) if args.previous else None
        overrides = load_priority_overrides(args.priority_overrides)
        backlog = build_backlog(coverage, previous, overrides)
        args.json.parent.mkdir(parents=True, exist_ok=True)
        args.markdown.parent.mkdir(parents=True, exist_ok=True)
        args.json.write_text(json.dumps(backlog, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
        args.markdown.write_text(render_markdown(backlog), encoding="utf-8")
    except (OSError, BacklogError) as exc:
        print(f"Compendium editorial backlog: FAIL: {exc}", file=sys.stderr)
        return 1

    print(
        "Compendium editorial backlog: PASS "
        f"({backlog['entry_count']} active, {backlog['orphaned_entry_count']} orphaned)"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())