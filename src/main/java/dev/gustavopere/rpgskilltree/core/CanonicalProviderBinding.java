package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

/** Provider-neutral mapping from one canonical stat to one concrete provider target. */
public record CanonicalProviderBinding(
    String bindingId,
    CanonicalStatKey canonicalStat,
    String providerTargetId
) {
    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern PATH = Pattern.compile("[a-z0-9/._-]+");

    public CanonicalProviderBinding {
        bindingId = validateNamespacedId(bindingId, "bindingId");
        Objects.requireNonNull(canonicalStat, "canonicalStat");
        providerTargetId = validateNamespacedId(providerTargetId, "providerTargetId");
    }

    public static CanonicalProviderBinding of(
        String bindingId,
        CanonicalStatKey canonicalStat,
        String providerTargetId
    ) {
        return new CanonicalProviderBinding(bindingId, canonicalStat, providerTargetId);
    }

    private static String validateNamespacedId(String value, String label) {
        Objects.requireNonNull(value, label);
        int separator = value.indexOf(':');
        if (separator <= 0 || separator == value.length() - 1 || value.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException(label + " must be a namespaced id: " + value);
        }
        String namespace = value.substring(0, separator);
        String path = value.substring(separator + 1);
        if (!NAMESPACE.matcher(namespace).matches() || !PATH.matcher(path).matches()) {
            throw new IllegalArgumentException("invalid " + label + ": " + value);
        }
        return value;
    }
}
