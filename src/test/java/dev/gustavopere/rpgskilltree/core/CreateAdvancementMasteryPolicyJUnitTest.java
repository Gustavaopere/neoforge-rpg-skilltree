package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.compat.create.CreateVersionContract;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

final class CreateAdvancementMasteryPolicyJUnitTest {
    @Test
    void auditedActionMilestonesMapToCanonicalCreateLanes() {
        assertLanes("mechanical_press", Set.of(
            MasteryLaneCatalog.CREATE_ENGINEERING,
            MasteryLaneCatalog.create("automation")
        ));
        assertLanes("water_wheel", Set.of(
            MasteryLaneCatalog.CREATE_ENGINEERING,
            MasteryLaneCatalog.create("kinetics")
        ));
        assertLanes("train", Set.of(
            MasteryLaneCatalog.CREATE_ENGINEERING,
            MasteryLaneCatalog.create("logistics")
        ));
    }

    @Test
    void milestoneIdentityIsPersistentAndProviderScoped() {
        var milestone = CreateAdvancementMasteryPolicy.confirmed("create", "mechanical_press").orElseThrow();
        assertEquals("mastery:create:engineering/advancement/mechanical_press", milestone.discoveryKey());
        assertEquals("create", milestone.action().provider());
        assertEquals("create:mechanical_press", milestone.action().actionId());
        assertEquals(Set.of("automation"), milestone.action().tags());
        assertTrue(milestone.awards().stream().allMatch(award -> award.experience() == 3));
    }

    @Test
    void possessionRecipesThroughputAndForeignAdvancementsFailClosed() {
        assertTrue(CreateAdvancementMasteryPolicy.confirmed("minecraft", "mechanical_press").isEmpty());
        assertTrue(CreateAdvancementMasteryPolicy.confirmed("create", "root").isEmpty());
        assertTrue(CreateAdvancementMasteryPolicy.confirmed("create", "andesite_alloy").isEmpty());
        assertTrue(CreateAdvancementMasteryPolicy.confirmed("create", "track_0").isEmpty());
        assertTrue(CreateAdvancementMasteryPolicy.confirmed("create", "track_crafting_factory").isEmpty());
        assertTrue(CreateAdvancementMasteryPolicy.confirmed("create", "recipes/mechanical_press").isEmpty());
        assertTrue(CreateAdvancementMasteryPolicy.confirmed("create", "potato_cannon").isEmpty());
    }

    @Test
    void baseCreateNeverClaimsAddonOwnedReservedLanes() {
        for (String path : CreateAdvancementMasteryPolicy.auditedAdvancementPaths()) {
            var milestone = CreateAdvancementMasteryPolicy.confirmed("create", path).orElseThrow();
            assertFalse(milestone.action().tags().contains("artillery"));
            assertFalse(milestone.action().tags().contains("aeronautics"));
            assertFalse(milestone.action().tags().contains("power"));
        }
    }

    @Test
    void versionGateIsExactForAuditedCreateRuntime() {
        assertTrue(CreateVersionContract.supportsVersion("6.0.10"));
        assertFalse(CreateVersionContract.supportsVersion("6.0.9"));
        assertFalse(CreateVersionContract.supportsVersion("6.0.11"));
        assertFalse(CreateVersionContract.supportsVersion("unknown"));
    }

    private static void assertLanes(String path, Set<String> expected) {
        var milestone = CreateAdvancementMasteryPolicy.confirmed("create", path).orElseThrow();
        Set<String> actual = milestone.awards().stream().map(MasteryAward::laneId).collect(Collectors.toSet());
        assertEquals(expected, actual);
        assertEquals(expected.size(), milestone.awards().size());
    }
}
