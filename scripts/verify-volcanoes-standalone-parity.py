#!/usr/bin/env python3
from __future__ import annotations

import hashlib
import json
from pathlib import Path
import sys
import urllib.request

ROOT = Path(__file__).resolve().parents[1]
STANDALONE_REPO = "Gustavaopere/Volcanoes"
STANDALONE_COMMIT = "eaddc3232dfc600780769f4a5e7e45ff1e50181c"
STANDALONE_TREE = "c87fb2c5aede57d6eab69592e3377d76f3a3c232"
TREE_URL = (
    f"https://api.github.com/repos/{STANDALONE_REPO}/git/trees/"
    f"{STANDALONE_TREE}?recursive=1"
)

FUNCTIONAL_PREFIXES = (
    "src/main/java/dev/gustavopere/volcanoes/",
    "src/main/resources/assets/volcanoes/",
    "src/main/resources/data/volcanoes/",
    "src/test/java/dev/gustavopere/volcanoes/",
    "src/test/resources/",
)
RESOURCE_PREFIXES = (
    "src/main/resources/assets/volcanoes/",
    "src/main/resources/data/volcanoes/",
    "src/test/resources/",
)
WORKFLOW_MAP = {
    "cold-sweat-acceptance.yml": "volcanoes-cold-sweat-acceptance.yml",
    "create-sable-acceptance.yml": "volcanoes-create-sable-acceptance.yml",
    "full-pack-compatibility-acceptance.yml": "volcanoes-full-pack-compatibility-acceptance.yml",
    "minecolonies-claim-acceptance.yml": "volcanoes-minecolonies-claim-acceptance.yml",
    "performance-acceptance.yml": "volcanoes-performance-acceptance.yml",
    "release-readiness.yml": "volcanoes-release-readiness.yml",
    "rns-hydrothermal-acceptance.yml": "volcanoes-rns-hydrothermal-acceptance.yml",
    "third-party-provenance-audit.yml": "volcanoes-third-party-provenance-audit.yml",
    "worldgen-compatibility-matrix.yml": "volcanoes-worldgen-compatibility-matrix.yml",
}


def git_blob_sha(path: Path) -> str:
    data = path.read_bytes()
    header = f"blob {len(data)}\0".encode("ascii")
    return hashlib.sha1(header + data, usedforsecurity=False).hexdigest()


def fetch_tree() -> list[dict[str, object]]:
    request = urllib.request.Request(
        TREE_URL,
        headers={
            "Accept": "application/vnd.github+json",
            "User-Agent": "rpgskilltree-volcanoes-consolidation-audit",
        },
    )
    with urllib.request.urlopen(request, timeout=30) as response:
        payload = json.load(response)
    if payload.get("truncated"):
        raise AssertionError("standalone recursive Git tree was truncated")
    return payload["tree"]


def main() -> int:
    tree = fetch_tree()
    blobs = {
        str(entry["path"]): str(entry["sha"])
        for entry in tree
        if entry.get("type") == "blob"
    }

    functional = {
        path: sha
        for path, sha in blobs.items()
        if path.startswith(FUNCTIONAL_PREFIXES)
    }
    missing = [path for path in sorted(functional) if not (ROOT / path).is_file()]
    if missing:
        raise AssertionError(
            "functional standalone paths missing from unified repository: " + ", ".join(missing)
        )

    resource_drift = []
    for path, expected_sha in sorted(functional.items()):
        if not path.startswith(RESOURCE_PREFIXES):
            continue
        actual_sha = git_blob_sha(ROOT / path)
        if actual_sha != expected_sha:
            resource_drift.append(f"{path} expected={expected_sha} actual={actual_sha}")
    if resource_drift:
        raise AssertionError(
            "standalone resource/test-resource bytes changed during consolidation: "
            + "; ".join(resource_drift)
        )

    missing_workflows = []
    for standalone_name, unified_name in WORKFLOW_MAP.items():
        standalone_path = f".github/workflows/{standalone_name}"
        if standalone_path in blobs and not (ROOT / ".github/workflows" / unified_name).is_file():
            missing_workflows.append(f"{standalone_name}->{unified_name}")
    if missing_workflows:
        raise AssertionError("standalone acceptance workflow mappings missing: " + ", ".join(missing_workflows))

    standalone_scripts = sorted(
        path for path in blobs if path.startswith(".github/scripts/")
    )
    missing_scripts = []
    for path in standalone_scripts:
        relative = path.removeprefix(".github/scripts/")
        if not (ROOT / ".github/scripts/volcanoes" / relative).is_file():
            missing_scripts.append(path)
    if missing_scripts:
        raise AssertionError(
            "standalone CI helper scripts missing from unified Volcanoes namespace: "
            + ", ".join(missing_scripts)
        )

    changed_java_or_tests = sum(
        1
        for path, expected_sha in functional.items()
        if path.startswith((
            "src/main/java/dev/gustavopere/volcanoes/",
            "src/test/java/dev/gustavopere/volcanoes/",
        ))
        and git_blob_sha(ROOT / path) != expected_sha
    )
    print(
        "VOLCANOES_STANDALONE_PARITY status=GREEN "
        f"standalone_commit={STANDALONE_COMMIT} functional_paths={len(functional)} "
        f"byte_exact_resources={sum(1 for p in functional if p.startswith(RESOURCE_PREFIXES))} "
        f"java_or_test_paths_changed_intentionally={changed_java_or_tests}"
    )
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (AssertionError, OSError, urllib.error.URLError) as exc:
        print(f"VOLCANOES_STANDALONE_PARITY status=RED reason={exc}", file=sys.stderr)
        raise SystemExit(1)
