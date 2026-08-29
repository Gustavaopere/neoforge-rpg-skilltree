package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientEntry;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

/**
 * Client-only state for a detached Compendium entity preview.
 *
 * <p>The controller never adds its entity to the client level and never ticks it. Rendering failures
 * are contained locally and quarantine the entity type so reopening the same entry cannot repeatedly
 * crash a modded renderer.</p>
 */
public final class CompendiumEntityPreview {
    private static final float MIN_ZOOM = 0.55F;
    private static final float MAX_ZOOM = 1.85F;
    private static final float ZOOM_STEP = 0.10F;
    private static final float DRAG_SENSITIVITY = 0.018F;
    private static final float MAX_HORIZONTAL_ANGLE = 1.45F;
    private static final float MAX_VERTICAL_ANGLE = 1.10F;
    private static final int MIN_RENDER_SCALE = 10;
    private static final int MAX_RENDER_SCALE = 84;

    private CompendiumEntryId loadedId;
    private LivingEntity entity;
    private EntityPreviewFactory.Failure failure = EntityPreviewFactory.Failure.BLOCKED;
    private boolean renderFailed;
    private float horizontalAngle;
    private float verticalAngle;
    private float zoom = 1.0F;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public void sync(CompendiumClientEntry entry, ClientLevel level) {
        Objects.requireNonNull(entry, "entry");
        CompendiumEntryId id = entry.id();
        if (id.equals(loadedId)) return;

        clearEntityState();
        loadedId = id;
        EntityPreviewFactory.Result result = EntityPreviewFactory.create(id, level);
        entity = result.entity();
        failure = result.failure();
    }

    public void clear() {
        loadedId = null;
        clearEntityState();
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
    }

    public boolean render(GuiGraphics graphics, int left, int top, int right, int bottom) {
        Objects.requireNonNull(graphics, "graphics");
        x1 = left;
        y1 = top;
        x2 = right;
        y2 = bottom;
        if (entity == null || renderFailed || right <= left || bottom <= top) return false;

        graphics.enableScissor(left, top, right, bottom);
        try {
            int scale = calculateScale(right - left, bottom - top, entity, zoom);
            InventoryScreen.renderEntityInInventoryFollowsAngle(
                graphics,
                left,
                top,
                right,
                bottom,
                scale,
                0.0F,
                horizontalAngle,
                verticalAngle,
                entity
            );
            return true;
        } catch (RuntimeException | LinkageError exception) {
            renderFailed = true;
            failure = EntityPreviewFactory.Failure.CONSTRUCTION_FAILED;
            entity = null;
            EntityPreviewFactory.quarantine(loadedId);
            return false;
        } finally {
            graphics.disableScissor();
        }
    }

    public boolean contains(double mouseX, double mouseY) {
        return x2 > x1 && y2 > y1 && mouseX >= x1 && mouseX < x2 && mouseY >= y1 && mouseY < y2;
    }

    public void drag(double deltaX, double deltaY) {
        horizontalAngle = Mth.clamp(
            horizontalAngle + (float) deltaX * DRAG_SENSITIVITY,
            -MAX_HORIZONTAL_ANGLE,
            MAX_HORIZONTAL_ANGLE
        );
        verticalAngle = Mth.clamp(
            verticalAngle - (float) deltaY * DRAG_SENSITIVITY,
            -MAX_VERTICAL_ANGLE,
            MAX_VERTICAL_ANGLE
        );
    }

    public void zoom(double scrollY) {
        if (scrollY == 0.0D) return;
        zoom = Mth.clamp(zoom + (scrollY > 0.0D ? ZOOM_STEP : -ZOOM_STEP), MIN_ZOOM, MAX_ZOOM);
    }

    public boolean available() {
        return entity != null && !renderFailed;
    }

    public EntityPreviewFactory.Failure failure() {
        return failure;
    }

    float zoomLevel() {
        return zoom;
    }

    float horizontalAngle() {
        return horizontalAngle;
    }

    float verticalAngle() {
        return verticalAngle;
    }

    static int calculateScale(int width, int height, LivingEntity entity, float zoom) {
        float entityWidth = Math.max(0.25F, entity.getBbWidth());
        float entityHeight = Math.max(0.25F, entity.getBbHeight());
        float dominantDimension = Math.max(entityWidth, entityHeight);
        float available = Math.max(1.0F, Math.min(width, height) * 0.70F);
        return Mth.clamp(Math.round((available / dominantDimension) * zoom), MIN_RENDER_SCALE, MAX_RENDER_SCALE);
    }

    private void clearEntityState() {
        entity = null;
        failure = EntityPreviewFactory.Failure.BLOCKED;
        renderFailed = false;
        horizontalAngle = 0.0F;
        verticalAngle = 0.0F;
        zoom = 1.0F;
    }
}
