#!/usr/bin/env python3
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[1]
LEGACY = ROOT / "scripts" / "verify-runtime-scaffold.py"
MOD_MAIN = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"
EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java"
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java"
ATTACHMENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java"
CORE_SERIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CoreProgressionAttachmentSerializer.java"
CORE_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
PLAYER_PLACED_ORES = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/mining/PlayerPlacedOreData.java"
NETWORKING = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java"
CORE_PAYLOAD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/CoreProgressionSyncPayload.java"
CORE_CLIENT = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientCoreProgressionState.java"
CORE_RULES_CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CoreProgressionRulesCatalog.java"
CORE_RULES_RELOADER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CoreProgressionRulesReloader.java"

STALE_ERRORS = {
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'ModNetworking.syncToOwner'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'AttributeNodeEffectRuntime.refresh'",
    "ERROR: missing file: src/main/resources/data/rpgskilltree/tags/entity_types/bosses.json",
}


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
        raise SystemExit(1)


def forbid(text: str, needle: str, location: str) -> None:
    if needle in text:
        print(f"ERROR: {location}: forbidden {needle!r}")
        raise SystemExit(1)


def read_required(path: Path) -> str:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required Core runtime file is missing")
        raise SystemExit(1)
    return path.read_text(encoding="utf-8")


events_text = EVENTS.read_text(encoding="utf-8")
runtime_text = RUNTIME.read_text(encoding="utf-8")

# Login/respawn now delegate to one reconciliation boundary. That boundary persists the
# reconciled state and then refreshes effects + syncs the owner through set().
require(events_text, "PlayerProgressionRuntime.reconcilePlayerState(player)", str(EVENTS.relative_to(ROOT)))
require(runtime_text, "public static ProgressionState reconcilePlayerState(ServerPlayer player)", str(RUNTIME.relative_to(ROOT)))
require(runtime_text, "set(player, reconciled)", str(RUNTIME.relative_to(ROOT)))
require(runtime_text, "AttributeNodeEffectRuntime.refresh(player, state)", str(RUNTIME.relative_to(ROOT)))
require(runtime_text, "ModNetworking.syncToOwner(player, state)", str(RUNTIME.relative_to(ROOT)))

result = subprocess.run(
    [sys.executable, str(LEGACY)],
    cwd=ROOT,
    text=True,
    capture_output=True,
)

lines = [line for line in (result.stdout + result.stderr).splitlines() if line.strip()]
unexpected = [line for line in lines if line not in STALE_ERRORS]

if result.returncode != 0:
    if unexpected:
        print("\n".join(unexpected))
        raise SystemExit(result.returncode)
    seen_stale = {line for line in lines if line in STALE_ERRORS}
    if seen_stale != STALE_ERRORS:
        print("ERROR: legacy validator failed for an unrecognized reconciliation contract state")
        raise SystemExit(1)

# Core progression is introduced beside the legacy attachment until Axxxx runtime
# mutations are migrated. It must never materialize the legacy default merely to
# decide whether a player has a legacy save.
attachments_text = read_required(ATTACHMENTS)
serializer_text = read_required(CORE_SERIALIZER)
core_runtime_text = read_required(CORE_RUNTIME)
core_runtime_compact = " ".join(core_runtime_text.split())

require(attachments_text, "CORE_PROGRESSION", str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, '"core_progression"', str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, "CoreProgressionAttachmentData::uninitialized", str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, "CoreProgressionAttachmentSerializer.INSTANCE", str(ATTACHMENTS.relative_to(ROOT)))
require(serializer_text, "CoreProgressionAttachmentDataCodec.decode", str(CORE_SERIALIZER.relative_to(ROOT)))
require(serializer_text, "CoreProgressionAttachmentDataCodec.encode", str(CORE_SERIALIZER.relative_to(ROOT)))
require(core_runtime_text, "public static CoreProgressionState bootstrap(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "player.hasData(ModAttachments.CORE_PROGRESSION)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionBootstrap.resume", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "player.hasData(ModAttachments.PROGRESSION)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionBootstrap.migrateDecodedLegacy", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionBootstrap.newPlayer", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_compact, "player.setData( ModAttachments.CORE_PROGRESSION,", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionAttachmentData.initialized(initialized)", str(CORE_RUNTIME.relative_to(ROOT)))

# Clientbound Core sync is deliberately explicit: no login hook is allowed to invent
# a rules snapshot. Callers must supply the same authoritative rules used by bootstrap.
networking_text = read_required(NETWORKING)
core_payload_text = read_required(CORE_PAYLOAD)
core_client_text = read_required(CORE_CLIENT)
networking_compact = " ".join(networking_text.split())

require(core_payload_text, "implements CustomPacketPayload", str(CORE_PAYLOAD.relative_to(ROOT)))
require(core_payload_text, '"core_progression_sync"', str(CORE_PAYLOAD.relative_to(ROOT)))
require(core_payload_text, "CoreProgressionSyncStateCodec.encode", str(CORE_PAYLOAD.relative_to(ROOT)))
require(core_client_text, "CoreProgressionSyncStateCodec.decode(payload.snapshot())", str(CORE_CLIENT.relative_to(ROOT)))
require(core_client_text, "CURRENT.set(decoded)", str(CORE_CLIENT.relative_to(ROOT)))
require(networking_text, "CoreProgressionSyncPayload.TYPE", str(NETWORKING.relative_to(ROOT)))
require(networking_text, "CoreProgressionSyncPayload.STREAM_CODEC", str(NETWORKING.relative_to(ROOT)))
require(networking_text, "ClientCoreProgressionState::handleSync", str(NETWORKING.relative_to(ROOT)))
require(networking_text, "public static void syncCoreToOwner(", str(NETWORKING.relative_to(ROOT)))
require(networking_compact, "PacketDistributor.sendToPlayer( player, CoreProgressionSyncPayload.fromState(state, rules)", str(NETWORKING.relative_to(ROOT)))

# Mutations remain opt-in until a rules provider is authoritative. Once invoked, the
# runtime must persist and sync only the accepted final state, never an intermediate
# bootstrap/migration snapshot.
require(core_runtime_text, "public static CoreProgressionState grantXp(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionMutationService.grantXp", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "public static CoreProgressionState applyCorePointTransaction(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionMutationService.applyCorePointTransaction", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "public static void set(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_compact, "CoreProgressionAttachmentData.initialized(state)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "ModNetworking.syncCoreToOwner(player, state, rules)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "set(player, next, rules)", str(CORE_RUNTIME.relative_to(ROOT)))

# Semantic gameplay XP stays opt-in and receives an explicit rules snapshot. The
# runtime delegates evaluation/mutation to the pure service and persists/syncs only
# when that service returns a distinct Core state.
require(core_runtime_text, "public static SemanticProgressionResult applySemanticAction(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "SemanticProgressionService.apply(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "if (result.state() != current)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "set(player, result.state(), rules)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "return result;", str(CORE_RUNTIME.relative_to(ROOT)))

# Mining anti-farm must use the same persisted provenance that placement/break lifecycle
# already maintains. Evaluation is a non-consuming view; consume() remains a lifecycle
# operation after the block is actually broken.
player_placed_ores_text = read_required(PLAYER_PLACED_ORES)
require(player_placed_ores_text, "public AntiFarmService antiFarmService()", str(PLAYER_PLACED_ORES.relative_to(ROOT)))
require(player_placed_ores_text, "new BlockProvenanceAntiFarmService(provenance)", str(PLAYER_PLACED_ORES.relative_to(ROOT)))
require(player_placed_ores_text, "public boolean consume(BlockPos pos)", str(PLAYER_PLACED_ORES.relative_to(ROOT)))
require(player_placed_ores_text, "provenance.consume(pos.asLong())", str(PLAYER_PLACED_ORES.relative_to(ROOT)))

# The uncapped Core ruleset has its own datapack boundary. The old progression/defaults.json
# remains an Alpha-2 compatibility asset and must never be treated as the authoritative
# infinite rules snapshot. Missing Core rules disable the provider; ambiguity fails closed.
mod_main_text = read_required(MOD_MAIN)
core_rules_catalog_text = read_required(CORE_RULES_CATALOG)
core_rules_reloader_text = read_required(CORE_RULES_RELOADER)

require(core_rules_catalog_text, "InstallableProgressionRulesProvider", str(CORE_RULES_CATALOG.relative_to(ROOT)))
require(core_rules_catalog_text, "public static ProgressionRulesProvider provider()", str(CORE_RULES_CATALOG.relative_to(ROOT)))
require(core_rules_catalog_text, "public static void install(ProgressionRulesSnapshot rules)", str(CORE_RULES_CATALOG.relative_to(ROOT)))
require(core_rules_catalog_text, "PROVIDER.install(rules)", str(CORE_RULES_CATALOG.relative_to(ROOT)))
require(core_rules_catalog_text, "public static void clear()", str(CORE_RULES_CATALOG.relative_to(ROOT)))
require(core_rules_catalog_text, "PROVIDER.clear()", str(CORE_RULES_CATALOG.relative_to(ROOT)))
forbid(core_rules_catalog_text, "progression/defaults.json", str(CORE_RULES_CATALOG.relative_to(ROOT)))

require(core_rules_reloader_text, 'super(GSON, "core_progression_rules")', str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "if (resources.isEmpty())", str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "CoreProgressionRulesCatalog.clear()", str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "resources.size() != 1", str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "ROOT_FIELDS", str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "BAND_FIELDS", str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "longValueExact()", str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "new ProgressionRulesSnapshot(", str(CORE_RULES_RELOADER.relative_to(ROOT)))
require(core_rules_reloader_text, "CoreProgressionRulesCatalog.install(snapshot)", str(CORE_RULES_RELOADER.relative_to(ROOT)))
forbid(core_rules_reloader_text, "progression/defaults.json", str(CORE_RULES_RELOADER.relative_to(ROOT)))

# Level-derived Core Points are part of the authoritative rules snapshot. The
# datapack must configure the policy explicitly; the compatibility-disabled
# constructor must never become the live runtime economy by omission.
for needle in [
    '"level_core_points"',
    "POINT_POLICY_FIELDS",
    "requiredObject(root, \"level_core_points\", resourceId)",
    '"periodic"',
    '"first_award_level"',
    '"levels_per_award"',
    '"points_per_award"',
    "PeriodicLevelCorePointAwardPolicy",
    "levelPointPolicy",
]:
    require(core_rules_reloader_text, needle, str(CORE_RULES_RELOADER.relative_to(ROOT)))

require(mod_main_text, "CoreProgressionRulesReloader", str(MOD_MAIN.relative_to(ROOT)))
require(mod_main_text, "NeoForge.EVENT_BUS.register(CoreProgressionRulesReloader.class)", str(MOD_MAIN.relative_to(ROOT)))

print("Runtime scaffold validation: PASS (legacy runtime + parallel Core persistence/sync/mutations/semantic XP/mining anti-farm/rules reload verified)")
