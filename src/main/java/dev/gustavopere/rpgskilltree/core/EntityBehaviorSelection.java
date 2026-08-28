package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/** Canonical immutable set-like behavior selection represented in stable ID order. */
public final class EntityBehaviorSelection {
    private final List<EntityBehaviorKey> behaviors;

    public EntityBehaviorSelection(List<EntityBehaviorKey> behaviors) {
        Objects.requireNonNull(behaviors, "behaviors");
        ArrayList<EntityBehaviorKey> copy = new ArrayList<>(behaviors.size());
        HashSet<EntityBehaviorKey> seen = new HashSet<>();
        for (EntityBehaviorKey behavior : behaviors) {
            EntityBehaviorKey key = Objects.requireNonNull(behavior, "behavior");
            if (!seen.add(key)) {
                throw new IllegalArgumentException("duplicate entity behavior: " + key.serializedId());
            }
            copy.add(key);
        }
        copy.sort(Comparator.comparing(EntityBehaviorKey::serializedId));
        this.behaviors = List.copyOf(copy);
    }

    public static EntityBehaviorSelection empty() {
        return new EntityBehaviorSelection(List.of());
    }

    public List<EntityBehaviorKey> behaviors() {
        return behaviors;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof EntityBehaviorSelection selection
            && behaviors.equals(selection.behaviors);
    }

    @Override
    public int hashCode() {
        return behaviors.hashCode();
    }

    @Override
    public String toString() {
        return "EntityBehaviorSelection" + behaviors;
    }
}
