import importlib.util
import pathlib
import tempfile
import unittest

MODULE_PATH = pathlib.Path(__file__).resolve().parents[1] / 'validate_story.py'


def load_module():
    spec = importlib.util.spec_from_file_location('validate_story', MODULE_PATH)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class ValidateStoryTests(unittest.TestCase):
    def make_root(self, files):
        td = tempfile.TemporaryDirectory()
        root = pathlib.Path(td.name)
        for rel, content in files.items():
            path = root / rel
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(content, encoding='utf-8')
        self.addCleanup(td.cleanup)
        return root

    def test_valid_graph_has_no_issues(self):
        mod = load_module()
        root = self.make_root({
            'NPC-0001-a.md': '# NPC-0001 — A\n\nRelação: QST-0001\n',
            'QST-0001-q.md': '# QST-0001 — Q\n\nParticipante: NPC-0001\n',
        })
        self.assertEqual([], mod.validate(root))

    def test_duplicate_declared_id_is_reported(self):
        mod = load_module()
        root = self.make_root({
            'a.md': '# NPC-0001 — A\n',
            'b.md': '# NPC-0001 — B\n',
        })
        issues = mod.validate(root)
        self.assertTrue(any(i.code == 'duplicate-id' and i.ref == 'NPC-0001' for i in issues))

    def test_unresolved_reference_is_reported(self):
        mod = load_module()
        root = self.make_root({
            'a.md': '# NPC-0001 — A\n\nQuest: QST-9999\n',
        })
        issues = mod.validate(root)
        self.assertTrue(any(i.code == 'unresolved-ref' and i.ref == 'QST-9999' for i in issues))

    def test_template_placeholders_are_ignored(self):
        mod = load_module()
        root = self.make_root({
            'TEMPLATE-NPC.md': '# NPC-#### — Nome\n\nQuest: QST-####\n',
            'real.md': '# NPC-0001 — A\n',
        })
        self.assertEqual([], mod.validate(root))

    def test_reference_on_heading_declaration_is_not_counted_as_unresolved(self):
        mod = load_module()
        root = self.make_root({'a.md': '# NPC-0001 — A\n'})
        self.assertEqual([], mod.validate(root))

    def test_auxiliary_heading_references_id_without_redeclaring_entity(self):
        mod = load_module()
        root = self.make_root({
            'NPC-0001-main.md': '# NPC-0001 — A\n',
            'NPC-0001-autoria.md': '# Ficha de autoria — NPC-0001 — A\n',
            'NPC-0001-asset-brief.md': '# Asset Brief — NPC-0001 — A\n',
        })
        issues = mod.validate(root)
        self.assertFalse(any(i.code == 'duplicate-id' for i in issues))
        self.assertFalse(any(i.code == 'unresolved-ref' and i.ref == 'NPC-0001' for i in issues))


class ExitPolicyTests(unittest.TestCase):
    def test_unresolved_reference_is_warning_unless_strict(self):
        mod = load_module()
        issue = mod.Issue('unresolved-ref', 'QST-9999', pathlib.Path('a.md'), 2)
        self.assertEqual(0, mod.exit_code([issue], strict_references=False))
        self.assertEqual(1, mod.exit_code([issue], strict_references=True))

    def test_duplicate_id_is_always_fatal(self):
        mod = load_module()
        issue = mod.Issue('duplicate-id', 'NPC-0001', pathlib.Path('b.md'), 1)
        self.assertEqual(1, mod.exit_code([issue], strict_references=False))


if __name__ == '__main__':
    unittest.main()
