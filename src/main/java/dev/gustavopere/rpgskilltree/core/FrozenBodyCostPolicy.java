package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

/** Frozen rank-to-causal-action mapping for the A0115-A0138 body-cost families. */
public final class FrozenBodyCostPolicy {
    private FrozenBodyCostPolicy() {}

    public static List<BodyCostResolver.Saving> savings(
        FrozenSurvivalPerkRanks ranks,
        BodyCostResolver.Channel channel,
        BodyCostResolver.Cause cause
    ) {
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(channel);
        Objects.requireNonNull(cause);
        String code = code(channel, cause);
        if (code == null) return List.of();
        int rank = ranks.rank(code);
        return rank <= 0 ? List.of() : List.of(new BodyCostResolver.Saving(code, 0.03D * rank));
    }

    private static String code(BodyCostResolver.Channel channel, BodyCostResolver.Cause cause) {
        return switch (cause) {
            case SPRINT -> channel == BodyCostResolver.Channel.METABOLIC ? "A0115" : "A0116";
            case JUMP -> channel == BodyCostResolver.Channel.METABOLIC ? "A0117" : "A0118";
            case SWIM -> channel == BodyCostResolver.Channel.METABOLIC ? "A0119" : "A0120";
            case CLIMB -> channel == BodyCostResolver.Channel.METABOLIC ? "A0121" : "A0122";
            case MINE -> channel == BodyCostResolver.Channel.METABOLIC ? "A0123" : "A0124";
            case FORESTRY -> channel == BodyCostResolver.Channel.METABOLIC ? "A0125" : "A0126";
            case MELEE -> channel == BodyCostResolver.Channel.METABOLIC ? "A0127" : "A0128";
            case RANGED -> channel == BodyCostResolver.Channel.METABOLIC ? "A0129" : "A0130";
            case CAST -> channel == BodyCostResolver.Channel.METABOLIC ? "A0131" : "A0132";
            case CARRY -> channel == BodyCostResolver.Channel.METABOLIC ? "A0133" : "A0134";
            case WORK_HOT, WORK_COLD, THERMAL_HOT, THERMAL_COLD, BASAL, UNATTRIBUTED -> null;
        };
    }
}
