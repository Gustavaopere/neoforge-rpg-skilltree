package dev.gustavopere.rpgskilltree.itemization.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class ItemizationModifiers {
    private static final int MIN_PER_FAMILY = 1;
    private static final int MAX_PER_FAMILY = 5;

    private ItemizationModifiers() {}

    static Map<ModifierFamily, List<RolledModifier>> immutableValidated(
        Map<ModifierFamily, List<RolledModifier>> source
    ) {
        Objects.requireNonNull(source, "modifiers");
        if (source.size() != ModifierFamily.values().length) {
            throw new IllegalArgumentException("exactly PREFIX, SUFFIX and INFIX must be present");
        }

        EnumMap<ModifierFamily, List<RolledModifier>> copy = new EnumMap<>(ModifierFamily.class);
        for (ModifierFamily family : ModifierFamily.values()) {
            List<RolledModifier> values = source.get(family);
            if (values == null) {
                throw new IllegalArgumentException("missing modifier family: " + family);
            }
            List<RolledModifier> immutable = List.copyOf(values);
            if (immutable.size() < MIN_PER_FAMILY || immutable.size() > MAX_PER_FAMILY) {
                throw new IllegalArgumentException(
                    family + " must contain between " + MIN_PER_FAMILY + " and " + MAX_PER_FAMILY + " modifiers"
                );
            }
            copy.put(family, immutable);
        }
        return Collections.unmodifiableMap(copy);
    }
}
