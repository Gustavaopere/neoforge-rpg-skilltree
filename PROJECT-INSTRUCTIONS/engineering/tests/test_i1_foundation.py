import importlib.util
import json
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[3]
SCRIPT = ROOT / "PROJECT-INSTRUCTIONS" / "engineering" / "tooling" / "validate-i1-foundation.py"
spec = importlib.util.spec_from_file_location("i1_validator", SCRIPT)
validator = importlib.util.module_from_spec(spec)
spec.loader.exec_module(validator)

class I1FoundationContractTest(unittest.TestCase):
    def test_repository_foundation_is_complete(self):
        self.assertEqual([], validator.run_validation(ROOT))

    def test_mod_id_contract_rejects_invalid_identifier(self):
        schema_path = ROOT / "PROJECT-INSTRUCTIONS" / "engineering" / "schemas" / "mod-spec.schema.json"
        schema = json.loads(schema_path.read_text(encoding="utf-8"))
        example_path = ROOT / "PROJECT-INSTRUCTIONS" / "engineering" / "examples" / "mod-spec.example.json"
        example = json.loads(example_path.read_text(encoding="utf-8"))
        example["identity"]["mod_id"] = "Invalid-Mod"
        errors = validator.validate_instance(schema, example)
        self.assertTrue(any("mod_id" in e and "pattern" in e for e in errors), errors)

if __name__ == "__main__":
    unittest.main()
