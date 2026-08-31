package dev.gustavopere.volcanoes.pressure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Thin NeoForge bridge from server-player lifecycle events into the loader-neutral pressure core.
 * Optional mod integrations remain behind the Stage 05 SPIs and are deliberately not referenced here.
 */
public final class PressureNeoForgeRuntime {
    private static final double WATER_DENSITY_KG_M3 = 1_000.0;
    private static final int MAX_VISITED_WATER_CELLS = 256;
    private static final int MAX_WATER_GRAPH_DISTANCE = 128;
    private static final long WATER_DEPTH_CACHE_TICKS = 20L;
    private static final int EFFECT_REFRESH_TICKS = 5;
    private static final int MAX_HOST_PROTECTION_CONTRIBUTIONS = 64;
    private static final int MAX_HOST_EQUIPPED_ITEMS = 64;
    private static final int MAX_SHARED_PROTECTION_SESSIONS = 4_096;

    private static final EnclosedEnvironmentResolver ENCLOSED_ENVIRONMENTS =
            new EnclosedEnvironmentResolver(List.of(), 5L, 4_096);
    private static final AtmosphericPressureResolver ATMOSPHERIC_PRESSURE =
            new AtmosphericPressureResolver(AtmosphericPressureRuntime::pressureAtm);
    private static final EquipmentProtectionResolver EQUIPMENT_PROTECTION =
            new EquipmentProtectionResolver(List.of());
    private static final ProtectionUseRegistry PROTECTION_USES =
            new ProtectionUseRegistry(MAX_SHARED_PROTECTION_SESSIONS);
    private static volatile List<HostProtectionProvider> hostProtectionProviders = List.of();
    private static volatile List<HostEquipmentProvider> hostEquipmentProviders = List.of();
    private static final PressureEntityCoordinator COORDINATOR = new PressureEntityCoordinator(
            PressureService.fallback(),
            ATMOSPHERIC_PRESSURE,
            ENCLOSED_ENVIRONMENTS,
            EQUIPMENT_PROTECTION,
            new PressureExposureTracker(),
            PressureExposureConfigRuntime::current,
            WATER_DENSITY_KG_M3);

    /** ServerLevel lifetime is explicit: entries are removed by onLevelUnload. */
    private static final Map<ServerLevel, LevelRuntime> LEVEL_RUNTIMES =
            Collections.synchronizedMap(new IdentityHashMap<>());

    private PressureNeoForgeRuntime() {
    }

    @FunctionalInterface
    public interface HostProtectionProvider {
        List<ProtectionContribution> resolve(ServerPlayer player, long gameTick);
    }

    @FunctionalInterface
    public interface HostEquipmentProvider {
        List<EquippedItemView> resolve(ServerPlayer player);
    }

    /**
     * Registers an optional external atmospheric-pressure authority.
     *
     * <p>Integrations should register during setup. Sampling remains hot-path safe because provider
     * ordering is rebuilt only when this method is called, never during player ticks.</p>
     */
    public static void registerAtmosphericPressureProvider(AtmosphericPressureProvider provider) {
        ATMOSPHERIC_PRESSURE.register(provider);
    }

    /**
     * Registers an optional enclosed-environment provider during integration setup.
     * Registration invalidates stale enclosed-environment cache entries before gameplay sampling.
     */
    public static void registerEnclosedEnvironmentProvider(EnclosedEnvironmentProvider provider) {
        ENCLOSED_ENVIRONMENTS.register(provider);
    }

    /** Invalidates cached enclosed state for one entity after a trustworthy host-state transition. */
    public static void invalidateEnclosedEnvironmentEntity(UUID entityId) {
        ENCLOSED_ENVIRONMENTS.invalidateEntity(entityId);
    }

    /** Invalidates cached enclosed state for every cached occupant of one vehicle/host. */
    public static void invalidateEnclosedEnvironmentVehicle(UUID vehicleId) {
        ENCLOSED_ENVIRONMENTS.invalidateVehicle(vehicleId);
    }

    /**
     * Registers an optional loader-neutral equipment protection adapter during integration setup.
     * Native host-resource collectors remain a separate bridge because they need access to ServerPlayer state.
     */
    public static void registerEquipmentProtectionAdapter(EquipmentProtectionAdapter adapter) {
        EQUIPMENT_PROTECTION.register(adapter);
    }

    /** Registers a bounded host-state collector such as Create backtank air. Setup-time only. */
    public static synchronized void registerHostProtectionProvider(HostProtectionProvider provider) {
        Objects.requireNonNull(provider, "provider");
        ArrayList<HostProtectionProvider> updated = new ArrayList<>(hostProtectionProviders);
        updated.add(provider);
        hostProtectionProviders = List.copyOf(updated);
    }

    /** Registers optional equipped-item sources such as Curios. Setup-time only. */
    public static synchronized void registerHostEquipmentProvider(HostEquipmentProvider provider) {
        Objects.requireNonNull(provider, "provider");
        ArrayList<HostEquipmentProvider> updated = new ArrayList<>(hostEquipmentProviders);
        updated.add(provider);
        hostEquipmentProviders = List.copyOf(updated);
    }

    /**
     * Returns the canonical protection transaction for this player and logical server tick.
     * Independent Pressure and Respiration callbacks therefore share resource-debit results.
     */
    public static ProtectionUseSession protectionSession(ServerPlayer player, long gameTick) {
        Objects.requireNonNull(player, "player");
        if (!(player.level() instanceof ServerLevel level)) {
            throw new IllegalArgumentException("player must be in a ServerLevel");
        }
        PressureEntityContext context = pressureContext(player, level);
        List<ProtectionContribution> hostContributions = hostResolvedContributions(player, gameTick);
        return protectionSession(context, hostContributions, gameTick);
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }

        long gameTick = level.getGameTime();
        PressurePlayerTickSnapshot snapshot = snapshot(player, level, gameTick);
        ProtectionUseSession protectionUseSession = protectionSession(
                snapshot.context(),
                snapshot.hostResolvedContributions(),
                gameTick);
        levelRuntime(level).processor().tick(
                snapshot,
                gameTick,
                protectionUseSession,
                plan -> applyEffects(player, plan));
    }

    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearEntityRuntime(player.getUUID());
        }
    }

    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearEntityRuntime(player.getUUID());
        }
    }

    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearEntityRuntime(player.getUUID());
        }
    }

    private static void clearEntityRuntime(UUID entityId) {
        COORDINATOR.clearEntity(entityId);
        PROTECTION_USES.clear(entityId);
    }

    public static void onChunkLoad(ChunkEvent.Load event) {
        invalidateWaterDepthChunk(event);
    }

    public static void onChunkUnload(ChunkEvent.Unload event) {
        invalidateWaterDepthChunk(event);
    }

    private static void invalidateWaterDepthChunk(ChunkEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        LevelRuntime runtime;
        synchronized (LEVEL_RUNTIMES) {
            runtime = LEVEL_RUNTIMES.get(level);
        }
        if (runtime == null) {
            return;
        }

        String dimensionId = level.dimension().location().toString();
        runtime.depthLookup().invalidateChunk(
                dimensionId,
                event.getChunk().getPos().x,
                event.getChunk().getPos().z);
    }

    public static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        LevelRuntime removed;
        synchronized (LEVEL_RUNTIMES) {
            removed = LEVEL_RUNTIMES.remove(level);
        }
        if (removed != null) {
            removed.close();
        }
        ENCLOSED_ENVIRONMENTS.clear();
        PROTECTION_USES.clear();
    }

    private static LevelRuntime levelRuntime(ServerLevel level) {
        synchronized (LEVEL_RUNTIMES) {
            return LEVEL_RUNTIMES.computeIfAbsent(level, PressureNeoForgeRuntime::createLevelRuntime);
        }
    }

    private static LevelRuntime createLevelRuntime(ServerLevel level) {
        BoundedConnectedWaterDepthLookup depthLookup = new BoundedConnectedWaterDepthLookup(
                MAX_VISITED_WATER_CELLS,
                MAX_WATER_GRAPH_DISTANCE,
                WATER_DEPTH_CACHE_TICKS);
        WaterVolumeProbe probe = new ServerLevelWaterVolumeProbe(level);
        PressureWaterDepthLookup playerDepthLookup = (context, gameTick) -> {
            BlockPos origin = BlockPos.containing(context.x(), context.y(), context.z());
            FluidState originFluid = level.getFluidState(origin);
            if (originFluid.is(FluidTags.WATER)) {
                Optional<WaterDepthSample> localSurface = localOpenSurfaceDepth(
                        true,
                        context.y(),
                        origin.getY(),
                        originFluid.getHeight(level, origin),
                        classifyWaterCell(level, origin.above()));
                if (localSurface.isPresent()) {
                    return localSurface.orElseThrow();
                }
            }

            WaterDepthSample discrete = depthLookup.sample(
                    probe,
                    context.dimensionId(),
                    origin.getX(),
                    origin.getY(),
                    origin.getZ(),
                    gameTick);
            return continuousEyeDepth(discrete, context.y());
        };
        return new LevelRuntime(depthLookup, new PressurePlayerTickProcessor(COORDINATOR, playerDepthLookup));
    }

    private static PressurePlayerTickSnapshot snapshot(ServerPlayer player, ServerLevel level, long gameTick) {
        double eyeY = player.getEyeY();
        BlockPos eyeBlock = BlockPos.containing(player.getX(), eyeY, player.getZ());
        FluidState eyeFluid = level.getFluidState(eyeBlock);
        boolean immersedInWater = isEyeSubmergedInTaggedWater(
                eyeFluid.is(FluidTags.WATER),
                eyeY,
                eyeBlock.getY(),
                eyeFluid.getHeight(level, eyeBlock));
        PressureEntityContext context = pressureContext(player, level);
        return new PressurePlayerTickSnapshot(
                context,
                immersedInWater,
                hostResolvedContributions(player, gameTick));
    }

    private static PressureEntityContext pressureContext(ServerPlayer player, ServerLevel level) {
        Optional<UUID> vehicleId = Optional.ofNullable(player.getVehicle())
                .map(vehicle -> vehicle.getUUID());
        return new PressureEntityContext(
                player.getUUID(),
                vehicleId,
                level.dimension().location().toString(),
                player.getX(),
                player.getEyeY(),
                player.getZ(),
                equippedItems(player));
    }

    private static ProtectionUseSession protectionSession(
            PressureEntityContext context,
            List<ProtectionContribution> hostContributions,
            long gameTick
    ) {
        return PROTECTION_USES.session(
                context.entityId(),
                gameTick,
                () -> EQUIPMENT_PROTECTION.resolve(context.equipmentContext(), hostContributions));
    }

    private static List<ProtectionContribution> hostResolvedContributions(
            ServerPlayer player,
            long gameTick
    ) {
        ArrayList<ProtectionContribution> contributions = new ArrayList<>();
        List<HostProtectionProvider> providers = hostProtectionProviders;
        for (HostProtectionProvider provider : providers) {
            List<ProtectionContribution> resolved;
            try {
                resolved = provider.resolve(player, gameTick);
            } catch (RuntimeException | LinkageError optionalHostFailure) {
                continue;
            }
            if (resolved == null) {
                continue;
            }
            for (ProtectionContribution contribution : resolved) {
                if (contribution == null) {
                    continue;
                }
                contributions.add(contribution);
                if (contributions.size() >= MAX_HOST_PROTECTION_CONTRIBUTIONS) {
                    return List.copyOf(contributions);
                }
            }
        }
        return List.copyOf(contributions);
    }

    private static List<EquippedItemView> equippedItems(ServerPlayer player) {
        ArrayList<EquippedItemView> equipped = new ArrayList<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) {
                continue;
            }
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty()) {
                continue;
            }
            Set<String> tags = stack.getTags()
                    .map(tag -> tag.location().toString())
                    .collect(Collectors.toUnmodifiableSet());
            equipped.add(new EquippedItemView(
                    slot.getName(),
                    BuiltInRegistries.ITEM.getKey(stack.getItem()).toString(),
                    tags));
        }
        equipped.addAll(hostEquippedItems(player));
        return List.copyOf(equipped);
    }

    private static List<EquippedItemView> hostEquippedItems(ServerPlayer player) {
        ArrayList<EquippedItemView> equipped = new ArrayList<>();
        List<HostEquipmentProvider> providers = hostEquipmentProviders;
        for (HostEquipmentProvider provider : providers) {
            List<EquippedItemView> resolved;
            try {
                resolved = provider.resolve(player);
            } catch (RuntimeException | LinkageError optionalHostFailure) {
                continue;
            }
            if (resolved == null) {
                continue;
            }
            for (EquippedItemView item : resolved) {
                if (item == null) {
                    continue;
                }
                equipped.add(item);
                if (equipped.size() >= MAX_HOST_EQUIPPED_ITEMS) {
                    return List.copyOf(equipped);
                }
            }
        }
        return List.copyOf(equipped);
    }

    private static void applyEffects(ServerPlayer player, PressureEntityEffectPlan plan) {
        if (plan.movementPenalty()) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    EFFECT_REFRESH_TICKS,
                    plan.movementAmplifier(),
                    true,
                    false,
                    false));
        }
        if (plan.neurologicalPenalty()) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.CONFUSION,
                    EFFECT_REFRESH_TICKS,
                    plan.neurologicalAmplifier(),
                    true,
                    false,
                    false));
        }
        if (plan.damage() > 0.0) {
            player.hurt(player.damageSources().generic(), (float) Math.min(plan.damage(), Float.MAX_VALUE));
        }
    }

    static boolean isEyeSubmergedInTaggedWater(
            boolean taggedWater,
            double eyeY,
            int blockY,
            double fluidHeight
    ) {
        if (!taggedWater || !Double.isFinite(eyeY) || !Double.isFinite(fluidHeight) || fluidHeight <= 0.0) {
            return false;
        }
        return eyeY < blockY + fluidHeight;
    }

    static Optional<WaterDepthSample> localOpenSurfaceDepth(
            boolean taggedWater,
            double eyeY,
            int blockY,
            double fluidHeight,
            WaterCellKind aboveCell
    ) {
        Objects.requireNonNull(aboveCell, "aboveCell");
        if (!taggedWater
                || !Double.isFinite(eyeY)
                || !Double.isFinite(fluidHeight)
                || fluidHeight <= 0.0
                || fluidHeight > 1.0
                || aboveCell != WaterCellKind.OPEN_AIR) {
            return Optional.empty();
        }

        double surfaceY = blockY + fluidHeight;
        if (eyeY >= surfaceY) {
            return Optional.empty();
        }
        return Optional.of(new WaterDepthSample(surfaceY - eyeY, true));
    }

    static WaterDepthSample continuousEyeDepth(WaterDepthSample discrete, double eyeY) {
        double fractionalY = eyeY - Math.floor(eyeY);
        return new WaterDepthSample(
                Math.max(0.0, discrete.depthMeters() - fractionalY),
                discrete.surfaceResolved());
    }

    static WaterCellKind classifyWaterCell(boolean water, boolean air, boolean externallyOpen) {
        if (water) {
            return WaterCellKind.WATER;
        }
        if (air && externallyOpen) {
            return WaterCellKind.OPEN_AIR;
        }
        return WaterCellKind.BLOCKED;
    }

    static WaterCellKind classifyWaterCell(ServerLevel level, BlockPos pos) {
        int motionBlockingHeight = level.getHeight(
                Heightmap.Types.MOTION_BLOCKING,
                pos.getX(),
                pos.getZ());
        return classifyWaterCell(
                level.getFluidState(pos).is(FluidTags.WATER),
                level.getBlockState(pos).isAir(),
                pos.getY() >= motionBlockingHeight);
    }

    private record LevelRuntime(
            BoundedConnectedWaterDepthLookup depthLookup,
            PressurePlayerTickProcessor processor
    ) {
        private void close() {
            depthLookup.clear();
        }
    }

    private static final class ServerLevelWaterVolumeProbe implements WaterVolumeProbe {
        private final ServerLevel level;
        private final String dimensionId;

        private ServerLevelWaterVolumeProbe(ServerLevel level) {
            this.level = level;
            this.dimensionId = level.dimension().location().toString();
        }

        @Override
        public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
            if (!this.dimensionId.equals(dimensionId)) {
                return false;
            }
            return level.getChunkSource().hasChunk(Math.floorDiv(blockX, 16), Math.floorDiv(blockZ, 16));
        }

        @Override
        public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
            if (!isColumnLoaded(dimensionId, blockX, blockZ)) {
                return WaterCellKind.BLOCKED;
            }

            return classifyWaterCell(level, new BlockPos(blockX, blockY, blockZ));
        }
    }
}
