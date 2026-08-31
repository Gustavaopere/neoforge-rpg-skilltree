#!/usr/bin/env python3
"""Wait for the consolidated Volcanoes sibling workflow set to be GREEN on one exact commit."""

from __future__ import annotations

import argparse
import json
import time
from urllib.parse import urlencode
from urllib.request import Request, urlopen

EXPECTED = (
    "RPG Skill Tree CI",
    "Volcanoes Consolidation Contract",
    "Volcanoes Cold Sweat Heat Acceptance",
    "Volcanoes Performance Hardening Acceptance",
    "Volcanoes MineColonies Claim Acceptance",
    "Volcanoes Create Sable Acceptance",
    "Volcanoes RNS Hydrothermal Acceptance",
    "Volcanoes Full Pack Compatibility Acceptance",
    "Volcanoes Third-Party Provenance Audit",
    "Volcanoes Worldgen Compatibility Matrix",
)


def fetch_runs(repo: str, sha: str, event: str, token: str) -> list[dict]:
    query = urlencode({"head_sha": sha, "event": event, "per_page": 100})
    request = Request(
        f"https://api.github.com/repos/{repo}/actions/runs?{query}",
        headers={
            "Accept": "application/vnd.github+json",
            "Authorization": f"Bearer {token}",
            "X-GitHub-Api-Version": "2022-11-28",
            "User-Agent": "rpgskilltree-volcanoes-release-readiness",
        },
    )
    with urlopen(request, timeout=30) as response:
        payload = json.load(response)
    return [
        run for run in payload.get("workflow_runs", [])
        if run.get("head_sha") == sha and run.get("event") == event
    ]


def latest_by_name(runs: list[dict]) -> dict[str, dict]:
    latest: dict[str, dict] = {}
    for run in runs:
        name = run.get("name")
        if name not in EXPECTED:
            continue
        current = latest.get(name)
        if current is None or int(run.get("id", 0)) > int(current.get("id", 0)):
            latest[name] = run
    return latest


def snapshot(latest: dict[str, dict]) -> str:
    parts = []
    for name in EXPECTED:
        run = latest.get(name)
        if run is None:
            parts.append(f"{name}=MISSING")
        else:
            status = run.get("status") or "?"
            conclusion = run.get("conclusion") or "-"
            parts.append(f"{name}={status}/{conclusion}#{run.get('id')}")
    return " | ".join(parts)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo", required=True)
    parser.add_argument("--sha", required=True)
    parser.add_argument("--event", required=True, choices=("pull_request", "push"))
    parser.add_argument("--token", required=True)
    parser.add_argument("--timeout-seconds", type=int, default=3600)
    parser.add_argument("--poll-seconds", type=int, default=15)
    args = parser.parse_args()

    deadline = time.monotonic() + args.timeout_seconds
    previous = None
    while True:
        latest = latest_by_name(fetch_runs(args.repo, args.sha, args.event, args.token))
        current = snapshot(latest)
        if current != previous:
            print(f"VOLCANOES_CONSOLIDATED_WORKFLOW_SET {current}", flush=True)
            previous = current

        if len(latest) == len(EXPECTED) and all(
            run.get("status") == "completed" and run.get("conclusion") == "success"
            for run in latest.values()
        ):
            print(
                f"VOLCANOES_CONSOLIDATED_WORKFLOW_SET_RESULT status=GREEN sha={args.sha} "
                f"event={args.event} workflows={len(EXPECTED)}",
                flush=True,
            )
            return 0

        if time.monotonic() >= deadline:
            print(
                f"VOLCANOES_CONSOLIDATED_WORKFLOW_SET_RESULT status=FAIL sha={args.sha} "
                f"event={args.event} workflows={len(latest)}/{len(EXPECTED)}",
                flush=True,
            )
            return 1
        time.sleep(args.poll_seconds)


if __name__ == "__main__":
    raise SystemExit(main())
