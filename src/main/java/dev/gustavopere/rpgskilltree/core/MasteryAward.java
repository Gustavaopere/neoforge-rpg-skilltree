package dev.gustavopere.rpgskilltree.core;
import java.util.Objects;
public record MasteryAward(String laneId,int experience,String sourceId){public MasteryAward{Objects.requireNonNull(laneId);Objects.requireNonNull(sourceId);if(laneId.isBlank()||sourceId.isBlank()||experience<=0)throw new IllegalArgumentException("mastery award must be positive");}}
