package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Atomic saturation cost and resting-basal benefit for A0142. */
public final class FrugalDigestionPolicy {
    private static final Set<String> METABOLIC = Set.of(
        "A0115", "A0117", "A0119", "A0121", "A0123", "A0125",
        "A0127", "A0129", "A0131", "A0133", "A0135", "A0137");

    private FrugalDigestionPolicy() {}

    public static Result evaluate(
        FrozenSurvivalPerkRanks ranks,
        boolean saturationHookPresent,
        boolean basalMetabolismHookPresent
    ) {
        Objects.requireNonNull(ranks);
        long distinct = METABOLIC.stream().filter(code -> ranks.rank(code) > 0).count();
        boolean active = ranks.rank("A0142") > 0 && distinct >= 2
            && saturationHookPresent && basalMetabolismHookPresent;
        return new Result(active);
    }

    public record FoodRestoration(
        double hunger,
        double saturation,
        Map<String, Double> nutrients,
        double water,
        double toxicity
    ) {
        public FoodRestoration {
            requireNonNegative(hunger, "hunger");
            requireNonNegative(saturation, "saturation");
            requireNonNegative(water, "water");
            requireNonNegative(toxicity, "toxicity");
            nutrients = Map.copyOf(nutrients);
            nutrients.forEach((id, value) -> {
                if (id.isBlank()) throw new IllegalArgumentException("nutrient id must not be blank");
                requireNonNegative(value, "nutrient");
            });
        }
    }

    public record Result(boolean active) {
        public FoodRestoration adjustFood(FoodRestoration nativeRestoration) {
            Objects.requireNonNull(nativeRestoration);
            if (!active) return nativeRestoration;
            return new FoodRestoration(
                nativeRestoration.hunger(), nativeRestoration.saturation() * 0.92D,
                nativeRestoration.nutrients(), nativeRestoration.water(), nativeRestoration.toxicity());
        }

        public double basalMultiplier(boolean physiologicalRest) {
            return active && physiologicalRest ? 0.85D : 1.0D;
        }
    }

    private static void requireNonNegative(double value, String field) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(field + " must be finite and non-negative");
        }
    }
}
