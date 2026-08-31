package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.InvestmentState;
import dev.gustavopere.rpgskilltree.core.MasteryState;
import dev.gustavopere.rpgskilltree.core.NodeInvestment;
import dev.gustavopere.rpgskilltree.core.NodeSpecializationGrant;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionService;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.SpecializationAvailability;
import dev.gustavopere.rpgskilltree.core.SpecializationResolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class SpecializationGatewayAvailabilityJUnitTest {
    private static final Path SPECIALIZATIONS = Path.of(
        "src/main/resources/data/rpgskilltree/specializations");
    private static final Set<String> CANONICAL_IDS = Set.of(
        "ae2_networks",
        "ars_amplification",
        "ars_aoe",
        "ars_control",
        "ars_duration",
        "ars_projectile",
        "ars_summoning",
        "create_aeronautics",
        "create_artillery",
        "create_automation",
        "create_kinetics",
        "epic_heavy",
        "epic_ranged",
        "epic_sword",
        "irons_blood",
        "irons_eldritch",
        "irons_ender",
        "irons_evocation",
        "irons_fire",
        "irons_holy",
        "irons_ice",
        "irons_lightning",
        "irons_nature",
        "oritech_mining",
        "oritech_power"
    );

    @AfterEach
    void resetCatalog() {
        SpecializationCatalog.replace(List.of());
    }

    @Test
    void canonicalTwentyFiveDefinitionsRetainProviderIdentity() throws IOException {
        SpecializationReloader.load(
            specializationResources(),
            provider -> true,
            provider -> true
        );

        assertEquals(25, SpecializationCatalog.size());
        assertEquals(CANONICAL_IDS, SpecializationCatalog.definitions().stream()
            .map(definition -> definition.specializationId())
            .collect(Collectors.toUnmodifiableSet()));
        assertEquals(Map.of(
            "irons_spellbooks", 9L,
            "ars_nouveau", 6L,
            "epicfight", 3L,
            "create", 4L,
            "oritech", 2L,
            "ae2", 1L
        ), SpecializationCatalog.definitions().stream().collect(Collectors.groupingBy(
            definition -> definition.providerId(), Collectors.counting())));
    }

    @Test
    void definitionExistenceProviderPresenceAndAdapterCompletenessAreDistinct() throws IOException {
        Set<String> loadedProviders = Set.of(
            "irons_spellbooks", "ars_nouveau", "epicfight", "create", "ae2", "oritech");
        SpecializationReloader.load(
            specializationResources(),
            loadedProviders::contains,
            SpecializationProviderRuntimePolicy::hasCompleteAdapter
        );

        assertTrue(SpecializationCatalog.definition("irons_fire").isPresent());
        var irons = SpecializationCatalog.availability("irons_fire").orElseThrow();
        assertTrue(irons.providerLoaded());
        assertTrue(irons.runtimeAdapterComplete());
        assertTrue(irons.gatewayAvailable());

        assertTrue(SpecializationCatalog.definition("create_kinetics").isPresent());
        var create = SpecializationCatalog.availability("create_kinetics").orElseThrow();
        assertTrue(create.providerLoaded());
        assertFalse(create.runtimeAdapterComplete());
        assertFalse(create.gatewayAvailable());

        assertTrue(SpecializationCatalog.definition("ae2_networks").isPresent());
        assertFalse(SpecializationCatalog.gatewayAvailable("ae2_networks"));
        assertTrue(SpecializationCatalog.definition("oritech_power").isPresent());
        assertFalse(SpecializationCatalog.gatewayAvailable("oritech_power"));
    }

    @Test
    void providerSpecificResolverFailsClosedForMissingProviderOrAdapter() throws IOException {
        SpecializationReloader.load(specializationResources(), provider -> true, provider -> true);
        var definition = SpecializationCatalog.definition("irons_fire").orElseThrow();
        var investment = InvestmentState.of(List.of(new NodeInvestment(
            "gateway",
            Map.of(),
            Set.of("gateway:irons_fire")
        )));
        var mastery = MasteryState.of(Map.of("irons:fire", 100));

        var available = SpecializationResolver.evaluate(
            Set.of("arcanist"), mastery, investment, definition,
            new SpecializationAvailability(true, true));
        assertTrue(available.unlockable());
        assertFalse(available.providerUnavailable());
        assertFalse(available.runtimeAdapterIncomplete());

        var missingProvider = SpecializationResolver.evaluate(
            Set.of("arcanist"), mastery, investment, definition,
            new SpecializationAvailability(false, true));
        assertFalse(missingProvider.unlockable());
        assertTrue(missingProvider.providerUnavailable());
        assertFalse(missingProvider.runtimeAdapterIncomplete());

        var incompleteAdapter = SpecializationResolver.evaluate(
            Set.of("arcanist"), mastery, investment, definition,
            new SpecializationAvailability(true, false));
        assertFalse(incompleteAdapter.unlockable());
        assertFalse(incompleteAdapter.providerUnavailable());
        assertTrue(incompleteAdapter.runtimeAdapterIncomplete());
    }

    @Test
    void nodeOwnedGatewayUnlockAndRevocationFollowCurrentAvailability() throws IOException {
        String nodeId = "rpgskilltree:technomancer/create_gateway";
        var grant = new NodeSpecializationGrant(nodeId, "create_kinetics", 1);
        var state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(nodeId, 1)))
            .withMastery(MasteryState.of(Map.of("create:kinetics", 80)));

        SpecializationReloader.load(
            specializationResources(),
            Set.of("create")::contains,
            provider -> false
        );
        ProgressionState blocked = ProgressionService.reconcileNodeSpecializations(
            state, List.of(grant), SpecializationCatalog::gatewayAvailable);
        assertFalse(blocked.specializations().isUnlocked("create_kinetics"));
        assertTrue(blocked.passiveNodes().learned(nodeId));

        SpecializationReloader.load(
            specializationResources(),
            Set.of("create")::contains,
            provider -> provider.equals("create")
        );
        ProgressionState unlocked = ProgressionService.reconcileNodeSpecializations(
            blocked, List.of(grant), SpecializationCatalog::gatewayAvailable);
        assertTrue(unlocked.specializations().isUnlocked("create_kinetics"));

        SpecializationReloader.load(
            specializationResources(),
            provider -> false,
            provider -> provider.equals("create")
        );
        ProgressionState revoked = ProgressionService.reconcileNodeSpecializations(
            unlocked, List.of(grant), SpecializationCatalog::gatewayAvailable);
        assertFalse(revoked.specializations().isUnlocked("create_kinetics"));
        assertTrue(revoked.passiveNodes().learned(nodeId),
            "provider loss revokes only derived specialization state");
        assertEquals(80, revoked.mastery().experience("create:kinetics"),
            "provider loss must not erase earned mastery");
    }

    @Test
    void currentAdapterPolicyDoesNotPromiseTechnologyIntegrationsThatDoNotExist() {
        assertTrue(SpecializationProviderRuntimePolicy.hasCompleteAdapter("irons_spellbooks"));
        assertTrue(SpecializationProviderRuntimePolicy.hasCompleteAdapter("ars_nouveau"));
        assertTrue(SpecializationProviderRuntimePolicy.hasCompleteAdapter("epicfight"));
        assertFalse(SpecializationProviderRuntimePolicy.hasCompleteAdapter("create"));
        assertFalse(SpecializationProviderRuntimePolicy.hasCompleteAdapter("ae2"));
        assertFalse(SpecializationProviderRuntimePolicy.hasCompleteAdapter("oritech"));
        assertFalse(SpecializationProviderRuntimePolicy.hasCompleteAdapter("unknown_provider"));
    }

    private static Map<ResourceLocation, JsonElement> specializationResources() throws IOException {
        Map<ResourceLocation, JsonElement> resources = new LinkedHashMap<>();
        try (var paths = Files.list(SPECIALIZATIONS)) {
            for (Path path : paths.filter(file -> file.getFileName().toString().endsWith(".json"))
                .sorted()
                .toList()) {
                String fileName = path.getFileName().toString();
                String resourcePath = fileName.substring(0, fileName.length() - ".json".length());
                try (var reader = Files.newBufferedReader(path)) {
                    resources.put(
                        ResourceLocation.fromNamespaceAndPath("rpgskilltree", resourcePath),
                        JsonParser.parseReader(reader)
                    );
                }
            }
        }
        return resources;
    }
}
