#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[2]
VALIDATOR = ROOT / "scripts/compendium/validate_ptbr_localization.py"


def write_json(path: Path, payload: dict[str, str]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(payload, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")


def run_validator(pt_br: Path, en_us: Path, source_root: Path) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        [sys.executable, str(VALIDATOR), "--pt-br", str(pt_br), "--en-us", str(en_us), "--source-root", str(source_root)],
        cwd=ROOT,
        check=False,
        capture_output=True,
        text=True,
    )


class PtBrLocalizationContractTest(unittest.TestCase):
    def fixture(self) -> tuple[tempfile.TemporaryDirectory[str], Path, Path, Path]:
        tmp = tempfile.TemporaryDirectory()
        root = Path(tmp.name)
        pt_br = root / "pt_br.json"
        en_us = root / "en_us.json"
        source_root = root / "src"
        source_root.mkdir(parents=True)
        return tmp, pt_br, en_us, source_root

    def test_matching_locales_and_referenced_key_pass(self) -> None:
        tmp, pt_br, en_us, source_root = self.fixture()
        with tmp:
            write_json(pt_br, {"screen.rpgskilltree.example": "Exemplo"})
            write_json(en_us, {"screen.rpgskilltree.example": "Example"})
            (source_root / "Example.java").write_text('Component.translatable("screen.rpgskilltree.example");\n', encoding="utf-8")
            result = run_validator(pt_br, en_us, source_root)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            self.assertIn("1 localized keys", result.stdout)

    def test_en_us_key_missing_from_pt_br_fails(self) -> None:
        tmp, pt_br, en_us, source_root = self.fixture()
        with tmp:
            write_json(pt_br, {})
            write_json(en_us, {"screen.rpgskilltree.missing": "Missing"})
            result = run_validator(pt_br, en_us, source_root)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("screen.rpgskilltree.missing", result.stderr)
            self.assertIn("pt_br", result.stderr)

    def test_pt_br_key_missing_explicit_en_us_fallback_fails(self) -> None:
        tmp, pt_br, en_us, source_root = self.fixture()
        with tmp:
            write_json(pt_br, {"screen.rpgskilltree.sem_fallback": "Sem fallback"})
            write_json(en_us, {})
            result = run_validator(pt_br, en_us, source_root)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("screen.rpgskilltree.sem_fallback", result.stderr)
            self.assertIn("en_us", result.stderr)

    def test_referenced_project_translation_key_must_exist_in_both_locales(self) -> None:
        tmp, pt_br, en_us, source_root = self.fixture()
        with tmp:
            write_json(pt_br, {})
            write_json(en_us, {})
            nested = source_root / "nested"
            nested.mkdir()
            (nested / "Example.java").write_text('return "screen.rpgskilltree.runtime_only";\n', encoding="utf-8")
            result = run_validator(pt_br, en_us, source_root)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("screen.rpgskilltree.runtime_only", result.stderr)
            self.assertIn("source", result.stderr.lower())


if __name__ == "__main__":
    unittest.main()
