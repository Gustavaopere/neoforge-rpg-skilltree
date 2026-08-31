package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.gustavopere.rpgskilltree.core.ArchetypeDefinition;
import dev.gustavopere.rpgskilltree.core.InvestmentState;
import dev.gustavopere.rpgskilltree.core.NodeInvestment;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import dev.gustavopere.rpgskilltree.runtime.data.ArchetypeCatalog;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class ClassResolutionRuntimeJUnitTest {
    @Test
    void resolvesAgainstCurrentCatalogWithoutBecomingPlayerStateAuthority() {
        List<ArchetypeDefinition> previous = ArchetypeCatalog.definitions();
        try {
            ArchetypeCatalog.replace(List.of(
                new ArchetypeDefinition(
                    "rpgskilltree:mage", 10, 1,
                    Map.of(ProgressionDomain.ARCANE, 8), Set.of(), Set.of()
                ),
                new ArchetypeDefinition(
                    "rpgskilltree:spellblade", 30, 2,
                    Map.of(ProgressionDomain.ARCANE, 8, ProgressionDomain.MARTIAL, 8), Set.of(), Set.of()
                )
            ));
            InvestmentState state = InvestmentState.of(List.of(
                new NodeInvestment("arcane", Map.of(ProgressionDomain.ARCANE, 10), Set.of()),
                new NodeInvestment("martial", Map.of(ProgressionDomain.MARTIAL, 9), Set.of())
            ));

            var resolution = ClassResolutionRuntime.resolve(state);

            assertEquals("rpgskilltree:spellblade", resolution.primaryClassId().orElseThrow());
            assertEquals(List.of("rpgskilltree:mage"), resolution.secondaryClassIds());
            assertEquals(2, ArchetypeCatalog.size());
        } finally {
            ArchetypeCatalog.replace(previous);
        }
    }
}
