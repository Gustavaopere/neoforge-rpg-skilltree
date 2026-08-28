#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
path = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeFloraCatalogCollector.java"
if not path.exists():
    raise SystemExit("RuntimeFloraCatalogCollector.java is required")
text = path.read_text(encoding="utf-8")

required = [
    "BuiltInRegistries.BLOCK",
    "FloraClassifier.classify",
    "CropBlock",
    "SaplingBlock",
]
for token in required:
    if token not in text:
        raise SystemExit(f"flora runtime collector missing required contract: {token}")

for forbidden in [
    "net.minecraft.client",
    ".randomTick(",
    ".setBlock(",
    ".setBlockAndUpdate(",
    "getDescriptionId()",
    "getName()",
    "CompoundTag",
]:
    if forbidden in text:
        raise SystemExit(f"flora runtime collector contains forbidden surface: {forbidden}")

if "BuiltInRegistries.BLOCK.getKey" not in text:
    raise SystemExit("collector must derive technical identity from the block registry")

print("Compendium flora runtime validation: PASS")
