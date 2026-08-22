package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record TreeUnlockDefinition(String treeId,Map<ProgressionDomain,Integer> minimumDomainScores,Set<String> requiredTags,Map<String,Integer> minimumMasteryExperience){
 public TreeUnlockDefinition { Objects.requireNonNull(treeId); if(treeId.isBlank()) throw new IllegalArgumentException("treeId"); minimumDomainScores=Map.copyOf(minimumDomainScores); requiredTags=Set.copyOf(requiredTags); minimumMasteryExperience=Map.copyOf(minimumMasteryExperience); }
}
