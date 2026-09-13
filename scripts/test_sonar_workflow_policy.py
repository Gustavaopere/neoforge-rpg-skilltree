#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github" / "workflows" / "sonarqube.yml"
GAME_TEST_COVERAGE_INIT = ROOT / "gradle" / "sonar-gametest-coverage.init.gradle"
ARS_PROVIDER_RUNTIME_INIT = ROOT / "gradle" / "ars-provider-gametest-runtime.init.gradle"
LEGACY_ARS_JUNIT_RUNTIME_INIT = ROOT / "gradle" / "ars-sonar-test-runtime.init.gradle"
LEGACY_BASELINE_SCRIPT = ROOT / "scripts" / "refresh-sonar-new-code-baseline.py"
NEW_CODE_POLICY_HELPER = ROOT / "scripts" / "ensure_sonar_new_code_period.py"

BATTLE_MAGE_TEST_PATTERNS = (
    "src/main/java/dev/gustavopere/rpgskilltree/gametest/BattleMageProviderGameTests.java",
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/battlemage/gametest/**/*",
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/battlemage/BattleMageReloadAndAuthorityGameTests.java",
)
ARS_PROVIDER_TEST_PATTERN = (
    "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/gametest/ArsProviderCausalityGameTests.java"
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
        "-Dsonar.projectVersion=${GITHUB_SHA}" in workflow,
        "Previous version requires each CI analysis to publish the immutable Git commit as sonar.projectVersion.",
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
        "ars-provider/ars-provider.xml" in workflow and "'ars-provider'" in game_test_coverage,
        "Sonar CI must import a dedicated transformed-class Ars provider GameTest report.",
    )
    require(
        "minecolonies-provider/minecolonies-provider.xml" in workflow
        and "'minecolonies-provider'" in game_test_coverage,
        "Sonar CI must import a dedicated transformed-class MineColonies provider GameTest report.",
    )
    require(
        ARS_PROVIDER_RUNTIME_INIT.exists()
        and "ars-provider-gametest-runtime.init.gradle" in workflow
        and "-ParsProviderRuntime=true" in workflow,
        "Ars coverage must run in its opt-in loaded-provider NeoForge GameTest runtime.",
    )
    require(
        not LEGACY_ARS_JUNIT_RUNTIME_INIT.exists()
        and "ars-sonar-test-runtime.init.gradle" not in workflow,
        "Ars provider coverage must not regress to plain JUnit bootstrap injection.",
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
        ARS_PROVIDER_TEST_PATTERN in workflow,
        "Ars provider GameTest must be test-scoped in Sonar classification from the isolated Ars adapter tree.",
    )
    for pattern in ECONOMY_TEST_PATTERNS:
        require(
            pattern in workflow,
            f"MineColonies Economy GameTest scope is missing from Sonar classification: {pattern}",
        )
    require(
        "-Dsonar.test.inclusions=" in workflow and "-Dsonar.exclusions=" in workflow,
        "Provider GameTests must be test-scoped and excluded only from main-code scope.",
    )
    require(
        "sonar.coverage.exclusions" not in workflow and "sonar.cpd.exclusions" not in workflow,
        "Sonar CI must not game the Quality Gate with coverage or duplication exclusions.",
    )

    diagnose_name = "Diagnose Sonar pull-request new-code issues"
    upload_name = "Upload Sonar test diagnostics on failure"
    require(
        diagnose_name in workflow,
        "Sonar CI must diagnose the pull-request analysis that actually failed the Quality Gate.",
    )
    require(
        "if: ${{ always() && github.event_name == 'pull_request' }}" in workflow,
        "Pull-request Sonar diagnostics must run even when the analysis/Quality Gate step fails.",
    )
    require(
        "SONAR_PULL_REQUEST: ${{ github.event.pull_request.number }}" in workflow
        and '--data-urlencode "pullRequest=${SONAR_PULL_REQUEST}"' in workflow,
        "Sonar diagnostics must query the current pull request rather than branch=main.",
    )
    require(
        "branch=main" not in workflow[workflow.index(diagnose_name):workflow.index(upload_name)],
        "Pull-request diagnostics must not inspect main when explaining a PR Quality Gate failure.",
    )
    require(
        workflow.index(diagnose_name) < workflow.index(upload_name),
        "Sonar diagnostics must run before the failure artifact is uploaded.",
    )
    require(
        "build/sonar-diagnostics/pr-issues.json" in workflow,
        "The exact Sonar pull-request issue payload must be retained in the diagnostic artifact.",
    )
    require(
        all(field in workflow for field in (".rule", ".component", ".line", ".message", ".severity", ".type")),
        "Sonar diagnostics must expose rule, component, line, message, severity, and type for root-cause triage.",
    )

    print(
        "Sonar workflow policy is race-safe, self-heals Previous version through the Cloud settings API, "
        "publishes a commit-scoped project version, uses basic Gradle caching, imports transformed Ars and "
        "MineColonies provider GameTest coverage, keeps provider coverage out of plain JUnit, and preserves "
        "pull-request-specific Sonar issue diagnostics even after a Quality Gate failure."
    )


if __name__ == "__main__":
    main()
