#!/usr/bin/env python3
"""Ensure SonarQube Cloud main uses the race-safe PREVIOUS_VERSION New Code policy.

The project previously drifted to a SPECIFIC_ANALYSIS policy whose referenced
analysis was later removed by SonarQube housekeeping. That makes new analyses
fail before the Quality Gate can run. This helper repairs configuration drift to
the deterministic PREVIOUS_VERSION policy without storing or selecting analysis
UUIDs.

The operation is idempotent and fail-closed: malformed responses, API failures,
missing credentials, or a mutation that does not persist all make CI fail.
"""

from __future__ import annotations

import json
import os
import sys
import urllib.error
import urllib.parse
import urllib.request

SONAR_HOST_URL = os.environ.get("SONAR_HOST_URL", "https://sonarcloud.io").rstrip("/")
SONAR_PROJECT_KEY = os.environ.get("SONAR_PROJECT_KEY", "Gustavaopere_neoforge-rpg-skilltree")
SONAR_BASELINE_BRANCH = os.environ.get("SONAR_BASELINE_BRANCH", "main")
EXPECTED_TYPE = "PREVIOUS_VERSION"


def api_request(
    path: str,
    token: str,
    *,
    method: str = "GET",
    data: dict[str, str] | None = None,
) -> bytes:
    encoded = urllib.parse.urlencode(data).encode("utf-8") if data is not None else None
    request = urllib.request.Request(
        f"{SONAR_HOST_URL}{path}",
        data=encoded,
        method=method,
        headers={
            "Authorization": f"Bearer {token}",
            "Accept": "application/json",
            "Content-Type": "application/x-www-form-urlencoded",
        },
    )
    try:
        with urllib.request.urlopen(request, timeout=30) as response:
            return response.read()
    except urllib.error.HTTPError as exc:
        body = exc.read().decode("utf-8", errors="replace")
        raise RuntimeError(
            f"SonarQube API {method} {path} failed with HTTP {exc.code}: {body}"
        ) from exc
    except urllib.error.URLError as exc:
        raise RuntimeError(f"SonarQube API {method} {path} failed: {exc.reason}") from exc


def load_period(token: str) -> dict[str, object]:
    query = urllib.parse.urlencode(
        {
            "project": SONAR_PROJECT_KEY,
            "branch": SONAR_BASELINE_BRANCH,
        }
    )
    raw = api_request(f"/api/new_code_periods/show?{query}", token)
    try:
        payload = json.loads(raw.decode("utf-8"))
    except (UnicodeDecodeError, json.JSONDecodeError) as exc:
        raise RuntimeError("SonarQube New Code period response was not valid JSON") from exc

    if not isinstance(payload, dict):
        raise RuntimeError("SonarQube New Code period response was not an object")

    period_type = payload.get("type")
    if not isinstance(period_type, str) or not period_type:
        raise RuntimeError("SonarQube New Code period response did not expose a valid type")

    return payload


def set_previous_version(token: str) -> None:
    api_request(
        "/api/new_code_periods/set",
        token,
        method="POST",
        data={
            "project": SONAR_PROJECT_KEY,
            "branch": SONAR_BASELINE_BRANCH,
            "type": EXPECTED_TYPE,
        },
    )


def ensure_previous_version(token: str) -> bool:
    before = load_period(token)
    before_type = before["type"]
    if before_type == EXPECTED_TYPE:
        return False

    print(
        "Sonar New Code period drift detected: "
        f"project={SONAR_PROJECT_KEY} branch={SONAR_BASELINE_BRANCH} "
        f"current={before_type} expected={EXPECTED_TYPE}"
    )
    set_previous_version(token)

    after = load_period(token)
    after_type = after["type"]
    if after_type != EXPECTED_TYPE:
        raise RuntimeError(
            "Sonar New Code period repair did not persist: "
            f"expected {EXPECTED_TYPE}, got {after_type}"
        )

    return True


def main() -> int:
    token = os.environ.get("SONAR_TOKEN", "").strip()
    if not token:
        print("SONAR_TOKEN is required to enforce the Sonar New Code period", file=sys.stderr)
        return 2

    try:
        repaired = ensure_previous_version(token)
    except RuntimeError as exc:
        print(str(exc), file=sys.stderr)
        return 1

    print(
        "SONAR_NEW_CODE_PERIOD "
        f"project={SONAR_PROJECT_KEY} branch={SONAR_BASELINE_BRANCH} "
        f"type={EXPECTED_TYPE} repaired={'true' if repaired else 'false'}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
