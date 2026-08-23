package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public final class CoreProgressionTest {
 public static void main(String[] a){modifierOrder();override();archetypes();hybrids();tags();treeUnlocksRequireGatewayAndMastery();canonicalStatsResolveWithoutAmbiguity();normalizedActionsHaveStableIdentityAndProcGuard();attunementRespecEjectsOverflowDeterministically();masteryPoliciesRewardRealActionsOnly();System.out.println("CoreProgressionTest: PASS");}
 static void modifierOrder(){var r=ModifierResolver.resolve(10,List.of(new ModifierSpec("attack_damage",ModifierOperation.ADD_FLAT,5,"node:flat",0),new ModifierSpec("attack_damage",ModifierOperation.ADD_PERCENT_BASE,.2,"node:percent",0),new ModifierSpec("attack_damage",ModifierOperation.MULTIPLY_TOTAL,.5,"node:more",0)));close(25.5,r.value());}
 static void override(){var r=ModifierResolver.resolve(1,List.of(new ModifierSpec("x",ModifierOperation.OVERRIDE,2,"z",10),new ModifierSpec("x",ModifierOperation.OVERRIDE,3,"a",10)));eq(3.0,r.value());}
 static void archetypes(){var s=InvestmentState.of(List.of(new NodeInvestment("a",Map.of(ProgressionDomain.ARCANE,8),Set.of("magic")),new NodeInvestment("m",Map.of(ProgressionDomain.MARTIAL,7),Set.of())));var mage=new ArchetypeDefinition("mage",10,Map.of(ProgressionDomain.ARCANE,8),Set.of(),Set.of());var war=new ArchetypeDefinition("warrior",10,Map.of(ProgressionDomain.MARTIAL,8),Set.of(),Set.of());eq(List.of("mage"),ArchetypeResolver.resolve(s,List.of(mage,war)).stream().map(ArchetypeMatch::archetypeId).toList());}
 static void hybrids(){var mage=new ArchetypeDefinition("mage",10,Map.of(ProgressionDomain.ARCANE,8),Set.of(),Set.of());var sb=new ArchetypeDefinition("spellblade",20,Map.of(ProgressionDomain.ARCANE,6,ProgressionDomain.MARTIAL,6),Set.of(),Set.of());var tm=new ArchetypeDefinition("technomancer",30,Map.of(ProgressionDomain.ARCANE,6,ProgressionDomain.ENGINEERING,6),Set.of(),Set.of());var s=InvestmentState.of(List.of(new NodeInvestment("a",Map.of(ProgressionDomain.ARCANE,10),Set.of()),new NodeInvestment("m",Map.of(ProgressionDomain.MARTIAL,7),Set.of())));eq(List.of("spellblade","mage"),ArchetypeResolver.resolve(s,List.of(mage,sb,tm)).stream().map(ArchetypeMatch::archetypeId).toList());var t=InvestmentState.of(List.of(new NodeInvestment("a",Map.of(ProgressionDomain.ARCANE,9),Set.of()),new NodeInvestment("e",Map.of(ProgressionDomain.ENGINEERING,8),Set.of())));eq(List.of("technomancer","mage"),ArchetypeResolver.resolve(t,List.of(mage,sb,tm)).stream().map(ArchetypeMatch::archetypeId).toList());}
 static void tags(){var py=new ArchetypeDefinition("pyromancer",25,Map.of(ProgressionDomain.ARCANE,6),Set.of("mastery:fire"),Set.of("oath:no_elemental"));var s=InvestmentState.of(List.of(new NodeInvestment("f",Map.of(ProgressionDomain.ARCANE,8),Set.of("mastery:fire"))));eq(List.of("pyromancer"),ArchetypeResolver.resolve(s,List.of(py)).stream().map(ArchetypeMatch::archetypeId).toList());}

 static void treeUnlocksRequireGatewayAndMastery(){
  var fireTree=new TreeUnlockDefinition("irons:fire",Map.of(ProgressionDomain.ARCANE,8),Set.of("gateway:irons_fire"),Map.of("irons:fire",100));
  var invested=InvestmentState.of(List.of(new NodeInvestment("fire_gate",Map.of(ProgressionDomain.ARCANE,10),Set.of("gateway:irons_fire"))));
  eq(false,TreeUnlockResolver.canUnlock(invested,MasteryState.of(Map.of("irons:fire",99)),fireTree));
  eq(true,TreeUnlockResolver.canUnlock(invested,MasteryState.of(Map.of("irons:fire",100)),fireTree));
  var noGate=InvestmentState.of(List.of(new NodeInvestment("arcane",Map.of(ProgressionDomain.ARCANE,50),Set.of())));
  eq(false,TreeUnlockResolver.canUnlock(noGate,MasteryState.of(Map.of("irons:fire",9999)),fireTree));
 }

 static void canonicalStatsResolveWithoutAmbiguity(){
  var catalog=CanonicalStatCatalog.defaults();
  eq("irons:spell_power",catalog.resolve("spell_power").id());
  eq("irons:fire_spell_power",catalog.resolve("irons_spellbooks:fire_spell_power").id());
  eq("minecraft:attack_damage",catalog.resolve("attack_damage").id());
  eq("ars:amplification",catalog.resolve("ars:amplification").id());
  eq(catalog.size(),catalog.canonicalIds().size());
  boolean rejected=false;
  try { new CanonicalStatCatalog(List.of(new CanonicalStat("a:x",Set.of("same")),new CanonicalStat("b:x",Set.of("same")))); } catch(IllegalArgumentException expected){ rejected=true; }
  eq(true,rejected);
 }

 static void normalizedActionsHaveStableIdentityAndProcGuard(){
  var spell=new SpellAction(new ActionOrigin("irons_spellbooks:fireball",0),"irons","irons_spellbooks:fireball","fire",Set.of("spell","projectile"),40);
  eq("irons:irons_spellbooks:fireball",spell.stableActionId());
  eq(true,ProcGuard.mayTriggerSecondaryEffect(spell.origin()));
  var duplicated=spell.withOrigin(spell.origin().child("rpgskilltree:echo_cast"));
  eq(false,ProcGuard.mayTriggerSecondaryEffect(duplicated.origin()));
  var award=new MasteryAward("irons:fire",5,"irons_spellbooks:fireball");
  eq(5,award.experience());
  boolean rejected=false; try { new MasteryAward("irons:fire",0,"bad"); } catch(IllegalArgumentException expected){rejected=true;} eq(true,rejected);
 }

 static void attunementRespecEjectsOverflowDeterministically(){
  var current=List.of(new AttunedItem("relics:relic_a",0),new AttunedItem("artifacts:artifact_b",1),new AttunedItem("create_wizardry:focus",2));
  var plan=AttunementPlanner.resize(current,2);
  eq(List.of("relics:relic_a","artifacts:artifact_b"),plan.kept().stream().map(AttunedItem::itemId).toList());
  eq(List.of("create_wizardry:focus"),plan.ejected().stream().map(AttunedItem::itemId).toList());
  eq(0,AttunementPlanner.resize(current,0).kept().size());
 }

 static void masteryPoliciesRewardRealActionsOnly(){
  var fire=new SpellAction(new ActionOrigin("irons_spellbooks:fireball",0),"irons","irons_spellbooks:fireball","fire",Set.of("spell","projectile"),40);
  eq(List.of("magic:casting","irons:casting","irons:fire"),MasteryPolicies.forIron(fire).stream().map(MasteryAward::laneId).toList());
  eq(List.of(),MasteryPolicies.forIron(fire.withOrigin(fire.origin().child("rpgskilltree:echo"))));
  var ars=new SpellAction(new ActionOrigin("ars_nouveau:glyph_spell",0),"ars","ars_nouveau:glyph_spell","glyph",Set.of("projectile","amplification"),20);
  eq(Set.of("magic:casting","ars:casting","ars:projectile","ars:amplification"),new HashSet<>(MasteryPolicies.forArs(ars).stream().map(MasteryAward::laneId).toList()));
  eq(List.of(),MasteryPolicies.forArs(ars.withOrigin(ars.origin().child("rpgskilltree:echo"))));
  var engineering=new EngineeringAction(new ActionOrigin("create:mechanical_crafter",0),"create","assemble",Set.of("kinetics","logistics"),1);
  eq(Set.of("create:engineering","create:kinetics","create:logistics"),new HashSet<>(MasteryPolicies.forCreate(engineering).stream().map(MasteryAward::laneId).toList()));
 }
 static void close(double e,double a){if(Math.abs(e-a)>1e-9)throw new AssertionError(e+" != "+a);} static void eq(Object e,Object a){if(!Objects.equals(e,a))throw new AssertionError(e+" != "+a);}
}
