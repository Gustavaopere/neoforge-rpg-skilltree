package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public final class InvestmentState {
 private final Map<ProgressionDomain,Integer> scores; private final Set<String> tags;
 private InvestmentState(Map<ProgressionDomain,Integer> scores, Set<String> tags){this.scores=Map.copyOf(scores);this.tags=Set.copyOf(tags);}
 public static InvestmentState of(Collection<NodeInvestment> inv){EnumMap<ProgressionDomain,Integer>s=new EnumMap<>(ProgressionDomain.class);Set<String>t=new HashSet<>();for(NodeInvestment n:inv){n.domainWeights().forEach((d,w)->s.merge(d,w,Integer::sum));t.addAll(n.tags());}return new InvestmentState(s,t);}
 public int domainScore(ProgressionDomain d){return scores.getOrDefault(d,0);} public boolean hasTag(String t){return tags.contains(t);} public Map<ProgressionDomain,Integer> domainScores(){return scores;} public Set<String> tags(){return tags;}
}
