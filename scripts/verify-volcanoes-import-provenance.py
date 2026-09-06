#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
SOURCE_SHA = "eaddc3232dfc600780769f4a5e7e45ff1e50181c"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def read(path: str) -> str:
    target = ROOT / path
    require(target.is_file(), f"missing provenance evidence: {path}")
    return target.read_text(encoding="utf-8")


def main() -> int:
    import_note = read("docs/volcanoes/IMPORT_PROVENANCE.md")
    require(SOURCE_SHA in import_note, "import provenance must pin the canonical Volcanoes source SHA")
    require("single" in import_note.lower() and "rpgskilltree" in import_note,
            "import provenance must identify the single consolidated RPG artifact")

    inventory_path = ROOT / "docs/volcanoes/provenance/third-party-inventory.json"
    require(inventory_path.is_file(), "missing frozen Volcanoes third-party inventory")
    inventory = json.loads(inventory_path.read_text(encoding="utf-8"))
    require(inventory.get("schema_version") == 1, "unexpected Volcanoes provenance schema")
    require(inventory.get("default_derivation_status") == "NO_DERIVED_MATERIAL_FOUND",
            "Volcanoes import must retain the canonical no-derived-material default")

    policy = inventory.get("release_policy", {})
    require(policy.get("review_required_blocks_release") is True,
            "provenance release policy must fail closed on review-required material")
    require(policy.get("derived_material_requires_exact_upstream_revision_and_paths") is True,
            "derived material must require exact upstream provenance")
    require(policy.get("derived_material_requires_permission_and_notice_evidence") is True,
            "derived material must require permission/notice evidence")
    require(policy.get("external_dependencies_are_not_redistributed_in_volcanoes_jar") is True,
            "external provider dependencies must remain non-redistributed")

    projects = inventory.get("projects")
    require(isinstance(projects, list) and len(projects) >= 40,
            "frozen Volcanoes inventory is unexpectedly incomplete")
    ids = {entry.get("id") for entry in projects if isinstance(entry, dict)}
    for required_id in (
        "tfc-volcanoes", "neoforge", "minecolonies", "cold-sweat", "create-rns",
        "sable", "create", "curios", "destroy", "kubejs",
    ):
        require(required_id in ids, f"missing canonical provenance record: {required_id}")

    serialized = json.dumps(inventory, sort_keys=True)
    for blocker in ("REVIEW_REQUIRED", "PERMISSION_REQUIRED"):
        require(blocker not in serialized, f"unresolved provenance blocker remains: {blocker}")

    for evidence in (
        "docs/volcanoes/provenance/THIRD_PARTY_AUDIT.md",
        "docs/volcanoes/upstream/NOTICE.md",
        "docs/volcanoes/upstream/TFC_VOLCANOES.md",
        "docs/volcanoes/repository-source/SOURCES.md",
        "docs/volcanoes/repository-source/THIRD_PARTY_NOTICES.md",
        "licenses/Volcanoes-BSD-2-Clause.txt",
    ):
        read(evidence)

    build = read("build.gradle")
    require("Volcanoes-BSD-2-Clause.txt" in build,
            "unified JAR build must package the Volcanoes BSD notice")
    require("THIRD_PARTY_NOTICES.md" in build,
            "unified JAR build must package third-party notices")

    mods = read("src/main/resources/META-INF/neoforge.mods.toml")
    require(mods.count("[[mods]]") == 1 and 'modId="volcanoes"' not in mods,
            "provenance gate requires a single distributed mod, not a nested Volcanoes mod")

    print(
        "VOLCANOES_IMPORT_PROVENANCE status=GREEN "
        f"source_sha={SOURCE_SHA} projects={len(projects)} artifact=rpgskilltree"
    )
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (AssertionError, json.JSONDecodeError) as exc:
        print(f"VOLCANOES_IMPORT_PROVENANCE status=RED reason={exc}", file=sys.stderr)
        raise SystemExit(1)
