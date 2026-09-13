from __future__ import annotations

import pathlib
import re
from typing import NamedTuple

ENTITY_TYPES = ('HIST', 'ARC', 'NPC', 'QST', 'FAC', 'SET', 'LOC', 'EVT', 'EVD', 'END', 'DLG')
ID_RE = re.compile(r'\b(?:' + '|'.join(ENTITY_TYPES) + r')-\d{4}\b')
DECL_RE = re.compile(r'^#\s+((?:' + '|'.join(ENTITY_TYPES) + r')-\d{4})\b')


class Issue(NamedTuple):
    code: str
    ref: str
    path: pathlib.Path
    line: int


def _markdown_files(root: pathlib.Path):
    return sorted(path for path in root.rglob('*.md') if path.is_file())


def validate(root: pathlib.Path) -> list[Issue]:
    root = pathlib.Path(root)
    declarations: dict[str, list[tuple[pathlib.Path, int]]] = {}
    references: list[tuple[str, pathlib.Path, int]] = []

    for path in _markdown_files(root):
        text = path.read_text(encoding='utf-8')
        for line_no, line in enumerate(text.splitlines(), start=1):
            decl = DECL_RE.match(line)
            if decl:
                ref = decl.group(1)
                declarations.setdefault(ref, []).append((path, line_no))
            for ref in ID_RE.findall(line):
                references.append((ref, path, line_no))

    issues: list[Issue] = []
    for ref, locations in sorted(declarations.items()):
        if len(locations) > 1:
            for path, line_no in locations[1:]:
                issues.append(Issue('duplicate-id', ref, path, line_no))

    declared_ids = set(declarations)
    seen_unresolved: set[tuple[str, pathlib.Path, int]] = set()
    for ref, path, line_no in references:
        key = (ref, path, line_no)
        if ref not in declared_ids and key not in seen_unresolved:
            seen_unresolved.add(key)
            issues.append(Issue('unresolved-ref', ref, path, line_no))

    return sorted(issues, key=lambda i: (str(i.path), i.line, i.code, i.ref))


def exit_code(issues: list[Issue], strict_references: bool = False) -> int:
    if any(issue.code == 'duplicate-id' for issue in issues):
        return 1
    if strict_references and any(issue.code == 'unresolved-ref' for issue in issues):
        return 1
    return 0


def main(argv=None) -> int:
    import argparse

    parser = argparse.ArgumentParser(description='Validate stable IDs and references in historia Markdown files.')
    parser.add_argument('root', nargs='?', default='historia', help='story root directory (default: historia)')
    parser.add_argument(
        '--strict-references',
        action='store_true',
        help='treat unresolved references as errors instead of warnings',
    )
    parser.add_argument(
        '--reveal',
        action='store_true',
        help='show IDs, paths and line numbers for editorial debugging',
    )
    args = parser.parse_args(argv)

    root = pathlib.Path(args.root)
    issues = validate(root)
    if issues:
        if args.reveal:
            for issue in issues:
                level = 'ERROR' if issue.code == 'duplicate-id' or args.strict_references else 'WARN'
                try:
                    display_path = issue.path.relative_to(root)
                except ValueError:
                    display_path = issue.path
                print(f'{level} {issue.code} {issue.ref} {display_path}:{issue.line}')
        else:
            counts: dict[tuple[str, str], int] = {}
            for issue in issues:
                level = 'ERROR' if issue.code == 'duplicate-id' or args.strict_references else 'WARN'
                key = (level, issue.code)
                counts[key] = counts.get(key, 0) + 1
            for (level, code), count in sorted(counts.items()):
                print(f'{level} {code}: {count}')
            print('Details hidden by spoiler-safe mode. Use --reveal for editorial debugging.')
    else:
        print('OK no story ID/reference issues found')
    return exit_code(issues, strict_references=args.strict_references)


if __name__ == '__main__':
    raise SystemExit(main())
