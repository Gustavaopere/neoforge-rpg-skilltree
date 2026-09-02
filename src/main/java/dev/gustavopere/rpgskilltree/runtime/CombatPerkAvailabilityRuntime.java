package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSustainVersionContract;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
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
    private static final ResourceLocation EPIC_FIGHT_STUN_ARMOR =
        ResourceLocation.fromNamespaceAndPath("epicfight", "stun_armor");

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
            case "A0067", "A0072", "A0077" -> false;
            case "A0075" -> false;
            case "A0080" -> false;
            case "A0081" -> false;
            case "A0083" -> ironsDirectMagicAvailable();
            case "A0084", "A0085" -> false;
            case "A0086" -> false;
            case "A0087" -> false;
            case "A0093", "A0094", "A0100" -> false;
            case "A0095" -> epicFightStunArmorAvailable();

            // A0106 requires A0095>=3. Provider loss/version drift therefore removes the effective
            // capstone rank instead of allowing a stale dependent node to keep operating.
            case "A0106" -> epicFightStunArmorAvailable();

            // Contracts intentionally remain fail-closed. No generic substitute is allowed for
            // impact->Stamina, A0100's critical decomposition, body encumbrance, or the missing
            // post-Unbreaking/pre-decrement durability seam.
            case "A0107", "A0108", "A0109", "A0110" -> false;

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

    private static boolean epicFightStunArmorAvailable() {
        return OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT)
            && EpicFightVersionContract.supportsVersion(
                OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT)
            )
            && BuiltInRegistries.ATTRIBUTE.getHolder(EPIC_FIGHT_STUN_ARMOR).isPresent();
    }
}
