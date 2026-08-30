#!/usr/bin/env python3
from __future__ import annotations

import argparse
import sys
from pathlib import Path

from wiki_catalog import WikiCatalogDriftError, update_catalog_documents


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Regenera os blocos factuais da wiki a partir dos dados canônicos do RPG Skill Tree."
    )
    parser.add_argument(
        "--check",
        action="store_true",
        help="não grava arquivos; retorna erro se os blocos gerados estiverem desatualizados",
    )
    parser.add_argument(
        "--locale",
        default="pt_br",
        help="locale usado para nomes/descrições existentes (padrão: pt_br)",
    )
    args = parser.parse_args()

    root = Path(__file__).resolve().parents[1]
    try:
        changed = update_catalog_documents(root, locale=args.locale, check=args.check)
    except (ValueError, FileNotFoundError, KeyError, WikiCatalogDriftError) as failure:
        print(f"Wiki catalog generation: FAIL: {failure}", file=sys.stderr)
        return 1

    if args.check:
        print("Wiki catalog drift check: PASS")
    elif changed:
        for path in changed:
            print(f"Updated {path.relative_to(root)}")
    else:
        print("Wiki catalog generation: already up to date")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
