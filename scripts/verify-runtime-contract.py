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
require(core_runtime_text, "player.setData(ModAttachments.CORE_PROGRESSION", str(CORE_RUNTIME.relative_to(ROOT)))

print("Runtime scaffold validation: PASS (legacy reconciliation + parallel Core bootstrap verified)")
