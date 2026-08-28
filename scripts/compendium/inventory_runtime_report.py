#!/usr/bin/env python3
"""Validate a runtime registry inventory and build explicit Compendium coverage.

Every runtime entry is retained and receives exactly one coverage state. Invalid
entries become ERROR instead of disappearing. Optional overrides are data-driven,
and comparison with a previous snapshot records removed IDs as orphaned legacy
entries rather than silently deleting them.
"""
from __future__ import annotations

import argparse
from collections import Counter
import json
from pathlib import Path
import sys
from typing import Any

KINDS = ("ENTITY", "FLORA", "TREE", "CROP", "BIOME", "STRUCTURE", "DIMENSION")
STATES = ("AUTO", "CURATED", "ADAPTER", "IGNORED", "ERROR")
SUMMARY_KIND_KEYS = {
    "ENTITY": "entities",
    "FLORA": "flora",
    "TREE": "trees",
    "CROP": "crops",
    "BIOME": "biomes",
    "STRUCTURE": "structures",
    "DIMENSION": "dimensions",
}
REQUIRED_ENTRY_FIELDS = (
    "kind",
    "resource_location",
    "namespace",
    "translation_key",
    "mod_display_name",
    "registry_source",
)


class ReportError(ValueError):
    pass


def read_json(path: Path) -> Any:
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except OSError as exc:
        raise ReportError(f"cannot read {path}: {exc}") from exc
    except json.JSONDecodeError as exc:
        raise ReportError(f"invalid JSON in {path}: line {exc.lineno}, column {exc.colno}: {exc.msg}") from exc


def validate_runtime(payload: Any, source: Path) -> dict[str, Any]:
    if not isinstance(payload, dict):
        raise ReportError(f"runtime inventory must be an object: {source}")
    if payload.get("schema") != 1:
        raise ReportError(f"runtime inventory schema must be 1: {source}")
    entries = payload.get("entries")
    mods = payload.get("loaded_mods")
    if not isinstance(entries, list):
        raise ReportError("runtime inventory entries must be an array")
    if not isinstance(mods, list):
        raise ReportError("runtime inventory loaded_mods must be an array")
    if payload.get("entry_count") != len(entries):
        raise ReportError(
            f"runtime entry_count {payload.get('entry_count')!r} does not match entries length {len(entries)}"
        )
    return payload


def load_overrides(path: Path | None) -> dict[str, dict[str, str]]:
    if path is None:
        return {}
    payload = read_json(path)
    if not isinstance(payload, dict) or payload.get("schema") != 1:
        raise ReportError("coverage override schema must be 1")
    raw = payload.get("overrides", {})
    if not isinstance(raw, dict):
        raise ReportError("coverage overrides must be an object keyed by KIND|resource_location")
    result: dict[str, dict[str, str]] = {}
    for key, value in raw.items():
        if not isinstance(key, str) or not key.strip() or not isinstance(value, dict):
            raise ReportError("coverage override entries must be named objects")
        state = value.get("state")
        reason = value.get("reason", "")
        if state not in STATES:
            raise ReportError(f"unknown coverage state for {key}: {state!r}")
        if not isinstance(reason, str):
            raise ReportError(f"coverage reason for {key} must be a string")
        reason = reason.strip()
        if state in {"IGNORED", "ERROR"} and not reason:
            raise ReportError(f"{state} coverage requires an explicit reason for {key}")
        result[key.strip()] = {"state": state, "reason": reason}
    return result


def resource_key(entry: dict[str, Any], index: int) -> str:
    kind = entry.get("kind")
    resource_location = entry.get("resource_location")
    if isinstance(kind, str) and isinstance(resource_location, str) and kind and resource_location:
        return f"{kind}|{resource_location}"
    return f"ERROR|__invalid_entry_{index}"


def entry_validation_error(entry: Any) -> str | None:
    if not isinstance(entry, dict):
        return "runtime entry is not an object"
    missing = [
        field for field in REQUIRED_ENTRY_FIELDS
        if not isinstance(entry.get(field), str) or not entry[field].strip()
    ]
    if missing:
        return "missing required metadata: " + ", ".join(missing)
    kind = entry["kind"].strip()
    if kind not in KINDS:
        return f"unsupported inventory kind: {kind}"
    resource_location = entry["resource_location"].strip()
    if resource_location.count(":") != 1 or resource_location.startswith(":") or resource_location.endswith(":"):
        return f"invalid resource_location: {resource_location!r}"
    expected_namespace = resource_location.split(":", 1)[0]
    if entry["namespace"].strip() != expected_namespace:
        return f"namespace mismatch: expected {expected_namespace!r}"
    if entry.get("present_at_runtime") is not True:
        return "runtime entry must declare present_at_runtime=true"
    return None


def classify_entries(entries: list[Any], overrides: dict[str, dict[str, str]]) -> tuple[list[dict[str, Any]], set[str]]:
    classified: list[dict[str, Any]] = []
    seen: set[str] = set()
    runtime_keys: set[str] = set()

    for index, raw in enumerate(entries):
        error = entry_validation_error(raw)
        entry = dict(raw) if isinstance(raw, dict) else {"raw_value": raw}
        key = resource_key(entry, index)
        if error is None and key in seen:
            error = f"duplicate registry inventory key: {key}"
        seen.add(key)

        if error is not None:
            state = "ERROR"
            reason = error
        else:
            runtime_keys.add(key)
            override = overrides.get(key)
            state = override["state"] if override else "AUTO"
            reason = override["reason"] if override else "registry/runtime-derived coverage"

        entry["coverage_state"] = state
        entry["coverage_reason"] = reason
        entry["inventory_key"] = key
        classified.append(entry)

    unknown_overrides = sorted(set(overrides) - runtime_keys)
    if unknown_overrides:
        raise ReportError(
            "coverage overrides reference entries absent from current runtime: " + ", ".join(unknown_overrides)
        )
    classified.sort(key=lambda item: item["inventory_key"])
    return classified, runtime_keys


def mod_ids(payload: dict[str, Any]) -> set[str]:
    result: set[str] = set()
    for raw in payload.get("loaded_mods", []):
        if isinstance(raw, dict) and isinstance(raw.get("mod_id"), str) and raw["mod_id"].strip():
            result.add(raw["mod_id"].strip())
    return result


def valid_entry_keys(payload: dict[str, Any]) -> set[str]:
    keys: set[str] = set()
    for index, raw in enumerate(payload.get("entries", [])):
        if not isinstance(raw, dict) or entry_validation_error(raw) is not None:
            continue
        keys.add(resource_key(raw, index))
    return keys


def drift(current: dict[str, Any], previous: dict[str, Any] | None) -> dict[str, list[str]]:
    if previous is None:
        return {
            "added_mods": [],
            "removed_mods": [],
            "added_registry_entries": [],
            "removed_registry_entries": [],
            "orphaned_registry_entries": [],
        }
    current_mods = mod_ids(current)
    previous_mods = mod_ids(previous)
    current_entries = valid_entry_keys(current)
    previous_entries = valid_entry_keys(previous)
    removed_entries = sorted(previous_entries - current_entries)
    return {
        "added_mods": sorted(current_mods - previous_mods),
        "removed_mods": sorted(previous_mods - current_mods),
        "added_registry_entries": sorted(current_entries - previous_entries),
        "removed_registry_entries": removed_entries,
        "orphaned_registry_entries": removed_entries,
    }


def modlist_comparison(runtime: dict[str, Any], modlist: dict[str, Any] | None) -> dict[str, list[str]] | None:
    if modlist is None:
        return None
    if not isinstance(modlist, dict) or modlist.get("schema") != 1:
        raise ReportError("modlist inventory schema must be 1")
    top = modlist.get("top_level_mods")
    if not isinstance(top, list):
        raise ReportError("modlist top_level_mods must be an array")
    listed = {
        raw["mod_id"].strip()
        for raw in top
        if isinstance(raw, dict) and isinstance(raw.get("mod_id"), str) and raw["mod_id"].strip()
    }
    loaded = mod_ids(runtime)
    return {
        "listed_but_not_loaded": sorted(listed - loaded),
        "loaded_but_not_listed": sorted(loaded - listed),
    }


def build_summary(entries: list[dict[str, Any]]) -> list[dict[str, Any]]:
    by_namespace: dict[str, dict[str, Any]] = {}
    for entry in entries:
        namespace = entry.get("namespace")
        if not isinstance(namespace, str) or not namespace.strip():
            namespace = "__invalid__"
        namespace = namespace.strip()
        row = by_namespace.setdefault(namespace, {
            "namespace": namespace,
            "mod": entry.get("mod_display_name") or namespace,
            "entities": 0,
            "flora": 0,
            "trees": 0,
            "crops": 0,
            "biomes": 0,
            "structures": 0,
            "dimensions": 0,
            **{state: 0 for state in STATES},
        })
        kind = entry.get("kind")
        if kind in SUMMARY_KIND_KEYS:
            row[SUMMARY_KIND_KEYS[kind]] += 1
        row[entry["coverage_state"]] += 1
    return [by_namespace[key] for key in sorted(by_namespace)]


def coverage_totals(entries: list[dict[str, Any]]) -> dict[str, int]:
    counts = Counter(item["coverage_state"] for item in entries)
    return {state: counts.get(state, 0) for state in STATES}


def detailed_groups(entries: list[dict[str, Any]]) -> dict[str, dict[str, list[dict[str, Any]]]]:
    grouped: dict[str, dict[str, list[dict[str, Any]]]] = {}
    for kind in KINDS:
        grouped[kind] = {}
    for entry in entries:
        kind = entry.get("kind")
        if kind not in KINDS:
            continue
        namespace = entry.get("namespace") if isinstance(entry.get("namespace"), str) else "__invalid__"
        namespace = namespace.strip() or "__invalid__"
        grouped[kind].setdefault(namespace, []).append(entry)
    for namespaces in grouped.values():
        for values in namespaces.values():
            values.sort(key=lambda item: item["inventory_key"])
    return grouped


def render_markdown(payload: dict[str, Any]) -> str:
    totals = payload["coverage_totals"]
    lines = [
        "# Compêndio Natural — Cobertura do inventário runtime",
        "",
        f"- Minecraft: `{payload.get('minecraft_version', 'desconhecido')}`",
        f"- Loader: `{payload.get('loader', 'desconhecido')}`",
        f"- Entradas: **{len(payload['entries'])}**",
        "- Cobertura: " + ", ".join(f"{state}={totals[state]}" for state in STATES),
        "",
        "## Cobertura por namespace",
        "",
        "| namespace | mod | entities | flora | trees | crops | biomes | structures | dimensions | AUTO | CURATED | ADAPTER | IGNORED | ERROR |",
        "| --- | --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |",
    ]
    for row in payload["namespace_summary"]:
        lines.append(
            "| {namespace} | {mod} | {entities} | {flora} | {trees} | {crops} | {biomes} | {structures} | {dimensions} | {AUTO} | {CURATED} | {ADAPTER} | {IGNORED} | {ERROR} |".format(
                **{key: str(value).replace("|", "\\|") for key, value in row.items()}
            )
        )

    comparison = payload.get("modlist_comparison")
    if comparison is not None:
        lines.extend(["", "## Modlist x runtime", ""])
        for key in ("listed_but_not_loaded", "loaded_but_not_listed"):
            values = comparison[key]
            lines.append(f"- `{key}`: " + (", ".join(f"`{value}`" for value in values) if values else "nenhum"))

    drift_payload = payload["drift"]
    lines.extend(["", "## Drift", ""])
    for key in ("added_mods", "removed_mods", "added_registry_entries", "removed_registry_entries", "orphaned_registry_entries"):
        values = drift_payload[key]
        lines.append(f"- `{key}`: " + (", ".join(f"`{value}`" for value in values) if values else "nenhum"))

    groups = detailed_groups(payload["entries"])
    lines.extend(["", "## Listas detalhadas", ""])
    for kind in KINDS:
        lines.append(f"### {kind}")
        lines.append("")
        namespaces = groups[kind]
        if not namespaces:
            lines.append("Nenhuma entrada.")
            lines.append("")
            continue
        for namespace in sorted(namespaces):
            lines.append(f"#### `{namespace}`")
            lines.append("")
            for entry in namespaces[namespace]:
                lines.append(
                    f"- `{entry['resource_location']}` — **{entry['coverage_state']}** — "
                    f"`{entry['translation_key']}`"
                )
            lines.append("")

    errors = [entry for entry in payload["entries"] if entry["coverage_state"] == "ERROR"]
    if errors:
        lines.extend(["## Erros de inventário", ""])
        for entry in errors:
            lines.append(f"- `{entry['inventory_key']}` — {entry['coverage_reason']}")
        lines.append("")
    return "\n".join(lines)


def build_report(
    runtime: dict[str, Any],
    overrides: dict[str, dict[str, str]],
    previous: dict[str, Any] | None,
    modlist: dict[str, Any] | None,
) -> dict[str, Any]:
    classified, _ = classify_entries(runtime["entries"], overrides)
    report = {
        "schema": 1,
        "minecraft_version": runtime.get("minecraft_version"),
        "loader": runtime.get("loader"),
        "runtime_fingerprint_sha256": runtime.get("runtime_fingerprint_sha256"),
        "loaded_mods": runtime.get("loaded_mods", []),
        "entries": classified,
        "coverage_totals": coverage_totals(classified),
        "namespace_summary": build_summary(classified),
        "drift": drift(runtime, previous),
    }
    comparison = modlist_comparison(runtime, modlist)
    if comparison is not None:
        report["modlist_comparison"] = comparison
    return report


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("runtime", type=Path, help="runtime-registry-inventory.json")
    parser.add_argument("--overrides", type=Path, default=None, help="optional coverage override JSON")
    parser.add_argument("--previous", type=Path, default=None, help="optional previous runtime inventory for drift")
    parser.add_argument("--modlist", type=Path, default=None, help="optional parsed modpack-inventory.json")
    parser.add_argument("--json", type=Path, default=Path("generated/compendium/coverage-report.json"))
    parser.add_argument("--markdown", type=Path, default=Path("generated/compendium/coverage-report.md"))
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    try:
        runtime = validate_runtime(read_json(args.runtime), args.runtime)
        overrides = load_overrides(args.overrides)
        previous = validate_runtime(read_json(args.previous), args.previous) if args.previous else None
        modlist = read_json(args.modlist) if args.modlist else None
        report = build_report(runtime, overrides, previous, modlist)
        args.json.parent.mkdir(parents=True, exist_ok=True)
        args.markdown.parent.mkdir(parents=True, exist_ok=True)
        args.json.write_text(json.dumps(report, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
        args.markdown.write_text(render_markdown(report), encoding="utf-8")
    except (OSError, ReportError) as exc:
        print(f"Compendium runtime coverage report: FAIL: {exc}", file=sys.stderr)
        return 1

    error_count = report["coverage_totals"]["ERROR"]
    if error_count:
        print(f"Compendium runtime coverage report: ERROR ({error_count} invalid/unresolved entries)", file=sys.stderr)
        return 2
    print(
        "Compendium runtime coverage report: PASS "
        f"({len(report['entries'])} entries, {len(report['namespace_summary'])} namespaces)"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
