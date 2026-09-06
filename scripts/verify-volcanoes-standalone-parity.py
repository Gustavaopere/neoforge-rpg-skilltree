#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
STANDALONE_COMMIT = "eaddc3232dfc600780769f4a5e7e45ff1e50181c"

REQUIRED_FUNCTIONAL_PREFIXES = (
    "src/main/java/dev/gustavopere/volcanoes",
    "src/main/resources/assets/volcanoes",
    "src/main/resources/data/volcanoes",
    "src/test/java/dev/gustavopere/volcanoes",
)
OPTIONAL_FUNCTIONAL_PREFIXES = (
    "src/test/resources",
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


def functional_files(source_root: Path) -> list[Path]:
    files: list[Path] = []
    for relative in REQUIRED_FUNCTIONAL_PREFIXES:
        base = source_root / relative
        if not base.is_dir():
            raise AssertionError(f"standalone source root missing: {relative}")
        files.extend(path for path in base.rglob("*") if path.is_file())
    for relative in OPTIONAL_FUNCTIONAL_PREFIXES:
        base = source_root / relative
        if base.is_dir():
            files.extend(path for path in base.rglob("*") if path.is_file())
    return files


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("source_root", type=Path, help="restored frozen standalone Volcanoes source")
    args = parser.parse_args()
    source_root = args.source_root.resolve()
    if not source_root.is_dir():
        raise AssertionError(f"standalone source checkout does not exist: {source_root}")

    source_paths = functional_files(source_root)
    functional = {
        path.relative_to(source_root).as_posix(): git_blob_sha(path)
        for path in source_paths
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
        standalone_path = source_root / ".github/workflows" / standalone_name
        if standalone_path.is_file() and not (ROOT / ".github/workflows" / unified_name).is_file():
            missing_workflows.append(f"{standalone_name}->{unified_name}")
    if missing_workflows:
        raise AssertionError("standalone acceptance workflow mappings missing: " + ", ".join(missing_workflows))

    standalone_scripts_root = source_root / ".github/scripts"
    if not standalone_scripts_root.is_dir():
        raise AssertionError("standalone .github/scripts directory is missing")
    standalone_scripts = sorted(path for path in standalone_scripts_root.rglob("*") if path.is_file())
    missing_scripts = []
    for path in standalone_scripts:
        relative = path.relative_to(standalone_scripts_root)
        if not (ROOT / ".github/scripts/volcanoes" / relative).is_file():
            missing_scripts.append(path.relative_to(source_root).as_posix())
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
    except (AssertionError, OSError, ValueError) as exc:
        print(f"VOLCANOES_STANDALONE_PARITY status=RED reason={exc}", file=sys.stderr)
        raise SystemExit(1)
