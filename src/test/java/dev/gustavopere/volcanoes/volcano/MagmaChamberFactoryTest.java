package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class MagmaChamberFactoryTest {
    @Test
    void pureFactoryMatchesTheChamberPersistedByVolcanoManager() {
        UUID id = UUID.fromString("c810b848-cf3c-4d1d-bcdf-307cc41e8c58");
        VolcanoSite site = site(id, VolcanoType.CALDERA, VolcanoState.DORMANT, TectonicContext.HOTSPOT, 0.91);
        VolcanoSavedData data = new VolcanoSavedData();
        data.register(site);

        assertFalse(data.chamber(id).isPresent());
        MagmaChamber projected = MagmaChamberFactory.initialFor(site);
        assertFalse(data.chamber(id).isPresent(), "pure projection must not mutate persistence");

        MagmaChamber persisted = new VolcanoManager(data, tectonics()).ensureChamber(id);
        assertEquals(projected, persisted);
        assertEquals(projected, data.chamber(id).orElseThrow());
    }

    @Test
    void initialProjectionIsDeterministicAndUsesStableSiteIdentity() {
        VolcanoSite first = site(
                UUID.fromString("5ba22a09-e9a1-4d2d-b453-a114fc8fb6fb"),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.ACTIVE,
                TectonicContext.CONVERGENT,
                0.74);
        VolcanoSite same = site(first.persistenceId(), first.type(), first.state(), first.tectonicContext(), first.initialVolcanicPotential());
        VolcanoSite differentIdentity = site(
                UUID.fromString("0b9cf5cc-0dd4-4f91-aac5-d588fa1eb0d6"),
                first.type(),
                first.state(),
                first.tectonicContext(),
                first.initialVolcanicPotential());

        assertEquals(MagmaChamberFactory.initialFor(first), MagmaChamberFactory.initialFor(same));
        org.junit.jupiter.api.Assertions.assertNotEquals(
                MagmaChamberFactory.initialFor(first),
                MagmaChamberFactory.initialFor(differentIdentity),
                "stable site identity contributes deterministic physical jitter");
        assertThrows(NullPointerException.class, () -> MagmaChamberFactory.initialFor(null));
    }

    private static VolcanoSite site(
            UUID id,
            VolcanoType type,
            VolcanoState state,
            TectonicContext context,
            double volcanicPotential
    ) {
        return new VolcanoSite(
                id,
                new BlockPos(8_192, 96, -4_096),
                type,
                state,
                context,
                41L,
                42L,
                volcanicPotential);
    }

    private static TectonicService tectonics() {
        return (seed, x, z) -> new TectonicSample(
                41L,
                42L,
                TectonicContext.HOTSPOT,
                0.5,
                0.9,
                128.0,
                0.0,
                0.0);
    }
}
