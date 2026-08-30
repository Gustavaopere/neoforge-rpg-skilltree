package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.client.render.CompendiumStaticPreviewPolicy;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumStaticPreviewRegistryResolver;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class CompendiumStaticPreviewGameTests {
    private CompendiumStaticPreviewGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void resolvesRegistryBackedStaticPreviewSafely(GameTestHelper helper) {
        var flora = CompendiumStaticPreviewRegistryResolver.resolve(
            CompendiumStaticPreviewPolicy.requestFor(
                CompendiumEntryId.of(CompendiumEntryKind.FLORA, "minecraft:dandelion")
            )
        );
        if (flora.result() != CompendiumStaticPreviewRegistryResolver.Result.ITEM) {
            throw new AssertionError("dandelion should resolve to ITEM but got " + flora.result());
        }
        if (flora.itemStack().isEmpty() || flora.itemStack().orElseThrow().getItem() != Items.DANDELION) {
            throw new AssertionError("dandelion preview should use the registered dandelion item");
        }

        var water = CompendiumStaticPreviewRegistryResolver.resolve(
            CompendiumStaticPreviewPolicy.requestFor(
                CompendiumEntryId.of(CompendiumEntryKind.BLOCK_FEATURE, "minecraft:water")
            )
        );
        if (water.result() != CompendiumStaticPreviewRegistryResolver.Result.NO_ITEM) {
            throw new AssertionError("water should fail soft as NO_ITEM but got " + water.result());
        }

        var structure = CompendiumStaticPreviewRegistryResolver.resolve(
            CompendiumStaticPreviewPolicy.requestFor(
                CompendiumEntryId.of(CompendiumEntryKind.STRUCTURE, "minecraft:village_plains")
            )
        );
        if (structure.result() != CompendiumStaticPreviewRegistryResolver.Result.METADATA_ONLY) {
            throw new AssertionError("structures must remain metadata-only without owned assets");
        }

        var missing = CompendiumStaticPreviewRegistryResolver.resolve(
            CompendiumStaticPreviewPolicy.requestFor(
                CompendiumEntryId.of(CompendiumEntryKind.FLORA, "missing_preview_test:not_registered")
            )
        );
        if (missing.result() != CompendiumStaticPreviewRegistryResolver.Result.UNKNOWN_BLOCK) {
            throw new AssertionError("missing blocks should fail soft as UNKNOWN_BLOCK");
        }

        helper.succeed();
    }
}
