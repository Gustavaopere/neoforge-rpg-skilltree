package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class VolcanicGasEmissionContractTest {
    @Test
    void stage03EmissionAuthorityDoesNotInventGasSpeciesFromCoarseMagmaChemistry() {
        assertFalse(Arrays.stream(VolcanicGasEmission.class.getRecordComponents())
                        .anyMatch(component -> component.getName().equals("composition")),
                "Stage03 must publish normalized gas strength/lifecycle, not infer CO2/SO2 chemistry from silica");

        UUID volcanoId = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
        BlockPos source = new BlockPos(12, 90, -4);
        EruptionProfile profile = new EruptionProfile(0.9, 64, 320, 100, 100, 100, 100);
        EruptionSignal lowSilica = signal(
                volcanoId, source, profile, new MagmaComposition(0.45, 0.80), 0.9);
        EruptionSignal highSilica = signal(
                volcanoId, source, profile, new MagmaComposition(0.80, 0.80), 0.9);

        assertEquals(
                VolcanicGasEmissionProjector.project(lowSilica, 1_000L).orElseThrow().normalizedEmissionStrength(),
                VolcanicGasEmissionProjector.project(highSilica, 1_000L).orElseThrow().normalizedEmissionStrength(),
                1.0e-12,
                "silica alone must not fabricate a gas-species or total-emission difference");
    }

    @Test
    void projectorUsesOneStableIdentityAndCanonicalChamberGasAuthority() {
        UUID volcanoId = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
        BlockPos source = new BlockPos(12, 90, -4);
        MagmaChamber chamber = new MagmaChamber(
                new MagmaComposition(0.64, 0.80),
                12.0, 260.0, 0.75, 1200.0, 0.01);
        EruptionProfile profile = new EruptionProfile(0.9, 64, 320, 100, 100, 100, 100);
        EruptionSignal sustained = new EruptionSignal(
                volcanoId, source, EruptionPhase.SUSTAINED, profile, chamber, 0.5, 0.9);
        EruptionSignal waning = new EruptionSignal(
                volcanoId, source, EruptionPhase.WANING, profile, chamber, 0.75, 0.225);

        VolcanicGasEmission strong = VolcanicGasEmissionProjector.project(sustained, 1_000L).orElseThrow();
        VolcanicGasEmission weak = VolcanicGasEmissionProjector.project(waning, 1_020L).orElseThrow();

        assertEquals(strong.sourceId(), weak.sourceId());
        assertEquals(VolcanicGasEmissionProjector.sourceId(volcanoId), strong.sourceId());
        assertEquals(volcanoId, strong.volcanoId());
        assertEquals(source, strong.source());
        assertTrue(strong.normalizedEmissionStrength() > weak.normalizedEmissionStrength());
        assertTrue(strong.normalizedEmissionStrength() <= 1.0);
        assertTrue(strong.radiusBlocks() > 0.0);
        assertTrue(strong.expiresAtTick() > 1_000L);

        EruptionSignal dormant = new EruptionSignal(
                volcanoId, source, EruptionPhase.DORMANT, profile, chamber, 1.0, 0.0);
        assertTrue(VolcanicGasEmissionProjector.project(dormant, 1_040L).isEmpty());
    }

    @Test
    void authoritativeIndexReplaysDeterministicallyAndObserverFailureCannotVetoState() {
        VolcanicGasEmissionIndex index = new VolcanicGasEmissionIndex();
        UUID firstVolcano = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID secondVolcano = UUID.fromString("00000000-0000-0000-0000-000000000001");
        VolcanicGasEmission first = emission(firstVolcano);
        VolcanicGasEmission second = emission(secondVolcano);
        index.upsert(first);
        index.upsert(second);

        List<UUID> replay = new ArrayList<>();
        VolcanicGasEmissionLifecycleSink healthy = new VolcanicGasEmissionLifecycleSink() {
            @Override public void upsert(VolcanicGasEmission emission) { replay.add(emission.sourceId()); }
            @Override public void remove(UUID sourceId) { replay.add(sourceId); }
        };
        assertTrue(index.registerLifecycleSink(healthy));
        assertFalse(index.registerLifecycleSink(healthy));
        assertEquals(List.of(second.sourceId(), first.sourceId()).stream()
                        .sorted((a, b) -> a.toString().compareTo(b.toString())).toList(), replay);

        VolcanicGasEmissionLifecycleSink failing = new VolcanicGasEmissionLifecycleSink() {
            @Override public void upsert(VolcanicGasEmission emission) { throw new IllegalStateException("boom"); }
            @Override public void remove(UUID sourceId) { throw new NoClassDefFoundError("boom"); }
        };
        assertFalse(index.registerLifecycleSink(failing));

        VolcanicGasEmission replacement = new VolcanicGasEmission(
                first.sourceId(), first.volcanoId(), first.source(), EruptionPhase.WANING,
                0.2, first.radiusBlocks(), first.expiresAtTick() + 20);
        assertDoesNotThrow(() -> index.upsert(replacement));
        assertEquals(replacement, index.bySourceId(first.sourceId()).orElseThrow());
        assertDoesNotThrow(() -> index.remove(first.sourceId()));
        assertTrue(index.bySourceId(first.sourceId()).isEmpty());
        assertTrue(index.unregisterLifecycleSink(healthy));
    }

    private static EruptionSignal signal(
            UUID volcanoId,
            BlockPos source,
            EruptionProfile profile,
            MagmaComposition composition,
            double intensity
    ) {
        MagmaChamber chamber = new MagmaChamber(composition, 12.0, 260.0, 0.75, 1200.0, 0.01);
        return new EruptionSignal(
                volcanoId, source, EruptionPhase.SUSTAINED, profile, chamber, 0.5, intensity);
    }

    private static VolcanicGasEmission emission(UUID volcanoId) {
        UUID sourceId = VolcanicGasEmissionProjector.sourceId(volcanoId);
        return new VolcanicGasEmission(
                sourceId,
                volcanoId,
                new BlockPos(0, 80, 0),
                EruptionPhase.SUSTAINED,
                0.5,
                128.0,
                1_400L);
    }
}
