#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[2]
GENERATOR = ROOT / "scripts/compendium/editorial_backlog.py"


def coverage_entry(kind: str, resource_location: str, coverage: str = "AUTO") -> dict:
    namespace = resource_location.split(":", 1)[0]
    return {
        "kind": kind,
        "resource_location": resource_location,
        "namespace": namespace,
        "translation_key": f"{kind.lower()}.{namespace}.{resource_location.split(':', 1)[1]}",
        "mod_display_name": namespace,
        "registry_source": f"minecraft:{kind.lower()}",
        "present_at_runtime": True,
        "coverage_state": coverage,
        "coverage_reason": "test fixture",
        "inventory_key": f"{kind}|{resource_location}",
    }


def coverage_fixture(entries: list[dict]) -> dict:
    return {
        "schema": 1,
        "minecraft_version": "1.21.1",
        "loader": "neoforge",
        "runtime_fingerprint_sha256": "a" * 64,
        "loaded_mods": [],
        "entries": entries,
        "coverage_totals": {},
        "namespace_summary": [],
        "drift": {
            "added_mods": [],
            "removed_mods": [],
            "added_registry_entries": [],
            "removed_registry_entries": [],
            "orphaned_registry_entries": [],
        },
    }


def run_generator(
    coverage: Path,
    out_json: Path,
    out_md: Path,
    *,
    previous: Path | None = None,
    priority_overrides: Path | None = None,
):
    command = [
        sys.executable,
        str(GENERATOR),
        str(coverage),
        "--json", str(out_json),
        "--markdown", str(out_md),
    ]
    if previous is not None:
        command.extend(["--previous", str(previous)])
    if priority_overrides is not None:
        command.extend(["--priority-overrides", str(priority_overrides)])
    return subprocess.run(command, cwd=ROOT, check=False, capture_output=True, text=True)


class EditorialBacklogTest(unittest.TestCase):
    def test_generates_required_fields_and_provider_neutral_default_priorities(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            coverage = root / "coverage.json"
            coverage.write_text(json.dumps(coverage_fixture([
                coverage_entry("FLORA", "botania:mystical_flower"),
                coverage_entry("STRUCTURE", "yungsapi:sample_structure"),
                coverage_entry("BIOME", "biomesoplenty:redwood_forest"),
                coverage_entry("TREE", "examplemod:oak"),
                coverage_entry("ENTITY", "minecraft:zombie"),
            ])), encoding="utf-8")

            out_json = root / "backlog.json"
            out_md = root / "backlog.md"
            result = run_generator(coverage, out_json, out_md)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)

            payload = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(1, payload["schema"])
            self.assertEqual("pt_br", payload["language"])
            self.assertEqual(5, payload["entry_count"])
            self.assertEqual([], payload["orphaned_entries"])
            self.assertEqual(
                [
                    "ENTITY:minecraft:zombie",
                    "BIOME:biomesoplenty:redwood_forest",
                    "STRUCTURE:yungsapi:sample_structure",
                    "FLORA:botania:mystical_flower",
                    "TREE:examplemod:oak",
                ],
                [entry["entry_id"] for entry in payload["entries"]],
            )
            self.assertEqual([10, 50, 60, 70, 70], [entry["priority"] for entry in payload["entries"]])

            required = {
                "entry_id", "source_mod", "kind", "coverage", "priority",
                "ptbr_name_status", "summary_status", "full_description_status",
                "source_status", "review_status", "present_at_runtime",
            }
            for entry in payload["entries"]:
                self.assertTrue(required.issubset(entry))
                self.assertEqual("PENDING", entry["ptbr_name_status"])
                self.assertEqual("PENDING", entry["summary_status"])
                self.assertEqual("PENDING", entry["full_description_status"])
                self.assertEqual("PENDING", entry["source_status"])
                self.assertEqual("PENDING", entry["review_status"])
                self.assertTrue(entry["present_at_runtime"])

            markdown = out_md.read_text(encoding="utf-8")
            self.assertIn("# Compêndio Natural — Backlog editorial pt-BR", markdown)
            self.assertIn("`ENTITY:minecraft:zombie`", markdown)

    def test_permanently_excluded_providers_fail_closed(self) -> None:
        for namespace in ("tfc", "terrafirmacraft"):
            with self.subTest(namespace=namespace), tempfile.TemporaryDirectory() as tmp:
                root = Path(tmp)
                coverage = root / "coverage.json"
                coverage.write_text(json.dumps(coverage_fixture([
                    coverage_entry("TREE", f"{namespace}:oak"),
                ])), encoding="utf-8")
                result = run_generator(coverage, root / "backlog.json", root / "backlog.md")
                self.assertNotEqual(0, result.returncode)
                output = (result.stdout + result.stderr).lower()
                self.assertIn("permanently excluded provider namespace", output)
                self.assertIn(namespace, output)

    def test_ignored_and_error_entries_are_fail_closed(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            coverage = root / "coverage.json"
            coverage.write_text(json.dumps(coverage_fixture([
                coverage_entry("FLORA", "decorative:grass", "IGNORED"),
                coverage_entry("ENTITY", "broken:creature", "ERROR"),
            ])), encoding="utf-8")
            out_json = root / "backlog.json"
            result = run_generator(coverage, out_json, root / "backlog.md")
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            entries = {entry["entry_id"]: entry for entry in json.loads(out_json.read_text(encoding="utf-8"))["entries"]}

            ignored = entries["FLORA:decorative:grass"]
            for field in ("ptbr_name_status", "summary_status", "full_description_status", "source_status", "review_status"):
                self.assertEqual("NOT_REQUIRED", ignored[field])

            error = entries["ENTITY:broken:creature"]
            self.assertEqual(0, error["priority"])
            for field in ("ptbr_name_status", "summary_status", "full_description_status", "source_status", "review_status"):
                self.assertEqual("BLOCKED", error[field])

    def test_previous_backlog_preserves_progress_and_removed_entries_as_orphans(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            coverage = root / "coverage.json"
            coverage.write_text(json.dumps(coverage_fixture([
                coverage_entry("ENTITY", "minecraft:wolf"),
                coverage_entry("FLORA", "newmod:flower"),
            ])), encoding="utf-8")
            previous = root / "previous.json"
            previous.write_text(json.dumps({
                "schema": 1,
                "language": "pt_br",
                "entries": [
                    {
                        "entry_id": "ENTITY:minecraft:wolf",
                        "source_mod": "minecraft",
                        "kind": "ENTITY",
                        "coverage": "CURATED",
                        "priority": 10,
                        "ptbr_name_status": "COMPLETE",
                        "summary_status": "COMPLETE",
                        "full_description_status": "IN_PROGRESS",
                        "source_status": "COMPLETE",
                        "review_status": "PENDING",
                        "present_at_runtime": True,
                    },
                    {
                        "entry_id": "ENTITY:oldmod:beast",
                        "source_mod": "oldmod",
                        "kind": "ENTITY",
                        "coverage": "CURATED",
                        "priority": 20,
                        "ptbr_name_status": "COMPLETE",
                        "summary_status": "COMPLETE",
                        "full_description_status": "COMPLETE",
                        "source_status": "COMPLETE",
                        "review_status": "COMPLETE",
                        "present_at_runtime": True,
                    },
                ],
                "orphaned_entries": [],
            }), encoding="utf-8")

            out_json = root / "backlog.json"
            result = run_generator(coverage, out_json, root / "backlog.md", previous=previous)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            payload = json.loads(out_json.read_text(encoding="utf-8"))
            active = {entry["entry_id"]: entry for entry in payload["entries"]}
            wolf = active["ENTITY:minecraft:wolf"]
            self.assertEqual("COMPLETE", wolf["ptbr_name_status"])
            self.assertEqual("COMPLETE", wolf["summary_status"])
            self.assertEqual("IN_PROGRESS", wolf["full_description_status"])
            self.assertEqual("PENDING", active["FLORA:newmod:flower"]["summary_status"])

            self.assertEqual(["ENTITY:oldmod:beast"], [entry["entry_id"] for entry in payload["orphaned_entries"]])
            orphan = payload["orphaned_entries"][0]
            self.assertFalse(orphan["present_at_runtime"])
            self.assertEqual("COMPLETE", orphan["review_status"])

    def test_priority_override_requires_reason_and_runtime_target(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            coverage = root / "coverage.json"
            coverage.write_text(json.dumps(coverage_fixture([
                coverage_entry("ENTITY", "bossmod:ancient_one"),
            ])), encoding="utf-8")

            overrides = root / "priorities.json"
            overrides.write_text(json.dumps({
                "schema": 1,
                "overrides": {
                    "ENTITY|bossmod:ancient_one": {"priority": 20, "reason": "Boss de progressão confirmado"}
                },
            }), encoding="utf-8")
            out_json = root / "backlog.json"
            result = run_generator(coverage, out_json, root / "backlog.md", priority_overrides=overrides)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            self.assertEqual(20, json.loads(out_json.read_text(encoding="utf-8"))["entries"][0]["priority"])

            invalid = root / "invalid-priorities.json"
            invalid.write_text(json.dumps({
                "schema": 1,
                "overrides": {
                    "ENTITY|bossmod:missing": {"priority": 20, "reason": "Não existe no runtime"}
                },
            }), encoding="utf-8")
            failed = run_generator(coverage, root / "invalid.json", root / "invalid.md", priority_overrides=invalid)
            self.assertNotEqual(0, failed.returncode)
            self.assertIn("absent", (failed.stdout + failed.stderr).lower())


if __name__ == "__main__":
    unittest.main()
