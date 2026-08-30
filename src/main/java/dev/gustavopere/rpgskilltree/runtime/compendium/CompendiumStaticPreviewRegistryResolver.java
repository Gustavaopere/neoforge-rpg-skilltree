package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.client.render.CompendiumStaticPreviewPolicy;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

/**
 * Server-safe registry resolver shared by the client static-preview bridge and bootstrapped GameTests.
 */
public final class CompendiumStaticPreviewRegistryResolver {
    private CompendiumStaticPreviewRegistryResolver() {}

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
            case NONE -> empty(Result.NOT_APPLICABLE);
            case METADATA_ONLY -> empty(Result.METADATA_ONLY);
            case REGISTRY_ITEM_OR_BLOCK -> resolveRegistryItem(request.resourceLocation());
        };
    }

    private static Resolution resolveRegistryItem(String resourceLocation) {
        ResourceLocation key = ResourceLocation.tryParse(resourceLocation);
        if (key == null || !BuiltInRegistries.BLOCK.containsKey(key)) {
            return empty(Result.UNKNOWN_BLOCK);
        }

        Block block = BuiltInRegistries.BLOCK.get(key);
        if (block.asItem() == Items.AIR) return empty(Result.NO_ITEM);
        return new Resolution(Result.ITEM, Optional.of(new ItemStack(block.asItem())));
    }

    private static Resolution empty(Result result) {
        return new Resolution(result, Optional.empty());
    }
}
