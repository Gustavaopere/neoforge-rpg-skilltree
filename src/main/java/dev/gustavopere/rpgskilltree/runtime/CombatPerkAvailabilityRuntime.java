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
 * <p>Persisted legacy ranks remain stored for recovery/refund, but unavailable ranks are masked
 * from gameplay so a historical allocation cannot bypass a fail-closed provider boundary.</p>
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
            // A0042 requires a canonical eligible_kill anti-abuse receipt. The repository does not
            // currently publish one; Enemy/Player identity alone is not sufficient evidence.
            case "A0042" -> false;

            // No audited provider currently exposes semantic draw/preparation speed for A0044.
            // A0047 depends structurally on A0044, and A0048 depends structurally on A0047.
            case "A0044", "A0047", "A0048" -> false;

            // No audited provider currently exposes semantic reload/preparation speed for A0050.
            // A0052 requires A0050 >= 2, A0053 requires A0052, and A0054 requires A0052+A0053.
            // Availability is therefore propagated transitively instead of allowing ghost ranks.
            case "A0050", "A0052", "A0053", "A0054" -> false;

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
