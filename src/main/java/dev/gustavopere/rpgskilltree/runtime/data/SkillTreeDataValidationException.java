package dev.gustavopere.rpgskilltree.runtime.data;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Fail-visible datapack validation error with stable source, entry and field context. */
public final class SkillTreeDataValidationException extends IllegalArgumentException {
    private final ResourceLocation resourceId;
    private final String entryId;
    private final String field;

    public SkillTreeDataValidationException(
        ResourceLocation resourceId,
        String entryId,
        String field,
        String detail
    ) {
        this(resourceId, entryId, field, detail, null);
    }

    public SkillTreeDataValidationException(
        ResourceLocation resourceId,
        String entryId,
        String field,
        String detail,
        Throwable cause
    ) {
        super(format(resourceId, entryId, field, detail), cause);
        this.resourceId = Objects.requireNonNull(resourceId, "resourceId");
        this.entryId = entryId;
        this.field = Objects.requireNonNull(field, "field");
    }

    public ResourceLocation resourceId() {
        return resourceId;
    }

    public String entryId() {
        return entryId;
    }

    public String field() {
        return field;
    }

    private static String format(ResourceLocation resourceId, String entryId, String field, String detail) {
        Objects.requireNonNull(resourceId, "resourceId");
        Objects.requireNonNull(field, "field");
        Objects.requireNonNull(detail, "detail");
        return "skill-tree data validation failed: resource=" + resourceId
            + (entryId == null ? "" : " id=" + entryId)
            + " field=" + field + " detail=" + detail;
    }
}
