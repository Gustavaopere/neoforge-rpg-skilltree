package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class RespirationProtectionProviderContractTest {
    @Test
    void additionalOxygenSupplyCombinesWithoutErasingTaggedFiltration() {
        RespirationProtection tagged = TaggedRespirationProtectionProvider.fromMatches(true, false, true);
        RespirationProtection oxygenSupply = RespirationProtection.of(0.0, 0.0, 0.0, 0.21);

        RespirationProtection combined = tagged.combine(oxygenSupply);

        assertEquals(1.0, combined.particulateFilterEfficiency(), 0.0);
        assertEquals(0.0, combined.acidGasFilterEfficiency(), 0.0);
        assertEquals(1.0, combined.toxicGasFilterEfficiency(), 0.0);
        assertEquals(0.21, combined.oxygenSupplyPartialPressureAtm(), 0.0);
    }

    @Test
    void protectionCombinationUsesBestCapabilityInsteadOfAdditiveDoubleCounting() {
        RespirationProtection first = RespirationProtection.of(0.60, 0.20, 0.30, 0.15);
        RespirationProtection second = RespirationProtection.of(0.50, 0.80, 0.10, 0.21);

        RespirationProtection combined = first.combine(second);

        assertEquals(0.60, combined.particulateFilterEfficiency(), 0.0);
        assertEquals(0.80, combined.acidGasFilterEfficiency(), 0.0);
        assertEquals(0.30, combined.toxicGasFilterEfficiency(), 0.0);
        assertEquals(0.21, combined.oxygenSupplyPartialPressureAtm(), 0.0);
    }

    @Test
    void runtimeExposesNeutralAdditionalProtectionInstallation() throws Exception {
        assertEquals(
                void.class,
                AtmosphereRuntime.class.getMethod(
                        "installRespirationProtectionProvider",
                        RespirationProtectionProvider.class).getReturnType());
    }

    @Test
    void optionalProtectionLinkageFailureFallsBackToNoAdditionalProtection() {
        RespirationProtection resolved = AtmosphereRuntime.safeInstalledProtection(
                (entity, state) -> {
                    throw new NoClassDefFoundError("simulated optional Create/pressure linkage failure");
                },
                null,
                AtmosphereState.standardOverworld());

        assertEquals(RespirationProtection.NONE, resolved);
    }

    @Test
    void optionalProtectionRuntimeFailureFallsBackToNoAdditionalProtection() {
        RespirationProtection resolved = AtmosphereRuntime.safeInstalledProtection(
                (entity, state) -> {
                    throw new IllegalStateException("simulated optional provider failure");
                },
                null,
                AtmosphereState.standardOverworld());

        assertEquals(RespirationProtection.NONE, resolved);
    }

    @Test
    void optionalProtectionNullOutputFallsBackToNoAdditionalProtection() {
        RespirationProtection resolved = AtmosphereRuntime.safeInstalledProtection(
                (entity, state) -> null,
                null,
                AtmosphereState.standardOverworld());

        assertEquals(RespirationProtection.NONE, resolved);
    }

    @Test
    void optionalFailureCannotEraseAlreadyResolvedTaggedFiltration() {
        RespirationProtection tagged = TaggedRespirationProtectionProvider.fromMatches(true, true, true);
        RespirationProtection optional = AtmosphereRuntime.safeInstalledProtection(
                (entity, state) -> {
                    throw new IllegalStateException("simulated optional provider failure");
                },
                null,
                AtmosphereState.standardOverworld());

        RespirationProtection combined = tagged.combine(optional);

        assertEquals(1.0, combined.particulateFilterEfficiency(), 0.0);
        assertEquals(1.0, combined.acidGasFilterEfficiency(), 0.0);
        assertEquals(1.0, combined.toxicGasFilterEfficiency(), 0.0);
        assertEquals(0.0, combined.oxygenSupplyPartialPressureAtm(), 0.0);
    }

    @Test
    void oneOptionalFailureDoesNotCorruptProviderForLaterResolution() {
        AtomicInteger calls = new AtomicInteger();
        RespirationProtectionProvider provider = (entity, state) -> {
            if (calls.incrementAndGet() == 1) {
                throw new IllegalStateException("transient optional provider failure");
            }
            return RespirationProtection.of(0.0, 0.0, 0.0, 0.21);
        };

        assertEquals(
                RespirationProtection.NONE,
                AtmosphereRuntime.safeInstalledProtection(provider, null, AtmosphereState.standardOverworld()));
        assertEquals(
                0.21,
                AtmosphereRuntime.safeInstalledProtection(provider, null, AtmosphereState.standardOverworld())
                        .oxygenSupplyPartialPressureAtm(),
                0.0);
        assertEquals(2, calls.get());
    }
}
