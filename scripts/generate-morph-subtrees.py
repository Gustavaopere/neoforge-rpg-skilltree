#!/usr/bin/env python3
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]

def write_tree(name, display_key, required_class, nodes, edges, effects):
    rules=[]; visuals=[]
    neigh={n['id']:[] for n in nodes}
    for a,b in edges:
        neigh[a].append(b); neigh[b].append(a)
    for n in nodes:
        base={
            'id':n['id'],'maxRank':n.get('maxRank',1),'costPerRank':1,
            'startingPoint':n.get('startingPoint',False),'neighbors':neigh[n['id']],
            'requiredClasses':[required_class],'minCharacterLevel':n.get('level',20)
        }
        rules.append(base)
        vis={
            'id':n['id'],'kind':n.get('kind','lesser'),'domain':required_class.upper(),
            'x':n['x'],'y':n['y'],'maxRank':base['maxRank'],'costPerRank':1,
            'startingPoint':base['startingPoint'],'minCharacterLevel':base['minCharacterLevel'],
            'requiredClasses':[required_class]
        }
        visuals.append(vis)
    out_rules={'treeId':f'rpgskilltree:class/{name}','nodes':rules}
    out_asset={'id':f'rpgskilltree:class/{name}','displayKey':display_key,'nodes':visuals,'edges':edges}
    (ROOT/f'src/main/resources/data/rpgskilltree/node_rules/{name}.json').write_text(json.dumps(out_rules,indent=2)+"\n")
    (ROOT/f'src/main/resources/assets/rpgskilltree/tree/{name}.json').write_text(json.dumps(out_asset,indent=2)+"\n")
    (ROOT/f'src/main/resources/data/rpgskilltree/node_effects/{name}.json').write_text(json.dumps({'attributes':effects},indent=2)+"\n")

D='rpgskilltree:druid/'
druid_nodes=[
 {'id':D+'core','kind':'class','x':0,'y':150,'startingPoint':True},
 {'id':D+'wild_shape','x':0,'y':85,'maxRank':3},
 {'id':D+'natural_resilience','x':0,'y':20,'maxRank':2},
 {'id':D+'aquatic_shape','kind':'notable','x':-155,'y':-50},
 {'id':D+'deep_lungs','x':-215,'y':-120,'maxRank':2},
 {'id':D+'winged_shape','kind':'notable','x':0,'y':-70},
 {'id':D+'sky_instinct','x':0,'y':-145,'maxRank':2},
 {'id':D+'predator_form','kind':'notable','x':155,'y':-50,'maxRank':2},
 {'id':D+'pack_guardian','x':215,'y':-120,'maxRank':2},
 {'id':D+'primal_spirit','kind':'keystone','x':0,'y':-225,'level':30},
 {'id':D+'archdruid','kind':'keystone','x':0,'y':-305,'level':35},
]
druid_edges=[
 [D+'core',D+'wild_shape'],[D+'wild_shape',D+'natural_resilience'],
 [D+'natural_resilience',D+'aquatic_shape'],[D+'aquatic_shape',D+'deep_lungs'],
 [D+'natural_resilience',D+'winged_shape'],[D+'winged_shape',D+'sky_instinct'],
 [D+'natural_resilience',D+'predator_form'],[D+'predator_form',D+'pack_guardian'],
 [D+'sky_instinct',D+'primal_spirit'],[D+'deep_lungs',D+'primal_spirit'],
 [D+'pack_guardian',D+'primal_spirit'],[D+'primal_spirit',D+'archdruid']]
druid_effects=[
 {'effectId':D+'wild_shape/health','nodeId':D+'wild_shape','attributeId':'minecraft:generic.max_health','operation':'ADD_FLAT','amountPerRank':1.0},
 {'effectId':D+'natural_resilience/armor','nodeId':D+'natural_resilience','attributeId':'minecraft:generic.armor','operation':'ADD_FLAT','amountPerRank':0.5},
 {'effectId':D+'deep_lungs/speed','nodeId':D+'deep_lungs','attributeId':'minecraft:generic.movement_speed','operation':'ADD_PERCENT_BASE','amountPerRank':0.02},
 {'effectId':D+'sky_instinct/speed','nodeId':D+'sky_instinct','attributeId':'minecraft:generic.movement_speed','operation':'ADD_PERCENT_BASE','amountPerRank':0.02},
 {'effectId':D+'predator_form/damage','nodeId':D+'predator_form','attributeId':'minecraft:generic.attack_damage','operation':'ADD_PERCENT_BASE','amountPerRank':0.04},
 {'effectId':D+'pack_guardian/health','nodeId':D+'pack_guardian','attributeId':'minecraft:generic.max_health','operation':'ADD_FLAT','amountPerRank':1.0},
 {'effectId':D+'primal_spirit/nature','nodeId':D+'primal_spirit','attributeId':'irons_spellbooks:nature_spell_power','operation':'ADD_PERCENT_BASE','amountPerRank':0.08},
]
write_tree('druid','tree.rpgskilltree.druid','druid',druid_nodes,druid_edges,druid_effects)

M='rpgskilltree:metamorph/'
meta_nodes=[
 {'id':M+'core','kind':'class','x':0,'y':150,'startingPoint':True,'level':25},
 {'id':M+'borrowed_face','x':0,'y':80,'maxRank':2,'level':25},
 {'id':M+'mutable_bones','x':0,'y':10,'maxRank':3,'level':25},
 {'id':M+'monstrous_flesh','kind':'notable','x':-145,'y':-70,'level':28},
 {'id':M+'predatory_mimicry','x':-210,'y':-140,'maxRank':2,'level':28},
 {'id':M+'aberrant_form','kind':'notable','x':145,'y':-70,'level':30},
 {'id':M+'unstable_anatomy','x':210,'y':-140,'maxRank':2,'level':30},
 {'id':M+'chimeric_memory','kind':'notable','x':0,'y':-90,'maxRank':2,'level':30},
 {'id':M+'perfect_mimicry','kind':'keystone','x':0,'y':-180,'level':35},
 {'id':M+'apex_metamorph','kind':'keystone','x':0,'y':-265,'level':40},
]
meta_edges=[
 [M+'core',M+'borrowed_face'],[M+'borrowed_face',M+'mutable_bones'],
 [M+'mutable_bones',M+'monstrous_flesh'],[M+'monstrous_flesh',M+'predatory_mimicry'],
 [M+'mutable_bones',M+'aberrant_form'],[M+'aberrant_form',M+'unstable_anatomy'],
 [M+'mutable_bones',M+'chimeric_memory'],[M+'predatory_mimicry',M+'perfect_mimicry'],
 [M+'unstable_anatomy',M+'perfect_mimicry'],[M+'chimeric_memory',M+'perfect_mimicry'],
 [M+'perfect_mimicry',M+'apex_metamorph']]
meta_effects=[
 {'effectId':M+'borrowed_face/speed','nodeId':M+'borrowed_face','attributeId':'minecraft:generic.movement_speed','operation':'ADD_PERCENT_BASE','amountPerRank':0.02},
 {'effectId':M+'mutable_bones/armor','nodeId':M+'mutable_bones','attributeId':'minecraft:generic.armor','operation':'ADD_FLAT','amountPerRank':0.5},
 {'effectId':M+'predatory_mimicry/damage','nodeId':M+'predatory_mimicry','attributeId':'minecraft:generic.attack_damage','operation':'ADD_PERCENT_BASE','amountPerRank':0.04},
 {'effectId':M+'unstable_anatomy/health','nodeId':M+'unstable_anatomy','attributeId':'minecraft:generic.max_health','operation':'ADD_FLAT','amountPerRank':1.0},
 {'effectId':M+'chimeric_memory/dodge','nodeId':M+'chimeric_memory','attributeId':'apothic_attributes:dodge_chance','operation':'ADD_PERCENT_BASE','amountPerRank':0.02},
]
write_tree('metamorph','tree.rpgskilltree.metamorph','metamorph',meta_nodes,meta_edges,meta_effects)
print('Generated Druid and Metamorph subtrees')
