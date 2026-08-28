package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Stable read-only projection of the player's canonical RPG envelope.
 *
 * <p>Core progression is the only level/XP/point authority exposed here.
 * Compatibility-owned domains remain individually visible until their deliberate
 * migration, without leaking the raw legacy ProgressionState or PassivePointLedger.</p>
 */
public record CanonicalPlayerSnapshot(
    CoreProgressionQuerySnapshot progression,
    BossProgress bosses,
    ClassProgressionState classes,
    MasteryState mastery,
    ClassChoiceState classChoices,
    SpecializationProgressionState specializations,
    FinalTriadProgress finalTriads,
    PassiveNodeProgress passiveNodes,
    DiscoveryProgress discoveries
) {
    public CanonicalPlayerSnapshot {
        Objects.requireNonNull(progression, "progression");
        Objects.requireNonNull(bosses, "bosses");
        Objects.requireNonNull(classes, "classes");
        Objects.requireNonNull(mastery, "mastery");
        Objects.requireNonNull(classChoices, "classChoices");
        Objects.requireNonNull(specializations, "specializations");
        Objects.requireNonNull(finalTriads, "finalTriads");
        Objects.requireNonNull(passiveNodes, "passiveNodes");
        Objects.requireNonNull(discoveries, "discoveries");
    }
}
