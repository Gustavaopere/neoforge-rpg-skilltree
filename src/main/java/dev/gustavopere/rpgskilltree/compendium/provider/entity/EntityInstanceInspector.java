package dev.gustavopere.rpgskilltree.compendium.provider.entity;

import dev.gustavopere.rpgskilltree.compendium.entity.EntityEffectSnapshot;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityInstanceSnapshot;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Pure whitelist projection used by the NeoForge runtime adapter. */
public final class EntityInstanceInspector {
    private EntityInstanceInspector() {}

    public record Input(
        String entityId,
        float width,
        float height,
        Double currentHealth,
        Double maxHealth,
        Map<String, Double> currentAttributes,
        Integer ageTicks,
        List<EntityEffectSnapshot> effects,
        Boolean tame,
        String ownerId,
        Boolean sitting,
        Boolean breedReady,
        boolean noAi,
        boolean invulnerable,
        boolean silent,
        boolean leashed
    ) {}

    public static EntityInstanceSnapshot inspect(Input input) {
        if (input == null) throw new IllegalArgumentException("input must not be null");

        LinkedHashMap<String, Double> attributes = new LinkedHashMap<>();
        if (input.currentAttributes() != null) {
            input.currentAttributes().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    if (key == null || key.isBlank()) throw new IllegalArgumentException("attribute key must not be blank");
                    if (value == null || !Double.isFinite(value)) throw new IllegalArgumentException("attribute value must be finite");
                    attributes.put(key, value);
                });
        }

        Integer ageTicks = input.ageTicks();
        Boolean baby = ageTicks == null ? null : ageTicks < 0;

        return new EntityInstanceSnapshot(
            input.entityId(),
            input.width(),
            input.height(),
            input.currentHealth(),
            input.maxHealth(),
            attributes,
            baby,
            ageTicks,
            input.effects(),
            input.tame(),
            input.ownerId(),
            input.sitting(),
            input.breedReady(),
            input.noAi(),
            input.invulnerable(),
            input.silent(),
            input.leashed()
        );
    }
}
