package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.catalog.InventoryKind;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.StemBlock;

final class BlockCatalogClassifier {
    private BlockCatalogClassifier() {}

    static Optional<InventoryKind> classify(Block block, ResourceLocation id) {
        if (block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock) {
            return Optional.of(InventoryKind.CROP);
        }
        if (block instanceof SaplingBlock || block instanceof LeavesBlock) {
            return Optional.of(InventoryKind.TREE);
        }
        if (block instanceof BushBlock) {
            return Optional.of(InventoryKind.FLORA);
        }

        String path = id.getPath().toLowerCase(Locale.ROOT);
        if (path.endsWith("_sapling") || path.endsWith("_leaves") || path.endsWith("_propagule")) {
            return Optional.of(InventoryKind.TREE);
        }
        if (path.contains("crop") || path.endsWith("_stem") || path.endsWith("_crop")
            || path.equals("wheat") || path.equals("carrots") || path.equals("potatoes")
            || path.equals("beetroots") || path.equals("cocoa")) {
            return Optional.of(InventoryKind.CROP);
        }
        if (path.endsWith("_flower") || path.endsWith("_bush") || path.endsWith("_grass")
            || path.endsWith("_fern") || path.endsWith("_mushroom") || path.endsWith("_fungus")
            || path.endsWith("_roots") || path.endsWith("_vine") || path.endsWith("_plant")
            || path.endsWith("_cactus") || path.endsWith("_lily")) {
            return Optional.of(InventoryKind.FLORA);
        }
        return Optional.empty();
    }
}
