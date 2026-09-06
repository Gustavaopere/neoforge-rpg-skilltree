package dev.gustavopere.rpgskilltree.runtime.compat;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Stable internal identifier for a semantic action exposed by an integration adapter.
 *
 * <p>The syntax intentionally mirrors Minecraft namespaced identifiers while remaining
 * independent from provider APIs. IDs are immutable and canonical so the same action can
 * be deduplicated across optional integrations.</p>
 */
public record SemanticActionId(String value) {
    private static final int MAX_LENGTH = 255;
    private static final Pattern CANONICAL = Pattern.compile("[a-z0-9_.-]+:[a-z0-9/._-]+");

    public SemanticActionId {
        Objects.requireNonNull(value, "value");
        if (value.isEmpty() || value.length() > MAX_LENGTH || !CANONICAL.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid semantic action id: " + value);
        }
    }

    public static SemanticActionId of(String value) {
        return new SemanticActionId(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
