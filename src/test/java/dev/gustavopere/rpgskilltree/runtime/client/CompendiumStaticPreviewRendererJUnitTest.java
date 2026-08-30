package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.client.render.CompendiumStaticPreviewPolicy;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CompendiumStaticPreviewRendererJUnitTest {
    @Test
    void resolvesBotanicalRegistryBlockToItsItemWhenAvailable() {
        var request = CompendiumStaticPreviewPolicy.requestFor(
            CompendiumEntryId.of(CompendiumEntryKind.FLORA, "minecraft:dandelion")
        );

        var resolved = CompendiumStaticPreviewRenderer.resolve(request);

        assertEquals(CompendiumStaticPreviewRenderer.Result.ITEM, resolved.result());
        assertTrue(resolved.itemStack().isPresent());
        assertEquals(Items.DANDELION, resolved.itemStack().orElseThrow().getItem());
    }

    @Test
    void blockWithoutItemFailsSoftInsteadOfInventingVisual() {
        var request = CompendiumStaticPreviewPolicy.requestFor(
            CompendiumEntryId.of(CompendiumEntryKind.BLOCK_FEATURE, "minecraft:water")
        );

        var resolved = CompendiumStaticPreviewRenderer.resolve(request);

        assertEquals(CompendiumStaticPreviewRenderer.Result.NO_ITEM, resolved.result());
        assertTrue(resolved.itemStack().isEmpty());
    }

    @Test
    void metadataOnlyEntriesNeverResolveRegistryItems() {
        var request = CompendiumStaticPreviewPolicy.requestFor(
            CompendiumEntryId.of(CompendiumEntryKind.STRUCTURE, "minecraft:village_plains")
        );

        var resolved = CompendiumStaticPreviewRenderer.resolve(request);

        assertEquals(CompendiumStaticPreviewRenderer.Result.METADATA_ONLY, resolved.result());
        assertTrue(resolved.itemStack().isEmpty());
    }

    @Test
    void entityEntriesRemainOutsideStaticRenderer() {
        var request = CompendiumStaticPreviewPolicy.requestFor(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:pig")
        );

        var resolved = CompendiumStaticPreviewRenderer.resolve(request);

        assertEquals(CompendiumStaticPreviewRenderer.Result.NOT_APPLICABLE, resolved.result());
        assertFalse(resolved.itemStack().isPresent());
    }

    @Test
    void missingRegistryBlockFailsSoft() {
        var request = CompendiumStaticPreviewPolicy.requestFor(
            CompendiumEntryId.of(CompendiumEntryKind.FLORA, "missing_preview_test:not_registered")
        );

        var resolved = CompendiumStaticPreviewRenderer.resolve(request);

        assertEquals(CompendiumStaticPreviewRenderer.Result.UNKNOWN_BLOCK, resolved.result());
        assertTrue(resolved.itemStack().isEmpty());
    }
}
