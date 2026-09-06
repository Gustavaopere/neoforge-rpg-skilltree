package dev.gustavopere.volcanoes.performance;

import net.neoforged.neoforge.common.ModConfigSpec;

/** Server-side performance budgets shared by bounded volcanic world mutation paths. */
public final class PerformanceConfig {
    public static final int DEFAULT_ASH_DEPOSITION_BLOCKS_PER_TICK = 64;
    public static final int DEFAULT_LAVA_SPECIALIZATION_BLOCKS_PER_TICK = 32;
    public static final int DEFAULT_ERUPTION_TERRAIN_MUTATIONS_PER_TICK = 8;

    private static ModConfigSpec.IntValue ashDepositionBlocksPerTick;
    private static ModConfigSpec.IntValue lavaSpecializationBlocksPerTick;
    private static ModConfigSpec.IntValue eruptionTerrainMutationsPerTick;

    private PerformanceConfig() {
    }

    public static void define(ModConfigSpec.Builder builder) {
        builder.push("performance");
        builder.push("budgets");
        ashDepositionBlocksPerTick = builder
                .comment("Maximum successful ash block mutations per level per server tick.")
                .defineInRange("ashDepositionBlocksPerTick", DEFAULT_ASH_DEPOSITION_BLOCKS_PER_TICK, 1, 4096);
        lavaSpecializationBlocksPerTick = builder
                .comment("Maximum specialized volcanic lava block work admitted per server tick.")
                .defineInRange("lavaSpecializationBlocksPerTick", DEFAULT_LAVA_SPECIALIZATION_BLOCKS_PER_TICK, 1, 4096);
        eruptionTerrainMutationsPerTick = builder
                .comment("Maximum bomb/pyroclastic terrain mutations per level per server tick.")
                .defineInRange("eruptionTerrainMutationsPerTick", DEFAULT_ERUPTION_TERRAIN_MUTATIONS_PER_TICK, 1, 256);
        builder.pop();
        builder.pop();
    }

    /**
     * Returns configured values after NeoForge config loading. Loader-neutral unit tests may cause
     * the spec to be defined without attaching a loaded config; that pre-bootstrap state must retain
     * the same deterministic defaults instead of making core runtime construction fail.
     */
    public static Budgets current() {
        if (ashDepositionBlocksPerTick == null
                || lavaSpecializationBlocksPerTick == null
                || eruptionTerrainMutationsPerTick == null) {
            return defaults();
        }
        try {
            return budgets(
                    ashDepositionBlocksPerTick.get(),
                    lavaSpecializationBlocksPerTick.get(),
                    eruptionTerrainMutationsPerTick.get());
        } catch (IllegalStateException configNotLoadedYet) {
            return defaults();
        }
    }

    public static Budgets budgets(int ash, int lava, int terrain) {
        return new Budgets(ash, lava, terrain);
    }

    private static Budgets defaults() {
        return budgets(
                DEFAULT_ASH_DEPOSITION_BLOCKS_PER_TICK,
                DEFAULT_LAVA_SPECIALIZATION_BLOCKS_PER_TICK,
                DEFAULT_ERUPTION_TERRAIN_MUTATIONS_PER_TICK);
    }

    public record Budgets(
            int ashDepositionBlocksPerTick,
            int lavaSpecializationBlocksPerTick,
            int eruptionTerrainMutationsPerTick
    ) {
        public Budgets {
            if (ashDepositionBlocksPerTick <= 0
                    || lavaSpecializationBlocksPerTick <= 0
                    || eruptionTerrainMutationsPerTick <= 0) {
                throw new IllegalArgumentException("performance budgets must be positive");
            }
        }
    }
}
