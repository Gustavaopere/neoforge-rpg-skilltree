#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / "src" / "main" / "java"
BOOTSTRAP = JAVA / "dev" / "gustavopere" / "rpgskilltree" / "RpgSkillTreeMod.java"
CENTRAL = JAVA / "dev" / "gustavopere" / "rpgskilltree" / "runtime" / "compat" / "OptionalIntegrations.java"
IDENTITY_MIXIN = JAVA / "dev" / "gustavopere" / "rpgskilltree" / "runtime" / "compat" / "identity2" / "mixin" / "IdentityProgressionMixin.java"
IDENTITY_PLUGIN = JAVA / "dev" / "gustavopere" / "rpgskilltree" / "bootstrap" / "Identity2MixinPlugin.java"
MIXIN_CONFIG = ROOT / "src" / "main" / "resources" / "rpgskilltree.mixins.json"
METADATA = ROOT / "src" / "main" / "resources" / "META-INF" / "neoforge.mods.toml"
WORKFLOW = ROOT / ".github" / "workflows" / "alpha2-build.yml"
SMOKE_VERIFIER = ROOT / "scripts" / "verify-optional-provider-smoke.py"

PROVIDERS = {
    "IRONS_SPELLBOOKS": ("irons_spellbooks", "runtime/compat/irons/", "io.redspace.ironsspellbooks"),
    "ARS_NOUVEAU": ("ars_nouveau", "runtime/compat/ars/", "com.hollingsworth.arsnouveau"),
    "EPIC_FIGHT": ("epicfight", "runtime/compat/epicfight/", "yesman.epicfight"),
    "GOETY": ("goety", "runtime/compat/goety/", "com.Polarice3.Goety"),
    "MALUM": ("malum", "runtime/compat/malum/", "com.sammy.malum"),
    "EIDOLON": ("eidolon", "runtime/compat/eidolon/", "alexthw.eidolon_repraised"),
    "IDENTITY2": ("identity2", "runtime/compat/identity2/", "net.Gabou.identity2"),
}

# Cross-provider adapters remain narrow and explicit. Battle Mage is a MineColonies-owned
# integration whose spellbook seam necessarily links Iron's after both provider gates pass.
CROSS_PROVIDER_ALLOWED = {
    "io.redspace.ironsspellbooks": ("runtime/compat/minecolonies/battlemage/",),
}


def fail(message: str) -> None:
    raise SystemExit(f"Optional integration contract: {message}")


if not CENTRAL.is_file():
    fail("missing central runtime/compat/OptionalIntegrations.java registry")

central = CENTRAL.read_text(encoding="utf-8")
for enum_name, (mod_id, _, _) in PROVIDERS.items():
    if enum_name not in central or f'"{mod_id}"' not in central:
        fail(f"central registry does not classify {enum_name}/{mod_id}")
for marker in ("boolean isLoaded", "String version", "String summary"):
    if marker not in central:
        fail(f"central registry is missing API marker {marker!r}")
if "ModList.get()" not in central:
    fail("central registry must own NeoForge ModList access")

bootstrap = BOOTSTRAP.read_text(encoding="utf-8")
if "import net.neoforged.fml.ModList;" in bootstrap or "ModList.get()" in bootstrap:
    fail("RpgSkillTreeMod must not query ModList directly after centralization")
if "OptionalIntegrations" not in bootstrap:
    fail("RpgSkillTreeMod must route optional-provider detection through OptionalIntegrations")
for enum_name in PROVIDERS:
    if f"OptionalIntegrations.Provider.{enum_name}" not in bootstrap:
        fail(f"bootstrap does not use central provider identity {enum_name}")
for marker in (
    "RuntimeDiagnostics.info(",
    "Category.COMPAT",
    '"optional_providers"',
    '"Optional integrations: {}"',
    "OptionalIntegrations.summary()",
):
    if marker not in bootstrap:
        fail(f"bootstrap optional-provider diagnostic is missing semantic marker {marker!r}")
summary_position = bootstrap.find('"Optional integrations: {}"')
summary_value_position = bootstrap.find("OptionalIntegrations.summary()", summary_position)
first_optional_guard = bootstrap.find("OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)")
if summary_position < 0 or summary_value_position < summary_position:
    fail("bootstrap must emit one bounded optional-integration summary for server-smoke evidence")
if first_optional_guard < 0 or first_optional_guard <= summary_value_position:
    fail("optional-provider summary must be emitted before optional adapter registration")

metadata = METADATA.read_text(encoding="utf-8")
metadata_optional = set()
for block in metadata.split("[[dependencies.${mod_id}]]")[1:]:
    block = block.split("[[", 1)[0]
    mod_match = re.search(r'(?m)^modId\s*=\s*"([^"]+)"\s*$', block)
    type_match = re.search(r'(?m)^type\s*=\s*"([^"]+)"\s*$', block)
    if mod_match and type_match and type_match.group(1) == "optional":
        metadata_optional.add(mod_match.group(1))
expected_optional = {mod_id for mod_id, _, _ in PROVIDERS.values()}
if metadata_optional != expected_optional:
    fail(f"metadata optional providers differ from registry: {sorted(metadata_optional)} != {sorted(expected_optional)}")

for path in JAVA.rglob("*.java"):
    rel = path.relative_to(JAVA).as_posix()
    text = path.read_text(encoding="utf-8")
    for _, (_, allowed_suffix, external_package) in PROVIDERS.items():
        if external_package not in text:
            continue
        cross_provider_allowed = CROSS_PROVIDER_ALLOWED.get(external_package, ())
        if allowed_suffix not in rel and not any(suffix in rel for suffix in cross_provider_allowed):
            # A standalone early Mixin plugin may carry the provider target as an inert string,
            # but it must never import or otherwise link the provider package.
            if path == IDENTITY_PLUGIN and f'"{external_package}' in text and f"import {external_package}" not in text:
                continue
            fail(f"external provider type {external_package} leaked outside isolated adapter path: {rel}")

if not IDENTITY_MIXIN.is_file():
    fail("Identity2 mixin must live inside the isolated identity2 adapter tree")
if not IDENTITY_PLUGIN.is_file():
    fail("missing early-startup Identity2 mixin gate")
plugin = IDENTITY_PLUGIN.read_text(encoding="utf-8")
for marker in ("implements IMixinConfigPlugin", "shouldApplyMixin", "IDENTITY_TARGET_RESOURCE", "getResource"):
    if marker not in plugin:
        fail(f"Identity2 mixin gate is missing marker {marker!r}")
for forbidden in (
    "import net.minecraft.",
    "import net.Gabou.identity2",
    "import net.neoforged.fml.ModList",
    "ModList.get(",
):
    if forbidden in plugin:
        fail(f"Identity2 mixin gate must remain early-startup safe; forbidden marker {forbidden!r}")
config = MIXIN_CONFIG.read_text(encoding="utf-8")
if '"plugin": "dev.gustavopere.rpgskilltree.bootstrap.Identity2MixinPlugin"' not in config:
    fail("mixin config must install the early Identity2 target gate")

if not SMOKE_VERIFIER.is_file():
    fail("missing dedicated-server optional-provider absence verifier")
smoke = SMOKE_VERIFIER.read_text(encoding="utf-8")
for marker in ("ClassNotFoundException", "NoClassDefFoundError"):
    if marker not in smoke:
        fail(f"server-smoke verifier must reject {marker}")
workflow = WORKFLOW.read_text(encoding="utf-8")
if 'python3 scripts/verify-optional-provider-smoke.py "$LOG"' not in workflow:
    fail("dedicated-server smoke must assert the optional-provider absence matrix")

print("Optional integration contract: PASS")
