package dev.gustavopere.rpgskilltree.core;

/** Persisted anti-reset cooldown deadlines for the stateful A0104-A0106 defensive perks. */
public record CombatPerkCooldownState(
    long secondWindCooldownUntilTick,
    long reactiveShellCooldownUntilTick,
    long emergencyGuardCooldownUntilTick
) {
    private static final CombatPerkCooldownState EMPTY = new CombatPerkCooldownState(0L, 0L, 0L);

    public CombatPerkCooldownState {
        if (secondWindCooldownUntilTick < 0L
            || reactiveShellCooldownUntilTick < 0L
            || emergencyGuardCooldownUntilTick < 0L) {
            throw new IllegalArgumentException("combat perk cooldown deadlines must be non-negative");
        }
    }

    public static CombatPerkCooldownState empty() {
        return EMPTY;
    }
}
