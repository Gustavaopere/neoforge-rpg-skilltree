#!/usr/bin/env python3
"""Generate the complete Stage 10.02 inventory from modlist + runtime snapshot."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
import sys

from inventory_modlist import InventoryError, parse_modlist_bytes, render_markdown as render_modlist_markdown
from inventory_runtime_report import (
    ReportError,
    build_report,
    load_overrides,
    read_json,
    render_markdown as render_coverage_markdown,
    validate_runtime,
)


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("modlist", type=Path, help="canonical modlist text snapshot")
    parser.add_argument("runtime", type=Path, help="runtime-registry-inventory.json from the loaded pack")
    parser.add_argument("--output-dir", type=Path, default=Path("generated/compendium"))
    parser.add_argument("--overrides", type=Path, default=None)
    parser.add_argument("--previous", type=Path, default=None)
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    try:
        modlist = parse_modlist_bytes(args.modlist.read_bytes(), args.modlist.name)
        runtime = validate_runtime(read_json(args.runtime), args.runtime)
        overrides = load_overrides(args.overrides)
        previous = validate_runtime(read_json(args.previous), args.previous) if args.previous else None
        report = build_report(runtime, overrides, previous, modlist)

        output = args.output_dir.resolve()
        output.mkdir(parents=True, exist_ok=True)
        (output / "modpack-inventory.json").write_text(
            json.dumps(modlist, indent=2, ensure_ascii=False) + "\n",
            encoding="utf-8",
        )
        (output / "modpack-inventory.md").write_text(
            render_modlist_markdown(modlist),
            encoding="utf-8",
        )
        (output / "coverage-report.json").write_text(
            json.dumps(report, indent=2, ensure_ascii=False) + "\n",
            encoding="utf-8",
        )
        (output / "coverage-report.md").write_text(
            render_coverage_markdown(report),
            encoding="utf-8",
        )
    except (OSError, InventoryError, ReportError) as exc:
        print(f"Compendium inventory pipeline: FAIL: {exc}", file=sys.stderr)
        return 1

    errors = report["coverage_totals"]["ERROR"]
    if errors:
        print(f"Compendium inventory pipeline: ERROR ({errors} coverage errors)", file=sys.stderr)
        return 2
    print(
        "Compendium inventory pipeline: PASS "
        f"({modlist['parsed_top_level_count']} top-level mods, {len(report['entries'])} runtime entries)"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
