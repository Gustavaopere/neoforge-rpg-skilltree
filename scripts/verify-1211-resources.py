#!/usr/bin/env python3
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DATA = ROOT / 'src/main/resources/data/rpgskilltree'

VANILLA_1211_ATTRIBUTES = {
    'minecraft:generic.armor',
    'minecraft:generic.armor_toughness',
    'minecraft:generic.attack_damage',
    'minecraft:generic.attack_knockback',
    'minecraft:generic.attack_speed',
    'minecraft:generic.burning_time',
    'minecraft:generic.explosion_knockback_resistance',
    'minecraft:generic.fall_damage_multiplier',
    'minecraft:generic.flying_speed',
    'minecraft:generic.follow_range',
    'minecraft:generic.gravity',
    'minecraft:generic.jump_strength',
    'minecraft:generic.knockback_resistance',
    'minecraft:generic.luck',
    'minecraft:generic.max_absorption',
    'minecraft:generic.max_health',
    'minecraft:generic.movement_efficiency',
    'minecraft:generic.movement_speed',
    'minecraft:generic.oxygen_bonus',
    'minecraft:generic.safe_fall_distance',
    'minecraft:generic.scale',
    'minecraft:generic.step_height',
    'minecraft:generic.water_movement_efficiency',
    'minecraft:player.block_break_speed',
    'minecraft:player.block_interaction_range',
    'minecraft:player.entity_interaction_range',
    'minecraft:player.mining_efficiency',
    'minecraft:player.sneaking_speed',
    'minecraft:player.submerged_mining_speed',
    'minecraft:player.sweeping_damage_ratio',
    'minecraft:zombie.spawn_reinforcements',
}


def validate_entity_type_tag():
    legacy_dir = DATA / 'tags/entity_types'
    if legacy_dir.exists():
        raise AssertionError('Minecraft 1.21 uses tags/entity_type, not tags/entity_types')

    bosses = DATA / 'tags/entity_type/bosses.json'
    if not bosses.exists():
        raise AssertionError('missing 1.21 entity_type boss tag')

    payload = json.loads(bosses.read_text())
    values = payload.get('values')
    if not isinstance(values, list) or not values:
        raise AssertionError('boss tag values must be a non-empty list')

    seen = set()
    cataclysm = 0
    for entry in values:
        if isinstance(entry, str):
            entity_id = entry
            required = True
        elif isinstance(entry, dict):
            entity_id = entry.get('id')
            required = entry.get('required', True)
        else:
            raise AssertionError(f'invalid boss tag entry: {entry!r}')

        if not isinstance(entity_id, str) or ':' not in entity_id:
            raise AssertionError(f'invalid boss entity id: {entity_id!r}')
        if entity_id in seen:
            raise AssertionError(f'duplicate boss entity id: {entity_id}')
        seen.add(entity_id)

        if entity_id.startswith('cataclysm:'):
            cataclysm += 1
            if not isinstance(entry, dict) or required is not False:
                raise AssertionError(f'optional Cataclysm boss must use required=false: {entity_id}')

    if not {'minecraft:ender_dragon', 'minecraft:wither'} <= seen:
        raise AssertionError('boss tag must contain vanilla dragon and wither')
    if cataclysm == 0:
        raise AssertionError('expected curated optional Cataclysm boss entries')


def validate_node_effect_attribute_ids():
    effects_path = DATA / 'node_effects/main.json'
    payload = json.loads(effects_path.read_text())
    effects = payload.get('attributes')
    if not isinstance(effects, list) or not effects:
        raise AssertionError('node effect attribute list must be non-empty')

    vanilla_seen = 0
    for effect in effects:
        attribute_id = effect.get('attributeId')
        if not isinstance(attribute_id, str) or ':' not in attribute_id:
            raise AssertionError(f'invalid node-effect attribute id: {attribute_id!r}')
        if attribute_id.startswith('minecraft:'):
            vanilla_seen += 1
            if attribute_id not in VANILLA_1211_ATTRIBUTES:
                raise AssertionError(f'invalid Minecraft 1.21.1 attribute id: {attribute_id}')

    if vanilla_seen == 0:
        raise AssertionError('expected at least one vanilla attribute effect')


def main():
    validate_entity_type_tag()
    validate_node_effect_attribute_ids()
    print('Minecraft 1.21.1 resource validation: PASS')


if __name__ == '__main__':
    main()
