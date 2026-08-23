#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
source=ROOT/'src/main/resources/data/rpgskilltree/class_choices'
out=ROOT/'src/main/resources/assets/rpgskilltree/tree/class_choices.json'
out.parent.mkdir(parents=True, exist_ok=True)
choices=[]
for path in sorted(source.rglob('*.json')):
    data=json.loads(path.read_text())
    choices.append({
        'id':data['choice_id'],
        'requiredClassId':data['required_class_id'],
        'groupId':data['group_id'],
        'capacity':int(data['default_group_capacity']),
        'displayKey':data.get('display_key',f"choice.rpgskilltree.{data['choice_id'].replace(':','.')}")
    })
out.write_text(json.dumps({'choices':choices},indent=2)+'\n')
print(f'Exported client class choices: {len(choices)} -> {out}')
