package dev.gustavopere.rpgskilltree.runtime.compat;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class IntegrationAdapterContractJUnitTest {
    private static final SemanticActionId CAST = SemanticActionId.of("rpgskilltree:test_cast");
    private static final SemanticActionId HIT = SemanticActionId.of("rpgskilltree:test_hit");

    @Test
    void absentProviderNeverConstructsProviderAdapterAndFallsBackNeutral() {
        AtomicInteger constructions = new AtomicInteger();
        IntegrationAdapterFactory factory = factory(
            "rpgskilltree:epicfight_test",
            OptionalIntegrations.Provider.EPIC_FIGHT,
            constructions,
            Set.of(capability(HIT))
        );

        IntegrationAdapterRegistry registry = IntegrationAdapterRegistry.create(
            provider -> false,
            List.of(factory)
        );

        assertEquals(0, constructions.get());
        assertTrue(registry.capability(HIT).isEmpty());
        assertEquals(IntegrationAdapterRegistry.AdapterState.ABSENT, registry.diagnostics().getFirst().state());
        assertTrue(registry.summary().contains("rpgskilltree:epicfight_test=absent(provider_absent)"));
    }

    @Test
    void enabledAdapterOwnsItsSemanticCapabilitiesExactlyOnce() {
        AtomicInteger constructions = new AtomicInteger();
        IntegrationAdapterFactory factory = factory(
            "rpgskilltree:ars_test",
            OptionalIntegrations.Provider.ARS_NOUVEAU,
            constructions,
            Set.of(capability(CAST))
        );

        IntegrationAdapterRegistry registry = IntegrationAdapterRegistry.create(
            provider -> provider == OptionalIntegrations.Provider.ARS_NOUVEAU,
            List.of(factory)
        );

        assertEquals(1, constructions.get());
        assertTrue(registry.capability(CAST).isPresent());
        assertEquals(
            SemanticActionId.of("rpgskilltree:ars_test"),
            registry.owner(CAST).orElseThrow()
        );
        assertEquals(IntegrationAdapterRegistry.AdapterState.ENABLED, registry.diagnostics().getFirst().state());
    }

    @Test
    void duplicateSemanticActionOwnersFailClosedInsteadOfDoubleProcessing() {
        IntegrationAdapterFactory first = factory(
            "rpgskilltree:first",
            OptionalIntegrations.Provider.ARS_NOUVEAU,
            new AtomicInteger(),
            Set.of(capability(CAST))
        );
        IntegrationAdapterFactory second = factory(
            "rpgskilltree:second",
            OptionalIntegrations.Provider.IRONS_SPELLBOOKS,
            new AtomicInteger(),
            Set.of(capability(CAST))
        );

        IllegalStateException error = assertThrows(
            IllegalStateException.class,
            () -> IntegrationAdapterRegistry.create(provider -> true, List.of(first, second))
        );

        assertTrue(error.getMessage().contains("rpgskilltree:test_cast"));
        assertTrue(error.getMessage().contains("rpgskilltree:first"));
        assertTrue(error.getMessage().contains("rpgskilltree:second"));
    }

    @Test
    void disabledAdapterPublishesBoundedDiagnosticWithoutCapabilities() {
        IntegrationAdapterFactory factory = new IntegrationAdapterFactory() {
            @Override
            public SemanticActionId adapterId() {
                return SemanticActionId.of("rpgskilltree:disabled_test");
            }

            @Override
            public OptionalIntegrations.Provider provider() {
                return OptionalIntegrations.Provider.EPIC_FIGHT;
            }

            @Override
            public IntegrationAdapter create() {
                return new IntegrationAdapter() {
                    @Override
                    public SemanticActionId adapterId() {
                        return SemanticActionId.of("rpgskilltree:disabled_test");
                    }

                    @Override
                    public OptionalIntegrations.Provider provider() {
                        return OptionalIntegrations.Provider.EPIC_FIGHT;
                    }

                    @Override
                    public Set<? extends IntegrationCapability> capabilities() {
                        return Set.of(capability(HIT));
                    }

                    @Override
                    public boolean enabled() {
                        return false;
                    }

                    @Override
                    public String disabledReason() {
                        return "unsupported_contract";
                    }
                };
            }
        };

        IntegrationAdapterRegistry registry = IntegrationAdapterRegistry.create(provider -> true, List.of(factory));

        assertFalse(registry.capability(HIT).isPresent());
        assertEquals(IntegrationAdapterRegistry.AdapterState.DISABLED, registry.diagnostics().getFirst().state());
        assertTrue(registry.summary().contains("disabled(unsupported_contract)"));
    }

    @Test
    void semanticIdsRequireCanonicalNamespacedSyntax() {
        assertEquals("rpgskilltree:valid_path", SemanticActionId.of("rpgskilltree:valid_path").toString());
        assertThrows(IllegalArgumentException.class, () -> SemanticActionId.of("not_namespaced"));
        assertThrows(IllegalArgumentException.class, () -> SemanticActionId.of("RPG:Uppercase"));
        assertThrows(IllegalArgumentException.class, () -> SemanticActionId.of("rpgskilltree:"));
    }

    private static IntegrationCapability capability(SemanticActionId actionId) {
        return () -> actionId;
    }

    private static IntegrationAdapterFactory factory(
        String adapterId,
        OptionalIntegrations.Provider provider,
        AtomicInteger constructions,
        Set<? extends IntegrationCapability> capabilities
    ) {
        SemanticActionId id = SemanticActionId.of(adapterId);
        return new IntegrationAdapterFactory() {
            @Override
            public SemanticActionId adapterId() {
                return id;
            }

            @Override
            public OptionalIntegrations.Provider provider() {
                return provider;
            }

            @Override
            public IntegrationAdapter create() {
                constructions.incrementAndGet();
                return new IntegrationAdapter() {
                    @Override
                    public SemanticActionId adapterId() {
                        return id;
                    }

                    @Override
                    public OptionalIntegrations.Provider provider() {
                        return provider;
                    }

                    @Override
                    public Set<? extends IntegrationCapability> capabilities() {
                        return capabilities;
                    }
                };
            }
        };
    }
}
