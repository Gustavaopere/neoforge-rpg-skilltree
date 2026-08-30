package dev.gustavopere.rpgskilltree.itemization.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Immutable query/client projection. Constructing it has no persistence or mutation side effects. */
public record ItemizationSnapshot(
    ItemizationIdentity identity,
    ItemRank rank,
    ItemPower itemPower,
    GenerationSource generationSource,
    Map<ModifierFamily, List<RolledModifier>> modifiers
) {
    public ItemizationSnapshot {
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
