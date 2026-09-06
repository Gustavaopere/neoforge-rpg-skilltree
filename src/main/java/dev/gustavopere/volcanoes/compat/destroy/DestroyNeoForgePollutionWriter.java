package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.environment.PollutionEmission;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * Reflective binding for the verified Destroy 0.4.1 NeoForge pollution API.
 *
 * <p>No Destroy type appears in this class' linkage surface. Construction resolves the complete
 * host contract before the adapter can become authoritative. Publication then uses Destroy's own
 * {@code PollutionHelper.changePollution(Level, BlockPos, PollutionType, int)} routing so level and
 * chunk pollution scopes remain host-owned.</p>
 */
final class DestroyNeoForgePollutionWriter {
    private static final String POLLUTION_TYPES_CLASS = "petrolpark.mc.destroy.DestroyPollutionTypes";
    private static final String POLLUTION_TYPE_CLASS = "petrolpark.mc.destroy.core.pollution.PollutionType";
    private static final String POLLUTION_HELPER_CLASS = "petrolpark.mc.destroy.core.pollution.PollutionHelper";
    private static final int APPLIED_COMPONENT_CAPACITY = 16_384;

    private final Method changePollution;
    private final Object acidRainType;
    private final Object smogType;
    private final Object greenhouseType;
    private final Object ozoneDepletionType;
    private final DestroyPollutionApplicationLedger applied =
            new DestroyPollutionApplicationLedger(APPLIED_COMPONENT_CAPACITY);

    private DestroyNeoForgePollutionWriter(
            Method changePollution,
            Object acidRainType,
            Object smogType,
            Object greenhouseType,
            Object ozoneDepletionType
    ) {
        this.changePollution = Objects.requireNonNull(changePollution, "changePollution");
        this.acidRainType = Objects.requireNonNull(acidRainType, "acidRainType");
        this.smogType = Objects.requireNonNull(smogType, "smogType");
        this.greenhouseType = Objects.requireNonNull(greenhouseType, "greenhouseType");
        this.ozoneDepletionType = Objects.requireNonNull(ozoneDepletionType, "ozoneDepletionType");
    }

    static DestroyNeoForgePollutionWriter createVerified() {
        try {
            Class<?> pollutionTypeClass = Class.forName(POLLUTION_TYPE_CLASS);
            Class<?> helperClass = Class.forName(POLLUTION_HELPER_CLASS);
            Class<?> typesClass = Class.forName(POLLUTION_TYPES_CLASS);
            Method changePollution = helperClass.getMethod(
                    "changePollution",
                    Level.class,
                    BlockPos.class,
                    pollutionTypeClass,
                    int.class);
            return new DestroyNeoForgePollutionWriter(
                    changePollution,
                    resolveRegistryEntry(typesClass, "ACID_RAIN"),
                    resolveRegistryEntry(typesClass, "SMOG"),
                    resolveRegistryEntry(typesClass, "GREENHOUSE"),
                    resolveRegistryEntry(typesClass, "OZONE_DEPLETION"));
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new IllegalStateException("verified Destroy pollution API is unavailable", failure);
        }
    }

    void publish(ServerLevel level, PollutionEmission emission, DestroyPollutionProjection projection) {
        Objects.requireNonNull(level, "level");
        PollutionEmission value = Objects.requireNonNull(emission, "emission");
        DestroyPollutionProjection mapped = Objects.requireNonNull(projection, "projection");
        String actualDimension = level.dimension().location().toString();
        if (!actualDimension.equals(value.dimensionId())) {
            throw new IllegalArgumentException("pollution emission dimension does not match target level");
        }
        BlockPos position = BlockPos.containing(value.x(), value.y(), value.z());
        apply(level, position, value.id(), "acid_rain", acidRainType, mapped.acidRain());
        apply(level, position, value.id(), "smog", smogType, mapped.smog());
        apply(level, position, value.id(), "greenhouse", greenhouseType, mapped.greenhouse());
        apply(level, position, value.id(), "ozone_depletion", ozoneDepletionType, mapped.ozoneDepletion());
    }

    private void apply(
            ServerLevel level,
            BlockPos position,
            UUID emissionId,
            String component,
            Object pollutionType,
            double pollutionUnits
    ) {
        int delta = hostDelta(emissionId, component, pollutionUnits);
        if (delta <= 0) {
            return;
        }
        applied.applyOnce(emissionId, component, () -> invokeChange(level, position, pollutionType, delta));
    }

    private void invokeChange(ServerLevel level, BlockPos position, Object pollutionType, int delta) {
        try {
            changePollution.invoke(null, level, position, pollutionType, delta);
        } catch (IllegalAccessException failure) {
            throw new IllegalStateException("Destroy pollution API became inaccessible", failure);
        } catch (InvocationTargetException failure) {
            Throwable cause = failure.getCause();
            if (cause instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if (cause instanceof Error error) {
                throw error;
            }
            throw new IllegalStateException("Destroy pollution publication failed", cause);
        }
    }

    /**
     * Mirrors Destroy's fractional pollution semantics without using nondeterministic retry rolls.
     * One normalized Volcanoes load unit equals one Destroy pollution unit for one atmosphere
     * interval; a fractional unit is rounded with a deterministic Bernoulli decision keyed by the
     * pulse UUID and component, so retrying the same pulse always yields the same delta.
     */
    static int hostDelta(UUID emissionId, String component, double pollutionUnits) {
        Objects.requireNonNull(emissionId, "emissionId");
        Objects.requireNonNull(component, "component");
        if (!Double.isFinite(pollutionUnits) || pollutionUnits < 0.0) {
            throw new IllegalArgumentException("pollutionUnits must be finite and non-negative");
        }
        if (pollutionUnits >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        int whole = (int) Math.floor(pollutionUnits);
        double fraction = pollutionUnits - whole;
        if (fraction <= 0.0) {
            return whole;
        }
        UUID rollId = UUID.nameUUIDFromBytes(
                (emissionId + ":" + component).getBytes(StandardCharsets.UTF_8));
        double roll = (rollId.getMostSignificantBits() >>> 11) * 0x1.0p-53;
        return roll < fraction ? whole + 1 : whole;
    }

    private static Object resolveRegistryEntry(Class<?> typesClass, String fieldName)
            throws ReflectiveOperationException {
        Object registryEntry = typesClass.getField(fieldName).get(null);
        Method get = registryEntry.getClass().getMethod("get");
        return get.invoke(registryEntry);
    }
}
