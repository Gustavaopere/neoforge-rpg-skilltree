#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def text(path: str) -> str:
    target = ROOT / path
    if not target.is_file():
        raise SystemExit(f"missing required relevant-player runtime file: {path}")
    return target.read_text(encoding="utf-8")


def require(source: str, token: str, where: str) -> None:
    if token not in source:
        raise SystemExit(f"{where}: missing required contract token {token!r}")


def forbid(source: str, token: str, where: str) -> None:
    if token in source:
        raise SystemExit(f"{where}: forbidden contract token {token!r}")


runtime_path = "src/main/java/dev/gustavopere/rpgskilltree/runtime/RelevantPlayerCandidateRuntime.java"
selector_path = "src/main/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerSpatialSelector.java"
mod_path = "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"
player_events_path = "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java"
sync_events_path = "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/ProgressionOwnerSyncEvents.java"
test_path = "src/test/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerLevelFoundationTest.java"

runtime = text(runtime_path)
selector = text(selector_path)
mod = text(mod_path)
player_events = text(player_events_path)
sync_events = text(sync_events_path)
test = text(test_path)

# Pure selection must recompute exact encounter distance and keep deterministic bounded ordering.
require(selector, "public static List<RelevantPlayerCandidate> select(", selector_path)
require(selector, "distanceSquared(", selector_path)
require(selector, "selected.sort(CANONICAL_ORDER)", selector_path)
require(selector, "selected.subList(maxCandidates, selected.size()).clear()", selector_path)
require(selector, "public static List<RelevantPlayerCandidate> mergeParty(", selector_path)
require(selector, "party adapter candidate must set partyMember=true", selector_path)
require(selector, "conflicting relevant-player level", selector_path)

# Runtime scan must be bounded, cached and derived from the authoritative Core query.
require(runtime, "level.getPlayers(", runtime_path)
require(runtime, "probeLimit", runtime_path)
require(runtime, "config.maxPlayersScanned()", runtime_path)
forbid(runtime, "level.players()", runtime_path)
require(runtime, "CorePlayerProgressionRuntime.queryProgression(player).level()", runtime_path)
require(runtime, "LinkedHashMap<CacheKey, CacheEntry>", runtime_path)
require(runtime, "cacheTtlTicks", runtime_path)
require(runtime, "maxCacheEntries", runtime_path)
require(runtime, "trimCache(config.maxCacheEntries())", runtime_path)
require(runtime, "saturatedScans", runtime_path)
require(runtime, "players.size() > config.maxPlayersScanned()", runtime_path)
require(runtime, "List.of(), players.size(), true", runtime_path)

# Cache must follow confirmed progression mutations and expose an optional party seam only.
require(runtime, "ProgressionMutationEvents.subscribe", runtime_path)
require(runtime, "ProgressionMutationEvent.Section.CORE", runtime_path)
require(runtime, "installPartyCandidateSource", runtime_path)
require(runtime, "PartyCandidateSource", runtime_path)
require(runtime, "RelevantPlayerSpatialSelector.mergeParty", runtime_path)

# Runtime is initialized from the mod bootstrap and lifecycle topology changes invalidate samples.
require(mod, "RelevantPlayerCandidateRuntime.initialize();", mod_path)
for event_name in ("onPlayerLoggedIn", "onPlayerRespawn", "onPlayerChangedDimension"):
    require(player_events, event_name, player_events_path)
require(player_events, "RelevantPlayerCandidateRuntime.invalidateAll();", player_events_path)
require(sync_events, "onPlayerLoggedOut", sync_events_path)
require(sync_events, "onServerStopped", sync_events_path)
require(sync_events, "RelevantPlayerCandidateRuntime.invalidateAll();", sync_events_path)

# Behavioral coverage must include deterministic spatial capping and party merge fail-closed paths.
require(test, "spatialSelectionIsDeterministicAndBounded();", test_path)
require(test, "partyMergePreservesLocalPriorityAndFailsClosed();", test_path)

print("Relevant player runtime validation: PASS")
