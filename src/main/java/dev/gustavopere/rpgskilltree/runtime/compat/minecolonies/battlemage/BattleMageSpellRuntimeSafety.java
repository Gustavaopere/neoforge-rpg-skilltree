package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.core.entity.citizen.EntityCitizen;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.spells.fire.FireballSpell;

/** Exact-version safety handlers for spells whose runtime footprint exceeds static profile data. */
final class BattleMageSpellRuntimeSafety {
    private BattleMageSpellRuntimeSafety() {}

    static double friendlyFireRadius(
        EntityCitizen caster,
        SpellData spellData,
        BattleMageSpellProfile profile
    ) {
        if (profile == null) return Double.POSITIVE_INFINITY;
        double configuredFloor = profile.friendlyFireRadius();
        if (spellData == null || spellData.getSpell() == null) return configuredFloor;

        if (spellData.getSpell() instanceof FireballSpell fireball) {
            return Math.max(configuredFloor, fireball.getRadius(spellData.getLevel(), caster));
        }
        return configuredFloor;
    }
}
