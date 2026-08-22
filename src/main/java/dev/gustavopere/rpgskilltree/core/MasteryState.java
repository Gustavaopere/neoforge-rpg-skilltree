package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record MasteryState(Map<String,Integer> experience){
 public MasteryState { experience=Map.copyOf(experience); if(experience.values().stream().anyMatch(v->v<0)) throw new IllegalArgumentException("mastery XP must be >= 0"); }
 public static MasteryState of(Map<String,Integer> experience){return new MasteryState(experience);} public int experience(String lane){return experience.getOrDefault(lane,0);}
}
