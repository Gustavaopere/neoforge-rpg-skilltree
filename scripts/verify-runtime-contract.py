#!/usr/bin/env python3
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[1]
LEGACY = ROOT / "scripts" / "verify-runtime-scaffold.py"
EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java"
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java"
ATTACHMENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java"
CORE_SERIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CoreProgressionAttachmentSerializer.java"
CORE_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
NETWORKING = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java"
CORE_PAYLOAD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/CoreProgressionSyncPayload.java"
CORE_CLIENT = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientCoreProgressionState.java"

STALE_ERRORS = {
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'ModNetworking.syncToOwner'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'AttributeNodeEffectRuntime.refresh'",
}


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
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

print("Runtime scaffold validation: PASS (legacy runtime + parallel Core persistence/sync/mutations/semantic XP verified)")
