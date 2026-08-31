package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.UUID;

/** Immutable metadata describing a discoverable geological resource concentration. */
public record GeologicalDeposit(
        UUID persistenceId,
        ResourceLocation resourceTag,
        BlockPos center,
        double radius,
        double richness,
        DepositOrigin origin
) {
    private static final String ID = "id";
    private static final String RESOURCE_TAG = "resource_tag";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String RADIUS = "radius";
    private static final String RICHNESS = "richness";
    private static final String ORIGIN = "origin";

    public GeologicalDeposit {
        Objects.requireNonNull(persistenceId, "persistenceId");
        Objects.requireNonNull(resourceTag, "resourceTag");
        Objects.requireNonNull(center, "center");
        Objects.requireNonNull(origin, "origin");
        if (!Double.isFinite(radius) || radius <= 0.0) {
            throw new IllegalArgumentException("radius must be finite and positive");
        }
        if (!Double.isFinite(richness) || richness < 0.0 || richness > 1.0) {
            throw new IllegalArgumentException("richness must be finite and within [0, 1]");
        }
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString(ID, persistenceId.toString());
        tag.putString(RESOURCE_TAG, resourceTag.toString());
        tag.putInt(X, center.getX());
        tag.putInt(Y, center.getY());
        tag.putInt(Z, center.getZ());
        tag.putDouble(RADIUS, radius);
        tag.putDouble(RICHNESS, richness);
        tag.putString(ORIGIN, origin.name());
        return tag;
    }

    public static GeologicalDeposit fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        try {
            return new GeologicalDeposit(
                    UUID.fromString(tag.getString(ID)),
                    ResourceLocation.parse(tag.getString(RESOURCE_TAG)),
                    new BlockPos(tag.getInt(X), tag.getInt(Y), tag.getInt(Z)),
                    tag.getDouble(RADIUS),
                    tag.getDouble(RICHNESS),
                    DepositOrigin.valueOf(tag.getString(ORIGIN)));
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException("Invalid geological deposit NBT", exception);
        }
    }
}
