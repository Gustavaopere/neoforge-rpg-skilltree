package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;
import java.util.Map;

/**
 * Exact-source Iron's Spells 'n Spellbooks 1.21.1-3.16.3 native attribute contract.
 * No optional-provider implementation classes are referenced by this policy.
 *
 * <p>Evidence: iron431/irons-spells-n-spellbooks branch 1.21 at
 * e4056af90302d37eb1739f5ff05020b020e6e252
 * api/registry/AttributeRegistry.java and capabilities/magic/MagicManager.java.</p>
 */
public final class IronsInvestmentAttributePolicy {
    public static final String SUPPORTED_VERSION = "1.21.1-3.16.3";

    private static final Map<Benefit, String> TARGETS = Map.of(
        Benefit.INTELLIGENCE_MAX_MANA, "irons_spellbooks:max_mana",
        Benefit.INTELLIGENCE_MANA_REGEN, "irons_spellbooks:mana_regen",
        Benefit.INTELLIGENCE_SPELL_POWER, "irons_spellbooks:spell_power"
    );

    private IronsInvestmentAttributePolicy() {}

    public static boolean supportsVersion(String version) {
        return SUPPORTED_VERSION.equals(version);
    }

    public static boolean operational(boolean providerLoaded, String providerVersion) {
        return providerLoaded && supportsVersion(providerVersion);
    }

    public static Map<Benefit, String> targets() {
        return TARGETS;
    }
}
