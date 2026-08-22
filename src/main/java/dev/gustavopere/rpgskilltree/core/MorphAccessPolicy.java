package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

public final class MorphAccessPolicy {
    private MorphAccessPolicy() {}

    public static boolean canUse(MorphFormDescriptor form, Set<MorphPermission> permissions) {
        if (form.explicitlyBlacklisted() || form.category() == MorphFormCategory.TECHNICAL) return false;
        return switch (form.category()) {
            case NATURAL_LAND -> permissions.contains(MorphPermission.DRUID_LAND);
            case NATURAL_AQUATIC -> permissions.contains(MorphPermission.DRUID_AQUATIC);
            case NATURAL_FLYING -> permissions.contains(MorphPermission.DRUID_FLYING);
            case MAGICAL_NATURAL -> permissions.contains(MorphPermission.DRUID_MAGICAL_NATURAL);
            case HUMANOID -> permissions.contains(MorphPermission.METAMORPH_HUMANOID);
            case MONSTER -> permissions.contains(MorphPermission.METAMORPH_MONSTER);
            case ABERRATION -> permissions.contains(MorphPermission.METAMORPH_ABERRATION);
            case TECHNICAL -> false;
        };
    }
}
