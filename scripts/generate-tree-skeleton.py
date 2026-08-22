#!/usr/bin/env python3
import json, math
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
blue=json.loads((ROOT/'src/main/resources/data/rpgskilltree/tree_blueprints/main.json').read_text())
regions=blue['regions']; nodes=[]; edges=[]
# 28-node central wheel
center_count=blue['shared_core_nodes']
for i in range(center_count):
    a=2*math.pi*i/center_count
    nodes.append({'id':f'core_{i:02d}','kind':'core','x':round(math.cos(a)*260,3),'y':round(math.sin(a)*260,3)})
    edges.append([f'core_{i:02d}',f'core_{(i+1)%center_count:02d}'])
# Domain fans. Each fan is made of concentric arcs; the exact passive bonuses are filled later.
for ri,region in enumerate(regions):
    count=region['node_budget']; center_angle=-math.pi/2 + 2*math.pi*ri/len(regions)
    ring_sizes=[]; remaining=count
    for desired in (6,8,10,12,14,16):
        if remaining<=0: break
        n=min(desired,remaining); ring_sizes.append(n); remaining-=n
    prev=[]; seq=0
    for ring,n in enumerate(ring_sizes,1):
        radius=520+ring*245
        spread=math.radians(34+ring*4)
        current=[]
        for j in range(n):
            frac=0 if n==1 else j/(n-1)-0.5
            a=center_angle+frac*spread
            nid=f'{region["id"].lower()}_{seq:03d}'; seq+=1
            nodes.append({'id':nid,'kind':'domain','domain':region['id'],'ring':ring,'x':round(math.cos(a)*radius,3),'y':round(math.sin(a)*radius,3)})
            current.append(nid)
            if j: edges.append([current[j-1],nid])
            if prev:
                k=round(j*(len(prev)-1)/max(1,n-1)); edges.append([prev[k],nid])
        prev=current
    # connect domain fan to central wheel near the sector angle
    core_idx=round((center_angle%(2*math.pi))/(2*math.pi)*center_count)%center_count
    first=f'{region["id"].lower()}_000'; edges.append([f'core_{core_idx:02d}',first])
# Hybrid bridges at angular midpoints between the two relevant domain centers.
angle_by_domain={r['id']:-math.pi/2+2*math.pi*i/len(regions) for i,r in enumerate(regions)}
bridges=blue['class_bridges']; per=max(1,blue['hybrid_bridge_nodes']//len(bridges))
for b in bridges:
    a1,a2=(angle_by_domain[d] for d in b['domains'])
    vx,vy=math.cos(a1)+math.cos(a2),math.sin(a1)+math.sin(a2)
    a=math.atan2(vy,vx)
    previous=None
    for i in range(per):
        radius=1120+i*155
        nid=f'bridge_{b["id"]}_{i:02d}'
        nodes.append({'id':nid,'kind':'hybrid','archetype':b['id'],'domains':b['domains'],'x':round(math.cos(a)*radius,3),'y':round(math.sin(a)*radius,3)})
        if previous: edges.append([previous,nid])
        previous=nid
# Outer keystones placed evenly around outer ring.
for i in range(blue['outer_keystone_nodes']):
    a=2*math.pi*i/blue['outer_keystone_nodes']-math.pi/2
    nodes.append({'id':f'keystone_{i:02d}','kind':'keystone','x':round(math.cos(a)*1950,3),'y':round(math.sin(a)*1950,3)})
out={'id':blue['id'],'target_node_count':blue['target_node_count'],'actual_node_count':len(nodes),'nodes':nodes,'edges':edges}
path=ROOT/'generated/main-tree-layout.json'; path.parent.mkdir(exist_ok=True); path.write_text(json.dumps(out,indent=2)+"\n")
print(f'Generated {len(nodes)} layout nodes and {len(edges)} edges -> {path}')
