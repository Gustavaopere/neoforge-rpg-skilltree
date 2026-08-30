#!/usr/bin/env python3
from pathlib import Path
import unittest


class PlayerTextBatchBoundaryTest(unittest.TestCase):
    def test_catalog_declares_a0021_a0030_and_stops_before_a0031(self):
        root = Path(__file__).resolve().parents[1]
        source = (root / "src/main/java/dev/gustavopere/rpgskilltree/core/CombatPerkPlayerTextCatalog.java").read_text(encoding="utf-8")

        for number in range(21, 31):
            self.assertIn(f'add(entries, "A{number:04d}"', source)
        self.assertNotIn('add(entries, "A0031"', source)


if __name__ == "__main__":
    unittest.main()