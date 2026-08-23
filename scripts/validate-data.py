#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]/'src/main/resources/data/rpgskilltree'
DOMAINS={'MARTIAL','AGILITY','VITALITY','ARCANE','ENGINEERING','SURVIVAL','SUMMONING','HEALING','MINING','OCCULT','LOGISTICS'}
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
    arch=load_dir('archetypes'); unlocks=load_dir('tree_unlocks'); classes=load_dir('classes'); specs=load_dir('specializations')
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
    class_ids=set()
    for p,d in classes:
        id_=d['class_id']
        if id_ in class_ids: raise AssertionError(f'duplicate class {id_}')
        class_ids.add(id_)
        domains=d.get('required_completed_domains',[])
        if not isinstance(domains,list): raise AssertionError(f'{p}: required_completed_domains')
        if any(domain not in DOMAINS for domain in domains): raise AssertionError(f'{p}: unknown class domain')
        mastery=d.get('minimum_mastery_experience',{})
        if not isinstance(mastery,dict) or any(
            not isinstance(k,str) or not k or not isinstance(v,int) or v<=0
            for k,v in mastery.items()
        ): raise AssertionError(f'{p}: invalid class mastery requirements')
        required_nodes=d.get('required_nodes',[])
        if not isinstance(required_nodes,list) or any(not isinstance(node,str) or not node for node in required_nodes):
            raise AssertionError(f'{p}: invalid required_nodes')
        if not domains and not mastery and not required_nodes:
            raise AssertionError(f'{p}: class needs domain, mastery, or node requirements')
        if d.get('final_triad_rank_per_capstone') != 3: raise AssertionError(f'{p}: final triad rank must be 3')
        if d.get('final_triad_capstones_per_domain') != 3: raise AssertionError(f'{p}: final triad must have three capstones')
        cost=d.get('non_adjacent_bridge_cost',0)
        if not isinstance(cost,int) or cost<0: raise AssertionError(f'{p}: non_adjacent_bridge_cost')
        if d.get('adjacent_confluence',False) and cost != 0: raise AssertionError(f'{p}: adjacent class cannot have bridge surcharge')
        if len(domains)<=1 and cost != 0: raise AssertionError(f'{p}: pure/provider class cannot have bridge surcharge')

    rewards=json.loads((ROOT/'boss_rewards/defaults.json').read_text())
    expected_rewards={'minecraft':3,'cataclysm':5,'apotheosis':2}
    for namespace,points in expected_rewards.items():
        if rewards.get('namespace_defaults',{}).get(namespace) != points: raise AssertionError(f'boss reward {namespace} must be {points}')
    if rewards.get('first_kill_only') is not True: raise AssertionError('boss rewards must be first-kill-only')

    spec_ids=set()
    for p,d in specs:
        sid=d['specialization_id']
        if sid in spec_ids: raise AssertionError(f'duplicate specialization {sid}')
        spec_ids.add(sid)
        eligible=d.get('eligible_class_ids',[])
        if not isinstance(eligible,list) or not eligible: raise AssertionError(f'{p}: eligible_class_ids')
        if any(cid not in class_ids for cid in eligible): raise AssertionError(f'{p}: unknown eligible class')
        mastery=d.get('minimum_mastery_experience',{})
        if not isinstance(mastery,dict) or any(not isinstance(v,int) or v<0 for v in mastery.values()): raise AssertionError(f'{p}: mastery requirements')
        if not isinstance(d.get('required_tags',[]),list): raise AssertionError(f'{p}: required_tags')

    pact_dir=ROOT/'class_choices/warlock_pacts'
    pacts=[]
    for p in sorted(pact_dir.glob('*.json')):
        d=json.loads(p.read_text()); pacts.append((p,d))
        if d.get('required_class_id') != 'warlock': raise AssertionError(f'{p}: pact must require warlock')
        if d.get('group_id') != 'warlock:pact': raise AssertionError(f'{p}: pact group')
        if not isinstance(d.get('default_group_capacity'),int) or d['default_group_capacity']<1: raise AssertionError(f'{p}: pact capacity')
    if len(pacts)<5: raise AssertionError('expected at least five warlock pacts')

    techno_rules_path=ROOT/'node_rules/technomancer.json'
    if not techno_rules_path.exists(): raise AssertionError('missing technomancer node rules')
    techno=json.loads(techno_rules_path.read_text())
    techno_nodes={node['id']:node for node in techno.get('nodes',[])}
    if len(techno_nodes) != 17: raise AssertionError(f'technomancer subtree must contain 17 nodes, got {len(techno_nodes)}')
    root=techno_nodes.get('rpgskilltree:technomancer/core')
    if root is None or root.get('startingPoint') is not True: raise AssertionError('technomancer root must be a starting point')
    if root.get('requiredClasses') != ['technomancer']: raise AssertionError('technomancer root must require technomancer class')
    expected_gateways={
        'rpgskilltree:technomancer/create_gateway':'create_kinetics',
        'rpgskilltree:technomancer/ae2_gateway':'ae2_networks',
        'rpgskilltree:technomancer/oritech_gateway':'oritech_power',
    }
    for node_id,spec_id in expected_gateways.items():
        node=techno_nodes.get(node_id)
        if node is None: raise AssertionError(f'missing technomancer gateway {node_id}')
        grant=node.get('grantsSpecialization',{})
        if grant.get('id') != spec_id: raise AssertionError(f'{node_id} must grant {spec_id}')
        if not node.get('requiredMastery'): raise AssertionError(f'{node_id} must require mastery')
    triune=techno_nodes.get('rpgskilltree:technomancer/triune_core')
    if triune is None: raise AssertionError('missing technomancer triune core')
    if set(triune.get('requiredSpecializations',[])) != set(expected_gateways.values()):
        raise AssertionError('technomancer triune core must require all three gateway specializations')

    progression=json.loads((ROOT/'progression/defaults.json').read_text())
    if progression.get('max_character_level') != 100: raise AssertionError('default character level cap must be 100')
    if progression.get('passive_points_per_level') != 1: raise AssertionError('default points per level must be 1')
    if progression.get('default_non_adjacent_class_bridge_cost') != 10: raise AssertionError('default abnormal bridge cost must be 10')

    if len(arch)<10: raise AssertionError('expected at least 10 archetypes')
    if len(unlocks)<15: raise AssertionError('expected at least 15 specialized tree gateways')
    if len(classes)<20: raise AssertionError('expected at least 20 alpha 2 class definitions')
    if len(specs)<20: raise AssertionError('expected at least 20 post-class specializations')
    
    blue=json.loads((ROOT/'tree_blueprints/main.json').read_text())
    total=sum(r['node_budget'] for r in blue['regions'])+blue['shared_core_nodes']+blue['hybrid_bridge_nodes']+blue['outer_keystone_nodes']
    if total != blue['target_node_count']: raise AssertionError(f'blueprint node budget {total} != target {blue["target_node_count"]}')
    if set(r['id'] for r in blue['regions']) != DOMAINS: raise AssertionError('blueprint must cover all alpha 2 progression domains')
    if blue['target_node_count'] < 500: raise AssertionError('alpha 2 main tree must budget at least 500 base nodes')
    for cid in blue.get('dynamic_non_adjacent_classes',[]):
        if cid not in class_ids: raise AssertionError(f'unknown dynamic class {cid}')
    print(f'Data validation: PASS ({len(arch)} archetypes, {len(classes)} classes, {len(specs)} specializations, {len(pacts)} pacts, {len(unlocks)} tree gateways, {total} main-tree nodes budgeted)')
if __name__=='__main__': main()
