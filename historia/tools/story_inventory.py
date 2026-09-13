from __future__ import annotations

import json
import pathlib
import re
from collections import Counter
from typing import NamedTuple

ENTITY_TYPES = ('HIST', 'ARC', 'NPC', 'QST', 'FAC', 'SET', 'LOC', 'EVT', 'EVD', 'END', 'DLG')
ID_RE = re.compile(r'\b(?:' + '|'.join(ENTITY_TYPES) + r')-\d{4}\b')
DECL_RE = re.compile(
    r'^#\s+((?:' + '|'.join(ENTITY_TYPES) + r')-\d{4})\b(?:\s*[—-]\s*(.*?))?\s*$',
    re.MULTILINE,
)
STATE_RE = re.compile(r'^##\s+Estado editorial\s*$', re.IGNORECASE | re.MULTILINE)


class Record(NamedTuple):
    id: str
    type: str
    title: str
    state: str
    path: pathlib.Path
    references: tuple[str, ...]


def _markdown_files(root: pathlib.Path):
    root = pathlib.Path(root)
    return sorted(path for path in root.rglob('*.md') if path.is_file())


def _extract_state(text: str) -> str:
    match = STATE_RE.search(text)
    if not match:
        return 'NÃO DECLARADO'

    after = text[match.end():].splitlines()
    for line in after:
        stripped = line.strip()
        if not stripped:
            continue
        if stripped.startswith('#'):
            return 'NÃO DECLARADO'
        return stripped
    return 'NÃO DECLARADO'


def _extract_record(path: pathlib.Path) -> Record | None:
    text = path.read_text(encoding='utf-8')
    decl = DECL_RE.search(text)
    if not decl:
        return None

    record_id = decl.group(1)
    title = (decl.group(2) or '').strip()
    refs = tuple(sorted(set(ID_RE.findall(text)) - {record_id}))
    return Record(
        id=record_id,
        type=record_id.split('-', 1)[0],
        title=title,
        state=_extract_state(text),
        path=path,
        references=refs,
    )


def inventory(root: pathlib.Path) -> list[Record]:
    records = []
    for path in _markdown_files(pathlib.Path(root)):
        record = _extract_record(path)
        if record is not None:
            records.append(record)
    return sorted(records, key=lambda record: record.id)


def _summary(records: list[Record]):
    by_type = Counter(record.type for record in records)
    by_state = Counter(record.state for record in records)
    return {
        'total': len(records),
        'by_type': dict(sorted(by_type.items())),
        'by_state': dict(sorted(by_state.items())),
    }


def _display_path(path: pathlib.Path, root: pathlib.Path) -> str:
    try:
        return str(path.relative_to(root))
    except ValueError:
        return str(path)


def render_markdown(records: list[Record], root: pathlib.Path) -> str:
    root = pathlib.Path(root)
    summary = _summary(records)
    lines = [
        '# Story Inventory',
        '',
        f"Total: {summary['total']}",
        '',
        '## Por tipo',
        '',
    ]
    for entity_type, count in summary['by_type'].items():
        lines.append(f'- {entity_type}: {count}')

    lines.extend([
        '',
        '## Registros',
        '',
        '| ID | Título | Estado editorial | Arquivo | Referências |',
        '| --- | --- | --- | --- | --- |',
    ])

    for record in records:
        refs = ', '.join(record.references) if record.references else '—'
        lines.append(
            f"| {record.id} | {record.title or '—'} | {record.state} | "
            f'{_display_path(record.path, root)} | {refs} |'
        )
    return '\n'.join(lines) + '\n'


def render_json(records: list[Record], root: pathlib.Path) -> str:
    root = pathlib.Path(root)
    payload = {
        'summary': _summary(records),
        'records': [
            {
                'id': record.id,
                'type': record.type,
                'title': record.title,
                'state': record.state,
                'path': _display_path(record.path, root),
                'references': list(record.references),
            }
            for record in records
        ],
    }
    return json.dumps(payload, ensure_ascii=False, indent=2) + '\n'


def main(argv=None) -> int:
    import argparse

    parser = argparse.ArgumentParser(description='Inventory versioned story records.')
    parser.add_argument('root', nargs='?', default='historia', help='story root (default: historia)')
    parser.add_argument('--format', choices=('markdown', 'json'), default='markdown')
    args = parser.parse_args(argv)

    root = pathlib.Path(args.root)
    records = inventory(root)
    if args.format == 'json':
        print(render_json(records, root), end='')
    else:
        print(render_markdown(records, root), end='')
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
