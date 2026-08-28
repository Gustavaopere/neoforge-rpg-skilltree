#!/usr/bin/env python3
"""Regression tests for the Stage 10 provenance contract.

This file intentionally starts with the smallest observable contract: the three
machine-auditable provenance manifests required by plan 10.01 must exist.
Further validator behavior is added red-green as the implementation grows.
"""

from pathlib import Path
import unittest


ROOT = Path(__file__).resolve().parents[2]
REQUIRED_MANIFESTS = (
    ROOT / "docs/compendium/UPSTREAM.md",
    ROOT / "docs/compendium/PROVENANCE.md",
    ROOT / "docs/compendium/ASSET_SOURCES.md",
)


class ProvenanceBootstrapTest(unittest.TestCase):
    def test_required_manifests_exist(self) -> None:
        missing = [path.relative_to(ROOT).as_posix() for path in REQUIRED_MANIFESTS if not path.is_file()]
        self.assertEqual([], missing, f"missing Stage 10 provenance manifests: {missing}")


if __name__ == "__main__":
    unittest.main()
