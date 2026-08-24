package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.CombatAction;
import dev.gustavopere.rpgskilltree.core.CombatWeaponMasteryPolicy;
import dev.gustavopere.rpgskilltree.core.EpicFightStaminaPolicy;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.FakePlayer;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.event.types.entity.DodgeEvent;
import yesman.epicfight.api.event.types.player.SkillConsumeEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Optional Epic Fight adapter. Registered only when Epic Fight is present. */
public final class EpicFightProgressionHooks {
    private static final String DAMAGE_SUBSCRIBER_ID = "rpgskilltree:mastery/damage";
    private static final String SKILL_SUBSCRIBER_ID = "rpgskilltree:mastery/skill";
    private static final String DODGE_SUBSCRIBER_ID = "rpgskilltree:mastery/dodge";
    private static boolean registered;

    private EpicFightProgressionHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(
            EpicFightProgressionHooks::onDealDamage,
            DAMAGE_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Player.CONSUME_SKILL.registerEvent(
            EpicFightProgressionHooks::onSkillConsume,
            SKILL_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Entity.ON_DODGE.registerEvent(
            EpicFightProgressionHooks::onSuccessfulDodge,
            DODGE_SUBSCRIBER_ID
        );
        registered = true;
    }

    private static void onDealDamage(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player)) return;

        double damage = Math.max(0.0D, event.getModifiedDamage());
        if (damage <= 0.0D) return;

        EpicFightDamageSource damageSource = event.getDamageSource();
        ItemStack usedItem = EpicFightCombatPerkHooks.usedWeapon(damageSource);
        if (!directWeaponAction(player, damageSource, usedItem)) return;
        var weaponCapability = EpicFightCapabilities.getItemStackCapability(usedItem);
        var origin = new ActionOrigin("epicfight:damage_post", 0);
        var family = EpicFightCombatPerkHooks.weaponFamily(usedItem, weaponCapability);
        if (family.isPresent()) {
            PlayerProgressionRuntime.awardMastery(
                player,
                CombatWeaponMasteryPolicy.forConfirmedHit(origin, family.get(), "weapon_hit")
            );
            return;
        }
        String category = EpicFightWeaponCategory.normalize(weaponCapability.getWeaponCategory().toString());

        CombatAction action = new CombatAction(
            origin,
            "epicfight",
            category,
            "weapon_hit",
            Set.of("hit"),
            damage
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forEpicFight(action));
    }

    private static boolean directWeaponAction(
        ServerPlayer player,
        EpicFightDamageSource source,
        ItemStack usedItem
    ) {
        if (source.getDirectEntity() == player) return true;
        return !usedItem.isEmpty()
            && source.getDirectEntity() instanceof Projectile projectile
            && projectile.getOwner() == player;
    }

    private static void onSkillConsume(SkillConsumeEvent event) {
        if (!(event.getEntityPatch() instanceof PlayerPatch<?> patch)) return;
        if (!(patch.getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player)) return;
        if (event.getSkill() == null || event.getResourceType() != Skill.Resource.STAMINA) return;

        String category = event.getSkill().getCategory().toString().toLowerCase(Locale.ROOT);
        float originalCost = Math.max(0.0F, event.getAmount());
        float adjustedCost = EpicFightStaminaPolicy.adjustedCost(
            PlayerProgressionRuntime.get(player).passiveNodes(),
            category,
            originalCost
        );
        event.setAmount(adjustedCost);

        if (adjustedCost <= 0.0F || !patch.hasStamina(adjustedCost)) return;

        Set<String> tags = new HashSet<>();
        tags.add("skill");
        tags.add("stamina");
        switch (category) {
            case "guard" -> tags.add("guard");
            case "dodge" -> tags.add("dodge");
            case "mover" -> tags.add("mover");
            case "weapon_innate" -> tags.add("weapon_innate");
            default -> { }
        }

        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:skill_consume", 0),
            "epicfight",
            "skill",
            event.getSkill().getRegistryName().toString(),
            Set.copyOf(tags),
            adjustedCost
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forEpicFight(action));
    }

    private static void onSuccessfulDodge(DodgeEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player)) return;

        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:dodge_success", 0),
            "epicfight",
            "dodge",
            "successful_dodge",
            Set.of("dodge_success"),
            0.0D
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forEpicFight(action));
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }
}
