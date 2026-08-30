#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
import subprocess
import sys
import tempfile
import textwrap
import unittest

ROOT = Path(__file__).resolve().parents[2]
PARSER = ROOT / "scripts/compendium/inventory_modlist.py"
PIPELINE = ROOT / "scripts/compendium/generate_inventory.py"
BACKLOG = ROOT / "scripts/compendium/editorial_backlog.py"


def fixture(declared: int = 3, suffix: str = "") -> str:
    return textwrap.dedent(
        f"""\
        Mods count: {declared}

        jar name | notes | mod id | mod name | mod version | mixin configs | modrinth hash | curseforge hash
        ---------|-------|--------|----------|-------------|---------------|---------------|----------------
        neoforge-21.1.248 (modloader) | | neoforge | NeoForge | neoforge-21.1.248 | | |
        moda-1.0.jar | | moda | Mod A | 1.0 | | hash-a | 101
            /META-INF/jarjar/lib-2.0.jar | | embeddedlib | Embedded Lib | 2.0 | | |
        modb-3.4.jar | note{suffix} | modb | Mod B | 3.4 | mixins.modb.json | hash-b | 202
        """
    )


def run_parser(input_path: Path, json_path: Path, markdown_path: Path) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        [
            sys.executable,
            str(PARSER),
            str(input_path),
            "--json",
            str(json_path),
            "--markdown",
            str(markdown_path),
        ],
        cwd=ROOT,
        check=False,
        capture_output=True,
        text=True,
    )


def runtime_fixture() -> dict:
    entries = [
        {
            "kind": "ENTITY",
            "resource_location": "minecraft:zombie",
            "namespace": "minecraft",
            "translation_key": "entity.minecraft.zombie",
            "mod_display_name": "Minecraft",
            "registry_source": "minecraft:entity_type",
            "present_at_runtime": True,
        },
        {
            "kind": "STRUCTURE",
            "resource_location": "moda:tower",
            "namespace": "moda",
            "translation_key": "structure.moda.tower",
            "mod_display_name": "Mod A",
            "registry_source": "minecraft:structure",
            "present_at_runtime": True,
        },
    ]
    return {
        "schema": 1,
        "minecraft_version": "1.21.1",
        "loader": "neoforge",
        "runtime_fingerprint_sha256": "b" * 64,
        "loaded_mods": [
            {"mod_id": "neoforge", "display_name": "NeoForge", "runtime_version": "21.1.248"},
            {"mod_id": "moda", "display_name": "Mod A", "runtime_version": "1.0"},
            {"mod_id": "modb", "display_name": "Mod B", "runtime_version": "3.4"},
        ],
        "entry_count": len(entries),
        "entries": entries,
    }


def run_pipeline(modlist: Path, runtime: Path, output: Path) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        [sys.executable, str(PIPELINE), str(modlist), str(runtime), "--output-dir", str(output)],
        cwd=ROOT,
        check=False,
        capture_output=True,
        text=True,
    )


class ModlistParserTest(unittest.TestCase):
    def test_top_level_and_embedded_are_separated(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            source = root / "modlist agora atual.txt"
            source.write_text(fixture(), encoding="utf-8")
            out_json = root / "inventory.json"
            out_md = root / "inventory.md"
            result = run_parser(source, out_json, out_md)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            payload = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(3, payload["declared_top_level_count"])
            self.assertEqual(3, payload["parsed_top_level_count"])
            self.assertEqual(["neoforge", "moda", "modb"], [entry["mod_id"] for entry in payload["top_level_mods"]])
            self.assertEqual(1, len(payload["embedded_dependencies"]))
            self.assertEqual("embeddedlib", payload["embedded_dependencies"][0]["mod_id"])
            self.assertEqual("moda", payload["embedded_dependencies"][0]["parent_mod_id"])
            self.assertRegex(payload["snapshot_sha256"], r"^[0-9a-f]{64}$")
            self.assertIn("moda", out_md.read_text(encoding="utf-8"))

    def test_embedded_path_without_leading_slash_is_not_promoted(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            source = root / "modlist.txt"
            source.write_text(fixture().replace("/META-INF/jarjar/", "META-INF/jarjar/"), encoding="utf-8")
            out_json = root / "inventory.json"
            result = run_parser(source, out_json, root / "inventory.md")
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            payload = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(3, payload["parsed_top_level_count"])
            self.assertEqual(1, payload["embedded_dependency_count"])
            self.assertEqual("embeddedlib", payload["embedded_dependencies"][0]["mod_id"])

    def test_declared_count_mismatch_fails_closed(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            source = root / "modlist.txt"
            source.write_text(fixture(declared=4), encoding="utf-8")
            result = run_parser(source, root / "out.json", root / "out.md")
            self.assertNotEqual(0, result.returncode)
            self.assertIn("declared", (result.stdout + result.stderr).lower())

    def test_snapshot_hash_detects_drift(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            first = root / "first.txt"
            second = root / "second.txt"
            first.write_text(fixture(), encoding="utf-8")
            second.write_text(fixture(suffix=" changed"), encoding="utf-8")
            self.assertEqual(0, run_parser(first, root / "a.json", root / "a.md").returncode)
            self.assertEqual(0, run_parser(second, root / "b.json", root / "b.md").returncode)
            a = json.loads((root / "a.json").read_text(encoding="utf-8"))["snapshot_sha256"]
            b = json.loads((root / "b.json").read_text(encoding="utf-8"))["snapshot_sha256"]
            self.assertNotEqual(a, b)

    def test_single_command_pipeline_generates_all_stage_outputs(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            modlist = root / "modlist agora atual.txt"
            runtime = root / "runtime-registry-inventory.json"
            output = root / "generated/compendium"
            modlist.write_text(fixture(), encoding="utf-8")
            runtime.write_text(json.dumps(runtime_fixture()), encoding="utf-8")
            result = run_pipeline(modlist, runtime, output)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            for filename in (
                "modpack-inventory.json",
                "modpack-inventory.md",
                "coverage-report.json",
                "coverage-report.md",
                "editorial-backlog.json",
                "editorial-backlog.md",
            ):
                self.assertTrue((output / filename).is_file(), filename)
            report = json.loads((output / "coverage-report.json").read_text(encoding="utf-8"))
            self.assertEqual(2, len(report["entries"]))
            self.assertEqual([], report["modlist_comparison"]["listed_but_not_loaded"])
            self.assertEqual([], report["modlist_comparison"]["loaded_but_not_listed"])
            backlog = json.loads((output / "editorial-backlog.json").read_text(encoding="utf-8"))
            self.assertEqual(2, backlog["entry_count"])
            self.assertEqual("ENTITY:minecraft:zombie", backlog["entries"][0]["entry_id"])
            self.assertEqual("STRUCTURE:moda:tower", backlog["entries"][1]["entry_id"])

    def test_editorial_backlog_preserves_malformed_runtime_error_as_blocked(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            coverage = root / "coverage-report.json"
            coverage.write_text(json.dumps({
                "schema": 1,
                "runtime_fingerprint_sha256": "c" * 64,
                "entries": [
                    {
                        "raw_value": ["malformed-runtime-row"],
                        "coverage_state": "ERROR",
                        "coverage_reason": "runtime entry is not an object",
                        "inventory_key": "ERROR|__invalid_entry_0",
                    }
                ],
            }), encoding="utf-8")
            out_json = root / "editorial-backlog.json"
            result = subprocess.run(
                [
                    sys.executable,
                    str(BACKLOG),
                    str(coverage),
                    "--json", str(out_json),
                    "--markdown", str(root / "editorial-backlog.md"),
                ],
                cwd=ROOT,
                check=False,
                capture_output=True,
                text=True,
            )
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            payload = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(1, payload["entry_count"])
            row = payload["entries"][0]
            self.assertEqual("ERROR:__invalid_entry_0", row["entry_id"])
            self.assertEqual("ERROR", row["kind"])
            self.assertEqual("__invalid__", row["source_mod"])
            self.assertEqual("ERROR", row["coverage"])
            self.assertEqual(0, row["priority"])
            self.assertTrue(row["present_at_runtime"])
            for field in (
                "ptbr_name_status",
                "summary_status",
                "full_description_status",
                "source_status",
                "review_status",
            ):
                self.assertEqual("BLOCKED", row[field])

    def test_single_command_pipeline_reuses_existing_backlog_by_default(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            modlist = root / "modlist agora atual.txt"
            runtime = root / "runtime-registry-inventory.json"
            output = root / "generated/compendium"
            modlist.write_text(fixture(), encoding="utf-8")
            runtime.write_text(json.dumps(runtime_fixture()), encoding="utf-8")

            first = run_pipeline(modlist, runtime, output)
            self.assertEqual(0, first.returncode, first.stdout + first.stderr)
            backlog_path = output / "editorial-backlog.json"
            backlog = json.loads(backlog_path.read_text(encoding="utf-8"))
            zombie = next(row for row in backlog["entries"] if row["entry_id"] == "ENTITY:minecraft:zombie")
            zombie["summary_status"] = "COMPLETE"
            zombie["review_status"] = "IN_PROGRESS"
            backlog["orphaned_entries"] = [
                {
                    "entry_id": "ENTITY:oldmod:beast",
                    "source_mod": "oldmod",
                    "kind": "ENTITY",
                    "coverage": "CURATED",
                    "priority": 20,
                    "priority_reason": "preserved legacy editorial work",
                    "ptbr_name_status": "COMPLETE",
                    "summary_status": "COMPLETE",
                    "full_description_status": "COMPLETE",
                    "source_status": "COMPLETE",
                    "review_status": "COMPLETE",
                    "present_at_runtime": False,
                }
            ]
            backlog["orphaned_entry_count"] = 1
            backlog_path.write_text(json.dumps(backlog), encoding="utf-8")

            second = run_pipeline(modlist, runtime, output)
            self.assertEqual(0, second.returncode, second.stdout + second.stderr)
            refreshed = json.loads(backlog_path.read_text(encoding="utf-8"))
            refreshed_zombie = next(
                row for row in refreshed["entries"] if row["entry_id"] == "ENTITY:minecraft:zombie"
            )
            self.assertEqual("COMPLETE", refreshed_zombie["summary_status"])
            self.assertEqual("IN_PROGRESS", refreshed_zombie["review_status"])
            self.assertEqual(
                ["ENTITY:oldmod:beast"],
                [row["entry_id"] for row in refreshed["orphaned_entries"]],
            )
            self.assertFalse(refreshed["orphaned_entries"][0]["present_at_runtime"])


if __name__ == "__main__":
    unittest.main()
