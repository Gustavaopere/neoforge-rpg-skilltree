import importlib.util
import pathlib
import tempfile
import unittest
from contextlib import redirect_stdout
from io import StringIO

MODULE_PATH = pathlib.Path(__file__).resolve().parents[1] / 'validate_dialogues.py'

def load_module():
    spec = importlib.util.spec_from_file_location('validate_dialogues', MODULE_PATH)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module

VALID = '''# DLG-0001 — Teste

## Estado editorial
RASCUNHO

## Participantes
- NPC-0001
- jogador

## Contexto
Teste.

## Precondições editoriais
Nenhuma.

## Knowledge exigido de NPC-0001
Somente fatos conhecidos.

## Relações/estado relevantes
Nenhuma.

## Entrada padrão — início
NPC: Olá.

## Ramos
### Ramo A
- resposta: fim.

## Saídas do diálogo
- fim.

## Intents/consequências solicitadas
Nenhuma.

## Arcos/quests relacionados
Nenhum.

## NPCs/facções/locais/evidências/eventos relacionados
- NPC-0001.

## Invariantes
- não inventar knowledge.

## Notas de voz
Preciso.

## QA
- [x] knowledge válido.

## Spoilers internos
Nenhum.
'''

class DialogueValidationTests(unittest.TestCase):
    def make_root(self, files):
        td = tempfile.TemporaryDirectory()
        root = pathlib.Path(td.name)
        for rel, content in files.items():
            path = root / rel
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(content, encoding='utf-8')
        self.addCleanup(td.cleanup)
        return root

    def test_valid_dialogue_accepts_semantic_heading_variants(self):
        mod = load_module()
        root = self.make_root({'DLG-0001-test.md': VALID})
        self.assertEqual([], mod.validate(root))

    def test_missing_knowledge_section_is_reported(self):
        mod = load_module()
        text = VALID.replace('## Knowledge exigido de NPC-0001\nSomente fatos conhecidos.\n\n', '')
        root = self.make_root({'DLG-0001-test.md': text})
        issues = mod.validate(root)
        self.assertTrue(any(i.code == 'missing-section' and i.detail == 'knowledge' for i in issues))

    def test_empty_participants_is_reported(self):
        mod = load_module()
        text = VALID.replace('## Participantes\n- NPC-0001\n- jogador\n', '## Participantes\n')
        root = self.make_root({'DLG-0001-test.md': text})
        issues = mod.validate(root)
        self.assertTrue(any(i.code == 'empty-section' and i.detail == 'participants' for i in issues))

    def test_abertura_heading_is_also_accepted(self):
        mod = load_module()
        text = VALID.replace('## Entrada padrão — início', '## Abertura')
        root = self.make_root({'DLG-0001-test.md': text})
        self.assertEqual([], mod.validate(root))

    def test_placeholder_id_is_reported_in_real_dialogue_file(self):
        mod = load_module()
        text = VALID.replace('NPC-0001', 'NPC-####')
        root = self.make_root({'DLG-0001-test.md': text})
        issues = mod.validate(root)
        self.assertTrue(any(i.code == 'placeholder-id' for i in issues))

    def test_qa_section_requires_checkbox(self):
        mod = load_module()
        text = VALID.replace('- [x] knowledge válido.', 'knowledge válido.')
        root = self.make_root({'DLG-0001-test.md': text})
        issues = mod.validate(root)
        self.assertTrue(any(i.code == 'qa-without-checkbox' for i in issues))

    def test_default_cli_output_is_spoiler_safe(self):
        mod = load_module()
        text = VALID.replace('## Knowledge exigido de NPC-0001\nSomente fatos conhecidos.\n\n', '')
        root = self.make_root({'DLG-0001-secret-context.md': text})
        output = StringIO()
        with redirect_stdout(output):
            code = mod.main([str(root)])
        rendered = output.getvalue()
        self.assertEqual(1, code)
        self.assertIn('ERROR missing-section: 1', rendered)
        self.assertNotIn('knowledge', rendered)
        self.assertNotIn('DLG-0001-secret-context.md', rendered)

    def test_reveal_cli_output_includes_editorial_details(self):
        mod = load_module()
        text = VALID.replace('## Knowledge exigido de NPC-0001\nSomente fatos conhecidos.\n\n', '')
        root = self.make_root({'DLG-0001-secret-context.md': text})
        output = StringIO()
        try:
            with redirect_stdout(output):
                code = mod.main([str(root), '--reveal'])
        except SystemExit as exc:
            self.fail(f'--reveal must be supported: {exc}')
        rendered = output.getvalue()
        self.assertEqual(1, code)
        self.assertIn('ERROR missing-section knowledge', rendered)
        self.assertIn('DLG-0001-secret-context.md:1', rendered)

    def test_non_dialogue_markdown_is_ignored(self):
        mod = load_module()
        root = self.make_root({'README.md': '# 12 — Diálogos\n'})
        self.assertEqual([], mod.validate(root))
