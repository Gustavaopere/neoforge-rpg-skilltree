package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.ClassRequirementPolicy;
import dev.gustavopere.rpgskilltree.core.ClassUnlockDefinition;
import dev.gustavopere.rpgskilltree.core.MasteryState;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionService;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ProviderClassAvailabilityRegistry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class ProviderIdentityAvailabilityJUnitTest {
    private static final String ARCANE_AWAKENING = "rpgskilltree:arcane_000";

    @AfterEach
    void resetAvailability() {
        ProviderClassAvailabilityRegistry.replace(Map.of());
    }

    @Test
    void providerAvailabilityFailsClosedGenerically() {
        Set<String> loaded = Set.of("irons_spellbooks");

        assertTrue(ProviderAvailabilityPolicy.allAvailable(
            Set.of("irons_spellbooks"), loaded::contains));
        assertFalse(ProviderAvailabilityPolicy.allAvailable(
            Set.of("ars_nouveau"), loaded::contains));
        assertFalse(ProviderAvailabilityPolicy.allAvailable(
            Set.of("irons_spellbooks", "ars_nouveau"), loaded::contains));
    }

    @Test
    void mageAndSorcererDeclareTheirProviderModsInData() throws IOException {
        JsonObject mage = classJson("mage");
        JsonObject sorcerer = classJson("sorcerer");

        assertTrue(requiredProviders(mage).equals(Set.of("irons_spellbooks")));
        assertTrue(requiredProviders(sorcerer).equals(Set.of("ars_nouveau")));
    }

    @Test
    void thresholdsAreReadFromCurrentDataAndRespectBelowExactAbove() throws IOException {
        assertThresholdBoundary(classJson("mage"), "irons:casting");
        assertThresholdBoundary(classJson("sorcerer"), "ars:casting");
    }

    @Test
    void unavailableProviderRevokesDerivedIdentityButPreservesMastery() throws IOException {
        JsonObject mage = classJson("mage");
        int threshold = mage.getAsJsonObject("minimum_mastery_experience").get("irons:casting").getAsInt();
        ClassUnlockDefinition definition = definition(mage, "irons:casting", threshold);
        ProgressionState eligible = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(ARCANE_AWAKENING, 1)))
            .withMastery(MasteryState.of(Map.of("irons:casting", threshold)));

        ProviderClassAvailabilityRegistry.replace(Map.of("mage", true));
        ProgressionState unlocked = ProgressionService.reconcileAutomaticClasses(
            eligible, List.of(definition)).state();
        assertTrue(unlocked.classProgression().isUnlocked("mage"));

        ProviderClassAvailabilityRegistry.replace(Map.of("mage", false));
        ProgressionState revoked = ProgressionService.reconcileAutomaticClasses(
            unlocked, List.of(definition)).state();
        assertFalse(revoked.classProgression().isUnlocked("mage"));
        assertTrue(revoked.mastery().experience("irons:casting") == threshold,
            "provider absence must not erase earned mastery");
    }

    private static void assertThresholdBoundary(JsonObject root, String masteryLane) {
        int threshold = root.getAsJsonObject("minimum_mastery_experience").get(masteryLane).getAsInt();
        ClassUnlockDefinition definition = definition(root, masteryLane, threshold);

        ProgressionState node = ProgressionState.empty().withPassiveNodes(
            PassiveNodeProgress.of(Map.of(ARCANE_AWAKENING, 1))
        );
        assertFalse(ClassRequirementPolicy.satisfied(
            node.withMastery(MasteryState.of(Map.of(masteryLane, threshold - 1))), definition));
        assertTrue(ClassRequirementPolicy.satisfied(
            node.withMastery(MasteryState.of(Map.of(masteryLane, threshold))), definition));
        assertTrue(ClassRequirementPolicy.satisfied(
            node.withMastery(MasteryState.of(Map.of(masteryLane, threshold + 1))), definition));
    }

    private static ClassUnlockDefinition definition(JsonObject root, String masteryLane, int threshold) {
        return new ClassUnlockDefinition(
            root.get("class_id").getAsString(),
            Set.of(),
            root.get("adjacent_confluence").getAsBoolean(),
            root.get("non_adjacent_bridge_cost").getAsInt(),
            Map.of(masteryLane, threshold),
            Set.of(ARCANE_AWAKENING)
        );
    }

    private static Set<String> requiredProviders(JsonObject root) {
        var providers = new java.util.LinkedHashSet<String>();
        root.getAsJsonArray("required_provider_mods")
            .forEach(value -> providers.add(value.getAsString()));
        return Set.copyOf(providers);
    }

    private static JsonObject classJson(String id) throws IOException {
        Path path = Path.of("src/main/resources/data/rpgskilltree/classes/" + id + ".json");
        try (var reader = Files.newBufferedReader(path)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }
}
