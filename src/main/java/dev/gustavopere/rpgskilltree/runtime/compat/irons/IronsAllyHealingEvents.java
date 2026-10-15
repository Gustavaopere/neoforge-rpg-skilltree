package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import io.redspace.ironsspellbooks.api.events.SpellHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

/** Test-first placeholder; no runtime registration until the red GameTest is observed. */
public final class IronsAllyHealingEvents {
    private IronsAllyHealingEvents() {}

    public static void onSpellHeal(SpellHealEvent event) {}

    public static void onLivingHeal(LivingHealEvent event) {}
}
