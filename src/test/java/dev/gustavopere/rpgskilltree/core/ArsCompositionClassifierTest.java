package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Set;

public final class ArsCompositionClassifierTest {
    public static void main(String[] args) {
        require(ArsCompositionClassifier.classify(List.of(
            "ars_nouveau:projectile",
            "ars_nouveau:harm",
            "ars_nouveau:amplify",
            "ars_nouveau:aoe",
            "ars_nouveau:extend_time"
        )).equals(Set.of("projectile", "amplification", "aoe", "duration")), "base composition lanes");

        require(ArsCompositionClassifier.classify(List.of(
            "some_addon:homing_projectile",
            "some_addon:summon_construct",
            "some_addon:gravity_snare"
        )).equals(Set.of("projectile", "summoning", "control")), "addon semantic lanes");

        require(
            ArsCompositionClassifier.classify(List.of("some_addon:fire_bolt", "ars_nouveau:harm")).isEmpty(),
            "unrelated glyphs must not invent lanes"
        );

        System.out.println("ArsCompositionClassifierTest PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
