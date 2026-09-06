package dev.gustavopere.volcanoes.tectonics;

@FunctionalInterface
public interface TectonicService {
    TectonicSample sample(long worldSeed, double x, double z);

    static TectonicService fallback() {
        return (worldSeed, x, z) -> {
            long cellX = (long) Math.floor(x / 4096.0);
            long cellZ = (long) Math.floor(z / 4096.0);
            long plateId = mix64(worldSeed ^ (cellX * 0x9E3779B97F4A7C15L) ^ (cellZ * 0xC2B2AE3D27D4EB4FL));
            return new TectonicSample(
                    plateId,
                    plateId,
                    TectonicContext.INTERIOR,
                    0.0,
                    0.0,
                    4096.0,
                    0.0,
                    0.0);
        };
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
