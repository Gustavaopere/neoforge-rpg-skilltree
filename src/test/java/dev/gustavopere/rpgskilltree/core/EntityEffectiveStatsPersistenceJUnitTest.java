package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class EntityEffectiveStatsPersistenceJUnitTest {
    @Test
    void schemaV4RoundTripsResolvedEffectiveStatsWithoutRecomputation() {
        EntityEffectiveStatsSnapshot effectiveStats = EntityEffectiveStatsSnapshot.of(Map.of(
            CanonicalStatKey.of("minecraft:max_health"), new BigDecimal("37.5"),
            CanonicalStatKey.of("minecraft:attack_damage"), new BigDecimal("8.25")
        ));
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 3L, -2L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                20L,
                OptionalLong.of(25L),
                25L,
                29L,
                29L
            ),
            2L,
            Optional.of(new MobRaritySelection(MobRarityKey.of("rpgskilltree:veteran"), 2L)),
            1234L,
            Optional.of(effectiveStats),
            MobAffixSelection.empty(),
            EntityBehaviorSelection.empty()
        );

        assertEquals(4, EntityScalingStateCodec.CURRENT_VERSION);
        EntityScalingState decoded = EntityScalingStateCodec.decodeState(EntityScalingStateCodec.encode(state));
        assertEquals(Optional.of(effectiveStats), decoded.effectiveStats());
        assertEquals(state.entityLevel(), decoded.entityLevel());
        assertEquals(state.rarity(), decoded.rarity());
    }
}
