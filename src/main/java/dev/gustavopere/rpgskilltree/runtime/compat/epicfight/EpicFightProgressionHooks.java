package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.CombatAction;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.Set;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.util.FakePlayer;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

/** Optional Epic Fight adapter. Registered only when Epic Fight is present. */
public final class EpicFightProgressionHooks {
    private static final String SUBSCRIBER_ID = "rpgskilltree:mastery";
    private static boolean registered;

    private EpicFightProgressionHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(
            EpicFightProgressionHooks::onDealDamage,
            SUBSCRIBER_ID
        );
        registered = true;
    }

    private static void onDealDamage(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer) return;

        double damage = Math.max(0.0D, event.getModifiedDamage());
        if (damage <= 0.0D) return;

        var usedItem = event.getDamageSource().getUsedItem();
        var weaponCapability = EpicFightCapabilities.getItemStackCapability(usedItem);
        String category = EpicFightWeaponCategory.normalize(weaponCapability.getWeaponCategory().toString());

        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:damage_post", 0),
            "epicfight",
            category,
            "weapon_hit",
            Set.of("hit"),
            damage
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forEpicFight(action));
    }
}
