#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[2]
VALIDATOR = ROOT / "scripts/compendium/validate_editorial_corpus.py"
COVERAGE = ROOT / "scripts/compendium/editorial_coverage.py"


def coverage_fixture() -> dict:
    entries = [
        {
            "kind": "ENTITY",
            "resource_location": "minecraft:zombie",
            "namespace": "minecraft",
            "coverage_state": "CURATED",
            "coverage_reason": "fixture",
            "inventory_key": "ENTITY|minecraft:zombie",
            "present_at_runtime": True,
        },
        {
            "kind": "ENTITY",
            "resource_location": "minecraft:skeleton",
            "namespace": "minecraft",
            "coverage_state": "AUTO",
            "coverage_reason": "fixture",
            "inventory_key": "ENTITY|minecraft:skeleton",
            "present_at_runtime": True,
        },
        {
            "kind": "BIOME",
            "resource_location": "moda:ashen_grove",
            "namespace": "moda",
            "coverage_state": "AUTO",
            "coverage_reason": "fixture",
            "inventory_key": "BIOME|moda:ashen_grove",
            "present_at_runtime": True,
        },
        {
            "kind": "ERROR",
            "coverage_state": "ERROR",
            "coverage_reason": "malformed fixture",
            "inventory_key": "ERROR|__invalid_entry_0",
            "present_at_runtime": True,
        },
    ]
    return {
        "schema": 1,
        "runtime_fingerprint_sha256": "a" * 64,
        "entries": entries,
        "coverage_totals": {"AUTO": 2, "CURATED": 1, "ADAPTER": 0, "IGNORED": 0, "ERROR": 1},
    }


def source(ref: str = "minecraft:entity_type/minecraft:zombie") -> dict:
    return {"type": "RUNTIME", "ref": ref}


def entry(
    entry_id: str = "ENTITY:minecraft:zombie",
    *,
    title: str = "Zumbi",
    summary: str = "Criatura hostil registrada no catálogo técnico.",
    review_status: str = "REVIEWED",
    availability: str = "RUNTIME",
    availability_reason: str | None = None,
    references: list[str] | None = None,
) -> dict:
    result = {
        "entry_id": entry_id,
        "title": title,
        "summary": {"text": summary, "sources": [source()]},
        "sections": {
            "behavior": {
                "text": "Seu comportamento detalhado deve ser sustentado por fonte explícita.",
                "sources": [source()],
            }
        },
        "references": references or [],
        "review_status": review_status,
        "availability": availability,
    }
    if availability_reason is not None:
        result["availability_reason"] = availability_reason
    return result


def pack(entries: list[dict] | None = None, *, namespace: str = "minecraft", kind: str = "ENTITY", language: str = "pt_br") -> dict:
    return {
        "schema": 1,
        "language": language,
        "namespace": namespace,
        "kind": kind,
        "entries": entries or [entry()],
    }


def write_json(path: Path, payload: dict) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(payload, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")


def run_validator(corpus: Path, coverage: Path, *extra: str) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        [sys.executable, str(VALIDATOR), str(corpus), "--coverage", str(coverage), *extra],
        cwd=ROOT,
        check=False,
        capture_output=True,
        text=True,
    )


def backlog_fixture() -> dict:
    return {
        "schema": 1,
        "language": "pt_br",
        "entries": [
            {"entry_id": "ENTITY:minecraft:zombie", "source_mod": "minecraft", "kind": "ENTITY", "coverage": "CURATED"},
            {"entry_id": "ENTITY:minecraft:skeleton", "source_mod": "minecraft", "kind": "ENTITY", "coverage": "AUTO"},
            {"entry_id": "BIOME:moda:ashen_grove", "source_mod": "moda", "kind": "BIOME", "coverage": "AUTO"},
            {"entry_id": "ERROR:__invalid_entry_0", "source_mod": "__invalid__", "kind": "ERROR", "coverage": "ERROR"},
        ],
        "orphaned_entries": [],
    }


class EditorialCorpusContractTest(unittest.TestCase):
    def fixture_root(self) -> tuple[tempfile.TemporaryDirectory[str], Path, Path, Path]:
        tmp = tempfile.TemporaryDirectory()
        root = Path(tmp.name)
        corpus = root / "editorial/pt_br"
        coverage_path = root / "coverage-report.json"
        write_json(coverage_path, coverage_fixture())
        return tmp, root, corpus, coverage_path

    def test_valid_corpus_accepts_multiple_namespace_packages(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/entities.json", pack())
            write_json(
                corpus / "moda/biomes.json",
                pack(
                    [
                        entry(
                            "BIOME:moda:ashen_grove",
                            title="Bosque Cinzento",
                            summary="Bioma registrado no catálogo runtime do pacote.",
                        )
                    ],
                    namespace="moda",
                    kind="BIOME",
                ),
            )
            result = run_validator(corpus, coverage_path, "--release")
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            self.assertIn("2 entries", result.stdout)

    def test_language_must_be_pt_br(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/entities.json", pack(language="en_us"))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("pt_br", result.stderr)

    def test_placeholder_text_is_rejected(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/entities.json", pack([entry(summary="TODO: escrever depois")]))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("placeholder", result.stderr.lower())

    def test_source_reference_placeholder_is_rejected(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            broken = entry()
            broken["summary"]["sources"][0]["ref"] = "..."
            write_json(corpus / "minecraft/entities.json", pack([broken]))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("placeholder", result.stderr.lower())

    def test_prose_requires_explicit_sources(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            broken = entry()
            broken["sections"]["behavior"]["sources"] = []
            write_json(corpus / "minecraft/entities.json", pack([broken]))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("sources", result.stderr.lower())

    def test_duplicate_entry_ids_across_files_fail(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/a.json", pack())
            write_json(corpus / "minecraft/b.json", pack())
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("duplicate", result.stderr.lower())

    def test_package_namespace_and_kind_must_match_entry_id(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/entities.json", pack([entry("BIOME:moda:ashen_grove")]))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            combined = (result.stdout + result.stderr).lower()
            self.assertTrue("namespace" in combined or "kind" in combined)

    def test_package_directory_must_match_declared_namespace(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "wrong/entities.json", pack())
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("directory namespace", result.stderr.lower())

    def test_availability_is_required(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            broken = entry()
            del broken["availability"]
            write_json(corpus / "minecraft/entities.json", pack([broken]))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("availability", result.stderr.lower())

    def test_runtime_absent_entry_requires_optional_or_legacy_reason(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "moda/entities.json", pack([entry("ENTITY:moda:removed_beast")], namespace="moda"))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("runtime", result.stderr.lower())

            write_json(
                corpus / "moda/entities.json",
                pack(
                    [
                        entry(
                            "ENTITY:moda:removed_beast",
                            availability="LEGACY",
                            availability_reason="Conteúdo documentado apenas para migração de mundos antigos.",
                        )
                    ],
                    namespace="moda",
                ),
            )
            result = run_validator(corpus, coverage_path)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)

    def test_optional_or_legacy_must_not_mask_runtime_content(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(
                corpus / "minecraft/entities.json",
                pack(
                    [
                        entry(
                            availability="LEGACY",
                            availability_reason="Não deve ocultar conteúdo presente no runtime.",
                        )
                    ]
                ),
            )
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("runtime", result.stderr.lower())

    def test_internal_references_must_resolve_to_runtime_or_corpus_entry(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/entities.json", pack([entry(references=["ENTITY:minecraft:skeleton"])]))
            result = run_validator(corpus, coverage_path)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)

            write_json(corpus / "minecraft/entities.json", pack([entry(references=["ENTITY:minecraft:not_real"])]))
            result = run_validator(corpus, coverage_path)
            self.assertNotEqual(0, result.returncode)
            self.assertIn("reference", result.stderr.lower())

    def test_release_mode_rejects_draft_entries(self) -> None:
        tmp, _, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/entities.json", pack([entry(review_status="DRAFT")]))
            self.assertEqual(0, run_validator(corpus, coverage_path).returncode)
            result = run_validator(corpus, coverage_path, "--release")
            self.assertNotEqual(0, result.returncode)
            self.assertIn("reviewed", result.stderr.lower())

    def test_coverage_report_counts_reviewed_draft_missing_and_blocked(self) -> None:
        tmp, root, corpus, coverage_path = self.fixture_root()
        with tmp:
            write_json(corpus / "minecraft/entities.json", pack())
            backlog_path = root / "editorial-backlog.json"
            write_json(backlog_path, backlog_fixture())
            out_json = root / "editorial-coverage.json"
            out_md = root / "editorial-coverage.md"
            result = subprocess.run(
                [
                    sys.executable,
                    str(COVERAGE),
                    str(corpus),
                    str(backlog_path),
                    "--coverage",
                    str(coverage_path),
                    "--json",
                    str(out_json),
                    "--markdown",
                    str(out_md),
                ],
                cwd=ROOT,
                check=False,
                capture_output=True,
                text=True,
            )
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            report = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(1, report["totals"]["reviewed"])
            self.assertEqual(0, report["totals"]["draft"])
            self.assertEqual(2, report["totals"]["missing"])
            self.assertEqual(1, report["totals"]["blocked"])
            self.assertEqual(2, report["namespaces"]["minecraft"]["expected"])
            self.assertEqual(1, report["namespaces"]["minecraft"]["reviewed"])
            self.assertIn("minecraft", out_md.read_text(encoding="utf-8"))

    def test_coverage_report_accepts_empty_corpus_as_all_missing(self) -> None:
        tmp, root, corpus, coverage_path = self.fixture_root()
        with tmp:
            corpus.mkdir(parents=True, exist_ok=True)
            backlog_path = root / "editorial-backlog.json"
            write_json(backlog_path, backlog_fixture())
            out_json = root / "editorial-coverage.json"
            result = subprocess.run(
                [
                    sys.executable,
                    str(COVERAGE),
                    str(corpus),
                    str(backlog_path),
                    "--coverage",
                    str(coverage_path),
                    "--json",
                    str(out_json),
                    "--markdown",
                    str(root / "editorial-coverage.md"),
                ],
                cwd=ROOT,
                check=False,
                capture_output=True,
                text=True,
            )
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            report = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(0, report["totals"]["reviewed"])
            self.assertEqual(3, report["totals"]["missing"])
            self.assertEqual(1, report["totals"]["blocked"])

    def test_coverage_report_rejects_backlog_entries_absent_from_current_coverage(self) -> None:
        tmp, root, corpus, coverage_path = self.fixture_root()
        with tmp:
            corpus.mkdir(parents=True, exist_ok=True)
            backlog = backlog_fixture()
            backlog["entries"][1] = {
                "entry_id": "ENTITY:minecraft:creeper",
                "source_mod": "minecraft",
                "kind": "ENTITY",
                "coverage": "AUTO",
            }
            backlog_path = root / "editorial-backlog.json"
            write_json(backlog_path, backlog)
            result = subprocess.run(
                [
                    sys.executable,
                    str(COVERAGE),
                    str(corpus),
                    str(backlog_path),
                    "--coverage",
                    str(coverage_path),
                    "--json",
                    str(root / "editorial-coverage.json"),
                    "--markdown",
                    str(root / "editorial-coverage.md"),
                ],
                cwd=ROOT,
                check=False,
                capture_output=True,
                text=True,
            )
            self.assertNotEqual(0, result.returncode)
            self.assertIn("coverage", result.stderr.lower())

    def test_coverage_report_accepts_error_row_with_registry_inventory_key(self) -> None:
        tmp, root, corpus, coverage_path = self.fixture_root()
        with tmp:
            corpus.mkdir(parents=True, exist_ok=True)
            coverage = coverage_fixture()
            coverage["entries"][0] = {
                "kind": "ENTITY",
                "resource_location": "minecraft:zombie",
                "namespace": "minecraft",
                "coverage_state": "ERROR",
                "coverage_reason": "missing translation key",
                "inventory_key": "ENTITY|minecraft:zombie",
                "present_at_runtime": True,
            }
            coverage["coverage_totals"] = {"AUTO": 1, "CURATED": 0, "ADAPTER": 0, "IGNORED": 0, "ERROR": 2}
            write_json(coverage_path, coverage)

            backlog = backlog_fixture()
            backlog["entries"][0] = {
                "entry_id": "ENTITY:minecraft:zombie",
                "source_mod": "minecraft",
                "kind": "ENTITY",
                "coverage": "ERROR",
            }
            backlog_path = root / "editorial-backlog.json"
            write_json(backlog_path, backlog)
            out_json = root / "editorial-coverage.json"
            result = subprocess.run(
                [
                    sys.executable,
                    str(COVERAGE),
                    str(corpus),
                    str(backlog_path),
                    "--coverage",
                    str(coverage_path),
                    "--json",
                    str(out_json),
                    "--markdown",
                    str(root / "editorial-coverage.md"),
                ],
                cwd=ROOT,
                check=False,
                capture_output=True,
                text=True,
            )
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            report = json.loads(out_json.read_text(encoding="utf-8"))
            self.assertEqual(2, report["totals"]["blocked"])
            self.assertEqual(1, report["namespaces"]["minecraft"]["blocked"])


if __name__ == "__main__":
    unittest.main()
