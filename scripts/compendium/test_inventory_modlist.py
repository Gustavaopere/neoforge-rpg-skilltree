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


if __name__ == "__main__":
    unittest.main()
