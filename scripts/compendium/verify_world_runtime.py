#!/usr/bin/env python3
from pathlib import Path
import sys

root = Path(__file__).resolve().parents[2]
path = root / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeWorldCatalogCollector.java'
if not path.is_file():
    raise SystemExit('RuntimeWorldCatalogCollector.java is required')
text = path.read_text(encoding='utf-8')
required = ['Registries.BIOME', 'Registries.STRUCTURE', 'registryAccess()', 'possibleBiomes()', '.biomes()']
for token in required:
    if token not in text:
        raise SystemExit(f'RuntimeWorldCatalogCollector must use {token}')
for forbidden in ['net.minecraft.client', 'EntityType.create', 'randomTick(', 'PlayerTickEvent', 'translationKey.contains', 'getDescriptionId().contains']:
    if forbidden in text:
        raise SystemExit(f'forbidden world collector pattern: {forbidden}')
print('Compendium world runtime validation: PASS')
