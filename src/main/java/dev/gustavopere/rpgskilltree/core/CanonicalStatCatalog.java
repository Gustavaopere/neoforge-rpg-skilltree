package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public final class CanonicalStatCatalog {
 private final Map<String,CanonicalStat> byKey; private final Set<String> canonicalIds;
 public CanonicalStatCatalog(Collection<CanonicalStat> stats){
  Map<String,CanonicalStat> map=new HashMap<>(); Set<String> ids=new HashSet<>();
  for(CanonicalStat stat:stats){ if(!ids.add(stat.id())) throw new IllegalArgumentException("duplicate canonical stat: "+stat.id()); register(map,stat.id(),stat); for(String a:stat.aliases()) register(map,a,stat); }
  byKey=Map.copyOf(map); canonicalIds=Set.copyOf(ids);
 }
 private static void register(Map<String,CanonicalStat> map,String key,CanonicalStat stat){CanonicalStat old=map.putIfAbsent(key,stat);if(old!=null&&!old.id().equals(stat.id()))throw new IllegalArgumentException("ambiguous stat alias: "+key);}
 public CanonicalStat resolve(String key){CanonicalStat stat=byKey.get(key);if(stat==null)throw new IllegalArgumentException("unknown stat: "+key);return stat;}
 public int size(){return canonicalIds.size();} public Set<String> canonicalIds(){return canonicalIds;}
 public static CanonicalStatCatalog defaults(){
  List<CanonicalStat> s=new ArrayList<>();
  s.add(stat("minecraft:max_health","max_health")); s.add(stat("minecraft:armor","armor")); s.add(stat("minecraft:armor_toughness","armor_toughness")); s.add(stat("minecraft:attack_damage","attack_damage")); s.add(stat("minecraft:attack_speed","attack_speed")); s.add(stat("minecraft:movement_speed","movement_speed")); s.add(stat("minecraft:knockback_resistance","knockback_resistance")); s.add(stat("minecraft:luck","luck"));
  for(String id:List.of("max_mana","mana_regen","cooldown_reduction","spell_power","spell_resist","cast_time_reduction","summon_damage","casting_movespeed")) s.add(stat("irons:"+id,id,"irons_spellbooks:"+id));
  for(String school:List.of("fire","ice","lightning","holy","ender","blood","evocation","nature","eldritch")){s.add(stat("irons:"+school+"_spell_power","irons_spellbooks:"+school+"_spell_power"));s.add(stat("irons:"+school+"_magic_resist","irons_spellbooks:"+school+"_magic_resist"));}
  s.add(stat("ars:amplification")); s.add(stat("ars:damage_modifier")); s.add(stat("ars:duration_multiplier")); s.add(stat("ars:aoe_multiplier")); s.add(stat("ars:acceleration"));
  s.add(stat("passive:projectile_duplication")); s.add(stat("passive:projectile_speed"));
  return new CanonicalStatCatalog(s);
 }
 private static CanonicalStat stat(String id,String...aliases){return new CanonicalStat(id,Set.of(aliases));}
}
