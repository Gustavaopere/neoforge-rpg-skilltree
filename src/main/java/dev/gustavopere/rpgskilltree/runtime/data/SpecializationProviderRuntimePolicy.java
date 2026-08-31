package dev.gustavopere.rpgskilltree.runtime.data;

import java.util.Set;

/**
 * Current build capability for specialization providers.
 *
 * <p>This is intentionally separate from mod presence: a loaded mod is not enough to expose a
 * gateway until the RPG runtime has a complete provider adapter for that specialization family.</p>
 */
public final class SpecializationProviderRuntimePolicy {
    private static final Set<String> COMPLETE_ADAPTERS = Set.of(
        "rpgskilltree",
        "irons_spellbooks",
        "ars_nouveau",
        "epicfight"
    );

    private SpecializationProviderRuntimePolicy() {}

    public static boolean hasCompleteAdapter(String providerId) {
        return providerId != null && COMPLETE_ADAPTERS.contains(providerId);
    }
}
