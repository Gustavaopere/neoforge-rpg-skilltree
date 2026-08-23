#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
layout=json.loads((ROOT/'generated/main-tree-layout.json').read_text())
out=ROOT/'src/main/resources/assets/rpgskilltree/tree/main.json'
out.parent.mkdir(parents=True,exist_ok=True)

def namespaced(node_id: str) -> str:
    return node_id if ':' in node_id else f'rpgskilltree:{node_id}'

nodes=[]
for source in layout['nodes']:
    node=dict(source)
    node['id']=namespaced(node['id'])
    nodes.append(node)

client={
    'id': namespaced(layout['id']),
    'nodes': nodes,
    'edges': [[namespaced(a), namespaced(b)] for a,b in layout['edges']],
}
out.write_text(json.dumps(client,indent=2)+'\n')
print(f'Exported client tree asset: {len(client["nodes"])} nodes, {len(client["edges"])} edges -> {out}')
