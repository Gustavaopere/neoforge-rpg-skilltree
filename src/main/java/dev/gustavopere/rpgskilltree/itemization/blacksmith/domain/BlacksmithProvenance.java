package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/** Diagnostic provenance of a physical part without granting authority over its stats. */
public record BlacksmithProvenance(ResourceLocation providerId, ResourceLocation recipeId) {
    public BlacksmithProvenance {
        Objects.requireNonNull(providerId, "providerId");
        Objects.requireNonNull(recipeId, "recipeId");
    }
}
