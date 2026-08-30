#!/usr/bin/env python3
from __future__ import annotations

import argparse
import json
from pathlib import Path
import re
import sys

TRANSLATION_KEY = re.compile(
    r'(?<![A-Za-z0-9_.-])'
    r'((?:key(?:\.categories)?|screen|tree|node|class|choice|message|item|block|entity|effect|attribute|tooltip|advancement|gui|category|death|command)'
    r'\.rpgskilltree(?:\.[a-z0-9_.-]+)*)'
)
SOURCE_SUFFIXES = {".java", ".json"}


class LocalizationValidationError(ValueError):
    pass


def load_locale(path: Path, label: str) -> dict[str, str]:
    try:
        payload = json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError) as failure:
        raise LocalizationValidationError(f"{label}: could not read valid JSON from {path}: {failure}") from failure
    if not isinstance(payload, dict):
        raise LocalizationValidationError(f"{label}: root must be a JSON object: {path}")
    invalid = sorted(key for key, value in payload.items() if not isinstance(key, str) or not isinstance(value, str))
    if invalid:
        raise LocalizationValidationError(f"{label}: every localization entry must map string to string: {', '.join(invalid)}")
    return payload


def source_translation_keys(roots: list[Path]) -> set[str]:
    keys: set[str] = set()
    for root in roots:
        if not root.exists():
            raise LocalizationValidationError(f"source root does not exist: {root}")
        paths = [root] if root.is_file() else sorted(path for path in root.rglob("*") if path.is_file())
        for path in paths:
            if path.suffix not in SOURCE_SUFFIXES:
                continue
            if path.name in {"pt_br.json", "en_us.json"} and path.parent.name == "lang":
                continue
            try:
                text = path.read_text(encoding="utf-8")
            except UnicodeDecodeError:
                continue
            keys.update(match.group(1) for match in TRANSLATION_KEY.finditer(text))
    return keys


def validate(pt_br: Path, en_us: Path, source_roots: list[Path]) -> tuple[int, int]:
    pt = load_locale(pt_br, "pt_br")
    en = load_locale(en_us, "en_us")
    pt_keys = set(pt)
    en_keys = set(en)
    errors: list[str] = []

    missing_pt = sorted(en_keys - pt_keys)
    if missing_pt:
        errors.append("pt_br is missing keys present in en_us: " + ", ".join(missing_pt))

    missing_en = sorted(pt_keys - en_keys)
    if missing_en:
        errors.append("en_us is missing explicit fallback keys present in pt_br: " + ", ".join(missing_en))

    referenced = source_translation_keys(source_roots)
    missing_source_pt = sorted(referenced - pt_keys)
    missing_source_en = sorted(referenced - en_keys)
    if missing_source_pt:
        errors.append("source-referenced project keys missing from pt_br: " + ", ".join(missing_source_pt))
    if missing_source_en:
        errors.append("source-referenced project keys missing from en_us: " + ", ".join(missing_source_en))

    if errors:
        raise LocalizationValidationError("\n".join(errors))
    return len(pt_keys), len(referenced)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Validate RPG Skill Tree pt-BR localization completeness and explicit en-US fallback parity.")
    parser.add_argument("--pt-br", type=Path, required=True)
    parser.add_argument("--en-us", type=Path, required=True)
    parser.add_argument("--source-root", action="append", type=Path, default=[])
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    try:
        localized_count, referenced_count = validate(args.pt_br, args.en_us, args.source_root)
    except LocalizationValidationError as failure:
        print(f"PT-BR localization validation: FAIL\n{failure}", file=sys.stderr)
        return 1
    print(
        "PT-BR localization validation: PASS "
        f"({localized_count} localized keys, {referenced_count} source-referenced keys)"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
