#!/usr/bin/env python3
"""Regression tests for the Stage 10 provenance contract.

The suite is intentionally stdlib-only so it can run before Gradle and can also
exercise malformed temporary repositories without importing production code.
"""

from __future__ import annotations

import json
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[2]
DOCS = ROOT / "docs/compendium"
VALIDATOR = ROOT / "scripts/compendium/validate_provenance.py"
REQUIRED_MANIFESTS = (
    DOCS / "UPSTREAM.md",
    DOCS / "PROVENANCE.md",
    DOCS / "ASSET_SOURCES.md",
)
MARKERS = {
    "UPSTREAM.md": "<!-- compendium-upstream:v1 -->",
    "PROVENANCE.md": "<!-- compendium-provenance:v1 -->",
    "ASSET_SOURCES.md": "<!-- compendium-assets:v1 -->",
}
ALLOWED_POLICIES = {
    "BEHAVIOR_REFERENCE",
    "PUBLIC_API",
    "CODE_REUSE",
    "ASSET_REUSE",
    "NO_REUSE",
}
EXPECTED_REFERENCES = {
    "biology_dictionary": {
        "source_sha": "5b70858371960d95a4ffba1ef4c1320aa94452e8",
        "observed_version": "1.2.1",
        "code_license": "LGPL-3.0-or-later",
    },
    "field_guide": {
        "source_sha": "a206cf81a4465e453b0663b0173066f30dcdc348",
        "observed_version": "1.15.2",
        "code_license": "MIT",
    },
    "wildex": {
        "source_sha": "b67267f6e664af58fe4ff430ba83c78a379029a5",
        "observed_version": "3.0.0",
        "code_license": "CC-BY-NC-4.0",
    },
}


def extract_manifest(path: Path) -> object:
    text = path.read_text(encoding="utf-8")
    marker = MARKERS[path.name]
    marker_index = text.index(marker) + len(marker)
    fenced = text[marker_index:]
    open_fence = fenced.index("```json") + len("```json")
    close_fence = fenced.index("```", open_fence)
    return json.loads(fenced[open_fence:close_fence].strip())


def replace_manifest(path: Path, data: object) -> None:
    text = path.read_text(encoding="utf-8")
    marker = MARKERS[path.name]
    marker_index = text.index(marker) + len(marker)
    open_fence = text.index("```json", marker_index) + len("```json")
    close_fence = text.index("```", open_fence)
    payload = "\n" + json.dumps(data, indent=2, ensure_ascii=False) + "\n"
    path.write_text(text[:open_fence] + payload + text[close_fence:], encoding="utf-8")


def run_validator(root: Path) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        [sys.executable, str(VALIDATOR), "--root", str(root)],
        cwd=ROOT,
        check=False,
        capture_output=True,
        text=True,
    )


class ProvenanceBootstrapTest(unittest.TestCase):
    def test_required_manifests_exist(self) -> None:
        missing = [path.relative_to(ROOT).as_posix() for path in REQUIRED_MANIFESTS if not path.is_file()]
        self.assertEqual([], missing, f"missing Stage 10 provenance manifests: {missing}")

    def test_manifests_are_machine_readable(self) -> None:
        upstream = extract_manifest(DOCS / "UPSTREAM.md")
        policy = extract_manifest(DOCS / "PROVENANCE.md")
        assets = extract_manifest(DOCS / "ASSET_SOURCES.md")

        self.assertIsInstance(upstream, list)
        self.assertIsInstance(policy, dict)
        self.assertIsInstance(assets, dict)

    def test_frozen_reference_set_is_explicit(self) -> None:
        upstream = extract_manifest(DOCS / "UPSTREAM.md")
        self.assertIsInstance(upstream, list)
        records = {entry["id"]: entry for entry in upstream}
        self.assertEqual(set(EXPECTED_REFERENCES), set(records))
        for upstream_id, expected in EXPECTED_REFERENCES.items():
            record = records[upstream_id]
            self.assertEqual(expected["source_sha"], record["source_sha"], upstream_id)
            self.assertEqual(expected["observed_version"], record["observed_version"], upstream_id)
            self.assertEqual(expected["code_license"], record["code_license"], upstream_id)
            self.assertRegex(record["source_sha"], r"^[0-9a-f]{40}$", upstream_id)
            self.assertIn(record["code_reuse_policy"], ALLOWED_POLICIES, upstream_id)
            self.assertIn(record["asset_reuse_policy"], ALLOWED_POLICIES, upstream_id)

    def test_validator_exists(self) -> None:
        self.assertTrue(
            VALIDATOR.is_file(),
            "Stage 10.01 requires scripts/compendium/validate_provenance.py",
        )


@unittest.skipUnless(VALIDATOR.is_file(), "validator not implemented yet")
class ProvenanceValidatorTest(unittest.TestCase):
    def make_fixture(self) -> tuple[tempfile.TemporaryDirectory[str], Path]:
        holder = tempfile.TemporaryDirectory()
        root = Path(holder.name)
        shutil.copytree(DOCS, root / "docs/compendium")
        (root / "src/main/resources/assets/rpgskilltree/compendium").mkdir(parents=True)
        (root / "src/main/resources/data/rpgskilltree/compendium/editorial").mkdir(parents=True)
        return holder, root

    def test_clean_repository_passes(self) -> None:
        result = run_validator(ROOT)
        self.assertEqual(0, result.returncode, result.stdout + result.stderr)
        self.assertIn("PASS", result.stdout)

    def test_unlisted_compendium_asset_fails(self) -> None:
        holder, root = self.make_fixture()
        with holder:
            asset = root / "src/main/resources/assets/rpgskilltree/compendium/untracked.txt"
            asset.write_text("not registered", encoding="utf-8")
            result = run_validator(root)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("untracked asset", (result.stdout + result.stderr).lower())

    def test_imported_editorial_content_without_provenance_fails(self) -> None:
        holder, root = self.make_fixture()
        with holder:
            editorial = root / "src/main/resources/data/rpgskilltree/compendium/editorial/example.json"
            editorial.write_text(
                json.dumps({"origin": "imported", "text": "external text"}),
                encoding="utf-8",
            )
            result = run_validator(root)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("imported editorial", (result.stdout + result.stderr).lower())

    def test_floating_or_malformed_upstream_sha_fails(self) -> None:
        holder, root = self.make_fixture()
        with holder:
            path = root / "docs/compendium/UPSTREAM.md"
            upstream = extract_manifest(path)
            self.assertIsInstance(upstream, list)
            upstream[0]["source_sha"] = "main-architectury-1.21.1"
            replace_manifest(path, upstream)
            result = run_validator(root)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("source_sha", result.stdout + result.stderr)

    def test_derived_asset_requires_frozen_source_sha(self) -> None:
        holder, root = self.make_fixture()
        with holder:
            asset = root / "src/main/resources/assets/rpgskilltree/compendium/derived.txt"
            asset.write_text("derived fixture", encoding="utf-8")
            manifest_path = root / "docs/compendium/ASSET_SOURCES.md"
            manifest = extract_manifest(manifest_path)
            self.assertIsInstance(manifest, dict)
            manifest["assets"] = [
                {
                    "path": asset.relative_to(root).as_posix(),
                    "origin": "DERIVED",
                    "author": "Fixture Author",
                    "license": "MIT",
                    "source": "https://example.invalid/upstream",
                    "source_sha": "main",
                    "notes": "This deliberately uses a floating source ref.",
                }
            ]
            replace_manifest(manifest_path, manifest)
            result = run_validator(root)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("source_sha", result.stdout + result.stderr)


if __name__ == "__main__":
    unittest.main()