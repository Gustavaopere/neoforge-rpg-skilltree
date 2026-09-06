package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.performance.PerformanceConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Level-aware execution layer for ash, volcanic bombs and pyroclastic flows.
 *
 * <p>The canonical eruption dispatcher stays server-agnostic. This runtime drains a small bounded
 * bridge on the Overworld tick, never force-loads chunks, and keeps destructive bomb/flow terrain
 * mutation fail-closed until an authoritative protection integration explicitly enables it.</p>
 */
public final class VolcanicHazardWorldRuntime {
    private static final int HAZARD_QUEUE_CAPACITY = 128;
    private static final int MAX_QUEUED_SIGNALS_PER_TICK = 8;
    private static final int MAX_ACTIVE_BOMBS_PER_LEVEL = 64;
    private static final int MAX_ACTIVE_FLOWS_PER_LEVEL = 16;
    private static final int MAX_ENTITY_EXPOSURES_PER_TICK = 32;
    private static final int MAX_STORED_TERRAIN_TOKENS_PER_VOLCANO = 4;
    private static final int MAX_TRAIL_SAMPLES_PER_LEVEL = 96;
    private static final int MAX_TRAIL_SAMPLES_PROCESSED_PER_TICK = 16;
    private static final long TRAIL_SAMPLE_INTERVAL_TICKS = 10L;
    private static final long TRAIL_SAMPLE_LIFETIME_TICKS = 100L;
    private static final long TRAIL_EXPOSURE_INTERVAL_TICKS = 10L;
    private static final long PYROCLASTIC_HEAT_TTL_TICKS = 20L;
    private static final long LAVA_HEAT_TTL_TICKS = 400L;

    private static final AshEmissionIndex ASH_INDEX = new AshEmissionIndex();
    private static final VolcanicHazardQueue QUEUE = new VolcanicHazardQueue(HAZARD_QUEUE_CAPACITY);
    private static final VolcanicHazardSink SINK = new VolcanicHazardSink(ASH_INDEX, QUEUE);
    private static final VolcanicBombPlanner BOMB_PLANNER = new VolcanicBombPlanner();
    private static final Map<ServerLevel, LevelHazards> LEVEL_HAZARDS =
            Collections.synchronizedMap(new WeakHashMap<>());

    private static volatile VolcanicProtectionService protection = VolcanicProtectionService.none();
    private static boolean registered;

    private VolcanicHazardWorldRuntime() {
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }
        VolcanoLifecycleRuntime.registerEruptionSink(SINK);
        registered = true;
    }

    public static AshEmissionIndex ashIndex() {
        return ASH_INDEX;
    }

    public static boolean registerAshEmissionLifecycleSink(AshEmissionLifecycleSink sink) {
        return ASH_INDEX.registerLifecycleSink(sink);
    }

    public static boolean unregisterAshEmissionLifecycleSink(AshEmissionLifecycleSink sink) {
        return ASH_INDEX.unregisterLifecycleSink(sink);
    }

    /** Stage 06 may replace the default with a claim/colony-aware implementation. */
    public static void setProtectionService(VolcanicProtectionService service) {
        protection = Objects.requireNonNull(service, "service");
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level) || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        LevelHazards hazards;
        synchronized (LEVEL_HAZARDS) {
            hazards = LEVEL_HAZARDS.computeIfAbsent(level, ignored -> new LevelHazards());
        }
        long gameTick = level.getGameTime();
        for (VolcanicHazardQueue.HazardWork work : QUEUE.drain(MAX_QUEUED_SIGNALS_PER_TICK)) {
            hazards.accept(level, work, gameTick);
        }
        hazards.tick(level, gameTick);
    }

    private static final class LevelHazards {
        private final List<VolcanicBombState> bombs = new ArrayList<>();
        private final List<PyroclasticFlowState> flows = new ArrayList<>();
        private final PyroclasticTrailBuffer trails = new PyroclasticTrailBuffer(
                MAX_TRAIL_SAMPLES_PER_LEVEL,
                TRAIL_SAMPLE_INTERVAL_TICKS,
                TRAIL_SAMPLE_LIFETIME_TICKS);
        private final VolcanicTerrainCreditLedger terrainCredits = new VolcanicTerrainCreditLedger(
                MAX_STORED_TERRAIN_TOKENS_PER_VOLCANO,
                MAX_STORED_TERRAIN_TOKENS_PER_VOLCANO);

        void accept(ServerLevel level, VolcanicHazardQueue.HazardWork work, long gameTick) {
            EruptionSignal signal = work.signal();
            UUID volcanoId = signal.volcanoId();
            publishLavaHeat(level, signal, gameTick);
            if (signal.phase() == EruptionPhase.DORMANT) {
                bombs.removeIf(bomb -> bomb.volcanoId().equals(volcanoId));
                flows.removeIf(flow -> flow.volcanoId().equals(volcanoId));
                trails.clear(volcanoId);
                terrainCredits.clear(volcanoId);
                VolcanicHeatService.remove(level, PyroclasticHeatSourceProjector.sourceId(volcanoId));
                return;
            }

            VolcanicHazardAllocation allocation = VolcanicHazardAllocation.from(signal, work.workGrant());
            ASH_INDEX.forVolcano(volcanoId).ifPresent(emission -> {
                AshDepositionWorldEffects.apply(
                        level,
                        emission,
                        allocation.ashBlockWork(),
                        gameTick,
                        protection);
                renderPlume(level, emission);
            });

            int bombsBefore = bombs.size();
            if (allocation.bombEntityWork() > 0 && bombsBefore < MAX_ACTIVE_BOMBS_PER_LEVEL) {
                EruptionScheduler.WorkGrant bombGrant = new EruptionScheduler.WorkGrant(
                        0,
                        allocation.bombEntityWork(),
                        0,
                        0,
                        0,
                        0);
                for (VolcanicBombLaunch launch : BOMB_PLANNER.launches(signal, bombGrant, gameTick)) {
                    if (bombs.size() >= MAX_ACTIVE_BOMBS_PER_LEVEL) {
                        break;
                    }
                    bombs.add(VolcanicBombState.fromLaunch(launch));
                }
            }
            if (bombs.size() > bombsBefore) {
                terrainCredits.add(volcanoId, allocation.bombTerrainWork(), 0);
            }

            if (allocation.flowSpawnWork() > 0
                    && flows.size() < MAX_ACTIVE_FLOWS_PER_LEVEL
                    && flows.stream().noneMatch(flow -> flow.volcanoId().equals(volcanoId))) {
                PyroclasticFlowPlanner.seed(signal, allocation.flowSpawnWork(), gameTick)
                        .ifPresent(flow -> {
                            flows.add(flow);
                            terrainCredits.add(volcanoId, 0, allocation.flowTerrainWork());
                        });
            }
        }

        void tick(ServerLevel level, long gameTick) {
            trails.tick();
            int terrainBudget = PerformanceConfig.current().eruptionTerrainMutationsPerTick();
            BombTickResult bombResult = tickBombs(
                    level,
                    MAX_ENTITY_EXPOSURES_PER_TICK,
                    terrainBudget);
            FlowTickResult flowResult = tickFlows(
                    level,
                    gameTick,
                    Math.max(0, MAX_ENTITY_EXPOSURES_PER_TICK - bombResult.entityExposures()),
                    Math.max(0, terrainBudget - bombResult.terrainMutations()));
            tickTrails(
                    level,
                    gameTick,
                    Math.max(0, MAX_ENTITY_EXPOSURES_PER_TICK
                            - bombResult.entityExposures()
                            - flowResult.entityExposures()));
        }

        private BombTickResult tickBombs(ServerLevel level, int exposureBudget, int terrainBudget) {
            int exposures = 0;
            int terrainMutations = 0;
            List<VolcanicBombState> advanced = new ArrayList<>(bombs.size());
            for (VolcanicBombState current : bombs) {
                VolcanicBombState next = VolcanicBombDynamics.step(current);
                if (!next.active()) {
                    continue;
                }
                BlockPos pos = BlockPos.containing(next.position().x, next.position().y, next.position().z);
                if (!level.hasChunkAt(pos)) {
                    continue;
                }
                BlockState state = level.getBlockState(pos);
                if (!state.getCollisionShape(level, pos).isEmpty()) {
                    exposures += impact(level, next.position(), Math.max(0, exposureBudget - exposures));
                    if (terrainMutations < terrainBudget && terrainCredits.trySpendBomb(
                            next.volcanoId(),
                            () -> VolcanicTerrainWorldEffects.applyBombImpact(level, pos, 1, protection) > 0)) {
                        terrainMutations++;
                    }
                    continue;
                }
                level.sendParticles(
                        ParticleTypes.SMOKE,
                        next.position().x,
                        next.position().y,
                        next.position().z,
                        2,
                        0.08,
                        0.08,
                        0.08,
                        0.01);
                advanced.add(next);
            }
            bombs.clear();
            bombs.addAll(advanced);
            return new BombTickResult(exposures, terrainMutations);
        }

        private FlowTickResult tickFlows(ServerLevel level, long gameTick, int exposureBudget, int terrainBudget) {
            int exposures = 0;
            int terrainMutations = 0;
            List<PyroclasticFlowState> advanced = new ArrayList<>(flows.size());
            for (PyroclasticFlowState current : flows) {
                BlockPos center = BlockPos.containing(current.position().x, current.position().y, current.position().z);
                PyroclasticSlopeSample slope = slopeSample(level, center);
                boolean blocked = slope == null;
                PyroclasticFlowState next = PyroclasticFlowDynamics.step(
                        current,
                        blocked ? new PyroclasticSlopeSample(0.0, 0.0, 0.0, 0.0) : slope,
                        blocked);
                if (!next.active()) {
                    continue;
                }
                BlockPos nextColumn = BlockPos.containing(next.position().x, next.position().y, next.position().z);
                if (!level.hasChunkAt(nextColumn)) {
                    continue;
                }
                int surfaceY = level.getHeight(
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        nextColumn.getX(),
                        nextColumn.getZ());
                next = new PyroclasticFlowState(
                        next.volcanoId(),
                        new Vec3(next.position().x, surfaceY + 0.25, next.position().z),
                        next.velocity(),
                        next.radiusBlocks(),
                        next.heatSeverity(),
                        next.particulateSeverity(),
                        next.ageTicks(),
                        next.maxLifetimeTicks());
                advanced.add(next);
                trails.record(next);
                renderFlow(level, next);
                VolcanicHeatService.upsert(
                        level,
                        PyroclasticHeatSourceProjector.fromFlow(next, gameTick, PYROCLASTIC_HEAT_TTL_TICKS));
                if (terrainMutations < terrainBudget) {
                    BlockPos surface = new BlockPos(nextColumn.getX(), surfaceY - 1, nextColumn.getZ());
                    if (terrainCredits.trySpendFlow(
                            next.volcanoId(),
                            () -> VolcanicTerrainWorldEffects.applyPyroclasticSurface(level, surface, 1, protection) > 0)) {
                        terrainMutations++;
                    }
                }
                if (gameTick % 10L == 0L && exposures < exposureBudget) {
                    exposures += expose(level, next, exposureBudget - exposures);
                }
            }
            flows.clear();
            flows.addAll(advanced);
            return new FlowTickResult(exposures, terrainMutations);
        }

        private int tickTrails(ServerLevel level, long gameTick, int exposureBudget) {
            List<PyroclasticTrailState> samples = trails.samples();
            if (samples.isEmpty()) {
                return 0;
            }

            int affected = 0;
            int processed = 0;
            int start = Math.floorMod((int) (gameTick % samples.size()), samples.size());
            for (int offset = 0;
                 offset < samples.size() && processed < MAX_TRAIL_SAMPLES_PROCESSED_PER_TICK;
                 offset++) {
                PyroclasticTrailState sample = samples.get((start + offset) % samples.size());
                processed++;
                BlockPos pos = BlockPos.containing(sample.position().x, sample.position().y, sample.position().z);
                if (!level.hasChunkAt(pos)) {
                    continue;
                }
                renderTrail(level, sample);
                if (sample.ageTicks() > 0L
                        && gameTick % TRAIL_EXPOSURE_INTERVAL_TICKS == 0L
                        && affected < exposureBudget) {
                    affected += expose(level, sample, exposureBudget - affected);
                }
            }
            return affected;
        }
    }

    private static void publishLavaHeat(ServerLevel level, EruptionSignal signal, long gameTick) {
        UUID volcanoId = signal.volcanoId();
        if (signal.phase() == EruptionPhase.DORMANT || signal.intensity() <= 0.0) {
            VolcanicHeatService.remove(level, LavaHeatSourceProjector.sourceId(volcanoId));
            return;
        }
        VolcanicHeatService.upsert(
                level,
                LavaHeatSourceProjector.fromSignal(signal, gameTick, LAVA_HEAT_TTL_TICKS));
    }

    private static int impact(ServerLevel level, Vec3 position, int budget) {
        level.sendParticles(ParticleTypes.LAVA, position.x, position.y, position.z, 12, 0.7, 0.5, 0.7, 0.08);
        if (budget <= 0) {
            return 0;
        }
        AABB box = new AABB(
                position.x - 2.5,
                position.y - 2.5,
                position.z - 2.5,
                position.x + 2.5,
                position.y + 2.5,
                position.z + 2.5);
        int affected = 0;
        for (LivingEntity entity : VolcanicEntityQueryBudget.collect(
                budget,
                level,
                box,
                LivingEntity::isAlive)) {
            entity.hurt(level.damageSources().generic(), 6.0F);
            affected++;
        }
        return affected;
    }

    private static int expose(ServerLevel level, PyroclasticFlowState state, int budget) {
        return expose(level, state.position(), PyroclasticExposure.from(state), budget);
    }

    private static int expose(ServerLevel level, PyroclasticTrailState state, int budget) {
        return expose(level, state.position(), PyroclasticExposure.from(state), budget);
    }

    private static int expose(ServerLevel level, Vec3 position, PyroclasticExposure exposure, int budget) {
        if (budget <= 0) {
            return 0;
        }
        double radius = exposure.radiusBlocks();
        AABB box = new AABB(
                position.x - radius,
                position.y - Math.min(3.0, radius),
                position.z - radius,
                position.x + radius,
                position.y + Math.min(3.0, radius),
                position.z + radius);
        int affected = 0;
        for (LivingEntity entity : VolcanicEntityQueryBudget.collect(
                budget,
                level,
                box,
                LivingEntity::isAlive)) {
            float heatDamage = (float) (0.5 + 1.5 * exposure.heatSeverity());
            float particulateDamage = (float) (0.25 + 0.75 * exposure.particulateSeverity());
            entity.hurt(level.damageSources().inFire(), heatDamage);
            entity.hurt(level.damageSources().generic(), particulateDamage);
            affected++;
        }
        return affected;
    }

    private static PyroclasticSlopeSample slopeSample(ServerLevel level, BlockPos center) {
        int step = 2;
        BlockPos west = center.offset(-step, 0, 0);
        BlockPos east = center.offset(step, 0, 0);
        BlockPos north = center.offset(0, 0, -step);
        BlockPos south = center.offset(0, 0, step);
        if (!level.hasChunkAt(west)
                || !level.hasChunkAt(east)
                || !level.hasChunkAt(north)
                || !level.hasChunkAt(south)) {
            return null;
        }
        return new PyroclasticSlopeSample(
                level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, west.getX(), west.getZ()),
                level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, east.getX(), east.getZ()),
                level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, north.getX(), north.getZ()),
                level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, south.getX(), south.getZ()));
    }

    private static void renderPlume(ServerLevel level, AshPlumeEmission emission) {
        double spread = Math.min(10.0, Math.max(1.0, emission.plumeRadiusBlocks() * 0.10));
        double x = emission.source().getX() + 0.5;
        double y = emission.source().getY() + 3.0;
        double z = emission.source().getZ() + 0.5;
        level.sendParticles(ParticleTypes.ASH, x, y, z, 8, spread, 3.0, spread, 0.02);
        level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 4, spread * 0.5, 2.0, spread * 0.5, 0.01);
    }

    private static void renderFlow(ServerLevel level, PyroclasticFlowState state) {
        double spread = Math.min(3.0, Math.max(0.3, state.radiusBlocks() * 0.25));
        level.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                state.position().x,
                state.position().y + 0.5,
                state.position().z,
                8,
                spread,
                0.8,
                spread,
                0.03);
        level.sendParticles(
                ParticleTypes.ASH,
                state.position().x,
                state.position().y + 0.3,
                state.position().z,
                8,
                spread,
                0.5,
                spread,
                0.02);
    }

    private static void renderTrail(ServerLevel level, PyroclasticTrailState state) {
        double spread = Math.min(2.5, Math.max(0.2, state.radiusBlocks() * 0.20));
        level.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                state.position().x,
                state.position().y + 0.35,
                state.position().z,
                3,
                spread,
                0.45,
                spread,
                0.015);
        level.sendParticles(
                ParticleTypes.ASH,
                state.position().x,
                state.position().y + 0.20,
                state.position().z,
                3,
                spread,
                0.30,
                spread,
                0.01);
    }

    private record BombTickResult(int entityExposures, int terrainMutations) {
    }

    private record FlowTickResult(int entityExposures, int terrainMutations) {
    }
}
