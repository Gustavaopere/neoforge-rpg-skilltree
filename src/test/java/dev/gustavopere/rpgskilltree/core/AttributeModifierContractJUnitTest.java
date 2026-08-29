package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectCatalog;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AttributeModifierContractJUnitTest {
    private static final double EPSILON = 1.0e-12;

    @AfterEach
    void clearCurrentCatalog() {
        NodeEffectCatalog.replace(List.of());
    }

    @Test
    void modifierOperationsComposeFromCanonicalBaseWithoutDrift() {
        List<ModifierSpec> specs = List.of(
            new ModifierSpec("health", ModifierOperation.ADD_FLAT, 10.0, "node:flat", 0),
            new ModifierSpec("health", ModifierOperation.ADD_PERCENT_BASE, 0.20, "node:percent_base", 0),
            new ModifierSpec("health", ModifierOperation.MULTIPLY_TOTAL, 0.50, "node:multiply_total", 0)
        );

        ResolvedModifier first = ModifierResolver.resolve(100.0, specs);
        ResolvedModifier reapplied = ModifierResolver.resolve(100.0, specs);

        assertEquals(195.0, first.value(), EPSILON);
        assertEquals(first, reapplied);
        assertEquals(
            List.of("node:flat", "node:multiply_total", "node:percent_base"),
            first.sourceIds()
        );
    }

    @Test
    void rankChangesReuseStableEffectIdentityInsteadOfCreatingStackedModifiers() {
        NodeAttributeEffect effect = new NodeAttributeEffect(
            "rpgskilltree:node/martial_000/attack_damage",
            "rpgskilltree:martial_000",
            "minecraft:generic.attack_damage",
            ModifierOperation.ADD_FLAT,
            0.35
        );

        var rankOne = NodeEffectResolver.resolveAttributes(
            PassiveNodeProgress.of(Map.of("rpgskilltree:martial_000", 1)),
            List.of(effect)
        );
        var rankThree = NodeEffectResolver.resolveAttributes(
            PassiveNodeProgress.of(Map.of("rpgskilltree:martial_000", 3)),
            List.of(effect)
        );

        assertEquals(1, rankOne.size());
        assertEquals(1, rankThree.size());
        assertEquals(rankOne.getFirst().effectId(), rankThree.getFirst().effectId());
        assertEquals(0.35, rankOne.getFirst().amount(), EPSILON);
        assertEquals(1.05, rankThree.getFirst().amount(), EPSILON);
    }

    @Test
    void catalogRetainsHistoricalTargetsSoReloadCanRemoveOrphanedModifiers() {
        NodeAttributeEffect oldTarget = new NodeAttributeEffect(
            "rpgskilltree:node/agility_000/mobility",
            "rpgskilltree:agility_000",
            "minecraft:generic.movement_speed",
            ModifierOperation.ADD_PERCENT_BASE,
            0.02
        );
        NodeAttributeEffect movedTarget = new NodeAttributeEffect(
            "rpgskilltree:node/agility_000/mobility",
            "rpgskilltree:agility_000",
            "minecraft:generic.attack_speed",
            ModifierOperation.ADD_PERCENT_BASE,
            0.02
        );

        NodeEffectCatalog.replace(List.of(oldTarget));
        NodeEffectCatalog.replace(List.of(movedTarget));

        assertEquals(List.of(movedTarget), NodeEffectCatalog.attributeEffects());
        assertEquals(List.of(movedTarget, oldTarget), NodeEffectCatalog.clearableAttributeEffects());

        NodeEffectCatalog.replace(List.of());
        assertEquals(List.of(), NodeEffectCatalog.attributeEffects());
        assertEquals(List.of(movedTarget, oldTarget), NodeEffectCatalog.clearableAttributeEffects());
    }

    @Test
    void duplicateEffectIdsAreRejectedBeforeRuntimeApplication() {
        NodeAttributeEffect first = new NodeAttributeEffect(
            "rpgskilltree:node/mining_000/mining_speed",
            "rpgskilltree:mining_000",
            "apothic_attributes:mining_speed",
            ModifierOperation.ADD_PERCENT_BASE,
            0.04
        );
        NodeAttributeEffect duplicate = new NodeAttributeEffect(
            first.effectId(),
            "rpgskilltree:mining_001",
            "minecraft:generic.luck",
            ModifierOperation.ADD_FLAT,
            0.25
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> NodeEffectCatalog.replace(List.of(first, duplicate))
        );
    }
}
