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
        String effectId = "rpgskilltree:test/retarget";
        NodeEffectCatalog.replace(List.of(effect(effectId, "minecraft:max_health")));
        NodeEffectCatalog.replace(List.of(effect(effectId, "minecraft:movement_speed")));

        eq(1, NodeEffectCatalog.attributeEffects().size());
        eq("minecraft:movement_speed", NodeEffectCatalog.attributeEffects().getFirst().attributeId());
        eq(List.of(
            effectId + "@minecraft:max_health",
            effectId + "@minecraft:movement_speed"
        ), cleanupKeys(effectId));
    }

    private static void repeatedEquivalentReloadDoesNotGrowCleanupHistory() {
        String effectId = "rpgskilltree:test/repeated";
        NodeAttributeEffect effect = effect(effectId, "minecraft:max_health");
        NodeEffectCatalog.replace(List.of(effect));
        NodeEffectCatalog.replace(List.of(effect));
        NodeEffectCatalog.replace(List.of(effect));

        eq(List.of(effectId + "@minecraft:max_health"), cleanupKeys(effectId));
    }

    private static void removedEffectRemainsClearable() {
        String effectId = "rpgskilltree:test/removed";
        NodeEffectCatalog.replace(List.of(effect(effectId, "minecraft:max_health")));
        NodeEffectCatalog.replace(List.of());

        eq(List.of(), NodeEffectCatalog.attributeEffects());
        eq(List.of(effectId + "@minecraft:max_health"), cleanupKeys(effectId));
    }

    private static List<String> cleanupKeys(String effectId) {
        return NodeEffectCatalog.clearableAttributeEffects().stream()
            .filter(effect -> effect.effectId().equals(effectId))
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
