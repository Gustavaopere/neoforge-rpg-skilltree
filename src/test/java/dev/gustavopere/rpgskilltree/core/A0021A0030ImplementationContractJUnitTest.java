package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0021A0030ImplementationContractJUnitTest {
    @Test
    void a0022IdleDecayDoesNotRequireALiveCombatTarget() {
        var state = new A0021A0040CombatState();
        for (int i = 0; i < 4; i++) state.addFlow("player", 2, 0L);
        state.recordHorizontalMovement("player", 0L);

        state.tickFlow("player", false, 2_999L);
        assertEquals(4, state.flow("player", 2_999L));

        state.tickFlow("player", false, 3_000L);
        assertEquals(3, state.flow("player", 3_000L),
            "A0022 must start losing one Flow per second after 3 s idle even without a target");

        state.tickFlow("player", false, 4_000L);
        assertEquals(2, state.flow("player", 4_000L));
    }

    @Test
    void a0025AwardsTenXpOnlyForANewProviderNativeHostileType() {
        var first = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", true);
        assertEquals(1, first.size());
        assertEquals("epicfight:heavy", first.getFirst().laneId());
        assertEquals(10, first.getFirst().experience());

        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", false).isEmpty(),
            "repeating the same hostile entity type must grant zero mastery");
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, false, true, 4.0D, "minecraft:skeleton", true).isEmpty(),
            "indirect damage must not grant A0025 mastery");
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.DAGGER, true, true, 4.0D, "minecraft:skeleton", true).isEmpty(),
            "A0025 discovery must stay on the provider-native HAMMER family");
    }

    @Test
    void a0025DiscoveryKeyIsStablePerEntityType() {
        assertEquals(
            "mastery/epicfight:heavy/entity_type/minecraft:zombie",
            A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.HAMMER, "minecraft:zombie").orElseThrow());
        assertTrue(A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.DAGGER, "minecraft:zombie").isEmpty());
    }
}
