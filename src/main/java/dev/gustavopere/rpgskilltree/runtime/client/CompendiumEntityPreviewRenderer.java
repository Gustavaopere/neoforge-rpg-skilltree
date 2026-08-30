package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientEntry;
import dev.gustavopere.rpgskilltree.compendium.client.render.CompendiumEntityPreview;
import dev.gustavopere.rpgskilltree.compendium.client.render.EntityPreviewFactory;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

/** Physical-client bridge for detached Natural Compendium entity previews. */
public final class CompendiumEntityPreviewRenderer {
    private static final int MIN_RENDER_SCALE = 10;
    private static final int MAX_RENDER_SCALE = 84;
    private static final Map<ResourceLocation, PreviewAdapter> ADAPTERS = new ConcurrentHashMap<>();

    private final CompendiumEntityPreview state = new CompendiumEntityPreview();
    private LivingEntity entity;
    private EntityPreviewFactory.Failure failure = EntityPreviewFactory.Failure.BLOCKED;

    @FunctionalInterface
    public interface PreviewAdapter {
        /** Returns a detached living entity. The adapter must not add or tick it in the client level. */
        LivingEntity create(ClientLevel level);
    }

    public void sync(CompendiumClientEntry entry, ClientLevel level) {
        Objects.requireNonNull(entry, "entry");
        CompendiumEntryId entryId = entry.id();
        if (!state.sync(entryId)) return;

        entity = null;
        failure = EntityPreviewFactory.Failure.BLOCKED;
        if (entryId.kind() != CompendiumEntryKind.ENTITY) {
            failure = EntityPreviewFactory.Failure.NOT_ENTITY;
            return;
        }
        if (level == null) {
            failure = EntityPreviewFactory.Failure.NO_LEVEL;
            return;
        }

        EntityPreviewFactory.Policy policy = EntityPreviewFactory.policyFor(entryId);
        if (policy == EntityPreviewFactory.Policy.BLOCKED) return;

        ResourceLocation key = ResourceLocation.tryParse(entryId.resourceLocation());
        if (key == null) {
            failure = EntityPreviewFactory.Failure.UNKNOWN_TYPE;
            return;
        }

        try {
            LivingEntity preview;
            if (policy == EntityPreviewFactory.Policy.ADAPTER) {
                PreviewAdapter adapter = ADAPTERS.get(key);
                if (adapter == null) {
                    EntityPreviewFactory.quarantine(entryId);
                    return;
                }
                preview = adapter.create(level);
            } else {
                if (!BuiltInRegistries.ENTITY_TYPE.containsKey(key)) {
                    failure = EntityPreviewFactory.Failure.UNKNOWN_TYPE;
                    return;
                }
                EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(key);
                Entity created = type.create(level);
                if (!(created instanceof LivingEntity living)) {
                    failure = EntityPreviewFactory.Failure.NOT_LIVING;
                    return;
                }
                preview = living;
            }

            if (preview == null) {
                failure = EntityPreviewFactory.Failure.CONSTRUCTION_FAILED;
                EntityPreviewFactory.quarantine(entryId);
                return;
            }
            entity = preview;
            failure = EntityPreviewFactory.Failure.NONE;
        } catch (RuntimeException | LinkageError exception) {
            failure = EntityPreviewFactory.Failure.CONSTRUCTION_FAILED;
            EntityPreviewFactory.quarantine(entryId);
        }
    }

    public boolean render(GuiGraphics graphics, int left, int top, int right, int bottom) {
        Objects.requireNonNull(graphics, "graphics");
        state.markBounds(left, top, right, bottom);
        if (entity == null || right <= left || bottom <= top) return false;

        graphics.enableScissor(left, top, right, bottom);
        try {
            InventoryScreen.renderEntityInInventoryFollowsAngle(
                graphics,
                left,
                top,
                right,
                bottom,
                calculateScale(right - left, bottom - top, entity, state.zoomLevel()),
                0.0F,
                state.horizontalAngle(),
                state.verticalAngle(),
                entity
            );
            return true;
        } catch (RuntimeException | LinkageError exception) {
            failure = EntityPreviewFactory.Failure.RENDER_FAILED;
            entity = null;
            EntityPreviewFactory.quarantine(state.loadedId());
            return false;
        } finally {
            graphics.disableScissor();
        }
    }

    public void clear() {
        state.clear();
        entity = null;
        failure = EntityPreviewFactory.Failure.BLOCKED;
    }

    public boolean contains(double mouseX, double mouseY) {
        return state.contains(mouseX, mouseY);
    }

    public void drag(double deltaX, double deltaY) {
        state.drag(deltaX, deltaY);
    }

    public void zoom(double scrollY) {
        state.zoom(scrollY);
    }

    public boolean available() {
        return entity != null && failure == EntityPreviewFactory.Failure.NONE;
    }

    public EntityPreviewFactory.Failure failure() {
        return failure;
    }

    /**
     * Registers a client-only safe constructor for a modded entity type and opts it into the shared
     * preview policy.
     */
    public static void registerAdapter(ResourceLocation entityId, PreviewAdapter adapter) {
        ResourceLocation key = Objects.requireNonNull(entityId, "entityId");
        ADAPTERS.put(key, Objects.requireNonNull(adapter, "adapter"));
        EntityPreviewFactory.registerAdapter(entryId(key));
    }

    /** Blocks a type even if a client adapter was registered previously. */
    public static void blacklist(ResourceLocation entityId) {
        EntityPreviewFactory.blacklist(entryId(Objects.requireNonNull(entityId, "entityId")));
    }

    static int calculateScale(int width, int height, LivingEntity entity, float zoom) {
        float entityWidth = Math.max(0.25F, entity.getBbWidth());
        float entityHeight = Math.max(0.25F, entity.getBbHeight());
        float dominantDimension = Math.max(entityWidth, entityHeight);
        float available = Math.max(1.0F, Math.min(width, height) * 0.70F);
        return Mth.clamp(Math.round((available / dominantDimension) * zoom), MIN_RENDER_SCALE, MAX_RENDER_SCALE);
    }

    private static CompendiumEntryId entryId(ResourceLocation entityId) {
        return CompendiumEntryId.of(CompendiumEntryKind.ENTITY, entityId.toString());
    }
}
