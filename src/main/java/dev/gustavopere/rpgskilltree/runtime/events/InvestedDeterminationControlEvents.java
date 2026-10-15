package dev.gustavopere.rpgskilltree.runtime.events;

import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Test-first no-op: validates a native, source-specific control duration seam. */
public final class InvestedDeterminationControlEvents {
    private InvestedDeterminationControlEvents() {}
    public static void onAdded(MobEffectEvent.Added event) {}
    public static void onPlayerTick(PlayerTickEvent.Post event) {}
}
