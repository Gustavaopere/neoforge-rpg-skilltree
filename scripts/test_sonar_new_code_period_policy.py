#!/usr/bin/env python3
from __future__ import annotations

import importlib.util
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
HELPER = ROOT / "scripts" / "ensure_sonar_new_code_period.py"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def load_helper():
    require(HELPER.exists(), f"Missing Sonar New Code policy helper: {HELPER}")
    spec = importlib.util.spec_from_file_location("ensure_sonar_new_code_period", HELPER)
    require(spec is not None and spec.loader is not None, "Unable to load Sonar New Code policy helper")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def test_previous_version_is_noop(module) -> None:
    calls = []

    def fake_request(path, token, *, method="GET", data=None):
        calls.append((path, method, data))
        require(token == "token", "Unexpected token in no-op test")
        require(method == "GET", "PREVIOUS_VERSION must not trigger a mutation")
        return json.dumps({"type": "PREVIOUS_VERSION", "inherited": False}).encode("utf-8")

    module.api_request = fake_request
    repaired = module.ensure_previous_version("token")
    require(repaired is False, "PREVIOUS_VERSION should be a no-op")
    require(len(calls) == 1, "PREVIOUS_VERSION should require exactly one read")


def test_stale_manual_baseline_repairs_to_previous_version(module) -> None:
    calls = []
    show_count = 0

    def fake_request(path, token, *, method="GET", data=None):
        nonlocal show_count
        calls.append((path, method, data))
        require(token == "token", "Unexpected token in repair test")
        if path.startswith("/api/new_code_periods/show?"):
            show_count += 1
            period_type = "SPECIFIC_ANALYSIS" if show_count == 1 else "PREVIOUS_VERSION"
            return json.dumps({"type": period_type, "inherited": False}).encode("utf-8")
        require(path == "/api/new_code_periods/set", "Unexpected Sonar mutation endpoint")
        require(method == "POST", "New Code period repair must use POST")
        require(
            data
            == {
                "project": module.SONAR_PROJECT_KEY,
                "branch": module.SONAR_BASELINE_BRANCH,
                "type": "PREVIOUS_VERSION",
            },
            "Repair must set PREVIOUS_VERSION without an analysis UUID/value",
        )
        return b""

    module.api_request = fake_request
    repaired = module.ensure_previous_version("token")
    require(repaired is True, "Stale manual baseline must be repaired")
    require(show_count == 2, "Repair must verify persisted policy after mutation")
    require(len(calls) == 3, "Repair must perform read, mutation, verification read")


def test_malformed_show_response_fails_closed(module) -> None:
    def fake_request(path, token, *, method="GET", data=None):
        return b"not-json"

    module.api_request = fake_request
    try:
        module.ensure_previous_version("token")
    except RuntimeError:
        return
    raise AssertionError("Malformed Sonar New Code response must fail closed")


def test_non_persistent_repair_fails_closed(module) -> None:
    def fake_request(path, token, *, method="GET", data=None):
        if path.startswith("/api/new_code_periods/show?"):
            return json.dumps({"type": "SPECIFIC_ANALYSIS", "inherited": False}).encode("utf-8")
        return b""

    module.api_request = fake_request
    try:
        module.ensure_previous_version("token")
    except RuntimeError as exc:
        require("did not persist" in str(exc), "Persistence failure should be explicit")
        return
    raise AssertionError("A repair that does not persist must fail closed")


def main() -> None:
    module = load_helper()
    test_previous_version_is_noop(module)
    test_stale_manual_baseline_repairs_to_previous_version(module)
    test_malformed_show_response_fails_closed(module)
    test_non_persistent_repair_fails_closed(module)
    print("Sonar New Code period self-healing contract: PASS")


if __name__ == "__main__":
    main()
