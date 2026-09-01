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
    properties = text("gradle.properties")

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

    require('minecolonies_file_id=8765939' in properties,
            "canonical MineColonies file id must remain pinned to 8765939")
    require('curse.maven:minecolonies-245506:${minecolonies_file_id}' in build,
            "Volcanoes and RPG integrations must share the canonical MineColonies build property")
    require('minecolonies-245506:8621898' not in build,
            "obsolete MineColonies 1.1.1374 artifact must not remain on the unified classpath")

    for dependency in (
        "cold-sweat-506194:8302211",
        "create-rns-1370563:8729955",
        "sable-1312371:8673825",
        "sable-companion-common-1.21.1:1.6.0",
        "create-1.21.1:6.0.10-280:slim",
        "ponder-neoforge:1.0.82+mc1.21.1",
        "curios-neoforge:9.5.1+1.21.1:api",
    ):
        require(dependency in build, f"missing imported optional build contract: {dependency}")

    print("VOLCANOES_CONSOLIDATION_CONTRACT status=GREEN mod=rpgskilltree namespace=volcanoes jars=1")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except AssertionError as exc:
        print(f"VOLCANOES_CONSOLIDATION_CONTRACT status=RED reason={exc}", file=sys.stderr)
        raise SystemExit(1)
