package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record CombatAction(ActionOrigin origin,String provider,String weaponCategory,String skillId,Set<String> tags,double damage){
 public CombatAction{Objects.requireNonNull(origin);Objects.requireNonNull(provider);Objects.requireNonNull(weaponCategory);Objects.requireNonNull(skillId);tags=Set.copyOf(tags);if(!Double.isFinite(damage)||damage<0)throw new IllegalArgumentException();}
}
