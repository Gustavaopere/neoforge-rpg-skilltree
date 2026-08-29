#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]


def fail(message: str) -> None:
    raise SystemExit(f"Foundation bootstrap contract: {message}")


def properties(path: Path) -> dict[str, str]:
    result: dict[str, str] = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        if "=" not in line:
            fail(f"malformed property line in {path.relative_to(ROOT)}: {raw!r}")
        key, value = line.split("=", 1)
        result[key.strip()] = value.strip()
    return result


props = properties(ROOT / "gradle.properties")
expected_properties = {
    "minecraft_version": "1.21.1",
    "minecraft_version_range": "[1.21.1,1.21.2)",
    "neo_version": "21.1.248",
    "loader_version_range": "[1,)",
    "neogradle.subsystems.parchment.minecraftVersion": "1.21.1",
    "neogradle.subsystems.parchment.mappingsVersion": "2024.11.17",
    "mod_id": "rpgskilltree",
}
for key, expected in expected_properties.items():
    actual = props.get(key)
    if actual != expected:
        fail(f"{key} must be {expected!r}, found {actual!r}")

build = (ROOT / "build.gradle").read_text(encoding="utf-8")
for snippet in (
    "id 'net.neoforged.gradle.userdev' version '7.1.26'",
    "JavaLanguageVersion.of(21)",
    'implementation "net.neoforged:neoforge:${neo_version}"',
    'compileOnly "io.redspace:irons_spellbooks:${irons_spells_version}:api"',
    "compileOnly(\"maven.modrinth:TKB6INcv:${ars_nouveau_version_id}\")",
    "compileOnly(\"maven.modrinth:vu3NZ5Ma:${epicfight_version_id}\")",
    "compileOnly(\"curse.maven:goety-586095:${goety_file_id}\")",
    "compileOnly(\"curse.maven:malum-484064:${malum_file_id}\")",
    "compileOnly(\"curse.maven:eidolon-repraised-870250:${eidolon_file_id}\")",
    "compileOnly(\"curse.maven:identity2-1238155:${identity2_file_id}\")",
):
    if snippet not in build:
        fail(f"build.gradle is missing canonical declaration: {snippet}")

wrapper = (ROOT / "gradle" / "wrapper" / "gradle-wrapper.properties").read_text(encoding="utf-8")
if "distributionUrl=https\\://services.gradle.org/distributions/gradle-8.14-bin.zip" not in wrapper:
    fail("Gradle wrapper must remain pinned to Gradle 8.14 bin")

workflow = (ROOT / ".github" / "workflows" / "alpha2-build.yml").read_text(encoding="utf-8")
for command in (
    "./gradlew --no-daemon test",
    "./gradlew --no-daemon runGameTestServer",
    "./gradlew --no-daemon build",
    "./gradlew --no-daemon runServer",
):
    if command not in workflow:
        fail(f"CI must execute canonical command {command}")

metadata = (ROOT / "src" / "main" / "resources" / "META-INF" / "neoforge.mods.toml").read_text(encoding="utf-8")
if re.search(r"(?m)^mandatory\s*=", metadata):
    fail("neoforge.mods.toml must use explicit NeoForge dependency type=required/optional, not legacy mandatory=")

header = "[[dependencies.${mod_id}]]"
blocks = metadata.split(header)[1:]
dependencies: dict[str, dict[str, str]] = {}
for block in blocks:
    block = block.split("[[", 1)[0]
    fields = dict(re.findall(r'(?m)^([A-Za-z][A-Za-z0-9_]*)\s*=\s*"([^"]*)"\s*$', block))
    mod_id = fields.get("modId")
    if not mod_id:
        fail("dependency block without modId")
    if mod_id in dependencies:
        fail(f"duplicate dependency metadata for {mod_id}")
    dependencies[mod_id] = fields

required = {
    "neoforge": "[${neo_version},)",
    "minecraft": "${minecraft_version_range}",
}
for mod_id, version_range in required.items():
    fields = dependencies.get(mod_id)
    if fields is None:
        fail(f"missing required dependency metadata for {mod_id}")
    if fields.get("type") != "required":
        fail(f"{mod_id} must be type=required")
    if fields.get("versionRange") != version_range:
        fail(f"{mod_id} versionRange must be {version_range!r}")
    if fields.get("side") != "BOTH":
        fail(f"{mod_id} must be declared for BOTH sides")

optional = {
    "irons_spellbooks",
    "ars_nouveau",
    "epicfight",
    "goety",
    "malum",
    "eidolon",
    "identity2",
}
for mod_id in sorted(optional):
    fields = dependencies.get(mod_id)
    if fields is None:
        fail(f"missing optional provider metadata for {mod_id}")
    if fields.get("type") != "optional":
        fail(f"{mod_id} must be type=optional")
    if fields.get("versionRange") != "":
        fail(
            f"{mod_id} versionRange must remain empty until the provider compatibility matrix is formally certified"
        )
    if fields.get("ordering") != "NONE" or fields.get("side") != "BOTH":
        fail(f"{mod_id} must use ordering=NONE and side=BOTH")

unexpected = set(dependencies) - set(required) - optional
if unexpected:
    fail(f"unclassified dependency metadata: {sorted(unexpected)}")

mod_bootstrap = (
    ROOT / "src" / "main" / "java" / "dev" / "gustavopere" / "rpgskilltree" / "RpgSkillTreeMod.java"
).read_text(encoding="utf-8")
ordered_bootstrap_markers = (
    "AttributeRankCostPolicyCatalog.install(UnitAttributeRankCostPolicy.INSTANCE);",
    "ModAttachments.register(modBus);",
    "ModNetworking.register(modBus);",
    "ProgressionOwnerSyncRuntime.initialize();",
    "NeoForge.EVENT_BUS.register(ProgressionOwnerSyncEvents.class);",
    "NeoForge.EVENT_BUS.register(PlayerProgressionEvents.class);",
    "NeoForge.EVENT_BUS.register(NodeRulesReloader.class);",
    "NeoForge.EVENT_BUS.register(TreeArchitectureReloader.class);",
    "NeoForge.EVENT_BUS.register(TreeUnlockReloader.class);",
    "NeoForge.EVENT_BUS.register(ClassRulesReloader.class);",
    "NeoForge.EVENT_BUS.register(ClassChoiceRulesReloader.class);",
    "NeoForge.EVENT_BUS.register(ArchetypeReloader.class);",
    "NeoForge.EVENT_BUS.register(SpecializationReloader.class);",
    "NeoForge.EVENT_BUS.register(MorphCategoryReloader.class);",
    "NeoForge.EVENT_BUS.register(NodeEffectsReloader.class);",
    "NeoForge.EVENT_BUS.register(BossRewardReloader.class);",
    "NeoForge.EVENT_BUS.register(CoreProgressionRulesReloader.class);",
    "NeoForge.EVENT_BUS.register(CanonicalProviderBindingReloader.class);",
    "NeoForge.EVENT_BUS.register(EntityScalingEvents.class);",
    "NeoForge.EVENT_BUS.register(CompendiumEntityCatalogEvents.class);",
    "NeoForge.EVENT_BUS.register(CompendiumFloraCatalogEvents.class);",
    "NeoForge.EVENT_BUS.register(CompendiumInventoryEvents.class);",
    "NeoForge.EVENT_BUS.register(CompendiumLootResourceReloader.class);",
    "NeoForge.EVENT_BUS.register(CompendiumDiscoveryEvents.class);",
)
position = -1
for marker in ordered_bootstrap_markers:
    next_position = mod_bootstrap.find(marker)
    if next_position < 0:
        fail(f"bootstrap registration is missing: {marker}")
    if next_position <= position:
        fail(f"bootstrap registration order changed before: {marker}")
    position = next_position

first_optional_guard = mod_bootstrap.find('ModList.get().isLoaded("irons_spellbooks")')
if first_optional_guard < 0 or first_optional_guard <= position:
    fail("optional provider registration must occur after the deterministic common bootstrap")

java_sources = ROOT / "src" / "main" / "java"
registered_configs = []
for path in java_sources.rglob("*.java"):
    text = path.read_text(encoding="utf-8")
    if "registerConfig(" in text:
        registered_configs.append(path.relative_to(ROOT).as_posix())
if registered_configs:
    config_sources = [
        path for path in java_sources.rglob("*.java")
        if "ModConfigSpec" in path.read_text(encoding="utf-8")
    ]
    if not config_sources:
        fail("registerConfig is used without a ModConfigSpec-backed configuration")

print("Foundation bootstrap contract: PASS")
print("Configuration contract:", "registered" if registered_configs else "no user configuration currently registered")
