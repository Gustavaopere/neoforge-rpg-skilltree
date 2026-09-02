#!/usr/bin/env python3
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]


def text(path: str) -> str:
    p = ROOT / path
    if not p.is_file():
        raise AssertionError(f"missing required file: {path}")
    return p.read_text(encoding="utf-8")


def require(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def main() -> int:
    volcanoes_root = ROOT / "src/main/java/dev/gustavopere/volcanoes"
    volcanoes = text("src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java")
    rpg = text("src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java")
    mods_toml = text("src/main/resources/META-INF/neoforge.mods.toml")
    mixins = text("src/main/resources/volcanoes.mixins.json")
    rns_mixin = text("src/main/java/dev/gustavopere/volcanoes/mixin/rns/CustomServerDepositLocationMixin.java")
    build = text("build.gradle")
    create_sable_acceptance = text(".github/workflows/volcanoes-create-sable-acceptance.yml")
    minecolonies_acceptance = text(".github/workflows/volcanoes-minecolonies-claim-acceptance.yml")
    full_pack_installer = text(".github/scripts/volcanoes/install_full_pack_acceptance.sh")

    stray_mods = []
    stale_subscribers = []
    for source in volcanoes_root.rglob("*.java"):
        source_text = source.read_text(encoding="utf-8")
        relative = source.relative_to(ROOT).as_posix()
        if "@Mod(" in source_text:
            stray_mods.append(relative)
        if "@EventBusSubscriber(modid = VolcanoesMod.MOD_ID" in source_text:
            stale_subscribers.append(relative)
    require(not stray_mods,
            "Volcanoes must not declare any independent @Mod entry point: " + ", ".join(stray_mods))
    require(not stale_subscribers,
            "Volcanoes event subscribers must be owned by rpgskilltree, not the removed standalone mod id: "
            + ", ".join(stale_subscribers))

    require('public static void initialize(IEventBus modBus, ModContainer container)' in volcanoes,
            "Volcanoes must expose the native subsystem initializer")
    require('public static final String MOD_ID = "volcanoes";' in volcanoes,
            "the legacy volcanoes namespace must remain stable for persistent/resource IDs")
    require('"volcanoes-server.toml"' in volcanoes,
            "the standalone Volcanoes server config filename must remain stable")

    require('import dev.gustavopere.volcanoes.VolcanoesMod;' in rpg,
            "RPG bootstrap must import the native Volcanoes subsystem")
    require('ModContainer container' in rpg,
            "RPG bootstrap must receive ModContainer for the imported server config")
    require('VolcanoesMod.initialize(modBus, container);' in rpg,
            "RPG bootstrap must initialize Volcanoes inside the single mod lifecycle")

    require(mods_toml.count('[[mods]]') == 1,
            "the consolidated artifact must contain exactly one [[mods]] declaration")
    require('modId="volcanoes"' not in mods_toml,
            "volcanoes must not be advertised as a second mod")
    require('config="rpgskilltree.mixins.json"' in mods_toml,
            "RPG mixin config must remain registered")
    require('config="volcanoes.mixins.json"' in mods_toml,
            "Volcanoes mixin config must be registered by the single artifact")
    require('"package": "dev.gustavopere.volcanoes.mixin"' in mixins,
            "imported Volcanoes mixin package must remain stable")
    require('import org.spongepowered.asm.mixin.Pseudo;' in rns_mixin and '@Pseudo' in rns_mixin,
            "the optional RNS mixin must be @Pseudo so core-only servers never require the RNS target class")

    require((ROOT / "src/main/resources/assets/volcanoes").is_dir(),
            "volcanoes asset namespace must be retained")
    require((ROOT / "src/main/resources/data/volcanoes").is_dir(),
            "volcanoes data namespace must be retained")
    require((ROOT / "licenses/Volcanoes-BSD-2-Clause.txt").is_file(),
            "Volcanoes BSD license evidence must ship in the unified repository")

    for dependency in (
        "minecolonies-245506:8765939",
        "cold-sweat-506194:8302211",
        "create-rns-1370563:8729955",
        "sable-1312371:8673825",
        "sable-companion-common-1.21.1:1.6.0",
        "create-1.21.1:6.0.10-280:slim",
        "ponder-neoforge:1.0.82+mc1.21.1",
        "curios-neoforge:9.5.1+1.21.1:api",
    ):
        require(dependency in build, f"missing imported optional build contract: {dependency}")

    require("download_modrinth_version 44pLdPGg '1.3.2+mc1.21.1'" in create_sable_acceptance,
            "Create/Sable acceptance must pin current Create Aeronautics 1.3.2 (Modrinth 44pLdPGg)")
    require("Create Aeronautics 1.3.2 (aeronautics_bundled)" in create_sable_acceptance,
            "Create/Sable acceptance log contract must assert Create Aeronautics 1.3.2")
    require("minecolonies-245506/8765939" in minecolonies_acceptance,
            "MineColonies acceptance must download current file 8765939")
    require("minecolonies-1.1.1375-1.21.1-snapshot.jar" in minecolonies_acceptance,
            "MineColonies acceptance must expose the current 1.1.1375 runtime filename")

    require("modrinth_version 44pLdPGg '1.3.2+mc1.21.1'" in full_pack_installer,
            "Full-pack acceptance must pin current Create Aeronautics 1.3.2 (Modrinth 44pLdPGg)")
    require("minecolonies-245506/8765939" in full_pack_installer,
            "Full-pack acceptance must download current MineColonies file 8765939")
    require("minecolonies-1.1.1375-1.21.1-snapshot.jar" in full_pack_installer,
            "Full-pack acceptance must expose current MineColonies 1.1.1375 runtime filename")

    for obsolete in (
        "Vzp221Un",
        "minecolonies-245506/8621898",
        "minecolonies-1.1.1374-1.21.1-snapshot.jar",
    ):
        require(obsolete not in create_sable_acceptance + minecolonies_acceptance + full_pack_installer,
                f"obsolete Volcanoes acceptance provider pin remains: {obsolete}")

    require(not (ROOT / "plans" / "volcanoes").exists(),
            "completed Volcanoes plans must not remain in active plans/")
    require((ROOT / "docs/archive/volcanoes").is_dir(),
            "completed Volcanoes plans must be archived under docs/archive/volcanoes/")

    print("VOLCANOES_CONSOLIDATION_CONTRACT status=GREEN mod=rpgskilltree namespace=volcanoes jars=1 providers=current plans=archived")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except AssertionError as exc:
        print(f"VOLCANOES_CONSOLIDATION_CONTRACT status=RED reason={exc}", file=sys.stderr)
        raise SystemExit(1)
