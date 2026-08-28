#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/EntityScalingEvents.java"
INITIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingInitializer.java"
CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingInitializerCatalog.java"
MOD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"


def read_required(path: Path) -> str:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required entity-scaling lifecycle file is missing")
        raise SystemExit(1)
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
        raise SystemExit(1)


def forbid(text: str, needle: str, location: str) -> None:
    if needle in text:
        print(f"ERROR: {location}: forbidden {needle!r}")
        raise SystemExit(1)


events = read_required(EVENTS)
initializer = read_required(INITIALIZER)
catalog = read_required(CATALOG)
mod = read_required(MOD)

events_location = str(EVENTS.relative_to(ROOT))
initializer_location = str(INITIALIZER.relative_to(ROOT))
catalog_location = str(CATALOG.relative_to(ROOT))
mod_location = str(MOD.relative_to(ROOT))

require(initializer, "EntityScalingState initialize(ServerLevel level, LivingEntity entity)", initializer_location)
require(catalog, "Optional<EntityScalingInitializer> current()", catalog_location)
require(catalog, "install(EntityScalingInitializer initializer)", catalog_location)
require(catalog, "clear()", catalog_location)

require(events, "@SubscribeEvent", events_location)
require(events, "EntityJoinLevelEvent", events_location)
require(events, "event.getLevel() instanceof ServerLevel", events_location)
require(events, "event.getEntity() instanceof LivingEntity", events_location)
require(events, "instanceof Player", events_location)
require(events, "EntityScalingRuntime.current", events_location)
require(events, "EntityScalingInitializerCatalog.current", events_location)
require(events, "EntityScalingRuntime.getOrInitialize", events_location)

# The join event may fire before the underlying chunk reaches FULL. Keep world threat/player scans
# outside this adapter and behind the explicitly installed initializer contract.
for forbidden in ("EntityLevelService", "MobRarityService", ".getChunk(", ".getBiome(", "StructureManager"):
    forbid(events, forbidden, events_location)

require(mod, "NeoForge.EVENT_BUS.register(EntityScalingEvents.class);", mod_location)

persisted = events.find("EntityScalingRuntime.current")
catalog_lookup = events.find("EntityScalingInitializerCatalog.current")
initialize = events.find("EntityScalingRuntime.getOrInitialize")
if persisted < 0 or catalog_lookup < 0 or initialize < 0 or not (persisted < catalog_lookup < initialize):
    print(f"ERROR: {events_location}: persisted state must be checked before initializer lookup and initialization")
    raise SystemExit(1)

print("Entity scaling event validation: PASS (server-only join boundary + persisted-state-first initialization verified)")
