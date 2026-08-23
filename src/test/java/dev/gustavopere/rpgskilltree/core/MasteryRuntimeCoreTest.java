package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;

public final class MasteryRuntimeCoreTest {
    public static void main(String[] args) {
        awardsAccumulateAcrossAndWithinMasteryLanes();
        emptyAwardsLeaveStateUnchanged();
        System.out.println("MasteryRuntimeCoreTest: PASS");
    }

    static void awardsAccumulateAcrossAndWithinMasteryLanes() {
        MasteryState start = MasteryState.of(Map.of("magic:casting", 10));
        MasteryState next = MasteryAwardService.apply(start, List.of(
            new MasteryAward("magic:casting", 2, "irons_spellbooks:fireball"),
            new MasteryAward("irons:fire", 5, "irons_spellbooks:fireball"),
            new MasteryAward("irons:fire", 3, "addon:flame_wave")
        ));

        eq(12, next.experience("magic:casting"));
        eq(8, next.experience("irons:fire"));
        eq(10, start.experience("magic:casting"));
        eq(0, start.experience("irons:fire"));
    }

    static void emptyAwardsLeaveStateUnchanged() {
        MasteryState start = MasteryState.of(Map.of("ars:projectile", 7));
        eq(start, MasteryAwardService.apply(start, List.of()));
    }

    static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
