package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class ProviderIdentityAvailabilityJUnitTest {
    private static final String ARCANE_AWAKENING = "rpgskilltree:arcane_000";

    @AfterEach
    void resetAvailability() {
        ProviderClassAvailabilityRegistry.replace(Map.of());
        ClassRuleCatalog.replace(java.util.List.of());
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
    void loaderPublishesProviderAvailabilityIntoCanonicalClassResolution() throws IOException {
        JsonObject mageJson = classJson("mage");
        JsonObject sorcererJson = classJson("sorcerer");
        int ironThreshold = mageJson.getAsJsonObject("minimum_mastery_experience").get("irons:casting").getAsInt();
        int arsThreshold = sorcererJson.getAsJsonObject("minimum_mastery_experience").get("ars:casting").getAsInt();

        Map<ResourceLocation, JsonElement> resources = new LinkedHashMap<>();
        resources.put(ResourceLocation.fromNamespaceAndPath("rpgskilltree", "mage"), mageJson);
        resources.put(ResourceLocation.fromNamespaceAndPath("rpgskilltree", "sorcerer"), sorcererJson);

        ClassRulesReloader.load(resources, Set.of("irons_spellbooks")::contains);
        assertTrue(ProviderClassAvailabilityRegistry.isAvailable("mage"));
        assertFalse(ProviderClassAvailabilityRegistry.isAvailable("sorcerer"));
        assertTrue(ClassRuleCatalog.definition("mage").isPresent());
        assertTrue(ClassRuleCatalog.definition("sorcerer").isPresent());

        ProgressionState practicedBoth = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(ARCANE_AWAKENING, 1)))
            .withMastery(MasteryState.of(Map.of(
                "irons:casting", ironThreshold,
                "ars:casting", arsThreshold
            )));
        ProgressionState ironLoaded = ProgressionService.reconcileAutomaticClasses(
            practicedBoth, ClassRuleCatalog.definitions()).state();
        assertTrue(ironLoaded.classProgression().isUnlocked("mage"));
        assertFalse(ironLoaded.classProgression().isUnlocked("sorcerer"));

        ClassRulesReloader.load(resources, Set.of("ars_nouveau")::contains);
        ProgressionState arsLoaded = ProgressionService.reconcileAutomaticClasses(
            ironLoaded, ClassRuleCatalog.definitions()).state();
        assertFalse(arsLoaded.classProgression().isUnlocked("mage"));
        assertTrue(arsLoaded.classProgression().isUnlocked("sorcerer"));
        assertTrue(arsLoaded.mastery().experience("irons:casting") == ironThreshold,
            "revoking Mage must preserve Iron mastery");
        assertTrue(arsLoaded.mastery().experience("ars:casting") == arsThreshold,
            "unlocking Sorcerer must preserve Ars mastery");
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
