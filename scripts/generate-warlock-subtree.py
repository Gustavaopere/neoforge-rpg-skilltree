#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
RULE=ROOT/'src/main/resources/data/rpgskilltree/node_rules/warlock.json'
ASSET=ROOT/'src/main/resources/assets/rpgskilltree/tree/warlock.json'
EFFECT=ROOT/'src/main/resources/data/rpgskilltree/node_effects/warlock.json'

nodes=[]
def add(slug,x,y,kind='lesser',max_rank=1,level=25,choice=None,neighbors=(),mastery=None):
    node={
      'id':f'rpgskilltree:warlock/{slug}','kind':kind,'domain':'WARLOCK','x':x,'y':y,
      'maxRank':max_rank,'costPerRank':1,'startingPoint':slug=='core','minCharacterLevel':level,
      'requiredClasses':['warlock']
    }
    if choice: node['requiredClassChoices']=[f'warlock:{choice}']
    if mastery: node['requiredMastery']=mastery
    node['neighbors']=[f'rpgskilltree:warlock/{n}' for n in neighbors]
    nodes.append(node)

add('core',0,170,'class',1,25,neighbors=['forbidden_lore'])
add('forbidden_lore',0,105,'lesser',3,25,neighbors=['core','pact_confluence'])
add('pact_confluence',0,35,'notable',1,25,neighbors=['forbidden_lore','blade_bond','blood_sacrifice','familiar_bond','grimoire_pages','soul_harvest'])

branches={
'blade':('pact_blade',-260,[('blade_bond',-70,3),('hex_edge',-140,2),('soulsteel_keystone',-215,1)]),
'blood':('pact_blood',-130,[('blood_sacrifice',-70,3),('blood_hunger',-140,2),('crimson_covenant',-215,1)]),
'familiar':('pact_familiar',0,[('familiar_bond',-70,3),('servant_command',-140,2),('anchored_familiar',-215,1)]),
'grimoire':('pact_grimoire',130,[('grimoire_pages',-70,3),('ritual_memory',-140,2),('forbidden_formula',-215,1)]),
'souls':('pact_souls',260,[('soul_harvest',-70,3),('servant_link',-140,2),('soul_conduit',-215,1)]),
}
for key,(choice,x,parts) in branches.items():
    for i,(slug,y,rank) in enumerate(parts):
        neigh=[]
        if i==0: neigh=['pact_confluence']
        else: neigh=[parts[i-1][0]]
        if i+1<len(parts): neigh.append(parts[i+1][0])
        kind='keystone' if i==2 else ('notable' if i==1 else 'lesser')
        mastery=None
        if slug=='crimson_covenant': mastery={'irons:blood':80}
        elif slug=='anchored_familiar': mastery={'magic:casting':80}
        elif slug=='forbidden_formula': mastery={'magic:casting':120}
        elif slug=='soul_conduit': mastery={'magic:casting':80}
        elif slug=='soulsteel_keystone': mastery={'magic:casting':80}
        add(slug,x,y,kind,rank,35 if i==2 else 25,choice,neigh,mastery)

# ensure hub links are symmetric and all edges represented
by={n['id']:n for n in nodes}
for node in list(nodes):
    for target in list(node['neighbors']):
        if node['id'] not in by[target]['neighbors']:
            by[target]['neighbors'].append(node['id'])
for n in nodes: n['neighbors']=sorted(set(n['neighbors']))

rules={'treeId':'rpgskilltree:class/warlock','nodes':[]}
for n in nodes:
    r={k:n[k] for k in ['id','maxRank','costPerRank','startingPoint','neighbors','requiredClasses','minCharacterLevel']}
    if 'requiredClassChoices' in n:r['requiredClassChoices']=n['requiredClassChoices']
    if 'requiredMastery' in n:r['requiredMastery']=n['requiredMastery']
    rules['nodes'].append(r)
asset={'id':'rpgskilltree:class/warlock','displayKey':'tree.rpgskilltree.warlock','nodes':[{k:v for k,v in n.items() if k!='neighbors'} for n in nodes], 'edges':[]}
seen=set()
for n in nodes:
    for t in n['neighbors']:
        edge=tuple(sorted((n['id'],t)))
        if edge not in seen:
            seen.add(edge); asset['edges'].append(list(edge))

attrs=[]
def effect(slug,suffix,attribute,operation,amount):
    attrs.append({'effectId':f'rpgskilltree:warlock/{slug}/{suffix}','nodeId':f'rpgskilltree:warlock/{slug}','attributeId':attribute,'operation':operation,'amountPerRank':amount})
effect('forbidden_lore','mana','irons_spellbooks:max_mana','ADD_FLAT',8.0)
effect('pact_confluence','spell_power','irons_spellbooks:spell_power','ADD_PERCENT_BASE',0.04)
effect('blade_bond','attack','minecraft:generic.attack_damage','ADD_FLAT',0.5)
effect('hex_edge','speed','minecraft:generic.attack_speed','ADD_PERCENT_BASE',0.03)
effect('soulsteel_keystone','spell','irons_spellbooks:spell_power','MULTIPLY_TOTAL',0.10)
effect('blood_sacrifice','blood_power','irons_spellbooks:blood_spell_power','ADD_PERCENT_BASE',0.06)
effect('blood_hunger','life_steal','apothic_attributes:life_steal','ADD_FLAT',0.01)
effect('crimson_covenant','overheal','apothic_attributes:overheal','ADD_PERCENT_BASE',0.12)
effect('familiar_bond','summon','irons_spellbooks:summon_damage','ADD_PERCENT_BASE',0.07)
effect('servant_command','health','minecraft:generic.max_health','ADD_FLAT',1.0)
effect('anchored_familiar','summon_total','irons_spellbooks:summon_damage','MULTIPLY_TOTAL',0.12)
effect('grimoire_pages','mana','irons_spellbooks:max_mana','ADD_FLAT',12.0)
effect('ritual_memory','regen','irons_spellbooks:mana_regen','ADD_PERCENT_BASE',0.05)
effect('forbidden_formula','spell','irons_spellbooks:spell_power','MULTIPLY_TOTAL',0.10)
effect('soul_harvest','spell','irons_spellbooks:spell_power','ADD_PERCENT_BASE',0.04)
effect('servant_link','summon','irons_spellbooks:summon_damage','ADD_PERCENT_BASE',0.06)
effect('soul_conduit','health','minecraft:generic.max_health','ADD_FLAT',2.0)

RULE.write_text(json.dumps(rules,indent=2)+'\n')
ASSET.write_text(json.dumps(asset,indent=2)+'\n')
EFFECT.write_text(json.dumps({'attributes':attrs},indent=2)+'\n')
print(f'Generated Warlock subtree: {len(nodes)} nodes, {len(seen)} edges, {len(attrs)} effects')
