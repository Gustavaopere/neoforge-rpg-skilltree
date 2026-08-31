package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/** Persistent identity and adapter-facing state for one generated geothermal surface expression. */
public record GeothermalSource(
        UUID persistenceId,
        GeothermalFeatureType type,
        BlockPos center,
        int radiusBlocks,
        double heatSeverity,
        double gasSeverity
) {
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String RADIUS = "radius";
    private static final String HEAT = "heat";
    private static final String GAS = "gas";

    public GeothermalSource {
        persistenceId = Objects.requireNonNull(persistenceId, "persistenceId");
        type = Objects.requireNonNull(type, "type");
        center = Objects.requireNonNull(center, "center").immutable();
        if (radiusBlocks <= 0) {
            throw new IllegalArgumentException("radiusBlocks must be positive");
        }
        requireUnit(heatSeverity, "heatSeverity");
        requireUnit(gasSeverity, "gasSeverity");
    }

    public static GeothermalSource fromPlacement(long worldSeed, GeothermalFeaturePlacement placement) {
        Objects.requireNonNull(placement, "placement");
        BlockPos center = placement.center();
        String key = "volcanoes:geothermal-source:"
                + worldSeed + ':'
                + placement.type().name() + ':'
                + center.getX() + ':'
                + center.getY() + ':'
                + center.getZ();
        UUID id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
        return new GeothermalSource(
                id,
                placement.type(),
                center,
                placement.radiusBlocks(),
                placement.heatSeverity(),
                placement.gasSeverity());
    }

    public VolcanicHeatSource toHeatSource() {
        return new VolcanicHeatSource(
                persistenceId,
                VolcanicHeatSource.Kind.GEOTHERMAL,
                center,
                radiusBlocks,
                heatSeverity,
                Long.MAX_VALUE);
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID(ID, persistenceId);
        tag.putString(TYPE, type.name());
        tag.putInt(X, center.getX());
        tag.putInt(Y, center.getY());
        tag.putInt(Z, center.getZ());
        tag.putInt(RADIUS, radiusBlocks);
        tag.putDouble(HEAT, heatSeverity);
        tag.putDouble(GAS, gasSeverity);
        return tag;
    }

    public static GeothermalSource fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        return new GeothermalSource(
                tag.getUUID(ID),
                GeothermalFeatureType.valueOf(tag.getString(TYPE)),
                new BlockPos(tag.getInt(X), tag.getInt(Y), tag.getInt(Z)),
                tag.getInt(RADIUS),
                tag.getDouble(HEAT),
                tag.getDouble(GAS));
    }

    private static void requireUnit(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
    }
}
