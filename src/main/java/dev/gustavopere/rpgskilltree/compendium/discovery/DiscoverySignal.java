package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.Objects;
import java.util.Optional;

/** Trusted server-derived discovery observation passed into the pure discovery runtime. */
public record DiscoverySignal(
    CompendiumEntryId entryId,
    DiscoveryTriggerType trigger,
    long gameTime,
    Optional<DiscoveryOrigin> origin,
    Optional<String> variantId
) {
    public DiscoverySignal {
        Objects.requireNonNull(entryId, "entryId");
        Objects.requireNonNull(trigger, "trigger");
        if (gameTime < 0L) throw new IllegalArgumentException("gameTime must not be negative");
        origin = origin == null ? Optional.empty() : origin;
        variantId = normalizeOptionalText(variantId);
    }

    private static Optional<String> normalizeOptionalText(Optional<String> value) {
        if (value == null || value.isEmpty()) return Optional.empty();
        String normalized = value.orElseThrow().trim();
        if (normalized.isEmpty()) throw new IllegalArgumentException("variantId must not be blank");
        return Optional.of(normalized);
    }
}
