#!/usr/bin/env python3
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[1]
LEGACY = ROOT / "scripts" / "verify-runtime-scaffold.py"
MOD_MAIN = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"
EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java"
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java"
OWNER_SYNC_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionOwnerSyncRuntime.java"
ATTACHMENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java"
CORE_SERIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CoreProgressionAttachmentSerializer.java"
CORE_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
CANONICAL_SERIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentSerializer.java"
CANONICAL_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentRuntime.java"
PLAYER_PLACED_ORES = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/mining/PlayerPlacedOreData.java"
NETWORKING = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java"
CORE_PAYLOAD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/CoreProgressionSyncPayload.java"
CORE_CLIENT = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientCoreProgressionState.java"
CORE_RULES_CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CoreProgressionRulesCatalog.java"
CORE_RULES_RELOADER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CoreProgressionRulesReloader.java"

STALE_ERRORS = {
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'ModNetworking.syncToOwner'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'AttributeNodeEffectRuntime.refresh'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/CombatProgressionEvents.java: missing 'GameplayXpPolicy.combatKill'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/CombatProgressionEvents.java: missing 'PlayerProgressionRuntime.applyXp'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/MiningProgressionEvents.java: missing 'GameplayXpPolicy.oreMined'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/MiningProgressionEvents.java: missing 'PlayerProgressionRuntime.applyXp'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java: missing 'player.getData(ModAttachments.PROGRESSION)'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java: missing 'player.setData(ModAttachments.PROGRESSION, state)'",
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
owner_sync_text = read_required(OWNER_SYNC_RUNTIME)

# Login/respawn delegate to one reconciliation boundary. Confirmed compatibility
# mutations use the canonical commit boundary before effects; owner sync is coalesced
# from the published mutation event and flushed at server tick end.
require(events_text, "PlayerProgressionRuntime.reconcilePlayerState(player)", str(EVENTS.relative_to(ROOT)))
require(runtime_text, "public static ProgressionState reconcilePlayerState(ServerPlayer player)", str(RUNTIME.relative_to(ROOT)))
require(runtime_text, "set(player, reconciled)", str(RUNTIME.relative_to(ROOT)))
require(runtime_text, "NodeEffectRuntime.refresh(player, state)", str(RUNTIME.relative_to(ROOT)))
require(runtime_text, "CanonicalPlayerAttachmentRuntime.readOrMigrate(player)", str(RUNTIME.relative_to(ROOT)))
require(runtime_text, "CanonicalPlayerAttachmentRuntime.commitMutation(", str(RUNTIME.relative_to(ROOT)))
forbid(runtime_text, "setData(ModAttachments.PROGRESSION", str(RUNTIME.relative_to(ROOT)))
require(owner_sync_text, "ProgressionMutationEvents.subscribe", str(OWNER_SYNC_RUNTIME.relative_to(ROOT)))
require(owner_sync_text, "ModNetworking.syncToOwner", str(OWNER_SYNC_RUNTIME.relative_to(ROOT)))

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
        print("ERROR: legacy validator failed for an unrecognized canonical persistence contract state")
        raise SystemExit(1)

# The old progression attachments remain registered only as migration inputs so old
# worlds can deserialize. CANONICAL_PLAYER is the sole normal persistence destination.
attachments_text = read_required(ATTACHMENTS)
serializer_text = read_required(CORE_SERIALIZER)
core_runtime_text = read_required(CORE_RUNTIME)
canonical_serializer_text = read_required(CANONICAL_SERIALIZER)
canonical_runtime_text = read_required(CANONICAL_RUNTIME)
core_runtime_compact = " ".join(core_runtime_text.split())

require(attachments_text, "PROGRESSION", str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, "CORE_PROGRESSION", str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, "CANONICAL_PLAYER", str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, '"canonical_player"', str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, "CanonicalPlayerAttachmentData::empty", str(ATTACHMENTS.relative_to(ROOT)))
require(attachments_text, "CanonicalPlayerAttachmentSerializer.INSTANCE", str(ATTACHMENTS.relative_to(ROOT)))
require(serializer_text, "CoreProgressionAttachmentDataCodec.decode", str(CORE_SERIALIZER.relative_to(ROOT)))
require(serializer_text, "CoreProgressionAttachmentDataCodec.encode", str(CORE_SERIALIZER.relative_to(ROOT)))
require(canonical_serializer_text, "CanonicalPlayerAttachmentDataCodec.decode", str(CANONICAL_SERIALIZER.relative_to(ROOT)))
require(canonical_serializer_text, "CanonicalPlayerAttachmentDataCodec.encode", str(CANONICAL_SERIALIZER.relative_to(ROOT)))

require(canonical_runtime_text, "public static CanonicalPlayerAttachmentData readOrMigrate(", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "public static CanonicalPlayerAttachmentData observe(", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "static void write(", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "static boolean commitMutation(", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "ModAttachments.CANONICAL_PLAYER", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "ModAttachments.PROGRESSION", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "ModAttachments.CORE_PROGRESSION", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "CanonicalPlayerAttachmentData.fromMigrationInputs", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "player.removeData(ModAttachments.PROGRESSION)", str(CANONICAL_RUNTIME.relative_to(ROOT)))
require(canonical_runtime_text, "player.removeData(ModAttachments.CORE_PROGRESSION)", str(CANONICAL_RUNTIME.relative_to(ROOT)))
forbid(canonical_runtime_text, "setData(ModAttachments.PROGRESSION", str(CANONICAL_RUNTIME.relative_to(ROOT)))
forbid(canonical_runtime_text, "setData(ModAttachments.CORE_PROGRESSION", str(CANONICAL_RUNTIME.relative_to(ROOT)))

require(core_runtime_text, "public static CoreProgressionState bootstrap(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CanonicalPlayerAttachmentRuntime.readOrMigrate(player)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "current.initializeCore(rules)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CanonicalPlayerAttachmentRuntime.observe(player)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionBootstrap.resume(state, rules)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CanonicalPlayerAttachmentRuntime.commitMutation(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionAttachmentData.initialized(state)", str(CORE_RUNTIME.relative_to(ROOT)))
forbid(core_runtime_text, "setData(ModAttachments.PROGRESSION", str(CORE_RUNTIME.relative_to(ROOT)))
forbid(core_runtime_text, "setData(ModAttachments.CORE_PROGRESSION", str(CORE_RUNTIME.relative_to(ROOT)))

# Clientbound Core sync remains explicit and uses the same authoritative rules as the
# canonical Core section. Persistence consolidation does not conflate packet schemas.
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

# Mutations persist accepted final state through the canonical envelope. Owner packet
# emission is coalesced in ProgressionOwnerSyncRuntime rather than performed in setters.
require(core_runtime_text, "public static CoreProgressionState grantXp(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionMutationService.grantXp", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "public static CoreProgressionState applyCorePointTransaction(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionMutationService.applyCorePointTransaction", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "public static void set(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "CoreProgressionAttachmentData.initialized(state)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "set(player, next, rules)", str(CORE_RUNTIME.relative_to(ROOT)))
require(owner_sync_text, "ModNetworking.syncCoreToOwner", str(OWNER_SYNC_RUNTIME.relative_to(ROOT)))

# Semantic gameplay XP stays opt-in and receives an explicit rules snapshot.
require(core_runtime_text, "public static SemanticProgressionResult applySemanticAction(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "SemanticProgressionService.apply(", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "if (result.state() != current)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "set(player, result.state(), rules)", str(CORE_RUNTIME.relative_to(ROOT)))
require(core_runtime_text, "return result;", str(CORE_RUNTIME.relative_to(ROOT)))

# Mining anti-farm uses the same persisted provenance that placement/break lifecycle maintains.
player_placed_ores_text = read_required(PLAYER_PLACED_ORES)
require(player_placed_ores_text, "public AntiFarmService antiFarmService()", str(PLAYER_PLACED_ORES.relative_to(ROOT)))
require(player_placed_ores_text, "new BlockProvenanceAntiFarmService(provenance)", str(PLAYER_PLACED_ORES.relative_to(ROOT)))
require(player_placed_ores_text, "public boolean consume(BlockPos pos)", str(PLAYER_PLACED_ORES.relative_to(ROOT)))
require(player_placed_ores_text, "provenance.consume(pos.asLong())", str(PLAYER_PLACED_ORES.relative_to(ROOT)))

# The uncapped Core ruleset has its own datapack boundary. The old progression/defaults.json
# remains a compatibility asset and must never become the infinite rules snapshot.
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

# Level-derived Core Points are part of the authoritative rules snapshot.
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

print("Runtime scaffold validation: PASS (canonical persistence + coalesced owner sync + Core mutations/semantic XP/rules reload verified)")