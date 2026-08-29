#!/usr/bin/env python3
from pathlib import Path

root = Path(__file__).resolve().parents[2]
runtime = root / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumWorldCatalog.java'
events = root / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumWorldCatalogEvents.java'
mod = root / 'src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java'
for path in (runtime, events):
    if not path.is_file():
        raise SystemExit(f'{path.name} is required')
runtime_text = runtime.read_text(encoding='utf-8')
events_text = events.read_text(encoding='utf-8')
mod_text = mod.read_text(encoding='utf-8')
for token in ['WorldCatalogCoverage.compare', 'coverage.complete()', 'CURRENT = candidate']:
    if token not in runtime_text:
        raise SystemExit(f'RuntimeCompendiumWorldCatalog must contain {token}')
if runtime_text.index('coverage.complete()') > runtime_text.index('CURRENT = candidate'):
    raise SystemExit('world catalog must validate before publication')
if 'ServerStartedEvent' not in events_text or 'RuntimeCompendiumWorldCatalog.publish' not in events_text:
    raise SystemExit('CompendiumWorldCatalogEvents must publish from ServerStartedEvent')
if 'CompendiumWorldCatalogEvents.class' not in mod_text:
    raise SystemExit('RpgSkillTreeMod must register CompendiumWorldCatalogEvents')
for forbidden in ['PlayerTickEvent', 'net.minecraft.client']:
    if forbidden in runtime_text or forbidden in events_text:
        raise SystemExit(f'forbidden world publication pattern: {forbidden}')
print('Compendium world catalog publication validation: PASS')
