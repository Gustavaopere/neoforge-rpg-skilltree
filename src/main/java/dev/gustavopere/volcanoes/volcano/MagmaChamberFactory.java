package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.UUID;

/** Pure deterministic projection of the initial physical magma chamber for a volcano site. */
public final class MagmaChamberFactory {
    private MagmaChamberFactory() {
    }

    public static MagmaChamber initialFor(VolcanoSite site) {
        Objects.requireNonNull(site, "site");
        MagmaComposition composition = MagmaComposition.forType(site.type());
        double jitter = deterministicUnit(site.persistenceId());
        double baseVolume = switch (site.type()) {
            case STRATOVOLCANO -> 5.0;
            case SHIELD -> 8.0;
            case FISSURE -> 3.5;
            case CALDERA -> 12.0;
        };
        double pressure = switch (site.state()) {
            case EXTINCT -> 25.0;
            case DORMANT -> 75.0 + site.initialVolcanicPotential() * 35.0;
            case ACTIVE -> 185.0 + site.initialVolcanicPotential() * 30.0;
            case ERUPTING -> 285.0 + site.initialVolcanicPotential() * 25.0;
        };
        return new MagmaChamber(
                composition,
                baseVolume * (0.9 + jitter * 0.2),
                pressure + jitter * 8.0,
                0.025 + composition.volatileRichness() * 0.055,
                1_145.0 + composition.silicaFraction() * 110.0 + jitter * 20.0,
                0.04 + site.initialVolcanicPotential() * 0.42 + contextBoost(site.tectonicContext()) * 0.08);
    }

    private static double contextBoost(dev.gustavopere.volcanoes.tectonics.TectonicContext context) {
        return switch (context) {
            case CONVERGENT -> 0.25;
            case HOTSPOT -> 0.35;
            case DIVERGENT -> 0.12;
            case TRANSFORM -> 0.08;
            case INTERIOR -> 0.0;
        };
    }

    private static double deterministicUnit(UUID id) {
        long mixed = id.getMostSignificantBits() ^ Long.rotateLeft(id.getLeastSignificantBits(), 17);
        mixed ^= mixed >>> 33;
        mixed *= 0xff51afd7ed558ccdL;
        mixed ^= mixed >>> 33;
        long mantissa = (mixed >>> 11) & ((1L << 53) - 1L);
        return mantissa * 0x1.0p-53;
    }
}
