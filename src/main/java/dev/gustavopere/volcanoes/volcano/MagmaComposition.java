package dev.gustavopere.volcanoes.volcano;

import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

/** Stable coarse magma chemistry used by the lifecycle simulation. */
public record MagmaComposition(double silicaFraction, double volatileRichness) {
    private static final String SILICA = "silica_fraction";
    private static final String VOLATILES = "volatile_richness";

    public MagmaComposition {
        silicaFraction = requireUnit("silicaFraction", silicaFraction);
        volatileRichness = requireUnit("volatileRichness", volatileRichness);
    }

    public static MagmaComposition forType(VolcanoType type) {
        Objects.requireNonNull(type, "type");
        return switch (type) {
            case STRATOVOLCANO -> new MagmaComposition(0.64, 0.62);
            case SHIELD -> new MagmaComposition(0.49, 0.28);
            case FISSURE -> new MagmaComposition(0.47, 0.24);
            case CALDERA -> new MagmaComposition(0.72, 0.78);
        };
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble(SILICA, silicaFraction);
        tag.putDouble(VOLATILES, volatileRichness);
        return tag;
    }

    public static MagmaComposition fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        return new MagmaComposition(tag.getDouble(SILICA), tag.getDouble(VOLATILES));
    }

    private static double requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }
}
