package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.DamageMitigationResolver;
import dev.gustavopere.rpgskilltree.core.EmergencyGuardService;
import dev.gustavopere.rpgskilltree.core.FrozenA0107IntegrationPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenDamageMitigationPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenDefensiveTradeoffPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkRanks;
import dev.gustavopere.rpgskilltree.core.ReactiveShellService;
import dev.gustavopere.rpgskilltree.core.SecondWindService;
import dev.gustavopere.rpgskilltree.runtime.CanonicalCombatRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.CanonicalSustainRuntime;
import dev.gustavopere.rpgskilltree.runtime.FrozenSurvivalRuntimeState;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Public NeoForge adapters for the provider-independent A0101-A0108 contracts. */
public final class FrozenSurvivalEvents {
    private static final TagKey<DamageType> MAGIC_DIRECT = damageTag("magic_direct");
    private static final TagKey<DamageType> ELEMENTAL_DIRECT = damageTag("elemental_direct");
    private static final TagKey<DamageType> PERIODIC_SUSTAIN = damageTag("periodic_sustain");
    private static final TagKey<DamageType> BLOOD_MAGIC_COST = damageTag("blood_magic_cost");
    private static final TagKey<DamageType> ENVIRONMENTAL_NON_ELEMENTAL =
        damageTag("environmental_non_elemental");

    private FrozenSurvivalEvents() {}

    /** P-0035 is deliberately not implemented on this branch. */
    public static boolean impactStaminaIntegrationAvailable() {
        return FrozenA0107IntegrationPolicy.providerCertified();
    }

    /** Runs after the older vitality resolver so A0106 observes every prior mitigation. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0.0F
            || !(event.getEntity() instanceof ServerPlayer victim) || !eligible(victim)
            || event.getSource().is(BLOOD_MAGIC_COST)) return;

        DamageSource source = event.getSource();
        long nowTick = victim.level().getGameTime();
        long nowMillis = Math.multiplyExact(nowTick, 50L);
        CanonicalActionIdentity action = CanonicalCombatRuntimeState.receivedDamageAction(
            victim, source, nowMillis);
        FrozenSurvivalPerkRanks ranks = FrozenSurvivalRuntimeState.ranks(victim);
        boolean magic = source.is(MAGIC_DIRECT) || source.is(ELEMENTAL_DIRECT)
            || CanonicalSustainRuntime.isProviderClassifiedNonWeapon(source);
        boolean periodic = source.is(PERIODIC_SUSTAIN);
        boolean physicalProjectile = source.getDirectEntity() instanceof AbstractArrow
            && !magic && !periodic;
        boolean explicitEnvironment = source.is(ENVIRONMENTAL_NON_ELEMENTAL);

        List<DamageMitigationResolver.Modifier> modifiers = new ArrayList<>(
            FrozenDamageMitigationPolicy.modifiers(ranks, new FrozenDamageMitigationPolicy.Facts(
                physicalProjectile, magic, physicalProjectile,
                explicitEnvironment, false, explicitEnvironment)));
        FrozenDefensiveTradeoffPolicy.Tradeoff stoneSkin = FrozenDefensiveTradeoffPolicy.stoneSkin(
            ranks.rank("A0108"), safeDirectPhysical(source),
            FrozenSurvivalRuntimeState.stoneSkinCostApplied(victim));
        if (stoneSkin.active()) {
            modifiers.add(new DamageMitigationResolver.Modifier(
                "A0108", "stone_skin", stoneSkin.physicalReduction()));
        }

        boolean bypassesMitigation = source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
        DamageMitigationResolver.Resolution mitigation = FrozenSurvivalRuntimeState.damageMitigation().resolve(
            new DamageMitigationResolver.Request(
                action, victim.getUUID().toString(), event.getAmount(), bypassesMitigation),
            List.copyOf(modifiers), nowTick);
        if (!mitigation.duplicateEvent()) event.setAmount((float) mitigation.finalDamage());

        boolean hostile = hostile(victim, source);
        EmergencyGuardService.Resolution guard = FrozenSurvivalRuntimeState.emergencyGuard().resolve(
            new EmergencyGuardService.Damage(
                victim.getUUID().toString(), action, victim.getMaxHealth(), victim.getHealth(),
                event.getAmount(), hostile && !bypassesMitigation),
            ranks.rank("A0106"), nowTick);
        if (!guard.duplicate()) event.setAmount((float) guard.finalDamage());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (event.getNewDamage() <= 0.0F
            || !(event.getEntity() instanceof ServerPlayer victim) || !eligible(victim)
            || event.getSource().is(BLOOD_MAGIC_COST)) return;
        DamageSource source = event.getSource();
        boolean hostile = hostile(victim, source);
        boolean direct = hostile && directHostile(source);
        if (!hostile) return;

        long nowTick = victim.level().getGameTime();
        CanonicalActionIdentity action = CanonicalCombatRuntimeState.receivedDamageAction(
            victim, source, Math.multiplyExact(nowTick, 50L));
        FrozenSurvivalPerkRanks ranks = FrozenSurvivalRuntimeState.ranks(victim);
        double healthAfter = victim.getHealth();
        double healthBefore = Math.min(victim.getMaxHealth(), healthAfter + event.getNewDamage());
        FrozenSurvivalRuntimeState.secondWind().onDamage(new SecondWindService.Damage(
            victim.getUUID().toString(), action, healthBefore, healthAfter, victim.getMaxHealth(),
            true, direct), ranks.rank("A0104"), nowTick);
        FrozenSurvivalRuntimeState.reactiveShell().record(new ReactiveShellService.Hit(
            victim.getUUID().toString(), action, true, direct), ranks.rank("A0105"), nowTick);
        FrozenSurvivalRuntimeState.refreshReactiveShell(victim, ranks, nowTick);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || player.level().isClientSide()) return;
        if (!eligible(player)) {
            FrozenSurvivalRuntimeState.clearTransient(player);
            return;
        }
        FrozenSurvivalPerkRanks ranks = FrozenSurvivalRuntimeState.ranks(player);
        FrozenSurvivalRuntimeState.revalidate(player, dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime.get(player));
        FrozenSurvivalRuntimeState.secondWind().claimPulse(
            player.getUUID().toString(), player.level().getGameTime())
            .ifPresent(fraction -> player.heal((float)(player.getMaxHealth() * fraction)));
        FrozenSurvivalRuntimeState.refreshReactiveShell(player, ranks, player.level().getGameTime());
    }

    private static boolean safeDirectPhysical(DamageSource source) {
        if (source.is(MAGIC_DIRECT) || source.is(ELEMENTAL_DIRECT) || source.is(PERIODIC_SUSTAIN)
            || source.is(DamageTypeTags.BYPASSES_ARMOR)
            || CanonicalSustainRuntime.isProviderClassifiedNonWeapon(source)) return false;
        if (source.getDirectEntity() instanceof AbstractArrow) return true;
        return source.getEntity() instanceof LivingEntity attacker && source.getDirectEntity() == attacker;
    }

    private static boolean hostile(ServerPlayer victim, DamageSource source) {
        return source.getEntity() instanceof LivingEntity attacker
            && attacker != victim
            && !victim.isAlliedTo(attacker)
            && (attacker instanceof Enemy || attacker instanceof Player)
            && (!(attacker instanceof ServerPlayer player) || eligible(player));
    }

    private static boolean directHostile(DamageSource source) {
        if (source.getEntity() == source.getDirectEntity()) return true;
        return source.getDirectEntity() instanceof AbstractArrow arrow
            && arrow.getOwner() == source.getEntity();
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    private static TagKey<DamageType> damageTag(String path) {
        return TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("rpgskilltree", path));
    }
}
