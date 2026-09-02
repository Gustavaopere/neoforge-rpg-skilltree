#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github" / "workflows" / "sonarqube.yml"
LEGACY_BASELINE_SCRIPT = ROOT / "scripts" / "refresh-sonar-new-code-baseline.py"
NEW_CODE_POLICY_HELPER = ROOT / "scripts" / "ensure_sonar_new_code_period.py"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    workflow = WORKFLOW.read_text(encoding="utf-8")

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
        "Diagnose Sonar main analysis history on internal PR" in workflow,
        "Internal PR Sonar runs must expose bounded main analysis history before any baseline repair is attempted.",
    )
    require(
        "github.event.pull_request.head.repo.full_name == github.repository" in workflow,
        "Sonar analysis-history diagnostics must use secrets only for same-repository pull requests.",
    )
    require(
        "/api/project_analyses/search" in workflow
        and '--data-urlencode "project=${SONAR_PROJECT}"' in workflow
        and "--data-urlencode 'branch=main'" in workflow
        and "--data-urlencode 'ps=100'" in workflow,
        "Sonar analysis-history diagnostics must query a bounded main-branch project analysis history.",
    )
    require(
        "Sonar main analysis history:" in workflow
        and ".events[]?" in workflow
        and "revision=" in workflow,
        "Sonar analysis-history diagnostics must print revision and version-event evidence.",
    )

    print(
        "Sonar workflow policy is race-safe, self-heals Previous version through the Cloud settings API, "
        "uses basic Gradle caching, exposes bounded analysis-history evidence, and does not install a manual analysis baseline."
    )


if __name__ == "__main__":
    main()
