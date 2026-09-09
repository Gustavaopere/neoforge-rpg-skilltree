import importlib.util
import json
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[3]
SCRIPT = ROOT / "PROJECT-INSTRUCTIONS" / "engineering" / "tooling" / "import-physical-modlist.py"
FIXTURE = ROOT / "PROJECT-INSTRUCTIONS" / "engineering" / "tests" / "fixtures" / "modlist-i2-sample.txt"

spec = importlib.util.spec_from_file_location("i2_modlist", SCRIPT)
modlist = importlib.util.module_from_spec(spec)
spec.loader.exec_module(modlist)


class I2PhysicalModlistCatalogTest(unittest.TestCase):
    def test_parser_preserves_top_level_and_nested_hierarchy(self):
        snapshot = modlist.parse_modlist_text(FIXTURE.read_text(encoding="utf-8"), captured_at="2026-09-09")
        self.assertEqual(3, snapshot["declared_top_level_mods"])
        self.assertEqual(3, snapshot["parsed_top_level_mods"])
        self.assertEqual(2, snapshot["parsed_nested_mods"])

        by_mod_id = {entry["mod_id"]: entry for entry in snapshot["entries"]}
        self.assertTrue(by_mod_id["alpha"]["top_level"])
        self.assertEqual(0, by_mod_id["alpha"]["nesting_depth"])
        self.assertFalse(by_mod_id["shared"]["top_level"])
        self.assertEqual(1, by_mod_id["shared"]["nesting_depth"])
        self.assertEqual("alpha-1.0.0.jar", by_mod_id["shared"]["parent_jar"])
        self.assertEqual(2, by_mod_id["deep"]["nesting_depth"])
        self.assertEqual("/META-INF/jarjar/shared-2.0.0.jar", by_mod_id["deep"]["parent_jar"])

    def test_parser_preserves_provider_metadata_and_hashes(self):
        snapshot = modlist.parse_modlist_text(FIXTURE.read_text(encoding="utf-8"), captured_at="2026-09-09")
        beta = next(entry for entry in snapshot["entries"] if entry["mod_id"] == "beta")
        self.assertEqual("MCreator mod", beta["notes"])
        self.assertEqual("1.1.0", beta["version"])
        self.assertEqual("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", beta["modrinth_hash"])
        self.assertEqual("222", beta["curseforge_hash"])
        self.assertEqual([], beta["mixin_configs"])

    def test_parser_fails_closed_on_declared_count_mismatch(self):
        text = FIXTURE.read_text(encoding="utf-8").replace("Mods count: 3", "Mods count: 4", 1)
        with self.assertRaisesRegex(ValueError, "declared top-level mod count"):
            modlist.parse_modlist_text(text, captured_at="2026-09-09")

    def test_provider_catalog_does_not_collapse_nested_occurrences(self):
        snapshot = modlist.parse_modlist_text(FIXTURE.read_text(encoding="utf-8"), captured_at="2026-09-09")
        providers = modlist.build_provider_catalog(snapshot)
        shared = providers["shared"]
        self.assertEqual(["2.0.0"], shared["versions"])
        self.assertEqual(0, shared["top_level_occurrences"])
        self.assertEqual(1, shared["nested_occurrences"])
        self.assertEqual(1, len(shared["occurrences"]))

    def test_version_drift_reports_added_removed_version_and_topology_changes(self):
        before = modlist.parse_modlist_text(FIXTURE.read_text(encoding="utf-8"), captured_at="2026-09-08")
        after = json.loads(json.dumps(before))
        after["captured_at"] = "2026-09-09"
        for entry in after["entries"]:
            if entry["mod_id"] == "alpha":
                entry["version"] = "1.1.0"
            if entry["mod_id"] == "shared":
                entry["parent_jar"] = "beta-1.1.0.jar"
        after["entries"] = [entry for entry in after["entries"] if entry["mod_id"] != "deep"]
        after["entries"].append({
            "jar": "gamma-1.0.0.jar",
            "notes": "",
            "mod_id": "gamma",
            "mod_name": "Gamma",
            "version": "1.0.0",
            "mixin_configs": [],
            "modrinth_hash": "",
            "curseforge_hash": "",
            "top_level": False,
            "nesting_depth": 1,
            "parent_jar": "beta-1.1.0.jar",
        })
        drift = modlist.compare_snapshots(before, after)
        self.assertIn("gamma", drift["added_mod_ids"])
        self.assertIn("deep", drift["removed_mod_ids"])
        self.assertIn("alpha", drift["version_changed_mod_ids"])
        self.assertIn("shared", drift["topology_changed_mod_ids"])

    def test_repository_snapshot_matches_physical_contract(self):
        snapshot_path = ROOT / "PROJECT-INSTRUCTIONS" / "engineering" / "catalog" / "physical-modlist" / "snapshot-2026-09-09.json"
        snapshot = json.loads(snapshot_path.read_text(encoding="utf-8"))
        self.assertEqual(595, snapshot["declared_top_level_mods"])
        self.assertEqual(595, snapshot["parsed_top_level_mods"])
        self.assertEqual(404, snapshot["parsed_nested_mods"])
        self.assertEqual("21.1.248", snapshot["loader"]["version"])
        self.assertEqual("7c0a23d6013101383d196526e4b6ba6940fb54a0fed10eaed5956ab015cfcc00", snapshot["source_sha256"])
        self.assertEqual(999, len(snapshot["entries"]))


if __name__ == "__main__":
    unittest.main()
