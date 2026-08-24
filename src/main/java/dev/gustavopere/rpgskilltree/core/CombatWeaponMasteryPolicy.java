package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Canonical mastery lanes for confirmed direct hits by the audited combat weapon families. */
public final class CombatWeaponMasteryPolicy {
    private static final Map<WeaponFamily, String> LANES = Map.ofEntries(
        Map.entry(WeaponFamily.SWORD, "epicfight:sword"),
        Map.entry(WeaponFamily.AXE, "epicfight:axe"),
        Map.entry(WeaponFamily.SPEAR, "epicfight:spear"),
        Map.entry(WeaponFamily.DAGGER, "epicfight:dagger"),
        Map.entry(WeaponFamily.HAMMER, "epicfight:heavy"),
        Map.entry(WeaponFamily.MACE, "combat:mace"),
        Map.entry(WeaponFamily.SCYTHE, "combat:scythe"),
        Map.entry(WeaponFamily.BOW, "combat:bow"),
        Map.entry(WeaponFamily.CROSSBOW, "combat:crossbow")
    );

    private CombatWeaponMasteryPolicy() {}

    public static String masteryLane(WeaponFamily family) {
        Objects.requireNonNull(family);
        String lane = LANES.get(family);
        if (lane == null) throw new IllegalArgumentException("missing mastery lane for " + family);
        return lane;
    }

    public static List<MasteryAward> forConfirmedHit(
        ActionOrigin origin,
        WeaponFamily family,
        String sourceId
    ) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(family);
        Objects.requireNonNull(sourceId);
        if (sourceId.isBlank()) throw new IllegalArgumentException("sourceId must not be blank");
        if (origin.procDepth() > 0) return List.of();
        return List.of(
            new MasteryAward("epicfight:weapon", 2, sourceId),
            new MasteryAward(masteryLane(family), 3, sourceId)
        );
    }

    public static List<MasteryAward> forConfirmedFistHit(ActionOrigin origin, String sourceId) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(sourceId);
        if (sourceId.isBlank()) throw new IllegalArgumentException("sourceId must not be blank");
        if (origin.procDepth() > 0) return List.of();
        return List.of(
            new MasteryAward("epicfight:weapon", 2, sourceId),
            new MasteryAward(CombatFistPolicy.MASTERY_ID, 3, sourceId)
        );
    }
}
