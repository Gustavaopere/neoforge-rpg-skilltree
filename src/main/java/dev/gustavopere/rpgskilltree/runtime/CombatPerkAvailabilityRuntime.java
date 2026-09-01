package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSustainVersionContract;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/**
 * Server-authoritative availability boundary for combat nodes whose approved contract requires
 * a concrete runtime binding before purchase or effect activation.
 *
 * <p>This class deliberately models only availability facts already closed in the perk dossiers.
 * It does not invent provider fallbacks. Persisted legacy ranks are left untouched so they remain
 * recoverable/refundable, but unavailable ranks are masked to zero for gameplay.</p>
 */
public final class CombatPerkAvailabilityRuntime {
    private CombatPerkAvailabilityRuntime() {}

    public static boolean isAvailable(ResourceLocation nodeId) {
        Objects.requireNonNull(nodeId, "nodeId");
        return CombatPerkNodeBinding.catalogCode(nodeId.toString())
            .map(CombatPerkAvailabilityRuntime::isCatalogCodeAvailable)
            .orElse(true);
    }

    public static boolean isCatalogCodeAvailable(String code) {
        Objects.requireNonNull(code, "code");
        return switch (code) {
            // A0067 has no provider-native offensive attack-window binding yet. A0072 and A0077
            // inherit that structural unavailability from their required predecessor.
            case "A0067", "A0072", "A0077" -> false;

            // A0075 is all-or-nothing and still lacks the required causal Cold Sweat metabolic
            // contribution binding. Mathematical support alone is not availability.
            case "A0075" -> false;

            // A0080 has no server-authoritative producer proving an actually avoided hostile hit.
            case "A0080" -> false;

            // A0081 inherits A0075 availability. The recovery mathematics being present does not
            // make the dependent node purchasable while A0075 is structurally unavailable.
            case "A0081" -> false;

            // A0083 becomes available only for the audited Iron's 3.16.3 SpellDamageSource
            // contract. The runtime bridge itself still fails closed per root when native
            // lifesteal is present because no exact final native-heal receipt is exposed.
            case "A0083" -> ironsDirectMagicAvailable();

            // A0084 still lacks an approved/versioned school->element mapping in runtime; A0085
            // lacks owner+application+pulse receipts. Formula-only support is intentionally not
            // promoted to availability.
            case "A0084", "A0085" -> false;

            // A0086 requires A0083=3 and A0085>=2 legitimately available. Since A0085 is closed,
            // the universal keystone must not synthesize a classifier or bypass its predecessors.
            case "A0086" -> false;

            // A0087 inherits unavailable A0075/A0081 and still has no canonical all-or-nothing
            // Cold Sweat metabolic heat + vanilla exhaustion BodyProvider/general-heal boundary.
            case "A0087" -> false;

            default -> true;
        };
    }

    /** Returns an effective rank snapshot where structurally unavailable nodes contribute zero. */
    public static CombatPerkRanks effectiveRanks(CombatPerkRanks persistedRanks) {
        Objects.requireNonNull(persistedRanks, "persistedRanks");
        Map<String, Integer> effective = new LinkedHashMap<>();
        persistedRanks.ranks().forEach((code, rank) -> {
            if (isCatalogCodeAvailable(code)) effective.put(code, rank);
        });
        return effective.isEmpty() ? CombatPerkRanks.empty() : CombatPerkRanks.of(effective);
    }

    private static boolean ironsDirectMagicAvailable() {
        return OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            && IronsSustainVersionContract.supportsVersion(
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            )
            && IronsSustainVersionContract.runtimeContractPresent();
    }
}
