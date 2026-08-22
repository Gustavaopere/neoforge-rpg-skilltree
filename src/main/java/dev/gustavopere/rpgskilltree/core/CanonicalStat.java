package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record CanonicalStat(String id, Set<String> aliases){
 public CanonicalStat { Objects.requireNonNull(id); Objects.requireNonNull(aliases); if(id.isBlank()) throw new IllegalArgumentException("id"); aliases=Set.copyOf(aliases); }
}
