#!/usr/bin/env python3
from __future__ import annotations

import argparse
from pathlib import Path
import sys

from editorial_corpus import EditorialCorpusError, load_corpus, read_json


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Validate Stage 10.10 pt-BR editorial corpus packages.")
    parser.add_argument("corpus", type=Path, help="root directory containing pt-BR editorial JSON packages")
    parser.add_argument("--coverage", type=Path, required=True, help="Stage 10.02 coverage-report.json")
    parser.add_argument(
        "--release",
        action="store_true",
        help="require every editorial entry to have review_status=REVIEWED",
    )
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    try:
        coverage = read_json(args.coverage)
        corpus = load_corpus(args.corpus, coverage, release=args.release)
    except EditorialCorpusError as exc:
        print(f"Editorial corpus validation: FAIL: {exc}", file=sys.stderr)
        return 1

    print(
        "Editorial corpus validation: PASS "
        f"({len(corpus.entries)} entries from {len(corpus.files)} files)"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
