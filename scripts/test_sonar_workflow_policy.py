#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github" / "workflows" / "sonarqube.yml"
LEGACY_BASELINE_SCRIPT = ROOT / "scripts" / "refresh-sonar-new-code-baseline.py"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    workflow = WORKFLOW.read_text(encoding="utf-8")

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

    print("Sonar workflow policy is race-safe and does not install a manual New Code baseline.")


if __name__ == "__main__":
    main()
