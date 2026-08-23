#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
layout=json.loads((ROOT/'generated/main-tree-layout.json').read_text())
asset=ROOT/'src/main/resources/assets/rpgskilltree/tree/main.json'
if not asset.exists():
    raise SystemExit('Client tree validation: FAIL missing assets/rpgskilltree/tree/main.json')
client=json.loads(asset.read_text())

def namespaced(node_id: str) -> str:
    return node_id if ':' in node_id else f'rpgskilltree:{node_id}'

if client.get('id') != namespaced(layout.get('id')):
    raise SystemExit('Client tree validation: FAIL id mismatch')
if len(client.get('nodes',[])) != len(layout['nodes']):
    raise SystemExit('Client tree validation: FAIL node count mismatch')
if not all(n['id'].startswith('rpgskilltree:') for n in client.get('nodes', [])):
    raise SystemExit('Client tree validation: FAIL client node ids must be namespaced')
if not all(a.startswith('rpgskilltree:') and b.startswith('rpgskilltree:') for a,b in client.get('edges', [])):
    raise SystemExit('Client tree validation: FAIL client edge ids must be namespaced')
expected_ids={namespaced(n['id']) for n in layout['nodes']}
if {n['id'] for n in client['nodes']} != expected_ids:
    raise SystemExit('Client tree validation: FAIL node ids mismatch')
expected_edges={tuple(sorted((namespaced(a), namespaced(b)))) for a,b in layout['edges']}
if {tuple(sorted(e)) for e in client.get('edges',[])} != expected_edges:
    raise SystemExit('Client tree validation: FAIL edge mismatch')
final=[n for n in client['nodes'] if n.get('finalTriadSlot') is not None]
if len(final)!=33:
    raise SystemExit(f'Client tree validation: FAIL expected 33 final triad nodes, got {len(final)}')
print(f"Client tree validation: PASS ({len(client['nodes'])} nodes, {len(client['edges'])} edges, {len(final)} final triads)")

# Class-tree asset parity: first vertical slice is Technomancer.
tech_asset=ROOT/'src/main/resources/assets/rpgskilltree/tree/technomancer.json'
tech_rules_path=ROOT/'src/main/resources/data/rpgskilltree/node_rules/technomancer.json'
if not tech_asset.exists() or not tech_rules_path.exists():
    raise SystemExit('Client tree validation: FAIL missing technomancer asset/rules')
tech=json.loads(tech_asset.read_text())
tech_rules=json.loads(tech_rules_path.read_text())
rule_nodes={n['id']:n for n in tech_rules.get('nodes',[])}
client_nodes={n['id']:n for n in tech.get('nodes',[])}
if set(rule_nodes) != set(client_nodes):
    raise SystemExit('Client tree validation: FAIL technomancer node ids mismatch')
if len(client_nodes) != 17:
    raise SystemExit(f'Client tree validation: FAIL expected 17 technomancer nodes, got {len(client_nodes)}')
rule_edges=set()
for node in rule_nodes.values():
    for neighbor in node.get('neighbors',[]):
        rule_edges.add(tuple(sorted((node['id'],neighbor))))
client_edges={tuple(sorted(edge)) for edge in tech.get('edges',[])}
if rule_edges != client_edges:
    raise SystemExit('Client tree validation: FAIL technomancer edge mismatch')
for node_id,rule in rule_nodes.items():
    visual=client_nodes[node_id]
    for key,default in [('minCharacterLevel',1),('requiredClasses',[]),('requiredMastery',{}),('requiredSpecializations',[]),('requiredClassChoices',[])]:
        if visual.get(key,default) != rule.get(key,default):
            raise SystemExit(f'Client tree validation: FAIL requirement mismatch {node_id}.{key}')
    for key in ('maxRank','costPerRank','startingPoint'):
        if visual.get(key) != rule.get(key):
            raise SystemExit(f'Client tree validation: FAIL purchase metadata mismatch {node_id}.{key}')
if tech.get('displayKey') != 'tree.rpgskilltree.technomancer':
    raise SystemExit('Client tree validation: FAIL technomancer display key')
print(f"Client class-tree validation: PASS ({len(client_nodes)} technomancer nodes, {len(client_edges)} edges)")

# Paid-class client catalog must be derived from the same authoritative class definitions.
paid_asset=ROOT/'src/main/resources/assets/rpgskilltree/tree/paid_classes.json'
if not paid_asset.exists():
    raise SystemExit('Client tree validation: FAIL missing paid_classes.json')
client_paid=json.loads(paid_asset.read_text()).get('classes',[])
expected_paid=[]
for path in sorted((ROOT/'src/main/resources/data/rpgskilltree/classes').glob('*.json')):
    data=json.loads(path.read_text())
    cost=int(data.get('non_adjacent_bridge_cost',0))
    if cost <= 0:
        continue
    expected_paid.append({
        'id':data['class_id'],
        'requiredCompletedDomains':data['required_completed_domains'],
        'bridgeCost':cost,
        'displayKey':f"class.rpgskilltree.{data['class_id']}"
    })
if client_paid != expected_paid:
    raise SystemExit('Client tree validation: FAIL paid class catalog differs from authoritative class data')
print(f'Client paid-class validation: PASS ({len(client_paid)} abnormal confluences)')

# Warlock class-tree parity, including pact-gated branches.
warlock_asset=ROOT/'src/main/resources/assets/rpgskilltree/tree/warlock.json'
warlock_rules_path=ROOT/'src/main/resources/data/rpgskilltree/node_rules/warlock.json'
if not warlock_asset.exists() or not warlock_rules_path.exists():
    raise SystemExit('Client tree validation: FAIL missing warlock asset/rules')
warlock=json.loads(warlock_asset.read_text())
warlock_rules=json.loads(warlock_rules_path.read_text())
wrules={n['id']:n for n in warlock_rules.get('nodes',[])}
wnodes={n['id']:n for n in warlock.get('nodes',[])}
if set(wrules) != set(wnodes):
    raise SystemExit('Client tree validation: FAIL warlock node ids mismatch')
if len(wnodes) != 18:
    raise SystemExit(f'Client tree validation: FAIL expected 18 warlock nodes, got {len(wnodes)}')
w_rule_edges=set()
for node in wrules.values():
    for neighbor in node.get('neighbors',[]):
        w_rule_edges.add(tuple(sorted((node['id'],neighbor))))
w_client_edges={tuple(sorted(edge)) for edge in warlock.get('edges',[])}
if w_rule_edges != w_client_edges:
    raise SystemExit('Client tree validation: FAIL warlock edge mismatch')
for node_id,rule in wrules.items():
    visual=wnodes[node_id]
    for key,default in [('minCharacterLevel',1),('requiredClasses',[]),('requiredMastery',{}),('requiredSpecializations',[]),('requiredClassChoices',[])]:
        if visual.get(key,default) != rule.get(key,default):
            raise SystemExit(f'Client tree validation: FAIL warlock requirement mismatch {node_id}.{key}')
    for key in ('maxRank','costPerRank','startingPoint'):
        if visual.get(key) != rule.get(key):
            raise SystemExit(f'Client tree validation: FAIL warlock purchase metadata mismatch {node_id}.{key}')
choice_gated=[node_id for node_id,node in wrules.items() if node.get('requiredClassChoices')]
if len(choice_gated) != 15:
    raise SystemExit(f'Client tree validation: FAIL expected 15 pact-gated warlock nodes, got {len(choice_gated)}')
print(f'Client warlock-tree validation: PASS ({len(wnodes)} nodes, {len(w_client_edges)} edges, {len(choice_gated)} pact-gated)')

# Class-choice client catalog parity.
choice_asset=ROOT/'src/main/resources/assets/rpgskilltree/tree/class_choices.json'
if not choice_asset.exists():
    raise SystemExit('Client tree validation: FAIL missing class_choices.json')
client_choices=json.loads(choice_asset.read_text()).get('choices',[])
expected_choices=[]
for path in sorted((ROOT/'src/main/resources/data/rpgskilltree/class_choices').rglob('*.json')):
    data=json.loads(path.read_text())
    expected_choices.append({
        'id':data['choice_id'],
        'requiredClassId':data['required_class_id'],
        'groupId':data['group_id'],
        'capacity':int(data['default_group_capacity']),
        'displayKey':data.get('display_key',f"choice.rpgskilltree.{data['choice_id'].replace(':','.')}")
    })
if client_choices != expected_choices:
    raise SystemExit('Client tree validation: FAIL class choice catalog differs from authoritative data')
print(f'Client class-choice validation: PASS ({len(client_choices)} choices)')

# Morph class-tree parity.
for morph_tree, expected_count in [('druid', 11), ('metamorph', 10)]:
    asset_path=ROOT/f'src/main/resources/assets/rpgskilltree/tree/{morph_tree}.json'
    rules_path=ROOT/f'src/main/resources/data/rpgskilltree/node_rules/{morph_tree}.json'
    if not asset_path.exists() or not rules_path.exists():
        raise SystemExit(f'Client tree validation: FAIL missing {morph_tree} asset/rules')
    asset_data=json.loads(asset_path.read_text())
    rules_data=json.loads(rules_path.read_text())
    arules={n['id']:n for n in rules_data.get('nodes',[])}
    anodes={n['id']:n for n in asset_data.get('nodes',[])}
    if set(arules) != set(anodes) or len(anodes) != expected_count:
        raise SystemExit(f'Client tree validation: FAIL {morph_tree} node parity/count')
    redges=set()
    for node in arules.values():
        for neighbor in node.get('neighbors',[]): redges.add(tuple(sorted((node['id'],neighbor))))
    aedges={tuple(sorted(edge)) for edge in asset_data.get('edges',[])}
    if redges != aedges:
        raise SystemExit(f'Client tree validation: FAIL {morph_tree} edge mismatch')
print('Client morph-tree validation: PASS (Druid + Metamorph)')
