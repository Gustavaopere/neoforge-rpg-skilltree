#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[2]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeEntityCatalogCollector.java"
ENTITY_PROVIDER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    require(RUNTIME.is_file(), f"missing runtime entity collector: {RUNTIME}")
    text = RUNTIME.read_text(encoding="utf-8")
    require("BuiltInRegistries.ENTITY_TYPE" in text, "runtime entity collector must enumerate ENTITY_TYPE registry")
    require("DefaultAttributes.hasSupplier" in text, "runtime entity collector must guard default attribute lookup")
    require("DefaultAttributes.getSupplier" in text, "runtime entity collector must use public DefaultAttributes supplier lookup")
    require("CommonHooks.getAttributesView" not in text, "do not call deprecated internal CommonHooks.getAttributesView directly")
    require("net.minecraft.client" not in text, "runtime entity collector must not import client classes")
    require("EntityType.create" not in text, "runtime entity collector must not construct arbitrary entities")
    require(not re.search(r"\.create\s*\(\s*[^)]*Level", text), "runtime entity collector appears to construct entities")

    for path in sorted(ENTITY_PROVIDER.glob("*.java")):
        provider_text = path.read_text(encoding="utf-8")
        require("net.minecraft.client" not in provider_text, f"client import leaked into {path.name}")

    print("Compendium entity runtime validation: PASS")


if __name__ == "__main__":
    main()
