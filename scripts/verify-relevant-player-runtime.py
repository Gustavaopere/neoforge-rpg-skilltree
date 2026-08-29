#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def text(path: str) -> str:
    target = ROOT / path
    if not target.is_file():
        raise SystemExit(f"missing required relevant-player file: {path}")
    return target.read_text(encoding="utf-8")


def require(source: str, token: str, where: str) -> None:
    if token not in source:
        raise SystemExit(f"{where}: missing required contract token {token!r}")


def forbid(source: str, token: str, where: str) -> None:
    if token in source:
        raise SystemExit(f"{where}: forbidden contract token {token!r}")


policy_path = "src/main/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerSearchPolicy.java"
index_path = "src/main/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerSpatialIndex.java"
query_path = "src/main/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerSpatialQuery.java"
merger_path = "src/main/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerCandidateMerger.java"
runtime_path = "src/main/java/dev/gustavopere/rpgskilltree/runtime/RelevantPlayerCandidateRuntime.java"
events_path = "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/RelevantPlayerCacheEvents.java"
mod_path = "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"
level_test_path = "src/test/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerLevelFoundationTest.java"
index_test_path = "src/test/java/dev/gustavopere/rpgskilltree/core/RelevantPlayerSpatialIndexTest.java"

policy = text(policy_path)
index = text(index_path)
query = text(query_path)
merger = text(merger_path)
runtime = text(runtime_path)
events = text(events_path)
mod = text(mod_path)
level_test = text(level_test_path)
index_test = text(index_test_path)

# Pure search policy owns locality/output/TTL and rejects unbounded cell work.
for token in (
    "candidateRadiusBlocks",
    "engagementRadiusBlocks",
    "maxCandidates",
    "cacheTtlTicks",
    "MAX_OUTPUT_CANDIDATES",
    "MAX_QUERY_CELLS",
    "worstCaseVisitedCells",
):
    require(policy, token, policy_path)

# Spatial index is provider-neutral, immutable, deterministic and auditable.
require(index, "public final class RelevantPlayerSpatialIndex", index_path)
require(index, "Map.copyOf(buckets)", index_path)
require(index, "query(", index_path)
require(index, "visitedCells", index_path)
require(index, "scannedPlayers", index_path)
require(index, "selected.subList(policy.maxCandidates(), selected.size()).clear()", index_path)
require(index, "distanceSquaredSaturated", index_path)
forbid(index, "net.minecraft", index_path)
require(query, "candidates = List.copyOf(candidates)", query_path)

# Optional party evidence is validated independently and cannot displace local spatial evidence first.
require(merger, "party adapter candidate must set partyMember=true", merger_path)
require(merger, "conflicting relevant-player level", merger_path)
require(merger, "sourcePriority", merger_path)
require(merger, "Math.min(existingEntry.sourcePriority(), sourcePriority)", merger_path)

# Runtime builds a bounded player snapshot and never scans an unbounded players() list.
require(runtime, "level.getPlayers(", runtime_path)
require(runtime, "probeLimit", runtime_path)
require(runtime, "limits.maxIndexedPlayers()", runtime_path)
forbid(runtime, "level.players()", runtime_path)
require(runtime, "players.size() > limits.maxIndexedPlayers()", runtime_path)
require(runtime, "RelevantPlayerSpatialIndex.build(List.of(), searchPolicy.cellSizeBlocks())", runtime_path)
require(runtime, "CorePlayerProgressionRuntime.queryProgression(player).level()", runtime_path)
require(runtime, "LinkedHashMap<CacheKey, CacheEntry>", runtime_path)
require(runtime, "searchPolicy.cacheTtlTicks()", runtime_path)
require(runtime, "trimCache(limits.maxCacheEntries())", runtime_path)
require(runtime, "RelevantPlayerCandidateMerger.merge", runtime_path)
require(runtime, "PartyCandidateSource", runtime_path)
require(runtime, "party.size() > searchPolicy.maxCandidates()", runtime_path)
require(runtime, "party candidate source exceeded maxCandidates", runtime_path)

# Cost accounting and invalidation are executable contracts, not documentation only.
for token in (
    "cacheHits",
    "cacheMisses",
    "playersSampled",
    "saturatedBuilds",
    "spatialQueries",
    "spatialPlayersScanned",
    "spatialCellsVisited",
):
    require(runtime, token, runtime_path)
require(runtime, "ProgressionMutationEvents.subscribe", runtime_path)
require(runtime, "ProgressionMutationEvent.Section.CORE", runtime_path)
require(runtime, "invalidateAll();", runtime_path)

for event_name in (
    "onPlayerLoggedIn",
    "onPlayerRespawn",
    "onPlayerChangedDimension",
    "onPlayerLoggedOut",
    "onServerStopped",
):
    require(events, event_name, events_path)
require(events, "RelevantPlayerCandidateRuntime.invalidateAll();", events_path)
require(mod, "RelevantPlayerCandidateRuntime.initialize();", mod_path)
require(mod, "NeoForge.EVENT_BUS.register(RelevantPlayerCacheEvents.class);", mod_path)

# Core behavioral tests cover multiplayer/filter semantics, party merging and bounded spatial work.
require(level_test, "partyMergePreservesSpatialPriorityAndFailsClosed();", level_test_path)
for token in (
    "localCandidatesAreDeterministicAndDistanceOrdered();",
    "farPlayersDoNotLeakIntoEncounter();",
    "outputAndCellWorkAreBounded();",
    "extremeCoordinatesFailSafeInsteadOfOverflowing();",
):
    require(index_test, token, index_test_path)

print("Relevant player runtime validation: PASS")
