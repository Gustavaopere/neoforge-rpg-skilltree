package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public final class MasteryPolicies {
 private MasteryPolicies(){}
 public static List<MasteryAward> forIron(SpellAction action){
  if(action.origin().procDepth()>0) return List.of();
  int intensity=Math.max(1,Math.min(5,(Math.max(1,action.resourceCost())+49)/50));
  return List.of(
   new MasteryAward(MasteryLaneCatalog.MAGIC_CASTING,2,action.spellId()),
   new MasteryAward(MasteryLaneCatalog.IRONS_CASTING,2+intensity,action.spellId()),
   new MasteryAward(MasteryLaneCatalog.ironsDiscipline(action.discipline()),4+intensity,action.spellId())
  );
 }
 public static List<MasteryAward> forArs(SpellAction action){
  if(action.origin().procDepth()>0) return List.of();
  int intensity=Math.max(1,Math.min(5,(Math.max(1,action.resourceCost())+49)/50));
  List<MasteryAward> out=new ArrayList<>();
  out.add(new MasteryAward(MasteryLaneCatalog.MAGIC_CASTING,2,action.spellId()));
  out.add(new MasteryAward(MasteryLaneCatalog.ARS_CASTING,2+intensity,action.spellId()));
  for(String lane:List.of("projectile","amplification","aoe","duration","summoning","control")) if(action.tags().contains(lane)) out.add(new MasteryAward(MasteryLaneCatalog.ars(lane),2+intensity,action.spellId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forGoety(SpellAction action){
  if(action.origin().procDepth()>0) return List.of();
  int intensity=Math.max(1,Math.min(4,(Math.max(1,action.resourceCost())+49)/50));
  List<MasteryAward> out=new ArrayList<>();
  out.add(new MasteryAward(MasteryLaneCatalog.OCCULT_PRACTICE,2,action.spellId()));
  out.add(new MasteryAward(MasteryLaneCatalog.GOETY_CASTING,2+intensity,action.spellId()));
  out.add(new MasteryAward(MasteryLaneCatalog.GOETY_SOUL_SPENDING,1+intensity,action.spellId()));
  for(String lane:List.of("necromancy","nether","ill","frost","geomancy","wind","storm","abyss","wild","void","summoning")) if(action.tags().contains(lane)) out.add(new MasteryAward(MasteryLaneCatalog.goety(lane),3+intensity,action.spellId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forGoetyServant(CombatAction action){
  if(action.origin().procDepth()>0 || !action.tags().contains("servant_kill")) return List.of();
  int intensity=Math.max(1,Math.min(4,(int)Math.ceil(Math.max(1.0D,action.damage())/10.0D)));
  List<MasteryAward> out=new ArrayList<>();
  out.add(new MasteryAward(MasteryLaneCatalog.GOETY_SERVANTS,2+intensity,action.skillId()));
  out.add(new MasteryAward(MasteryLaneCatalog.SUMMONING_PRACTICE,2,action.skillId()));
  if(action.tags().contains("necromancer")) out.add(new MasteryAward(MasteryLaneCatalog.goety("necromancy"),3+intensity,action.skillId()));
  if(action.tags().contains("warlock")) out.add(new MasteryAward(MasteryLaneCatalog.GOETY_PACT_SERVANTS,2+intensity,action.skillId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forGoetyCommand(GoetyCommandAction action){
  if(action.origin().procDepth()>0 || !action.tags().contains("confirmed_command") || action.servantCount()<=0) return List.of();
  int breadth=Math.min(3,action.servantCount());
  List<MasteryAward> out=new ArrayList<>();
  out.add(new MasteryAward(MasteryLaneCatalog.GOETY_COMMANDING,2+breadth,action.targetId()));
  out.add(new MasteryAward(MasteryLaneCatalog.SUMMONING_PRACTICE,1+Math.min(2,action.servantCount()),action.targetId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forMalum(SpiritPracticeAction action){
  if(action.origin().procDepth()>0) return List.of();
  List<MasteryAward> out=new ArrayList<>();
  int magnitude=Math.max(1,Math.min(action.magnitude(),8));
  out.add(new MasteryAward(MasteryLaneCatalog.MALUM_SPIRIT_ARCANA,2+Math.min(3,magnitude/2),action.actionId()));
  if(action.tags().contains("reaping")){
   out.add(new MasteryAward(MasteryLaneCatalog.OCCULT_PRACTICE,2,action.actionId()));
   out.add(new MasteryAward(MasteryLaneCatalog.MALUM_REAPING,2+Math.min(6,magnitude),action.actionId()));
  }
  if(action.tags().contains("collection")) out.add(new MasteryAward(MasteryLaneCatalog.MALUM_COLLECTION,1,action.actionId()));
  action.tags().stream().filter(tag->tag.startsWith("spirit:")).sorted().forEach(tag->{
   String affinity=tag.substring("spirit:".length());
   if(!affinity.isBlank()) out.add(new MasteryAward(MasteryLaneCatalog.malumSpirit(affinity),2,action.actionId()));
  });
  return List.copyOf(out);
 }
 public static List<MasteryAward> forEidolonRitual(EidolonRitualAction action){
  if(action.origin().procDepth()>0 || !action.tags().contains("confirmed_ritual")) return List.of();
  int ritualXp=action.firstCompletion()?8:3;
  int occultXp=action.firstCompletion()?4:2;
  List<MasteryAward> out=new ArrayList<>();
  out.add(new MasteryAward(MasteryLaneCatalog.EIDOLON_RITUAL,ritualXp,action.ritualId()));
  out.add(new MasteryAward(MasteryLaneCatalog.OCCULT_PRACTICE,occultXp,action.ritualId()));
  if(action.tags().contains("summoning")) out.add(new MasteryAward(MasteryLaneCatalog.SUMMONING_PRACTICE,action.firstCompletion()?4:2,action.ritualId()));
  if(action.tags().contains("holy")) out.add(new MasteryAward(MasteryLaneCatalog.HEALING_PRACTICE,action.firstCompletion()?3:1,action.ritualId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forEidolonAlchemy(EidolonAlchemyAction action){
  if(action.origin().procDepth()>0 || !action.confirmedResult()) return List.of();
  List<MasteryAward> out=new ArrayList<>();
  out.add(new MasteryAward(MasteryLaneCatalog.EIDOLON_ALCHEMY,action.firstCompletion()?8:3,action.recipeId()));
  out.add(new MasteryAward(MasteryLaneCatalog.OCCULT_PRACTICE,action.firstCompletion()?3:1,action.recipeId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forCreate(EngineeringAction action){
  if(action.origin().procDepth()>0) return List.of();
  List<MasteryAward> out=new ArrayList<>();out.add(new MasteryAward(MasteryLaneCatalog.CREATE_ENGINEERING,3,action.actionId()));
  for(String lane:List.of("kinetics","logistics","artillery","aeronautics","power","automation")) if(action.tags().contains(lane)) out.add(new MasteryAward(MasteryLaneCatalog.create(lane),3,action.actionId()));
  return List.copyOf(out);
 }
 public static List<MasteryAward> forEpicFight(CombatAction action){
  if(action.origin().procDepth()>0 || !action.tags().contains("milestone")) return List.of();
  List<MasteryAward> out=new ArrayList<>();
  if(action.tags().contains("hit")){
   // One hostile entity-type discovery per weapon category. Six distinct types reach the
   // foundation gate (60), eight reach capstones (80), and ten reach mastery scaling (100).
   out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_WEAPON,5,action.skillId()));
   out.add(new MasteryAward(MasteryLaneCatalog.epicFightWeaponCategory(action.weaponCategory()),10,action.skillId()));
  }
  if(action.tags().contains("skill")){
   int intensity=Math.max(1,Math.min(3,(int)Math.ceil(Math.max(0.0D,action.damage()))));
   out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_PRACTICE,1,action.skillId()));
   out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_SKILL,2,action.skillId()));
   if(action.tags().contains("stamina")) out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_STAMINA,1+intensity,action.skillId()));
   // Guard milestones are additionally deduplicated by hostile target type in the runtime
   // adapter. Six distinct combat-context discoveries therefore reach 60 and eight reach 80.
   if(action.tags().contains("guard")) out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_GUARD,10,action.skillId()));
   if(action.tags().contains("dodge")) out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_DODGE,2,action.skillId()));
   if(action.tags().contains("mover")) out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_MOBILITY,2,action.skillId()));
   if(action.tags().contains("weapon_innate")) out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_WEAPON_INNATE,3,action.skillId()));
  }
  if(action.tags().contains("dodge_success")){
   out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_PRACTICE,2,action.skillId()));
   out.add(new MasteryAward(MasteryLaneCatalog.EPICFIGHT_DODGE,6,action.skillId()));
   out.add(new MasteryAward(MasteryLaneCatalog.AGILITY_PRACTICE,3,action.skillId()));
  }
  return List.copyOf(out);
 }
}