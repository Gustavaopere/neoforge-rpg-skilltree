package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityRewardScalingResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Converts the canonical decimal reward result into Minecraft's integer experience boundary. */
public final class EntityRewardExperienceRuntime {
    private static final BigDecimal MAX_EXPERIENCE = BigDecimal.valueOf(Integer.MAX_VALUE);

    private EntityRewardExperienceRuntime() {}

    /**
     * Scales an already-resolved current XP amount.
     *
     * <p>XP is non-negative and integer-valued in Minecraft. Fractional results round downward so the
     * adapter never grants more than the configured canonical multiplier, and overflow clamps at the
     * event's {@code int} boundary instead of wrapping.</p>
     */
    public static int scaleExperience(int currentExperience, EntityRewardScalingResult scaling) {
        if (currentExperience < 0) {
            throw new IllegalArgumentException("currentExperience must be non-negative");
        }
        Objects.requireNonNull(scaling, "scaling");

        BigDecimal scaled = scaling.scale(BigDecimal.valueOf(currentExperience));
        BigDecimal integral = scaled.setScale(0, RoundingMode.DOWN);
        if (integral.compareTo(MAX_EXPERIENCE) >= 0) {
            return Integer.MAX_VALUE;
        }
        return integral.intValueExact();
    }
}
