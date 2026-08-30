package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.Objects;

/**
 * Platform-agnostic policy describing which Compendium entries may request a static registry preview.
 *
 * <p>Physical Minecraft rendering belongs under {@code runtime/client}. This layer only decides
 * whether an entry may use an item/block visual, must remain metadata-only, or belongs to another
 * preview pipeline.</p>
 */
public final class CompendiumStaticPreviewPolicy {
    private CompendiumStaticPreviewPolicy() {}

    public enum Mode {
        REGISTRY_ITEM_OR_BLOCK,
        METADATA_ONLY,
        NONE
    }

    public record Request(
        CompendiumEntryId entryId,
        String resourceLocation,
        Mode mode
    ) {
        public Request {
            Objects.requireNonNull(entryId, "entryId");
            if (resourceLocation == null || resourceLocation.isBlank()) {
                throw new IllegalArgumentException("resourceLocation must not be blank");
            }
            resourceLocation = resourceLocation.trim();
            Objects.requireNonNull(mode, "mode");
        }
    }

    public static Request requestFor(CompendiumEntryId entryId) {
        CompendiumEntryId id = Objects.requireNonNull(entryId, "entryId");
        Mode mode = switch (id.kind()) {
            case FLORA, TREE, CROP, BLOCK_FEATURE -> Mode.REGISTRY_ITEM_OR_BLOCK;
            case BIOME, STRUCTURE, DIMENSION -> Mode.METADATA_ONLY;
            case ENTITY -> Mode.NONE;
        };
        return new Request(id, id.resourceLocation(), mode);
    }
}
