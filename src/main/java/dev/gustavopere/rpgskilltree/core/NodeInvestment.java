package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record NodeInvestment(String nodeId, Map<ProgressionDomain,Integer> domainWeights, Set<String> tags) {
 public NodeInvestment { Objects.requireNonNull(nodeId); Objects.requireNonNull(domainWeights); Objects.requireNonNull(tags); if(nodeId.isBlank()) throw new IllegalArgumentException(); domainWeights=Map.copyOf(domainWeights); tags=Set.copyOf(tags); }
}
