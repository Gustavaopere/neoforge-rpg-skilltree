package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public record ArchetypeDefinition(String id,int priority,Map<ProgressionDomain,Integer> minimumDomainScores,Set<String> requiredTags,Set<String> forbiddenTags){public ArchetypeDefinition{Objects.requireNonNull(id);minimumDomainScores=Map.copyOf(minimumDomainScores);requiredTags=Set.copyOf(requiredTags);forbiddenTags=Set.copyOf(forbiddenTags);}public int specificity(){return minimumDomainScores.size()+requiredTags.size()+forbiddenTags.size();}}
