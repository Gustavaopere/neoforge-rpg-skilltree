package dev.gustavopere.volcanoes.volcano;

/** Four-neighbor surface-height sample supplied by a world adapter without forcing chunk loads. */
public record PyroclasticSlopeSample(
        double westY,
        double eastY,
        double northY,
        double southY
) {
    public PyroclasticSlopeSample {
        requireFinite("westY", westY);
        requireFinite("eastY", eastY);
        requireFinite("northY", northY);
        requireFinite("southY", southY);
    }

    private static void requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
    }
}
