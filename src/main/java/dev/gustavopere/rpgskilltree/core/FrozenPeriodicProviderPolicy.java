package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Exact A0085 provider boundary; school/category names never imply periodic damage. */
public final class FrozenPeriodicProviderPolicy {
    private static final String IRONS_SOURCE =
        "io.redspace.ironsspellbooks.damage.SpellDamageSource";
    private static final String IRONS_RAY =
        "io.redspace.ironsspellbooks.spells.blood.RayOfSiphoningSpell";
    private static final String GOETY_ACID_POOL =
        "com.Polarice3.Goety.common.entities.projectiles.AcidPool";

    private FrozenPeriodicProviderPolicy() {}

    public static Classification classify(
        String sourceClass,
        String directEntityClass,
        String spellClass,
        boolean configuredPeriodicTag,
        boolean persistentOrigin
    ) {
        Objects.requireNonNull(sourceClass);
        Objects.requireNonNull(directEntityClass);
        Objects.requireNonNull(spellClass);
        if (!persistentOrigin) return Classification.NONE;
        if (IRONS_SOURCE.equals(sourceClass) && IRONS_RAY.equals(spellClass)) {
            return Classification.IRONS_RAY_OF_SIPHONING;
        }
        if (GOETY_ACID_POOL.equals(directEntityClass)) {
            return Classification.GOETY_ACID_POOL;
        }
        return configuredPeriodicTag ? Classification.CONFIGURED_TAG : Classification.NONE;
    }

    public enum Classification {
        NONE(""),
        IRONS_RAY_OF_SIPHONING("irons_ray_of_siphoning"),
        GOETY_ACID_POOL("goety_acid_pool"),
        CONFIGURED_TAG("configured_periodic_tag");

        private final String providerId;

        Classification(String providerId) { this.providerId = providerId; }

        public String providerId() { return providerId; }
    }
}
