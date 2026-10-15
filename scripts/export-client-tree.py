#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
layout=json.loads((ROOT/'generated/main-tree-layout.json').read_text())
out=ROOT/'src/main/resources/assets/rpgskilltree/tree/main.json'
out.parent.mkdir(parents=True,exist_ok=True)

def namespaced(node_id: str) -> str:
    return node_id if ':' in node_id else f'rpgskilltree:{node_id}'

# Copy only access metadata declared by authoritative server rules.
# Do not infer requirements from geometry or player-facing names.
server_rules=json.loads((ROOT/'src/main/resources/data/rpgskilltree/node_rules/main.json').read_text())
rule_nodes={node['id']:node for node in server_rules.get('nodes',[])}
nodes=[]
for source in layout['nodes']:
    node=dict(source)
    node['id']=namespaced(node['id'])
    rule=rule_nodes.get(node['id'],{})
    if 'requiredAttributes' in rule:
        node['requiredAttributes']=rule['requiredAttributes']
    nodes.append(node)

client={
    'id': namespaced(layout['id']),
    'nodes': nodes,
    'edges': [[namespaced(a), namespaced(b)] for a,b in layout['edges']],
}
out.write_text(json.dumps(client,indent=2)+'\n')
print(f'Exported client tree asset: {len(client["nodes"])} nodes, {len(client["edges"])} edges -> {out}')
