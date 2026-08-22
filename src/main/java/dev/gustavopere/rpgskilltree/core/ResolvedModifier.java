package dev.gustavopere.rpgskilltree.core;
import java.util.List;
public record ResolvedModifier(double value, List<String> sourceIds) { public ResolvedModifier { sourceIds = List.copyOf(sourceIds); } }
