package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record SpellAction(ActionOrigin origin,String provider,String spellId,String discipline,Set<String> tags,int resourceCost){
 public SpellAction{Objects.requireNonNull(origin);Objects.requireNonNull(provider);Objects.requireNonNull(spellId);Objects.requireNonNull(discipline);tags=Set.copyOf(tags);if(provider.isBlank()||spellId.isBlank()||discipline.isBlank()||resourceCost<0)throw new IllegalArgumentException();}
 public String stableActionId(){return provider+":"+spellId;} public SpellAction withOrigin(ActionOrigin o){return new SpellAction(o,provider,spellId,discipline,tags,resourceCost);}
}
