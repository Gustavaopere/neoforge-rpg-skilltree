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
        "Sonar CI must include the deterministic New Code period policy helper.",
    )

    helper = NEW_CODE_POLICY_HELPER.read_text(encoding="utf-8")
    require(
        "ensure_sonar_new_code_period.py" in workflow,
        "Sonar workflow must enforce the New Code period before analysis.",
    )
    require(
        workflow.index("ensure_sonar_new_code_period.py")
        < workflow.index("Build and analyze with SonarQube"),
        "Sonar New Code period repair must run before analysis creation.",
    )
    require(
        'EXPECTED_TYPE = "PREVIOUS_VERSION"' in helper,
        "Sonar New Code policy must be PREVIOUS_VERSION.",
    )
    require(
        '"type": EXPECTED_TYPE' in helper,
        "Sonar policy repair must explicitly set PREVIOUS_VERSION.",
    )
    require(
        "/api/new_code_periods/list" in helper and "/api/new_code_periods/set" in helper,
        "Sonar Cloud policy helper must read through list and repair through set.",
    )
    require(
        "/api/new_code_periods/show" not in helper,
        "Sonar Cloud policy helper must not depend on the unavailable show endpoint.",
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

    print(
        "Sonar workflow policy is race-safe, self-heals to Previous version through the Cloud list API, "
        "uses basic Gradle caching, and does not install a manual analysis baseline."
    )


if __name__ == "__main__":
    main()
