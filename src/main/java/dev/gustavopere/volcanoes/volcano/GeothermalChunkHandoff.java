package dev.gustavopere.volcanoes.volcano;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

import java.util.Objects;
import java.util.UUID;

/**
 * Durable chunk-local receipt for a geothermal expression whose terrain mutation already succeeded.
 *
 * <p>The receipt contains deterministic worldgen metadata plus an explicit proof bit for physical
 * hydrothermal ore realization. SavedData remains authoritative and is reconstructed later on the
 * server tick; this object exists solely to close the crash window between chunk mutation and that
 * later reconciliation. Legacy receipts decode the proof bit as {@code false}.</p>
 */
public record GeothermalChunkHandoff(
        long worldSeed,
        GeothermalFeaturePlacement placement,
        boolean hydrothermalDepositPhysicallyRealized
) {
    private static final Codec<GeothermalFeatureType> TYPE_CODEC = Codec.STRING.xmap(
            GeothermalFeatureType::valueOf,
            GeothermalFeatureType::name);

    private static final Codec<GeothermalFeaturePlacement> PLACEMENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TYPE_CODEC.fieldOf("type").forGetter(GeothermalFeaturePlacement::type),
            BlockPos.CODEC.fieldOf("center").forGetter(GeothermalFeaturePlacement::center),
            Codec.INT.fieldOf("radius_blocks").forGetter(GeothermalFeaturePlacement::radiusBlocks),
            Codec.DOUBLE.fieldOf("heat_severity").forGetter(GeothermalFeaturePlacement::heatSeverity),
            Codec.DOUBLE.fieldOf("gas_severity").forGetter(GeothermalFeaturePlacement::gasSeverity),
            Codec.DOUBLE.fieldOf("hydrothermal_deposit_chance")
                    .forGetter(GeothermalFeaturePlacement::hydrothermalDepositChance)
    ).apply(instance, GeothermalFeaturePlacement::new));

    public static final Codec<GeothermalChunkHandoff> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("world_seed").forGetter(GeothermalChunkHandoff::worldSeed),
            PLACEMENT_CODEC.fieldOf("placement").forGetter(GeothermalChunkHandoff::placement),
            Codec.BOOL.optionalFieldOf("hydrothermal_deposit_physically_realized", false)
                    .forGetter(GeothermalChunkHandoff::hydrothermalDepositPhysicallyRealized)
    ).apply(instance, GeothermalChunkHandoff::new));

    public GeothermalChunkHandoff {
        placement = Objects.requireNonNull(placement, "placement");
    }

    public GeothermalChunkHandoff(long worldSeed, GeothermalFeaturePlacement placement) {
        this(worldSeed, placement, false);
    }

    public static GeothermalChunkHandoff generated(long worldSeed, GeothermalFeaturePlacement placement) {
        return generated(worldSeed, placement, false);
    }

    public static GeothermalChunkHandoff generated(
            long worldSeed,
            GeothermalFeaturePlacement placement,
            boolean hydrothermalDepositPhysicallyRealized
    ) {
        return new GeothermalChunkHandoff(worldSeed, placement, hydrothermalDepositPhysicallyRealized);
    }

    /** Stable identity shared with the persistent geothermal source reconstructed from this receipt. */
    public UUID sourceId() {
        return GeothermalSource.fromPlacement(worldSeed, placement).persistenceId();
    }
}
