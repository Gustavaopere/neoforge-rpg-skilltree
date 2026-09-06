package dev.gustavopere.rpgskilltree.runtime.compat;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class OptionalIntegrationAdapterRegistryJUnitTest {
    @Test
    void everyOptionalProviderHasAUniqueProductionAdapterAndSemanticSource() {
        var adapterIds = new HashSet<SemanticActionId>();
        var actionIds = new HashSet<SemanticActionId>();

        for (OptionalIntegrations.Provider provider : OptionalIntegrations.Provider.values()) {
            assertTrue(adapterIds.add(OptionalIntegrationAdapterRegistry.adapterId(provider)));
            assertTrue(actionIds.add(OptionalIntegrationAdapterRegistry.sourceAction(provider)));
        }

        assertEquals(OptionalIntegrations.Provider.values().length, adapterIds.size());
        assertEquals(OptionalIntegrations.Provider.values().length, actionIds.size());
    }

    @Test
    void onlyLoadedAndEnabledProvidersOwnTheirProductionSemanticSource() {
        IntegrationAdapterRegistry registry = OptionalIntegrationAdapterRegistry.create(
            provider -> provider == OptionalIntegrations.Provider.EPIC_FIGHT,
            provider -> ""
        );

        assertTrue(OptionalIntegrationAdapterRegistry.isActive(registry, OptionalIntegrations.Provider.EPIC_FIGHT));
        assertFalse(OptionalIntegrationAdapterRegistry.isActive(registry, OptionalIntegrations.Provider.ARS_NOUVEAU));
        assertEquals(
            OptionalIntegrationAdapterRegistry.adapterId(OptionalIntegrations.Provider.EPIC_FIGHT),
            registry.owner(OptionalIntegrationAdapterRegistry.sourceAction(OptionalIntegrations.Provider.EPIC_FIGHT)).orElseThrow()
        );
        assertTrue(registry.capability(
            OptionalIntegrationAdapterRegistry.sourceAction(OptionalIntegrations.Provider.EPIC_FIGHT)
        ).isPresent());
    }

    @Test
    void loadedButDisabledProviderPublishesNoProductionCapability() {
        IntegrationAdapterRegistry registry = OptionalIntegrationAdapterRegistry.create(
            provider -> provider == OptionalIntegrations.Provider.EPIC_FIGHT,
            provider -> provider == OptionalIntegrations.Provider.EPIC_FIGHT ? "unsupported_version" : ""
        );

        assertFalse(OptionalIntegrationAdapterRegistry.isActive(registry, OptionalIntegrations.Provider.EPIC_FIGHT));
        assertTrue(registry.owner(
            OptionalIntegrationAdapterRegistry.sourceAction(OptionalIntegrations.Provider.EPIC_FIGHT)
        ).isEmpty());
        assertEquals(IntegrationAdapterRegistry.AdapterState.DISABLED, registry.diagnostics().stream()
            .filter(entry -> entry.adapterId().equals(
                OptionalIntegrationAdapterRegistry.adapterId(OptionalIntegrations.Provider.EPIC_FIGHT)
            ))
            .findFirst()
            .orElseThrow()
            .state());
        assertTrue(registry.summary().contains("rpgskilltree:adapter/epicfight=disabled(unsupported_version)"));
    }
}
