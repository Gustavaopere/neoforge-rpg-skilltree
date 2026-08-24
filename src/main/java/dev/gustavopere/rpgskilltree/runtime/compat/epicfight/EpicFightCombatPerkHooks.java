package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.CombatPerkAttackPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkControlPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefensePolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkFinalizationPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatPositionPolicy;
import dev.gustavopere.rpgskilltree.core.CombatWeaponFamilyPolicy;
import dev.gustavopere.rpgskilltree.core.CombatWeaponMasteryPolicy;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkState;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.BossRewardKeyResolver;
import dev.gustavopere.rpgskilltree.runtime.CanonicalCombatRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.client.ClientProgressionState;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.WeakHashMap;
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
import yesman.epicfight.api.event.types.entity.KillEntityEvent;
import yesman.epicfight.api.event.types.entity.ModifyAttackSpeedEvent;
import yesman.epicfight.api.event.types.player.TickPlayerEpicFightModeEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Optional Epic Fight adapter for the audited A0001-A0050 combat-perk batch. */
public final class EpicFightCombatPerkHooks {
    private static final String PRE_SUBSCRIBER_ID = "rpgskilltree:notion_combat/damage_pre";
    private static final String POST_SUBSCRIBER_ID = "rpgskilltree:notion_combat/damage_post";
    private static final String KILL_SUBSCRIBER_ID = "rpgskilltree:notion_combat/kill";
    private static final String SPEED_SUBSCRIBER_ID = "rpgskilltree:notion_combat/attack_speed";
    private static final String DODGE_SUBSCRIBER_ID = "rpgskilltree:notion_combat/dodge";
    private static final String TICK_SUBSCRIBER_ID = "rpgskilltree:notion_combat/tick";
    private static final String A0029_REFUND_CONSUMER = "A0029:posture-break-refund";
    private static final String A0042_REFUND_CONSUMER = "A0042:battle-harvest-refund";

    private static final TagKey<Item> SWORDS = tag("swords");
    private static final TagKey<Item> AXES = tag("axes");
    private static final TagKey<Item> SPEARS = tag("spears");
    private static final TagKey<Item> DAGGERS = tag("daggers");
    private static final TagKey<Item> HAMMERS = tag("hammers");
    private static final TagKey<Item> MACES = tag("maces");
    private static final TagKey<Item> COMBAT_MACE = tag("combat", "mace");
    private static final TagKey<Item> SCYTHES = tag("scythes");
    private static final TagKey<Item> BOWS = tag("bows");
    private static final TagKey<Item> CROSSBOWS = tag("crossbows");
    private static final Map<EpicFightDamageSource, Map<String, CanonicalActionIdentity>> ACTIONS =
        new WeakHashMap<>();
    private static final Map<EpicFightDamageSource, Map<String, Boolean>> ARMOR_CRACKED_BEFORE_HIT =
        new WeakHashMap<>();
    private static final Map<EpicFightDamageSource, Map<String, Boolean>> REAPING_MATURE_BEFORE_HIT =
        new WeakHashMap<>();

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
        EpicFightEventHooks.Entity.KILL_ENTITY.registerEvent(
            EpicFightCombatPerkHooks::onKillEntity,
            KILL_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Entity.MODIFY_ATTACK_SPEED.registerEvent(
            EpicFightCombatPerkHooks::onModifyAttackSpeed,
            SPEED_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Entity.ON_DODGE.registerEvent(
            EpicFightCombatPerkHooks::onSuccessfulDodge,
            DODGE_SUBSCRIBER_ID
        );
        EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(
            EpicFightCombatPerkHooks::onEpicFightModeTick,
            TICK_SUBSCRIBER_ID
        );
        registered = true;
    }

    private static void onDealDamagePre(DealDamageEvent.Pre event) {
        applyOutgoingDesync(event);

        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player)) return;

        ItemStack usedItem = usedWeapon(event.getDamageSource());
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(usedItem);
        Optional<WeaponFamily> resolved = weaponFamily(usedItem, capability);
        if (resolved.isEmpty()) return;

        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        if (ranks.ranks().isEmpty()) return;

        NotionCombatPerkState state = CombatPerkRuntimeState.state();
        long nowMillis = now(player);
        CanonicalActionIdentity action = actionForPre(
            player, event.getTarget(), event.getDamageSource(), nowMillis);
        CombatPerkAttackPolicy.AttackContext context = context(
            player,
            event.getTarget(),
            event.getDamageSource(),
            resolved.get(),
            capability,
            action,
            canonicalCritical(action, event.getDamageSource(), nowMillis)
        );

        if (ranks.learned("A0036") && unambiguousMace(usedItem, capability)) {
            rememberArmorCrackedBeforeHit(
                event.getDamageSource(),
                context.targetId(),
                state.hasTargetFlag(
                    context.actorId(),
                    context.targetId(),
                    NotionCombatPerkState.TargetFlag.ARMOR_CRACKED,
                    context.nowMillis()
                )
            );
        }
        if (ranks.learned("A0042") && resolved.get() == WeaponFamily.SCYTHE) {
            rememberReapingMatureBeforeHit(
                event.getDamageSource(),
                context.targetId(),
                state.hasTargetFlag(
                    context.actorId(),
                    context.targetId(),
                    NotionCombatPerkState.TargetFlag.REAPING_MATURE,
                    context.nowMillis()
                )
            );
        }

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
        if (!eligible(player)) return;
        if (event.getModifiedDamage() <= 0.0F) {
            consumeArmorCrackedBeforeHit(event.getDamageSource(), event.getTarget().getUUID().toString());
            return;
        }

        ItemStack usedItem = usedWeapon(event.getDamageSource());
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(usedItem);
        Optional<WeaponFamily> resolved = weaponFamily(usedItem, capability);
        if (resolved.isEmpty()) {
            consumeArmorCrackedBeforeHit(event.getDamageSource(), event.getTarget().getUUID().toString());
            return;
        }

        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        if (ranks.ranks().isEmpty()) {
            consumeArmorCrackedBeforeHit(event.getDamageSource(), event.getTarget().getUUID().toString());
            return;
        }

        NotionCombatPerkState state = CombatPerkRuntimeState.state();
        long nowMillis = now(player);
        CanonicalActionIdentity action = actionForPost(
            player, event.getTarget(), event.getDamageSource(), nowMillis);
        CombatPerkAttackPolicy.AttackContext context = context(
            player,
            event.getTarget(),
            event.getDamageSource(),
            resolved.get(),
            capability,
            action,
            canonicalCritical(action, event.getDamageSource(), nowMillis)
        );

        boolean armorCrackedBeforeHit = consumeArmorCrackedBeforeHit(
            event.getDamageSource(),
            context.targetId()
        );
        if (unambiguousMace(usedItem, capability)) {
            CombatPerkFinalizationPolicy.activateBoneBreakerFromPreHitSnapshot(
                action,
                context.actorId(),
                context.targetId(),
                WeaponFamily.MACE,
                context.direct(),
                context.hostile(),
                context.heavyAttack(),
                armorCrackedBeforeHit,
                BossRewardKeyResolver.isBoss(event.getTarget()),
                ranks,
                state,
                weaponMastery(player, WeaponFamily.MACE),
                context.nowMillis()
            ).ifPresent(effect -> CombatPerkRuntimeState.targetDebuffs().applyDesync(
                context.actorId(),
                context.targetId(),
                effect
            ));
        }

        boolean consumedBattleHarvest = resolved.get() == WeaponFamily.SCYTHE
            && CombatPerkFinalizationPolicy.consumeBattleHarvestOnHit(
                action,
                context.actorId(),
                context.targetId(),
                WeaponFamily.SCYTHE,
                context.direct(),
                context.hostile(),
                ranks,
                state,
                context.nowMillis()
            );
        if (consumedBattleHarvest) {
            refundExactStamina(player, action, A0042_REFUND_CONSUMER, 0.10D, context.nowMillis());
        }

        // A0035 may mutate Armor Cracked here. A0036 has already consumed the immutable PRE snapshot above.
        CombatPerkAttackPolicy.afterConfirmedHit(context, ranks, state);

        if (resolved.get() == WeaponFamily.HAMMER
            && state.consumeTargetFlag(
                context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.POSTURE_BREAK_PENDING, context.nowMillis())) {
            HurtableEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(event.getTarget(), HurtableEntityPatch.class);
            if (targetPatch != null && targetPatch.getStunShield() <= 0.0F) {
                if (ranks.rank("A0029") > 0
                    && state.actorCooldownReady(context.actorId(), A0029_REFUND_CONSUMER, context.nowMillis())
                    && refundExactStamina(player, action, A0029_REFUND_CONSUMER, 0.10D, context.nowMillis())) {
                    state.startActorCooldown(context.actorId(), A0029_REFUND_CONSUMER, context.nowMillis(), 8_000L);
                }
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

    private static void onKillEntity(KillEntityEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        if (!eligible(player)) return;
        if (!(event.getDamageSource() instanceof EpicFightDamageSource source)) return;

        LivingEntity victim = event.getKilledEntity();
        if (!legitimateBattleHarvestKill(player, victim, source)) return;

        ItemStack usedItem = usedWeapon(source);
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(usedItem);
        if (weaponFamily(usedItem, capability).orElse(null) != WeaponFamily.SCYTHE) return;

        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        if (!ranks.learned("A0042")) return;

        long nowMillis = now(player);
        Optional<CanonicalActionIdentity> action = existingActionForDamage(player, victim, source, nowMillis);
        boolean reapingMatureBeforeHit = consumeReapingMatureBeforeHit(source, victim.getUUID().toString());
        if (action.isEmpty()) return;

        CombatPerkFinalizationPolicy.activateBattleHarvestFromPreHitSnapshot(
            action.get().withSource("epicfight:kill_entity"),
            CombatPerkRuntimeState.actorId(player),
            victim.getUUID().toString(),
            WeaponFamily.SCYTHE,
            true,
            true,
            true,
            reapingMatureBeforeHit,
            ranks,
            CombatPerkRuntimeState.state(),
            weaponMastery(player, WeaponFamily.SCYTHE),
            nowMillis
        );
    }

    /** Epic Fight applies damage modifiers to baseDamage before its separate extra-damage instances. */
    private static void applyOutgoingDesync(DealDamageEvent.Pre event) {
        LivingEntity attacker = event.getEntityPatch().getOriginal();
        if (attacker.level().isClientSide()) return;
        long nowMillis = Math.multiplyExact(attacker.level().getGameTime(), 50L);
        CombatPerkRuntimeState.targetDebuffs()
            .desync(attacker.getUUID().toString(), nowMillis)
            .ifPresent(desync -> event.getDamageSource().attachDamageModifier(
                ValueModifier.multiplier((float)desync.outgoingPhysicalDamageMultiplier())
            ));
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

    private static void onEpicFightModeTick(TickPlayerEpicFightModeEvent event) {
        if (!(event.getPlayerPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
        if (!eligible(player)) return;
        LivingEntity target = patch.getTarget();
        if (target == null || !target.isAlive() || !(target instanceof Enemy || target instanceof Player)) return;

        ItemStack held = player.getMainHandItem();
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(held);
        if (weaponFamily(held, capability).orElse(null) != WeaponFamily.SPEAR) return;
        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        if (!ranks.learned("A0018")) return;

        double effectiveReach = player.entityInteractionRange() + Math.max(0.0D, capability.getReach());
        var targetMotion = target.getDeltaMovement();
        boolean targetAdvancing = CombatPositionPolicy.isAdvancingToward(
            player.getX(),
            player.getZ(),
            target.getX(),
            target.getZ(),
            targetMotion.x,
            targetMotion.z
        );
        CombatPerkControlPolicy.onSpearRangeUpdate(
            CombatPerkRuntimeState.actorId(player),
            target.getUUID().toString(),
            WeaponFamily.SPEAR,
            player.distanceTo(target),
            effectiveReach,
            targetAdvancing,
            ranks,
            CombatPerkRuntimeState.state(),
            weaponMastery(player, WeaponFamily.SPEAR),
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
        CapabilityItem capability,
        CanonicalActionIdentity action,
        boolean criticalHit
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
            action,
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
            criticalHit,
            0.0D,
            now(player)
        );
    }

    private static synchronized CanonicalActionIdentity actionForPre(
        ServerPlayer player,
        LivingEntity target,
        EpicFightDamageSource source,
        long nowMillis
    ) {
        String targetId = target.getUUID().toString();
        Map<String, CanonicalActionIdentity> byTarget = ACTIONS.computeIfAbsent(source, ignored -> new HashMap<>());
        CanonicalActionIdentity existing = byTarget.get(targetId);
        if (existing != null) return existing.withSource("epicfight:damage_pre");

        Optional<CanonicalActionIdentity> bridgeBound = EpicFightExactStaminaReceiptBridge.boundActionForDamage(
            player,
            source,
            nowMillis
        );
        if (bridgeBound.isPresent()) {
            CanonicalActionIdentity action = bridgeBound.get();
            byTarget.put(targetId, action);
            canonicalCritical(action, source, nowMillis);
            return action.withSource("epicfight:damage_pre");
        }

        CanonicalActionIdentity action;
        if (source.getDirectEntity() instanceof Projectile projectile && projectile.getOwner() == player) {
            action = CanonicalCombatRuntimeState.projectileAction(
                player,
                projectile.getUUID().toString(),
                nowMillis
            );
        } else {
            action = CanonicalCombatRuntimeState.claimMeleeForProvider(player, targetId, nowMillis)
                .orElseGet(() -> CanonicalCombatRuntimeState.newRoot(
                    player,
                    "epicfight:damage_pre",
                    nowMillis
                ));
        }
        byTarget.put(targetId, action);
        EpicFightExactStaminaReceiptBridge.bindDamageAction(player, source, action, nowMillis);
        canonicalCritical(action, source, nowMillis);
        return action.withSource("epicfight:damage_pre");
    }

    private static synchronized CanonicalActionIdentity actionForPost(
        ServerPlayer player,
        LivingEntity target,
        EpicFightDamageSource source,
        long nowMillis
    ) {
        Map<String, CanonicalActionIdentity> byTarget = ACTIONS.get(source);
        CanonicalActionIdentity action = byTarget == null ? null : byTarget.get(target.getUUID().toString());
        return action == null
            ? actionForPre(player, target, source, nowMillis).withSource("epicfight:damage_post")
            : action.withSource("epicfight:damage_post");
    }

    private static synchronized Optional<CanonicalActionIdentity> existingActionForDamage(
        ServerPlayer player,
        LivingEntity target,
        EpicFightDamageSource source,
        long nowMillis
    ) {
        Map<String, CanonicalActionIdentity> byTarget = ACTIONS.get(source);
        CanonicalActionIdentity action = byTarget == null ? null : byTarget.get(target.getUUID().toString());
        if (action != null) return Optional.of(action);

        Optional<CanonicalActionIdentity> bridgeBound = EpicFightExactStaminaReceiptBridge.boundActionForDamage(
            player,
            source,
            nowMillis
        );
        bridgeBound.ifPresent(bound -> ACTIONS
            .computeIfAbsent(source, ignored -> new HashMap<>())
            .put(target.getUUID().toString(), bound));
        return bridgeBound;
    }

    private static synchronized void rememberArmorCrackedBeforeHit(
        EpicFightDamageSource source,
        String targetId,
        boolean armorCracked
    ) {
        ARMOR_CRACKED_BEFORE_HIT
            .computeIfAbsent(source, ignored -> new HashMap<>())
            .put(targetId, armorCracked);
    }

    private static synchronized boolean consumeArmorCrackedBeforeHit(
        EpicFightDamageSource source,
        String targetId
    ) {
        Map<String, Boolean> byTarget = ARMOR_CRACKED_BEFORE_HIT.get(source);
        if (byTarget == null) return false;
        Boolean value = byTarget.remove(targetId);
        if (byTarget.isEmpty()) ARMOR_CRACKED_BEFORE_HIT.remove(source);
        return Boolean.TRUE.equals(value);
    }

    private static synchronized void rememberReapingMatureBeforeHit(
        EpicFightDamageSource source,
        String targetId,
        boolean reapingMature
    ) {
        REAPING_MATURE_BEFORE_HIT
            .computeIfAbsent(source, ignored -> new HashMap<>())
            .put(targetId, reapingMature);
    }

    private static synchronized boolean consumeReapingMatureBeforeHit(
        EpicFightDamageSource source,
        String targetId
    ) {
        Map<String, Boolean> byTarget = REAPING_MATURE_BEFORE_HIT.get(source);
        if (byTarget == null) return false;
        Boolean value = byTarget.remove(targetId);
        if (byTarget.isEmpty()) REAPING_MATURE_BEFORE_HIT.remove(source);
        return Boolean.TRUE.equals(value);
    }

    private static boolean refundExactStamina(
        ServerPlayer player,
        CanonicalActionIdentity action,
        String consumerId,
        double fraction,
        long nowMillis
    ) {
        ServerPlayerPatch patch = EpicFightCapabilities.getServerPlayerPatch(player);
        if (patch == null) return false;
        if (EpicFightExactStaminaReceiptBridge.receipt(action, nowMillis).isEmpty()) return false;

        OptionalDouble refund = EpicFightExactStaminaReceiptBridge.claimRefundAmount(
            action,
            consumerId,
            fraction,
            nowMillis
        );
        if (refund.isEmpty()) return false;

        float amount = (float)refund.getAsDouble();
        patch.setStamina(Math.min(patch.getMaxStamina(), patch.getStamina() + amount));
        return true;
    }

    private static boolean legitimateBattleHarvestKill(
        ServerPlayer player,
        LivingEntity victim,
        EpicFightDamageSource source
    ) {
        if (victim == player || player.isAlliedTo(victim)) return false;
        if (!(victim instanceof Enemy || victim instanceof Player)) return false;
        return source.getEntity() == player && source.getDirectEntity() == player;
    }

    private static boolean canonicalCritical(
        CanonicalActionIdentity action,
        EpicFightDamageSource source,
        long nowMillis
    ) {
        Optional<Boolean> existing = CanonicalCombatRuntimeState.criticalDecision(action, nowMillis);
        if (existing.isPresent()) return existing.get();
        boolean providerCritical = source.getDirectEntity() instanceof AbstractArrow arrow && arrow.isCritArrow();
        return CanonicalCombatRuntimeState.resolveProviderCritical(
            action,
            providerCritical,
            nowMillis
        );
    }

    /** Explicit mace tags win; Epic Fight category is only a fallback when no explicit family is present. */
    static boolean unambiguousMace(ItemStack stack, CapabilityItem capability) {
        boolean explicitMace = stack.is(MACES) || stack.is(COMBAT_MACE);
        boolean explicitOther = stack.getItem() instanceof BowItem
            || stack.getItem() instanceof CrossbowItem
            || stack.is(SWORDS)
            || stack.is(AXES)
            || stack.is(SPEARS)
            || stack.is(DAGGERS)
            || stack.is(HAMMERS)
            || stack.is(SCYTHES)
            || stack.is(BOWS)
            || stack.is(CROSSBOWS);
        if (explicitMace) return !explicitOther;
        if (explicitOther || capability == null || capability.isEmpty()) return false;
        return CombatWeaponFamilyPolicy.fromEpicFightCategory(capability.getWeaponCategory().toString())
            .orElse(null) == WeaponFamily.MACE;
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
        return tag("rpgskilltree", path);
    }

    private static TagKey<Item> tag(String namespace, String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }
}
