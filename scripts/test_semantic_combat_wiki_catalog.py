#!/usr/bin/env python3
import json
import sys
import tempfile
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))

from wiki_catalog import build_catalog_sections


class SemanticCombatWikiCatalogContractTest(unittest.TestCase):
    def test_semantic_combat_snapshot_is_included_without_inventing_unaudited_text(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self._write_minimum_data_fixture(root)
            self._write_json(
                root / "wiki/generated/combat-perks.json",
                {
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

            perk_catalog, effect_catalog = build_catalog_sections(root, locale="pt_br")

            self.assertIn("`rpgskilltree:runtime/combat_perks`", perk_catalog)
            self.assertIn("`rpgskilltree:combat/a0001`", perk_catalog)
            self.assertIn("Treino com Espadas I", perk_catalog)
            self.assertIn("+3% de dano com espadas por rank, máximo +9%.", perk_catalog)
            self.assertIn("Mastery `epicfight:sword` ≥ 60", perk_catalog)
            self.assertIn("Nó `rpgskilltree:martial_000` rank ≥ 1", perk_catalog)

            a0021_line = next(
                line for line in perk_catalog.splitlines()
                if "`rpgskilltree:combat/a0021`" in line
            )
            self.assertIn("Precisão com Adagas", a0021_line)
            self.assertIn(" | — | 3 | 1 | ", a0021_line)
            self.assertNotIn("chance de crítico", a0021_line.lower())
            self.assertNotIn("rpgskilltree:combat/a0001", effect_catalog)

    @classmethod
    def _write_minimum_data_fixture(cls, root: Path) -> None:
        cls._write_json(
            root / "src/main/resources/data/rpgskilltree/node_rules/main.json",
            {
                "treeId": "rpgskilltree:main",
                "nodes": [
                    {
                        "id": "rpgskilltree:martial_000",
                        "maxRank": 1,
                        "costPerRank": 1,
                        "startingPoint": True,
                        "neighbors": [],
                    }
                ],
            },
        )
        cls._write_json(
            root / "src/main/resources/data/rpgskilltree/node_effects/main.json",
            {"attributes": [], "behaviors": []},
        )
        cls._write_json(
            root / "src/main/resources/assets/rpgskilltree/lang/pt_br.json",
            {},
        )

    @staticmethod
    def _write_json(path: Path, payload: object) -> None:
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(payload, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


if __name__ == "__main__":
    unittest.main()
