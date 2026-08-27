package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

/** One auditable signed contribution to a territory's native threat level. */
public record NativeAreaLevelContribution(String sourceId, long delta) {
    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern PATH = Pattern.compile("[a-z0-9/._-]+");

    public NativeAreaLevelContribution {
        Objects.requireNonNull(sourceId, "sourceId");
        int separator = sourceId.indexOf(':');
        if (separator <= 0 || separator == sourceId.length() - 1
            || sourceId.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException("native area contribution source must be namespaced: " + sourceId);
        }
        String namespace = sourceId.substring(0, separator);
        String path = sourceId.substring(separator + 1);
        if (!NAMESPACE.matcher(namespace).matches() || !PATH.matcher(path).matches()) {
            throw new IllegalArgumentException("invalid native area contribution source: " + sourceId);
        }
    }

    public static NativeAreaLevelContribution of(String sourceId, long delta) {
        return new NativeAreaLevelContribution(sourceId, delta);
    }
}
