package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalLong;

public final class EntityScalingDecisionServiceTest {
    public static void main(String[] args) {
        completeDecisionUsesOneCausalOrderAndProducesPersistableState();
        hugeLevelsAndDeterministicSeedRemainStable();
        policyFailuresRemainFailClosed();
        System.out.println("EntityScalingDecisionServiceTest: PASS");
    }

    private static void completeDecisionUsesOneCausalOrderAndProducesPersistableState() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        long seed = 0x1234ABCDL;
        ArrayList<String> order = new ArrayList<>();

        EntityScalingDecisionRequest request = new EntityScalingDecisionRequest(
            TerritoryKey.of("minecraft:overworld", 3L, 7L),
            NativeAreaLevelPlan.of(5L, List.of(
                NativeAreaLevelContribution.of("rpgskilltree:structure", 20L)
            )),
            List.of(
                new RelevantPlayerCandidate("beginner", 10L, 25L, true, false),
                new RelevantPlayerCandidate("unrelated-veteran", 800L, 36L, false, false)
            ),
            RelevantPlayerCandidate::engaged,
            candidates -> OptionalLong.of(candidates.stream().mapToLong(RelevantPlayerCandidate::level).max().orElseThrow()),
            EntityArchetype.HOSTILE,
            2L,
            seed,
            context -> {
                order.add("rarity");
                eq(25L, context.baseFloor());
                eq(seed, context.deterministicSeed());
                return new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 3L);
            },
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            Map.of(EntityArchetype.HOSTILE, context -> {
                order.add("stats");
                eq(30L, context.entityLevel());
                return Map.of(health, stat -> stat.providerValue().add(BigDecimal.valueOf(context.entityLevel())));
            }),
            context -> {
                order.add("affixes");
                eq(30L, context.entityLevel());
                eq(MobRarityKey.of("rpgskilltree:elite"), context.rarity().rarity());
                return new MobAffixSelection(List.of(MobAffixKey.of("rpgskilltree:armored")));
            },
            context -> {
                order.add("behaviors");
                eq(List.of(MobAffixKey.of("rpgskilltree:armored")), context.affixes().affixes());
                return new EntityBehaviorSelection(List.of(EntityBehaviorKey.of("rpgskilltree:combat/pressure")));
            }
        );

        EntityScalingDecisionResult result = EntityScalingDecisionService.resolve(request);
        eq(List.of("rarity", "stats", "affixes", "behaviors"), order);
        eq(25L, result.nativeArea().resolvedLevel());
        eq(OptionalLong.of(10L), result.relevantPlayers().relevantPlayerLevel());
        eq(MobRarityKey.of("rpgskilltree:elite"), result.rarity().rarity());
        eq(30L, result.scaledStats().entityLevel());
        decimalEq("50", result.scaledStats().effectiveStats().value(health));

        EntityScalingState state = result.state();
        eq(request.territoryKey(), state.territory());
        eq(30L, state.entityLevel());
        eq(2L, state.variance());
        eq(seed, state.deterministicSeed());
        eq(result.rarity(), state.rarity().orElseThrow());
        eq(List.of(MobAffixKey.of("rpgskilltree:armored")), state.affixes().affixes());
        eq(List.of(EntityBehaviorKey.of("rpgskilltree:combat/pressure")), state.behaviors().behaviors());
    }

    private static void hugeLevelsAndDeterministicSeedRemainStable() {
        long huge = 5_000_000_000L;
        long seed = Long.MIN_VALUE + 77L;
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");

        EntityScalingDecisionResult result = EntityScalingDecisionService.resolve(new EntityScalingDecisionRequest(
            TerritoryKey.of("minecraft:the_end", Long.MAX_VALUE, Long.MIN_VALUE),
            NativeAreaLevelPlan.of(huge, List.of()),
            List.of(),
            candidate -> false,
            candidates -> OptionalLong.empty(),
            EntityArchetype.BOSS,
            0L,
            seed,
            context -> new MobRaritySelection(MobRarityKey.of("rpgskilltree:boss"), 0L),
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("40"))),
            Map.of(EntityArchetype.BOSS, context -> Map.of(health, stat -> stat.providerValue())),
            context -> MobAffixSelection.empty(),
            context -> EntityBehaviorSelection.empty()
        ));

        eq(huge, result.state().entityLevel());
        eq(seed, result.state().deterministicSeed());
        decimalEq("40", result.scaledStats().effectiveStats().value(health));
    }

    private static void policyFailuresRemainFailClosed() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        EntityScalingDecisionRequest nullRarity = baseRequest(
            health,
            context -> null,
            context -> MobAffixSelection.empty(),
            context -> EntityBehaviorSelection.empty()
        );
        expect(IllegalStateException.class, () -> EntityScalingDecisionService.resolve(nullRarity));

        EntityScalingDecisionRequest nullAffix = baseRequest(
            health,
            context -> new MobRaritySelection(MobRarityKey.of("rpgskilltree:normal"), 0L),
            context -> null,
            context -> EntityBehaviorSelection.empty()
        );
        expect(IllegalStateException.class, () -> EntityScalingDecisionService.resolve(nullAffix));

        EntityScalingDecisionRequest nullBehavior = baseRequest(
            health,
            context -> new MobRaritySelection(MobRarityKey.of("rpgskilltree:normal"), 0L),
            context -> MobAffixSelection.empty(),
            context -> null
        );
        expect(IllegalStateException.class, () -> EntityScalingDecisionService.resolve(nullBehavior));
    }

    private static EntityScalingDecisionRequest baseRequest(
        CanonicalStatKey health,
        MobRarityPolicy rarity,
        MobAffixPolicy affixes,
        EntityBehaviorPolicy behaviors
    ) {
        return new EntityScalingDecisionRequest(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            NativeAreaLevelPlan.of(1L, List.of()),
            List.of(),
            candidate -> false,
            candidates -> OptionalLong.empty(),
            EntityArchetype.PASSIVE,
            0L,
            1L,
            rarity,
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            Map.of(EntityArchetype.PASSIVE, context -> Map.of(health, stat -> stat.providerValue())),
            affixes,
            behaviors
        );
    }

    private static void decimalEq(String expected, BigDecimal actual) {
        BigDecimal expectedDecimal = new BigDecimal(expected);
        if (expectedDecimal.compareTo(actual) != 0) throw new AssertionError(expectedDecimal + " != " + actual);
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
