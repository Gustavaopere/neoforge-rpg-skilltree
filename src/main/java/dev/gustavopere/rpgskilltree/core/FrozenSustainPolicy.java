package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Frozen coefficient selection for A0082-A0086. */
public final class FrozenSustainPolicy {
    private FrozenSustainPolicy() {}
    public static double weaponCoefficient(FrozenCombatPerkRanks ranks) { return 0.006D * ranks.rank("A0082"); }
    public static double magicCoefficient(FrozenCombatPerkRanks ranks) { return 0.006D * ranks.rank("A0083"); }
    public static double elementalCoefficient(FrozenCombatPerkRanks ranks) { return 0.005D * ranks.rank("A0084"); }
    public static double periodicCoefficient(FrozenCombatPerkRanks ranks) { return 0.0035D * ranks.rank("A0085"); }
    public static double coefficientFor(FrozenCombatPerkRanks ranks, boolean weapon, boolean magic, boolean elemental, boolean periodic) {
        Objects.requireNonNull(ranks);
        double best = 0.0D;
        if (weapon) best = Math.max(best, weaponCoefficient(ranks));
        if (magic) best = Math.max(best, magicCoefficient(ranks));
        if (elemental) best = Math.max(best, elementalCoefficient(ranks));
        if (periodic) best = Math.max(best, periodicCoefficient(ranks));
        if (best == 0.0D && ranks.learned("A0086") && (weapon || magic || elemental || periodic)) best = 0.01D;
        return best;
    }
}
