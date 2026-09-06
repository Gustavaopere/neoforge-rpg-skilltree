#!/usr/bin/env python3
"""Ensure SonarQube Cloud uses the race-safe Previous version New Code policy.

SonarQube Cloud configures project New Code definitions through the settings
Web API rather than the SonarQube Server ``new_code_periods`` endpoints. This
helper writes the two documented project settings for Previous version and then
reads them back to verify persistence.

The operation is idempotent at the configuration level and fail-closed: missing
credentials, API failures, malformed responses, missing settings, or values that
do not persist all make CI fail. It never selects or stores an analysis UUID.
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
EXPECTED_VALUE = "previous_version"
EXPECTED_SETTINGS = {
    "sonar.leak.period": EXPECTED_VALUE,
    "sonar.leak.period.type": EXPECTED_VALUE,
}


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


def load_settings(token: str) -> dict[str, str]:
    query = urllib.parse.urlencode(
        {
            "component": SONAR_PROJECT_KEY,
            "keys": ",".join(EXPECTED_SETTINGS),
        }
    )
    raw = api_request(f"/api/settings/values?{query}", token)
    try:
        payload = json.loads(raw.decode("utf-8"))
    except (UnicodeDecodeError, json.JSONDecodeError) as exc:
        raise RuntimeError("SonarQube settings response was not valid JSON") from exc

    if not isinstance(payload, dict):
        raise RuntimeError("SonarQube settings response was not an object")

    settings = payload.get("settings")
    if not isinstance(settings, list):
        raise RuntimeError("SonarQube settings response did not expose settings")

    resolved: dict[str, str] = {}
    for setting in settings:
        if not isinstance(setting, dict):
            continue
        key = setting.get("key")
        value = setting.get("value")
        if isinstance(key, str) and key in EXPECTED_SETTINGS and isinstance(value, str):
            resolved[key] = value
    return resolved


def set_setting(token: str, key: str, value: str) -> None:
    api_request(
        "/api/settings/set",
        token,
        method="POST",
        data={
            "component": SONAR_PROJECT_KEY,
            "key": key,
            "value": value,
        },
    )


def ensure_previous_version(token: str) -> bool:
    before = load_settings(token)
    repaired = any(before.get(key) != value for key, value in EXPECTED_SETTINGS.items())

    if repaired:
        current = ", ".join(
            f"{key}={before.get(key, '<unset>')}" for key in EXPECTED_SETTINGS
        )
        print(
            "Sonar New Code settings drift detected: "
            f"project={SONAR_PROJECT_KEY} current=[{current}] expected={EXPECTED_VALUE}"
        )

    # SonarQube Cloud documents Previous version as two project settings. Writing
    # both on every run is intentional: the operation is idempotent and avoids
    # partial configuration if a prior manual change touched only one key.
    for key, value in EXPECTED_SETTINGS.items():
        set_setting(token, key, value)

    after = load_settings(token)
    mismatches = {
        key: after.get(key)
        for key, value in EXPECTED_SETTINGS.items()
        if after.get(key) != value
    }
    if mismatches:
        raise RuntimeError(
            "Sonar New Code settings repair did not persist: "
            + ", ".join(
                f"{key}=expected:{EXPECTED_SETTINGS[key]},actual:{actual!r}"
                for key, actual in mismatches.items()
            )
        )

    return repaired


def main() -> int:
    token = os.environ.get("SONAR_TOKEN", "").strip()
    if not token:
        print("SONAR_TOKEN is required to enforce the Sonar New Code settings", file=sys.stderr)
        return 2

    try:
        repaired = ensure_previous_version(token)
    except RuntimeError as exc:
        print(str(exc), file=sys.stderr)
        return 1

    print(
        "SONAR_NEW_CODE_SETTINGS "
        f"project={SONAR_PROJECT_KEY} value={EXPECTED_VALUE} "
        f"repaired={'true' if repaired else 'false'}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
