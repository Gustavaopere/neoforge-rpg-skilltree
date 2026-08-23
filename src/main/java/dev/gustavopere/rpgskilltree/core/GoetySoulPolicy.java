package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Provider-native Goety Soul Energy adjustments from unified progression. */
public final class GoetySoulPolicy {
    private static final String OCCULT_ENTRY = "rpgskilltree:occult_000";
    private static final String OCCULT_EFFICIENCY = "rpgskilltree:occult_001";

    private GoetySoulPolicy() {}

    public static int adjustedGain(PassiveNodeProgress nodes, ClassProgressionState classes, int originalGain) {
        Objects.requireNonNull(nodes);
        Objects.requireNonNull(classes);
        if (originalGain < 0) throw new IllegalArgumentException("Soul gain must be non-negative");
        if (originalGain == 0) return 0;

        double bonus = 0.0D;
        if (nodes.learned(OCCULT_ENTRY)) bonus += 0.05D;
        if (nodes.learned(OCCULT_EFFICIENCY)) bonus += 0.05D;
        if (classes.isUnlocked("warlock")) bonus += 0.10D;
        if (classes.isUnlocked("necromancer")) bonus += 0.05D;
        bonus = Math.min(0.25D, bonus);
        return Math.max(originalGain, (int)Math.floor(originalGain * (1.0D + bonus)));
    }

    public static int adjustedSpellCost(
        PassiveNodeProgress nodes,
        ClassProgressionState classes,
        Set<String> spellTags,
        int originalCost
    ) {
        Objects.requireNonNull(nodes);
        Objects.requireNonNull(classes);
        Objects.requireNonNull(spellTags);
        if (originalCost < 0) throw new IllegalArgumentException("Soul cost must be non-negative");
        if (originalCost == 0) return 0;

        double discount = 0.0D;
        if (nodes.learned(OCCULT_ENTRY)) discount += 0.05D;
        if (nodes.learned(OCCULT_EFFICIENCY)) discount += 0.05D;
        if (classes.isUnlocked("warlock")) discount += 0.10D;
        if (classes.isUnlocked("necromancer") && spellTags.contains("summoning")) discount += 0.10D;
        discount = Math.min(0.30D, discount);
        return Math.max(1, (int)Math.ceil(originalCost * (1.0D - discount)));
    }
}
