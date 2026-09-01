#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import re
import sys

ROOT = Path(__file__).resolve().parents[1]
ERRORS: list[str] = []


def read(path: str) -> str:
    target = ROOT / path
    if not target.is_file():
        ERRORS.append(f"missing required release evidence: {path}")
        return ""
    return target.read_text(encoding="utf-8")


def require(condition: bool, message: str) -> None:
    if not condition:
        ERRORS.append(message)


def require_tokens(path: str, *tokens: str) -> None:
    text = read(path)
    for token in tokens:
        require(token in text, f"{path}: missing release token {token!r}")


def main() -> int:
    mod = read("src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java")
    volcanoes = read("src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java")
    client = read("src/main/java/dev/gustavopere/volcanoes/VolcanoesClientMod.java")
    toml = read("src/main/resources/META-INF/neoforge.mods.toml")
    build = read("build.gradle")

    require("VolcanoesMod.initialize(modBus, container);" in mod,
            "RPG Skill Tree must bootstrap the native Volcanoes subsystem")
    require('@Mod("volcanoes")' not in volcanoes and "@Mod(VolcanoesMod.MOD_ID)" not in volcanoes,
            "Volcanoes must not remain an independent NeoForge @Mod entry point")
    require("@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID" in client,
            "Volcanoes client subscriber must be owned by rpgskilltree")
    require(toml.count("[[mods]]") == 1,
            "unified artifact must declare exactly one NeoForge mod")
    require('modId="volcanoes"' not in toml,
            "volcanoes must remain a data/resource namespace, not a second mod id")
    require('config="volcanoes.mixins.json"' in toml,
            "unified metadata must register the Volcanoes mixin configuration")

    required_types = re.findall(
        r'\[\[dependencies\.\$\{mod_id\}\]\]\s*\nmodId="([^"]+)"\s*\ntype="required"',
        toml,
        re.MULTILINE,
    )
    require(set(required_types) <= {"neoforge", "minecraft"},
            f"optional provider became mandatory: {required_types}")
    require(not any("tfc" in mod_id.lower() or "terrafirma" in mod_id.lower()
                    for mod_id in required_types),
            "TFC/TerraFirmaCraft must not be a required runtime dependency")

    for line in build.splitlines():
        stripped = line.strip().lower()
        if stripped.startswith(("implementation", "runtimeonly", "localruntime")):
            require("terrafirma" not in stripped and "tfc" not in stripped,
                    f"TFC runtime dependency found in build.gradle: {line.strip()}")

    require_tokens(
        "build.gradle",
        "Volcanoes-BSD-2-Clause.txt",
        "THIRD_PARTY_NOTICES.md",
    )
    require_tokens(
        "scripts/verify-volcanoes-import-provenance.py",
        "eaddc3232dfc600780769f4a5e7e45ff1e50181c",
        "VOLCANOES_IMPORT_PROVENANCE status=GREEN",
    )

    for workflow in (
        ".github/workflows/alpha2-build.yml",
        ".github/workflows/volcanoes-consolidation-contract.yml",
        ".github/workflows/volcanoes-cold-sweat-acceptance.yml",
        ".github/workflows/volcanoes-performance-acceptance.yml",
        ".github/workflows/volcanoes-minecolonies-claim-acceptance.yml",
        ".github/workflows/volcanoes-create-sable-acceptance.yml",
        ".github/workflows/volcanoes-rns-hydrothermal-acceptance.yml",
        ".github/workflows/volcanoes-full-pack-compatibility-acceptance.yml",
        ".github/workflows/volcanoes-third-party-provenance-audit.yml",
        ".github/workflows/volcanoes-worldgen-compatibility-matrix.yml",
    ):
        read(workflow)

    for task in (
        "docs/archive/volcanoes/07-hardening/✅-01-test-matrix.md",
        "docs/archive/volcanoes/07-hardening/✅-02-performance.md",
        "docs/archive/volcanoes/07-hardening/✅-03-world-upgrade.md",
        "docs/archive/volcanoes/07-hardening/✅-04-release-checklist.md",
        "docs/archive/volcanoes/07-hardening/✅-05-third-party-licenses-provenance.md",
    ):
        read(task)

    require_tokens(
        "src/test/java/dev/gustavopere/rpgskilltree/runtime/volcanoes/NativeVolcanoesServicesContractTest.java",
        "NativeVolcanoesServices",
    )
    require_tokens(
        "src/main/java/dev/gustavopere/rpgskilltree/runtime/volcanoes/NativeVolcanoesServices.java",
        "GeologicalDepositSource",
        "VolcanicRegionService",
        "TectonicService",
        "AtmosphereState",
        "atmosphereAt",
        "pressureAtm",
    )

    print(f"VOLCANOES_CONSOLIDATED_RELEASE_READINESS errors={len(ERRORS)}")
    for error in ERRORS:
        print(f"ERROR: {error}")
    if ERRORS:
        return 1
    print("VOLCANOES_CONSOLIDATED_RELEASE_READINESS status=GREEN")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
