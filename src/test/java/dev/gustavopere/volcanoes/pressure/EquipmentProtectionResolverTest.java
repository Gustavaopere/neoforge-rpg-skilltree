package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

final class EquipmentProtectionResolverTest {
    @Test
    void mixedEquipmentRemainsModularInsteadOfMakingOneItemUniversal() {
        EquipmentProtectionAdapter breathingGear = context -> List.of(
                ProtectionContribution.passive("helmet-filter", Map.of(
                        ProtectionCapability.PARTICULATE_FILTER, 1.0,
                        ProtectionCapability.ACID_GAS_FILTER, 0.75)),
                ProtectionContribution.passive("backtank-air", Map.of(
                        ProtectionCapability.OXYGEN_SUPPLY, 1.0)));
        EquipmentProtectionAdapter pressureSuit = context -> List.of(
                ProtectionContribution.passive("pressure-suit", Map.of(
                        ProtectionCapability.PRESSURE_RATING, 2.5,
                        ProtectionCapability.THERMAL_PROTECTION, 0.5)));

        EquipmentProtectionResolver resolver = new EquipmentProtectionResolver(List.of(breathingGear, pressureSuit));
        ProtectionSnapshot snapshot = resolver.resolve(context());

        assertEquals(1.0, snapshot.rating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-9);
        assertEquals(1.0, snapshot.rating(ProtectionCapability.PARTICULATE_FILTER), 1.0e-9);
        assertEquals(0.75, snapshot.rating(ProtectionCapability.ACID_GAS_FILTER), 1.0e-9);
        assertEquals(0.0, snapshot.rating(ProtectionCapability.TOXIC_GAS_FILTER), 1.0e-9);
        assertEquals(2.5, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
        assertEquals(0.5, snapshot.rating(ProtectionCapability.THERMAL_PROTECTION), 1.0e-9);
    }

    @Test
    void adaptersRegisteredAfterConstructionBecomeVisibleWithoutRebuildingConsumer() {
        EquipmentProtectionResolver resolver = new EquipmentProtectionResolver(List.of());
        assertEquals(0.0, resolver.resolve(context()).rating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-9);

        resolver.register(context -> List.of(
                ProtectionContribution.passive(
                        "late-oxygen-adapter",
                        Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0))));

        assertEquals(1.0, resolver.resolve(context()).rating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-9);
    }

    @Test
    void failingOptionalAdapterFailsClosedWithoutSuppressingIndependentProtection() {
        EquipmentProtectionAdapter broken = context -> {
            throw new IllegalStateException("simulated optional-mod API mismatch");
        };
        EquipmentProtectionAdapter independentSuit = context -> List.of(
                ProtectionContribution.passive(
                        "independent-pressure-suit",
                        Map.of(ProtectionCapability.PRESSURE_RATING, 2.0)));

        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of(broken, independentSuit)).resolve(context());

        assertEquals(0.0, snapshot.rating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-9,
                "a failing adapter must not invent protection");
        assertEquals(2.0, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 1.0e-9,
                "independent valid equipment must remain usable");
    }

    @Test
    void hostResolvedConsumableContributionParticipatesWithoutNativeHandleInCoreContext() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution hostBacktank = ProtectionContribution.consumable(
                "host:create_backtank",
                Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });
        EquipmentProtectionAdapter passiveSuit = context -> List.of(
                ProtectionContribution.passive(
                        "tag:pressure-suit",
                        Map.of(ProtectionCapability.PRESSURE_RATING, 2.0)));
        EquipmentProtectionResolver resolver = new EquipmentProtectionResolver(List.of(passiveSuit));

        ProtectionSnapshot snapshot = resolver.resolve(context(), List.of(hostBacktank));
        ProtectionUseSession update = snapshot.beginUpdate();

        assertEquals(1.0, snapshot.rating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-9);
        assertEquals(2.0, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
        assertTrue(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertTrue(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertEquals(1, debits.get(), "host resource must still be consumed exactly once per update");
    }

    @Test
    void passiveContributionWinsEqualRatingWithoutSpendingConsumableResource() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution consumableSuit = ProtectionContribution.consumable(
                "host:consumable-pressure-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });
        EquipmentProtectionAdapter passiveSuit = context -> List.of(
                ProtectionContribution.passive(
                        "tag:passive-pressure-suit",
                        Map.of(ProtectionCapability.PRESSURE_RATING, 2.0)));
        EquipmentProtectionResolver resolver = new EquipmentProtectionResolver(List.of(passiveSuit));

        ProtectionSnapshot snapshot = resolver.resolve(context(), List.of(consumableSuit));
        ProtectionUseSession update = snapshot.beginUpdate();

        assertEquals(2.0, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
        assertTrue(update.activate(ProtectionCapability.PRESSURE_RATING));
        assertEquals(0, debits.get(), "an equal passive source must win to avoid pointless resource consumption");
    }

    @Test
    void changingDemandWithinOneUpdateReevaluatesCandidatesWithoutDoubleDebit() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution strongConsumable = ProtectionContribution.consumable(
                "host:strong-suit",
                "host:shared-pressure-resource",
                Map.of(ProtectionCapability.PRESSURE_RATING, 3.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });
        ProtectionContribution weakPassive = ProtectionContribution.passive(
                "tag:weak-passive-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 1.0));
        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of())
                .resolve(context(), List.of(strongConsumable, weakPassive));
        ProtectionUseSession update = snapshot.beginUpdate();

        assertEquals(1.0,
                update.activatedRating(ProtectionCapability.PRESSURE_RATING, 1.0),
                1.0e-9,
                "a passive source that fully covers the first demand should avoid a debit");
        assertEquals(0, debits.get());

        assertEquals(3.0,
                update.activatedRating(ProtectionCapability.PRESSURE_RATING, 2.0),
                1.0e-9,
                "a later higher demand must re-evaluate candidates instead of reusing the low-demand rating");
        assertEquals(1, debits.get());

        assertEquals(3.0,
                update.activatedRating(ProtectionCapability.PRESSURE_RATING, 2.5),
                1.0e-9);
        assertEquals(1, debits.get(), "re-evaluation must still debit the physical resource at most once");
    }

    @Test
    void respiratorFiltersPollutantsButDoesNotInventOxygenOrPressureProtection() {
        EquipmentProtectionAdapter respirator = context -> List.of(
                ProtectionContribution.passive("respirator", Map.of(
                        ProtectionCapability.PARTICULATE_FILTER, 1.0,
                        ProtectionCapability.ACID_GAS_FILTER, 1.0,
                        ProtectionCapability.TOXIC_GAS_FILTER, 1.0)));

        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of(respirator)).resolve(context());

        assertEquals(0.0, snapshot.rating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-9);
        assertEquals(0.0, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
        assertEquals(1.0, snapshot.rating(ProtectionCapability.TOXIC_GAS_FILTER), 1.0e-9);
    }

    @Test
    void consumableSourceIsDebitedExactlyOncePerUpdate() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionResourceConsumer airDebit = () -> {
            debits.incrementAndGet();
            return true;
        };
        EquipmentProtectionAdapter backtank = context -> List.of(
                ProtectionContribution.consumable(
                        "backtank-air",
                        Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                        airDebit));

        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of(backtank)).resolve(context());

        ProtectionUseSession firstUpdate = snapshot.beginUpdate();
        assertTrue(firstUpdate.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertTrue(firstUpdate.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertEquals(1, debits.get());

        ProtectionUseSession secondUpdate = snapshot.beginUpdate();
        assertTrue(secondUpdate.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertEquals(2, debits.get());
    }

    @Test
    void failedResourceDebitFailsClosedAndIsNotRetriedWithinSameUpdate() {
        AtomicInteger debits = new AtomicInteger();
        EquipmentProtectionAdapter emptyTank = context -> List.of(
                ProtectionContribution.consumable(
                        "empty-backtank",
                        Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                        () -> {
                            debits.incrementAndGet();
                            return false;
                        }));

        ProtectionUseSession update = new EquipmentProtectionResolver(List.of(emptyTank))
                .resolve(context())
                .beginUpdate();

        assertFalse(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertFalse(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertEquals(1, debits.get());
    }

    @Test
    void callTimeLinkageFailureFailsClosedAndPhysicalDebitIsAttemptedOnlyOncePerUpdate() {
        AtomicInteger debitAttempts = new AtomicInteger();
        ProtectionContribution brokenHostResource = ProtectionContribution.consumable(
                "host:broken-pressure-resource",
                "host:shared-pressure-resource",
                Map.of(ProtectionCapability.PRESSURE_RATING, 3.0),
                () -> {
                    debitAttempts.incrementAndGet();
                    throw new NoSuchMethodError("simulated optional host API drift during debit");
                });
        ProtectionContribution passiveFallback = ProtectionContribution.passive(
                "tag:passive-pressure-fallback",
                Map.of(ProtectionCapability.PRESSURE_RATING, 1.0));
        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of())
                .resolve(context(), List.of(brokenHostResource, passiveFallback));
        ProtectionUseSession update = snapshot.beginUpdate();

        assertEquals(1.0,
                update.activatedRating(ProtectionCapability.PRESSURE_RATING, 2.0),
                1.0e-9,
                "call-time host linkage failure must fail closed and allow an independent passive fallback");
        assertEquals(1.0,
                update.activatedRating(ProtectionCapability.PRESSURE_RATING, 2.5),
                1.0e-9,
                "the failed physical resource must remain memoized as unavailable for this update");
        assertEquals(1, debitAttempts.get(),
                "one physical resource key may be attempted at most once in the shared update transaction");
    }

    @Test
    void invalidOrDuplicateCapabilitiesCannotCreateNegativeProtection() {
        assertThrows(IllegalArgumentException.class, () ->
                ProtectionContribution.passive("bad", Map.of(ProtectionCapability.PRESSURE_RATING, -1.0)));
        assertThrows(IllegalArgumentException.class, () ->
                ProtectionContribution.passive("nan", Map.of(ProtectionCapability.PRESSURE_RATING, Double.NaN)));
    }

    private static EquipmentProtectionContext context() {
        return new EquipmentProtectionContext(
                UUID.randomUUID(),
                List.of(
                        new EquippedItemView("head", "example:helmet", Set.of("volcanoes:pressure/particulate_filter")),
                        new EquippedItemView("back", "example:backtank", Set.of("volcanoes:pressure/oxygen_supply"))),
                Optional.empty());
    }
}
