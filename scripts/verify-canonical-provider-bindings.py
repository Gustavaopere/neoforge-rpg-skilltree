#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CanonicalProviderBindingDataCatalog.java"
RELOADER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CanonicalProviderBindingsReloader.java"
MOD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"


def require(path: Path, needle: str) -> None:
    if not path.exists():
        raise SystemExit(f"{path.relative_to(ROOT)}: required runtime file is missing")
    text = path.read_text(encoding="utf-8")
    if needle not in text:
        raise SystemExit(f"{path.relative_to(ROOT)}: missing required contract token: {needle}")


require(CATALOG, "CanonicalProviderBindingCatalog")
require(CATALOG, "public static void install")
require(CATALOG, "public static void clear")
require(CATALOG, "public static CanonicalProviderBindingCatalog current")

require(RELOADER, "SimpleJsonResourceReloadListener")
require(RELOADER, '"canonical_provider_bindings"')
require(RELOADER, '"canonical_stat"')
require(RELOADER, '"bindings"')
require(RELOADER, '"binding_id"')
require(RELOADER, '"provider_target"')
require(RELOADER, "CanonicalProviderBinding.of")
require(RELOADER, "CanonicalProviderBindingCatalog.of")
require(RELOADER, "CanonicalProviderBindingDataCatalog.install")
require(RELOADER, "CanonicalProviderBindingDataCatalog.clear")
require(RELOADER, "rejectUnknown")

require(MOD, "CanonicalProviderBindingsReloader")
require(MOD, "NeoForge.EVENT_BUS.register(CanonicalProviderBindingsReloader.class)")

print("Canonical provider binding runtime contract: PASS")
