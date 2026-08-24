package dev.gustavopere.rpgskilltree.core;

import java.util.Locale;
import java.util.Objects;

/** Native Epic Fight stamina-cost adjustments derived from learned RPG nodes. */
public final class EpicFightStaminaPolicy {
    private static final String MARTIAL_ENTRY = "rpgskilltree:martial_000";
    private static final String MARTIAL_CAPSTONE = "rpgskilltree:martial_036";
    private static final String AGILITY_ENTRY = "rpgskilltree:agility_000";
    private static final String AGILITY_CAPSTONE = "rpgskilltree:agility_036";

    private EpicFightStaminaPolicy() {}

    /**
     * Epic Fight 1.21.1 exposes {@code SkillConsumeEvent} before its resource predicate/consumer.
     * It does not expose a public post-consume receipt, so exact-cost refunds remain unsupported.
     */
    public static ExactCostSupport exactCostSupport() {
        return ExactCostSupport.UNSUPPORTED_PRE_CONSUME_ONLY;
    }

    public static float adjustedCost(PassiveNodeProgress nodes, String skillCategory, float originalCost) {
        Objects.requireNonNull(nodes);
        Objects.requireNonNull(skillCategory);
        if (!Float.isFinite(originalCost) || originalCost < 0.0F) {
            throw new IllegalArgumentException("stamina cost must be finite and non-negative");
        }
        if (originalCost == 0.0F) return 0.0F;

        String category = skillCategory.trim().toUpperCase(Locale.ROOT);
        double discount = 0.0D;

        if (nodes.learned(MARTIAL_ENTRY)) discount += 0.05D;
        discount += Math.min(3, nodes.rank(MARTIAL_CAPSTONE)) * 0.02D;

        if (category.equals("DODGE") || category.equals("MOVER")) {
            if (nodes.learned(AGILITY_ENTRY)) discount += 0.10D;
            discount += Math.min(3, nodes.rank(AGILITY_CAPSTONE)) * 0.03D;
        }

        discount = Math.min(0.35D, discount);
        return (float)(originalCost * (1.0D - discount));
    }

    public enum ExactCostSupport {
        UNSUPPORTED_PRE_CONSUME_ONLY
    }
}
