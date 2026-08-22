package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record EngineeringAction(ActionOrigin origin,String provider,String actionId,Set<String> tags,double magnitude){
 public EngineeringAction{Objects.requireNonNull(origin);Objects.requireNonNull(provider);Objects.requireNonNull(actionId);tags=Set.copyOf(tags);if(!Double.isFinite(magnitude)||magnitude<0)throw new IllegalArgumentException();}
}
