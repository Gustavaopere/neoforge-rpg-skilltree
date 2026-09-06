#!/usr/bin/env python3
"""Verify functional parity with the frozen standalone Volcanoes source tree.

The consolidated RPG repository is allowed to adapt files only when an explicit,
reviewed exception documents why the change is required by single-artifact
consolidation or later verified fixes. Missing source functionality and silent
content drift always fail the contract.
"""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
SOURCE_SHA = "eaddc3232dfc600780769f4a5e7e45ff1e50181c"
EXCEPTIONS_PATH = ROOT / "docs/volcanoes/provenance/functional-parity-exceptions.json"
FUNCTIONAL_ROOTS = (
    "src/main/java/dev/gustavopere/volcanoes",
    "src/main/resources/assets/volcanoes",
    "src/main/resources/data/volcanoes",
    "src/test/java/dev/gustavopere/volcanoes",
    "integration-templates",
)
FUNCTIONAL_FILES = (
    "src/main/resources/volcanoes.mixins.json",
)


def digest(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


def source_files(source_root: Path) -> set[str]:
    paths: set[str] = set(FUNCTIONAL_FILES)
    for root in FUNCTIONAL_ROOTS:
        base = source_root / root
        if not base.is_dir():
            raise AssertionError(f"source root missing: {root}")
        paths.update(
            path.relative_to(source_root).as_posix()
            for path in base.rglob("*")
            if path.is_file()
        )
    return paths


def load_exceptions() -> dict[str, str]:
    if not EXCEPTIONS_PATH.is_file():
        raise AssertionError(f"missing exception ledger: {EXCEPTIONS_PATH.relative_to(ROOT)}")
    payload = json.loads(EXCEPTIONS_PATH.read_text(encoding="utf-8"))
    if payload.get("source_sha") != SOURCE_SHA:
        raise AssertionError("functional parity exception ledger source_sha is stale")
    raw = payload.get("adapted_files")
    if not isinstance(raw, dict):
        raise AssertionError("adapted_files must be an object mapping path to reason")
    result: dict[str, str] = {}
    for path, reason in raw.items():
        if not isinstance(path, str) or not path.strip():
            raise AssertionError("adapted_files contains an invalid path")
        if not isinstance(reason, str) or not reason.strip():
            raise AssertionError(f"adapted file {path} must have a non-empty reason")
        result[path] = reason.strip()
    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("source_root", type=Path, help="checkout of frozen standalone Volcanoes source")
    args = parser.parse_args()
    source_root = args.source_root.resolve()
    if not source_root.is_dir():
        raise AssertionError(f"source checkout does not exist: {source_root}")

    files = source_files(source_root)
    exceptions = load_exceptions()
    unknown_exceptions = sorted(set(exceptions) - files)
    if unknown_exceptions:
        raise AssertionError("exception ledger references non-functional source files: " + ", ".join(unknown_exceptions))

    missing: list[str] = []
    unexpected_drift: list[str] = []
    adapted: list[str] = []
    identical = 0

    for relative in sorted(files):
        source = source_root / relative
        target = ROOT / relative
        if not target.is_file():
            missing.append(relative)
            continue
        if digest(source) == digest(target):
            identical += 1
            continue
        if relative in exceptions:
            adapted.append(relative)
            continue
        unexpected_drift.append(relative)

    print(
        "VOLCANOES_FUNCTIONAL_PARITY "
        f"source_sha={SOURCE_SHA} source_files={len(files)} identical={identical} "
        f"adapted={len(adapted)} missing={len(missing)} unexpected_drift={len(unexpected_drift)}"
    )
    for relative in adapted:
        print(f"ADAPTED {relative}: {exceptions[relative]}")
    for relative in missing:
        print(f"MISSING {relative}", file=sys.stderr)
    for relative in unexpected_drift:
        print(f"UNCLASSIFIED_DRIFT {relative}", file=sys.stderr)

    if missing or unexpected_drift:
        return 1
    print("VOLCANOES_FUNCTIONAL_PARITY status=GREEN")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (AssertionError, OSError, ValueError, json.JSONDecodeError) as exc:
        print(f"VOLCANOES_FUNCTIONAL_PARITY status=RED reason={exc}", file=sys.stderr)
        raise SystemExit(1)
