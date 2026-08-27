package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Explicit source of the authoritative Core progression rules snapshot. */
@FunctionalInterface
public interface ProgressionRulesProvider {
    Optional<ProgressionRulesSnapshot> current();

    static ProgressionRulesProvider unconfigured() {
        return Optional::empty;
    }

    static ProgressionRulesProvider fixed(ProgressionRulesSnapshot rules) {
        Objects.requireNonNull(rules, "rules");
        return () -> Optional.of(rules);
    }

    default ProgressionRulesSnapshot requireCurrent() {
        Optional<ProgressionRulesSnapshot> value = current();
        if (value == null) {
            throw new IllegalStateException("progression rules provider returned null instead of Optional");
        }
        return value.orElseThrow(() -> new IllegalStateException("Core progression rules are not configured"));
    }
}
