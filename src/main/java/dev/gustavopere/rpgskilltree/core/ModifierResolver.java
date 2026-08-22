package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public final class ModifierResolver {
 private ModifierResolver() {}
 public static ResolvedModifier resolve(double base, Collection<ModifierSpec> specs) {
  Objects.requireNonNull(specs); if (specs.isEmpty()) return new ResolvedModifier(base, List.of());
  String key = specs.iterator().next().statKey();
  if (specs.stream().anyMatch(s -> !s.statKey().equals(key))) throw new IllegalArgumentException("all modifiers must target one stat");
  List<ModifierSpec> overrides = specs.stream().filter(s -> s.operation()==ModifierOperation.OVERRIDE).toList();
  if (!overrides.isEmpty()) {
   ModifierSpec winner = overrides.stream().sorted(Comparator.comparingInt(ModifierSpec::priority).reversed().thenComparing(ModifierSpec::sourceId)).findFirst().orElseThrow();
   return new ResolvedModifier(winner.amount(), List.of(winner.sourceId()));
  }
  double flat=0, percent=0, more=1;
  for (ModifierSpec s: specs) switch (s.operation()) { case ADD_FLAT -> flat += s.amount(); case ADD_PERCENT_BASE -> percent += s.amount(); case MULTIPLY_TOTAL -> more *= 1+s.amount(); default -> {} }
  List<String> sources = specs.stream().sorted(Comparator.comparing(ModifierSpec::sourceId)).map(ModifierSpec::sourceId).toList();
  return new ResolvedModifier((base + flat + base*percent)*more, sources);
 }
}
