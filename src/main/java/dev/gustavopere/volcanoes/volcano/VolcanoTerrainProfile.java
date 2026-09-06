package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/**
 * Bounded terrain envelope for one volcano morphology.
 *
 * <p>This class is intentionally world-independent. It describes only the local height delta that
 * a future worldgen feature may apply while generating a new chunk; it never mutates loaded or
 * previously generated terrain by itself.</p>
 */
public final class VolcanoTerrainProfile {
    private final VolcanoType type;
    private final double radiusBlocks;
    private final int maxRiseBlocks;

    private VolcanoTerrainProfile(VolcanoType type, double radiusBlocks, int maxRiseBlocks) {
        this.type = Objects.requireNonNull(type, "type");
        this.radiusBlocks = radiusBlocks;
        this.maxRiseBlocks = maxRiseBlocks;
    }

    public static VolcanoTerrainProfile forType(VolcanoType type) {
        Objects.requireNonNull(type, "type");
        return switch (type) {
            case STRATOVOLCANO -> new VolcanoTerrainProfile(type, 240.0, 88);
            case SHIELD -> new VolcanoTerrainProfile(type, 320.0, 48);
            case FISSURE -> new VolcanoTerrainProfile(type, 240.0, 24);
            case CALDERA -> new VolcanoTerrainProfile(type, 280.0, 64);
        };
    }

    public VolcanoType type() {
        return type;
    }

    public double radiusBlocks() {
        return radiusBlocks;
    }

    public int maxRiseBlocks() {
        return maxRiseBlocks;
    }

    public double heightDelta(double deltaX, double deltaZ) {
        if (!Double.isFinite(deltaX) || !Double.isFinite(deltaZ)) {
            throw new IllegalArgumentException("terrain offsets must be finite");
        }

        double radialDistance = Math.hypot(deltaX, deltaZ);
        if (radialDistance > radiusBlocks) {
            return 0.0;
        }

        return switch (type) {
            case STRATOVOLCANO -> stratovolcano(radialDistance / radiusBlocks);
            case SHIELD -> shield(radialDistance / radiusBlocks);
            case FISSURE -> fissure(deltaX, deltaZ);
            case CALDERA -> caldera(radialDistance / radiusBlocks);
        };
    }

    private double stratovolcano(double normalizedRadius) {
        double taper = Math.max(0.0, 1.0 - normalizedRadius);
        return maxRiseBlocks * Math.pow(taper, 1.55);
    }

    private double shield(double normalizedRadius) {
        double taper = Math.max(0.0, 1.0 - normalizedRadius);
        return maxRiseBlocks * Math.pow(taper, 1.20);
    }

    private double fissure(double deltaX, double deltaZ) {
        double along = deltaX / radiusBlocks;
        double across = deltaZ / (radiusBlocks * 0.30);
        double ellipticalDistance = Math.hypot(along, across);
        if (ellipticalDistance >= 1.0) {
            return 0.0;
        }
        return maxRiseBlocks * Math.pow(1.0 - ellipticalDistance, 1.15);
    }

    private double caldera(double normalizedRadius) {
        double apron = 22.0 * Math.max(0.0, 1.0 - normalizedRadius);
        double rimOffset = (normalizedRadius - 0.58) / 0.16;
        double rim = 50.0 * Math.exp(-(rimOffset * rimOffset));
        double craterOffset = normalizedRadius / 0.22;
        double crater = 28.0 * Math.exp(-(craterOffset * craterOffset));
        return Math.max(-32.0, Math.min(maxRiseBlocks, apron + rim - crater));
    }
}
