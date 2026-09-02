package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import dev.gustavopere.rpgskilltree.core.CharacterLevelCurve;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodeAccessResolver;
import dev.gustavopere.rpgskilltree.core.NodePurchaseResult;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class A0041A0050AvailabilityJUnitTest {
    @Test
    void unavailableNodesHaveExplicitPurchaseStatusAndServerAvailabilityBoundary() throws Exception {
        assertEquals("UNAVAILABLE_NODE", NodePurchaseResult.Status.valueOf("UNAVAILABLE_NODE").name());

        Class<?> availability = Class.forName("dev.gustavopere.rpgskilltree.runtime.CombatPerkAvailabilityRuntime");
        Method available = availability.getMethod("isCatalogCodeAvailable", String.class);
        assertFalse((boolean) available.invoke(null, "A0042"), "A0042 must fail closed until canonical eligible_kill anti-abuse exists");
        assertFalse((boolean) available.invoke(null, "A0044"), "A0044 lacks semantic draw/preparation binding");
        assertFalse((boolean) available.invoke(null, "A0047"), "A0047 inherits structural unavailability from A0044");
        assertFalse((boolean) available.invoke(null, "A0048"), "A0048 inherits structural unavailability from required predecessor A0047");
        assertFalse((boolean) available.invoke(null, "A0050"), "A0050 lacks semantic reload/preparation binding");
        assertTrue((boolean) available.invoke(null, "A0043"), "A0043 has a real BOW damage/mastery path");
        assertTrue((boolean) available.invoke(null, "A0045"), "A0045 has a canonical critical path");
        assertTrue((boolean) available.invoke(null, "A0046"), "A0046 has valid Focus producers even while optional heavy/body components stay fail closed");
        assertTrue((boolean) available.invoke(null, "A0049"), "A0049 has a real CROSSBOW damage/mastery path");
    }

    @Test
    void unavailableLegacyRankCannotSatisfyLaterNodeRequirement() throws Exception {
        String unavailableNodeId = CombatPerkNodeBinding.nodeId("A0050");
        ProgressionState persisted = ProgressionState.empty().withPassiveNodes(
            PassiveNodeProgress.of(Map.of(unavailableNodeId, 1))
        );
        NodeAccessRequirement requiresUnavailableRank = new NodeAccessRequirement(
            1,
            Set.of(),
            Map.of(),
            Set.of(),
            Set.of(),
            Set.of(),
            Map.of(unavailableNodeId, 1),
            Set.of()
        );
        assertTrue(
            NodeAccessResolver.satisfied(persisted, requiresUnavailableRank, CharacterLevelCurve.defaultCurve()),
            "fixture must prove the raw persisted legacy rank would satisfy the requirement without masking"
        );

        Method effectiveAccessState;
        try {
            effectiveAccessState = CombatPerkAvailabilityRuntime.class.getMethod("effectiveAccessState", ProgressionState.class);
        } catch (NoSuchMethodException missingBoundary) {
            fail("availability boundary must mask unavailable legacy ranks before prerequisite evaluation");
            return;
        }
        ProgressionState accessState = (ProgressionState) effectiveAccessState.invoke(null, persisted);

        assertEquals(1, persisted.passiveNodes().rank(unavailableNodeId), "persisted rank must remain stored for recovery/refund");
        assertEquals(0, accessState.passiveNodes().rank(unavailableNodeId), "unavailable legacy rank must be invisible to access checks");
        assertFalse(
            NodeAccessResolver.satisfied(accessState, requiresUnavailableRank, CharacterLevelCurve.defaultCurve()),
            "a later node must not unlock from an unavailable legacy prerequisite"
        );
    }

    @Test
    void playerPurchasePathsEvaluateRequirementsThroughAvailabilityMask() throws Exception {
        String source = Files.readString(Path.of(
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java"
        ));
        assertTrue(
            source.contains("ProgressionState accessState = CombatPerkAvailabilityRuntime.effectiveAccessState(current);")
                && source.contains("NodeAccessResolver.satisfied(\n                accessState,"),
            "trusted and network purchase paths must evaluate prerequisites against the availability-masked access state"
        );
    }

    @Test
    void dominatedShotBridgeDoesNotFabricateProjectileSpeed() throws Exception {
        String source = Files.readString(Path.of(
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/A0041A0060ProjectileEvents.java"
        ));
        assertFalse(source.contains("fullyDrawn, stableAim,\n                    true, true, now"),
            "A0047 must not claim projectile-speed availability merely because an AbstractArrow exists");
        assertFalse(source.contains("arrow.setDeltaMovement(arrow.getDeltaMovement().scale(bowShot.launchSpeedMultiplier()))"),
            "A0047 must not synthesize its provider contract by scaling vanilla projectile velocity");
    }
}
