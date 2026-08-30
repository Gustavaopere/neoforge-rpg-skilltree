package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.Objects;

/** Platform-agnostic interaction state shared by the physical client preview renderer. */
public final class CompendiumEntityPreview {
    private static final float MIN_ZOOM = 0.55F;
    private static final float MAX_ZOOM = 1.85F;
    private static final float ZOOM_STEP = 0.10F;
    private static final float DRAG_SENSITIVITY = 0.018F;
    private static final float MAX_HORIZONTAL_ANGLE = 1.45F;
    private static final float MAX_VERTICAL_ANGLE = 1.10F;

    private CompendiumEntryId loadedId;
    private float horizontalAngle;
    private float verticalAngle;
    private float zoom = 1.0F;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    /** Returns true when the preview entry changed and runtime resources must be recreated. */
    public boolean sync(CompendiumEntryId entryId) {
        CompendiumEntryId id = Objects.requireNonNull(entryId, "entryId");
        if (id.equals(loadedId)) return false;
        loadedId = id;
        resetView();
        clearBounds();
        return true;
    }

    public void clear() {
        loadedId = null;
        resetView();
        clearBounds();
    }

    public void markBounds(int left, int top, int right, int bottom) {
        x1 = left;
        y1 = top;
        x2 = right;
        y2 = bottom;
    }

    public boolean contains(double mouseX, double mouseY) {
        return x2 > x1 && y2 > y1 && mouseX >= x1 && mouseX < x2 && mouseY >= y1 && mouseY < y2;
    }

    public void drag(double deltaX, double deltaY) {
        horizontalAngle = clamp(
            horizontalAngle + (float) deltaX * DRAG_SENSITIVITY,
            -MAX_HORIZONTAL_ANGLE,
            MAX_HORIZONTAL_ANGLE
        );
        verticalAngle = clamp(
            verticalAngle - (float) deltaY * DRAG_SENSITIVITY,
            -MAX_VERTICAL_ANGLE,
            MAX_VERTICAL_ANGLE
        );
    }

    public void zoom(double scrollY) {
        if (scrollY == 0.0D) return;
        zoom = clamp(zoom + (scrollY > 0.0D ? ZOOM_STEP : -ZOOM_STEP), MIN_ZOOM, MAX_ZOOM);
    }

    public CompendiumEntryId loadedId() {
        return loadedId;
    }

    public float zoomLevel() {
        return zoom;
    }

    public float horizontalAngle() {
        return horizontalAngle;
    }

    public float verticalAngle() {
        return verticalAngle;
    }

    private void resetView() {
        horizontalAngle = 0.0F;
        verticalAngle = 0.0F;
        zoom = 1.0F;
    }

    private void clearBounds() {
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
