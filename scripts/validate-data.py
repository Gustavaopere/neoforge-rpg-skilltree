#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]/'src/main/resources/data/rpgskilltree'
DOMAINS={'MARTIAL','AGILITY','VITALITY','ARCANE','ENGINEERING','SURVIVAL','SUMMONING'}
def load_dir(name):
    out=[]
    for p in sorted((ROOT/name).glob('*.json')):
        data=json.loads(p.read_text())
        out.append((p,data))
    return out
def nonneg_map(p,d,key):
    m=d.get(key,{})
    if not isinstance(m,dict): raise AssertionError(f'{p}: {key} must be object')
    for k,v in m.items():
        if key=='minimum_domain_scores' and k not in DOMAINS: raise AssertionError(f'{p}: unknown domain {k}')
        if not isinstance(v,int) or v<0: raise AssertionError(f'{p}: invalid {key}.{k}')
def main():
    arch=load_dir('archetypes'); unlocks=load_dir('tree_unlocks')
    ids=set()
    for p,d in arch:
        id_=d['id'];
        if id_ in ids: raise AssertionError(f'duplicate id {id_}')
        ids.add(id_); nonneg_map(p,d,'minimum_domain_scores')
        if not isinstance(d.get('priority'),int): raise AssertionError(f'{p}: priority')
        for key in ('required_tags','forbidden_tags'):
            if not isinstance(d.get(key,[]),list): raise AssertionError(f'{p}: {key}')
    tree_ids=set()
    for p,d in unlocks:
        id_=d['tree_id']
        if id_ in tree_ids: raise AssertionError(f'duplicate tree {id_}')
        tree_ids.add(id_); nonneg_map(p,d,'minimum_domain_scores'); nonneg_map(p,d,'minimum_mastery_experience')
        if not isinstance(d.get('required_tags',[]),list): raise AssertionError(f'{p}: required_tags')
    if len(arch)<10: raise AssertionError('expected at least 10 archetypes')
    if len(unlocks)<15: raise AssertionError('expected at least 15 specialized tree gateways')
    
    blue=json.loads((ROOT/'tree_blueprints/main.json').read_text())
    total=sum(r['node_budget'] for r in blue['regions'])+blue['shared_core_nodes']+blue['hybrid_bridge_nodes']+blue['outer_keystone_nodes']
    if total != blue['target_node_count']: raise AssertionError(f'blueprint node budget {total} != target {blue["target_node_count"]}')
    if blue['target_node_count'] < 350: raise AssertionError('main tree is not large enough for the design target')
    print(f'Data validation: PASS ({len(arch)} archetypes, {len(unlocks)} tree gateways, {total} main-tree nodes budgeted)')
if __name__=='__main__': main()
