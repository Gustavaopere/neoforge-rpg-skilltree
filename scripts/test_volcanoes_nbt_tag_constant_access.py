#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
TARGETS = (
    ROOT / "src/main/java/dev/gustavopere/volcanoes/tectonics/TectonicRegionState.java",
    ROOT / "src/main/java/dev/gustavopere/volcanoes/geology/DepositRegistry.java",
)

BAD_ACCESS = re.compile(r"\bCompoundTag\.TAG_(?:LIST|COMPOUND)\b")


def main() -> None:
    violations: list[str] = []
    for path in TARGETS:
        text = path.read_text(encoding="utf-8")
        for line_number, line in enumerate(text.splitlines(), start=1):
            if BAD_ACCESS.search(line):
                violations.append(f"{path.relative_to(ROOT)}:{line_number}: {line.strip()}")

    if violations:
        print("Non-canonical NBT tag constant access detected; use Tag.TAG_* for constants declared by Tag:")
        for violation in violations:
            print(f"  {violation}")
        raise SystemExit(1)

    print("Volcanoes NBT tag constant access contract: PASS")


if __name__ == "__main__":
    main()
