#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/effects/AttributeNodeEffectRuntime.java"
DIAGNOSTICS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/effects/AttributeEffectDiagnostics.java"
RELOADER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/NodeEffectsReloader.java"


def require(path: Path, needle: str) -> None:
    if not path.is_file():
        print(f"ERROR: missing file: {path.relative_to(ROOT)}")
        raise SystemExit(1)
    text = path.read_text(encoding="utf-8")
    if needle not in text:
        print(f"ERROR: {path.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)


def forbid(path: Path, needle: str) -> None:
    if not path.is_file():
        print(f"ERROR: missing file: {path.relative_to(ROOT)}")
        raise SystemExit(1)
    text = path.read_text(encoding="utf-8")
    if needle in text:
        print(f"ERROR: {path.relative_to(ROOT)}: forbidden silent skip {needle!r}")
        raise SystemExit(1)

for needle in [
    "public final class AttributeEffectDiagnostics",
    "MISSING_REGISTRY_TARGET",
    "MISSING_PLAYER_ATTRIBUTE",
    "public static boolean report(",
    "public static List<Entry> snapshot()",
    "public static void clear()",
    "putIfAbsent",
]:
    require(DIAGNOSTICS, needle)

for needle in [
    "AttributeEffectDiagnostics.report(",
    "MISSING_REGISTRY_TARGET",
    "MISSING_PLAYER_ATTRIBUTE",
    "RuntimeDiagnostics.warn(",
    "Category.EFFECTS",
    '"attribute_effect_unavailable"',
]:
    require(RUNTIME, needle)

for needle in [
    "if (holder == null) continue;",
    "if (instance == null) continue;",
]:
    forbid(RUNTIME, needle)

require(RELOADER, "AttributeEffectDiagnostics.clear()")
print("Node effect diagnostics validation: PASS")
