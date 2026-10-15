#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import re
import sys

ARS_LINE = "Ars Nouveau 5.13.1 (ars_nouveau)"
IRONS_LINE = "Iron's Spells 'n Spellbooks 1.21.1-3.16.3 (irons_spellbooks)"
ARS_ACTIVE = "rpgskilltree:adapter/ars_nouveau=enabled"
FORBIDDEN = (
    "Unsupported installed optional dependencies",
    "ModLoadingException",
    "Loading errors encountered:",
    "Failed to start the minecraft server",
    "Crash report saved to",
    "Error during pre-loading phase",
    "ClassNotFoundException",
    "NoClassDefFoundError",
    "IncompatibleClassChangeError",
)


def fail(message: str) -> None:
    raise SystemExit(f"Ars provider runtime: {message}")


if len(sys.argv) != 3:
    fail("usage: verify-ars-provider-runtime.py <ars-only|ars-irons> <gametest-log>")

mode = sys.argv[1]
if mode not in {"ars-only", "ars-irons"}:
    fail(f"unknown mode: {mode}")

log_path = Path(sys.argv[2])
if not log_path.is_file():
    fail(f"GameTest log does not exist: {log_path}")

text = log_path.read_text(encoding="utf-8", errors="replace")

for marker in FORBIDDEN:
    if marker in text:
        fail(f"provider-present GameTest emitted fatal marker: {marker}")

if ARS_LINE not in text:
    fail(f"exact Ars provider runtime was not loaded: {ARS_LINE}")

if ARS_ACTIVE not in text:
    fail("Ars adapter never reached enabled state in the optional adapter registry")

if mode == "ars-only":
    if IRONS_LINE in text:
        fail("ars-only lane unexpectedly loaded Iron's Spellbooks")
else:
    if IRONS_LINE not in text:
        fail(f"ars-irons lane did not load exact Iron's runtime: {IRONS_LINE}")

if "Started game test server" not in text:
    fail("Minecraft never reached the GameTest server startup boundary")

passed = re.search(r"All\s+(\d+)\s+required tests passed", text)
if passed is None:
    fail("GameTests did not report a completed all-required-tests-passed result")

count = int(passed.group(1))
if count <= 0:
    fail("GameTest completion reported a non-positive required test count")

print("Ars provider runtime: PASS")
print(f"Mode: {mode}")
print("Ars Nouveau: 5.13.1")
print("Iron's Spellbooks: " + ("1.21.1-3.16.3" if mode == "ars-irons" else "absent"))
print(f"GameTests completed: {count}")
