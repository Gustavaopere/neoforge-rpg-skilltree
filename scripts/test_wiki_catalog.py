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

    def test_catalog_is_derived_from_rules_localization_and_effects_without_inference(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)

            perk_catalog, effect_catalog = build_catalog_sections(root, locale="pt_br")

            self.assertIn("Treino com Espadas I", perk_catalog)
            self.assertIn("Aumenta o dano com espadas.", perk_catalog)
            self.assertIn("`rpgskilltree:martial_000`", perk_catalog)
            self.assertIn("3", perk_catalog)
            self.assertIn("1", perk_catalog)
            self.assertIn("Nível ≥ 8", perk_catalog)
            self.assertIn("Classe: `warrior`", perk_catalog)
            self.assertIn("Mastery `epicfight:sword` ≥ 60", perk_catalog)
            self.assertIn("Especialização: `duelist`", perk_catalog)
            self.assertIn("Escolha: `rpgskilltree:pact/blade`", perk_catalog)
            self.assertIn("Nó: `rpgskilltree:core_00`", perk_catalog)
            self.assertIn("Nó `rpgskilltree:core_01` rank ≥ 2", perk_catalog)
            self.assertIn("Descoberta: `rpgskilltree:discovery/arena`", perk_catalog)
            self.assertIn("`rpgskilltree:technical_only`", perk_catalog)
            self.assertNotIn("Technical Only", perk_catalog)

            self.assertIn("`rpgskilltree:node/martial_000/sword_damage`", effect_catalog)
            self.assertIn("`rpgskilltree:sword_damage`", effect_catalog)
            self.assertIn("ADD_PERCENT_BASE", effect_catalog)
            self.assertIn("+3%/rank", effect_catalog)
            self.assertIn("`rpgskilltree:riposte`", effect_catalog)
            self.assertIn("BEHAVIOR_HANDLER", effect_catalog)

    def test_semantic_combat_snapshot_is_appended_without_inferred_policy_text_or_effects(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)

            perk_catalog, _effect_catalog = build_catalog_sections(
                root,
                locale="pt_br",
                semantic_snapshot_required=True,
            )

            self.assertIn("`rpgskilltree:runtime/combat_perks`", perk_catalog)
            self.assertIn("`rpgskilltree:combat/a0001`", perk_catalog)
            self.assertIn("Treino com Espadas I", perk_catalog)
            self.assertIn("+3% de dano com espadas por rank, máximo +9%.", perk_catalog)
            self.assertIn("Nível ≥ 8", perk_catalog)
            self.assertIn("Mastery `epicfight:sword` ≥ 60", perk_catalog)
            self.assertIn("Nó `rpgskilltree:martial_000` rank ≥ 1", perk_catalog)
            self.assertIn("`rpgskilltree:combat/a0021`", perk_catalog)
            self.assertIn("Precisão com Adagas", perk_catalog)
            a0021_row = next(line for line in perk_catalog.splitlines() if "`rpgskilltree:combat/a0021`" in line)
            self.assertIn(" | — | 3 | 1 | ", a0021_row)
            self.assertTrue(a0021_row.endswith(" | — |"))
            self.assertNotIn("chance de crítico", a0021_row.lower())

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
            self.assertEqual(1, audit["summary"]["nodes_missing_names"])
            self.assertEqual(1, audit["summary"]["nodes_missing_descriptions"])

            martial = audit["nodes"][0]
            technical = audit["nodes"][1]
            self.assertEqual("rpgskilltree:martial_000", martial["id"])
            self.assertTrue(martial["has_name"])
            self.assertTrue(martial["has_description"])
            self.assertEqual(1, martial["attribute_effects"])
            self.assertEqual(1, martial["behavior_effects"])
            self.assertTrue(martial["has_declarative_effect"])

            self.assertEqual("rpgskilltree:technical_only", technical["id"])
            self.assertFalse(technical["has_name"])
            self.assertFalse(technical["has_description"])
            self.assertFalse(technical["has_declarative_effect"])
            self.assertNotIn("structural", technical)

    def test_document_update_is_idempotent_preserves_editorial_and_check_detects_drift(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)
            perk_path = root / "wiki/PERK_CATALOG.md"
            effect_path = root / "wiki/EFFECT_CATALOG.md"
            perk_path.parent.mkdir(parents=True, exist_ok=True)
            perk_path.write_text(
                "# Catálogo\n\nTexto manual.\n\n"
                "<!-- rpgskilltree:generated:perk-catalog:start -->\nvelho\n"
                "<!-- rpgskilltree:generated:perk-catalog:end -->\n\n"
                "## Trivia\n\nNunca sobrescrever esta trivia.\n",
                encoding="utf-8",
            )
            effect_path.write_text(
                "# Efeitos\n\nContexto manual.\n\n"
                "<!-- rpgskilltree:generated:effect-catalog:start -->\nvelho\n"
                "<!-- rpgskilltree:generated:effect-catalog:end -->\n",
                encoding="utf-8",
            )

            changed = update_catalog_documents(root, locale="pt_br", check=False)
            self.assertEqual([perk_path, effect_path], changed)
            self.assertIn("Treino com Espadas I", perk_path.read_text(encoding="utf-8"))
            self.assertIn("`rpgskilltree:combat/a0021`", perk_path.read_text(encoding="utf-8"))
            self.assertIn("Nunca sobrescrever esta trivia.", perk_path.read_text(encoding="utf-8"))
            self.assertEqual([], update_catalog_documents(root, locale="pt_br", check=False))
            self.assertEqual([], update_catalog_documents(root, locale="pt_br", check=True))

            perk_path.write_text(
                perk_path.read_text(encoding="utf-8").replace("Treino com Espadas I", "CATÁLOGO FORA DE SINCRONIA", 1),
                encoding="utf-8",
            )
            with self.assertRaises(WikiCatalogDriftError) as failure:
                update_catalog_documents(root, locale="pt_br", check=True)
            self.assertIn("wiki/PERK_CATALOG.md", str(failure.exception).replace("\\", "/"))
            self.assertIn("CATÁLOGO FORA DE SINCRONIA", perk_path.read_text(encoding="utf-8"))

    def test_document_update_fails_closed_when_derived_semantic_snapshot_is_missing(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_fixture(root)
            (root / "build/generated-wiki/combat-perks.json").unlink()
            (root / "wiki").mkdir(parents=True, exist_ok=True)
            (root / "wiki/PERK_CATALOG.md").write_text(
                "<!-- rpgskilltree:generated:perk-catalog:start -->\nold\n<!-- rpgskilltree:generated:perk-catalog:end -->\n",
                encoding="utf-8",
            )
            (root / "wiki/EFFECT_CATALOG.md").write_text(
                "<!-- rpgskilltree:generated:effect-catalog:start -->\nold\n<!-- rpgskilltree:generated:effect-catalog:end -->\n",
                encoding="utf-8",
            )

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
                        "neighbors": ["rpgskilltree:core_00"],
                        "minCharacterLevel": 8,
                        "requiredClasses": ["warrior"],
                        "requiredMastery": {"epicfight:sword": 60},
                        "requiredSpecializations": ["duelist"],
                        "requiredClassChoices": ["rpgskilltree:pact/blade"],
                        "requiredNodes": ["rpgskilltree:core_00"],
                        "requiredNodeRanks": {"rpgskilltree:core_01": 2},
                        "requiredDiscoveries": ["rpgskilltree:discovery/arena"],
                    },
                    {
                        "id": "rpgskilltree:technical_only",
                        "maxRank": 1,
                        "costPerRank": 2,
                        "startingPoint": True,
                        "neighbors": [],
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
                        "requiredNodeRanks": {
                            "rpgskilltree:combat/a0019": 1,
                            "rpgskilltree:martial_000": 1,
                        },
                    },
                ],
            },
        )

    @staticmethod
    def _write_json(path: Path, payload: object) -> None:
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(payload, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


if __name__ == "__main__":
    unittest.main()
