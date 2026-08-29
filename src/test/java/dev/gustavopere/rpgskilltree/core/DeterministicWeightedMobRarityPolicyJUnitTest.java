package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class DeterministicWeightedMobRarityPolicyJUnitTest {
    private static final MobRaritySelection COMMON = selection("rpgskilltree:common", 0L);
    private static final MobRaritySelection ELITE = selection("rpgskilltree:elite", 3L);
    private static final MobRaritySelection FALLBACK = selection("rpgskilltree:fallback", 0L);
    private static final MobRaritySelection BOSS = selection("rpgskilltree:boss", 7L);

    @Test
    void sameContextAndSeedAlwaysChooseSameWeightedEntry() {
        DeterministicWeightedMobRarityPolicy policy = new DeterministicWeightedMobRarityPolicy(
            List.of(
                new MobRarityRule(COMMON, 3L, 0L, Long.MAX_VALUE, Set.of()),
                new MobRarityRule(ELITE, 1L, 10L, Long.MAX_VALUE, Set.of(EntityArchetype.HOSTILE))
            ),
            FALLBACK,
            BOSS
        );

        MobRarityContext commonContext = context(15L, EntityArchetype.HOSTILE, 2L);
        MobRarityContext eliteContext = context(15L, EntityArchetype.HOSTILE, 3L);

        assertEquals(COMMON, policy.select(commonContext));
        assertEquals(COMMON, policy.select(commonContext));
        assertEquals(ELITE, policy.select(eliteContext));
        assertEquals(ELITE, policy.select(eliteContext));
    }

    @Test
    void floorAndArchetypeFilteringAreAppliedBeforeWeightedSelection() {
        DeterministicWeightedMobRarityPolicy policy = new DeterministicWeightedMobRarityPolicy(
            List.of(
                new MobRarityRule(COMMON, 1L, 0L, Long.MAX_VALUE, Set.of()),
                new MobRarityRule(ELITE, 100L, 10L, Long.MAX_VALUE, Set.of(EntityArchetype.HOSTILE))
            ),
            FALLBACK,
            BOSS
        );

        assertEquals(COMMON, policy.select(context(9L, EntityArchetype.HOSTILE, 1L)));
        assertEquals(COMMON, policy.select(context(99L, EntityArchetype.PASSIVE, 1L)));
    }

    @Test
    void noMatchingRuleUsesBossSpecificOrGeneralFallbackWithoutMixingArchetypeIntoRarity() {
        DeterministicWeightedMobRarityPolicy policy = new DeterministicWeightedMobRarityPolicy(
            List.of(
                new MobRarityRule(ELITE, 1L, 0L, Long.MAX_VALUE, Set.of(EntityArchetype.HOSTILE))
            ),
            FALLBACK,
            BOSS
        );

        assertEquals(BOSS, policy.select(context(100L, EntityArchetype.BOSS, 5L)));
        assertEquals(FALLBACK, policy.select(context(100L, EntityArchetype.PASSIVE, 5L)));
    }

    @Test
    void invalidRulesAndMatchingWeightOverflowFailClosed() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new MobRarityRule(COMMON, 0L, 0L, Long.MAX_VALUE, Set.of())
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new MobRarityRule(COMMON, 1L, -1L, Long.MAX_VALUE, Set.of())
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new MobRarityRule(COMMON, 1L, 10L, 9L, Set.of())
        );

        DeterministicWeightedMobRarityPolicy overflow = new DeterministicWeightedMobRarityPolicy(
            List.of(
                new MobRarityRule(COMMON, Long.MAX_VALUE, 0L, Long.MAX_VALUE, Set.of()),
                new MobRarityRule(ELITE, Long.MAX_VALUE, 0L, Long.MAX_VALUE, Set.of())
            ),
            FALLBACK,
            BOSS
        );
        assertThrows(
            IllegalStateException.class,
            () -> overflow.select(context(1L, EntityArchetype.HOSTILE, 0L))
        );
    }

    private static MobRarityContext context(long floor, EntityArchetype archetype, long seed) {
        return new MobRarityContext(EntityLevelContext.nativeOnly(floor, archetype), seed);
    }

    private static MobRaritySelection selection(String id, long levelBonus) {
        return new MobRaritySelection(MobRarityKey.of(id), levelBonus);
    }
}
