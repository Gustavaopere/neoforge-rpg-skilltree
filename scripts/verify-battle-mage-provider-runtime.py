#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import re
import sys

EXPECTED_MOD_LINES = (
    "Iron's Spells 'n Spellbooks 1.21.1-3.16.3 (irons_spellbooks)",
    "MineColonies 1.1.1375-1.21.1-snapshot (minecolonies)",
    "Just Enough Items 19.51.0.417 (jei)",
)
ACTIVE_MESSAGE = (
    "MineColonies Battle Mage integration active: "
    "MineColonies 1.1.1375-1.21.1-snapshot, Iron's 1.21.1-3.16.3"
)
FORBIDDEN = (
    "Unsupported installed optional dependencies",
    "ModLoadingException",
    "Loading errors encountered:",
    "Failed to start the minecraft server",
    "Crash report saved to",
    "Error during pre-loading phase",
)


def fail(message: str) -> None:
    raise SystemExit(f"Battle Mage provider runtime: {message}")


if len(sys.argv) != 2:
    fail("usage: verify-battle-mage-provider-runtime.py <gametest-log>")

log_path = Path(sys.argv[1])
if not log_path.is_file():
    fail(f"GameTest log does not exist: {log_path}")

text = log_path.read_text(encoding="utf-8", errors="replace")

for marker in FORBIDDEN:
    if marker in text:
        fail(f"provider-present GameTest emitted fatal startup marker: {marker}")

for expected in EXPECTED_MOD_LINES:
    if expected not in text:
        fail(f"expected exact provider runtime entry was not loaded: {expected}")

if ACTIVE_MESSAGE not in text:
    fail("Battle Mage integration never reached ACTIVE with the certified provider versions")

if "Started game test server" not in text:
    fail("Minecraft never reached the GameTest server startup boundary")

passed = re.search(r"All\s+(\d+)\s+required tests passed", text)
if passed is None:
    fail("GameTests did not report a completed all-required-tests-passed result")

count = int(passed.group(1))
if count <= 0:
    fail("GameTest completion reported a non-positive required test count")

for linkage_error in ("ClassNotFoundException", "NoClassDefFoundError", "IncompatibleClassChangeError"):
    if linkage_error in text:
        fail(f"provider-present runtime emitted {linkage_error}")

print("Battle Mage provider runtime: PASS")
print("Certified providers: MineColonies 1.1.1375-1.21.1-snapshot + Iron's 1.21.1-3.16.3")
print("JEI runtime: 19.51.0.417")
print(f"GameTests completed: {count}")
