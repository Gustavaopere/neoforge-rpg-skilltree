#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[2]
REPORTER = ROOT / "scripts/compendium/inventory_runtime_report.py"


def runtime_fixture(entries: list[dict], mods: list[dict] | None = None) -> dict:
    return {
        "schema": 1,
        "minecraft_version": "1.21.1",
        "loader": "neoforge",
        "runtime_fingerprint_sha256": "a" * 64,
        "loaded_mods": mods or [
            {"mod_id": "minecraft", "display_name": "Minecraft", "runtime_version": "1.21.1"},
            {"mod_id": "rpgskilltree", "display_name": "RPG Skill Tree", "runtime_version": "0-test"},
        ],
        "entry_count": len(entries),
        "entries": entries,
    }


def entry(kind: str, resource_location: str, mod_name: str | None = None) -> dict:
    namespace = resource_location.split(":", 1)[0]
    return {
        "kind": kind,
        "resource_location": resource_location,
        "namespace": namespace,
        "translation_key": f"{kind.lower()}.{namespace}.{resource_location.split(':', 1)[1]}",
        "mod_display_name": mod_name or namespace,
        "registry_source": f"minecraft:{kind.lower()}",
        "present_at_runtime": True,
    }


def run_reporter(runtime: Path, out_json: Path, out_md: Path, *, overrides: Path | None = None, previous: Path | None = None):
    command = [
        sys.executable,
        str(REPORTER),
        str(runtime),
        "--json", str(out_json),
        "--markdown", str(out_md),
    ]
    if overrides is not None:
        command.extend(["--overrides", str(overrides)])
    if previous is not None:
        command.extend(["--previous", str(previous)])
    return subprocess.run(command, cwd=ROOT, check=False, capture_output=True, text=True)


class RuntimeCoverageReportTest(unittest.TestCase):
    def test_every_runtime_entry_receives_exactly_one_coverage_state(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            runtime = root / "runtime.json"
            runtime.write_text(json.dumps(runtime_fixture([
                entry("ENTITY", "minecraft:zombie", "Minecraft"),
                entry("BIOME", "minecraft:plains", "Minecraft"),
                entry("STRUCTURE", "futuremod:tower", "Future Mod"),
            ])), encoding="utf-8")
            out_json = root / "coverage.json"
            out_md = root / "coverage.md"
            result = run_reporter(runtime, out_json, out_md)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            payload = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(3, len(payload["entries"]))
            self.assertEqual({"AUTO"}, {item["coverage_state"] for item in payload["entries"]})
            self.assertEqual(0, payload["coverage_totals"]["ERROR"])
            self.assertIn("futuremod", out_md.read_text(encoding="utf-8"))

    def test_ignored_without_reason_fails_validation(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            runtime = root / "runtime.json"
            runtime.write_text(json.dumps(runtime_fixture([
                entry("STRUCTURE", "minecolonies:work_camp", "MineColonies"),
            ])), encoding="utf-8")
            overrides = root / "overrides.json"
            overrides.write_text(json.dumps({
                "schema": 1,
                "overrides": {
                    "STRUCTURE|minecolonies:work_camp": {"state": "IGNORED", "reason": ""}
                },
            }), encoding="utf-8")
            result = run_reporter(runtime, root / "coverage.json", root / "coverage.md", overrides=overrides)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("ignored", (result.stdout + result.stderr).lower())

    def test_malformed_runtime_entry_is_error_not_silently_dropped(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            malformed = entry("ENTITY", "futuremod:creature")
            malformed["translation_key"] = ""
            runtime = root / "runtime.json"
            runtime.write_text(json.dumps(runtime_fixture([malformed])), encoding="utf-8")
            out_json = root / "coverage.json"
            result = run_reporter(runtime, out_json, root / "coverage.md")
            self.assertEqual(2, result.returncode, result.stdout + result.stderr)
            payload = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual("ERROR", payload["entries"][0]["coverage_state"])
            self.assertTrue(payload["entries"][0]["coverage_reason"])

    def test_previous_snapshot_reports_added_removed_and_orphaned_entries(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            previous = root / "previous.json"
            previous.write_text(json.dumps(runtime_fixture(
                [entry("ENTITY", "minecraft:zombie"), entry("ENTITY", "oldmod:beast", "Old Mod")],
                mods=[
                    {"mod_id": "minecraft", "display_name": "Minecraft", "runtime_version": "1.21.1"},
                    {"mod_id": "oldmod", "display_name": "Old Mod", "runtime_version": "1"},
                ],
            )), encoding="utf-8")
            current = root / "current.json"
            current.write_text(json.dumps(runtime_fixture(
                [entry("ENTITY", "minecraft:zombie"), entry("FLORA", "newmod:flower", "New Mod")],
                mods=[
                    {"mod_id": "minecraft", "display_name": "Minecraft", "runtime_version": "1.21.1"},
                    {"mod_id": "newmod", "display_name": "New Mod", "runtime_version": "2"},
                ],
            )), encoding="utf-8")
            out_json = root / "coverage.json"
            result = run_reporter(current, out_json, root / "coverage.md", previous=previous)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            payload = json.loads(out_json.read_text(encoding="utf-8"))
            drift = payload["drift"]
            self.assertEqual(["newmod"], drift["added_mods"])
            self.assertEqual(["oldmod"], drift["removed_mods"])
            self.assertEqual(["FLORA|newmod:flower"], drift["added_registry_entries"])
            self.assertEqual(["ENTITY|oldmod:beast"], drift["removed_registry_entries"])
            self.assertEqual(["ENTITY|oldmod:beast"], drift["orphaned_registry_entries"])

    def test_namespace_summary_has_all_required_columns(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            runtime = root / "runtime.json"
            runtime.write_text(json.dumps(runtime_fixture([
                entry("ENTITY", "minecraft:zombie", "Minecraft"),
                entry("FLORA", "minecraft:dandelion", "Minecraft"),
                entry("TREE", "minecraft:oak_sapling", "Minecraft"),
                entry("CROP", "minecraft:wheat", "Minecraft"),
                entry("BIOME", "minecraft:plains", "Minecraft"),
                entry("STRUCTURE", "minecraft:village_plains", "Minecraft"),
                entry("DIMENSION", "minecraft:overworld", "Minecraft"),
            ])), encoding="utf-8")
            out_json = root / "coverage.json"
            result = run_reporter(runtime, out_json, root / "coverage.md")
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            row = json.loads(out_json.read_text(encoding="utf-8"))["namespace_summary"][0]
            for key in ("namespace", "mod", "entities", "flora", "trees", "crops", "biomes", "structures", "dimensions", "AUTO", "CURATED", "ADAPTER", "IGNORED", "ERROR"):
                self.assertIn(key, row)
            self.assertEqual(1, row["entities"])
            self.assertEqual(7, row["AUTO"])


if __name__ == "__main__":
    unittest.main()
