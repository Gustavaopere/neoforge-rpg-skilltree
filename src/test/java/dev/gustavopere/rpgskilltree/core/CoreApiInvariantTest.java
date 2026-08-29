package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class CoreApiInvariantTest {
    public static void main(String[] args) {
        identityBoundariesFailClosed();
        publicCollectionsAreImmutableDefensiveCopies();
        technicalLimitsFailClosed();
        System.out.println("CoreApiInvariantTest: PASS");
    }

    private static void identityBoundariesFailClosed() {
        expect(IllegalArgumentException.class, () -> new MasteryAward(" ", 1, "source"));
        expect(IllegalArgumentException.class, () -> new MasteryAward("arcane", 1, " "));
        expect(IllegalArgumentException.class, () -> MasteryAward.replaySafe("arcane", 1, "source", " "));
        expect(IllegalArgumentException.class, () -> MasteryState.of(Map.of(" ", 1)));

        expect(IllegalArgumentException.class, () -> ClassProgressionState.of(Set.of(" ")));
        expect(IllegalArgumentException.class, () -> ClassProgressionState.empty().unlock(" "));

        expect(IllegalArgumentException.class, () -> PassiveNodeProgress.of(Map.of(" ", 1)));
        expect(IllegalArgumentException.class, () -> PassiveNodeProgress.empty().increase(" ", 1));

        expect(IllegalArgumentException.class, () -> DiscoveryProgress.of(Set.of(" ")));
        expect(IllegalArgumentException.class, () -> DiscoveryProgress.empty().add(" "));

        expect(IllegalArgumentException.class, () -> CorePointTransaction.credit(
            " ", CorePointTransactionKind.EARN, 1L, "source", 1L));
        expect(IllegalArgumentException.class, () -> CorePointTransaction.credit(
            "transaction", CorePointTransactionKind.EARN, 1L, " ", 1L));
    }

    private static void publicCollectionsAreImmutableDefensiveCopies() {
        Map<String, Integer> masteryInput = new HashMap<>();
        masteryInput.put("arcane", 5);
        MasteryState mastery = MasteryState.of(masteryInput);
        masteryInput.put("arcane", 99);
        eq(5, mastery.experience("arcane"));
        expect(UnsupportedOperationException.class, () -> mastery.experience().put("other", 1));

        Set<String> classInput = new HashSet<>();
        classInput.add("mage");
        ClassProgressionState classes = ClassProgressionState.of(classInput);
        classInput.add("warrior");
        eq(false, classes.isUnlocked("warrior"));
        expect(UnsupportedOperationException.class, () -> classes.unlockedClassIds().add("warrior"));

        Map<String, Integer> nodeInput = new HashMap<>();
        nodeInput.put("rpgskilltree:arcane_001", 2);
        PassiveNodeProgress nodes = PassiveNodeProgress.of(nodeInput);
        nodeInput.put("rpgskilltree:arcane_001", 9);
        eq(2, nodes.rank("rpgskilltree:arcane_001"));
        expect(UnsupportedOperationException.class, () -> nodes.ranks().put("rpgskilltree:other", 1));

        Set<String> discoveryInput = new HashSet<>();
        discoveryInput.add("rpgskilltree:ancient_ruin");
        DiscoveryProgress discoveries = DiscoveryProgress.of(discoveryInput);
        discoveryInput.add("minecraft:the_end");
        eq(false, discoveries.contains("minecraft:the_end"));
        expect(UnsupportedOperationException.class, () -> discoveries.discoveredKeys().add("minecraft:the_end"));

        EnumMap<AttributeId, Long> rankInput = new EnumMap<>(AttributeId.class);
        rankInput.put(AttributeId.STRENGTH, 3L);
        AttributeRanks ranks = AttributeRanks.of(rankInput);
        rankInput.put(AttributeId.STRENGTH, 8L);
        eq(3L, ranks.rank(AttributeId.STRENGTH));
        expect(UnsupportedOperationException.class, () -> ranks.asMap().put(AttributeId.AGILITY, 1L));
    }

    private static void technicalLimitsFailClosed() {
        ProgressionRulesSnapshot rules = new ProgressionRulesSnapshot(
            41L,
            "rpgskilltree:core_api_invariant_test",
            List.of(new LevelCurveBand(0L, 100L, 2L)),
            new MainPerkBudget(3L)
        );

        expect(IllegalArgumentException.class, () -> new CharacterProgressionState(-1L, 0L));
        expect(IllegalArgumentException.class, () -> new CharacterProgressionState(0L, -1L));
        expect(IllegalArgumentException.class, () -> CharacterProgressionService.grantXp(
            CharacterProgressionState.empty(), -1L, rules.levelCurve()));
        expect(IllegalArgumentException.class, () -> MasteryState.of(Map.of("arcane", -1)));
        expect(IllegalArgumentException.class, () -> PassiveNodeProgress.of(Map.of("rpgskilltree:node", 0)));
        expect(IllegalArgumentException.class, () -> AttributeRanks.of(Map.of(AttributeId.STRENGTH, -1L)));
        expect(IllegalArgumentException.class, () -> CorePointTransaction.credit(
            "zero", CorePointTransactionKind.EARN, 0L, "source", rules.version()));
        expect(IllegalArgumentException.class, () -> CorePointTransaction.credit(
            "bad-version", CorePointTransactionKind.EARN, 1L, "source", 0L));

        AttributeRanks maxRanks = AttributeRanks.of(Map.of(AttributeId.DETERMINATION, Long.MAX_VALUE));
        expect(ArithmeticException.class, () -> maxRanks.increase(AttributeId.DETERMINATION, 1L));
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
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
