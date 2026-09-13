import importlib.util
import json
import pathlib
import tempfile
import unittest

MODULE_PATH = pathlib.Path(__file__).resolve().parents[1] / 'story_inventory.py'


def load_module():
    spec = importlib.util.spec_from_file_location('story_inventory', MODULE_PATH)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class StoryInventoryTests(unittest.TestCase):
    def make_root(self, files):
        td = tempfile.TemporaryDirectory()
        root = pathlib.Path(td.name)
        for rel, content in files.items():
            path = root / rel
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(content, encoding='utf-8')
        self.addCleanup(td.cleanup)
        return root

    def test_extracts_id_title_state_and_references(self):
        mod = load_module()
        root = self.make_root({
            'npc.md': '# NPC-0001 — Severin\n\n## Estado editorial\nCANÔNICO\n\nRelações: QST-0001, FAC-0002.\n',
            'quest.md': '# QST-0001 — Ecos\n\n## Estado editorial\nRASCUNHO\n\nParticipante: NPC-0001.\n',
            'fac.md': '# FAC-0002 — Ordem\n\n## Estado editorial\nRASCUNHO\n',
        })
        records = {record.id: record for record in mod.inventory(root)}
        self.assertEqual('Severin', records['NPC-0001'].title)
        self.assertEqual('CANÔNICO', records['NPC-0001'].state)
        self.assertEqual(('FAC-0002', 'QST-0001'), records['NPC-0001'].references)

    def test_missing_state_is_explicit(self):
        mod = load_module()
        root = self.make_root({'npc.md': '# NPC-0001 — A\n'})
        record = mod.inventory(root)[0]
        self.assertEqual('NÃO DECLARADO', record.state)

    def test_template_placeholder_is_ignored(self):
        mod = load_module()
        root = self.make_root({
            'TEMPLATE-NPC.md': '# NPC-#### — Nome\n\n## Estado editorial\nRASCUNHO\n',
            'NPC-0001-a.md': '# NPC-0001 — A\n\n## Estado editorial\nCANÔNICO\n',
        })
        records = mod.inventory(root)
        self.assertEqual(['NPC-0001'], [record.id for record in records])

    def test_auxiliary_heading_does_not_create_second_record(self):
        mod = load_module()
        root = self.make_root({
            'NPC-0001-main.md': '# NPC-0001 — A\n\n## Estado editorial\nCANÔNICO\n',
            'NPC-0001-autoria.md': '# Ficha de autoria — NPC-0001 — A\n\n## Estado editorial\nRASCUNHO\n',
            'NPC-0001-asset-brief.md': '# Asset Brief — NPC-0001 — A\n\n## Estado\nRASCUNHO\n',
        })
        records = mod.inventory(root)
        self.assertEqual(['NPC-0001'], [record.id for record in records])

    def test_own_declaration_is_not_a_reference_and_refs_are_deduped(self):
        mod = load_module()
        root = self.make_root({
            'npc.md': '# NPC-0001 — A\n\n## Estado editorial\nCANÔNICO\n\nQST-0001 e QST-0001.\n',
            'q.md': '# QST-0001 — Q\n',
        })
        record = next(record for record in mod.inventory(root) if record.id == 'NPC-0001')
        self.assertEqual(('QST-0001',), record.references)

    def test_records_are_sorted_by_id(self):
        mod = load_module()
        root = self.make_root({
            'b.md': '# QST-0002 — B\n',
            'a.md': '# NPC-0003 — A\n',
            'c.md': '# NPC-0001 — C\n',
        })
        self.assertEqual(
            ['NPC-0001', 'NPC-0003', 'QST-0002'],
            [record.id for record in mod.inventory(root)],
        )

    def test_markdown_report_contains_summary_and_records(self):
        mod = load_module()
        root = self.make_root({
            'a.md': '# NPC-0001 — A\n\n## Estado editorial\nCANÔNICO\n',
            'b.md': '# QST-0001 — B\n\n## Estado editorial\nRASCUNHO\n',
        })
        report = mod.render_markdown(mod.inventory(root), root)
        self.assertIn('NPC: 1', report)
        self.assertIn('QST: 1', report)
        self.assertIn('| NPC-0001 | A | CANÔNICO |', report)

    def test_json_report_is_machine_readable(self):
        mod = load_module()
        root = self.make_root({'a.md': '# NPC-0001 — A\n\n## Estado editorial\nCANÔNICO\n'})
        payload = json.loads(mod.render_json(mod.inventory(root), root))
        self.assertEqual('NPC-0001', payload['records'][0]['id'])
        self.assertEqual(1, payload['summary']['by_type']['NPC'])


if __name__ == '__main__':
    unittest.main()
