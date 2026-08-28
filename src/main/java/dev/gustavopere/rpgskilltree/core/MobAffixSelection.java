package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/** Canonical immutable set-like affix selection represented in stable ID order. */
public final class MobAffixSelection {
    private final List<MobAffixKey> affixes;

    public MobAffixSelection(List<MobAffixKey> affixes) {
        Objects.requireNonNull(affixes, "affixes");
        ArrayList<MobAffixKey> copy = new ArrayList<>(affixes.size());
        HashSet<MobAffixKey> seen = new HashSet<>();
        for (MobAffixKey affix : affixes) {
            MobAffixKey key = Objects.requireNonNull(affix, "affix");
            if (!seen.add(key)) {
                throw new IllegalArgumentException("duplicate mob affix: " + key.serializedId());
            }
            copy.add(key);
        }
        copy.sort(Comparator.comparing(MobAffixKey::serializedId));
        this.affixes = List.copyOf(copy);
    }

    public static MobAffixSelection empty() {
        return new MobAffixSelection(List.of());
    }

    public List<MobAffixKey> affixes() {
        return affixes;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof MobAffixSelection selection
            && affixes.equals(selection.affixes);
    }

    @Override
    public int hashCode() {
        return affixes.hashCode();
    }

    @Override
    public String toString() {
        return "MobAffixSelection" + affixes;
    }
}
