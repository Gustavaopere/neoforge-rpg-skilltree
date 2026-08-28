package dev.gustavopere.rpgskilltree.compendium.provider.entity;

import dev.gustavopere.rpgskilltree.compendium.entity.EntityVariantSnapshot;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Ordered pure special-inspector registry; unsupported families preserve the generic entity page. */
public final class VanillaEntitySpecialInspectors {
    private static final List<EntitySpecialInspector> INSPECTORS = List.of(
        family("horse"),
        family("panda"),
        family("villager"),
        family("bee"),
        family("dolphin"),
        family("goat"),
        family("wandering_trader"),
        family("tameable")
    );

    private VanillaEntitySpecialInspectors() {}

    public static Optional<EntityVariantSnapshot> inspect(
        String family,
        Map<String, String> textFacts,
        Map<String, Long> numericFacts,
        Map<String, Boolean> booleanFacts
    ) {
        if (family == null || family.isBlank()) return Optional.empty();
        return INSPECTORS.stream()
            .filter(inspector -> inspector.supports(family))
            .findFirst()
            .map(inspector -> inspector.inspect(textFacts, numericFacts, booleanFacts));
    }

    public static Set<String> supportedFamilies() {
        return Set.of("horse", "panda", "villager", "bee", "dolphin", "goat", "wandering_trader", "tameable");
    }

    private static EntitySpecialInspector family(String expectedFamily) {
        return new EntitySpecialInspector() {
            @Override
            public boolean supports(String family) {
                return expectedFamily.equals(family);
            }

            @Override
            public EntityVariantSnapshot inspect(
                Map<String, String> textFacts,
                Map<String, Long> numericFacts,
                Map<String, Boolean> booleanFacts
            ) {
                return new EntityVariantSnapshot(expectedFamily, textFacts, numericFacts, booleanFacts);
            }
        };
    }
}
