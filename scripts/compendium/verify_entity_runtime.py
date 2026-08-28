#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[2]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeEntityCatalogCollector.java"
RUNTIME_INSPECTOR = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeEntityInstanceInspector.java"
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

    require(RUNTIME_INSPECTOR.is_file(), f"missing runtime entity instance inspector: {RUNTIME_INSPECTOR}")
    inspection = RUNTIME_INSPECTOR.read_text(encoding="utf-8")
    require("EntityInstanceInspector.inspect" in inspection, "runtime inspector must project through the whitelist inspector")
    require("BuiltInRegistries.ENTITY_TYPE.getKey" in inspection, "runtime inspector must derive identity from the registry")
    require("getActiveEffects" in inspection, "runtime inspector must expose bounded active effects")
    require("getHealth" in inspection and "getMaxHealth" in inspection, "runtime inspector must expose current/max health")
    require("net.minecraft.client" not in inspection, "runtime entity inspector must not import client classes")
    for forbidden in ("CompoundTag", "getPersistentData", "saveWithoutId", "saveAsPassenger", "save("):
        require(forbidden not in inspection, f"runtime entity inspector must not expose arbitrary entity data: {forbidden}")

    for path in sorted(ENTITY_PROVIDER.glob("*.java")):
        provider_text = path.read_text(encoding="utf-8")
        require("net.minecraft.client" not in provider_text, f"client import leaked into {path.name}")
        require("CompoundTag" not in provider_text, f"NBT surface leaked into {path.name}")

    print("Compendium entity runtime validation: PASS")


if __name__ == "__main__":
    main()
