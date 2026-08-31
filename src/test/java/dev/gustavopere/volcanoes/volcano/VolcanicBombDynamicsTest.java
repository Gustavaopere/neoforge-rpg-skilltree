package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicBombDynamicsTest {
    @Test
    void bombAdvancesBallisticallyWithGravityDragAndFiniteLifetime() {
        VolcanicBombState state = VolcanicBombState.fromLaunch(new VolcanicBombLaunch(
                UUID.fromString("a7aad696-fc58-4490-8b14-3dc4aa9dd8d0"),
                new Vec3(0.5, 100.5, 0.5),
                new Vec3(0.8, 1.4, 0.2),
                80L));

        VolcanicBombState next = VolcanicBombDynamics.step(state);

        assertTrue(next.position().x > state.position().x);
        assertTrue(next.position().y > state.position().y);
        assertTrue(next.velocity().y < state.velocity().y);
        assertTrue(next.velocity().length() <= state.velocity().length() + 0.05);
        assertTrue(next.active());
    }

    @Test
    void bombExpiresAtConfiguredLifetime() {
        VolcanicBombState state = new VolcanicBombState(
                UUID.randomUUID(),
                Vec3.ZERO,
                new Vec3(0.2, 0.1, 0.0),
                4L,
                5L);

        VolcanicBombState next = VolcanicBombDynamics.step(state);

        assertFalse(next.active());
    }
}
