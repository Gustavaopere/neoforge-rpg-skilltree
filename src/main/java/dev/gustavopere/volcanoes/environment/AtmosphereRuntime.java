package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.GeothermalSourceRegistry;
import dev.gustavopere.volcanoes.volcano.VolcanicHazardWorldRuntime;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

public final class AtmosphereRuntime {
    static final int DIFFUSION_INTERVAL_TICKS = 20;
    static final int MAX_SOURCE_UPDATES_PER_INTERVAL = 64;
    private static final int MAX_GEOTHERMAL_PENDING = 16_384;
    private static final RespirationModel RESPIRATION =
            new RespirationModel(RespirationThresholds.defaults());
    private static final AtmosphereSyncTracker SYNC_TRACKER = new AtmosphereSyncTracker();
    private static final AshAtmosphereBridge ASH_BRIDGE = new AshAtmosphereBridge();
    private static volatile RespirationProtectionProvider RESPIRATION_PROTECTION_PROVIDER =
            RespirationProtectionProvider.none();
    private static final Map<ServerLevel, AtmosphereField> FIELDS =
            Collections.synchronizedMap(new WeakHashMap<>());
    private static final Map<ServerLevel, GeothermalBridgeRegistration> GEOTHERMAL_BRIDGES =
            Collections.synchronizedMap(new WeakHashMap<>());
    private static boolean ashBridgeRegistered;

    private AtmosphereRuntime() {
    }

    public static synchronized void registerAshBridge() {
        if (ashBridgeRegistered) {
            return;
        }
        ashBridgeRegistered = VolcanicHazardWorldRuntime.registerAshEmissionLifecycleSink(ASH_BRIDGE);
    }

    static boolean shouldProcessDiffusion(long gameTime) {
        return gameTime >= 0L && Math.floorMod(gameTime, (long) DIFFUSION_INTERVAL_TICKS) == 0L;
    }

    public static void onLivingBreathe(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel level)) {
            return;
        }
        if (RespirationSubjectPolicy.exempt(entity)) {
            return;
        }
        if (!entity.getEyeInFluidType().isAir()) {
            return;
        }

        AtmosphereField field = fieldFor(level);
        AtmosphereState state = field.sample(
                level.dimension().location().toString(),
                level.getSeed(),
                entity.getX(),
                entity.getEyeY(),
                entity.getZ());
        RespirationProtection taggedProtection = TaggedRespirationProtectionProvider.fromEntity(entity);
        RespirationProtection additionalProtection = safeInstalledProtection(
                RESPIRATION_PROTECTION_PROVIDER,
                entity,
                state);
        RespirationProtection protection = taggedProtection.combine(additionalProtection);
        RespirationOutcome outcome = RESPIRATION.evaluate(state, protection);

        if (outcome.canBreathe()) {
            if (event.canBreathe()) {
                event.setRefillAirAmount(Math.max(event.getRefillAirAmount(), outcome.refillAirAmount()));
            }
        } else {
            event.setCanBreathe(false);
            event.setConsumeAirAmount(Math.max(event.getConsumeAirAmount(), outcome.consumeAirAmount()));
        }
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        if (!shouldProcessDiffusion(level.getGameTime())) {
            return;
        }
        ASH_BRIDGE.flush(level, MAX_SOURCE_UPDATES_PER_INTERVAL);
        geothermalBridgeFor(level).flush(MAX_SOURCE_UPDATES_PER_INTERVAL);
        AtmosphereField field = fieldFor(level);
        field.tick(MAX_SOURCE_UPDATES_PER_INTERVAL);
        syncPlayers(level, field);
    }

    public static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        ASH_BRIDGE.forgetLevel(level);
        forgetGeothermalBridge(level);
        synchronized (FIELDS) {
            FIELDS.remove(level);
        }
    }

    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        SYNC_TRACKER.forget(event.getEntity().getUUID());
    }

    public static AtmosphericSourceSink sourceSinkFor(ServerLevel level) {
        return fieldFor(Objects.requireNonNull(level, "level"));
    }

    public static void installExternalContributionProvider(
            ServerLevel level,
            AtmosphereExternalContributionProvider provider
    ) {
        fieldFor(Objects.requireNonNull(level, "level"))
                .replaceExternalContributionProvider(Objects.requireNonNull(provider, "provider"));
    }

    public static void installRespirationProtectionProvider(RespirationProtectionProvider provider) {
        RESPIRATION_PROTECTION_PROVIDER = Objects.requireNonNull(provider, "provider");
    }

    static RespirationProtection safeInstalledProtection(
            RespirationProtectionProvider provider,
            LivingEntity entity,
            AtmosphereState state
    ) {
        Objects.requireNonNull(provider, "provider");
        Objects.requireNonNull(state, "state");
        try {
            return Objects.requireNonNull(
                    provider.protectionFor(entity, state),
                    "respirationProtectionProvider returned null");
        } catch (RuntimeException | LinkageError optionalIntegrationFailure) {
            return RespirationProtection.NONE;
        }
    }

    static AtmosphereField fieldFor(ServerLevel level) {
        synchronized (FIELDS) {
            return FIELDS.computeIfAbsent(level, AtmosphereRuntime::createField);
        }
    }

    static GeothermalAtmosphereBridge geothermalBridgeFor(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        synchronized (GEOTHERMAL_BRIDGES) {
            GeothermalBridgeRegistration existing = GEOTHERMAL_BRIDGES.get(level);
            if (existing != null) {
                return existing.bridge();
            }
            GeothermalSourceRegistry registry = GeothermalSourceRegistry.get(level);
            GeothermalAtmosphereBridge bridge = new GeothermalAtmosphereBridge(
                    level.dimension().location().toString(),
                    fieldFor(level),
                    GeothermalAtmosphereProjectionPolicy.defaults(),
                    MAX_GEOTHERMAL_PENDING);
            registry.registerLifecycleSink(bridge);
            GEOTHERMAL_BRIDGES.put(level, new GeothermalBridgeRegistration(registry, bridge));
            return bridge;
        }
    }

    private static void forgetGeothermalBridge(ServerLevel level) {
        GeothermalBridgeRegistration registration;
        synchronized (GEOTHERMAL_BRIDGES) {
            registration = GEOTHERMAL_BRIDGES.remove(level);
        }
        if (registration != null) {
            registration.registry().unregisterLifecycleSink(registration.bridge());
        }
    }

    private static AtmosphereField createField(ServerLevel level) {
        AtmospherePersistencePolicy persistencePolicy = AtmosphereConfig.persistencePolicy();
        AtmosphereSavedData savedData = AtmosphereSavedData.get(level, persistencePolicy);
        AtmosphereField field = new AtmosphereField(
                PressureAtmosphereBaselineProvider.canonical(LayeredAtmosphereBaselineProvider.standard()),
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults(),
                AtmosphereTransportProvider.stillAir(),
                savedData);
        for (AtmosphericSource source : savedData.all()) {
            field.restore(source);
        }
        return field;
    }

    private static void syncPlayers(ServerLevel level, AtmosphereField field) {
        String dimensionId = level.dimension().location().toString();
        long worldSeed = level.getSeed();
        for (ServerPlayer player : level.players()) {
            AtmosphereSnapshot snapshot = AtmosphereSnapshot.from(field.sample(
                    dimensionId,
                    worldSeed,
                    player.getX(),
                    player.getEyeY(),
                    player.getZ()));
            if (SYNC_TRACKER.needsSend(player.getUUID(), snapshot)) {
                PacketDistributor.sendToPlayer(player, new AtmosphereSyncPayload(snapshot));
                SYNC_TRACKER.markSent(player.getUUID(), snapshot);
            }
        }
    }

    private record GeothermalBridgeRegistration(
            GeothermalSourceRegistry registry,
            GeothermalAtmosphereBridge bridge
    ) {
        private GeothermalBridgeRegistration {
            Objects.requireNonNull(registry, "registry");
            Objects.requireNonNull(bridge, "bridge");
        }
    }
}
