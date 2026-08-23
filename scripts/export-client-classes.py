#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
classes_dir=ROOT/'src/main/resources/data/rpgskilltree/classes'
out=ROOT/'src/main/resources/assets/rpgskilltree/tree/paid_classes.json'
out.parent.mkdir(parents=True, exist_ok=True)
entries=[]
for path in sorted(classes_dir.glob('*.json')):
    data=json.loads(path.read_text())
    cost=int(data.get('non_adjacent_bridge_cost',0))
    if cost <= 0:
        continue
    class_id=data['class_id']
    entries.append({
        'id': class_id,
        'requiredCompletedDomains': data['required_completed_domains'],
        'bridgeCost': cost,
        'displayKey': f'class.rpgskilltree.{class_id}'
    })
out.write_text(json.dumps({'classes':entries}, indent=2)+'\n')
print(f'Exported paid class catalog: {len(entries)} classes -> {out}')
