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


def settings_payload(values: dict[str, str]) -> bytes:
    return json.dumps(
        {
            "settings": [
                {"key": key, "value": value, "inherited": False}
                for key, value in values.items()
            ]
        }
    ).encode("utf-8")


def test_previous_version_remains_idempotent(module) -> None:
    calls = []
    reads = 0

    def fake_request(path, token, *, method="GET", data=None):
        nonlocal reads
        calls.append((path, method, data))
        require(token == "token", "Unexpected token in idempotency test")
        if path.startswith("/api/settings/values?"):
            reads += 1
            return settings_payload(module.EXPECTED_SETTINGS)
        require(path == "/api/settings/set", "Unexpected Sonar mutation endpoint")
        require(method == "POST", "Cloud setting writes must use POST")
        require(data["component"] == module.SONAR_PROJECT_KEY, "Wrong project component")
        require(data["key"] in module.EXPECTED_SETTINGS, "Unexpected setting key")
        require(data["value"] == module.EXPECTED_VALUE, "Unexpected setting value")
        return b""

    module.api_request = fake_request
    repaired = module.ensure_previous_version("token")
    require(repaired is False, "Already-correct settings should report no drift")
    require(reads == 2, "Helper must verify settings after idempotent writes")
    require(len(calls) == 4, "Helper must read, write both settings, and verify")


def test_stale_manual_baseline_repairs_to_previous_version(module) -> None:
    calls = []
    reads = 0

    stale = {
        "sonar.leak.period": "27a14430-6921-4c63-ab5f-ab2bf23e15db",
        "sonar.leak.period.type": "specific_analysis",
    }

    def fake_request(path, token, *, method="GET", data=None):
        nonlocal reads
        calls.append((path, method, data))
        require(token == "token", "Unexpected token in repair test")
        if path.startswith("/api/settings/values?"):
            reads += 1
            return settings_payload(stale if reads == 1 else module.EXPECTED_SETTINGS)
        require(path == "/api/settings/set", "Unexpected Sonar mutation endpoint")
        require(method == "POST", "New Code repair must use POST")
        require(
            data
            == {
                "component": module.SONAR_PROJECT_KEY,
                "key": data["key"],
                "value": "previous_version",
            },
            "Repair must use project settings without an analysis UUID",
        )
        require(data["key"] in module.EXPECTED_SETTINGS, "Unexpected setting key")
        return b""

    module.api_request = fake_request
    repaired = module.ensure_previous_version("token")
    require(repaired is True, "Stale manual baseline must be reported as repaired")
    require(reads == 2, "Repair must verify persisted settings")
    require(len(calls) == 4, "Repair must perform read, two writes, verification read")


def test_partial_setting_drift_repairs_both_keys(module) -> None:
    reads = 0
    writes = []

    def fake_request(path, token, *, method="GET", data=None):
        nonlocal reads
        if path.startswith("/api/settings/values?"):
            reads += 1
            if reads == 1:
                return settings_payload({"sonar.leak.period": "previous_version"})
            return settings_payload(module.EXPECTED_SETTINGS)
        writes.append(data)
        return b""

    module.api_request = fake_request
    repaired = module.ensure_previous_version("token")
    require(repaired is True, "Partial configuration must be treated as drift")
    require(len(writes) == 2, "Both documented Cloud settings must be written atomically-by-contract")


def test_malformed_settings_response_fails_closed(module) -> None:
    def fake_request(path, token, *, method="GET", data=None):
        return b"not-json"

    module.api_request = fake_request
    try:
        module.ensure_previous_version("token")
    except RuntimeError:
        return
    raise AssertionError("Malformed Sonar settings response must fail closed")


def test_non_persistent_repair_fails_closed(module) -> None:
    stale = {
        "sonar.leak.period": "old",
        "sonar.leak.period.type": "version",
    }

    def fake_request(path, token, *, method="GET", data=None):
        if path.startswith("/api/settings/values?"):
            return settings_payload(stale)
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
    test_previous_version_remains_idempotent(module)
    test_stale_manual_baseline_repairs_to_previous_version(module)
    test_partial_setting_drift_repairs_both_keys(module)
    test_malformed_settings_response_fails_closed(module)
    test_non_persistent_repair_fails_closed(module)
    print("SonarQube Cloud New Code settings self-healing contract: PASS")


if __name__ == "__main__":
    main()
