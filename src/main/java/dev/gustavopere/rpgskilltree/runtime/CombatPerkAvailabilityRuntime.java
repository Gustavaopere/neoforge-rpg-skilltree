package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/**
 * Server-authoritative availability boundary for combat nodes whose approved contract requires
 * a concrete runtime binding before purchase or effect activation.
 *
 * <p>This class deliberately models only availability facts that are already closed in the perk
 * dossiers. It does not invent provider fallbacks. Persisted legacy ranks are left untouched so
 * they remain recoverable/refundable, but unavailable ranks are masked to zero for gameplay.</p>
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
}
