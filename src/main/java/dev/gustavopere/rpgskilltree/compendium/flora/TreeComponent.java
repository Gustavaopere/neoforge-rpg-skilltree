package dev.gustavopere.rpgskilltree.compendium.flora;

import java.util.Objects;

public record TreeComponent(TreeComponentRole role, String resourceLocation) {
    public TreeComponent {
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(resourceLocation, "resourceLocation");
        resourceLocation = resourceLocation.trim();
        int colon = resourceLocation.indexOf(':');
        if (colon <= 0 || colon == resourceLocation.length() - 1) {
            throw new IllegalArgumentException("invalid tree component resource id: " + resourceLocation);
        }
    }
}
