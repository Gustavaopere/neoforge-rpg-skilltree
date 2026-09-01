package dev.gustavopere.rpgskilltree.runtime.compat;

import java.util.Arrays;
import java.util.stream.Collectors;
import net.neoforged.fml.ModList;

/**
 * Single NeoForge boundary for optional-provider presence and version queries.
 *
 * <p>This class intentionally contains no provider API types. Callers may use
 * the stable Provider identity before touching classes under a provider-specific
 * compat package.</p>
 */
public final class OptionalIntegrations {
    public enum Provider {
        IRONS_SPELLBOOKS("irons_spellbooks"),
        ARS_NOUVEAU("ars_nouveau"),
        EPIC_FIGHT("epicfight"),
        COLD_SWEAT("cold_sweat"),
        GOETY("goety"),
        MALUM("malum"),
        EIDOLON("eidolon"),
        IDENTITY2("identity2"),
        CREATE("create"),
        SABLE("sable");

        private final String modId;

        Provider(String modId) {
            this.modId = modId;
        }

        public String modId() {
            return modId;
        }
    }

    private OptionalIntegrations() {}

    public static boolean isLoaded(Provider provider) {
        if (provider == null) return false;
        return ModList.get().isLoaded(provider.modId());
    }

    /** Returns the provider version, or {@code absent} when the provider is not loaded. */
    public static String version(Provider provider) {
        if (provider == null || !isLoaded(provider)) return "absent";
        return ModList.get()
            .getModContainerById(provider.modId())
            .map(container -> container.getModInfo().getVersion().toString())
            .orElse("unknown");
    }

    /**
     * Stable, bounded bootstrap diagnostic used by dedicated-server smoke tests.
     * Provider enum order is deliberate so logs and regression evidence are deterministic.
     */
    public static String summary() {
        return Arrays.stream(Provider.values())
            .map(provider -> provider.modId() + "=" + version(provider))
            .collect(Collectors.joining(","));
    }
}
