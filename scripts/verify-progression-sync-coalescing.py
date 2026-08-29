from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]

def read(path: str) -> str:
    target = ROOT / path
    if not target.exists():
        raise SystemExit(f"missing required progression sync coalescing file: {path}")
    return target.read_text(encoding="utf-8")

runtime = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionOwnerSyncRuntime.java")
events = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/events/ProgressionOwnerSyncEvents.java")
mod = read("src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java")
compat = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java")
core = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java")

required_runtime = [
    "ProgressionSyncCoalescer",
    "ProgressionMutationEvent",
    "initialize()",
    "mark(ProgressionMutationEvent event)",
    "flush(MinecraftServer server)",
    "clear(UUID playerId)",
    "ModNetworking.syncToOwner",
    "ModNetworking.syncCoreToOwner",
]
for token in required_runtime:
    if token not in runtime:
        raise SystemExit(f"ProgressionOwnerSyncRuntime missing contract token: {token}")

if "ProgressionDirtyReason.PERSISTENT_STATE" not in runtime:
    raise SystemExit("confirmed mutations must at least mark PERSISTENT_STATE dirty")
if "ProgressionMutationEvents.subscribe" not in runtime:
    raise SystemExit("owner sync runtime must subscribe to canonical mutation events")
if "drainAll()" not in runtime:
    raise SystemExit("owner sync runtime must drain coalesced players once per flush")

required_events = [
    "ServerTickEvent.Post",
    "PlayerEvent.PlayerLoggedOutEvent",
    "ServerStoppedEvent",
    "ProgressionOwnerSyncRuntime.flush(event.getServer())",
    "ProgressionOwnerSyncRuntime.clear(player.getUUID())",
    "ProgressionOwnerSyncRuntime.clearAll()",
]
for token in required_events:
    if token not in events:
        raise SystemExit(f"ProgressionOwnerSyncEvents missing contract token: {token}")

if "ProgressionOwnerSyncRuntime.initialize();" not in mod:
    raise SystemExit("mod bootstrap must initialize the mutation-event sync subscription")
if "NeoForge.EVENT_BUS.register(ProgressionOwnerSyncEvents.class);" not in mod:
    raise SystemExit("mod bootstrap must register progression owner sync tick/lifecycle events")

# Confirmed mutation setters must not directly owner-sync anymore. Rejection/lifecycle
# syncs elsewhere remain intentionally immediate.
compat_set = re.search(r"private static boolean set\(ServerPlayer player, ProgressionState state\) \{(?P<body>.*?)\n    \}\n\}", compat, re.S)
if not compat_set:
    raise SystemExit("could not locate PlayerProgressionRuntime.set")
if "ModNetworking.syncToOwner" in compat_set.group("body"):
    raise SystemExit("compatibility mutation setter must queue owner sync instead of sending immediately")

core_set = re.search(r"public static void set\(\s*ServerPlayer player,\s*CoreProgressionState state,\s*ProgressionRulesSnapshot rules\s*\) \{(?P<body>.*?)\n    \}\n\}", core, re.S)
if not core_set:
    raise SystemExit("could not locate CorePlayerProgressionRuntime.set")
if "ModNetworking.syncCoreToOwner" in core_set.group("body"):
    raise SystemExit("Core mutation setter must queue owner sync instead of sending immediately")

print("Progression sync coalescing runtime contract: PASS")
