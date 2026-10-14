package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;

/**
 * Bounded Determination investment projected into the native Epic Fight stamina
 * registry. Exact runtime release is required; other stamina systems are not touched.
 */
public final class EpicFightInvestmentAttributePolicy {
    private EpicFightInvestmentAttributePolicy() {}

    public static boolean operational(boolean loaded, String version) {
        return loaded && EpicFightVersionContract.supportsVersion(version);
    }

    public static Benefit benefit() {
        return Benefit.DETERMINATION_STAMINA_REGEN;
    }

    public static String target() {
        return "epicfight:stamina_regen";
    }

    public static String modifierId() {
        return "rpgskilltree:investment/epicfight/determination_stamina_regen";
    }
}
