package dev.gustavopere.rpgskilltree.itemization.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Canonical persisted Stage 11 decision for an itemized equipment instance.
 * Runtime attribute/effect/UI projections are rebuilt from this state and modifier definitions.
 */
public record ItemizationState(
    ItemizationIdentity identity,
    ItemRank rank,
    ItemPower itemPower,
    GenerationSource generationSource,
    Map<ModifierFamily, List<RolledModifier>> modifiers
) {
    public ItemizationState {
        Objects.requireNonNull(identity, "identity");
        Objects.requireNonNull(rank, "rank");
        Objects.requireNonNull(itemPower, "itemPower");
        Objects.requireNonNull(generationSource, "generationSource");
        modifiers = ItemizationModifiers.immutableValidated(modifiers);
    }

    public List<RolledModifier> modifiers(ModifierFamily family) {
        return modifiers.get(Objects.requireNonNull(family, "family"));
    }
}
