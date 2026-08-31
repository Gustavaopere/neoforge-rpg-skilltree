package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import java.util.Set;

/**
 * Current build capability for specialization providers.
 *
 * <p>This is intentionally separate from mod presence: a loaded mod is not enough to expose a
 * gateway until the RPG runtime has a complete provider adapter for that specialization family.</p>
 */
public final class SpecializationProviderRuntimePolicy {
    private static final Set<String> VERSION_AGNOSTIC_COMPLETE_ADAPTERS = Set.of(
        "rpgskilltree",
        "irons_spellbooks",
        "ars_nouveau"
    );

    private SpecializationProviderRuntimePolicy() {}

    public static boolean hasCompleteAdapter(String providerId, String installedVersion) {
        if (providerId == null) return false;
        if (VERSION_AGNOSTIC_COMPLETE_ADAPTERS.contains(providerId)) return true;
        if (providerId.equals("epicfight")) {
            return EpicFightVersionContract.supportsVersion(installedVersion);
        }
        return false;
    }
}
