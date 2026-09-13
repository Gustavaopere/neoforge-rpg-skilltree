package dev.gustavopere.rpgskilltree;

import dev.gustavopere.rpgskilltree.itemization.blacksmith.compat.productivemetalworks.ProductiveMetalworksVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.coldsweat.ColdSweatFrenzyBridge;
import dev.gustavopere.rpgskilltree.runtime.compat.create.CreateVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.goety.GoetyVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.malum.MalumVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.MineColoniesVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyVersionContract;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class RpgSkillTreeOptionalAdapterPolicyJUnitTest {
    @Test
    void auditedVersionsEnableEveryVersionGatedProvider() {
        assertEquals("", reason(OptionalIntegrations.Provider.EPIC_FIGHT, EpicFightVersionContract.SUPPORTED_VERSION));
        assertEquals("", reason(OptionalIntegrations.Provider.COLD_SWEAT, ColdSweatFrenzyBridge.SUPPORTED_VERSION));
        assertEquals("", reason(OptionalIntegrations.Provider.CREATE, CreateVersionContract.SUPPORTED_VERSION));
        assertEquals("", reason(OptionalIntegrations.Provider.GOETY, GoetyVersionContract.SUPPORTED_VERSION));
        assertEquals("", reason(OptionalIntegrations.Provider.MALUM, MalumVersionContract.SUPPORTED_VERSION));
        assertEquals("", reason(OptionalIntegrations.Provider.EIDOLON, EidolonVersionContract.SUPPORTED_VERSION));
        assertEquals("", reason(OptionalIntegrations.Provider.MINECOLONIES, MineColoniesVersionContract.SUPPORTED_ARTIFACT_VERSION));
        assertEquals("", reason(OptionalIntegrations.Provider.PRODUCTIVE_METALWORKS, ProductiveMetalworksVersionContract.SUPPORTED_ARTIFACT_VERSION));
    }

    @Test
    void unsupportedVersionsFailClosedForEveryVersionGatedProvider() {
        String unsupported = "0.0.0-unsupported";
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.EPIC_FIGHT, unsupported));
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.COLD_SWEAT, unsupported));
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.CREATE, unsupported));
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.GOETY, unsupported));
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.MALUM, unsupported));
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.EIDOLON, unsupported));
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.MINECOLONIES, unsupported));
        assertEquals("unsupported_version", reason(OptionalIntegrations.Provider.PRODUCTIVE_METALWORKS, unsupported));
    }

    @Test
    void mineColoniesCommonAdapterAcceptsEitherAuditedRoute() {
        assertEquals("", reason(
            OptionalIntegrations.Provider.MINECOLONIES,
            MineColoniesVersionContract.SUPPORTED_ARTIFACT_VERSION
        ));
        assertEquals("", reason(
            OptionalIntegrations.Provider.MINECOLONIES,
            MineColoniesEconomyVersionContract.CURRENT_SUPPORTED_ARTIFACT_VERSION
        ));
    }

    @Test
    void providersWithoutVersionGateRemainNeutral() {
        for (OptionalIntegrations.Provider provider : new OptionalIntegrations.Provider[] {
            OptionalIntegrations.Provider.IRONS_SPELLBOOKS,
            OptionalIntegrations.Provider.ARS_NOUVEAU,
            OptionalIntegrations.Provider.IDENTITY2,
            OptionalIntegrations.Provider.SABLE
        }) {
            assertEquals("", reason(provider, "0.0.0-unsupported"), provider::name);
        }
    }

    private static String reason(OptionalIntegrations.Provider provider, String version) {
        return RpgSkillTreeMod.optionalAdapterDisabledReason(provider, version);
    }
}
