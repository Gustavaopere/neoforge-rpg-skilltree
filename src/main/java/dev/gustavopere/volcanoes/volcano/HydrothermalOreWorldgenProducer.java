package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Prepares and applies sparse hydrothermal ore replacements.
 *
 * <p>Preparation is fail-closed: an exact Stage 01 deposit becomes physically admissible only when
 * the entire bounded target can be satisfied by eligible natural hosts. The pure preparation layer
 * emits logical ore variants; registry-backed block states are materialized only at the level
 * boundary.</p>
 */
public final class HydrothermalOreWorldgenProducer {
    private static final int WORLDGEN_UPDATE_FLAGS = 2;

    private final HydrothermalOrePlacementPlanner planner;

    public HydrothermalOreWorldgenProducer() {
        this(new HydrothermalOrePlacementPlanner());
    }

    HydrothermalOreWorldgenProducer(HydrothermalOrePlacementPlanner planner) {
        this.planner = Objects.requireNonNull(planner, "planner");
    }

    Optional<PreparedPlacement> prepare(
            GeologicalDeposit deposit,
            ChunkPos ownerChunk,
            Function<BlockPos, HostRock> hostLookup
    ) {
        Objects.requireNonNull(deposit, "deposit");
        Objects.requireNonNull(ownerChunk, "ownerChunk");
        Objects.requireNonNull(hostLookup, "hostLookup");

        var plan = planner.plan(deposit, ownerChunk);
        if (plan.isEmpty()) {
            return Optional.empty();
        }

        HydrothermalOrePlacementPlanner.Plan physicalPlan = plan.orElseThrow();
        List<Replacement> replacements = new ArrayList<>(physicalPlan.targetBlocks());
        for (BlockPos candidate : physicalPlan.candidates()) {
            HostRock host = Objects.requireNonNullElse(hostLookup.apply(candidate), HostRock.NONE);
            if (host == HostRock.NONE) {
                continue;
            }
            replacements.add(new Replacement(
                    candidate.immutable(),
                    host,
                    variantFor(physicalPlan.resourceTag(), host)));
            if (replacements.size() == physicalPlan.targetBlocks()) {
                break;
            }
        }

        if (replacements.size() != physicalPlan.targetBlocks()) {
            return Optional.empty();
        }
        return Optional.of(new PreparedPlacement(
                deposit.persistenceId(),
                physicalPlan.resourceTag(),
                physicalPlan.targetBlocks(),
                replacements));
    }

    Optional<PreparedPlacement> prepare(LevelAccessor level, ChunkPos ownerChunk, GeologicalDeposit deposit) {
        Objects.requireNonNull(level, "level");
        return prepare(deposit, ownerChunk, position -> classifyHost(level.getBlockState(position)));
    }

    boolean apply(LevelAccessor level, PreparedPlacement prepared) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(prepared, "prepared");

        List<BlockState> originalStates = new ArrayList<>(prepared.replacements().size());
        for (Replacement replacement : prepared.replacements()) {
            BlockState current = level.getBlockState(replacement.position());
            if (classifyHost(current) != replacement.expectedHost()) {
                return false;
            }
            originalStates.add(current);
        }

        int applied = 0;
        for (Replacement replacement : prepared.replacements()) {
            BlockState target = materialize(replacement.variant());
            if (!level.setBlock(replacement.position(), target, WORLDGEN_UPDATE_FLAGS)
                    || !level.getBlockState(replacement.position()).is(target.getBlock())) {
                rollback(level, prepared.replacements(), originalStates, applied + 1);
                return false;
            }
            applied++;
        }
        return true;
    }

    private static void rollback(
            LevelAccessor level,
            List<Replacement> replacements,
            List<BlockState> originalStates,
            int attempted
    ) {
        int rollbackCount = Math.min(attempted, replacements.size());
        for (int index = rollbackCount - 1; index >= 0; index--) {
            level.setBlock(replacements.get(index).position(), originalStates.get(index), WORLDGEN_UPDATE_FLAGS);
        }
    }

    private static HostRock classifyHost(BlockState state) {
        Objects.requireNonNull(state, "state");
        if (state.is(BlockTags.STONE_ORE_REPLACEABLES)) {
            return HostRock.STONE;
        }
        if (state.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)) {
            return HostRock.DEEPSLATE;
        }
        return HostRock.NONE;
    }

    private static BlockState materialize(OreVariant variant) {
        return switch (Objects.requireNonNull(variant, "variant")) {
            case COPPER -> Blocks.COPPER_ORE.defaultBlockState();
            case DEEPSLATE_COPPER -> Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState();
            case IRON -> Blocks.IRON_ORE.defaultBlockState();
            case DEEPSLATE_IRON -> Blocks.DEEPSLATE_IRON_ORE.defaultBlockState();
            case GOLD -> Blocks.GOLD_ORE.defaultBlockState();
            case DEEPSLATE_GOLD -> Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState();
        };
    }

    static OreVariant variantFor(ResourceLocation resourceTag, HostRock host) {
        Objects.requireNonNull(resourceTag, "resourceTag");
        Objects.requireNonNull(host, "host");
        if (host == HostRock.NONE) {
            throw new IllegalArgumentException("NONE is not a replaceable hydrothermal ore host");
        }
        boolean deepslate = host == HostRock.DEEPSLATE;
        if (resourceTag.equals(GeologyResourceTags.COPPER_ORES.location())) {
            return deepslate ? OreVariant.DEEPSLATE_COPPER : OreVariant.COPPER;
        }
        if (resourceTag.equals(GeologyResourceTags.IRON_ORES.location())) {
            return deepslate ? OreVariant.DEEPSLATE_IRON : OreVariant.IRON;
        }
        if (resourceTag.equals(GeologyResourceTags.GOLD_ORES.location())) {
            return deepslate ? OreVariant.DEEPSLATE_GOLD : OreVariant.GOLD;
        }
        throw new IllegalArgumentException("unsupported physical hydrothermal resource tag: " + resourceTag);
    }

    enum HostRock {
        STONE,
        DEEPSLATE,
        NONE
    }

    enum OreVariant {
        COPPER,
        DEEPSLATE_COPPER,
        IRON,
        DEEPSLATE_IRON,
        GOLD,
        DEEPSLATE_GOLD
    }

    record PreparedPlacement(
            UUID depositId,
            ResourceLocation resourceTag,
            int targetBlocks,
            List<Replacement> replacements
    ) {
        PreparedPlacement {
            Objects.requireNonNull(depositId, "depositId");
            Objects.requireNonNull(resourceTag, "resourceTag");
            replacements = List.copyOf(Objects.requireNonNull(replacements, "replacements"));
            if (targetBlocks <= 0 || replacements.size() != targetBlocks) {
                throw new IllegalArgumentException("prepared physical placement must fully cover its target");
            }
        }
    }

    record Replacement(BlockPos position, HostRock expectedHost, OreVariant variant) {
        Replacement {
            Objects.requireNonNull(position, "position");
            Objects.requireNonNull(expectedHost, "expectedHost");
            Objects.requireNonNull(variant, "variant");
            if (expectedHost == HostRock.NONE) {
                throw new IllegalArgumentException("replacement requires an eligible host");
            }
        }
    }
}
