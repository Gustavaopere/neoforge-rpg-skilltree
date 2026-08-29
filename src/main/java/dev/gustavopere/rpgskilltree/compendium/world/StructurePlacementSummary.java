package dev.gustavopere.rpgskilltree.compendium.world;

public record StructurePlacementSummary(String placementType, Integer spacing, Integer separation) {
    public StructurePlacementSummary {
        if (placementType != null) {
            placementType = placementType.trim();
            if (placementType.isEmpty()) placementType = null;
        }
        if (spacing != null && spacing <= 0) throw new IllegalArgumentException("spacing must be positive");
        if (separation != null && separation < 0) throw new IllegalArgumentException("separation must be non-negative");
        if (spacing != null && separation != null && separation >= spacing) {
            throw new IllegalArgumentException("separation must be lower than spacing");
        }
    }

    public boolean empty() {
        return placementType == null && spacing == null && separation == null;
    }
}
