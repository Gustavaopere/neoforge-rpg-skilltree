package dev.gustavopere.volcanoes.pressure;

import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Consumer;

/** Runs one bounded pressure update for a player without depending on NeoForge entity types. */
public final class PressurePlayerTickProcessor {
    private static final WaterDepthSample DRY = new WaterDepthSample(0.0, true);

    private final PressureEntityCoordinator coordinator;
    private final PressureWaterDepthLookup waterDepthLookup;
    private final ContextualAtmosphericPressureLookup contextualAtmosphere;

    public PressurePlayerTickProcessor(
            PressureEntityCoordinator coordinator,
            PressureWaterDepthLookup waterDepthLookup
    ) {
        this(coordinator, waterDepthLookup, ContextualAtmosphericPressureRuntime::pressureAtm);
    }

    public PressurePlayerTickProcessor(
            PressureEntityCoordinator coordinator,
            PressureWaterDepthLookup waterDepthLookup,
            ContextualAtmosphericPressureLookup contextualAtmosphere
    ) {
        this.coordinator = Objects.requireNonNull(coordinator, "coordinator");
        this.waterDepthLookup = Objects.requireNonNull(waterDepthLookup, "waterDepthLookup");
        this.contextualAtmosphere = Objects.requireNonNull(contextualAtmosphere, "contextualAtmosphere");
    }

    public PressureEntityUpdate tick(
            PressurePlayerTickSnapshot snapshot,
            long gameTick,
            Consumer<PressureEntityEffectPlan> effectSink
    ) {
        Objects.requireNonNull(snapshot, "snapshot");
        return tickInternal(snapshot, gameTick, null, effectSink);
    }

    public PressureEntityUpdate tick(
            PressurePlayerTickSnapshot snapshot,
            long gameTick,
            ProtectionUseSession protectionUseSession,
            Consumer<PressureEntityEffectPlan> effectSink
    ) {
        Objects.requireNonNull(snapshot, "snapshot");
        Objects.requireNonNull(protectionUseSession, "protectionUseSession");
        return tickInternal(snapshot, gameTick, protectionUseSession, effectSink);
    }

    private PressureEntityUpdate tickInternal(
            PressurePlayerTickSnapshot snapshot,
            long gameTick,
            ProtectionUseSession protectionUseSession,
            Consumer<PressureEntityEffectPlan> effectSink
    ) {
        Objects.requireNonNull(effectSink, "effectSink");

        WaterDepthSample waterDepth = snapshot.immersedInWater()
                ? Objects.requireNonNull(
                        waterDepthLookup.sample(snapshot.context(), gameTick),
                        "waterDepthLookup.sample must not return null")
                : DRY;
        double atmosphericSampleY = waterDepth.surfaceResolved()
                ? snapshot.context().y() + waterDepth.depthMeters()
                : snapshot.context().y();
        OptionalDouble contextualAtmosphericAtm = contextualAtmosphere(
                snapshot.context(),
                atmosphericSampleY);

        PressureEntityUpdate update = protectionUseSession == null
                ? coordinator.update(
                        snapshot.context(),
                        waterDepth,
                        gameTick,
                        snapshot.hostResolvedContributions(),
                        contextualAtmosphericAtm)
                : coordinator.update(
                        snapshot.context(),
                        waterDepth,
                        gameTick,
                        protectionUseSession,
                        contextualAtmosphericAtm);
        effectSink.accept(update.effects());
        return update;
    }

    private OptionalDouble contextualAtmosphere(
            PressureEntityContext context,
            double atmosphericSampleY
    ) {
        try {
            OptionalDouble sampled = contextualAtmosphere.pressureAtm(context, atmosphericSampleY);
            if (sampled == null || sampled.isEmpty()) {
                return OptionalDouble.empty();
            }
            double pressureAtm = sampled.getAsDouble();
            if (!Double.isFinite(pressureAtm) || pressureAtm < 0.0) {
                return OptionalDouble.empty();
            }
            return OptionalDouble.of(pressureAtm);
        } catch (RuntimeException | LinkageError optionalHostFailure) {
            return OptionalDouble.empty();
        }
    }
}
