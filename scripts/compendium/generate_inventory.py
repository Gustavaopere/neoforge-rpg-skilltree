#!/usr/bin/env python3
"""Generate Stage 10.02 inventory plus the Stage 10.10 editorial backlog."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
import sys

from editorial_backlog import (
    BacklogError,
    build_backlog,
    load_priority_overrides,
    render_markdown as render_backlog_markdown,
    validate_previous as validate_previous_backlog,
)
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
    parser.add_argument("--overrides", type=Path, default=None, help="Stage 10.02 coverage overrides")
    parser.add_argument("--previous", type=Path, default=None, help="previous runtime inventory for Stage 10.02 drift")
    parser.add_argument(
        "--previous-editorial-backlog",
        type=Path,
        default=None,
        help="alternate previous Stage 10.10 backlog; existing output backlog is reused by default",
    )
    parser.add_argument(
        "--priority-overrides",
        type=Path,
        default=None,
        help="explicit Stage 10.10 editorial priority overrides with reasons",
    )
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    try:
        output = args.output_dir.resolve()
        existing_editorial_backlog = output / "editorial-backlog.json"
        previous_editorial_path = args.previous_editorial_backlog
        if previous_editorial_path is None and existing_editorial_backlog.is_file():
            previous_editorial_path = existing_editorial_backlog

        modlist = parse_modlist_bytes(args.modlist.read_bytes(), args.modlist.name)
        runtime = validate_runtime(read_json(args.runtime), args.runtime)
        overrides = load_overrides(args.overrides)
        previous = validate_runtime(read_json(args.previous), args.previous) if args.previous else None
        report = build_report(runtime, overrides, previous, modlist)

        previous_editorial = (
            validate_previous_backlog(
                read_json(previous_editorial_path),
                previous_editorial_path,
            )
            if previous_editorial_path
            else None
        )
        priority_overrides = load_priority_overrides(args.priority_overrides)
        backlog = build_backlog(report, previous_editorial, priority_overrides)

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
        (output / "editorial-backlog.json").write_text(
            json.dumps(backlog, indent=2, ensure_ascii=False) + "\n",
            encoding="utf-8",
        )
        (output / "editorial-backlog.md").write_text(
            render_backlog_markdown(backlog),
            encoding="utf-8",
        )
    except (OSError, InventoryError, ReportError, BacklogError) as exc:
        print(f"Compendium inventory pipeline: FAIL: {exc}", file=sys.stderr)
        return 1

    errors = report["coverage_totals"]["ERROR"]
    if errors:
        print(f"Compendium inventory pipeline: ERROR ({errors} coverage errors)", file=sys.stderr)
        return 2
    print(
        "Compendium inventory pipeline: PASS "
        f"({modlist['parsed_top_level_count']} top-level mods, {len(report['entries'])} runtime entries, "
        f"{backlog['entry_count']} editorial backlog entries)"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
