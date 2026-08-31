package dev.gustavopere.volcanoes.environment;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

/** Centralizes subject-level exemptions so hook behavior is not scattered across hazard logic. */
public final class RespirationSubjectPolicy {
    private RespirationSubjectPolicy() {
    }

    public static boolean exempt(LivingEntity entity) {
        Objects.requireNonNull(entity, "entity");
        boolean creativeOrAbilityInvulnerable =
                entity instanceof Player player && player.getAbilities().invulnerable;
        boolean tagged = entity.getType().is(AtmosphereTags.DOES_NOT_BREATHE);
        return exempt(creativeOrAbilityInvulnerable, false, tagged);
    }

    static boolean exempt(boolean creativeOrAbilityInvulnerable, boolean taggedDoesNotBreathe) {
        return exempt(creativeOrAbilityInvulnerable, false, taggedDoesNotBreathe);
    }

    /**
     * Test seam keeping damage invulnerability explicit: damage immunity is not a breathing trait.
     * The middle argument is intentionally ignored so callers cannot conflate the two domains.
     */
    static boolean exempt(
            boolean creativeOrAbilityInvulnerable,
            boolean entityInvulnerable,
            boolean taggedDoesNotBreathe
    ) {
        return creativeOrAbilityInvulnerable || taggedDoesNotBreathe;
    }
}
