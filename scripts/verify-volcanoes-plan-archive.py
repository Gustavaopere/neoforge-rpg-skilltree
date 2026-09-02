#!/usr/bin/env python3
"""Keep completed Volcanoes planning history out of the active plans surface."""

from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
OLD_PLAN_DIR = ROOT / "plans" / "volcanoes"
ARCHIVE_DIR = ROOT / "docs" / "archive" / "volcanoes"
NEEDLE = "/".join(("plans", "volcanoes"))
TEXT_SUFFIXES = {
    ".gradle", ".java", ".json", ".md", ".properties", ".py", ".sh",
    ".toml", ".txt", ".xml", ".yaml", ".yml",
}


def is_under(path: Path, parent: Path) -> bool:
    try:
        path.relative_to(parent)
        return True
    except ValueError:
        return False


def main() -> int:
    failures: list[str] = []

    if OLD_PLAN_DIR.exists():
        failures.append("completed Volcanoes planning subtree still exists under active plans")
    if not ARCHIVE_DIR.is_dir():
        failures.append("Volcanoes historical planning archive is missing")

    stale: list[str] = []
    for path in ROOT.rglob("*"):
        if not path.is_file() or path.suffix.lower() not in TEXT_SUFFIXES:
            continue
        if is_under(path, ARCHIVE_DIR):
            continue
        try:
            text = path.read_text(encoding="utf-8")
        except UnicodeDecodeError:
            continue
        if NEEDLE in text:
            stale.append(path.relative_to(ROOT).as_posix())

    if stale:
        failures.append("active files still reference the retired planning path: " + ", ".join(sorted(stale)))

    if failures:
        print("VOLCANOES_PLAN_ARCHIVE status=FAIL")
        for failure in failures:
            print(f"- {failure}")
        return 1

    archived_files = sum(1 for path in ARCHIVE_DIR.rglob("*") if path.is_file())
    print(
        "VOLCANOES_PLAN_ARCHIVE status=GREEN "
        f"archive={ARCHIVE_DIR.relative_to(ROOT).as_posix()} files={archived_files} stale_refs=0"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
