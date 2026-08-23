#!/usr/bin/env python3
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[1]
LEGACY = ROOT / "scripts" / "verify-runtime-scaffold.py"
EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java"
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java"

STALE_ERRORS = {
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'ModNetworking.syncToOwner'",
    "ERROR: src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java: missing 'AttributeNodeEffectRuntime.refresh'",
}


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
        raise SystemExit(1)


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

if result.returncode == 0:
    print("Runtime scaffold validation: PASS")
    raise SystemExit(0)

if unexpected:
    print("\n".join(unexpected))
    raise SystemExit(result.returncode)

seen_stale = {line for line in lines if line in STALE_ERRORS}
if seen_stale != STALE_ERRORS:
    print("ERROR: legacy validator failed for an unrecognized reconciliation contract state")
    raise SystemExit(1)

print("Runtime scaffold validation: PASS (reconciliation boundary verified)")
