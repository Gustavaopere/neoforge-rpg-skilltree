package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.CombatAction;
import dev.gustavopere.rpgskilltree.core.EpicFightStaminaPolicy;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.core.FistMasteryMilestonePolicy;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.WeaponMasteryMilestoneRuntime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.FakePlayer;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.event.types.entity.DodgeEvent;
import yesman.epicfight.api.event.types.player.SkillConsumeEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

/** Optional Epic Fight adapter. Registered only when Epic Fight is present. */
public final class EpicFightProgressionHooks {
    private static final String HIT_AUTHORITY_SUBSCRIBER_ID = "rpgskilltree:hit_authority/pre";
    private static final String DAMAGE_SUBSCRIBER_ID = "rpgskilltree:mastery/damage";
    private static final String SKILL_SUBSCRIBER_ID = "rpgskilltree:stamina/skill";
    private static final String DODGE_SUBSCRIBER_ID = "rpgskilltree:mastery/dodge";
    private static boolean registered;

    private EpicFightProgressionHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE.registerEvent(
            EpicFightProgressionHooks::onDealDamageAuthority,
            HIT_AUTHORITY_SUBSCRIBER_ID
        );
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

    /** Publishes one provider-native causal root before NeoForge observes the same damage source. */
    private static void onDealDamageAuthority(DealDamageEvent.Pre event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        LivingEntity target = event.getTarget();
        if (!shouldPublishHitAuthority(hostile(player, target), event.getDamageSource().getDirectEntity() == player)) return;
        EpicFightHitAuthority.publish(
            event.getDamageSource(),
            target.getUUID(),
            player.level().getGameTime()
        );
    }

    static boolean shouldPublishHitAuthority(boolean hostileTarget, boolean directPlayerEvidence) {
        return hostileTarget && directPlayerEvidence;
    }

    /**
     * Weapon mastery is discovery-based, not damage-farm based: each weapon category can earn its
     * milestone against a hostile entity type only once for the lifetime of the persisted player state.
     */
    private static void onDealDamage(DealDamageEvent.Post event) {
        EpicFightHitAuthority.discard(event.getDamageSource(), event.getTarget().getUUID());
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        LivingEntity target = event.getTarget();
        if (!hostile(player, target)) return;

        double damage = Math.max(0.0D, event.getModifiedDamage());
        if (damage <= 0.0D) return;

        var usedItem = event.getDamageSource().getUsedItem();
        var weaponCapability = EpicFightCapabilities.getItemStackCapability(usedItem);
        if (weaponCapability == null || weaponCapability.isEmpty()) return;
        String category = masteryCategory(EpicFightWeaponCategory.normalize(weaponCapability.getWeaponCategory().toString()));
        String targetType = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()).toString();

        if (FistMasteryMilestonePolicy.supportsProviderCategory(category)) {
            var milestone = FistMasteryMilestonePolicy.confirmedHit(
                "epicfight:damage_post",
                "epicfight",
                category,
                targetType,
                damage
            );
            WeaponMasteryMilestoneRuntime.awardIfNew(player, milestone);
            return;
        }

        String discoveryKey = "mastery:epicfight:weapon/" + category + "/hostile_type/" + targetType;
        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:damage_post", 0),
            "epicfight",
            category,
            "weapon_hit:" + targetType,
            Set.of("hit", "milestone"),
            damage
        );
        awardMilestone(player, discoveryKey, action);
    }

    /**
     * This hook remains authoritative only for the stamina cost adjustment. Epic Fight posts
     * CONSUME_SKILL before it checks final cancellation and before the resource consumer executes,
     * so it cannot prove that the skill actually consumed stamina or completed. Mastery therefore
     * fails closed here; confirmed post-result hooks own any award.
     */
    private static void onSkillConsume(SkillConsumeEvent event) {
        if (!(event.getEntityPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
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
    }

    /** A successful provider-native dodge is a milestone once; repeated dodges do not farm mastery. */
    private static void onSuccessfulDodge(DodgeEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        String discoveryKey = "mastery:epicfight:dodge_success/first";
        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:dodge_success", 0),
            "epicfight",
            "dodge",
            "successful_dodge",
            Set.of("dodge_success", "milestone"),
            0.0D
        );
        awardMilestone(player, discoveryKey, action);
    }

    private static void awardMilestone(ServerPlayer player, String discoveryKey, CombatAction action) {
        if (PlayerProgressionRuntime.get(player).discoveries().contains(discoveryKey)) return;
        PlayerProgressionRuntime.awardMasteryAndDiscoveries(
            player,
            MasteryPolicies.forEpicFight(action),
            List.of(discoveryKey)
        );
    }

    private static String masteryCategory(String normalized) {
        int slash = normalized.lastIndexOf('/');
        return slash >= 0 && slash + 1 < normalized.length() ? normalized.substring(slash + 1) : normalized;
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        if (target == player || player.isAlliedTo(target) || target.isInvulnerable()) return false;
        return target instanceof Enemy || target instanceof Player;
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }
}