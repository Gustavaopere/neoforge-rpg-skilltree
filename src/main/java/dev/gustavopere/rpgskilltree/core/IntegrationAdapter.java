package dev.gustavopere.rpgskilltree.core;
import java.util.List;
public interface IntegrationAdapter<A>{String id(); List<MasteryAward> masteryAwards(A action,InvestmentState investment);}
