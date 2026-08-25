package dev.gustavopere.rpgskilltree.core;

/** Frozen TFC five-nutrient quality resolver for A0143. */
public final class NutritionQualityResolver {
    public Resolution resolve(TfcNutrients nutrients) {
        double quality = Math.min(nutrients.fruit(), Math.min(nutrients.vegetables(),
            Math.min(nutrients.protein(), Math.min(nutrients.grain(), nutrients.dairy()))));
        Tier tier = quality >= 0.80D ? Tier.EXCELLENT
            : quality >= 0.60D ? Tier.GOOD
            : quality >= 0.40D ? Tier.ADEQUATE : Tier.NONE;
        return new Resolution(quality, tier, tier.multiplier);
    }

    public enum Tier {
        NONE(1.0D), ADEQUATE(1.05D), GOOD(1.10D), EXCELLENT(1.15D);
        private final double multiplier;
        Tier(double multiplier) { this.multiplier = multiplier; }
    }

    public record TfcNutrients(double fruit, double vegetables, double protein, double grain, double dairy) {
        public TfcNutrients {
            requireUnit(fruit); requireUnit(vegetables); requireUnit(protein); requireUnit(grain); requireUnit(dairy);
        }
    }

    public record Resolution(double quality, Tier tier, double multiplier) {
        public double applyNaturalRecovery(double nativePositiveRate, boolean componentHookPresent) {
            if (!Double.isFinite(nativePositiveRate) || nativePositiveRate < 0.0D) {
                throw new IllegalArgumentException("nativePositiveRate must be non-negative");
            }
            return componentHookPresent && nativePositiveRate > 0.0D
                ? nativePositiveRate * multiplier : nativePositiveRate;
        }
    }

    private static void requireUnit(double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException("nutrient must be in [0,1]");
        }
    }
}
