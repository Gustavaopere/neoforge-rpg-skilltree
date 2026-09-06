#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import re
import sys

EXPECTED = (
    ("epicfight", "21.17.3.1"),
    ("epic_colonies", "21.0.8"),
    ("efiscompat", "3.1.0"),
    ("minecolonies", "1.1.1375-1.21.1-snapshot"),
    ("irons_spellbooks", "1.21.1-3.16.3"),
)
# Optional compatibility mods may emit non-fatal mixin linkage warnings for absent third-party
# targets. Provider viability is enforced by successful server startup, exact-version evidence,
# Battle Mage activation, and the required GameTest pass boundary below.
FORBIDDEN = (
    "Unsupported installed optional dependencies",
    "ModLoadingException",
    "Loading errors encountered:",
    "Failed to start the minecraft server",
    "Crash report saved to",
    "Error during pre-loading phase",
)
ACTIVE_MESSAGE = (
    "MineColonies Battle Mage integration active: "
    "MineColonies 1.1.1375-1.21.1-snapshot, Iron's 1.21.1-3.16.3"
)


def fail(message: str) -> None:
    raise SystemExit(f"Battle Mage Epic runtime: {message}")


if len(sys.argv) != 2:
    fail("usage: verify-battle-mage-epic-runtime.py <gametest-log>")

path = Path(sys.argv[1])
if not path.is_file():
    fail(f"GameTest log does not exist: {path}")
text = path.read_text(encoding="utf-8", errors="replace")

for marker in FORBIDDEN:
    if marker in text:
        fail(f"Epic compatibility runtime emitted fatal startup marker: {marker}")

for mod_id, version in EXPECTED:
    pattern = re.compile(rf"(?m)^.*\b{re.escape(version)}\b.*\({re.escape(mod_id)}\).*$")
    if pattern.search(text) is None:
        fail(f"exact pack component was not loaded: {mod_id} {version}")

if ACTIVE_MESSAGE not in text:
    fail("Battle Mage did not remain ACTIVE with the Epic compatibility stack loaded")
if "Started game test server" not in text:
    fail("Minecraft never reached the GameTest server startup boundary")

passed = re.search(r"All\s+(\d+)\s+required tests passed", text)
if passed is None or int(passed.group(1)) <= 0:
    fail("GameTests did not complete successfully with the Epic stack")

print("Battle Mage Epic runtime: PASS")
print("Epic Fight 21.17.3.1 + Epic Colonies 21.0.8 + EFIS 3.1.0")
print("Battle Mage remained provider-native and all required GameTests completed")
