#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github" / "workflows" / "sonarqube.yml"
GAME_TEST_COVERAGE_INIT = ROOT / "gradle" / "sonar-gametest-coverage.init.gradle"
LEGACY_BASELINE_SCRIPT = ROOT / "scripts" / "refresh-sonar-new-code-baseline.py"
NEW_CODE_POLICY_HELPER = ROOT / "scripts" / "ensure_sonar_new_code_period.py"

BATTLE_MAGE_TEST_PATTERNS = (
    "src/main/java/dev/gustavopere/rpgskilltree/gametest/BattleMageProviderGameTests.java",
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/battlemage/gametest/**/*",
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/battlemage/BattleMageReloadAndAuthorityGameTests.java",
)

ECONOMY_TEST_PATTERNS = (
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyPersistenceGameTests.java",
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/economy/gametest/**/*",
)


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    workflow = WORKFLOW.read_text(encoding="utf-8")
    game_test_coverage = GAME_TEST_COVERAGE_INIT.read_text(encoding="utf-8")

    require(
        "refresh-sonar-new-code-baseline.py" not in workflow,
        "Sonar CI must not mutate the main New Code baseline to a specific analysis.",
    )
    require(
        not LEGACY_BASELINE_SCRIPT.exists(),
        "Legacy analysis-UUID baseline helper must remain removed.",
    )
    require(
        NEW_CODE_POLICY_HELPER.exists(),
        "Sonar CI must include the deterministic New Code settings helper.",
    )

    helper = NEW_CODE_POLICY_HELPER.read_text(encoding="utf-8")
    require(
        "ensure_sonar_new_code_period.py" in workflow,
        "Sonar workflow must enforce the New Code settings before analysis.",
    )
    require(
        workflow.index("ensure_sonar_new_code_period.py")
        < workflow.index("Build and analyze with SonarQube"),
        "Sonar New Code settings repair must run before analysis creation.",
    )
    require(
        'EXPECTED_VALUE = "previous_version"' in helper,
        "SonarQube Cloud New Code policy must be Previous version.",
    )
    require(
        '"sonar.leak.period": EXPECTED_VALUE' in helper
        and '"sonar.leak.period.type": EXPECTED_VALUE' in helper,
        "SonarQube Cloud policy must set both documented Previous version settings.",
    )
    require(
        "/api/settings/values" in helper and "/api/settings/set" in helper,
        "SonarQube Cloud policy helper must verify and write through the settings Web API.",
    )
    require(
        "/api/new_code_periods/" not in helper,
        "SonarQube Cloud policy helper must not depend on SonarQube Server new_code_periods endpoints.",
    )
    require(
        "/api/project_analyses" not in helper
        and "set_baseline" not in helper
        and '"analysis":' not in helper,
        "Sonar CI must never select or persist an analysis UUID as the New Code baseline.",
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
    for pattern in ECONOMY_TEST_PATTERNS:
        require(
            pattern in workflow,
            f"MineColonies Economy GameTest scope is missing from Sonar classification: {pattern}",
        )
    require(
        "-Dsonar.test.inclusions=" in workflow and "-Dsonar.exclusions=" in workflow,
        "NeoForge GameTests must be test-scoped and excluded only from main-code scope.",
    )
    require(
        "sonar.coverage.exclusions" not in workflow and "sonar.cpd.exclusions" not in workflow,
        "Sonar CI must not game the Quality Gate with coverage or duplication exclusions.",
    )

    print(
        "Sonar workflow policy is race-safe, self-heals Previous version through the Cloud settings API, "
        "uses basic Gradle caching, imports transformed GameTest coverage, and test-scopes NeoForge GameTests."
    )


if __name__ == "__main__":
    main()
