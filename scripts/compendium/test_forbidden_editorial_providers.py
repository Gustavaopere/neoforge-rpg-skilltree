#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
import unittest

ROOT = Path(__file__).resolve().parents[2]
CORPUS_ROOT = ROOT / "src/main/resources/data/rpgskilltree/compendium/editorial/pt_br"
FORBIDDEN_NAMESPACES = {"tfc", "terrafirmacraft"}


class ForbiddenEditorialProvidersTest(unittest.TestCase):
    def test_forbidden_namespaces_are_absent_from_checked_in_corpus(self) -> None:
        violations: list[str] = []
        for path in sorted(CORPUS_ROOT.rglob("*.json")):
            relative = path.relative_to(CORPUS_ROOT)
            payload = json.loads(path.read_text(encoding="utf-8"))

            directory_namespace = relative.parts[0] if len(relative.parts) > 1 else ""
            declared_namespace = str(payload.get("namespace", "")).strip()
            if directory_namespace in FORBIDDEN_NAMESPACES:
                violations.append(f"{relative}: forbidden directory namespace {directory_namespace!r}")
            if declared_namespace in FORBIDDEN_NAMESPACES:
                violations.append(f"{relative}: forbidden declared namespace {declared_namespace!r}")

            for index, entry in enumerate(payload.get("entries", [])):
                entry_id = str(entry.get("entry_id", "")).strip()
                parts = entry_id.split(":", 2)
                if len(parts) == 3 and parts[1] in FORBIDDEN_NAMESPACES:
                    violations.append(f"{relative} entries[{index}]: forbidden entry_id {entry_id!r}")
                for ref_index, reference in enumerate(entry.get("references", [])):
                    ref = str(reference).strip()
                    ref_parts = ref.split(":", 2)
                    if len(ref_parts) == 3 and ref_parts[1] in FORBIDDEN_NAMESPACES:
                        violations.append(
                            f"{relative} entries[{index}].references[{ref_index}]: forbidden reference {ref!r}"
                        )

        self.assertEqual([], violations, "Forbidden editorial provider content detected:\n" + "\n".join(violations))


if __name__ == "__main__":
    unittest.main()
