package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshEmissionIndex;
import dev.gustavopere.volcanoes.volcano.AshEmissionLifecycleSink;
import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;
import dev.gustavopere.volcanoes.volcano.EruptionPhase;
import dev.gustavopere.volcanoes.volcano.VolcanicHazardWorldRuntime;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AshAtmosphereLifecycleBehaviorContractTest {
    private static final double EPSILON = 1.0e-9;

    @Test
    void projectionPreservesAshAuthorityAndMapsOnlyParticulateChannels() throws Exception {
        UUID volcanoId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        UUID sourceId = AshPlumeEmission.sourceIdFor(volcanoId);
        AshPlumeEmission emission = new AshPlumeEmission(
                sourceId,
                volcanoId,
                new BlockPos(10, 72, -14),
                EruptionPhase.SUSTAINED,
                0.80,
                0.60,
                0.35,
                48.0,
                160L);

        Class<?> policyType = Class.forName(
                "dev.gustavopere.volcanoes.environment.AshAtmosphereProjectionPolicy");
        Object policy = policyType.getMethod("defaults").invoke(null);
        double maxParticulates = (double) policyType.getMethod("maxParticulatesMgM3").invoke(policy);
        double maxSmoke = (double) policyType.getMethod("maxSmokeMgM3").invoke(policy);

        Class<?> projectionType = Class.forName(
                "dev.gustavopere.volcanoes.environment.AshAtmosphereProjection");
        Method project = projectionType.getMethod(
                "project",
                String.class,
                AshPlumeEmission.class,
                policyType);
        assertTrue(Modifier.isStatic(project.getModifiers()), "projection must be stateless");

        AtmosphericSource source = (AtmosphericSource) project.invoke(
                null,
                "minecraft:overworld",
                emission,
                policy);

        assertEquals(sourceId, source.id(), "Stage 03 source identity is authoritative");
        assertEquals("minecraft:overworld", source.dimensionId());
        assertEquals(emission.radiusBlocks(), source.radiusBlocks(), EPSILON);
        assertEquals(emission.source(), BlockPos.containing(source.x(), source.y(), source.z()));
        assertFalse(source.persistent(), "derived Atmosphere source must never duplicate Stage 03 persistence");
        assertEquals(AtmosphericSourceEvolution.EXTERNAL, source.evolution());

        AtmosphereContribution effective = source.contributionAt(source.x(), source.y(), source.z()).orElseThrow();
        assertEquals(maxParticulates * emission.particulateStrength(), effective.particulatesMgM3(), EPSILON);
        assertEquals(maxSmoke * emission.smokeStrength(), effective.smokeMgM3(), EPSILON);

        assertEquals(0.0, effective.pressureDeltaAtm(), EPSILON);
        assertEquals(0.0, effective.oxygenFractionDelta(), EPSILON);
        assertEquals(0.0, effective.carbonDioxideFraction(), EPSILON);
        assertEquals(0.0, effective.sulfurDioxidePpm(), EPSILON);
        assertEquals(0.0, effective.toxicGasPpm(), EPSILON);
        assertEquals(0.0, effective.relativeHumidityDelta(), EPSILON);
        assertEquals(0.0, effective.thermalModifierDeltaC(), EPSILON);
        assertEquals(0.0, effective.oxygenDisplacementFraction(), EPSILON);
    }

    @Test
    void observerRegistrationReplaysCurrentAshAndTracksUpsertRemoveWithoutSecondEruptionConsumer() throws Exception {
        Method register = VolcanicHazardWorldRuntime.class.getMethod(
                "registerAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);
        Method unregister = VolcanicHazardWorldRuntime.class.getMethod(
                "unregisterAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);

        AshEmissionIndex index = VolcanicHazardWorldRuntime.ashIndex();
        UUID volcanoId = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        UUID sourceId = AshPlumeEmission.sourceIdFor(volcanoId);
        index.remove(sourceId);

        RecordingSink liveObserver = new RecordingSink();
        RecordingSink replayObserver = new RecordingSink();
        boolean liveRegistered = false;
        boolean replayRegistered = false;
        try {
            liveRegistered = (boolean) register.invoke(null, liveObserver);
            assertTrue(liveRegistered);
            assertFalse((boolean) register.invoke(null, liveObserver), "duplicate registration must be idempotent");

            AshPlumeEmission first = emission(sourceId, volcanoId, 0.40, 0.25, 0.20, 24.0);
            index.upsert(first);
            assertEquals(first, liveObserver.upserts.get(sourceId), "live Stage 03 lifecycle must be observed");

            replayRegistered = (boolean) register.invoke(null, replayObserver);
            assertTrue(replayRegistered);
            assertEquals(first, replayObserver.upserts.get(sourceId), "registration must replay current authoritative ash");

            AshPlumeEmission replacement = emission(sourceId, volcanoId, 0.85, 0.70, 0.55, 56.0);
            index.upsert(replacement);
            assertEquals(replacement, liveObserver.upserts.get(sourceId));
            assertEquals(replacement, replayObserver.upserts.get(sourceId));

            index.remove(sourceId);
            assertTrue(liveObserver.removes.contains(sourceId));
            assertTrue(replayObserver.removes.contains(sourceId));

            assertTrue((boolean) unregister.invoke(null, liveObserver));
            liveRegistered = false;
            assertFalse((boolean) unregister.invoke(null, liveObserver), "duplicate unregister must be idempotent");

            AshPlumeEmission afterUnregister = emission(sourceId, volcanoId, 0.65, 0.45, 0.40, 40.0);
            int priorLiveUpserts = liveObserver.upsertCount;
            index.upsert(afterUnregister);
            assertEquals(priorLiveUpserts, liveObserver.upsertCount, "unregistered observer must receive no new lifecycle events");
            assertEquals(afterUnregister, replayObserver.upserts.get(sourceId));
        } finally {
            index.remove(sourceId);
            if (liveRegistered) {
                unregister.invoke(null, liveObserver);
            }
            if (replayRegistered) {
                unregister.invoke(null, replayObserver);
            }
        }
    }

    @Test
    void observerFailuresNeverCorruptAuthoritativeAshOrStarveHealthyObservers() throws Exception {
        Method register = VolcanicHazardWorldRuntime.class.getMethod(
                "registerAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);
        Method unregister = VolcanicHazardWorldRuntime.class.getMethod(
                "unregisterAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);

        AshEmissionIndex index = VolcanicHazardWorldRuntime.ashIndex();
        UUID volcanoId = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
        UUID sourceId = AshPlumeEmission.sourceIdFor(volcanoId);
        index.remove(sourceId);

        AshPlumeEmission first = emission(sourceId, volcanoId, 0.55, 0.40, 0.30, 32.0);
        index.upsert(first);

        RecordingSink healthy = new RecordingSink();
        AshEmissionLifecycleSink replayFailing = new AshEmissionLifecycleSink() {
            @Override
            public void upsert(AshPlumeEmission emission) {
                throw new IllegalStateException("simulated optional atmosphere replay failure");
            }

            @Override
            public void remove(UUID id) {
            }
        };
        AshEmissionLifecycleSink liveFailing = new AshEmissionLifecycleSink() {
            private boolean replaySeen;

            @Override
            public void upsert(AshPlumeEmission emission) {
                if (!replaySeen) {
                    replaySeen = true;
                    return;
                }
                throw new IllegalStateException("simulated optional atmosphere live failure");
            }

            @Override
            public void remove(UUID id) {
                throw new NoSuchMethodError("simulated optional atmosphere linkage drift");
            }
        };

        boolean healthyRegistered = false;
        boolean replayFailingRegistered = false;
        boolean liveFailingRegistered = false;
        try {
            healthyRegistered = (boolean) register.invoke(null, healthy);
            assertTrue(healthyRegistered);
            assertEquals(first, healthy.upserts.get(sourceId), "healthy observer must receive restart/current-state replay");

            // A failing replay must not escape or corrupt authority. Implementations may either
            // keep the observer registered or quarantine/rollback it, so the boolean is not part
            // of this safety contract.
            replayFailingRegistered = (boolean) register.invoke(null, replayFailing);
            assertEquals(first, index.bySourceId(sourceId).orElseThrow(),
                    "failing optional replay must not remove or replace authoritative ash");
            if (replayFailingRegistered) {
                unregister.invoke(null, replayFailing);
                replayFailingRegistered = false;
            }

            liveFailingRegistered = (boolean) register.invoke(null, liveFailing);
            assertTrue(liveFailingRegistered, "fixture observer must survive its successful replay");

            AshPlumeEmission replacement = emission(sourceId, volcanoId, 0.90, 0.80, 0.65, 60.0);
            index.upsert(replacement);
            assertEquals(replacement, index.bySourceId(sourceId).orElseThrow(),
                    "observer RuntimeException must not veto authoritative Stage 03 upsert");
            assertEquals(replacement, healthy.upserts.get(sourceId),
                    "one failing observer must not starve later healthy observers");

            index.remove(sourceId);
            assertTrue(index.bySourceId(sourceId).isEmpty(),
                    "observer LinkageError must not veto authoritative Stage 03 removal");
            assertTrue(healthy.removes.contains(sourceId),
                    "healthy observers must still receive removal after another observer fails");
        } finally {
            if (liveFailingRegistered) {
                unregister.invoke(null, liveFailing);
            }
            if (replayFailingRegistered) {
                unregister.invoke(null, replayFailing);
            }
            if (healthyRegistered) {
                unregister.invoke(null, healthy);
            }
            index.remove(sourceId);
        }
    }

    @Test
    void registrationReplayUsesCanonicalSourceOrder() throws Exception {
        Method register = VolcanicHazardWorldRuntime.class.getMethod(
                "registerAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);
        Method unregister = VolcanicHazardWorldRuntime.class.getMethod(
                "unregisterAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);

        AshEmissionIndex index = VolcanicHazardWorldRuntime.ashIndex();
        List<UUID> volcanoIds = List.of(
                UUID.fromString("10000000-0000-0000-0000-000000000003"),
                UUID.fromString("10000000-0000-0000-0000-000000000001"),
                UUID.fromString("10000000-0000-0000-0000-000000000002"));
        List<UUID> sourceIds = volcanoIds.stream().map(AshPlumeEmission::sourceIdFor).toList();
        Set<UUID> expectedSources = new HashSet<>(sourceIds);
        sourceIds.forEach(index::remove);

        RecordingSink replayObserver = new RecordingSink();
        boolean registered = false;
        try {
            for (int i = 0; i < volcanoIds.size(); i++) {
                UUID volcanoId = volcanoIds.get(i);
                UUID sourceId = sourceIds.get(i);
                index.upsert(emission(sourceId, volcanoId, 0.50 + i * 0.10, 0.30, 0.20, 32.0));
            }

            registered = (boolean) register.invoke(null, replayObserver);
            assertTrue(registered);

            List<UUID> actualOrder = replayObserver.upsertOrder.stream()
                    .filter(expectedSources::contains)
                    .toList();
            List<UUID> canonicalOrder = sourceIds.stream()
                    .sorted(Comparator.comparing(UUID::toString))
                    .toList();
            assertEquals(
                    canonicalOrder,
                    actualOrder,
                    "restart/current-state replay must be deterministic so capacity pressure cannot change which ash sources are admitted");
        } finally {
            if (registered) {
                unregister.invoke(null, replayObserver);
            }
            sourceIds.forEach(index::remove);
        }
    }

    private static AshPlumeEmission emission(
            UUID sourceId,
            UUID volcanoId,
            double load,
            double particulates,
            double smoke,
            double radius
    ) {
        return new AshPlumeEmission(
                sourceId,
                volcanoId,
                new BlockPos(4, 80, 4),
                EruptionPhase.SUSTAINED,
                load,
                particulates,
                smoke,
                radius,
                120L);
    }

    private static final class RecordingSink implements AshEmissionLifecycleSink {
        private final Map<UUID, AshPlumeEmission> upserts = new HashMap<>();
        private final Set<UUID> removes = new HashSet<>();
        private final List<UUID> upsertOrder = new ArrayList<>();
        private int upsertCount;

        @Override
        public void upsert(AshPlumeEmission emission) {
            upserts.put(emission.sourceId(), emission);
            removes.remove(emission.sourceId());
            upsertOrder.add(emission.sourceId());
            upsertCount++;
        }

        @Override
        public void remove(UUID sourceId) {
            upserts.remove(sourceId);
            removes.add(sourceId);
        }
    }
}
