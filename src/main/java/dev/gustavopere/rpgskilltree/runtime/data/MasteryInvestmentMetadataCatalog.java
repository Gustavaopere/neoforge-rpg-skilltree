package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.MasteryInvestmentMetadata;
import dev.gustavopere.rpgskilltree.core.MasteryInvestmentMetadataPolicy;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/** Runtime publication boundary for explicit Mastery-to-investment metadata. */
public final class MasteryInvestmentMetadataCatalog {
    private static volatile List<MasteryInvestmentMetadata> current = List.of();

    private MasteryInvestmentMetadataCatalog() {}

    public static List<MasteryInvestmentMetadata> current() {
        return current;
    }

    public static synchronized void replace(Collection<MasteryInvestmentMetadata> next) {
        Objects.requireNonNull(next, "next");
        current = MasteryInvestmentMetadataPolicy.validate(next);
    }
}
