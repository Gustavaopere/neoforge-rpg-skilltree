#!/usr/bin/env python3
"""Parse the modpack table exported by the user's mod inventory tool.

The input format is deliberately treated as data, not trusted prose. Top-level
mods are kept separate from META-INF jar-in-jar dependencies, the declared
count is verified, and the exact input bytes are SHA-256 fingerprinted so drift
is observable across snapshots.
"""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import re
import sys
from typing import Any

COUNT_RE = re.compile(r"^\s*Mods count:\s*(\d+)\s*$", re.IGNORECASE | re.MULTILINE)
EMBEDDED_PREFIXES = ("META-INF/jarjar/", "META-INF/jars/")


class InventoryError(ValueError):
    pass


def _field(parts: list[str], index: int) -> str:
    return parts[index].strip() if index < len(parts) else ""


def _nullable(value: str) -> str | None:
    normalized = value.strip()
    return normalized or None


def parse_modlist_bytes(raw: bytes, source_name: str) -> dict[str, Any]:
    try:
        text = raw.decode("utf-8-sig")
    except UnicodeDecodeError as exc:
        raise InventoryError(f"modlist is not valid UTF-8: {exc}") from exc

    match = COUNT_RE.search(text)
    if not match:
        raise InventoryError("missing declared 'Mods count: N' header")
    declared = int(match.group(1))

    top_level: list[dict[str, Any]] = []
    embedded: list[dict[str, Any]] = []
    current_parent_mod_id: str | None = None
    in_table = False

    for line_number, line in enumerate(text.splitlines(), start=1):
        lowered = line.lower()
        if not in_table:
            if "jar name" in lowered and "mod id" in lowered and "mod version" in lowered:
                in_table = True
            continue

        if "|" not in line:
            continue
        parts = line.split("|")
        raw_name = parts[0]
        jar_name = raw_name.strip()
        if not jar_name or set(jar_name) <= {"-", "+"}:
            continue
        if jar_name.lower() == "jar name":
            continue

        normalized_path = jar_name.replace("\\", "/")
        is_embedded = normalized_path.lstrip("/").startswith(EMBEDDED_PREFIXES)
        mod_id = _field(parts, 2)
        mod_name = _field(parts, 3)
        runtime_version = _field(parts, 4)

        # Some embedded library rows intentionally have blank mod metadata. They
        # remain dependencies, but are keyed by filename rather than promoted.
        synthetic_id = False
        if not mod_id:
            if not is_embedded:
                raise InventoryError(f"line {line_number}: top-level row has no mod id: {jar_name}")
            mod_id = f"__embedded__:{Path(normalized_path).name}"
            synthetic_id = True
        if not mod_name:
            mod_name = mod_id

        common = {
            "filename": jar_name,
            "notes": _nullable(_field(parts, 1)),
            "mod_id": mod_id,
            "mod_name": mod_name,
            "runtime_version": _nullable(runtime_version),
            "published_version": None,
            "mixin_configs": _nullable(_field(parts, 5)),
            "modrinth_hash": _nullable(_field(parts, 6)),
            "curseforge_hash": _nullable(_field(parts, 7)),
        }

        if is_embedded:
            if current_parent_mod_id is None:
                raise InventoryError(f"line {line_number}: embedded dependency has no top-level parent: {jar_name}")
            embedded.append({
                **common,
                "parent_mod_id": current_parent_mod_id,
                "synthetic_mod_id": synthetic_id,
            })
            continue

        top_level.append(common)
        current_parent_mod_id = mod_id

    if not in_table:
        raise InventoryError("modlist table header was not found")
    if len(top_level) != declared:
        raise InventoryError(
            f"declared top-level count {declared} does not match parsed top-level count {len(top_level)}"
        )

    return {
        "schema": 1,
        "source_filename": source_name,
        "snapshot_sha256": hashlib.sha256(raw).hexdigest(),
        "declared_top_level_count": declared,
        "parsed_top_level_count": len(top_level),
        "embedded_dependency_count": len(embedded),
        "top_level_mods": top_level,
        "embedded_dependencies": embedded,
    }


def render_markdown(payload: dict[str, Any]) -> str:
    lines = [
        "# Compêndio Natural — Inventário do modpack",
        "",
        f"- Fonte: `{payload['source_filename']}`",
        f"- SHA-256: `{payload['snapshot_sha256']}`",
        f"- Mods top-level: **{payload['parsed_top_level_count']}**",
        f"- Dependências JAR-in-JAR: **{payload['embedded_dependency_count']}**",
        "",
        "## Mods top-level",
        "",
        "| mod id | mod | runtime | arquivo |",
        "| --- | --- | --- | --- |",
    ]
    for entry in payload["top_level_mods"]:
        lines.append(
            "| {mod_id} | {mod_name} | {runtime} | `{filename}` |".format(
                mod_id=entry["mod_id"].replace("|", "\\|"),
                mod_name=entry["mod_name"].replace("|", "\\|"),
                runtime=(entry["runtime_version"] or "—").replace("|", "\\|"),
                filename=entry["filename"].replace("|", "\\|"),
            )
        )

    lines.extend([
        "",
        "## Dependências embarcadas",
        "",
        "Essas entradas são registradas para proveniência/drift, mas não são promovidas automaticamente a conteúdo editorial.",
        "",
        "| parent | mod id | runtime | arquivo |",
        "| --- | --- | --- | --- |",
    ])
    for entry in payload["embedded_dependencies"]:
        lines.append(
            "| {parent} | {mod_id} | {runtime} | `{filename}` |".format(
                parent=entry["parent_mod_id"].replace("|", "\\|"),
                mod_id=entry["mod_id"].replace("|", "\\|"),
                runtime=(entry["runtime_version"] or "—").replace("|", "\\|"),
                filename=entry["filename"].replace("|", "\\|"),
            )
        )
    lines.append("")
    return "\n".join(lines)


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("input", type=Path, help="canonical modlist text file")
    parser.add_argument(
        "--json",
        type=Path,
        default=Path("generated/compendium/modpack-inventory.json"),
        help="JSON inventory output",
    )
    parser.add_argument(
        "--markdown",
        type=Path,
        default=Path("generated/compendium/modpack-inventory.md"),
        help="Markdown inventory output",
    )
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    try:
        raw = args.input.read_bytes()
        payload = parse_modlist_bytes(raw, args.input.name)
        args.json.parent.mkdir(parents=True, exist_ok=True)
        args.markdown.parent.mkdir(parents=True, exist_ok=True)
        args.json.write_text(json.dumps(payload, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
        args.markdown.write_text(render_markdown(payload), encoding="utf-8")
    except (OSError, InventoryError) as exc:
        print(f"Compendium modlist inventory: FAIL: {exc}", file=sys.stderr)
        return 1

    print(
        "Compendium modlist inventory: PASS "
        f"({payload['parsed_top_level_count']} top-level, {payload['embedded_dependency_count']} embedded, "
        f"sha256={payload['snapshot_sha256']})"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
