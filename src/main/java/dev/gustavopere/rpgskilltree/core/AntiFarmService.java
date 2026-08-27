package dev.gustavopere.rpgskilltree.core;

@FunctionalInterface
public interface AntiFarmService {
    AntiFarmDecision evaluate(SemanticAction action);
}
