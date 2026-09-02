#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github" / "workflows" / "sonarqube.yml"
GAME_TEST_COVERAGE_INIT = ROOT / "gradle" / "sonar-gametest-coverage.init.gradle"
LEGACY_BASELINE_SCRIPT = ROOT / "scripts" / "refresh-sonar-new-code-baseline.py"

BATTLE_MAGE_TEST_PATTERNS = (
    "src/main/java/dev/gustavopere/rpgskilltree/gametest/BattleMageProviderGameTests.java",
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/battlemage/gametest/**/*",
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/battlemage/BattleMageReloadAndAuthorityGameTests.java",
)


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    workflow = WORKFLOW.read_text(encoding="utf-8")
    game_test_coverage = GAME_TEST_COVERAGE_INIT.read_text(encoding="utf-8")

    require(
        "refresh-sonar-new-code-baseline.py" not in workflow,
        "Sonar CI must not mutate the main New Code baseline from normal analyses.",
    )
    require(
        not LEGACY_BASELINE_SCRIPT.exists(),
        "Legacy set_baseline helper must be removed so Previous version remains authoritative.",
    )
    require(
        "concurrency:" in workflow,
        "Sonar CI must declare concurrency so main analyses cannot overtake one another.",
    )
    require(
        "cancel-in-progress: ${{ github.ref != 'refs/heads/main' }}" in workflow,
        "Sonar CI must queue main analyses instead of cancelling an in-flight main verification.",
    )
    require(
        "gradle/actions/setup-gradle@" in workflow and "cache-provider: basic" in workflow,
        "Sonar CI must use Gradle Actions with the explicit open-source basic cache provider.",
    )
    require(
        "classdumpdir=" in game_test_coverage,
        "NeoForge GameTest coverage must dump the transformed runtime classes used by JaCoCo execution data.",
    )
    require(
        "provider-free/provider-free.xml" in workflow,
        "Sonar CI must import the provider-free transformed-class GameTest report.",
    )
    require(
        "battle-mage-provider/battle-mage-provider.xml" in workflow,
        "Sonar CI must import the provider-present Battle Mage transformed-class GameTest report.",
    )
    require(
        "JacocoReport" in game_test_coverage and "classDirectories" in game_test_coverage,
        "GameTest coverage must render dedicated JaCoCo XML reports from runtime class dumps.",
    )
    require(
        "-Dsonar.tests=src/test/java,src/main/java" in workflow,
        "Sonar must be allowed to classify runtime-discovered NeoForge GameTests as test code.",
    )
    for pattern in BATTLE_MAGE_TEST_PATTERNS:
        require(
            pattern in workflow,
            f"Battle Mage GameTest scope is missing from Sonar classification: {pattern}",
        )
    require(
        "-Dsonar.test.inclusions=" in workflow and "-Dsonar.exclusions=" in workflow,
        "Battle Mage GameTests must be test-scoped and excluded only from main-code scope.",
    )
    require(
        "sonar.coverage.exclusions" not in workflow and "sonar.cpd.exclusions" not in workflow,
        "Sonar CI must not game the Quality Gate with coverage or duplication exclusions.",
    )

    print("Sonar workflow policy is race-safe, uses basic Gradle caching, imports transformed GameTest coverage, and test-scopes NeoForge GameTests.")


if __name__ == "__main__":
    main()
