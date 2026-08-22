package dev.gustavopere.rpgskilltree.core;
public final class TreeUnlockResolver{
 private TreeUnlockResolver(){}
 public static boolean canUnlock(InvestmentState investment, MasteryState mastery, TreeUnlockDefinition definition){
  return definition.minimumDomainScores().entrySet().stream().allMatch(e->investment.domainScore(e.getKey())>=e.getValue())
    && definition.requiredTags().stream().allMatch(investment::hasTag)
    && definition.minimumMasteryExperience().entrySet().stream().allMatch(e->mastery.experience(e.getKey())>=e.getValue());
 }
}
