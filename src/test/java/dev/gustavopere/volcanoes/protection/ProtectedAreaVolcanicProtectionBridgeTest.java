package dev.gustavopere.volcanoes.protection;

import dev.gustavopere.volcanoes.volcano.VolcanicProtectionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProtectedAreaVolcanicProtectionBridgeTest {
    @Test
    void implementsCanonicalStage03ProtectionService() {
        assertTrue(VolcanicProtectionService.class.isAssignableFrom(
                ProtectedAreaVolcanicProtectionBridge.class));
    }

    @Test
    void propagatesFailClosedAuthorityFromProtectedAreaService() {
        ProtectedAreaVolcanicProtectionBridge empty = new ProtectedAreaVolcanicProtectionBridge(
                ProtectedAreaService.empty());
        assertFalse(empty.allowsTerrainMutation());

        ProtectedAreaVolcanicProtectionBridge authoritative = new ProtectedAreaVolcanicProtectionBridge(
                ProtectedAreaService.authoritative((dimension, pos) -> false));
        assertTrue(authoritative.allowsTerrainMutation());
    }
}
