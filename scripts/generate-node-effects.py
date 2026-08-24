#!/usr/bin/env python3
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / 'generated/main-tree-layout.json'
OUTPUT = ROOT / 'src/main/resources/data/rpgskilltree/node_effects/main.json'

layout = json.loads(LAYOUT.read_text())
nodes = layout['nodes']
by_domain = {}
for node in nodes:
    domain = node.get('domain')
    if domain:
        by_domain.setdefault(domain, []).append(node)

# Three early nodes give each region immediate mechanical identity.
BASE = {
    'MARTIAL': [
        ('minecraft:attack_damage', 'ADD_FLAT', 0.35, 'attack_damage'),
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.02, 'attack_speed'),
        ('apothic_attributes:armor_pierce', 'ADD_PERCENT_BASE', 0.02, 'armor_pierce'),
    ],
    'VITALITY': [
        ('minecraft:max_health', 'ADD_FLAT', 1.0, 'max_health'),
        ('minecraft:armor', 'ADD_FLAT', 0.5, 'armor'),
        ('minecraft:knockback_resistance', 'ADD_FLAT', 0.02, 'knockback_resistance'),
    ],
    'HEALING': [
        ('apothic_attributes:healing_received', 'ADD_PERCENT_BASE', 0.03, 'healing_received'),
        ('irons_spellbooks:holy_spell_power', 'ADD_PERCENT_BASE', 0.025, 'holy_power'),
        ('irons_spellbooks:spell_resist', 'ADD_PERCENT_BASE', 0.02, 'spell_resist'),
    ],
    'ARCANE': [
        ('irons_spellbooks:max_mana', 'ADD_FLAT', 20.0, 'max_mana'),
        ('irons_spellbooks:spell_power', 'ADD_PERCENT_BASE', 0.025, 'spell_power'),
        ('irons_spellbooks:mana_regen', 'ADD_PERCENT_BASE', 0.03, 'mana_regen'),
    ],
    'ENGINEERING': [
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.015, 'tooling_speed'),
        ('minecraft:luck', 'ADD_FLAT', 0.25, 'precision'),
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.01, 'field_mobility'),
    ],
    'MINING': [
        ('apothic_attributes:mining_speed', 'ADD_PERCENT_BASE', 0.04, 'mining_speed'),
        ('minecraft:luck', 'ADD_FLAT', 0.25, 'prospecting_luck'),
        ('apothic_attributes:armor_pierce', 'ADD_PERCENT_BASE', 0.015, 'hardness_pierce'),
    ],
    'SURVIVAL': [
        ('minecraft:max_health', 'ADD_FLAT', 0.75, 'hardiness'),
        ('minecraft:armor', 'ADD_FLAT', 0.35, 'field_armor'),
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.01, 'terrain_mobility'),
    ],
    'SUMMONING': [
        ('irons_spellbooks:summon_damage', 'ADD_PERCENT_BASE', 0.03, 'summon_damage'),
        ('irons_spellbooks:max_mana', 'ADD_FLAT', 12.0, 'summon_reserve'),
        ('irons_spellbooks:mana_regen', 'ADD_PERCENT_BASE', 0.02, 'summon_regen'),
    ],
    'OCCULT': [
        ('irons_spellbooks:blood_spell_power', 'ADD_PERCENT_BASE', 0.03, 'blood_power'),
        ('irons_spellbooks:eldritch_spell_power', 'ADD_PERCENT_BASE', 0.03, 'eldritch_power'),
        ('irons_spellbooks:spell_power', 'ADD_PERCENT_BASE', 0.015, 'occult_power'),
    ],
    'LOGISTICS': [
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.015, 'movement_speed'),
        ('minecraft:luck', 'ADD_FLAT', 0.2, 'routing_luck'),
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.01, 'handling_speed'),
    ],
    'AGILITY': [
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.02, 'movement_speed'),
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.02, 'attack_speed'),
        ('apothic_attributes:dodge_chance', 'ADD_PERCENT_BASE', 0.02, 'dodge_chance'),
    ],
}

# Final 3/3/3 capstones are deliberately stronger per rank.
FINAL = {
    'MARTIAL': [
        ('minecraft:attack_damage', 'ADD_FLAT', 0.75, 'capstone_damage'),
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.03, 'capstone_speed'),
        ('apothic_attributes:armor_pierce', 'ADD_PERCENT_BASE', 0.03, 'capstone_pierce'),
    ],
    'VITALITY': [
        ('minecraft:max_health', 'ADD_FLAT', 2.0, 'capstone_health'),
        ('minecraft:armor', 'ADD_FLAT', 1.0, 'capstone_armor'),
        ('minecraft:knockback_resistance', 'ADD_FLAT', 0.03, 'capstone_stability'),
    ],
    'HEALING': [
        ('apothic_attributes:healing_received', 'ADD_PERCENT_BASE', 0.05, 'capstone_healing'),
        ('irons_spellbooks:holy_spell_power', 'ADD_PERCENT_BASE', 0.04, 'capstone_holy'),
        ('irons_spellbooks:spell_resist', 'ADD_PERCENT_BASE', 0.03, 'capstone_ward'),
    ],
    'ARCANE': [
        ('irons_spellbooks:max_mana', 'ADD_FLAT', 35.0, 'capstone_mana'),
        ('irons_spellbooks:spell_power', 'ADD_PERCENT_BASE', 0.04, 'capstone_power'),
        ('irons_spellbooks:cooldown_reduction', 'ADD_PERCENT_BASE', 0.03, 'capstone_cooldown'),
    ],
    'ENGINEERING': [
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.03, 'capstone_tooling'),
        ('minecraft:luck', 'ADD_FLAT', 0.5, 'capstone_precision'),
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.02, 'capstone_mobility'),
    ],
    'MINING': [
        ('apothic_attributes:mining_speed', 'ADD_PERCENT_BASE', 0.06, 'capstone_mining'),
        ('minecraft:luck', 'ADD_FLAT', 0.5, 'capstone_prospecting'),
        ('apothic_attributes:armor_pierce', 'ADD_PERCENT_BASE', 0.025, 'capstone_hardness'),
    ],
    'SURVIVAL': [
        ('minecraft:armor', 'ADD_FLAT', 1.0, 'capstone_armor'),
        ('minecraft:max_health', 'ADD_FLAT', 1.5, 'capstone_health'),
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.02, 'capstone_terrain'),
    ],
    'SUMMONING': [
        ('irons_spellbooks:summon_damage', 'ADD_PERCENT_BASE', 0.05, 'capstone_summons'),
        ('irons_spellbooks:mana_regen', 'ADD_PERCENT_BASE', 0.04, 'capstone_regen'),
        ('irons_spellbooks:max_mana', 'ADD_FLAT', 20.0, 'capstone_reserve'),
    ],
    'OCCULT': [
        ('irons_spellbooks:blood_spell_power', 'ADD_PERCENT_BASE', 0.05, 'capstone_blood'),
        ('irons_spellbooks:eldritch_spell_power', 'ADD_PERCENT_BASE', 0.05, 'capstone_eldritch'),
        ('irons_spellbooks:spell_power', 'ADD_PERCENT_BASE', 0.03, 'capstone_power'),
    ],
    'LOGISTICS': [
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.03, 'capstone_movement'),
        ('minecraft:luck', 'ADD_FLAT', 0.5, 'capstone_luck'),
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.02, 'capstone_handling'),
    ],
    'AGILITY': [
        ('minecraft:movement_speed', 'ADD_PERCENT_BASE', 0.03, 'capstone_movement'),
        ('minecraft:attack_speed', 'ADD_PERCENT_BASE', 0.03, 'capstone_speed'),
        ('apothic_attributes:dodge_chance', 'ADD_PERCENT_BASE', 0.03, 'capstone_dodge'),
    ],
}

effects = []
for domain, domain_nodes in sorted(by_domain.items()):
    if domain not in BASE or domain not in FINAL:
        raise SystemExit(f'missing effect profile for domain {domain}')
    early = sorted(domain_nodes, key=lambda n: int(n['id'].rsplit('_', 1)[1]))[:3]
    for node, spec in zip(early, BASE[domain]):
        attribute, operation, amount, slug = spec
        effects.append({
            'effectId': f"rpgskilltree:node/{node['id']}/{slug}",
            'nodeId': f"rpgskilltree:{node['id']}",
            'attributeId': attribute,
            'operation': operation,
            'amountPerRank': amount,
        })
    final_nodes = sorted(
        [n for n in domain_nodes if 'finalTriadSlot' in n],
        key=lambda n: n['finalTriadSlot']
    )
    if len(final_nodes) != 3:
        raise SystemExit(f'{domain}: expected 3 final triad nodes, got {len(final_nodes)}')
    for node, spec in zip(final_nodes, FINAL[domain]):
        attribute, operation, amount, slug = spec
        effects.append({
            'effectId': f"rpgskilltree:node/{node['id']}/{slug}",
            'nodeId': f"rpgskilltree:{node['id']}",
            'attributeId': attribute,
            'operation': operation,
            'amountPerRank': amount,
        })

# Frozen Notion A0088-A0090 are explicit relative attribute nodes, independent of layout heuristics.
effects.extend([
    {
        'effectId': 'rpgskilltree:node/combat/a0088/max_health',
        'nodeId': 'rpgskilltree:combat/a0088',
        'attributeId': 'minecraft:max_health',
        'operation': 'MULTIPLY_TOTAL',
        'amountPerRank': 0.02,
    },
    {
        'effectId': 'rpgskilltree:node/combat/a0089/armor',
        'nodeId': 'rpgskilltree:combat/a0089',
        'attributeId': 'minecraft:armor',
        'operation': 'MULTIPLY_TOTAL',
        'amountPerRank': 0.02,
    },
    {
        'effectId': 'rpgskilltree:node/combat/a0090/armor_toughness',
        'nodeId': 'rpgskilltree:combat/a0090',
        'attributeId': 'minecraft:armor_toughness',
        'operation': 'MULTIPLY_TOTAL',
        'amountPerRank': 0.02,
    },
])

OUTPUT.parent.mkdir(parents=True, exist_ok=True)
OUTPUT.write_text(json.dumps({'attributes': effects}, indent=2) + '\n')
print(f'Generated {len(effects)} node attribute effects -> {OUTPUT}')
