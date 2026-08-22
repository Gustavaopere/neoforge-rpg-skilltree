package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class BossRewardRegistry {
    private final Map<String, BossRewardDefinition> namespaceDefaults;

    public BossRewardRegistry(Map<String, BossRewardDefinition> namespaceDefaults) {
        Map<String, BossRewardDefinition> copy = new HashMap<>();
        namespaceDefaults.forEach((namespace, definition) -> {
            if (namespace == null || namespace.isBlank()) throw new IllegalArgumentException("namespace must not be blank");
            copy.put(namespace, Objects.requireNonNull(definition));
        });
        this.namespaceDefaults = Map.copyOf(copy);
    }

    public static BossRewardRegistry defaults() {
        return new BossRewardRegistry(Map.of(
            "minecraft", new BossRewardDefinition("minecraft:boss", 3),
            "cataclysm", new BossRewardDefinition("cataclysm:boss", 5),
            "apotheosis", new BossRewardDefinition("apotheosis:boss", 2),
            "apothic_spawners", new BossRewardDefinition("apothic_spawners:boss", 2),
            "apothic_attributes", new BossRewardDefinition("apothic_attributes:boss", 2)
        ));
    }

    public BossRewardDefinition resolveForNamespace(String namespace) {
        BossRewardDefinition definition = namespaceDefaults.get(namespace);
        if (definition == null) throw new IllegalArgumentException("no boss reward configured for namespace: " + namespace);
        return definition;
    }

    public Map<String, BossRewardDefinition> namespaceDefaults() {
        return namespaceDefaults;
    }
}
