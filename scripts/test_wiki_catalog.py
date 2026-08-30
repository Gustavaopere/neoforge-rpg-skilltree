#!/usr/bin/env python3
import json
import sys
import tempfile
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))

from wiki_catalog import build_catalog_sections, replace_generated_block


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
            self._write_json(
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
                            "requiredDiscoveries": ["rpgskilltree:discovery/arena"]
                        },
                        {
                            "id": "rpgskilltree:technical_only",
                            "maxRank": 1,
                            "costPerRank": 2,
                            "startingPoint": True,
                            "neighbors": []
                        }
                    ]
                }
            )
            self._write_json(
                root / "src/main/resources/data/rpgskilltree/node_effects/main.json",
                {
                    "attributes": [
                        {
                            "effectId": "rpgskilltree:node/martial_000/sword_damage",
                            "nodeId": "rpgskilltree:martial_000",
                            "attributeId": "rpgskilltree:sword_damage",
                            "operation": "ADD_PERCENT_BASE",
                            "amountPerRank": 0.03
                        }
                    ],
                    "behaviors": [
                        {
                            "effectId": "rpgskilltree:node/martial_000/riposte",
                            "nodeId": "rpgskilltree:martial_000",
                            "handlerId": "rpgskilltree:riposte"
                        }
                    ]
                }
            )
            self._write_json(
                root / "src/main/resources/assets/rpgskilltree/lang/pt_br.json",
                {
                    "node.rpgskilltree.martial_000.name": "Treino com Espadas I",
                    "node.rpgskilltree.martial_000.description": "Aumenta o dano com espadas."
                }
            )

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

    @staticmethod
    def _write_json(path: Path, payload: object) -> None:
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(payload, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


if __name__ == "__main__":
    unittest.main()
