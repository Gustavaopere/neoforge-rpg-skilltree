#!/usr/bin/env python3
import json
import sys
import tempfile
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))

from wiki_catalog import (
    WikiCatalogDriftError,
    build_catalog_sections,
    build_content_coverage,
    build_semantic_combat_documents,
    build_semantic_combat_section,
    replace_generated_block,
    update_catalog_documents,
)


class WikiCatalogContractTest(unittest.TestCase):
    def test_generated_block_replacement_preserves_manual_editorial_content(self):
        original = """# Perks\n\nIntrodução manual.\n\n<!-- rpgskilltree:generated:perk-catalog:start -->\nconteúdo antigo\n<!-- rpgskilltree:generated:perk-catalog:end -->\n\n## Trivia\n\nEste texto editorial não pode ser alterado.\n"""
        updated = replace_generated_block(original, "perk-catalog", "novo catálogo\n")
        self.assertTrue(updated.startswith("# Perks\n\nIntrodução manual."))
        self.assertIn("novo catálogo\n", updated)
        self.assertTrue(updated.endswith("## Trivia\n\nEste texto editorial não pode ser alterado.\n"))
        self.assertNotIn("conteúdo antigo", updated)

    def test_generated_block_replacement_fails_closed_for_missing_or_duplicate_markers(self):
        with self.assertRaises(ValueError):
            replace_generated_block("# Sem marcadores\n", "perk-catalog", "novo\n")
        duplicate = """<!-- rpgskilltree:generated:perk-catalog:start -->\na\n<!-- rpgskilltree:generated:perk-catalog:end -->\n<!-- rpgskilltree:generated:perk-catalog:start -->\nb\n<!-- rpgskilltree:generated:perk-catalog:end -->\n"""
        with self.assertRaises(ValueError):
            replace_generated_block(duplicate, "perk-catalog", "novo\n")

    def test_historical_catalog_remains_separate_from_semantic_combat_tree(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)
            perk_catalog, effect_catalog = build_catalog_sections(root, locale="pt_br")
            self.assertIn("Treino com Espadas I", perk_catalog)
            self.assertIn("Aumenta o dano com espadas.", perk_catalog)
            self.assertIn("`rpgskilltree:martial_000`", perk_catalog)
            self.assertNotIn("`rpgskilltree:combat/a0021`", perk_catalog)
            self.assertIn("`rpgskilltree:node/martial_000/sword_damage`", effect_catalog)
            self.assertIn("BEHAVIOR_HANDLER", effect_catalog)

    def test_semantic_combat_snapshot_never_infers_policy_text_or_effects(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)
            combat_catalog = build_semantic_combat_section(root, required=True)
            self.assertIn("`rpgskilltree:runtime/combat_perks`", combat_catalog)
            self.assertIn("`rpgskilltree:combat/a0001`", combat_catalog)
            self.assertIn("+3% de dano com espadas por rank, máximo +9%.", combat_catalog)
            self.assertIn("Mastery `epicfight:sword` ≥ 60", combat_catalog)
            self.assertIn("`rpgskilltree:combat/a0021`", combat_catalog)
            a0021 = next(line for line in combat_catalog.splitlines() if "`rpgskilltree:combat/a0021`" in line)
            self.assertIn("Precisão com Adagas", a0021)
            self.assertIn(" | — | 3 | 1 | ", a0021)
            self.assertTrue(a0021.endswith(" | — |"))
            self.assertNotIn("chance de crítico", a0021.lower())

    def test_semantic_combat_documents_are_exact_ten_perk_shards(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_full_semantic_fixture(root)
            documents = build_semantic_combat_documents(root)
            self.assertEqual(10, len(documents))
            self.assertEqual(Path("wiki/combat-perks/A0001-A0010.md"), documents[0][0])
            self.assertEqual(Path("wiki/combat-perks/A0091-A0100.md"), documents[-1][0])
            for path, content in documents:
                rows = [line for line in content.splitlines() if line.startswith("| `rpgskilltree:runtime/combat_perks` |")]
                self.assertEqual(10, len(rows), str(path))
                self.assertIn("não editar manualmente", content)

    def test_content_coverage_reports_factual_gaps_without_calling_them_structural(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)
            audit = build_content_coverage(root, locale="pt_br", tree_id="rpgskilltree:main")
            self.assertEqual(2, audit["summary"]["total_nodes"])
            self.assertEqual(1, audit["summary"]["localized_names"])
            self.assertEqual(1, audit["summary"]["localized_descriptions"])
            self.assertEqual(1, audit["summary"]["nodes_with_declarative_effects"])
            self.assertEqual(1, audit["summary"]["nodes_without_declarative_effects"])
            self.assertNotIn("structural", audit["nodes"][1])

    def test_document_update_writes_shards_is_idempotent_and_detects_shard_drift(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_full_semantic_fixture(root)
            perk_path, effect_path = self._write_catalog_shells(root)

            changed = update_catalog_documents(root, locale="pt_br", check=False)
            self.assertEqual(12, len(changed))
            self.assertEqual(perk_path, changed[0])
            self.assertEqual(effect_path, changed[1])
            first_shard = root / "wiki/combat-perks/A0001-A0010.md"
            last_shard = root / "wiki/combat-perks/A0091-A0100.md"
            self.assertIn("`rpgskilltree:combat/a0001`", first_shard.read_text(encoding="utf-8"))
            self.assertIn("`rpgskilltree:combat/a0100`", last_shard.read_text(encoding="utf-8"))
            self.assertNotIn("`rpgskilltree:combat/a0001`", perk_path.read_text(encoding="utf-8"))
            self.assertEqual([], update_catalog_documents(root, locale="pt_br", check=False))
            self.assertEqual([], update_catalog_documents(root, locale="pt_br", check=True))

            last_shard.write_text(last_shard.read_text(encoding="utf-8").replace("Perk A0100", "DRIFT", 1), encoding="utf-8")
            with self.assertRaises(WikiCatalogDriftError) as failure:
                update_catalog_documents(root, locale="pt_br", check=True)
            self.assertIn("wiki/combat-perks/A0091-A0100.md", str(failure.exception).replace("\\", "/"))

    def test_document_update_fails_closed_when_derived_semantic_snapshot_is_missing(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)
            (root / "build/generated-wiki/combat-perks.json").unlink()
            self._write_catalog_shells(root)
            with self.assertRaises(FileNotFoundError):
                update_catalog_documents(root, locale="pt_br", check=False)

    def test_main_ci_enforces_real_wiki_catalog_drift_check(self):
        root = Path(__file__).resolve().parents[1]
        workflow = (root / ".github/workflows/alpha2-build.yml").read_text(encoding="utf-8")
        self.assertIn("Wiki catalog drift check", workflow)
        self.assertIn("python3 scripts/generate-wiki-catalog.py --check", workflow)

    @classmethod
    def _write_fixture(cls, root: Path) -> None:
        cls._write_json(
            root / "src/main/resources/data/rpgskilltree/node_rules/main.json",
            {
                "treeId": "rpgskilltree:main",
                "nodes": [
                    {
                        "id": "rpgskilltree:martial_000",
                        "maxRank": 3,
                        "costPerRank": 1,
                        "startingPoint": False,
                        "minCharacterLevel": 8,
                    },
                    {
                        "id": "rpgskilltree:technical_only",
                        "maxRank": 1,
                        "costPerRank": 2,
                        "startingPoint": True,
                    },
                ],
            },
        )
        cls._write_json(
            root / "src/main/resources/data/rpgskilltree/node_effects/main.json",
            {
                "attributes": [
                    {
                        "effectId": "rpgskilltree:node/martial_000/sword_damage",
                        "nodeId": "rpgskilltree:martial_000",
                        "attributeId": "rpgskilltree:sword_damage",
                        "operation": "ADD_PERCENT_BASE",
                        "amountPerRank": 0.03,
                    }
                ],
                "behaviors": [
                    {
                        "effectId": "rpgskilltree:node/martial_000/riposte",
                        "nodeId": "rpgskilltree:martial_000",
                        "handlerId": "rpgskilltree:riposte",
                    }
                ],
            },
        )
        cls._write_json(
            root / "src/main/resources/assets/rpgskilltree/lang/pt_br.json",
            {
                "node.rpgskilltree.martial_000.name": "Treino com Espadas I",
                "node.rpgskilltree.martial_000.description": "Aumenta o dano com espadas.",
            },
        )
        cls._write_json(
            root / "build/generated-wiki/combat-perks.json",
            {
                "schema": 1,
                "treeId": "rpgskilltree:runtime/combat_perks",
                "nodes": [
                    {
                        "id": "rpgskilltree:combat/a0001",
                        "code": "A0001",
                        "name": "Treino com Espadas I",
                        "description": "+3% de dano com espadas por rank, máximo +9%.",
                        "maxRank": 3,
                        "costPerRank": 1,
                        "startingPoint": True,
                        "minCharacterLevel": 8,
                        "requiredMastery": {"epicfight:sword": 60},
                        "requiredNodeRanks": {"rpgskilltree:martial_000": 1},
                    },
                    {
                        "id": "rpgskilltree:combat/a0021",
                        "code": "A0021",
                        "name": "Precisão com Adagas",
                        "description": None,
                        "maxRank": 3,
                        "costPerRank": 1,
                        "startingPoint": False,
                        "minCharacterLevel": 1,
                        "requiredMastery": {},
                        "requiredNodeRanks": {"rpgskilltree:combat/a0019": 1},
                    },
                ],
            },
        )

    @classmethod
    def _write_full_semantic_fixture(cls, root: Path) -> None:
        cls._write_fixture(root)
        nodes = []
        for number in range(1, 101):
            code = f"A{number:04d}"
            nodes.append(
                {
                    "id": f"rpgskilltree:combat/a{number:04d}",
                    "code": code,
                    "name": f"Perk {code}",
                    "description": "Descrição auditada." if number <= 20 else None,
                    "maxRank": 1,
                    "costPerRank": 1,
                    "startingPoint": number in {1, 11, 21, 31, 41, 51, 61, 71, 81, 91},
                    "minCharacterLevel": 1,
                    "requiredMastery": {},
                    "requiredNodeRanks": {},
                }
            )
        cls._write_json(
            root / "build/generated-wiki/combat-perks.json",
            {"schema": 1, "treeId": "rpgskilltree:runtime/combat_perks", "nodes": nodes},
        )

    @staticmethod
    def _write_catalog_shells(root: Path) -> tuple[Path, Path]:
        perk_path = root / "wiki/PERK_CATALOG.md"
        effect_path = root / "wiki/EFFECT_CATALOG.md"
        perk_path.parent.mkdir(parents=True, exist_ok=True)
        perk_path.write_text(
            "# Catálogo\n\nTexto manual.\n\n<!-- rpgskilltree:generated:perk-catalog:start -->\nvelho\n<!-- rpgskilltree:generated:perk-catalog:end -->\n",
            encoding="utf-8",
        )
        effect_path.write_text(
            "# Efeitos\n\nContexto manual.\n\n<!-- rpgskilltree:generated:effect-catalog:start -->\nvelho\n<!-- rpgskilltree:generated:effect-catalog:end -->\n",
            encoding="utf-8",
        )
        return perk_path, effect_path

    @staticmethod
    def _write_json(path: Path, payload: object) -> None:
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(payload, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


if __name__ == "__main__":
    unittest.main()
