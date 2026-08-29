package dev.gustavopere.rpgskilltree.runtime.data;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Validation failure carrying the exact datapack resource, subject id and field. */
public final class SkillTreeDataValidationException extends IllegalArgumentException {
    private final ResourceLocation resource;
    private final String subjectId;
    private final String field;

    public SkillTreeDataValidationException(
        ResourceLocation resource,
        String subjectId,
        String field,
        String detail
    ) {
        this(resource, subjectId, field, detail, null);
    }

    public SkillTreeDataValidationException(
        ResourceLocation resource,
        String subjectId,
        String field,
        String detail,
        Throwable cause
    ) {
        super(message(resource, subjectId, field, detail), cause);
        this.resource = Objects.requireNonNull(resource, "resource");
        this.subjectId = requireText(subjectId, "subjectId");
        this.field = requireText(field, "field");
    }

    public ResourceLocation resource() {
        return resource;
    }

    public String subjectId() {
        return subjectId;
    }

    public String field() {
        return field;
    }

    public static SkillTreeDataValidationException wrap(
        ResourceLocation resource,
        String subjectId,
        String field,
        RuntimeException cause
    ) {
        if (cause instanceof SkillTreeDataValidationException contextual) return contextual;
        String detail = cause.getMessage() == null || cause.getMessage().isBlank()
            ? cause.getClass().getSimpleName()
            : cause.getMessage();
        return new SkillTreeDataValidationException(resource, subjectId, field, detail, cause);
    }

    private static String message(ResourceLocation resource, String subjectId, String field, String detail) {
        Objects.requireNonNull(resource, "resource");
        return "invalid skill-tree data resource=" + resource
            + " id=" + requireText(subjectId, "subjectId")
            + " field=" + requireText(field, "field")
            + ": " + requireText(detail, "detail");
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }
}
