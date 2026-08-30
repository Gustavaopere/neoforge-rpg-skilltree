#!/usr/bin/env python3
from __future__ import annotations

import argparse
import json
from pathlib import Path

from wiki_catalog import build_content_coverage


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Audit factual localization and declarative-effect coverage for skill-tree nodes."
    )
    parser.add_argument("--root", type=Path, default=Path(__file__).resolve().parents[1])
    parser.add_argument("--locale", default="pt_br")
    parser.add_argument("--tree", dest="tree_id", default="rpgskilltree:main")
    parser.add_argument("--json", action="store_true", help="Print the complete deterministic audit as JSON.")
    args = parser.parse_args()

    audit = build_content_coverage(args.root, locale=args.locale, tree_id=args.tree_id)
    if args.json:
        print(json.dumps(audit, ensure_ascii=False, indent=2, sort_keys=True))
        return 0

    summary = audit["summary"]
    print(f"Content coverage audit: tree={args.tree_id} locale={args.locale}")
    for key in (
        "total_nodes",
        "localized_names",
        "localized_descriptions",
        "nodes_missing_names",
        "nodes_missing_descriptions",
        "nodes_with_declarative_effects",
        "nodes_without_declarative_effects",
    ):
        print(f"{key}={summary[key]}")
    print(
        "note=nodes_without_declarative_effects are review candidates only; "
        "runtime-backed Java behavior may still exist"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
