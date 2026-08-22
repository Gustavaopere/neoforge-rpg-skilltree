package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

public final class BossRewardKeyPolicy {
    private static final Pattern NAMESPACED_ID = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    private BossRewardKeyPolicy() {}

    public static String resolve(BossIdentity identity) {
        Objects.requireNonNull(identity);
        String group = identity.boundedRewardGroup();
        return group == null || group.isBlank() ? identity.entityId() : group;
    }

    static boolean isNamespacedId(String value) {
        return value != null && NAMESPACED_ID.matcher(value).matches();
    }
}
