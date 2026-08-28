#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CanonicalProviderBindingRuntimeCatalog.java"
RELOADER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/CanonicalProviderBindingReloader.java"
MOD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"


def read_required(path: Path) -> str:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required canonical-binding runtime file is missing")
        raise SystemExit(1)
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
        raise SystemExit(1)


def forbid(text: str, needle: str, location: str) -> None:
    if needle in text:
        print(f"ERROR: {location}: forbidden {needle!r}")
        raise SystemExit(1)


catalog = read_required(CATALOG)
reloader = read_required(RELOADER)
mod = read_required(MOD)
catalog_location = str(CATALOG.relative_to(ROOT))
reloader_location = str(RELOADER.relative_to(ROOT))
mod_location = str(MOD.relative_to(ROOT))

for needle in (
    "Optional<CanonicalProviderBindingCatalog>",
    "public static void install(CanonicalProviderBindingCatalog catalog)",
    "public static void clear()",
):
    require(catalog, needle, catalog_location)

for needle in (
    'super(GSON, "canonical_provider_bindings")',
    "CanonicalProviderBinding.of(",
    '"binding_id"',
    '"canonical_stat"',
    '"provider_target"',
    "CanonicalProviderBindingCatalog.of(bindings)",
    "CanonicalProviderBindingRuntimeCatalog.install(compiled)",
    "CanonicalProviderBindingRuntimeCatalog.clear()",
):
    require(reloader, needle, reloader_location)

require(mod, "CanonicalProviderBindingReloader", mod_location)
require(mod, "NeoForge.EVENT_BUS.register(CanonicalProviderBindingReloader.class)", mod_location)

# Loading compiles definitions only. Provider availability/precedence remains a later runtime policy.
for forbidden in (
    "ProviderBindingSelectionPolicy",
    "available.get(0)",
    "definitions().get(0)",
):
    forbid(reloader, forbidden, reloader_location)

compile_index = reloader.find("CanonicalProviderBindingCatalog.of(bindings)")
install_index = reloader.find("CanonicalProviderBindingRuntimeCatalog.install(compiled)")
if compile_index < 0 or install_index < 0 or compile_index > install_index:
    print(f"ERROR: {reloader_location}: catalog must compile completely before atomic install")
    raise SystemExit(1)

print("Canonical provider binding runtime validation: PASS")
