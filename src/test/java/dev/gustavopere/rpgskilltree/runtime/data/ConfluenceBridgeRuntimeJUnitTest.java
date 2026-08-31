package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.ClassProgressionState;
import dev.gustavopere.rpgskilltree.core.ClassUnlockDefinition;
import dev.gustavopere.rpgskilltree.core.ClassUnlockResolver;
import dev.gustavopere.rpgskilltree.core.FinalTriadProgress;
import dev.gustavopere.rpgskilltree.core.PassivePointLedger;
import dev.gustavopere.rpgskilltree.core.PassivePointSource;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ProviderClassAvailabilityRegistry;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.client.ClientClassCatalog;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class ConfluenceBridgeRuntimeJUnitTest {
    private static final Path CLASSES = Path.of("src/main/resources/data/rpgskilltree/classes");

    @AfterEach
    void resetCatalogs() {
        ClassRuleCatalog.replace(List.of());
        ProviderClassAvailabilityRegistry.replace(Map.of());
        TreeRuleCatalog.replace(List.of());
    }

    @Test
    void everyHybridDefinitionRequiresTheWholePathAndCurrentDistantBridgesCostTen() throws IOException {
        ClassRulesReloader.load(classResources(), provider -> true);
        List<ClassUnlockDefinition> hybrids = ClassRuleCatalog.definitions().stream()
            .filter(definition -> definition.requiredCompletedDomains().size() >= 2)
            .toList();
        assertFalse(hybrids.isEmpty(), "the current class data must contain hybrid definitions");

        for (ClassUnlockDefinition definition : hybrids) {
            List<ProgressionDomain> required = definition.requiredCompletedDomains().stream().sorted().toList();
            ProgressionDomain first = required.getFirst();
            FinalTriadProgress halfPath = FinalTriadProgress.of(Map.of(first, List.of(3, 3, 3)));
            var halfResult = ClassUnlockResolver.evaluate(halfPath, definition, 100);
            assertFalse(halfResult.unlockable(), definition.classId() + " must not unlock from a partial path");
            assertEquals(Set.copyOf(required.subList(1, required.size())), halfResult.missingCompletedDomains());

            Map<ProgressionDomain, List<Integer>> completed = new LinkedHashMap<>();
            for (ProgressionDomain domain : required) completed.put(domain, List.of(3, 3, 3));
            FinalTriadProgress wholePath = FinalTriadProgress.of(completed);

            if (definition.adjacentConfluence()) {
                assertEquals(0, definition.nonAdjacentBridgeCost(),
                    definition.classId() + " adjacent confluence must be natural");
                assertTrue(ClassUnlockResolver.evaluate(wholePath, definition, 0).unlockable());
            } else {
                assertEquals(10, definition.nonAdjacentBridgeCost(),
                    definition.classId() + " current distant bridge cost drifted");
                var ninePoints = ClassUnlockResolver.evaluate(wholePath, definition, 9);
                assertFalse(ninePoints.unlockable());
                assertEquals(1, ninePoints.missingBridgePoints());
                assertTrue(ClassUnlockResolver.evaluate(wholePath, definition, 10).unlockable());
            }
        }
    }

    @Test
    void paidConfluencesStayVisibleWhileTheirDomainRequirementsAreStillMissing() {
        var visible = ClientClassCatalog.visibleFor(ProgressionState.empty());

        assertEquals(ClientClassCatalog.entries().size(), visible.size(),
            "the UI must show paid confluences before both domains are complete");
        assertTrue(visible.stream().allMatch(view -> !view.result().missingCompletedDomains().isEmpty()));
        assertTrue(visible.stream().allMatch(view -> view.entry().definition().requiredCompletedDomains().size() >= 2));
    }

    @Test
    void liveDerivedReconciliationRevokesBrokenPaidBridgeAndRefundsItsInvestment() throws Exception {
        ClassUnlockDefinition geomancer = new ClassUnlockDefinition(
            "geomancer",
            Set.of(ProgressionDomain.ARCANE, ProgressionDomain.MINING),
            false,
            10
        );
        ClassRuleCatalog.replace(List.of(geomancer));
        TreeRuleCatalog.replace(List.of());

        ProgressionState brokenPath = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty()
                .award(PassivePointSource.ADMIN, 20)
                .spend(10))
            .withClassProgression(ClassProgressionState.of(
                Set.of("geomancer"),
                Set.of("geomancer")
            ))
            .withFinalTriads(FinalTriadProgress.of(Map.of(
                ProgressionDomain.ARCANE, List.of(3, 3, 3)
            )));

        ProgressionState reconciled = reconcileLive(brokenPath);

        assertFalse(reconciled.classProgression().isUnlocked("geomancer"));
        assertFalse(reconciled.classProgression().bridgePaid("geomancer"));
        assertEquals(20, reconciled.passivePoints().available());
        assertEquals(0, reconciled.passivePoints().spent());
    }

    private static ProgressionState reconcileLive(ProgressionState state) throws Exception {
        var method = PlayerProgressionRuntime.class.getDeclaredMethod(
            "reconcileDerivedState", ProgressionState.class);
        method.setAccessible(true);
        return (ProgressionState) method.invoke(null, state);
    }

    private static Map<ResourceLocation, JsonElement> classResources() throws IOException {
        Map<ResourceLocation, JsonElement> resources = new LinkedHashMap<>();
        try (var paths = Files.list(CLASSES)) {
            for (Path path : paths.filter(file -> file.getFileName().toString().endsWith(".json"))
                .sorted()
                .toList()) {
                String fileName = path.getFileName().toString();
                String id = fileName.substring(0, fileName.length() - ".json".length());
                try (var reader = Files.newBufferedReader(path)) {
                    resources.put(
                        ResourceLocation.fromNamespaceAndPath("rpgskilltree", id),
                        JsonParser.parseReader(reader)
                    );
                }
            }
        }
        return resources;
    }
}
