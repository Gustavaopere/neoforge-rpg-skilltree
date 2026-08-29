#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]


def read(path: str) -> str:
    target = ROOT / path
    if not target.is_file():
        raise SystemExit(f"missing persistence/sync contract file: {path}")
    return target.read_text(encoding="utf-8")


def require(text: str, token: str, label: str) -> None:
    if token not in text:
        raise SystemExit(f"{label}: missing contract token {token!r}")


def forbid(text: str, token: str, label: str) -> None:
    if token in text:
        raise SystemExit(f"{label}: forbidden authoritative client payload token {token!r}")


attachments = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java")
serializer = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentSerializer.java")
codec = read("src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalPlayerAttachmentDataCodec.java")
migrations = read("src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalPlayerAttachmentMigrations.java")
canonical_runtime = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentRuntime.java")
player_events = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java")
sync_runtime = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionOwnerSyncRuntime.java")
sync_events = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/events/ProgressionOwnerSyncEvents.java")
mod_main = read("src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java")
networking = read("src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java")

# The canonical attachment is the normal persisted destination and must survive death.
for token in [
    "CANONICAL_PLAYER",
    '"canonical_player"',
    "CanonicalPlayerAttachmentData::empty",
    "CanonicalPlayerAttachmentSerializer.INSTANCE",
    ".copyOnDeath()",
]:
    require(attachments, token, "ModAttachments")
require(serializer, "CanonicalPlayerAttachmentDataCodec.decode", "CanonicalPlayerAttachmentSerializer")
require(serializer, "CanonicalPlayerAttachmentDataCodec.encode", "CanonicalPlayerAttachmentSerializer")

# Persistence has an explicit schema version and a migration boundary that fails closed.
match = re.search(r"CURRENT_VERSION\s*=\s*(\d+)", codec)
if not match or int(match.group(1)) < 1:
    raise SystemExit("CanonicalPlayerAttachmentDataCodec: missing positive CURRENT_VERSION")
for token in [
    "CanonicalPlayerAttachmentMigrations.toCurrent",
    "canonical player attachment contains trailing bytes",
    'throw new IllegalArgumentException("truncated " + label + " section")',
    "invalid canonical player attachment payload",
]:
    require(codec, token, "CanonicalPlayerAttachmentDataCodec")
for token in [
    "CanonicalPlayerAttachmentMigrationChain",
    "CanonicalPlayerAttachmentDataCodec.CURRENT_VERSION",
    "toCurrent(byte[] encoded)",
    "CHAIN.migrateToCurrent(encoded)",
]:
    require(migrations, token, "CanonicalPlayerAttachmentMigrations")

# Old attachments may be read for migration, but normal writes converge on CANONICAL_PLAYER.
for token in [
    "CanonicalPlayerAttachmentData.fromMigrationInputs",
    "player.setData(ModAttachments.CANONICAL_PLAYER",
    "player.removeData(ModAttachments.PROGRESSION)",
    "player.removeData(ModAttachments.CORE_PROGRESSION)",
    "commitMutation",
    "ProgressionMutationEvents.publish",
]:
    require(canonical_runtime, token, "CanonicalPlayerAttachmentRuntime")
forbid(canonical_runtime, "setData(ModAttachments.PROGRESSION", "CanonicalPlayerAttachmentRuntime")
forbid(canonical_runtime, "setData(ModAttachments.CORE_PROGRESSION", "CanonicalPlayerAttachmentRuntime")

# Login/respawn reconcile authoritative state; dimension changes re-send the owner snapshot.
for token in [
    "PlayerEvent.PlayerLoggedInEvent",
    "PlayerEvent.PlayerRespawnEvent",
    "PlayerEvent.PlayerChangedDimensionEvent",
    "PlayerProgressionRuntime.reconcilePlayerState(player)",
    "PlayerProgressionRuntime.syncToOwner(player)",
    "CorePlayerProgressionRuntime.syncToOwnerIfInitialized(player)",
]:
    require(player_events, token, "PlayerProgressionEvents")

# Confirmed mutations coalesce to one owner sync at server-tick end.
for token in [
    "ProgressionMutationEvents.subscribe",
    "ProgressionSyncCoalescer",
    "ModNetworking.syncToOwner",
    "ModNetworking.syncCoreToOwner",
    "flush(MinecraftServer server)",
]:
    require(sync_runtime, token, "ProgressionOwnerSyncRuntime")
for token in [
    "ServerTickEvent.Post",
    "ProgressionOwnerSyncRuntime.flush(event.getServer())",
    "PlayerEvent.PlayerLoggedOutEvent",
    "ServerStoppedEvent",
]:
    require(sync_events, token, "ProgressionOwnerSyncEvents")
require(mod_main, "ProgressionOwnerSyncRuntime.initialize();", "RpgSkillTreeMod")
require(mod_main, "NeoForge.EVENT_BUS.register(ProgressionOwnerSyncEvents.class);", "RpgSkillTreeMod")

# State snapshots only travel server -> owner. Client -> server packets are intents and are
# re-evaluated against server state by the runtime; they never carry ProgressionState snapshots.
for token in [
    "registrar.playToClient(",
    "ProgressionSyncPayload.TYPE",
    "CoreProgressionSyncPayload.TYPE",
    "PacketDistributor.sendToPlayer",
]:
    require(networking, token, "ModNetworking")

server_payloads = [
    "PurchaseNodePayload",
    "RespecNodePayload",
    "UnlockClassPayload",
    "SelectClassChoicePayload",
    "ClearClassChoicePayload",
    "PurchaseAttributeRanksPayload",
    "RefundAttributeRanksPayload",
]
for payload in server_payloads:
    require(networking, f"{payload}.TYPE", "ModNetworking")
    require(networking, f"{payload}.STREAM_CODEC", "ModNetworking")
    require(networking, f"{payload}::handle", "ModNetworking")
    source = read(f"src/main/java/dev/gustavopere/rpgskilltree/runtime/network/{payload}.java")
    require(source, "context.player() instanceof ServerPlayer", payload)
    forbid(source, "ProgressionState", payload)
    forbid(source, "CoreProgressionState", payload)

print("Persistence and owner-sync contract: PASS")
