package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.NodePurchaseResult;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
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
