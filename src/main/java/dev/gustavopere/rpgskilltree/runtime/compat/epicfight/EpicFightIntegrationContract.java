package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import java.util.Set;

/**
 * Provider-neutral Stage 06.02 contract for Epic Fight integration.
 *
 * <p>This class intentionally contains no Epic Fight API types so the core can evaluate provider
 * presence/version, fallback ownership and required attribute IDs without classloading the
 * optional provider.</p>
 */
public final class EpicFightIntegrationContract {
    private static final Set<String> REQUIRED_ATTRIBUTE_IDS = Set.of(
        "epicfight:stamina",
        "epicfight:stamina_regen",
        "epicfight:impact"
    );

    private EpicFightIntegrationContract() {}

    /** Epic Fight owns the hit lifecycle only for the exact provider version audited by this build. */
    public static boolean providerOwnsHitPipeline(boolean providerLoaded, String version) {
        return providerLoaded && EpicFightVersionContract.supportsVersion(version);
    }

    /**
     * Vanilla fallback is legal only when Epic Fight did not classify the held weapon and the
     * weapon is one of the explicitly supported vanilla fallback cases.
     */
    public static boolean shouldUseVanillaFallback(
        boolean providerClassifiedWeapon,
        boolean exactVanillaFallbackWeapon
    ) {
        return !providerClassifiedWeapon && exactVanillaFallbackWeapon;
    }

    /** Attribute IDs that Stage 06.02 requires the node-effect pack to target. */
    public static Set<String> requiredAttributeIds() {
        return REQUIRED_ATTRIBUTE_IDS;
    }
}
