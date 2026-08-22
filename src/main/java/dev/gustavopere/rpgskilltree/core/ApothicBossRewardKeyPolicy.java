package dev.gustavopere.rpgskilltree.core;

import java.util.regex.Pattern;

/** Builds finite, stable reward identities for randomized Apotheosis/Apothic bosses. */
public final class ApothicBossRewardKeyPolicy {
    private static final Pattern RESOURCE_ID = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    private ApothicBossRewardKeyPolicy() {}

    /**
     * Elites are keyed by the Apotheosis elite registry id captured before Apotheosis rewrites its
     * temporary persistent-data marker. If that id is unavailable, entity type is the bounded fallback.
     */
    public static String elite(String eliteRegistryId, String entityId) {
        if (eliteRegistryId != null && !eliteRegistryId.isBlank()) {
            return "apotheosis:elite/" + pathify(requireResourceId(eliteRegistryId, "eliteRegistryId"));
        }
        return "apotheosis:elite_entity/" + pathify(requireResourceId(entityId, "entityId"));
    }

    /** Invaders expose a finite loot-rarity id, so each rarity can award passive points once. */
    public static String invader(String rarityId) {
        return "apotheosis:invader/" + pathify(requireResourceId(rarityId, "rarityId"));
    }

    private static String requireResourceId(String id, String field) {
        if (id == null || !RESOURCE_ID.matcher(id).matches()) {
            throw new IllegalArgumentException(field + " must be a stable namespaced id");
        }
        return id;
    }

    private static String pathify(String resourceId) {
        return resourceId.replace(':', '/');
    }
}
