package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Objects;

public final class CompendiumStaticPreviewPolicyTest {
    public static void main(String[] args) {
        botanicalEntriesUseRegistryItemOrBlockPreview();
        worldEntriesRemainMetadataOnlyWithoutOwnedAssets();
        entitiesStayOnDedicatedEntityPreviewPipeline();
        previewRequestPreservesCanonicalIdentity();
        System.out.println("CompendiumStaticPreviewPolicyTest: PASS");
    }

    private static void botanicalEntriesUseRegistryItemOrBlockPreview() {
        eq(CompendiumStaticPreviewPolicy.Mode.REGISTRY_ITEM_OR_BLOCK, mode(CompendiumEntryKind.FLORA));
        eq(CompendiumStaticPreviewPolicy.Mode.REGISTRY_ITEM_OR_BLOCK, mode(CompendiumEntryKind.TREE));
        eq(CompendiumStaticPreviewPolicy.Mode.REGISTRY_ITEM_OR_BLOCK, mode(CompendiumEntryKind.CROP));
        eq(CompendiumStaticPreviewPolicy.Mode.REGISTRY_ITEM_OR_BLOCK, mode(CompendiumEntryKind.BLOCK_FEATURE));
    }

    private static void worldEntriesRemainMetadataOnlyWithoutOwnedAssets() {
        eq(CompendiumStaticPreviewPolicy.Mode.METADATA_ONLY, mode(CompendiumEntryKind.BIOME));
        eq(CompendiumStaticPreviewPolicy.Mode.METADATA_ONLY, mode(CompendiumEntryKind.STRUCTURE));
        eq(CompendiumStaticPreviewPolicy.Mode.METADATA_ONLY, mode(CompendiumEntryKind.DIMENSION));
    }

    private static void entitiesStayOnDedicatedEntityPreviewPipeline() {
        eq(CompendiumStaticPreviewPolicy.Mode.NONE, mode(CompendiumEntryKind.ENTITY));
    }

    private static void previewRequestPreservesCanonicalIdentity() {
        CompendiumEntryId id = CompendiumEntryId.of(CompendiumEntryKind.FLORA, "minecraft:dandelion");
        CompendiumStaticPreviewPolicy.Request request = CompendiumStaticPreviewPolicy.requestFor(id);

        eq(id, request.entryId());
        eq("minecraft:dandelion", request.resourceLocation());
        eq(CompendiumStaticPreviewPolicy.Mode.REGISTRY_ITEM_OR_BLOCK, request.mode());
    }

    private static CompendiumStaticPreviewPolicy.Mode mode(CompendiumEntryKind kind) {
        return CompendiumStaticPreviewPolicy.requestFor(
            CompendiumEntryId.of(kind, "minecraft:test")
        ).mode();
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
