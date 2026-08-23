package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.BossRewardDefinition;
import dev.gustavopere.rpgskilltree.core.BossRewardRegistry;
import java.util.Map;
import java.util.Objects;

/** Live datapack-backed boss reward registry used by the server runtime. */
public final class BossRewardCatalog {
    private static volatile BossRewardRegistry current = BossRewardRegistry.defaults();

    private BossRewardCatalog() {}

    public static BossRewardDefinition resolveForNamespace(String namespace) {
        return current.resolveForNamespace(namespace);
    }

    public static Map<String, BossRewardDefinition> definitions() {
        return current.namespaceDefaults();
    }

    static void replace(Map<String, BossRewardDefinition> definitions) {
        Objects.requireNonNull(definitions);
        current = new BossRewardRegistry(definitions);
    }
}
