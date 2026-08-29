#!/usr/bin/env python3
from pathlib import Path
root = Path(__file__).resolve().parents[2]
path = root / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumWorldDiscoveryEvents.java'
mod = root / 'src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java'
if not path.is_file():
    raise SystemExit('CompendiumWorldDiscoveryEvents.java is required')
text = path.read_text(encoding='utf-8')
for token in ['PlayerTickEvent.Post', 'tickCount % 100', 'getStructureWithPieceAt', 'STRUCTURE_ENTRY', 'getKey(start.getStructure())', 'player.blockPosition()']:
    if token not in text:
        raise SystemExit(f'CompendiumWorldDiscoveryEvents must contain {token}')
if 'CompendiumWorldDiscoveryEvents.class' not in mod.read_text(encoding='utf-8'):
    raise SystemExit('RpgSkillTreeMod must register CompendiumWorldDiscoveryEvents')
for forbidden in ['Serverbound', 'CustomPacketPayload', 'requestedStructureId', 'net.minecraft.client']:
    if forbidden in text:
        raise SystemExit(f'forbidden structure discovery pattern: {forbidden}')
print('Compendium world discovery validation: PASS')
