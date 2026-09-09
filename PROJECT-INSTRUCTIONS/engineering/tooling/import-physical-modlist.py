#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import json
import re
from pathlib import Path

SCHEMA_VERSION = 1
TARGET = {"minecraft": "1.21.1", "loader": "neoforge", "java": 21}
SHA256_RE = re.compile(r"^[0-9a-f]{64}$")
MODS_COUNT_RE = re.compile(r"^Mods count:\s*(\d+)\s*$")


def _parse_declared_count(lines):
    for line in lines:
        match = MODS_COUNT_RE.match(line.strip())
        if match:
            return int(match.group(1))
    raise ValueError("missing 'Mods count:' declaration")


def _find_table_start(lines):
    header_index = None
    for index, line in enumerate(lines):
        if "jar name" in line and "mod id" in line and "mod version" in line:
            header_index = index
            break
    if header_index is None:
        raise ValueError("missing modlist table header")
    if header_index + 1 >= len(lines) or "+" not in lines[header_index + 1]:
        raise ValueError("missing modlist table separator")
    return header_index + 2


def _split_mixins(value):
    return [part.strip() for part in value.split(",") if part.strip()]


def parse_modlist_text(text, *, captured_at, source_sha256=None, source_name="modlist.txt"):
    if not isinstance(captured_at, str) or not captured_at.strip():
        raise ValueError("captured_at must be a non-empty string")
    lines = text.splitlines()
    declared_top_level_mods = _parse_declared_count(lines)
    start = _find_table_start(lines)
    entries = []
    stack = {}

    for source_line, line in enumerate(lines[start:], start=start + 1):
        if not line.strip():
            continue
        parts = line.split("|")
        if len(parts) != 8:
            raise ValueError(f"line {source_line}: expected 8 table columns, got {len(parts)}")
        raw_jar = parts[0]
        leading_spaces = len(raw_jar) - len(raw_jar.lstrip(" "))
        if leading_spaces % 4 != 0:
            raise ValueError(f"line {source_line}: nesting indentation must be a multiple of 4 spaces")
        nesting_depth = leading_spaces // 4
        jar = raw_jar.strip()
        if not jar:
            raise ValueError(f"line {source_line}: jar name must not be empty")
        if nesting_depth > 0 and not jar.startswith("/META-INF/"):
            raise ValueError(f"line {source_line}: nested JAR must use a /META-INF/ path")

        if nesting_depth == 0:
            parent_jar = None
        else:
            parent = stack.get(nesting_depth - 1)
            if parent is None:
                raise ValueError(f"line {source_line}: nested JAR has no parent at depth {nesting_depth - 1}")
            parent_jar = parent["jar"]

        entry = {
            "ordinal": len(entries),
            "jar": jar,
            "notes": parts[1].strip(),
            "mod_id": parts[2].strip(),
            "mod_name": parts[3].strip(),
            "version": parts[4].strip(),
            "mixin_configs": _split_mixins(parts[5].strip()),
            "modrinth_hash": parts[6].strip(),
            "curseforge_hash": parts[7].strip(),
            "top_level": nesting_depth == 0,
            "nesting_depth": nesting_depth,
            "parent_jar": parent_jar,
        }
        entries.append(entry)
        stack[nesting_depth] = entry
        for depth in [depth for depth in stack if depth > nesting_depth]:
            del stack[depth]

    parsed_top_level_mods = sum(1 for entry in entries if entry["top_level"])
    parsed_nested_mods = len(entries) - parsed_top_level_mods
    if parsed_top_level_mods != declared_top_level_mods:
        raise ValueError(
            f"declared top-level mod count {declared_top_level_mods} does not match "
            f"parsed top-level mod count {parsed_top_level_mods}"
        )

    loader_entry = next(
        (entry for entry in entries if entry["top_level"] and entry["mod_id"] == "neoforge"),
        None,
    )
    if loader_entry is None:
        raise ValueError("top-level NeoForge loader entry not found")
    loader_version = loader_entry["version"]
    if loader_version.startswith("neoforge-"):
        loader_version = loader_version[len("neoforge-"):]

    return {
        "schema_version": SCHEMA_VERSION,
        "captured_at": captured_at,
        "source_name": source_name,
        "source_sha256": source_sha256 or hashlib.sha256(text.encode("utf-8")).hexdigest(),
        "target": dict(TARGET),
        "loader": {"mod_id": "neoforge", "version": loader_version},
        "declared_top_level_mods": declared_top_level_mods,
        "parsed_top_level_mods": parsed_top_level_mods,
        "parsed_nested_mods": parsed_nested_mods,
        "entries": entries,
    }


def parse_modlist_bytes(data, *, captured_at, source_name="modlist.txt"):
    try:
        text = data.decode("utf-8")
    except UnicodeDecodeError as exc:
        raise ValueError(f"modlist source is not UTF-8: {exc}") from exc
    return parse_modlist_text(
        text,
        captured_at=captured_at,
        source_sha256=hashlib.sha256(data).hexdigest(),
        source_name=source_name,
    )


def build_provider_catalog(snapshot):
    grouped = {}
    for entry in snapshot.get("entries", []):
        mod_id = entry.get("mod_id", "")
        if not mod_id:
            continue
        provider = grouped.setdefault(
            mod_id,
            {
                "mod_id": mod_id,
                "versions": set(),
                "top_level_occurrences": 0,
                "nested_occurrences": 0,
                "occurrences": [],
            },
        )
        version = entry.get("version", "")
        if version:
            provider["versions"].add(version)
        if entry.get("top_level") is True:
            provider["top_level_occurrences"] += 1
        else:
            provider["nested_occurrences"] += 1
        provider["occurrences"].append(
            {
                "jar": entry.get("jar", ""),
                "version": version,
                "top_level": bool(entry.get("top_level")),
                "nesting_depth": entry.get("nesting_depth", 0),
                "parent_jar": entry.get("parent_jar"),
                "modrinth_hash": entry.get("modrinth_hash", ""),
                "curseforge_hash": entry.get("curseforge_hash", ""),
            }
        )

    result = {}
    for mod_id in sorted(grouped):
        provider = grouped[mod_id]
        provider["versions"] = sorted(provider["versions"])
        provider["occurrences"] = sorted(
            provider["occurrences"],
            key=lambda occurrence: (
                not occurrence["top_level"],
                occurrence["nesting_depth"],
                occurrence["parent_jar"] or "",
                occurrence["jar"],
                occurrence["version"],
            ),
        )
        result[mod_id] = provider
    return result


def _topology_signature(provider):
    return sorted(
        (occurrence["top_level"], occurrence["nesting_depth"], occurrence["parent_jar"])
        for occurrence in provider["occurrences"]
    )


def _artifact_signature(provider):
    return sorted(
        (occurrence["jar"], occurrence["modrinth_hash"], occurrence["curseforge_hash"])
        for occurrence in provider["occurrences"]
    )


def compare_snapshots(before, after):
    before_catalog = build_provider_catalog(before)
    after_catalog = build_provider_catalog(after)
    before_ids = set(before_catalog)
    after_ids = set(after_catalog)
    common = before_ids & after_ids
    return {
        "before_captured_at": before.get("captured_at"),
        "after_captured_at": after.get("captured_at"),
        "added_mod_ids": sorted(after_ids - before_ids),
        "removed_mod_ids": sorted(before_ids - after_ids),
        "version_changed_mod_ids": sorted(
            mod_id for mod_id in common
            if before_catalog[mod_id]["versions"] != after_catalog[mod_id]["versions"]
        ),
        "topology_changed_mod_ids": sorted(
            mod_id for mod_id in common
            if _topology_signature(before_catalog[mod_id]) != _topology_signature(after_catalog[mod_id])
        ),
        "artifact_changed_mod_ids": sorted(
            mod_id for mod_id in common
            if _artifact_signature(before_catalog[mod_id]) != _artifact_signature(after_catalog[mod_id])
        ),
    }


def validate_normalized_snapshot(snapshot):
    errors = []
    if snapshot.get("schema_version") != SCHEMA_VERSION:
        errors.append("schema_version must be 1")
    if snapshot.get("target") != TARGET:
        errors.append("target must match Minecraft 1.21.1 / NeoForge / Java 21")
    source_sha256 = snapshot.get("source_sha256")
    if not isinstance(source_sha256, str) or SHA256_RE.fullmatch(source_sha256) is None:
        errors.append("source_sha256 must be a lowercase SHA-256 digest")
    entries = snapshot.get("entries")
    if not isinstance(entries, list):
        return errors + ["entries must be an array"]

    top_count = sum(1 for entry in entries if isinstance(entry, dict) and entry.get("top_level") is True)
    nested_count = len(entries) - top_count
    if snapshot.get("declared_top_level_mods") != top_count:
        errors.append("declared_top_level_mods does not match entry topology")
    if snapshot.get("parsed_top_level_mods") != top_count:
        errors.append("parsed_top_level_mods does not match entries")
    if snapshot.get("parsed_nested_mods") != nested_count:
        errors.append("parsed_nested_mods does not match entries")

    seen_jars = {}
    for index, entry in enumerate(entries):
        if not isinstance(entry, dict):
            errors.append(f"entries[{index}] must be an object")
            continue
        required = {
            "ordinal", "jar", "mod_id", "mod_name", "version",
            "top_level", "nesting_depth", "parent_jar",
        }
        missing = sorted(required.difference(entry))
        if missing:
            errors.append(f"entries[{index}] missing fields: {', '.join(missing)}")
            continue
        if entry["ordinal"] != index:
            errors.append(f"entries[{index}].ordinal must equal its array index")
        depth = entry["nesting_depth"]
        if not isinstance(depth, int) or depth < 0:
            errors.append(f"entries[{index}].nesting_depth must be a non-negative integer")
            continue
        if entry["top_level"] != (depth == 0):
            errors.append(f"entries[{index}].top_level conflicts with nesting_depth")
        if depth == 0:
            if entry["parent_jar"] is not None:
                errors.append(f"entries[{index}].parent_jar must be null for top-level entries")
        else:
            if not isinstance(entry["parent_jar"], str) or not entry["parent_jar"]:
                errors.append(f"entries[{index}].parent_jar is required for nested entries")
            elif entry["parent_jar"] not in seen_jars:
                errors.append(f"entries[{index}].parent_jar must reference an earlier entry")
        if "mixin_configs" in entry and not isinstance(entry["mixin_configs"], list):
            errors.append(f"entries[{index}].mixin_configs must be an array when present")
        seen_jars[entry["jar"]] = entry

    loader = snapshot.get("loader")
    if not isinstance(loader, dict) or loader.get("mod_id") != "neoforge" or not loader.get("version"):
        errors.append("loader must identify the physical NeoForge entry")
    return errors


def build_persisted_snapshot(snapshot):
    entry_fields = (
        "ordinal", "jar", "mod_id", "mod_name", "version",
        "top_level", "nesting_depth", "parent_jar",
    )
    return {
        "schema_version": snapshot["schema_version"],
        "captured_at": snapshot["captured_at"],
        "source_name": snapshot["source_name"],
        "source_sha256": snapshot["source_sha256"],
        "target": snapshot["target"],
        "loader": snapshot["loader"],
        "declared_top_level_mods": snapshot["declared_top_level_mods"],
        "parsed_top_level_mods": snapshot["parsed_top_level_mods"],
        "parsed_nested_mods": snapshot["parsed_nested_mods"],
        "entries": [
            {field: entry[field] for field in entry_fields}
            for entry in snapshot["entries"]
        ],
    }


def build_persisted_provider_catalog(snapshot):
    providers = build_provider_catalog(snapshot)
    ordinals_by_mod_id = {}
    for entry in snapshot["entries"]:
        if entry.get("mod_id"):
            ordinals_by_mod_id.setdefault(entry["mod_id"], []).append(entry["ordinal"])
    return {
        "schema_version": SCHEMA_VERSION,
        "captured_at": snapshot["captured_at"],
        "source_sha256": snapshot["source_sha256"],
        "provider_count": len(providers),
        "providers": [
            {
                "mod_id": mod_id,
                "versions": provider["versions"],
                "top_level_occurrences": provider["top_level_occurrences"],
                "nested_occurrences": provider["nested_occurrences"],
                "entry_ordinals": ordinals_by_mod_id[mod_id],
            }
            for mod_id, provider in providers.items()
        ],
    }


def validate_persisted_provider_catalog(snapshot, catalog):
    errors = []
    if catalog.get("schema_version") != SCHEMA_VERSION:
        errors.append("provider catalog schema_version must be 1")
    if catalog.get("captured_at") != snapshot.get("captured_at"):
        errors.append("provider catalog captured_at must match snapshot")
    if catalog.get("source_sha256") != snapshot.get("source_sha256"):
        errors.append("provider catalog source_sha256 must match snapshot")
    expected = build_persisted_provider_catalog(snapshot)
    if catalog != expected:
        errors.append("provider catalog must be derived deterministically from the normalized snapshot")
    return errors


def _load_snapshot(path):
    return json.loads(Path(path).read_text(encoding="utf-8"))


def main(argv=None):
    parser = argparse.ArgumentParser(description="Normalize and validate physical Minecraft modlist snapshots.")
    parser.add_argument("input", nargs="?", help="physical modlist.txt input")
    parser.add_argument("--captured-at")
    parser.add_argument("--output")
    parser.add_argument("--providers-output")
    parser.add_argument("--check")
    parser.add_argument("--providers")
    parser.add_argument("--compare", nargs=2, metavar=("BEFORE", "AFTER"))
    args = parser.parse_args(argv)

    if args.check:
        snapshot = _load_snapshot(args.check)
        errors = validate_normalized_snapshot(snapshot)
        if args.providers:
            errors += validate_persisted_provider_catalog(snapshot, _load_snapshot(args.providers))
        if errors:
            print("I2 PHYSICAL MODLIST VALIDATION: FAIL")
            for error in errors:
                print(f"- {error}")
            return 1
        print("I2 PHYSICAL MODLIST VALIDATION: PASS")
        print(
            f"{snapshot['parsed_top_level_mods']} top-level + "
            f"{snapshot['parsed_nested_mods']} nested entries; "
            f"{len(build_provider_catalog(snapshot))} provider identities"
        )
        return 0

    if args.compare:
        result = compare_snapshots(_load_snapshot(args.compare[0]), _load_snapshot(args.compare[1]))
        print(json.dumps(result, indent=2, ensure_ascii=False, sort_keys=True))
        return 0

    if not args.input or not args.captured_at or not args.output:
        parser.error("import mode requires INPUT, --captured-at and --output")
    input_path = Path(args.input)
    snapshot = parse_modlist_bytes(
        input_path.read_bytes(),
        captured_at=args.captured_at,
        source_name=input_path.name,
    )
    errors = validate_normalized_snapshot(snapshot)
    if errors:
        for error in errors:
            print(f"- {error}")
        return 1
    persisted_snapshot = build_persisted_snapshot(snapshot)
    Path(args.output).parent.mkdir(parents=True, exist_ok=True)
    Path(args.output).write_text(
        json.dumps(persisted_snapshot, ensure_ascii=False, sort_keys=True, separators=(",", ":")) + "\n",
        encoding="utf-8",
    )
    if args.providers_output:
        provider_catalog = build_persisted_provider_catalog(snapshot)
        Path(args.providers_output).parent.mkdir(parents=True, exist_ok=True)
        Path(args.providers_output).write_text(
            json.dumps(provider_catalog, ensure_ascii=False, sort_keys=True, separators=(",", ":")) + "\n",
            encoding="utf-8",
        )
        print(f"Wrote {args.providers_output}")
    print(f"Wrote {args.output}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
