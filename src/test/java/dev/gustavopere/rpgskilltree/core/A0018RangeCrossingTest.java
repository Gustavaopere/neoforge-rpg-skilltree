package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class A0018RangeCrossingTest {
    public static void main(String[] args) {
        var ranks = CombatPerkRanks.of(Map.of("A0018", 1));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("player", 3, 0L, 7_000L);

        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", false, false, ranks, state, 80, 100L);
        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", true, false, ranks, state, 80, 200L);

        require(state.consumeLineWindow("player", "target", 300L),
            "A0018 must open on reliable outside-to-inside crossing even when A0017 advancing condition is false");
        require(!state.consumeInterceptWindow("player", "target", 300L),
            "A0017 intercept window must remain closed when target is not advancing");
        System.out.println("A0018RangeCrossingTest: PASS");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
