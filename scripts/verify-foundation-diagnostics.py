#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / "src" / "main" / "java" / "dev" / "gustavopere" / "rpgskilltree"
DIAGNOSTICS = JAVA / "runtime" / "diagnostics" / "RuntimeDiagnostics.java"
RELOAD_DIAGNOSTICS = JAVA / "runtime" / "diagnostics" / "ReloadDiagnostics.java"
TESTING_DOC = ROOT / "docs" / "TESTING.md"
DIAGNOSTICS_DOC = ROOT / "docs" / "DIAGNOSTICS.md"
CI = ROOT / ".github" / "workflows" / "alpha2-build.yml"
CORE_TESTS = ROOT / "scripts" / "test-core.sh"
GAMETEST = JAVA / "gametest" / "FoundationGameTests.java"
NODE_RULES = JAVA / "runtime" / "data" / "NodeRulesReloader.java"
CLASS_RULES = JAVA / "runtime" / "data" / "ClassRulesReloader.java"
ATTRIBUTE_DIAGNOSTICS = JAVA / "runtime" / "effects" / "AttributeEffectDiagnostics.java"


def fail(message: str) -> None:
    raise SystemExit(f"Foundation diagnostics contract: {message}")


def text(path: Path) -> str:
    if not path.is_file():
        fail(f"missing {path.relative_to(ROOT)}")
    return path.read_text(encoding="utf-8")


def require(source: str, marker: str, where: str) -> None:
    if marker not in source:
        fail(f"{where} is missing {marker!r}")


diag = text(DIAGNOSTICS)
for marker in (
    "enum Category",
    "BOOTSTRAP",
    "COMPAT",
    "PROGRESSION",
    "EFFECTS",
    "COMPENDIUM",
    "DATA",
    "String prefix(",
    "void info(",
    "void warn(",
    "void error(",
    '"[rpgskilltree/"',
):
    require(diag, marker, "RuntimeDiagnostics")

reload_diag = text(RELOAD_DIAGNOSTICS)
for marker in (
    "public final class ReloadDiagnostics",
    "Category.DATA",
    '"reload_failed"',
    "resources.keySet()",
    ".sorted()",
    "throw failure;",
):
    require(reload_diag, marker, "ReloadDiagnostics")

for label, path, data_path in (
    ("NodeRulesReloader", NODE_RULES, "node_rules"),
    ("ClassRulesReloader", CLASS_RULES, "classes"),
):
    source = text(path)
    require(source, "ReloadDiagnostics.run(", label)
    require(source, f'"{data_path}"', label)

# Persistent effect failures are emitted once per unique condition instead of once per refresh/tick.
require(text(ATTRIBUTE_DIAGNOSTICS), "putIfAbsent", "AttributeEffectDiagnostics")

operational_files = {
    "RpgSkillTreeMod.java": JAVA / "RpgSkillTreeMod.java",
    "ProgressionMutationEvents.java": JAVA / "runtime" / "ProgressionMutationEvents.java",
    "AttributeNodeEffectRuntime.java": JAVA / "runtime" / "effects" / "AttributeNodeEffectRuntime.java",
    "CompendiumInventoryEvents.java": JAVA / "runtime" / "compendium" / "CompendiumInventoryEvents.java",
    "CompendiumEntityCatalogEvents.java": JAVA / "runtime" / "compendium" / "CompendiumEntityCatalogEvents.java",
    "CompendiumFloraCatalogEvents.java": JAVA / "runtime" / "compendium" / "CompendiumFloraCatalogEvents.java",
    "CompendiumWorldCatalogEvents.java": JAVA / "runtime" / "compendium" / "CompendiumWorldCatalogEvents.java",
}
for label, path in operational_files.items():
    source = text(path)
    require(source, "RuntimeDiagnostics.", label)

bootstrap = text(operational_files["RpgSkillTreeMod.java"])
for marker in (
    "Category.COMPAT",
    '"optional_providers"',
    '"epicfight_version_unsupported"',
):
    require(bootstrap, marker, "RpgSkillTreeMod")

progression = text(operational_files["ProgressionMutationEvents.java"])
require(progression, '"mutation_listener_failed"', "ProgressionMutationEvents")
effects = text(operational_files["AttributeNodeEffectRuntime.java"])
require(effects, '"attribute_effect_unavailable"', "AttributeNodeEffectRuntime")

for label in (
    "CompendiumInventoryEvents.java",
    "CompendiumEntityCatalogEvents.java",
    "CompendiumFloraCatalogEvents.java",
    "CompendiumWorldCatalogEvents.java",
):
    require(text(operational_files[label]), "Category.COMPENDIUM", label)

testing = text(TESTING_DOC)
for stale in (
    "Gradle Wrapper está ausente",
    "JUnit não está integrado",
    "NeoForge GameTests estão ausentes",
    "JUnit remains a future",
    "GameTests are absent",
):
    if stale in testing:
        fail(f"docs/TESTING.md still contains stale baseline text: {stale!r}")
for command in (
    "bash scripts/test-core.sh",
    "./gradlew --no-daemon test",
    "./gradlew --no-daemon runGameTestServer",
    "./gradlew --no-daemon build",
    "./gradlew --no-daemon runServer",
    "python3 scripts/verify-foundation-bootstrap.py",
    "python3 scripts/verify-optional-integrations.py",
    "python3 scripts/verify-foundation-diagnostics.py",
):
    require(testing, command, "docs/TESTING.md")

operations = text(DIAGNOSTICS_DOC)
for marker in (
    "[rpgskilltree/<category>/<event>]",
    "INFO",
    "WARN",
    "ERROR",
    "bootstrap",
    "compat",
    "progression",
    "effects",
    "compendium",
    "data",
    "[rpgskilltree/data/reload_failed]",
    "putIfAbsent",
):
    require(operations, marker, "docs/DIAGNOSTICS.md")

ci = text(CI)
for marker in (
    "bash scripts/test-core.sh",
    "./gradlew --no-daemon test",
    "./gradlew --no-daemon runGameTestServer",
    "python3 scripts/verify-foundation-diagnostics.py",
    "./gradlew --no-daemon build",
    "./gradlew --no-daemon runServer",
    'python3 scripts/verify-optional-provider-smoke.py "$LOG"',
):
    require(ci, marker, "alpha2-build.yml")

core = text(CORE_TESTS)
for marker in ("javac --release 21", "verify-node-effect-diagnostics.py"):
    require(core, marker, "scripts/test-core.sh")

gametest = text(GAMETEST)
for marker in ("@GameTestHolder", "@GameTest", "dedicatedServerGameTestBoots"):
    require(gametest, marker, "FoundationGameTests.java")

print("Foundation diagnostics contract: PASS")
