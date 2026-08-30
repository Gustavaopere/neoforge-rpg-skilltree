package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.client.render.CompendiumStaticPreviewPolicy;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumStaticPreviewRegistryResolver;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

/** Physical-client renderer for bounded registry-backed static Compendium previews. */
public final class CompendiumStaticPreviewRenderer {
    private static final int ITEM_SIZE = 16;

    private CompendiumStaticPreviewRenderer() {}

    public enum RenderResult {
        RENDERED_ITEM,
        FALLBACK,
        METADATA_ONLY,
        NOT_APPLICABLE
    }

    public static RenderResult render(
        GuiGraphics graphics,
        CompendiumStaticPreviewPolicy.Request request,
        int left,
        int top,
        int right,
        int bottom
    ) {
        Objects.requireNonNull(graphics, "graphics");
        Objects.requireNonNull(request, "request");
        if (right <= left || bottom <= top) return RenderResult.FALLBACK;

        CompendiumStaticPreviewRegistryResolver.Resolution resolution =
            CompendiumStaticPreviewRegistryResolver.resolve(request);
        return switch (resolution.result()) {
            case ITEM -> renderItem(graphics, resolution.itemStack().orElseThrow(), left, top, right, bottom);
            case METADATA_ONLY -> RenderResult.METADATA_ONLY;
            case NOT_APPLICABLE -> RenderResult.NOT_APPLICABLE;
            case NO_ITEM, UNKNOWN_BLOCK -> RenderResult.FALLBACK;
        };
    }

    private static RenderResult renderItem(
        GuiGraphics graphics,
        ItemStack stack,
        int left,
        int top,
        int right,
        int bottom
    ) {
        int x = left + Math.max(0, (right - left - ITEM_SIZE) / 2);
        int y = top + Math.max(0, (bottom - top - ITEM_SIZE) / 2);
        try {
            graphics.renderItem(stack, x, y);
            return RenderResult.RENDERED_ITEM;
        } catch (RuntimeException | LinkageError exception) {
            return RenderResult.FALLBACK;
        }
    }
}
