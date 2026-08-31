#!/usr/bin/env python3
from __future__ import annotations

import io
import sys
import tempfile
import unittest
from pathlib import Path

SCRIPT_DIR = Path(__file__).resolve().parent
if str(SCRIPT_DIR) not in sys.path:
    sys.path.insert(0, str(SCRIPT_DIR))

import narrative_auditor  # type: ignore  # noqa: E402


class NarrativeAuditorTest(unittest.TestCase):
    def write(self, root: Path, relative: str, content: str) -> None:
        path = root / relative
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding="utf-8")

    def test_valid_cross_referenced_corpus_has_no_errors(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(
                root,
                "03-npcs/principais/NPC-0001-alfa.md",
                "# NPC-0001 — Alfa\n\n## Estado editorial\nRASCUNHO\n\n## Papel\nPessoa.\n\n## Relações\n- QST-0001\n\n## Invariantes\n- existe.\n",
            )
            self.write(
                root,
                "04-quests/secundarias/QST-0001-eco.md",
                "# QST-0001 — Eco\n\n## Estado editorial\nRASCUNHO\n\n## Premissa player-safe\nAlgo pode ser descoberto.\n\n## Participantes\n- NPC-0001\n\n## SIM / NÃO / ANTES / DEPOIS\nCoberto.\n\n## Anti-soft-lock\nExiste rota alternativa.\n\n## Idempotência/deduplicação\nSem duplicação.\n",
            )

            report = narrative_auditor.audit_tree(root)

            self.assertEqual([], report.errors)

    def test_duplicate_ids_are_blocking_errors(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(root, "03-npcs/NPC-0001-a.md", "# NPC-0001 — A\n")
            self.write(root, "03-npcs/NPC-0001-b.md", "# NPC-0001 — B\n")

            report = narrative_auditor.audit_tree(root)

            self.assertIn("DUPLICATE_ID", [finding.code for finding in report.errors])

    def test_unresolved_explicit_id_reference_is_blocking(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(
                root,
                "04-quests/QST-0001-eco.md",
                "# QST-0001 — Eco\n\n## Participantes\n- NPC-9999\n",
            )

            report = narrative_auditor.audit_tree(root)

            self.assertIn("UNRESOLVED_REFERENCE", [finding.code for finding in report.errors])

    def test_filename_heading_id_mismatch_is_blocking(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(root, "03-npcs/NPC-0001-a.md", "# NPC-0002 — A\n")

            report = narrative_auditor.audit_tree(root)

            self.assertIn("FILENAME_ID_MISMATCH", [finding.code for finding in report.errors])

    def test_missing_recommended_quest_sections_are_warnings_not_errors(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(
                root,
                "04-quests/QST-0001-eco.md",
                "# QST-0001 — Eco\n\n## Estado editorial\nRASCUNHO\n",
            )

            report = narrative_auditor.audit_tree(root)

            self.assertEqual([], report.errors)
            self.assertIn("MISSING_RECOMMENDED_SECTION", [finding.code for finding in report.warnings])

    def test_placeholder_marker_is_warning(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(
                root,
                "03-npcs/NPC-0001-a.md",
                "# NPC-0001 — A\n\n## Estado editorial\nRASCUNHO\n\nID ainda a definir para a facção.\n",
            )

            report = narrative_auditor.audit_tree(root)

            self.assertIn("PLACEHOLDER_MARKER", [finding.code for finding in report.warnings])

    def test_player_safe_console_hides_ids_paths_and_titles(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(
                root,
                "04-quests/QST-0001-segredo.md",
                "# QST-0001 — Segredo\n\n## Participantes\n- NPC-9999\n",
            )
            report = narrative_auditor.audit_tree(root)
            output = io.StringIO()

            narrative_auditor.render_report(report, stream=output, reveal=False)
            text = output.getvalue()

            self.assertIn("UNRESOLVED_REFERENCE", text)
            self.assertNotIn("QST-0001", text)
            self.assertNotIn("NPC-9999", text)
            self.assertNotIn("segredo.md", text)
            self.assertNotIn("Segredo", text)

    def test_detailed_console_can_reveal_debug_context(self) -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            self.write(
                root,
                "04-quests/QST-0001-segredo.md",
                "# QST-0001 — Segredo\n\n## Participantes\n- NPC-9999\n",
            )
            report = narrative_auditor.audit_tree(root)
            output = io.StringIO()

            narrative_auditor.render_report(report, stream=output, reveal=True)
            text = output.getvalue()

            self.assertIn("QST-0001", text)
            self.assertIn("NPC-9999", text)
            self.assertIn("QST-0001-segredo.md", text)


if __name__ == "__main__":
    unittest.main()
