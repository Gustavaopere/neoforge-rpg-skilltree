#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumEntityCatalog.java"
EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumEntityCatalogEvents.java"
ENTRYPOINT = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    require(CATALOG.is_file(), f"missing runtime Compendium entity catalog: {CATALOG}")
    catalog = CATALOG.read_text(encoding="utf-8")
    require("CompendiumCatalog" in catalog, "runtime entity catalog must publish through CompendiumCatalog")
    require("CompendiumCatalogBuilder" in catalog, "runtime entity catalog must build a validated snapshot")
    require("RuntimeEntityCatalogCollector.collectEntries()" in catalog, "runtime entity catalog must use registry collector")
    require("BuiltInRegistries.ENTITY_TYPE" in catalog, "runtime entity catalog must compare against ENTITY_TYPE")
    require("EntityCatalogCoverage.compare" in catalog, "runtime entity catalog must validate registry coverage")
    require("coverage.complete()" in catalog, "runtime entity catalog must reject missing entity pages")
    require("coverage.unexpectedCatalogIds().isEmpty()" in catalog, "runtime entity catalog must reject unexpected entity pages")

    require(EVENTS.is_file(), f"missing Compendium entity catalog startup event: {EVENTS}")
    events = EVENTS.read_text(encoding="utf-8")
    require("ServerStartedEvent" in events, "entity catalog must publish after registries are finalized")
    require("RuntimeCompendiumEntityCatalog.publishFromRegistries()" in events, "startup event must publish entity catalog")

    require(ENTRYPOINT.is_file(), f"missing mod entrypoint: {ENTRYPOINT}")
    entrypoint = ENTRYPOINT.read_text(encoding="utf-8")
    require("CompendiumEntityCatalogEvents.class" in entrypoint, "mod entrypoint must register entity catalog events")

    combined = catalog + events
    for forbidden in ("ServerTickEvent", "LevelTickEvent", "PlayerTickEvent"):
        require(forbidden not in combined, f"entity catalog must not scan on ticks: {forbidden}")

    print("Compendium entity catalog publication validation: PASS")


if __name__ == "__main__":
    main()
