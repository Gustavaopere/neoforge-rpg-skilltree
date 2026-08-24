package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.CombatPerkAttackPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkControlPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefensePolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatPositionPolicy;
import dev.gustavopere.rpgskilltree.core.CombatWeaponFamilyPolicy;
import dev.gustavopere.rpgskilltree.core.CombatWeaponMasteryPolicy;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkState;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.client.ClientProgressionState;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.FakePlayer;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.event.types.entity.DodgeEvent;
import yesman.epicfight.api.event.types.entity.ModifyAttackSpeedEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Optional Epic Fight adapter for the audited A0001-A0050 combat-perk batch. */
public final class EpicFightCombatPerkHooks {
    private static final String PRE_SUBSCRIBER_ID = "rpgskilltree:notion_combat/damage_pre";
    private static final String POST_SUBSCRIBER_ID = "rpgskilltree:notion_combat/damage_post";
    private static final String SPEED_SUBSCRIBER_ID = "rpgskilltree:notion_combat/attack_speed";
    private static final String DODGE_SUBSCRIBER_ID = "rpgskilltree:notion_combat/dodge";

    private static final TagKey<Item> SWORDS = tag("swords");
    private static final TagKey<Item> AXES = tag("axes");
    private static final TagKey<Item> SPEARS = tag("spears");
    private static final TagKey<Item> DAGGERS = tag("daggers");
    private static final TagKey<Item> HAMMERS = tag("hammers");
    private static final TagKey<Item> MACES = tag("maces");
    private static final TagKey<Item> SCYTHES = tag("scythes");
    private static final TagKey<Item> BOWS = tag("bows");
    private static final TagKey<Item> CROSSBOWS = tag("crossbows");

    private static boolean registered;

    private EpicFightCombatPerkHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE.registerEvent(
            EpicFightCombatPerkHooks::onDealDamagePre,
            PRE_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(
            EpicFightCombatPerkHooks::onDealDamagePost,
            POST_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Entity.MODIFY_ATTACK_SPEED.registerEvent(
            EpicFightCombatPerkHooks::onModifyAttackSpeed,
            SPEED_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Entity.ON_DODGE.registerEvent(
            EpicFightCombatPerkHooks::onSuccessfulDodge,
            DODGE_SUBSCRIBER_ID
        );
        registered = true;
    }

    private static void onDealDamagePre(DealDamageEvent.Pre event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player)) return;

        ItemStack usedItem = usedWeapon(event.getDamageSource());
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(usedItem);
        Optional<WeaponFamily> resolved = weaponFamily(usedItem, capability);
        if (resolved.isEmpty()) return;

        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        if (ranks.ranks().isEmpty()) return;

        NotionCombatPerkState state = CombatPerkRuntimeState.state();
        CombatPerkAttackPolicy.AttackContext context = context(
            player,
            event.getTarget(),
            event.getDamageSource(),
            resolved.get(),
            capability
        );
        HurtableEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(event.getTarget(), HurtableEntityPatch.class);
        int shockBefore = resolved.get() == WeaponFamily.HAMMER
            ? state.targetCounter(context.actorId(), context.targetId(), NotionCombatPerkState.TargetCounter.SHOCK, context.nowMillis())
            : 0;
        boolean postureBreakCandidate = resolved.get() == WeaponFamily.HAMMER
            && ranks.rank("A0029") > 0
            && context.direct()
            && context.hostile()
            && context.heavyAttack()
            && shockBefore >= 3
            && targetPatch != null
            && targetPatch.getStunShield() > 0.0F;

        CombatPerkAttackPolicy.HitModifiers modifiers = CombatPerkAttackPolicy.beforeHit(context, ranks, state);

        if (postureBreakCandidate) {
            state.setTargetFlag(
                context.actorId(),
                context.targetId(),
                NotionCombatPerkState.TargetFlag.POSTURE_BREAK_PENDING,
                Math.addExact(context.nowMillis(), 250L)
            );
        }

        if (Double.compare(modifiers.damageMultiplier(), 1.0D) != 0) {
            event.getDamageSource().attachDamageModifier(ValueModifier.multiplier((float)modifiers.damageMultiplier()));
        }
        if (modifiers.armorNegationPoints() > 0.0D) {
            event.getDamageSource().attachArmorNegationModifier(ValueModifier.adder((float)modifiers.armorNegationPoints()));
        }

        double impactMultiplier = context.relevantDefense()
            ? Math.max(modifiers.impactMultiplier(), modifiers.guardPressureMultiplier())
            : modifiers.impactMultiplier();
        if (Double.compare(impactMultiplier, 1.0D) != 0) {
            event.getDamageSource().attachImpactModifier(ValueModifier.multiplier((float)impactMultiplier));
        }
    }

    private static void onDealDamagePost(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player) || event.getModifiedDamage() <= 0.0F) return;

        ItemStack usedItem = usedWeapon(event.getDamageSource());
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(usedItem);
        Optional<WeaponFamily> resolved = weaponFamily(usedItem, capability);
        if (resolved.isEmpty()) return;

        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        if (ranks.ranks().isEmpty()) return;

        NotionCombatPerkState state = CombatPerkRuntimeState.state();
        CombatPerkAttackPolicy.AttackContext context = context(
            player,
            event.getTarget(),
            event.getDamageSource(),
            resolved.get(),
            capability
        );
        CombatPerkAttackPolicy.afterConfirmedHit(context, ranks, state);

        if (resolved.get() == WeaponFamily.HAMMER
            && state.consumeTargetFlag(
                context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.POSTURE_BREAK_PENDING, context.nowMillis())) {
            HurtableEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(event.getTarget(), HurtableEntityPatch.class);
            if (targetPatch != null && targetPatch.getStunShield() <= 0.0F) {
                CombatPerkControlPolicy.onConfirmedPostureBreak(
                    context.actorId(),
                    context.targetId(),
                    WeaponFamily.HAMMER,
                    ranks,
                    state,
                    weaponMastery(player, WeaponFamily.HAMMER),
                    context.nowMillis()
                );
            }
        }
    }

    private static void onModifyAttackSpeed(ModifyAttackSpeedEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof Player player)) return;
        ProgressionState progression;
        if (player instanceof ServerPlayer serverPlayer) {
            if (!eligible(serverPlayer)) return;
            progression = PlayerProgressionRuntime.get(serverPlayer);
        } else {
            if (!player.isLocalPlayer()) return;
            progression = ClientProgressionState.get();
        }

        ItemStack held = player.getMainHandItem();
        Optional<WeaponFamily> resolved = weaponFamily(held, event.getItemCapability());
        if (resolved.isEmpty() || resolved.get() == WeaponFamily.BOW || resolved.get() == WeaponFamily.CROSSBOW) return;

        CombatPerkRanks ranks = CombatPerkNodeBinding.ranks(progression.passiveNodes());
        double bonus = NotionCombatPerkRules.rhythmBonus(resolved.get(), ranks);
        if (bonus > 0.0D) {
            event.setAttackSpeed((float)(event.getAttackSpeed() * (1.0D + bonus)));
        }
    }

    private static void onSuccessfulDodge(DodgeEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player)) return;

        ItemStack held = player.getMainHandItem();
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(held);
        Optional<WeaponFamily> resolved = weaponFamily(held, capability);
        if (resolved.isEmpty()) return;

        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        if (ranks.ranks().isEmpty()) return;

        CombatPerkDefensePolicy.onSuccessfulDodge(
            CombatPerkRuntimeState.actorId(player),
            resolved.get(),
            ranks,
            CombatPerkRuntimeState.state(),
            weaponMastery(player, resolved.get()),
            Math.multiplyExact(player.level().getGameTime(), 50L)
        );
    }

    private static int weaponMastery(ServerPlayer player, WeaponFamily family) {
        return PlayerProgressionRuntime.get(player).mastery().experience(
            CombatWeaponMasteryPolicy.masteryLane(family)
        );
    }

    static ItemStack usedWeapon(EpicFightDamageSource source) {
        ItemStack usedItem = source.getUsedItem();
        if (!usedItem.isEmpty()) return usedItem;
        if (source.getDirectEntity() instanceof AbstractArrow arrow) {
            ItemStack firedFromWeapon = arrow.getWeaponItem();
            if (!firedFromWeapon.isEmpty()) return firedFromWeapon;
        }
        return ItemStack.EMPTY;
    }

    private static CombatPerkAttackPolicy.AttackContext context(
        ServerPlayer player,
        LivingEntity target,
        EpicFightDamageSource source,
        WeaponFamily family,
        CapabilityItem capability
    ) {
        boolean projectileDirect = source.getDirectEntity() instanceof Projectile projectile
            && projectile.getOwner() == player;
        boolean direct = source.getDirectEntity() == player
            || ((family == WeaponFamily.BOW || family == WeaponFamily.CROSSBOW) && projectileDirect);
        boolean hostile = target instanceof Enemy || target instanceof Player;
        HurtableEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, HurtableEntityPatch.class);
        boolean relevantDefense = target.isBlocking() || targetPatch != null && targetPatch.getStunShield() > 0.0F;
        boolean protectedTarget = relevantDefense || target.getArmorValue() > 0;
        double healthFraction = target.getMaxHealth() <= 0.0F
            ? 1.0D
            : Math.max(0.0D, Math.min(1.0D, target.getHealth() / target.getMaxHealth()));
        double distance = player.distanceTo(target);
        double providerReach = capability == null || capability.isEmpty() ? 0.0D : capability.getReach();
        boolean idealRange = family == WeaponFamily.SPEAR && CombatPositionPolicy.isIdealSpearRange(
            distance,
            player.entityInteractionRange(),
            Math.max(0.0D, providerReach)
        );
        var targetMotion = target.getDeltaMovement();
        boolean targetAdvancing = idealRange && CombatPositionPolicy.isAdvancingToward(
            player.getX(),
            player.getZ(),
            target.getX(),
            target.getZ(),
            targetMotion.x,
            targetMotion.z
        );
        var targetLook = target.getLookAngle();
        boolean flankOrBack = family == WeaponFamily.DAGGER && CombatPositionPolicy.isFlankOrBack(
            player.getX(),
            player.getZ(),
            target.getX(),
            target.getZ(),
            targetLook.x,
            targetLook.z
        );

        return new CombatPerkAttackPolicy.AttackContext(
            player.getUUID().toString(),
            target.getUUID().toString(),
            family,
            direct,
            hostile,
            relevantDefense,
            source.shouldChargeWeapon(),
            idealRange,
            targetAdvancing,
            flankOrBack,
            protectedTarget,
            healthFraction,
            false,
            0.0D,
            Math.multiplyExact(player.level().getGameTime(), 50L)
        );
    }

    static Optional<WeaponFamily> weaponFamily(ItemStack stack, CapabilityItem capability) {
        if (capability != null && !capability.isEmpty()) {
            Optional<WeaponFamily> provider = CombatWeaponFamilyPolicy.fromEpicFightCategory(
                capability.getWeaponCategory().toString()
            );
            if (provider.isPresent()) return provider;
        }

        if (stack.getItem() instanceof BowItem) return Optional.of(WeaponFamily.BOW);
        if (stack.getItem() instanceof CrossbowItem) return Optional.of(WeaponFamily.CROSSBOW);
        if (stack.is(SWORDS)) return Optional.of(WeaponFamily.SWORD);
        if (stack.is(AXES)) return Optional.of(WeaponFamily.AXE);
        if (stack.is(SPEARS)) return Optional.of(WeaponFamily.SPEAR);
        if (stack.is(DAGGERS)) return Optional.of(WeaponFamily.DAGGER);
        if (stack.is(HAMMERS)) return Optional.of(WeaponFamily.HAMMER);
        if (stack.is(MACES)) return Optional.of(WeaponFamily.MACE);
        if (stack.is(SCYTHES)) return Optional.of(WeaponFamily.SCYTHE);
        if (stack.is(BOWS)) return Optional.of(WeaponFamily.BOW);
        if (stack.is(CROSSBOWS)) return Optional.of(WeaponFamily.CROSSBOW);
        return Optional.empty();
    }

    private static TagKey<Item> tag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("rpgskilltree", path));
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }
}
