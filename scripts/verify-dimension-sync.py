#!/usr/bin/env python3
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
errors = []

def read(path: str) -> str:
    p = ROOT / path
    if not p.is_file():
        errors.append(f"missing file: {path}")
        return ""
    return p.read_text(encoding="utf-8")

def require_contains(path: str, *needles: str):
    text = read(path)
    for needle in needles:
        if needle not in text:
            errors.append(f"{path}: missing {needle!r}")
    return text


events_path = 'src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java'
require_contains(
    events_path,
    'PlayerEvent.PlayerChangedDimensionEvent',
    'PlayerProgressionRuntime.syncToOwner(player)',
    'CorePlayerProgressionRuntime.syncToOwnerIfInitialized(player)'
)

legacy_path = 'src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java'
require_contains(
    legacy_path,
    'public static void syncToOwner(ServerPlayer player)',
    'CanonicalPlayerAttachmentRuntime.observe(player)',
    'ModNetworking.syncToOwner(player, observed.compatibilityProgression())'
)

core_path = 'src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java'
core = require_contains(
    core_path,
    'public static boolean syncToOwnerIfInitialized(ServerPlayer player)',
    'CanonicalPlayerAttachmentRuntime.observe(player)',
    'CoreProgressionRulesCatalog.current()',
    'ModNetworking.syncCoreToOwner(player, state, rules)'
)

marker = 'public static boolean syncToOwnerIfInitialized(ServerPlayer player)'
if marker in core:
    start = core.index(marker)
    next_method = core.find('\n    public static ', start + len(marker))
    body = core[start:] if next_method < 0 else core[start:next_method]
    for forbidden in (
        'CanonicalPlayerAttachmentRuntime.readOrMigrate',
        'CanonicalPlayerAttachmentRuntime.write',
        'set(player,'
    ):
        if forbidden in body:
            errors.append(f"{core_path}: dimension sync must be observational, found {forbidden!r}")

if errors:
    for error in errors:
        print(f'ERROR: {error}')
    sys.exit(1)
print('Dimension sync validation: PASS')
