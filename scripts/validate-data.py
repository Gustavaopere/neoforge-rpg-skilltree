#!/usr/bin/env python3
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1] / 'src/main/resources/data/rpgskilltree'
DOMAINS = {'MARTIAL','AGILITY','VITALITY','ARCANE','ENGINEERING','SURVIVAL','SUMMONING','HEALING','MINING','OCCULT','LOGISTICS'}
TREE_TYPES = {'main','specialization','hybrid','provider','class'}
MORPH_CATEGORIES = {'NATURAL_LAND','NATURAL_AQUATIC','NATURAL_FLYING','MAGICAL_NATURAL','HUMANOID','MONSTER','ABERRATION','TECHNICAL'}
# Master taxonomy: these identities must not resolve as primary/secondary classes.
NON_CLASS_ARCHETYPE_IDS = {'rpgskilltree:arcane_archer','rpgskilltree:battlemage','rpgskilltree:tank'}
MIGRATED_CLASS_IDS = {'industrialist','prospector','logistician'}
REQUIRED_MORPH_BLACKLIST = {'minecraft:ender_dragon','minecraft:wither'}


def load_dir(name):
    out = []
    for p in sorted((ROOT / name).glob('*.json')):
        data = json.loads(p.read_text())
        out.append((p, data))
    return out


def nonneg_map(p, d, key):
    m = d.get(key, {})
    if not isinstance(m, dict):
        raise AssertionError(f'{p}: {key} must be object')
    for k, v in m.items():
        if key == 'minimum_domain_scores' and k not in DOMAINS:
            raise AssertionError(f'{p}: unknown domain {k}')
        if not isinstance(k, str) or not k or not isinstance(v, int) or isinstance(v, bool) or v < 0:
            raise AssertionError(f'{p}: invalid {key}.{k}')


def string_list(p, value, key, *, allow_empty=True):
    if not isinstance(value, list):
        raise AssertionError(f'{p}: {key} must be list')
    if not allow_empty and not value:
        raise AssertionError(f'{p}: {key} must not be empty')
    if any(not isinstance(item, str) or not item for item in value):
        raise AssertionError(f'{p}: {key} contains blank/non-string value')
    if len(value) != len(set(value)):
        raise AssertionError(f'{p}: {key} contains duplicates')
    return set(value)


def validate_morph_data():
    files = load_dir('morph_categories')
    if not files:
        raise AssertionError('missing morph category data')

    overrides = {}
    blacklist = set()
    factions_by_entity = {}
    traits_by_entity = {}
    relations = {}
    hostility_memory_seconds = None

    for p, d in files:
        raw_overrides = d.get('overrides', {})
        if not isinstance(raw_overrides, dict):
            raise AssertionError(f'{p}: overrides must be object')
        for entity_id, category in raw_overrides.items():
            if not isinstance(entity_id, str) or ':' not in entity_id:
                raise AssertionError(f'{p}: invalid morph override entity id {entity_id}')
            if category not in MORPH_CATEGORIES:
                raise AssertionError(f'{p}: invalid morph category {category} for {entity_id}')
            if entity_id in overrides:
                raise AssertionError(f'{p}: duplicate morph override {entity_id}')
            overrides[entity_id] = category

        for entity_id in string_list(p, d.get('blacklist', []), 'blacklist'):
            if ':' not in entity_id:
                raise AssertionError(f'{p}: invalid blacklist entity id {entity_id}')
            if entity_id in blacklist:
                raise AssertionError(f'{p}: duplicate blacklist entity across morph files {entity_id}')
            blacklist.add(entity_id)

        for field, target in (('entity_factions', factions_by_entity), ('entity_traits', traits_by_entity)):
            mapping = d.get(field, {})
            if not isinstance(mapping, dict):
                raise AssertionError(f'{p}: {field} must be object')
            for entity_id, values in mapping.items():
                if not isinstance(entity_id, str) or ':' not in entity_id:
                    raise AssertionError(f'{p}: invalid {field} entity id {entity_id}')
                if entity_id in target:
                    raise AssertionError(f'{p}: duplicate {field} entity {entity_id}')
                target[entity_id] = string_list(p, values, f'{field}.{entity_id}')

        raw_relations = d.get('faction_relations', {})
        if not isinstance(raw_relations, dict):
            raise AssertionError(f'{p}: faction_relations must be object')
        for faction_id, relation in raw_relations.items():
            if not isinstance(faction_id, str) or ':' not in faction_id:
                raise AssertionError(f'{p}: invalid faction id {faction_id}')
            if faction_id in relations:
                raise AssertionError(f'{p}: duplicate faction relation {faction_id}')
            if not isinstance(relation, dict):
                raise AssertionError(f'{p}: faction relation {faction_id} must be object')
            allowed = {'allies','enemies','fears'}
            unknown = set(relation) - allowed
            if unknown:
                raise AssertionError(f'{p}: unknown faction relation fields for {faction_id}: {sorted(unknown)}')
            allies = string_list(p, relation.get('allies', []), f'faction_relations.{faction_id}.allies')
            enemies = string_list(p, relation.get('enemies', []), f'faction_relations.{faction_id}.enemies')
            fears = string_list(p, relation.get('fears', []), f'faction_relations.{faction_id}.fears')
            if allies & enemies or allies & fears or enemies & fears:
                raise AssertionError(f'{p}: overlapping ecological relations for {faction_id}')
            relations[faction_id] = (allies, enemies, fears)

        if 'hostility_memory_seconds' in d:
            seconds = d['hostility_memory_seconds']
            if not isinstance(seconds, int) or isinstance(seconds, bool) or seconds <= 0:
                raise AssertionError(f'{p}: hostility_memory_seconds must be positive integer')
            if hostility_memory_seconds is not None and hostility_memory_seconds != seconds:
                raise AssertionError(f'{p}: conflicting hostility_memory_seconds')
            hostility_memory_seconds = seconds

    if not REQUIRED_MORPH_BLACKLIST <= blacklist:
        missing = sorted(REQUIRED_MORPH_BLACKLIST - blacklist)
        raise AssertionError(f'morph blacklist must include {missing}')
    if hostility_memory_seconds != 45:
        raise AssertionError('built-in morph hostility memory must default to 45 seconds')

    known_factions = set(relations)
    for factions in factions_by_entity.values():
        known_factions.update(factions)
    for faction_id, groups in relations.items():
        for target in set().union(*groups):
            if target not in known_factions:
                raise AssertionError(f'unknown morph faction relation target {faction_id} -> {target}')

    return len(overrides), len(factions_by_entity), len(relations)


def main():
    arch = load_dir('archetypes')
    unlocks = load_dir('tree_unlocks')
    classes = load_dir('classes')
    specs = load_dir('specializations')

    ids = set()
    for p, d in arch:
        id_ = d['id']
        if id_ in ids:
            raise AssertionError(f'duplicate id {id_}')
        if id_ in NON_CLASS_ARCHETYPE_IDS:
            raise AssertionError(f'{p}: {id_} is not a canonical class archetype')
        ids.add(id_)
        nonneg_map(p, d, 'minimum_domain_scores')
        if not isinstance(d.get('priority'), int) or isinstance(d.get('priority'), bool):
            raise AssertionError(f'{p}: priority')
        specificity = d.get('specificity_score')
        if not isinstance(specificity, int) or isinstance(specificity, bool) or specificity < 0:
            raise AssertionError(f'{p}: built-in archetype requires non-negative specificity_score')
        required = string_list(p, d.get('required_tags', []), 'required_tags')
        forbidden = string_list(p, d.get('forbidden_tags', []), 'forbidden_tags')
        if required & forbidden:
            raise AssertionError(f'{p}: archetype tag cannot be both required and forbidden')

    tree_ids = set()
    for p, d in unlocks:
        id_ = d['tree_id']
        if id_ in tree_ids:
            raise AssertionError(f'duplicate tree {id_}')
        tree_ids.add(id_)
        nonneg_map(p, d, 'minimum_domain_scores')
        nonneg_map(p, d, 'minimum_mastery_experience')
        string_list(p, d.get('required_tags', []), 'required_tags')

    class_ids = set()
    for p, d in classes:
        id_ = d['class_id']
        if id_ in MIGRATED_CLASS_IDS:
            raise AssertionError(f'{p}: migrated specialization {id_} must not reappear as a class')
        if id_ in class_ids:
            raise AssertionError(f'duplicate class {id_}')
        class_ids.add(id_)
        domains = d.get('required_completed_domains', [])
        if not isinstance(domains, list):
            raise AssertionError(f'{p}: required_completed_domains')
        if any(domain not in DOMAINS for domain in domains):
            raise AssertionError(f'{p}: unknown class domain')
        mastery = d.get('minimum_mastery_experience', {})
        if not isinstance(mastery, dict) or any(
            not isinstance(k, str) or not k or not isinstance(v, int) or isinstance(v, bool) or v <= 0
            for k, v in mastery.items()
        ):
            raise AssertionError(f'{p}: invalid class mastery requirements')
        required_nodes = d.get('required_nodes', [])
        string_list(p, required_nodes, 'required_nodes')
        if not domains and not mastery and not required_nodes:
            raise AssertionError(f'{p}: class needs domain, mastery, or node requirements')
        if d.get('final_triad_rank_per_capstone') != 3:
            raise AssertionError(f'{p}: final triad rank must be 3')
        if d.get('final_triad_capstones_per_domain') != 3:
            raise AssertionError(f'{p}: final triad must have three capstones')
        cost = d.get('non_adjacent_bridge_cost', 0)
        if not isinstance(cost, int) or isinstance(cost, bool) or cost < 0:
            raise AssertionError(f'{p}: non_adjacent_bridge_cost')
        if d.get('adjacent_confluence', False) and cost != 0:
            raise AssertionError(f'{p}: adjacent class cannot have bridge surcharge')
        if len(domains) <= 1 and cost != 0:
            raise AssertionError(f'{p}: pure/provider class cannot have bridge surcharge')

    rewards = json.loads((ROOT / 'boss_rewards/defaults.json').read_text())
    expected_rewards = {'minecraft':3,'cataclysm':5,'apotheosis':2}
    for namespace, points in expected_rewards.items():
        if rewards.get('namespace_defaults', {}).get(namespace) != points:
            raise AssertionError(f'boss reward {namespace} must be {points}')
    if rewards.get('first_kill_only') is not True:
        raise AssertionError('boss rewards must be first-kill-only')

    spec_ids = set()
    specs_by_id = {}
    for p, d in specs:
        sid = d['specialization_id']
        if sid in spec_ids:
            raise AssertionError(f'duplicate specialization {sid}')
        spec_ids.add(sid)
        specs_by_id[sid] = (p, d)
        eligible = d.get('eligible_class_ids', [])
        eligible_set = string_list(p, eligible, 'eligible_class_ids')
        migrated_as_class = eligible_set & MIGRATED_CLASS_IDS
        if migrated_as_class:
            raise AssertionError(f'{p}: migrated specialization ids used as classes: {sorted(migrated_as_class)}')
        if any(cid not in class_ids for cid in eligible):
            raise AssertionError(f'{p}: unknown eligible class')
        mastery = d.get('minimum_mastery_experience', {})
        if not isinstance(mastery, dict) or any(
            not isinstance(k, str) or not k or not isinstance(v, int) or isinstance(v, bool) or v < 0
            for k, v in mastery.items()
        ):
            raise AssertionError(f'{p}: mastery requirements')
        string_list(p, d.get('required_tags', []), 'required_tags')

    expected_combat_specializations = {
        'epic_sword': ('epicfight:sword', 60, 'gateway:epic_sword'),
        'epic_axe': ('epicfight:axe', 60, 'gateway:epic_axe'),
        'epic_spear': ('epicfight:spear', 60, 'gateway:epic_spear'),
        'epic_dagger': ('epicfight:dagger', 60, 'gateway:epic_dagger'),
        'epic_hammer': ('epicfight:heavy', 70, 'gateway:epic_hammer'),
        'combat_mace': ('combat:mace', 60, 'gateway:combat_mace'),
        'combat_scythe': ('combat:scythe', 60, 'gateway:combat_scythe'),
        'epic_bow': ('combat:bow', 60, 'gateway:epic_bow'),
        'epic_crossbow': ('combat:crossbow', 60, 'gateway:epic_crossbow'),
    }
    missing_combat_specs = set(expected_combat_specializations) - set(specs_by_id)
    if missing_combat_specs:
        raise AssertionError(f'missing combat specializations: {sorted(missing_combat_specs)}')
    for sid, (lane, threshold, gateway_tag) in expected_combat_specializations.items():
        p, d = specs_by_id[sid]
        if d.get('eligible_class_ids') != []:
            raise AssertionError(f'{p}: combat gateway specialization must not require a class')
        if d.get('minimum_mastery_experience') != {lane: threshold}:
            raise AssertionError(f'{p}: expected mastery {{{lane!r}: {threshold}}}')
        if d.get('required_tags') != [gateway_tag]:
            raise AssertionError(f'{p}: expected only gateway tag {gateway_tag}')

    pact_dir = ROOT / 'class_choices/warlock_pacts'
    pacts = []
    for p in sorted(pact_dir.glob('*.json')):
        d = json.loads(p.read_text())
        pacts.append((p, d))
        if d.get('required_class_id') != 'warlock':
            raise AssertionError(f'{p}: pact must require warlock')
        if d.get('group_id') != 'warlock:pact':
            raise AssertionError(f'{p}: pact group')
        if not isinstance(d.get('default_group_capacity'), int) or d['default_group_capacity'] < 1:
            raise AssertionError(f'{p}: pact capacity')
    if len(pacts) < 5:
        raise AssertionError('expected at least five warlock pacts')

    techno_rules_path = ROOT / 'node_rules/technomancer.json'
    if not techno_rules_path.exists():
        raise AssertionError('missing technomancer node rules')
    techno = json.loads(techno_rules_path.read_text())
    techno_nodes = {node['id']: node for node in techno.get('nodes', [])}
    if len(techno_nodes) != 17:
        raise AssertionError(f'technomancer subtree must contain 17 nodes, got {len(techno_nodes)}')
    root = techno_nodes.get('rpgskilltree:technomancer/core')
    if root is None or root.get('startingPoint') is not True:
        raise AssertionError('technomancer root must be a starting point')
    if root.get('requiredClasses') != ['technomancer']:
        raise AssertionError('technomancer root must require technomancer class')
    expected_gateways = {
        'rpgskilltree:technomancer/create_gateway':'create_kinetics',
        'rpgskilltree:technomancer/ae2_gateway':'ae2_networks',
        'rpgskilltree:technomancer/oritech_gateway':'oritech_power',
    }
    for node_id, spec_id in expected_gateways.items():
        node = techno_nodes.get(node_id)
        if node is None:
            raise AssertionError(f'missing technomancer gateway {node_id}')
        grant = node.get('grantsSpecialization', {})
        if grant.get('id') != spec_id:
            raise AssertionError(f'{node_id} must grant {spec_id}')
        if not node.get('requiredMastery'):
            raise AssertionError(f'{node_id} must require mastery')
    triune = techno_nodes.get('rpgskilltree:technomancer/triune_core')
    if triune is None:
        raise AssertionError('missing technomancer triune core')
    if set(triune.get('requiredSpecializations', [])) != set(expected_gateways.values()):
        raise AssertionError('technomancer triune core must require all three gateway specializations')

    progression = json.loads((ROOT / 'progression/defaults.json').read_text())
    if progression.get('max_character_level') != 100:
        raise AssertionError('default character level cap must be 100')
    if progression.get('passive_points_per_level') != 1:
        raise AssertionError('default points per level must be 1')
    if progression.get('default_non_adjacent_class_bridge_cost') != 10:
        raise AssertionError('default abnormal bridge cost must be 10')

    if not arch:
        raise AssertionError('expected at least one archetype prototype')
    if len(unlocks) < 15:
        raise AssertionError('expected at least 15 specialized tree gateways')
    if len(classes) < 20:
        raise AssertionError('expected at least 20 alpha 2 class definitions')
    if len(specs) < 20:
        raise AssertionError('expected at least 20 post-class specializations')

    blue = json.loads((ROOT / 'tree_blueprints/main.json').read_text())
    total = sum(r['node_budget'] for r in blue['regions']) + blue['shared_core_nodes'] + blue['hybrid_bridge_nodes'] + blue['outer_keystone_nodes']
    if total != blue['target_node_count']:
        raise AssertionError(f'blueprint node budget {total} != target {blue["target_node_count"]}')
    if set(r['id'] for r in blue['regions']) != DOMAINS:
        raise AssertionError('blueprint must cover all alpha 2 progression domains')
    if blue['target_node_count'] < 500:
        raise AssertionError('alpha 2 main tree must budget at least 500 base nodes')
    for cid in blue.get('dynamic_non_adjacent_classes', []):
        if cid in MIGRATED_CLASS_IDS:
            raise AssertionError(f'migrated specialization cannot remain a dynamic class: {cid}')
        if cid not in class_ids:
            raise AssertionError(f'unknown dynamic class {cid}')

    architecture_files = load_dir('tree_architecture')
    architecture = {}
    for p, architecture_root in architecture_files:
        trees = architecture_root.get('trees', [])
        if not isinstance(trees, list):
            raise AssertionError(f'{p}: trees must be a list')
        for tree in trees:
            tid = tree.get('id')
            if not isinstance(tid, str) or ':' not in tid:
                raise AssertionError(f'{p}: invalid architecture tree id {tid}')
            if tid in architecture:
                raise AssertionError(f'duplicate architecture tree {tid}')
            tree_type = tree.get('type')
            if tree_type not in TREE_TYPES:
                raise AssertionError(f'{p}: invalid tree type {tree_type} for {tid}')
            domains = tree.get('domains', [])
            if not isinstance(domains, list) or any(domain not in DOMAINS for domain in domains):
                raise AssertionError(f'{p}: invalid architecture domains for {tid}')
            provider = tree.get('provider', 'rpgskilltree')
            if not isinstance(provider, str) or not provider:
                raise AssertionError(f'{p}: invalid provider for {tid}')
            branches = tree.get('branches', [])
            if not isinstance(branches, list) or not branches:
                raise AssertionError(f'{p}: {tid} requires branches')
            branch_ids = set()
            for branch in branches:
                bid = branch.get('id')
                label = branch.get('label')
                order = branch.get('order', 0)
                if not isinstance(bid, str) or not bid:
                    raise AssertionError(f'{p}: invalid branch id in {tid}')
                if bid in branch_ids:
                    raise AssertionError(f'{p}: duplicate branch {tid}/{bid}')
                branch_ids.add(bid)
                if not isinstance(label, str) or not label:
                    raise AssertionError(f'{p}: missing branch label {tid}/{bid}')
                if not isinstance(order, int) or order < 0:
                    raise AssertionError(f'{p}: invalid branch order {tid}/{bid}')
                string_list(p, branch.get('tags', []), f'branch tags {tid}/{bid}')
            gate = tree.get('gate', {})
            if not isinstance(gate, dict):
                raise AssertionError(f'{p}: gate must be object for {tid}')
            min_level = gate.get('minimumCharacterLevel', 1)
            if not isinstance(min_level, int) or min_level < 1:
                raise AssertionError(f'{p}: invalid minimumCharacterLevel for {tid}')
            required_classes = gate.get('requiredClasses', [])
            required_class_set = string_list(p, required_classes, f'requiredClasses {tid}')
            migrated_as_class = required_class_set & MIGRATED_CLASS_IDS
            if migrated_as_class:
                raise AssertionError(f'{p}: {tid} uses migrated specialization as class: {sorted(migrated_as_class)}')
            if any(cid not in class_ids for cid in required_classes):
                raise AssertionError(f'{p}: unknown required class in {tid}')
            mastery = gate.get('requiredMastery', {})
            if not isinstance(mastery, dict) or any(
                not isinstance(k, str) or not k or not isinstance(v, int) or isinstance(v, bool) or v < 0
                for k, v in mastery.items()
            ):
                raise AssertionError(f'{p}: invalid requiredMastery for {tid}')
            required_specs = gate.get('requiredSpecializations', [])
            string_list(p, required_specs, f'requiredSpecializations {tid}')
            if any(sid not in spec_ids for sid in required_specs):
                raise AssertionError(f'{p}: unknown required specialization in {tid}')
            string_list(p, gate.get('requiredTags', []), f'requiredTags {tid}')
            string_list(p, tree.get('tags', []), f'tags {tid}')
            string_list(p, tree.get('bridges', []), f'bridges {tid}')
            architecture[tid] = (p, tree)

    if 'rpgskilltree:main' not in architecture:
        raise AssertionError('missing semantic main-tree architecture')
    if len(architecture) < 70:
        raise AssertionError(f'expected at least 70 semantic trees, got {len(architecture)}')
    for tid, (p, tree) in architecture.items():
        for bridge in tree.get('bridges', []):
            if bridge not in architecture:
                raise AssertionError(f'{p}: unknown architecture bridge {tid} -> {bridge}')
            if bridge == tid:
                raise AssertionError(f'{p}: architecture tree cannot bridge to itself: {tid}')

    morph_overrides, morph_entities, morph_relations = validate_morph_data()

    print(
        f'Data validation: PASS ({len(arch)} archetypes, {len(classes)} classes, {len(specs)} specializations, '
        f'{len(pacts)} pacts, {len(unlocks)} tree gateways, {total} main-tree nodes budgeted, '
        f'{len(architecture)} semantic trees, {morph_overrides} morph overrides, '
        f'{morph_entities} ecological entity mappings, {morph_relations} faction relations)'
    )


if __name__ == '__main__':
    main()
