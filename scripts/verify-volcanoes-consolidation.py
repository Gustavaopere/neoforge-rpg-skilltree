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
    build = text("build.gradle")

    stray_mods = []
    for source in volcanoes_root.rglob("*.java"):
        if "@Mod(" in source.read_text(encoding="utf-8"):
            stray_mods.append(source.relative_to(ROOT).as_posix())
    require(not stray_mods,
            "Volcanoes must not declare any independent @Mod entry point: " + ", ".join(stray_mods))

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

    require((ROOT / "src/main/resources/assets/volcanoes").is_dir(),
            "volcanoes asset namespace must be retained")
    require((ROOT / "src/main/resources/data/volcanoes").is_dir(),
            "volcanoes data namespace must be retained")
    require((ROOT / "licenses/Volcanoes-BSD-2-Clause.txt").is_file(),
            "Volcanoes BSD license evidence must ship in the unified repository")

    for dependency in (
        "minecolonies-245506:8621898",
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
