#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import sys

EXPECTED = (
    "irons_spellbooks",
    "ars_nouveau",
    "epicfight",
    "goety",
    "malum",
    "eidolon",
    "identity2",
)
PREFIX = "Optional integrations: "


def fail(message: str) -> None:
    raise SystemExit(f"Optional provider smoke: {message}")


if len(sys.argv) != 2:
    fail("usage: verify-optional-provider-smoke.py <server-log>")

log_path = Path(sys.argv[1])
if not log_path.is_file():
    fail(f"server log does not exist: {log_path}")

matches: list[str] = []
for line in log_path.read_text(encoding="utf-8", errors="replace").splitlines():
    if PREFIX in line:
        matches.append(line.split(PREFIX, 1)[1].strip())

if len(matches) != 1:
    fail(f"expected exactly one bounded optional-integration summary, found {len(matches)}")

entries: dict[str, str] = {}
for token in matches[0].split(","):
    if "=" not in token:
        fail(f"malformed summary token: {token!r}")
    mod_id, state = token.split("=", 1)
    mod_id = mod_id.strip()
    state = state.strip()
    if not mod_id or not state:
        fail(f"malformed summary token: {token!r}")
    if mod_id in entries:
        fail(f"duplicate provider in summary: {mod_id}")
    entries[mod_id] = state

if tuple(entries) != EXPECTED:
    fail(f"provider order/set differs from canonical matrix: {tuple(entries)!r}")

not_absent = {mod_id: state for mod_id, state in entries.items() if state != "absent"}
if not_absent:
    fail(
        "core-only dedicated server unexpectedly loaded optional providers: "
        + ", ".join(f"{mod_id}={state}" for mod_id, state in not_absent.items())
    )

print("Optional provider smoke: PASS")
print("Absence matrix:", ", ".join(f"{mod_id}=absent" for mod_id in EXPECTED))
