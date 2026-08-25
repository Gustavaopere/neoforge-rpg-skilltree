package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Supplemental OR/provider gates that cannot be represented by NodeAccessRequirement's AND fields. */
public final class FrozenBatchAccessPolicy {
    private static final Set<String> PHYSICAL_WEAPONS = Set.of(
        "epic_sword", "epic_axe", "epic_spear", "epic_dagger", "epic_hammer",
        "combat_mace", "combat_scythe", "epic_bow", "epic_crossbow", CombatFistPolicy.SPECIALIZATION_ID
    );
    private static final Set<String> ARCANE_DIRECT = Set.of(
        "ars_projectile", "ars_aoe", "irons_fire", "irons_ice", "irons_lightning", "irons_nature", "irons_blood"
    );
    private static final Set<String> ELEMENTAL = Set.of("irons_fire", "irons_ice", "irons_lightning", "irons_nature");
    private static final Set<String> PERIODIC = Set.of("irons_blood");
    private static final Set<String> DODGE_NODES = Set.of("rpgskilltree:agility_002", "rpgskilltree:agility_033");

    private FrozenBatchAccessPolicy() {}

    public static boolean satisfied(
        FrozenCombatPerkDefinition.SpecialGate gate,
        Set<String> unlockedSpecializations,
        Set<String> learnedNodeIds
    ) {
        Objects.requireNonNull(gate);
        Objects.requireNonNull(unlockedSpecializations);
        Objects.requireNonNull(learnedNodeIds);
        return switch (gate) {
            case NONE -> true;
            case ANY_PHYSICAL_WEAPON -> intersects(unlockedSpecializations, PHYSICAL_WEAPONS);
            case ARCANE_DIRECT_DAMAGE_BRANCH -> intersects(unlockedSpecializations, ARCANE_DIRECT);
            case ELEMENTAL_AFFINITY -> intersects(unlockedSpecializations, ELEMENTAL);
            case ATTRIBUTABLE_PERIODIC_SOURCE -> intersects(unlockedSpecializations, PERIODIC);
            case DODGE_BRANCH -> intersects(learnedNodeIds, DODGE_NODES);
            case GUARD_CORRIDOR -> false; // No frozen/public progression identity proves this corridor yet.
        };
    }

    private static boolean intersects(Set<String> left, Set<String> right) {
        return left.stream().anyMatch(right::contains);
    }
}
