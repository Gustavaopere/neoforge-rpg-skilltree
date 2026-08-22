package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public final class MasteryPolicies {
 private MasteryPolicies(){}
 public static List<MasteryAward> forIron(SpellAction action){
  if(action.origin().procDepth()>0) return List.of();
  return List.of(new MasteryAward("magic:casting",2,action.spellId()),new MasteryAward("irons:"+action.discipline(),5,action.spellId()));
 }
 public static List<MasteryAward> forArs(SpellAction action){
  if(action.origin().procDepth()>0) return List.of();
  List<MasteryAward> out=new ArrayList<>();out.add(new MasteryAward("magic:casting",2,action.spellId()));
  for(String lane:List.of("projectile","amplification","aoe","duration","summoning","control")) if(action.tags().contains(lane)) out.add(new MasteryAward("ars:"+lane,3,action.spellId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forCreate(EngineeringAction action){
  if(action.origin().procDepth()>0) return List.of();
  List<MasteryAward> out=new ArrayList<>();out.add(new MasteryAward("create:engineering",3,action.actionId()));
  for(String lane:List.of("kinetics","logistics","artillery","aeronautics","power","automation")) if(action.tags().contains(lane)) out.add(new MasteryAward("create:"+lane,3,action.actionId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forEpicFight(CombatAction action){
  if(action.origin().procDepth()>0) return List.of();
  return List.of(new MasteryAward("epicfight:weapon",2,action.skillId()),new MasteryAward("epicfight:"+action.weaponCategory(),3,action.skillId()));
 }
}
