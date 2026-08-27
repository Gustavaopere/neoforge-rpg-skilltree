package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.InstallableProgressionRulesProvider;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesProvider;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import java.util.Objects;
import java.util.Optional;

/** Runtime holder for the explicitly loaded uncapped Core progression rules. */
public final class CoreProgressionRulesCatalog {
    private static final InstallableProgressionRulesProvider PROVIDER =
        new InstallableProgressionRulesProvider();

    private CoreProgressionRulesCatalog() {}

    /** Read-only provider boundary for runtime consumers. */
    public static ProgressionRulesProvider provider() {
        return PROVIDER;
    }

    public static Optional<ProgressionRulesSnapshot> current() {
        return PROVIDER.current();
    }

    public static void install(ProgressionRulesSnapshot rules) {
        Objects.requireNonNull(rules, "rules");
        PROVIDER.install(rules);
    }

    public static void clear() {
        PROVIDER.clear();
    }
}
