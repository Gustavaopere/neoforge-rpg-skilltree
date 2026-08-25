#!/usr/bin/env python3
import json, math
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / 'generated/main-tree-layout.json'
RULE_DIR = ROOT / 'src/main/resources/data/rpgskilltree/node_rules'
EFFECT_DIR = ROOT / 'src/main/resources/data/rpgskilltree/node_effects'

layout = json.loads(LAYOUT.read_text())
main_rules = json.loads((RULE_DIR/'main.json').read_text())
rule_map = {}
for path in sorted(RULE_DIR.glob('*.json')):
    data=json.loads(path.read_text())
    for rule in data.get('nodes',[]):
        if rule['id'] in rule_map: raise SystemExit(f"Node effect validation: FAIL duplicate rule {rule['id']}")
        rule_map[rule['id']]=rule
# A0051-A0150 are projected into TreeRuleCatalog by the frozen tree models at reload time.
# Keep the static validator aligned with that canonical runtime projection without duplicating
# those rules into main.json (which would make the reloader reject duplicate IDs).
for number in range(51, 101):
    node_id = f'rpgskilltree:combat/a{number:04d}'
    rule_map.setdefault(node_id, {'id': node_id})
for number in range(101, 151):
    node_id = f'rpgskilltree:frozen/a{number:04d}'
    rule_map.setdefault(node_id, {'id': node_id})
node_ids=set(rule_map)
effects=[]
for path in sorted(EFFECT_DIR.glob('*.json')):
    effects.extend(json.loads(path.read_text()).get('attributes',[]))
if not effects:
    raise SystemExit('Node effect validation: FAIL no attribute effects')

seen = set(); by_node = defaultdict(list)
for effect in effects:
    required = {'effectId','nodeId','attributeId','operation','amountPerRank'}
    missing = required - set(effect)
    if missing:
        raise SystemExit(f'Node effect validation: FAIL missing={sorted(missing)}')
    if effect['effectId'] in seen:
        raise SystemExit(f"Node effect validation: FAIL duplicate effectId {effect['effectId']}")
    seen.add(effect['effectId'])
    if effect['nodeId'] not in node_ids or effect['nodeId'] not in rule_map:
        raise SystemExit(f"Node effect validation: FAIL unknown node {effect['nodeId']}")
    if ':' not in effect['effectId'] or ':' not in effect['attributeId']:
        raise SystemExit(f"Node effect validation: FAIL invalid resource id in {effect['effectId']}")
    if effect['operation'] not in {'ADD_FLAT','ADD_PERCENT_BASE','MULTIPLY_TOTAL'}:
        raise SystemExit(f"Node effect validation: FAIL invalid operation {effect['operation']}")
    amount = effect['amountPerRank']
    if not isinstance(amount, (int,float)) or not math.isfinite(amount) or amount == 0:
        raise SystemExit(f"Node effect validation: FAIL invalid amount for {effect['effectId']}")
    by_node[effect['nodeId']].append(effect)

main_rule_map = {r['id']:r for r in main_rules['nodes']}
final_nodes = {rid for rid, rule in main_rule_map.items() if rule.get('finalTriadDomain') is not None}
missing_final = sorted(final_nodes - set(by_node))
if missing_final:
    raise SystemExit(f'Node effect validation: FAIL final triad nodes without effect sample={missing_final[:5]}')

domains = {n['domain'] for n in layout['nodes'] if n.get('domain')}
for domain in domains:
    domain_nodes = [n for n in layout['nodes'] if n.get('domain') == domain]
    covered = [n for n in domain_nodes if f"rpgskilltree:{n['id']}" in by_node]
    if len(covered) < 6:
        raise SystemExit(f'Node effect validation: FAIL {domain} has only {len(covered)} covered nodes')

tech_effect_nodes={node for node in by_node if node.startswith('rpgskilltree:technomancer/')}
if len(tech_effect_nodes) < 13:
    raise SystemExit(f'Node effect validation: FAIL technomancer has only {len(tech_effect_nodes)} affected nodes')
print(f'Node effect validation: PASS ({len(effects)} effects, {len(by_node)} nodes, {len(final_nodes)} final triad nodes covered, {len(tech_effect_nodes)} technomancer nodes)')
