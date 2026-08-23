package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class MalumMasteryCoreTest {
    public static void main(String[] args) {
        normalizesBaseAndAddonSpiritAffinities();
        rewardsReapingBySpiritAndMagnitude();
        separatesCollectionFromReaping();
        canonicalMalumAttributesResolve();
        blocksProcGeneratedSpiritPractice();
        System.out.println("MalumMasteryCoreTest: PASS");
    }

    private static void normalizesBaseAndAddonSpiritAffinities() {
        eq("malum/sacred", MalumSpiritClassifier.affinityKey("malum:sacred_spirit"));
        eq("gaze/astral", MalumSpiritClassifier.affinityKey("gaze:astral_spirit"));
        eq(
            Set.of("spirit:malum/sacred", "spirit:gaze/astral"),
            MalumSpiritClassifier.spiritTags(List.of("malum:sacred_spirit", "gaze:astral_spirit"))
        );
    }

    private static void rewardsReapingBySpiritAndMagnitude() {
        SpiritPracticeAction reaping = new SpiritPracticeAction(
            new ActionOrigin("malum:reaping", 0),
            "malum",
            "reap:minecraft:zombie",
            Set.of("reaping", "spirit:malum/wicked", "spirit:malum/arcane"),
            4
        );
        var awards = MasteryPolicies.forMalum(reaping);
        Set<String> lanes = new HashSet<>(awards.stream().map(MasteryAward::laneId).toList());
        eq(Set.of(
            "malum:spirit_arcana",
            "occult:practice",
            "malum:reaping",
            "malum:spirit/malum/wicked",
            "malum:spirit/malum/arcane"
        ), lanes);
        eq(6, awards.stream().filter(a -> a.laneId().equals("malum:reaping")).findFirst().orElseThrow().experience());
    }

    private static void separatesCollectionFromReaping() {
        SpiritPracticeAction collection = new SpiritPracticeAction(
            new ActionOrigin("malum:collection", 0),
            "malum",
            "natural_spirit",
            Set.of("collection"),
            1
        );
        eq(
            Set.of("malum:spirit_arcana", "malum:collection"),
            new HashSet<>(MasteryPolicies.forMalum(collection).stream().map(MasteryAward::laneId).toList())
        );
    }

    private static void canonicalMalumAttributesResolve() {
        var catalog = CanonicalStatCatalog.defaults();
        eq("malum:geas_limit", catalog.resolve("malum:geas_limit").id());
        eq("malum:spirit_spoils", catalog.resolve("malum:spirit_spoils").id());
        eq("malum:soul_ward_capacity", catalog.resolve("malum:soul_ward_capacity").id());
    }

    private static void blocksProcGeneratedSpiritPractice() {
        SpiritPracticeAction action = new SpiritPracticeAction(
            new ActionOrigin("malum:reaping", 0),
            "malum",
            "reap:minecraft:skeleton",
            Set.of("reaping"),
            1
        );
        eq(List.of(), MasteryPolicies.forMalum(action.withOrigin(action.origin().child("rpgskilltree:secondary"))));
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
