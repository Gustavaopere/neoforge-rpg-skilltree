package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.client.render.CompendiumStaticPreviewPolicy;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

/** Physical-client resolver for bounded static Compendium previews. */
public final class CompendiumStaticPreviewRenderer {
    private CompendiumStaticPreviewRenderer() {}

    public enum Result {
        ITEM,
        NO_ITEM,
        UNKNOWN_BLOCK,
        METADATA_ONLY,
        NOT_APPLICABLE
    }

    public record Resolution(Result result, Optional<ItemStack> itemStack) {
        public Resolution {
            Objects.requireNonNull(result, "result");
            itemStack = itemStack == null ? Optional.empty() : itemStack.map(ItemStack::copy);
            if ((result == Result.ITEM) != itemStack.isPresent()) {
                throw new IllegalArgumentException("only ITEM resolutions may carry an ItemStack");
            }
        }

        @Override
        public Optional<ItemStack> itemStack() {
            return itemStack.map(ItemStack::copy);
        }
    }

    public static Resolution resolve(CompendiumStaticPreviewPolicy.Request request) {
        Objects.requireNonNull(request, "request");
        return switch (request.mode()) {
            case NONE -> new Resolution(Result.NOT_APPLICABLE, Optional.empty());
            case METADATA_ONLY -> new Resolution(Result.METADATA_ONLY, Optional.empty());
            case REGISTRY_ITEM_OR_BLOCK -> resolveRegistryItem(request.resourceLocation());
        };
    }

    private static Resolution resolveRegistryItem(String resourceLocation) {
        ResourceLocation key = ResourceLocation.tryParse(resourceLocation);
        if (key == null || !BuiltInRegistries.BLOCK.containsKey(key)) {
            return new Resolution(Result.UNKNOWN_BLOCK, Optional.empty());
        }

        Block block = BuiltInRegistries.BLOCK.get(key);
        if (block.asItem() == Items.AIR) {
            return new Resolution(Result.NO_ITEM, Optional.empty());
        }
        return new Resolution(Result.ITEM, Optional.of(new ItemStack(block.asItem())));
    }
}
