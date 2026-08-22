package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record ClassChoiceDefinition(String choiceId, String requiredClassId, String groupId, int defaultGroupCapacity) {
    public ClassChoiceDefinition {
        Objects.requireNonNull(choiceId);
        Objects.requireNonNull(requiredClassId);
        Objects.requireNonNull(groupId);
        if (choiceId.isBlank() || requiredClassId.isBlank() || groupId.isBlank()) throw new IllegalArgumentException("choice identifiers must not be blank");
        if (defaultGroupCapacity <= 0) throw new IllegalArgumentException("defaultGroupCapacity must be positive");
    }
}
