package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EntityPreviewFactoryJUnitTest {
    @Test
    void vanillaLivingEntityUsesConservativeDefaultPolicy() {
        CompendiumEntryId pig = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:pig");

        assertEquals(EntityPreviewFactory.Policy.VANILLA_DEFAULT, EntityPreviewFactory.policyFor(pig));
    }

    @Test
    void moddedEntityIsBlockedUntilExplicitAdapterOptsItIn() {
        CompendiumEntryId stag = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "preview_policy_test:stag");
        ResourceLocation key = ResourceLocation.fromNamespaceAndPath("preview_policy_test", "stag");

        assertEquals(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(stag));
        EntityPreviewFactory.registerAdapter(key, level -> null);
        assertEquals(EntityPreviewFactory.Policy.ADAPTER, EntityPreviewFactory.policyFor(stag));
    }

    @Test
    void explicitBlacklistOverridesAdapterAndCanBeRemoved() {
        CompendiumEntryId entity = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "preview_blacklist_test:entity");
        ResourceLocation key = ResourceLocation.fromNamespaceAndPath("preview_blacklist_test", "entity");
        EntityPreviewFactory.registerAdapter(key, level -> null);

        EntityPreviewFactory.blacklist(key);
        assertEquals(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(entity));

        EntityPreviewFactory.removeBlacklist(key);
        assertEquals(EntityPreviewFactory.Policy.ADAPTER, EntityPreviewFactory.policyFor(entity));
    }

    @Test
    void quarantineFailsClosedUntilAnAdapterExplicitlyRecoversTheEntry() {
        CompendiumEntryId entity = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "preview_quarantine_test:entity");
        ResourceLocation key = ResourceLocation.fromNamespaceAndPath("preview_quarantine_test", "entity");

        EntityPreviewFactory.quarantine(entity);
        assertTrue(EntityPreviewFactory.quarantined(key));
        assertEquals(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(entity));

        EntityPreviewFactory.registerAdapter(key, level -> null);
        assertFalse(EntityPreviewFactory.quarantined(key));
        assertEquals(EntityPreviewFactory.Policy.ADAPTER, EntityPreviewFactory.policyFor(entity));
    }

    @Test
    void nonEntityEntriesNeverEnterEntityPreviewPipeline() {
        CompendiumEntryId biome = CompendiumEntryId.of(CompendiumEntryKind.BIOME, "minecraft:plains");

        assertEquals(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(biome));
        assertEquals(EntityPreviewFactory.Failure.NOT_ENTITY, EntityPreviewFactory.create(biome, null).failure());
    }

    @Test
    void failedResultCannotPretendToBeReady() {
        EntityPreviewFactory.Result failed = EntityPreviewFactory.Result.failed(EntityPreviewFactory.Failure.BLOCKED);

        assertFalse(failed.ready());
        assertEquals(EntityPreviewFactory.Failure.BLOCKED, failed.failure());
        assertThrows(
            IllegalArgumentException.class,
            () -> new EntityPreviewFactory.Result(null, EntityPreviewFactory.Failure.NONE)
        );
    }
}
