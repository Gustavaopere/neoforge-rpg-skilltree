package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class GoetyMasteryCoreTest {
    public static void main(String[] args) {
        classifiesGoetySpellTypes();
        awardsOccultAndProviderMastery();
        rejectsProcGeneratedMastery();
        System.out.println("GoetyMasteryCoreTest: PASS");
    }

    private static void classifiesGoetySpellTypes() {
        Set<String> tags = GoetySpellClassifier.classify(List.of("NECROMANCY", "goety:VOID", "none"), true);
        eq(Set.of("necromancy", "void", "summoning"), tags);
        eq("necromancy", GoetySpellClassifier.primaryDiscipline(tags));
        eq("summoning", GoetySpellClassifier.primaryDiscipline(Set.of("summoning")));
        eq("dark_arts", GoetySpellClassifier.primaryDiscipline(Set.of()));
    }

    private static void awardsOccultAndProviderMastery() {
        SpellAction action = new SpellAction(
            new ActionOrigin("goety:cast", 0),
            "goety",
            "goety:necromancy_focus",
            "necromancy",
            Set.of("necromancy", "summoning"),
            12
        );
        Set<String> lanes = new HashSet<>(MasteryPolicies.forGoety(action).stream().map(MasteryAward::laneId).toList());
        eq(Set.of(
            "occult:practice",
            "goety:casting",
            "goety:soul_spending",
            "goety:necromancy",
            "goety:summoning"
        ), lanes);
    }

    private static void rejectsProcGeneratedMastery() {
        SpellAction action = new SpellAction(
            new ActionOrigin("goety:cast", 0),
            "goety",
            "addon:custom_focus",
            "void",
            Set.of("void"),
            5
        );
        eq(List.of(), MasteryPolicies.forGoety(action.withOrigin(action.origin().child("rpgskilltree:echo"))));
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
