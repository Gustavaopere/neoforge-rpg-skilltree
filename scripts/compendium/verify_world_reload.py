#!/usr/bin/env python3
from pathlib import Path
root = Path(__file__).resolve().parents[2]
path = root / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumWorldCatalogReloader.java'
mod = root / 'src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java'
if not path.is_file():
    raise SystemExit('CompendiumWorldCatalogReloader.java is required')
text = path.read_text(encoding='utf-8')
for token in ['AddReloadListenerEvent', 'event.addListener', 'ServerLifecycleHooks.getCurrentServer()', 'RuntimeCompendiumWorldCatalog.publish']:
    if token not in text:
        raise SystemExit(f'CompendiumWorldCatalogReloader must contain {token}')
if 'CompendiumWorldCatalogReloader.class' not in mod.read_text(encoding='utf-8'):
    raise SystemExit('RpgSkillTreeMod must register CompendiumWorldCatalogReloader')
for forbidden in ['PlayerTickEvent', 'net.minecraft.client']:
    if forbidden in text:
        raise SystemExit(f'forbidden world reload pattern: {forbidden}')
print('Compendium world reload validation: PASS')
