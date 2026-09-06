package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.environment.PollutionAdapter;
import dev.gustavopere.volcanoes.environment.PollutionEmission;
import dev.gustavopere.volcanoes.environment.PollutionLoad;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DestroyCompatContractTest {
    @Test
    void adapterIsPinnedToVerifiedDestroyArtifact() {
        assertEquals("destroy", DestroyCompat.MOD_ID);
        assertEquals("0.4.1", DestroyCompat.VERIFIED_ARTIFACT_VERSION);
    }

    @Test
    void absentOrMismatchedHostNeverResolvesInstaller() {
        AtomicInteger installs = new AtomicInteger();
        assertFalse(DestroyCompat.installForState(false, false, installs::incrementAndGet));
        assertFalse(DestroyCompat.installForState(true, false, installs::incrementAndGet));
        assertEquals(0, installs.get());
    }

    @Test
    void exactHostInstallsOnceAndOptionalFailuresFailClosed() {
        AtomicInteger installs = new AtomicInteger();
        assertTrue(DestroyCompat.installForState(true, true, installs::incrementAndGet));
        assertEquals(1, installs.get());
        assertFalse(DestroyCompat.installForState(true, true, () -> { throw new IllegalStateException("host failure"); }));
        assertFalse(DestroyCompat.installForState(true, true, () -> { throw new NoClassDefFoundError("host linkage failure"); }));
    }

    @Test
    void aggregateOnlyDestroyAdapterNeverPretendsItsReadbackIsSourceExclusive() {
        PollutionAdapter adapter = new DestroyPollutionAdapter((emission, projection) -> {});
        assertTrue(adapter.isAuthoritative());
        Optional<PollutionLoad> external = adapter.sampleExternalOnly("minecraft:overworld", 0.0, 64.0, 0.0);
        assertTrue(external.isEmpty());
    }

    @Test
    void oneEmissionIsPublishedOnceAndParticulatesAreNotSilentlyMapped() {
        AtomicInteger writes = new AtomicInteger();
        DestroyPollutionAdapter adapter = new DestroyPollutionAdapter((emission, projection) -> {
            writes.incrementAndGet();
            assertEquals(0.2, projection.acidRain(), 1.0e-12);
            assertEquals(0.3, projection.smog(), 1.0e-12);
            assertEquals(0.4, projection.greenhouse(), 1.0e-12);
            assertEquals(0.5, projection.ozoneDepletion(), 1.0e-12);
            assertFalse(projection.mapsParticulates());
        });
        adapter.publish(new PollutionEmission(
                UUID.randomUUID(), "minecraft:overworld", 1.0, 64.0, 2.0,
                new PollutionLoad(0.2, 0.9, 0.3, 0.4, 0.5)));
        assertEquals(1, writes.get());
    }

    @Test
    void concreteAdapterDoesNotReadAggregateDestroyPollutionBackAsExternalOnly() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/destroy/DestroyPollutionAdapter.java"));
        assertFalse(source.contains("getPollution("));
        assertFalse(source.contains("getPollutionProportion("));
        assertFalse(source.contains("Class.forName"));
        assertFalse(source.contains("java.lang.reflect"));
    }
}
