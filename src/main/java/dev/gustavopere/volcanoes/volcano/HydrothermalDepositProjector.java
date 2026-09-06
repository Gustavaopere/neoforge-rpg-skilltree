package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import net.minecraft.core.BlockPos;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Pure deterministic projection from a geothermal surface expression to hydrothermal metadata. */
public final class HydrothermalDepositProjector {
    private static final long TYPE_SALT = 0x9E3779B97F4A7C15L;
    private static final long X_SALT = 0xD1B54A32D192ED03L;
    private static final long Z_SALT = 0x94D049BB133111EBL;

    private final GeothermalWorldgenResolver geothermalResolver;

    public HydrothermalDepositProjector() {
        this(GeothermalWorldgenResolver.createDefault(
                GeothermalWorldgenFeature.MAGMA_INFLUENCE_RADIUS_BLOCKS));
    }

    HydrothermalDepositProjector(GeothermalWorldgenResolver geothermalResolver) {
        this.geothermalResolver = Objects.requireNonNull(geothermalResolver, "geothermalResolver");
    }

    public Optional<GeologicalDeposit> project(long worldSeed, GeothermalFeaturePlacement placement) {
        Objects.requireNonNull(placement, "placement");
        double chance = placement.hydrothermalDepositChance();
        if (chance <= 0.0) {
            return Optional.empty();
        }

        BlockPos surface = placement.center();
        long selection = mix64(
                worldSeed
                        ^ ((long) placement.type().ordinal() * TYPE_SALT)
                        ^ ((long) surface.getX() * X_SALT)
                        ^ ((long) surface.getZ() * Z_SALT));
        if (unitDouble(selection) >= chance) {
            return Optional.empty();
        }

        double radius = Math.max(8.0, placement.radiusBlocks() * 4.0);
        int depth = Math.max(6, (int) Math.ceil(radius) + 1);
        BlockPos center = surface.below(depth);
        double richness = clampUnit(
                0.20
                        + placement.heatSeverity() * 0.40
                        + placement.gasSeverity() * 0.30);
        var causalVolcanoType = geothermalResolver.causalVolcanoAt(worldSeed, surface)
                .map(VolcanoSite::type);
        var resourceTag = HydrothermalMineralizationPolicy.resourceFor(causalVolcanoType);

        return Optional.of(new GeologicalDeposit(
                stableId(worldSeed, placement),
                resourceTag,
                center,
                radius,
                richness,
                DepositOrigin.HYDROTHERMAL));
    }

    private static UUID stableId(long worldSeed, GeothermalFeaturePlacement placement) {
        BlockPos center = placement.center();
        String key = "volcanoes:hydrothermal:"
                + worldSeed + ':'
                + placement.type().name() + ':'
                + center.getX() + ':'
                + center.getY() + ':'
                + center.getZ();
        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    }

    private static double unitDouble(long hash) {
        return (hash >>> 11) * 0x1.0p-53;
    }

    private static long mix64(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        value ^= value >>> 33;
        return value;
    }

    private static double clampUnit(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
