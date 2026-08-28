package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import java.util.List;
import java.util.Objects;

public final class NodeEffectCatalogReloadTest {
    public static void main(String[] args) {
        retargetedEffectKeepsOldAndNewTargetsClearable();
        repeatedEquivalentReloadDoesNotGrowCleanupHistory();
        removedEffectRemainsClearable();
        System.out.println("NodeEffectCatalogReloadTest: PASS");
    }

    private static void retargetedEffectKeepsOldAndNewTargetsClearable() {
        NodeEffectCatalog.resetForTests();
        NodeEffectCatalog.replace(List.of(effect("rpgskilltree:test/effect", "minecraft:max_health")));
        NodeEffectCatalog.replace(List.of(effect("rpgskilltree:test/effect", "minecraft:movement_speed")));

        eq(1, NodeEffectCatalog.attributeEffects().size());
        eq("minecraft:movement_speed", NodeEffectCatalog.attributeEffects().getFirst().attributeId());
        eq(List.of(
            "rpgskilltree:test/effect@minecraft:max_health",
            "rpgskilltree:test/effect@minecraft:movement_speed"
        ), cleanupKeys());
    }

    private static void repeatedEquivalentReloadDoesNotGrowCleanupHistory() {
        NodeEffectCatalog.resetForTests();
        NodeAttributeEffect effect = effect("rpgskilltree:test/effect", "minecraft:max_health");
        NodeEffectCatalog.replace(List.of(effect));
        NodeEffectCatalog.replace(List.of(effect));
        NodeEffectCatalog.replace(List.of(effect));

        eq(List.of("rpgskilltree:test/effect@minecraft:max_health"), cleanupKeys());
    }

    private static void removedEffectRemainsClearable() {
        NodeEffectCatalog.resetForTests();
        NodeEffectCatalog.replace(List.of(effect("rpgskilltree:test/effect", "minecraft:max_health")));
        NodeEffectCatalog.replace(List.of());

        eq(List.of(), NodeEffectCatalog.attributeEffects());
        eq(List.of("rpgskilltree:test/effect@minecraft:max_health"), cleanupKeys());
    }

    private static List<String> cleanupKeys() {
        return NodeEffectCatalog.clearableAttributeEffects().stream()
            .map(effect -> effect.effectId() + "@" + effect.attributeId())
            .sorted()
            .toList();
    }

    private static NodeAttributeEffect effect(String effectId, String attributeId) {
        return new NodeAttributeEffect(
            effectId,
            "rpgskilltree:test_node",
            attributeId,
            ModifierOperation.ADD_FLAT,
            1.0D
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
