package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ExistingWorldVolcanoAdminSessionTest {
    private static final ResourceKey<Level> DIMENSION = ResourceKey.create(
            ResourceKey.createRegistryKey(ResourceLocation.parse("minecraft:dimension")),
            ResourceLocation.parse("minecraft:overworld"));

    @Test
    void applyRequiresARealUnexpiredPreviewAndConsumesItsToken() {
        ExistingWorldVolcanoAdminSession session = new ExistingWorldVolcanoAdminSession(1_200L);
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site();

        assertEquals(
                ExistingWorldVolcanoAdminSession.ApplyResult.NO_PREVIEW,
                session.apply("not-issued", DIMENSION, 1234L, 100L, site, data));
        assertEquals(0, data.size());

        ExistingWorldVolcanoAdminSession.Preview preview = session.preview(
                DIMENSION,
                1234L,
                100L,
                site);
        assertFalse(preview.token().isBlank());
        assertEquals(0, data.size(), "preview must be read-only");

        assertEquals(
                ExistingWorldVolcanoAdminSession.ApplyResult.REGISTERED,
                session.apply(preview.token(), DIMENSION, 1234L, 101L, site, data));
        assertTrue(data.get(site.persistenceId()).isPresent());

        assertEquals(
                ExistingWorldVolcanoAdminSession.ApplyResult.NO_PREVIEW,
                session.apply(preview.token(), DIMENSION, 1234L, 102L, site, data),
                "preview token must be one-shot");
    }

    @Test
    void previewCannotBeAppliedToAnotherWorldOrAfterExpiry() {
        ExistingWorldVolcanoAdminSession session = new ExistingWorldVolcanoAdminSession(10L);
        VolcanoSite site = site();

        ExistingWorldVolcanoAdminSession.Preview wrongWorld = session.preview(DIMENSION, 4L, 50L, site);
        assertEquals(
                ExistingWorldVolcanoAdminSession.ApplyResult.CONTEXT_CHANGED,
                session.apply(wrongWorld.token(), DIMENSION, 5L, 51L, site, new VolcanoSavedData()));

        ExistingWorldVolcanoAdminSession.Preview expired = session.preview(DIMENSION, 4L, 50L, site);
        assertEquals(
                ExistingWorldVolcanoAdminSession.ApplyResult.EXPIRED,
                session.apply(expired.token(), DIMENSION, 4L, 61L, site, new VolcanoSavedData()));
    }

    @Test
    void applyingMetadataNeverInvokesTerrainShaping() {
        ExistingWorldVolcanoAdminSession session = new ExistingWorldVolcanoAdminSession(20L);
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site();
        ExistingWorldVolcanoAdminSession.Preview preview = session.preview(DIMENSION, 77L, 5L, site);

        assertEquals(
                ExistingWorldVolcanoAdminSession.ApplyResult.REGISTERED,
                session.apply(preview.token(), DIMENSION, 77L, 6L, site, data));
        assertEquals(1, data.size());
        assertFalse(data.chamber(site.persistenceId()).isPresent(),
                "existing-world registration must only add site metadata, not synthesize lifecycle/terrain state");
    }

    private static VolcanoSite site() {
        return new VolcanoSite(
                UUID.fromString("162e2987-0138-4c43-9153-9e689998aac8"),
                new BlockPos(256, 90, 256),
                VolcanoType.SHIELD,
                VolcanoState.DORMANT,
                TectonicContext.DIVERGENT,
                9L,
                10L,
                0.76);
    }
}
