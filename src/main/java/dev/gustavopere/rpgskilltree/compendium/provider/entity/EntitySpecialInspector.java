package dev.gustavopere.rpgskilltree.compendium.provider.entity;

import dev.gustavopere.rpgskilltree.compendium.entity.EntityVariantSnapshot;
import java.util.Map;

/** Small fail-soft inspector for one narrow entity family. */
public interface EntitySpecialInspector {
    boolean supports(String family);

    EntityVariantSnapshot inspect(
        Map<String, String> textFacts,
        Map<String, Long> numericFacts,
        Map<String, Boolean> booleanFacts
    );
}
