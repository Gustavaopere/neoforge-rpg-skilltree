package dev.gustavopere.volcanoes.pressure;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;

/** Composes water depth, atmosphere, enclosed environments, equipment and staged exposure for one entity. */
public final class PressureEntityCoordinator {
    private final PressureService pressureService;
    private final AtmosphericPressureLookup atmosphericPressure;
    private final EnclosedEnvironmentResolver enclosedEnvironments;
    private final EquipmentProtectionResolver equipmentProtection;
    private final PressureExposureTracker exposureTracker;
    private final Supplier<PressureExposureConfig> exposureConfig;
    private final double waterDensityKgM3;

    public PressureEntityCoordinator(
            PressureService pressureService,
            AtmosphericPressureLookup atmosphericPressure,
            EnclosedEnvironmentResolver enclosedEnvironments,
            EquipmentProtectionResolver equipmentProtection,
            PressureExposureTracker exposureTracker,
            PressureExposureConfig exposureConfig,
            double waterDensityKgM3
    ) {
        this(
                pressureService,
                atmosphericPressure,
                enclosedEnvironments,
                equipmentProtection,
                exposureTracker,
                fixedConfig(exposureConfig),
                waterDensityKgM3);
    }

    public PressureEntityCoordinator(
            PressureService pressureService,
            AtmosphericPressureLookup atmosphericPressure,
            EnclosedEnvironmentResolver enclosedEnvironments,
            EquipmentProtectionResolver equipmentProtection,
            PressureExposureTracker exposureTracker,
            Supplier<PressureExposureConfig> exposureConfig,
            double waterDensityKgM3
    ) {
        this.pressureService = Objects.requireNonNull(pressureService, "pressureService");
        this.atmosphericPressure = Objects.requireNonNull(atmosphericPressure, "atmosphericPressure");
        this.enclosedEnvironments = Objects.requireNonNull(enclosedEnvironments, "enclosedEnvironments");
        this.equipmentProtection = Objects.requireNonNull(equipmentProtection, "equipmentProtection");
        this.exposureTracker = Objects.requireNonNull(exposureTracker, "exposureTracker");
        this.exposureConfig = Objects.requireNonNull(exposureConfig, "exposureConfig");
        if (!Double.isFinite(waterDensityKgM3) || waterDensityKgM3 < 0.0) {
            throw new IllegalArgumentException("waterDensityKgM3 must be finite and non-negative");
        }
        this.waterDensityKgM3 = waterDensityKgM3;
    }

    public PressureEntityUpdate update(
            PressureEntityContext context,
            WaterDepthSample waterDepth,
            long gameTick
    ) {
        return update(context, waterDepth, gameTick, List.of(), OptionalDouble.empty());
    }

    public PressureEntityUpdate update(
            PressureEntityContext context,
            WaterDepthSample waterDepth,
            long gameTick,
            List<ProtectionContribution> hostResolvedContributions
    ) {
        return update(context, waterDepth, gameTick, hostResolvedContributions, OptionalDouble.empty());
    }

    public PressureEntityUpdate update(
            PressureEntityContext context,
            WaterDepthSample waterDepth,
            long gameTick,
            List<ProtectionContribution> hostResolvedContributions,
            OptionalDouble contextualAtmosphericAtm
    ) {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(waterDepth, "waterDepth");
        Objects.requireNonNull(hostResolvedContributions, "hostResolvedContributions");
        Objects.requireNonNull(contextualAtmosphericAtm, "contextualAtmosphericAtm");
        ProtectionSnapshot protection = equipmentProtection.resolve(
                context.equipmentContext(),
                hostResolvedContributions);
        return updateResolved(
                context,
                waterDepth,
                gameTick,
                protection,
                protection.beginUpdate(),
                contextualAtmosphericAtm);
    }

    /**
     * Executes Pressure against a protection transaction already resolved for this entity/game tick.
     * Respiration may have opened the same transaction first; physical debit keys therefore remain
     * exactly-once across both callbacks.
     */
    public PressureEntityUpdate update(
            PressureEntityContext context,
            WaterDepthSample waterDepth,
            long gameTick,
            ProtectionUseSession protectionUseSession
    ) {
        return update(context, waterDepth, gameTick, protectionUseSession, OptionalDouble.empty());
    }

    public PressureEntityUpdate update(
            PressureEntityContext context,
            WaterDepthSample waterDepth,
            long gameTick,
            ProtectionUseSession protectionUseSession,
            OptionalDouble contextualAtmosphericAtm
    ) {
        Objects.requireNonNull(protectionUseSession, "protectionUseSession");
        Objects.requireNonNull(contextualAtmosphericAtm, "contextualAtmosphericAtm");
        return updateResolved(
                Objects.requireNonNull(context, "context"),
                Objects.requireNonNull(waterDepth, "waterDepth"),
                gameTick,
                protectionUseSession.snapshot(),
                protectionUseSession,
                contextualAtmosphericAtm);
    }

    private PressureEntityUpdate updateResolved(
            PressureEntityContext context,
            WaterDepthSample waterDepth,
            long gameTick,
            ProtectionSnapshot protection,
            ProtectionUseSession protectionUseSession,
            OptionalDouble contextualAtmosphericAtm
    ) {
        double atmosphericSampleY = waterDepth.surfaceResolved()
                ? context.y() + waterDepth.depthMeters()
                : context.y();
        double atmosphericAtm = contextualAtmosphericAtm.isPresent()
                ? contextualAtmosphericAtm.getAsDouble()
                : atmosphericPressure.pressureAtm(context.dimensionId(), atmosphericSampleY);
        if (!Double.isFinite(atmosphericAtm) || atmosphericAtm < 0.0) {
            atmosphericAtm = atmosphericPressure.pressureAtm(context.dimensionId(), atmosphericSampleY);
        }
        if (!Double.isFinite(atmosphericAtm) || atmosphericAtm < 0.0) {
            throw new IllegalStateException("atmospheric pressure lookup must return a finite non-negative value");
        }

        PressureSample externalPressure = pressureService.sample(
                atmosphericAtm,
                waterDepth.depthMeters(),
                waterDensityKgM3);
        Optional<EnclosedEnvironment> enclosed = enclosedEnvironments.resolve(context.enclosedQuery(), gameTick);
        PressureEnvironmentResult environment = PressureEnvironmentResolver.resolve(
                externalPressure,
                enclosed,
                protection,
                protectionUseSession);
        PressureExposureConfig currentExposureConfig = Objects.requireNonNull(
                exposureConfig.get(), "exposureConfig supplier must not return null");
        PressureExposureResult exposure = exposureTracker.update(
                context.entityId(),
                environment.protectedOverpressureAtm(),
                currentExposureConfig);
        PressureEntityEffectPlan effects = PressureEntityEffectPolicy.plan(exposure);

        return new PressureEntityUpdate(externalPressure, environment, exposure, effects);
    }

    public void clearEntity(java.util.UUID entityId) {
        exposureTracker.clear(entityId);
        enclosedEnvironments.invalidateEntity(entityId);
    }

    private static Supplier<PressureExposureConfig> fixedConfig(PressureExposureConfig config) {
        PressureExposureConfig fixed = Objects.requireNonNull(config, "exposureConfig");
        return () -> fixed;
    }
}
