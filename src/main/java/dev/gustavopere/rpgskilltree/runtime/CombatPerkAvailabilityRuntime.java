package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSustainVersionContract;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Server-authoritative availability boundary for combat perks with mandatory runtime providers. */
public final class CombatPerkAvailabilityRuntime {
    private CombatPerkAvailabilityRuntime() {}

    public static boolean isAvailable(ResourceLocation nodeId) {
        Objects.requireNonNull(nodeId);
        return CombatPerkNodeBinding.catalogCode(nodeId.toString())
            .map(CombatPerkAvailabilityRuntime::isCatalogCodeAvailable)
            .orElse(true);
    }

    public static boolean isCatalogCodeAvailable(String code) {
        Objects.requireNonNull(code);
        return switch (code) {
            case "A0042", "A0044", "A0047", "A0048", "A0050", "A0052", "A0053", "A0054",
                 "A0067", "A0072", "A0075", "A0077", "A0080", "A0081", "A0084", "A0085",
                 "A0086", "A0087" -> false;
            case "A0083" -> ironsDirectMagicAvailable();
            default -> true;
        };
    }

    public static CombatPerkRanks effectiveRanks(CombatPerkRanks persistedRanks) {
        Objects.requireNonNull(persistedRanks);
        Map<String, Integer> effective = new LinkedHashMap<>();
        persistedRanks.ranks().forEach((code, rank) -> {
            if (isCatalogCodeAvailable(code)) effective.put(code, rank);
        });
        return effective.isEmpty() ? CombatPerkRanks.empty() : CombatPerkRanks.of(effective);
    }

    public static CombatPerkRanks effectiveRanks(PassiveNodeProgress progress) {
        Objects.requireNonNull(progress);
        return effectiveRanks(CombatPerkNodeBinding.ranks(progress));
    }

    public static ProgressionState effectiveAccessState(ProgressionState state) {
        Objects.requireNonNull(state);
        Map<String, Integer> effective = new LinkedHashMap<>(state.passiveNodes().ranks());
        effective.entrySet().removeIf(entry -> CombatPerkNodeBinding.catalogCode(entry.getKey())
            .map(code -> !isCatalogCodeAvailable(code))
            .orElse(false));
        return state.withPassiveNodes(PassiveNodeProgress.of(effective));
    }

    private static boolean ironsDirectMagicAvailable() {
        return OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            && IronsSustainVersionContract.supportsVersion(
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            )
            && IronsSustainVersionContract.runtimeContractPresent();
    }
}
