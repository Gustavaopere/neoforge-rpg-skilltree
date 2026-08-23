#!/usr/bin/env python3
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / 'generated/main-tree-layout.json'
SKILLS_DIR = ROOT / 'src/main/resources/data/rpgskilltree/skills/main'
TREE_FILE = ROOT / 'src/main/resources/data/rpgskilltree/skill_trees/main.json'
RULES_FILE = ROOT / 'src/main/resources/data/rpgskilltree/node_rules/main.json'

layout = json.loads(LAYOUT.read_text())
expected_nodes = {n['id'] for n in layout['nodes']}
expected_ids = {f'rpgskilltree:{node_id}' for node_id in expected_nodes}
expected_edges = {tuple(sorted(edge)) for edge in layout['edges']}

if not TREE_FILE.exists():
    raise SystemExit(f'Passive export validation: FAIL missing {TREE_FILE.relative_to(ROOT)}')
if not RULES_FILE.exists():
    raise SystemExit(f'Passive export validation: FAIL missing {RULES_FILE.relative_to(ROOT)}')
if not SKILLS_DIR.exists():
    raise SystemExit(f'Passive export validation: FAIL missing {SKILLS_DIR.relative_to(ROOT)}')

skill_files = sorted(SKILLS_DIR.glob('*.json'))
if len(skill_files) != len(expected_nodes):
    raise SystemExit(f'Passive export validation: FAIL expected {len(expected_nodes)} skill files, found {len(skill_files)}')

skills = {}
for path in skill_files:
    data = json.loads(path.read_text())
    required = {
        'id','bonuses','requirements','directConnections','longConnections','oneWayConnections','tags',
        'backgroundTexture','iconTexture','borderTexture','positionX','positionY','buttonSize','isStartingPoint'
    }
    missing = required - set(data)
    if missing:
        raise SystemExit(f'Passive export validation: FAIL {path.name} missing {sorted(missing)}')
    sid = data['id']
    if sid in skills:
        raise SystemExit(f'Passive export validation: FAIL duplicate skill id {sid}')
    skills[sid] = data

if set(skills) != expected_ids:
    missing = sorted(expected_ids - set(skills))[:5]
    extra = sorted(set(skills) - expected_ids)[:5]
    raise SystemExit(f'Passive export validation: FAIL skill ID mismatch missing={missing} extra={extra}')

exported_edges = set()
for sid, data in skills.items():
    source = sid.split(':', 1)[1]
    for target_id in data['directConnections']:
        if target_id not in skills:
            raise SystemExit(f'Passive export validation: FAIL unknown connection {sid} -> {target_id}')
        target = target_id.split(':', 1)[1]
        exported_edges.add(tuple(sorted((source, target))))

if exported_edges != expected_edges:
    raise SystemExit(
        f'Passive export validation: FAIL edge mismatch expected={len(expected_edges)} actual={len(exported_edges)}'
    )

starting = [sid for sid, data in skills.items() if data['isStartingPoint']]
if starting != ['rpgskilltree:core_00']:
    raise SystemExit(f'Passive export validation: FAIL expected only core_00 starting point, found={starting}')

tree = json.loads(TREE_FILE.read_text())
if tree.get('id') != 'rpgskilltree:main':
    raise SystemExit('Passive export validation: FAIL main tree id mismatch')
if set(tree.get('skillIds', [])) != expected_ids:
    raise SystemExit('Passive export validation: FAIL main tree skillIds mismatch')

rules = json.loads(RULES_FILE.read_text())
rule_map = {r['id']: r for r in rules.get('nodes', [])}
if set(rule_map) != expected_ids:
    raise SystemExit('Passive export validation: FAIL node rules IDs mismatch')
rule_edges = set()
final_triads = {}
for sid, rule in rule_map.items():
    if rule['maxRank'] < 1 or rule['costPerRank'] < 1:
        raise SystemExit(f'Passive export validation: FAIL invalid purchase rule for {sid}')
    domain = rule.get('finalTriadDomain')
    slot = rule.get('finalTriadSlot')
    if domain is not None:
        if rule['maxRank'] != 3 or slot not in (0, 1, 2):
            raise SystemExit(f'Passive export validation: FAIL invalid final triad rule for {sid}')
        final_triads.setdefault(domain, []).append(slot)
    elif slot is not None:
        raise SystemExit(f'Passive export validation: FAIL finalTriadSlot without domain for {sid}')
    if not isinstance(rule.get('neighbors'), list):
        raise SystemExit(f'Passive export validation: FAIL missing neighbors for {sid}')
    source = sid.split(':', 1)[1]
    for neighbor_id in rule['neighbors']:
        if neighbor_id not in rule_map:
            raise SystemExit(f'Passive export validation: FAIL unknown authoritative neighbor {sid} -> {neighbor_id}')
        target = neighbor_id.split(':', 1)[1]
        rule_edges.add(tuple(sorted((source, target))))
expected_domains = {node['domain'] for node in layout['nodes'] if node.get('domain')}
if set(final_triads) != expected_domains:
    raise SystemExit(
        f'Passive export validation: FAIL final triad domains mismatch expected={sorted(expected_domains)} actual={sorted(final_triads)}'
    )
for domain, slots in final_triads.items():
    if sorted(slots) != [0, 1, 2]:
        raise SystemExit(f'Passive export validation: FAIL {domain} final triad slots={sorted(slots)}')

if rule_edges != expected_edges:
    raise SystemExit(
        f'Passive export validation: FAIL authoritative edge mismatch expected={len(expected_edges)} actual={len(rule_edges)}'
    )

adjacency = {sid: set() for sid in rule_map}
for sid, rule in rule_map.items():
    for neighbor_id in rule['neighbors']:
        adjacency[sid].add(neighbor_id)
        adjacency[neighbor_id].add(sid)
visited = set()
frontier = ['rpgskilltree:core_00']
while frontier:
    current = frontier.pop()
    if current in visited:
        continue
    visited.add(current)
    frontier.extend(adjacency[current] - visited)
if visited != set(rule_map):
    unreachable = sorted(set(rule_map) - visited)
    raise SystemExit(
        f'Passive export validation: FAIL {len(unreachable)} unreachable nodes from core_00, sample={unreachable[:8]}'
    )

print(
    f'Passive export validation: PASS ({len(skills)} skills, {len(exported_edges)} edges, '
    f'{len(starting)} starting point, {len(rule_map)} purchase rules)'
)
