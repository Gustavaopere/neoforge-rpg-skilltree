package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;

/** Pure translation of a provider-confirmed successful dodge into transient perk state. */
public final class CombatPerkDefensePolicy {
    private CombatPerkDefensePolicy() {}

    public static void onSuccessfulDodge(
        String actorId,
        WeaponFamily weaponFamily,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int weaponMastery,
        long nowMillis
    ) {
        Objects.requireNonNull(actorId);
        Objects.requireNonNull(weaponFamily);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        if (weaponMastery < 0) throw new IllegalArgumentException("weaponMastery must be non-negative");

        switch (weaponFamily) {
            case SWORD -> {
                if (ranks.learned("A0004")) {
                    state.addMomentum(actorId, 1, nowMillis);
                }
                if (ranks.learned("A0006")
                    && state.momentum(actorId) >= 5
                    && state.cooldownReady(actorId, actorId, "A0006", nowMillis)) {
                    state.setActorFlag(
                        actorId,
                        NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE,
                        Math.addExact(nowMillis, 3_000L)
                    );
                    long cooldown = weaponMastery >= 100 ? 8_000L : weaponMastery >= 90 ? 9_000L : 10_000L;
                    state.startCooldown(actorId, actorId, "A0006", nowMillis, cooldown);
                }
            }
            case DAGGER -> {
                if (ranks.learned("A0022") || ranks.learned("A0024")) {
                    state.setActorFlag(
                        actorId,
                        NotionCombatPerkState.ActorFlag.RECENT_DODGE,
                        Math.addExact(nowMillis, 2_000L)
                    );
                }
            }
            default -> {
                // A0001-A0050 define no provider-confirmed dodge trigger for the other families.
            }
        }
    }
}
