#!/usr/bin/env python3
"""Refresh SonarQube Cloud's manual new-code baseline to a live main analysis.

SonarQube Cloud's manually selected new-code baseline stores an analysis UUID.
If housekeeping removes that analysis, subsequent main analyses can fail before
the quality gate runs. This helper queries the currently retained analyses for
`main`, selects the newest existing one, and asks SonarQube Cloud to use it as
the manual baseline through the Cloud Web API.

The operation is intentionally fail-closed: missing credentials, an empty
analysis history, malformed API responses, or insufficient permissions make the
CI job fail rather than silently bypassing SonarQube.
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


def api_request(path: str, token: str, *, method: str = "GET", data: dict[str, str] | None = None) -> bytes:
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
        raise RuntimeError(f"SonarQube API {method} {path} failed with HTTP {exc.code}: {body}") from exc
    except urllib.error.URLError as exc:
        raise RuntimeError(f"SonarQube API {method} {path} failed: {exc.reason}") from exc


def newest_retained_analysis(token: str) -> str:
    query = urllib.parse.urlencode(
        {
            "project": SONAR_PROJECT_KEY,
            "branch": SONAR_BASELINE_BRANCH,
            "ps": "100",
        }
    )
    raw = api_request(f"/api/project_analyses/search?{query}", token)
    try:
        payload = json.loads(raw.decode("utf-8"))
    except (UnicodeDecodeError, json.JSONDecodeError) as exc:
        raise RuntimeError("SonarQube project analysis response was not valid JSON") from exc

    analyses = payload.get("analyses")
    if not isinstance(analyses, list) or not analyses:
        raise RuntimeError(
            f"No retained SonarQube analyses found for {SONAR_PROJECT_KEY}:{SONAR_BASELINE_BRANCH}"
        )

    key = analyses[0].get("key") if isinstance(analyses[0], dict) else None
    if not isinstance(key, str) or not key:
        raise RuntimeError("Newest SonarQube analysis did not expose a valid analysis key")
    return key


def set_specific_analysis_baseline(token: str, analysis_key: str) -> None:
    api_request(
        "/api/project_analyses/set_baseline",
        token,
        method="POST",
        data={
            "project": SONAR_PROJECT_KEY,
            "branch": SONAR_BASELINE_BRANCH,
            "analysis": analysis_key,
        },
    )


def main() -> int:
    token = os.environ.get("SONAR_TOKEN", "").strip()
    if not token:
        print("SONAR_TOKEN is required to refresh the SonarQube new-code baseline", file=sys.stderr)
        return 2

    try:
        analysis_key = newest_retained_analysis(token)
        set_specific_analysis_baseline(token, analysis_key)
    except RuntimeError as exc:
        print(str(exc), file=sys.stderr)
        return 1

    print(
        "SONAR_NEW_CODE_BASELINE "
        f"project={SONAR_PROJECT_KEY} branch={SONAR_BASELINE_BRANCH} analysis={analysis_key}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
