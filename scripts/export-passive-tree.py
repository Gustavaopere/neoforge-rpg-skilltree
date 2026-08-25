#!/usr/bin/env python3
import json
import shutil
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / 'generated/main-tree-layout.json'
SKILLS_DIR = ROOT / 'src/main/resources/data/rpgskilltree/skills/main'
TREE_FILE = ROOT / 'src/main/resources/data/rpgskilltree/skill_trees/main.json'
RULES_FILE = ROOT / 'src/main/resources/data/rpgskilltree/node_rules/main.json'

layout = json.loads(LAYOUT.read_text())
nodes = layout['nodes']
edges = layout['edges']
node_by_id = {node['id']: node for node in nodes}
if len(node_by_id) != len(nodes):
    raise SystemExit('duplicate layout node ids')

connections = defaultdict(list)
for a, b in edges:
    if a not in node_by_id or b not in node_by_id:
        raise SystemExit(f'unknown edge endpoint: {a} -> {b}')
    # Store each undirected visual connection exactly once. Passive Skill Tree renders
    # a DIRECT connection bidirectionally even if only one endpoint declares it.
    owner, target = sorted((a, b))
    connections[owner].append(target)

if SKILLS_DIR.exists():
    shutil.rmtree(SKILLS_DIR)
SKILLS_DIR.mkdir(parents=True, exist_ok=True)
TREE_FILE.parent.mkdir(parents=True, exist_ok=True)
RULES_FILE.parent.mkdir(parents=True, exist_ok=True)

skill_ids = []
rules = []
all_neighbors = defaultdict(set)
for a, b in edges:
    all_neighbors[a].add(b)
    all_neighbors[b].add(a)
for node in nodes:
    node_id = node['id']
    sid = f'rpgskilltree:{node_id}'
    skill_ids.append(sid)
    kind = node.get('kind', 'domain')
    tags = [f'rpgskilltree:kind/{kind}']
    if node.get('domain'):
        tags.append(f'rpgskilltree:domain/{node["domain"].lower()}')
    for domain in node.get('domains', []):
        tags.append(f'rpgskilltree:domain/{domain.lower()}')
    if node.get('archetype'):
        tags.append(f'rpgskilltree:bridge/{node["archetype"]}')
    tags.extend(node.get('tags', []))

    if kind == 'keystone':
        background = 'skilltree:textures/icons/background/keystone.png'
        border = 'skilltree:textures/tooltip/keystone.png'
        button_size = 32
    elif kind in ('hybrid', 'final_triad'):
        background = 'skilltree:textures/icons/background/notable.png'
        border = 'skilltree:textures/tooltip/notable.png'
        button_size = 24
    elif kind == 'core':
        background = 'skilltree:textures/icons/background/class.png'
        border = 'skilltree:textures/tooltip/lesser.png'
        button_size = 20
    else:
        background = 'skilltree:textures/icons/background/lesser.png'
        border = 'skilltree:textures/tooltip/lesser.png'
        button_size = 16

    data = {
        'id': sid,
        'bonuses': [],
        'requirements': [],
        'directConnections': [f'rpgskilltree:{target}' for target in sorted(connections[node_id])],
        'longConnections': [],
        'oneWayConnections': [],
        'tags': sorted(set(tags)),
        'backgroundTexture': background,
        # Placeholder icon intentionally uses a known upstream asset until authored icons land.
        'iconTexture': 'skilltree:textures/icons/potion_yellow_big.png',
        'borderTexture': border,
        'positionX': node['x'],
        'positionY': node['y'],
        'buttonSize': button_size,
        'isStartingPoint': node_id == 'core_00',
    }
    (SKILLS_DIR / f'{node_id}.json').write_text(json.dumps(data, indent=2) + '\n')

    # Purchase behavior lives in our authoritative server model instead of being
    # inferred from the client-visible Passive Skill Tree JSON.
    rule = {
        'id': sid,
        'maxRank': 3 if 'finalTriadSlot' in node else 1,
        'costPerRank': 1,
        'startingPoint': node_id == 'core_00',
        'neighbors': [f'rpgskilltree:{neighbor}' for neighbor in sorted(all_neighbors[node_id])],
    }
    if 'finalTriadSlot' in node:
        rule['finalTriadDomain'] = node['domain']
        rule['finalTriadSlot'] = node['finalTriadSlot']
    if node.get('tags'):
        rule['tags'] = sorted(set(node['tags']))
    rules.append(rule)

TREE_FILE.write_text(json.dumps({'skillIds': skill_ids, 'id': 'rpgskilltree:main'}, indent=2) + '\n')
RULES_FILE.write_text(json.dumps({'treeId': 'rpgskilltree:main', 'nodes': rules}, indent=2) + '\n')
print(f'Exported Passive Skill Tree data: {len(skill_ids)} skills, {len(edges)} edges, {len(rules)} purchase rules')
