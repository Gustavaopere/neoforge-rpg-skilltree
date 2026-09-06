package dev.gustavopere.volcanoes.geology;

import java.util.List;

/**
 * Pure seed/coordinate sampler for low-frequency virtual geology.
 *
 * <p>The sampler never reads chunks or biomes. Its large region cells provide a stable geological
 * substrate that later systems may bias with bounded local observations without making terrain
 * scanning authoritative.</p>
 */
public final class DeterministicStrataSampler {
    static final int REGION_SIZE_BLOCKS = 8_192;

    private final int minY;
    private final int maxYExclusive;

    public DeterministicStrataSampler(int minY, int maxYExclusive) {
        if ((long) maxYExclusive - minY < 3L) {
            throw new IllegalArgumentException("Strata sampling requires at least three vertical blocks");
        }
        this.minY = minY;
        this.maxYExclusive = maxYExclusive;
    }

    public GeologyColumn sample(long worldSeed, int x, int z) {
        long regionX = Math.floorDiv(x, REGION_SIZE_BLOCKS);
        long regionZ = Math.floorDiv(z, REGION_SIZE_BLOCKS);
        long seedHash = mix64(worldSeed);
        long regionHash = mix64(
                seedHash
                        ^ regionX * 0x9E3779B97F4A7C15L
                        ^ regionZ * 0xC2B2AE3D27D4EB4FL);

        int height = maxYExclusive - minY;
        int lowerNominal = minY + height / 3;
        int upperNominal = minY + (height * 2) / 3;

        // Coordinate terms deliberately participate directly so adjacent large geological regions
        // cannot all collapse onto one seed-only column template.
        int lowerOffset = (int) Math.floorMod(
                seedHash + regionX * 5L + regionZ * 3L,
                25L) - 12;
        int upperOffset = (int) Math.floorMod(
                Long.rotateLeft(seedHash, 17) + regionX * 7L - regionZ * 5L,
                25L) - 12;

        int lowerEnd = clamp(lowerNominal + lowerOffset, minY + 1, maxYExclusive - 2);
        int upperEnd = clamp(upperNominal + upperOffset, lowerEnd + 1, maxYExclusive - 1);

        String basement = switch ((int) Math.floorMod(regionHash, 3L)) {
            case 0 -> "volcanoes:granite";
            case 1 -> "generic";
            default -> "volcanoes:basalt";
        };
        String middle = switch ((int) Math.floorMod(regionHash >>> 8, 3L)) {
            case 0 -> "generic";
            case 1 -> "volcanoes:tuff";
            default -> "volcanoes:granite";
        };
        String upper = switch ((int) Math.floorMod(regionHash >>> 16, 3L)) {
            case 0 -> "volcanoes:tuff";
            case 1 -> "volcanoes:basalt";
            default -> "generic";
        };

        return new GeologyColumn(
                minY,
                maxYExclusive,
                List.of(
                        new Stratum(minY, lowerEnd, basement),
                        new Stratum(lowerEnd, upperEnd, middle),
                        new Stratum(upperEnd, maxYExclusive, upper)
                ));
    }

    private static int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static long mix64(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        value ^= value >>> 33;
        return value;
    }
}
