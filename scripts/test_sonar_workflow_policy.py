#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github" / "workflows" / "sonarqube.yml"
BUILD_GRADLE = ROOT / "build.gradle"
LEGACY_BASELINE_SCRIPT = ROOT / "scripts" / "refresh-sonar-new-code-baseline.py"
NEW_CODE_POLICY_HELPER = ROOT / "scripts" / "ensure_sonar_new_code_period.py"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    workflow = WORKFLOW.read_text(encoding="utf-8")
    build_gradle = BUILD_GRADLE.read_text(encoding="utf-8")

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
        'property "sonar.projectVersion", "${mod_version}-sonar.3"' in build_gradle,
        "Sonar Previous version must use the stable alpha.6 Sonar release marker, not the long-lived Gradle dev version.",
    )
    require(
        "GITHUB_SHA" not in build_gradle
        and "GITHUB_RUN" not in build_gradle
        and "github.run" not in build_gradle,
        "Sonar projectVersion must remain stable across CI builds; commit/run identifiers are forbidden.",
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

    print(
        "Sonar workflow policy is race-safe, self-heals Previous version through the Cloud settings API, "
        "uses a stable Sonar release marker and basic Gradle caching, and does not install a manual analysis baseline."
    )


if __name__ == "__main__":
    main()
