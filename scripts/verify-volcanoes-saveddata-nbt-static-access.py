#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
TARGETS = (
    ROOT / "src/main/java/dev/gustavopere/volcanoes/geology/DepositRegistry.java",
    ROOT / "src/main/java/dev/gustavopere/volcanoes/tectonics/TectonicRegionState.java",
)


def main() -> None:
    failures: list[str] = []
    for path in TARGETS:
        source = path.read_text(encoding="utf-8")
        relative = path.relative_to(ROOT)
        if "CompoundTag.TAG_INT" in source:
            failures.append(
                f"{relative}: reference TAG_INT through its declaring type net.minecraft.nbt.Tag"
            )
        if "Tag.TAG_INT" in source and "import net.minecraft.nbt.Tag;" not in source:
            failures.append(f"{relative}: Tag.TAG_INT requires an explicit net.minecraft.nbt.Tag import")

    if failures:
        raise SystemExit("\n".join(failures))

    print("Volcanoes SavedData NBT static-access contract: PASS")


if __name__ == "__main__":
    main()
