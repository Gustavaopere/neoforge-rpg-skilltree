package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;

/** Whitelisted active-effect state safe for Compendium inspection output. */
public record EntityEffectSnapshot(
    String effectId,
    int amplifier,
    long durationTicks,
    boolean ambient,
    boolean visible
) {
    public EntityEffectSnapshot {
        CompendiumEntryId.of(CompendiumEntryKind.ENTITY, effectId);
        if (amplifier < 0) throw new IllegalArgumentException("amplifier must be non-negative");
        if (durationTicks < 0L) throw new IllegalArgumentException("durationTicks must be non-negative");
    }
}
