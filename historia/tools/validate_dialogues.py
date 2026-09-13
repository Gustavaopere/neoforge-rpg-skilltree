from __future__ import annotations

import pathlib
import re
import unicodedata
from typing import NamedTuple

DLG_FILE_RE = re.compile(r'^DLG-\d{4}.*\.md$', re.IGNORECASE)
TITLE_RE = re.compile(r'^#\s+DLG-\d{4}\b', re.IGNORECASE | re.MULTILINE)
PLACEHOLDER_RE = re.compile(r'\b(?:HIST|ARC|NPC|QST|FAC|SET|LOC|EVT|EVD|END|DLG)-####')
CHECKBOX_RE = re.compile(r'(?m)^\s*-\s*\[[ xX]\]\s+')

REQUIRED_GROUPS = {
    'editorial-state': ('estado editorial',),
    'participants': ('participantes',),
    'context': ('contexto',),
    'preconditions': ('precondicoes',),
    'knowledge': ('knowledge exigido',),
    'relations-state': ('relacoes/estado relevantes',),
    'opening': ('abertura', 'entrada padrao'),
    'branches': ('ramos',),
    'outputs': ('saidas do dialogo',),
    'intents': ('intents/consequencias solicitadas',),
    'arcs-quests': ('arcos/quests relacionados',),
    'related-entities': ('npcs/faccoes/locais/evidencias/eventos relacionados',),
    'invariants': ('invariantes',),
    'voice-notes': ('notas de voz',),
    'qa': ('qa',),
    'internal-spoilers': ('spoilers internos',),
}


class Issue(NamedTuple):
    code: str
    detail: str
    path: pathlib.Path
    line: int


def _normalize(value: str) -> str:
    value = unicodedata.normalize('NFKD', value)
    value = ''.join(ch for ch in value if not unicodedata.combining(ch))
    value = value.casefold().strip()
    return re.sub(r'\s+', ' ', value)


def _dialogue_files(root: pathlib.Path):
    root = pathlib.Path(root)
    return sorted(
        path for path in root.rglob('*.md')
        if path.is_file() and DLG_FILE_RE.match(path.name)
    )


def _sections(text: str):
    lines = text.splitlines()
    found = []
    for index, line in enumerate(lines):
        if not line.startswith('## '):
            continue
        heading = line[3:].strip()
        end = len(lines)
        for next_index in range(index + 1, len(lines)):
            if lines[next_index].startswith('## '):
                end = next_index
                break
        body = '\n'.join(lines[index + 1:end]).strip()
        found.append((_normalize(heading), body, index + 1))
    return found


def _find_section(sections, prefixes):
    for heading, body, line in sections:
        if any(heading.startswith(prefix) for prefix in prefixes):
            return body, line
    return None


def validate_dialogue(path: pathlib.Path) -> list[Issue]:
    text = path.read_text(encoding='utf-8')
    issues: list[Issue] = []

    if not TITLE_RE.search(text):
        issues.append(Issue('missing-dialogue-title', 'DLG-####', path, 1))

    placeholder = PLACEHOLDER_RE.search(text)
    if placeholder:
        line = text[:placeholder.start()].count('\n') + 1
        issues.append(Issue('placeholder-id', placeholder.group(0), path, line))

    sections = _sections(text)
    located = {}
    for detail, prefixes in REQUIRED_GROUPS.items():
        result = _find_section(sections, prefixes)
        if result is None:
            issues.append(Issue('missing-section', detail, path, 1))
            continue
        body, line = result
        located[detail] = (body, line)
        if not body:
            issues.append(Issue('empty-section', detail, path, line))

    qa = located.get('qa')
    if qa and qa[0] and not CHECKBOX_RE.search(qa[0]):
        issues.append(Issue('qa-without-checkbox', 'qa', path, qa[1]))

    return sorted(issues, key=lambda i: (str(i.path), i.line, i.code, i.detail))


def validate(root: pathlib.Path) -> list[Issue]:
    root = pathlib.Path(root)
    issues = []
    for path in _dialogue_files(root):
        issues.extend(validate_dialogue(path))
    return sorted(issues, key=lambda i: (str(i.path), i.line, i.code, i.detail))


def main(argv=None) -> int:
    import argparse

    parser = argparse.ArgumentParser(description='Validate dialogue Markdown structure.')
    parser.add_argument(
        'root',
        nargs='?',
        default='historia/12-dialogos',
        help='dialogue root directory (default: historia/12-dialogos)',
    )
    parser.add_argument(
        '--reveal',
        action='store_true',
        help='show dialogue details and paths for editorial debugging',
    )
    args = parser.parse_args(argv)

    root = pathlib.Path(args.root)
    issues = validate(root)
    if issues and args.reveal:
        for issue in issues:
            try:
                display = issue.path.relative_to(root)
            except ValueError:
                display = issue.path
            print(f'ERROR {issue.code} {issue.detail} {display}:{issue.line}')
    elif issues:
        counts: dict[str, int] = {}
        for issue in issues:
            counts[issue.code] = counts.get(issue.code, 0) + 1
        for code, count in sorted(counts.items()):
            print(f'ERROR {code}: {count}')
        print('Details hidden by spoiler-safe mode. Use --reveal for editorial debugging.')
    else:
        print('OK no dialogue structure issues found')
    return 1 if issues else 0


if __name__ == '__main__':
    raise SystemExit(main())
