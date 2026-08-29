package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Objects;

public final class EntityPreviewFactoryTest {
    public static void main(String[] args) {
        vanillaEntityUsesConservativeDefaultPolicy();
        moddedEntityRequiresExplicitAdapter();
        explicitBlacklistOverridesAdapter();
        quarantineFailsClosedUntilAdapterRecovery();
        nonEntityEntriesRemainBlocked();
        interactionStateResetsAndClamps();
        System.out.println("EntityPreviewFactoryTest: PASS");
    }

    private static void vanillaEntityUsesConservativeDefaultPolicy() {
        CompendiumEntryId pig = entity("minecraft:pig");
        eq(EntityPreviewFactory.Policy.VANILLA_DEFAULT, EntityPreviewFactory.policyFor(pig));
    }

    private static void moddedEntityRequiresExplicitAdapter() {
        CompendiumEntryId stag = entity("preview_policy_test:stag");
        eq(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(stag));

        EntityPreviewFactory.registerAdapter(stag);
        eq(EntityPreviewFactory.Policy.ADAPTER, EntityPreviewFactory.policyFor(stag));
    }

    private static void explicitBlacklistOverridesAdapter() {
        CompendiumEntryId entry = entity("preview_blacklist_test:entity");
        EntityPreviewFactory.registerAdapter(entry);
        EntityPreviewFactory.blacklist(entry);
        eq(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(entry));

        EntityPreviewFactory.removeBlacklist(entry);
        eq(EntityPreviewFactory.Policy.ADAPTER, EntityPreviewFactory.policyFor(entry));
    }

    private static void quarantineFailsClosedUntilAdapterRecovery() {
        CompendiumEntryId entry = entity("preview_quarantine_test:entity");
        EntityPreviewFactory.quarantine(entry);
        check(EntityPreviewFactory.quarantined(entry), "entry should be quarantined");
        eq(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(entry));

        EntityPreviewFactory.registerAdapter(entry);
        check(!EntityPreviewFactory.quarantined(entry), "adapter registration should clear quarantine");
        eq(EntityPreviewFactory.Policy.ADAPTER, EntityPreviewFactory.policyFor(entry));
    }

    private static void nonEntityEntriesRemainBlocked() {
        CompendiumEntryId biome = CompendiumEntryId.of(CompendiumEntryKind.BIOME, "minecraft:plains");
        eq(EntityPreviewFactory.Policy.BLOCKED, EntityPreviewFactory.policyFor(biome));
    }

    private static void interactionStateResetsAndClamps() {
        CompendiumEntityPreview preview = new CompendiumEntityPreview();
        CompendiumEntryId pig = entity("minecraft:pig");
        check(preview.sync(pig), "initial sync should report an entry change");
        check(!preview.sync(pig), "same entry should not recreate runtime resources");

        preview.markBounds(10, 20, 110, 120);
        check(preview.contains(10, 20), "top-left should be inside preview bounds");
        check(!preview.contains(110, 120), "bottom-right edge should be exclusive");

        preview.drag(10_000, -10_000);
        check(preview.horizontalAngle() <= 1.45F, "horizontal angle must be clamped");
        check(preview.verticalAngle() <= 1.10F, "vertical angle must be clamped");
        for (int i = 0; i < 100; i++) preview.zoom(1.0D);
        check(preview.zoomLevel() <= 1.85F, "zoom upper bound must be clamped");
        for (int i = 0; i < 100; i++) preview.zoom(-1.0D);
        check(preview.zoomLevel() >= 0.55F, "zoom lower bound must be clamped");

        CompendiumEntryId cow = entity("minecraft:cow");
        check(preview.sync(cow), "new entry should reset interaction state");
        eq(1.0F, preview.zoomLevel());
        eq(0.0F, preview.horizontalAngle());
        eq(0.0F, preview.verticalAngle());
        check(!preview.contains(10, 20), "entry change should clear stale bounds");
    }

    private static CompendiumEntryId entity(String id) {
        return CompendiumEntryId.of(CompendiumEntryKind.ENTITY, id);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
