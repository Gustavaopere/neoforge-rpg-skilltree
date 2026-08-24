package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.CanonicalFocusService;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatWeaponMasteryPolicy;
import dev.gustavopere.rpgskilltree.core.CombatFistPolicy;
import dev.gustavopere.rpgskilltree.core.CrossbowCadenceService;
import dev.gustavopere.rpgskilltree.core.FrozenCombatOffensePolicy;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.FrozenMartialOffenseService;
import dev.gustavopere.rpgskilltree.core.FrozenMartialTacticsService;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import dev.gustavopere.rpgskilltree.runtime.BossRewardKeyResolver;
import dev.gustavopere.rpgskilltree.runtime.CanonicalCombatRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.CanonicalSustainRuntime;
import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.EliteTargetResolver;
import dev.gustavopere.rpgskilltree.runtime.FrozenCombatRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.ArrowNockEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** NeoForge adapters for canonical melee criticals, target debuffs, and bow shot/projectile correlation. */
public final class CanonicalCombatEvents {
    private static final TagKey<Item> SWORDS = tag("swords");
    private static final TagKey<Item> AXES = tag("axes");
    private static final TagKey<Item> SPEARS = tag("spears");
    private static final TagKey<Item> DAGGERS = tag("daggers");
    private static final TagKey<Item> HAMMERS = tag("hammers");
    private static final TagKey<Item> MACES = tag("maces");
    private static final TagKey<Item> SCYTHES = tag("scythes");
    private static final TagKey<Item> BOWS = tag("bows");
    private static final TagKey<Item> CROSSBOWS = tag("crossbows");
    private static final TagKey<Item> FIST_WEAPONS = tag("fist_weapons");
    private static final TagKey<DamageType> MAGIC_DIRECT = damageTag("magic_direct");
    private static final TagKey<DamageType> ELEMENTAL_DIRECT = damageTag("elemental_direct");
    private static final TagKey<DamageType> PERIODIC_SUSTAIN = damageTag("periodic_sustain");
    private static final TagKey<DamageType> BLOOD_MAGIC_COST = damageTag("blood_magic_cost");
    private static final Map<String, CrossbowReloadStart> CROSSBOW_RELOADS = new HashMap<>();
    private static final ResourceLocation A0036_DESYNC_MOVEMENT =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "a0036_desync_movement");

    private CanonicalCombatEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCriticalHit(CriticalHitEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        ItemStack held = player.getMainHandItem();
        Optional<WeaponFamily> family = weaponFamily(held);
        boolean curatedFist = CombatFistPolicy.isFistWeapon(
            held.isEmpty(), held.is(FIST_WEAPONS), CombatFistPolicy.ProviderCategory.UNKNOWN);
        if ((family.isEmpty() && !curatedFist)
            || family.orElse(null) == WeaponFamily.BOW
            || family.orElse(null) == WeaponFamily.CROSSBOW) return;

        long nowMillis = now(player);
        CanonicalActionIdentity action = CanonicalCombatRuntimeState.beginMelee(
            player,
            event.getTarget().getUUID().toString(),
            nowMillis
        );
        boolean critical = curatedFist
            ? CanonicalCombatRuntimeState.resolveCriticalBonus(
                action, event.isCriticalHit(),
                FrozenCombatOffensePolicy.fistCriticalChance(FrozenCombatRuntimeState.ranks(player))
                    + FrozenMartialOffenseService.criticalChanceBonus(
                        FrozenCombatRuntimeState.ranks(player), true, true, true, true), nowMillis)
            : CanonicalCombatRuntimeState.resolveCritical(
                action, family.orElseThrow(), CombatPerkRuntimeState.ranks(player), event.isCriticalHit(),
                FrozenMartialOffenseService.criticalChanceBonus(
                    FrozenCombatRuntimeState.ranks(player), true, true, true, true), nowMillis);
        if (critical && !event.isCriticalHit()) {
            event.setDamageMultiplier(Math.max(1.5F, event.getDamageMultiplier()));
        }
        event.setCriticalHit(critical);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onArrowNock(ArrowNockEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || event.getLevel().isClientSide()
            || event.isCanceled()
            || !event.hasAmmo()
            || !(event.getBow().getItem() instanceof BowItem)
            || !eligible(player)) {
            return;
        }
        CanonicalCombatRuntimeState.beginAim(player, now(player));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onArrowLoose(ArrowLooseEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || event.getLevel().isClientSide()
            || event.isCanceled()
            || event.getCharge() < 0
            || !event.hasAmmo()
            || !(event.getBow().getItem() instanceof BowItem)
            || !eligible(player)) {
            return;
        }
        boolean fullyDrawn = BowItem.getPowerForTime(event.getCharge()) >= 1.0F;
        CanonicalCombatRuntimeState.recordLoose(
            player,
            fullyDrawn,
            preparedShotCooldown(player),
            now(player)
        );
    }

    /**
     * Stop fires before BowItem's normal release. We only stage the cancellation here; a normal
     * ArrowLoose clears it synchronously, while a genuine no-arrow stop is resolved next tick.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onUseItemStop(LivingEntityUseItemEvent.Stop event) {
        if (event.getEntity() instanceof ServerPlayer crossbowPlayer
            && event.getItem().getItem() instanceof CrossbowItem
            && eligible(crossbowPlayer)) {
            CrossbowReloadStart start = CROSSBOW_RELOADS.remove(crossbowPlayer.getUUID().toString());
            int totalDuration = event.getItem().getUseDuration(crossbowPlayer);
            int usedTicks = Math.max(0, totalDuration - event.getDuration());
            if (start != null && totalDuration > 0 && usedTicks * 2 > totalDuration) {
                FrozenCombatRuntimeState.crossbow().missOrCancel(crossbowPlayer.getUUID().toString());
            }
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player)
            || !(event.getItem().getItem() instanceof BowItem)
            || !eligible(player)
            || !CanonicalCombatRuntimeState.hasAim(player)) {
            return;
        }
        int totalDuration = event.getItem().getUseDuration(player);
        int usedTicks = Math.max(0, totalDuration - event.getDuration());
        double drawFraction = BowItem.getPowerForTime(usedTicks);
        CanonicalCombatRuntimeState.recordUseStop(player, drawFraction, now(player));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || event.loadedFromDisk()) return;
        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.getOwner() instanceof ServerPlayer owner) || !eligible(owner)) return;

        ItemStack weapon = arrow.getWeaponItem();
        WeaponFamily family = weaponFamily(weapon).orElse(null);
        if (family != WeaponFamily.BOW && family != WeaponFamily.CROSSBOW) return;
        long nowMillis = now(owner);
        String projectileId = arrow.getUUID().toString();
        if (family == WeaponFamily.CROSSBOW) {
            ItemStack held = owner.getMainHandItem();
            ItemStack identityStack = weaponFamily(held).orElse(null) == WeaponFamily.CROSSBOW ? held : weapon;
            String stackIdentity = FrozenCombatRuntimeState.stackIdentity(owner, identityStack);
            CanonicalActionIdentity action = CanonicalCombatRuntimeState.crossbowProjectileAction(
                owner, stackIdentity, projectileId, nowMillis);
            FrozenCombatPerkRanks ranks = FrozenCombatRuntimeState.ranks(owner);
            FrozenCombatRuntimeState.crossbow().fire(new CrossbowCadenceService.ShotRequest(
                action, projectileId, stackIdentity, true, true, true, true,
                ranks.rank("A0053"), ranks.rank("A0054"),
                net.neoforged.fml.ModList.get().isLoaded("epicfight"),
                net.neoforged.fml.ModList.get().isLoaded("epicfight")
            ), nowMillis);
            boolean critical = CanonicalCombatRuntimeState.resolveCriticalBonus(
                action, arrow.isCritArrow(), FrozenCombatOffensePolicy.crossbowCriticalChance(ranks)
                    + FrozenMartialOffenseService.criticalChanceBonus(ranks, true, true, true, true), nowMillis);
            arrow.setCritArrow(critical);
            return;
        }
        Optional<CanonicalCombatRuntimeState.ShotCorrelation> correlated =
            CanonicalCombatRuntimeState.correlateProjectile(owner, projectileId, nowMillis);
        CanonicalActionIdentity action = correlated
            .map(CanonicalCombatRuntimeState.ShotCorrelation::action)
            .orElseGet(() -> CanonicalCombatRuntimeState.projectileAction(owner, projectileId, nowMillis));

        if (correlated.isPresent() && correlated.get().facts() != null && correlated.get().facts().fullyDrawn()) {
            CanonicalFocusService focus = CombatPerkRuntimeState.state().focusService();
            CanonicalFocusService.ReleaseStatus release = focus.release(
                new CanonicalFocusService.ReleaseRequest(
                    action, true, true, true, correlated.get().facts().cooldownMillis()
                ),
                CombatPerkRuntimeState.state(),
                nowMillis
            );
            if (release == CanonicalFocusService.ReleaseStatus.PREPARED_CONSUMED
                || release == CanonicalFocusService.ReleaseStatus.DUPLICATE) {
                focus.attachProjectile(
                    new CanonicalFocusService.ProjectileRequest(
                        action, projectileId, owner.getUUID().toString(), true, true, true
                    )
                );
            }
        }

        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(owner);
        boolean critical = CanonicalCombatRuntimeState.resolveCritical(
            action,
            WeaponFamily.BOW,
            ranks,
            arrow.isCritArrow(),
            FrozenMartialOffenseService.criticalChanceBonus(
                FrozenCombatRuntimeState.ranks(owner), true, true, true, true),
            nowMillis
        );
        arrow.setCritArrow(critical);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        long nowMillis = now(player);
        FrozenCombatRuntimeState.stationary().observe(new dev.gustavopere.rpgskilltree.core.StationaryStateService.Sample(
            player.getUUID().toString(), player.level().getGameTime(), player.level().dimension().location().toString(),
            player.getX(), player.getY(), player.getZ(), player.isPassenger(), false, false, true
        ));
        FrozenCombatRuntimeState.revalidateStance(player);

        int recoveryRank = FrozenCombatRuntimeState.ranks(player).rank("A0081");
        if (recoveryRank > 0 && eligible(player)) {
            FrozenCombatRuntimeState.recovery().offerInstallment(
                player.getUUID().toString(),
                player.getMaxHealth(),
                Math.max(0.0D, player.getMaxHealth() - player.getHealth()),
                nowMillis
            ).ifPresent(installment -> {
                double before = player.getHealth();
                player.heal((float) installment.attemptedHealing());
                FrozenCombatRuntimeState.recovery().confirmHealed(
                    installment,
                    Math.max(0.0D, player.getHealth() - before)
                );
            });
        }

        CrossbowReloadStart reload = CROSSBOW_RELOADS.get(player.getUUID().toString());
        if (reload != null) {
            String heldIdentity = FrozenCombatRuntimeState.stackIdentity(player, player.getMainHandItem());
            if (!reload.stackIdentity.equals(heldIdentity)) {
                CROSSBOW_RELOADS.remove(player.getUUID().toString());
                FrozenCombatRuntimeState.crossbow().missOrCancel(player.getUUID().toString());
            }
        }

        if (CanonicalCombatRuntimeState.hasPendingCancelledDraw(player)) {
            if (CanonicalCombatRuntimeState.resolvePendingCancelledDraw(player, nowMillis)) return;
            // Stop and ArrowLoose can share a game tick; do not invalidate the pending proof early.
            if (CanonicalCombatRuntimeState.hasPendingCancelledDraw(player)) return;
        }
        if (!CanonicalCombatRuntimeState.hasAim(player)) return;
        if (!eligible(player)) {
            CanonicalCombatRuntimeState.invalidateAim(player, nowMillis);
            return;
        }
        if (!player.isUsingItem() || !(player.getUseItem().getItem() instanceof BowItem)) {
            CanonicalCombatRuntimeState.invalidateAim(player, nowMillis);
            return;
        }
        CanonicalCombatRuntimeState.sampleAim(player, nowMillis);
    }

    /** Keeps the A0036 movement modifier synchronized with the canonical transient target service. */
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity living) || living.level().isClientSide()) return;
        var movement = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement == null) return;

        String targetId = living.getUUID().toString();
        if (!living.isAlive()) {
            CombatPerkRuntimeState.targetDebuffs().clearTarget(targetId);
            movement.removeModifier(A0036_DESYNC_MOVEMENT);
            return;
        }

        long nowMillis = Math.multiplyExact(living.level().getGameTime(), 50L);
        var desync = CombatPerkRuntimeState.targetDebuffs().desync(targetId, nowMillis);
        if (desync.isEmpty()) {
            movement.removeModifier(A0036_DESYNC_MOVEMENT);
            return;
        }

        movement.addOrUpdateTransientModifier(new AttributeModifier(
            A0036_DESYNC_MOVEMENT,
            desync.get().movementSpeedMultiplier() - 1.0D,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }

    @SubscribeEvent
    public static void onLivingDamaged(LivingDamageEvent.Post event) {
        if (event.getNewDamage() <= 0.0F) return;

        if (event.getEntity() instanceof ServerPlayer victim && eligible(victim)) {
            boolean directHostile = event.getSource().getDirectEntity() != null
                && event.getSource().getEntity() instanceof LivingEntity attacker
                && attacker != victim
                && !victim.isAlliedTo(attacker)
                && (attacker instanceof Enemy || attacker instanceof Player)
                && !event.getSource().is(BLOOD_MAGIC_COST);
            FrozenCombatRuntimeState.tactics().confirmDirectHostileDamage(
                victim.getUUID().toString(), true, directHostile, false, now(victim));
            if (directHostile) {
                FrozenCombatRuntimeState.recovery().recordHostileDamage(
                    victim.getUUID().toString(), true, now(victim));
                if (FrozenCombatRuntimeState.ranks(victim).learned("A0087")) {
                    FrozenCombatRuntimeState.bloodThirst().recordHostileDamage(
                        victim.getUUID().toString(),
                        event.getNewDamage(),
                        victim.getMaxHealth(),
                        true,
                        victim.level().getGameTime()
                    );
                }
            }
        }

        if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow
            && arrow.getOwner() instanceof ServerPlayer owner
            && eligible(owner)
            && weaponFamily(arrow.getWeaponItem()).orElse(null) == WeaponFamily.BOW) {
            int focusRank = CombatPerkRuntimeState.ranks(owner).rank("A0046");
            if (focusRank > 0) {
                long nowMillis = now(owner);
                String projectileId = arrow.getUUID().toString();
                CanonicalCombatRuntimeState.projectileShotFacts(owner, projectileId, nowMillis).ifPresent(facts -> {
                    double dx = event.getEntity().getX() - facts.shotX();
                    double dy = event.getEntity().getY() - facts.shotY();
                    double dz = event.getEntity().getZ() - facts.shotZ();
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    CombatPerkRuntimeState.state().focusService().creditDistantProjectileHit(
                        new CanonicalFocusService.DistantHitRequest(
                            facts.action(), projectileId, true, true, true, distance, focusRank
                        ),
                        CombatPerkRuntimeState.state(),
                        nowMillis
                    );
                });
            }
        }

        if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow
            && arrow.getOwner() instanceof ServerPlayer owner
            && eligible(owner)
            && weaponFamily(arrow.getWeaponItem()).orElse(null) == WeaponFamily.CROSSBOW) {
            long nowMillis = now(owner);
            CanonicalActionIdentity action = CanonicalCombatRuntimeState.projectileAction(
                owner, arrow.getUUID().toString(), nowMillis);
            FrozenCombatRuntimeState.crossbow().confirmHit(arrow.getUUID().toString(), action, nowMillis);
        }

        resolveFrozenSustain(event);

        if (event.getEntity() instanceof ServerPlayer player) {
            CanonicalCombatRuntimeState.invalidateAim(player, now(player));
        }
    }

    /** Vanilla physical fallback; the shared action ledger prevents NeoForge + Epic Fight double application. */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        rememberCanonicalDamageAction(event);
        if (CanonicalSustainRuntime.isProviderClassifiedNonWeapon(event.getSource())) return;
        if (event.getEntity() instanceof ServerPlayer victim && eligible(victim)
            && safeDirectPhysical(event)) {
            double taken = FrozenCombatRuntimeState.tactics().directPhysicalDamageTakenMultiplier(
                victim.getUUID().toString());
            if (Double.compare(taken, 1.0D) != 0) event.setAmount((float)(event.getAmount() * taken));
        }

        ServerPlayer owner;
        ItemStack weapon;
        AbstractArrow arrow = null;
        if (event.getSource().getDirectEntity() instanceof AbstractArrow directArrow
            && directArrow.getOwner() instanceof ServerPlayer projectileOwner) {
            owner = projectileOwner;
            arrow = directArrow;
            weapon = directArrow.getWeaponItem();
        } else if (event.getSource().getEntity() instanceof ServerPlayer meleeOwner
            && event.getSource().getDirectEntity() == meleeOwner) {
            owner = meleeOwner;
            weapon = meleeOwner.getMainHandItem();
        } else return;
        if (!eligible(owner) || owner.isAlliedTo(event.getEntity())) return;
        WeaponFamily family = weaponFamily(weapon).orElse(null);
        boolean fist = CombatFistPolicy.isFistWeapon(
            weapon.isEmpty(), weapon.is(FIST_WEAPONS), CombatFistPolicy.ProviderCategory.UNKNOWN);
        if (family == null && !fist) return;
        long nowMillis = now(owner);
        String targetId = event.getEntity().getUUID().toString();
        Optional<CanonicalCombatRuntimeState.DamageActionFacts> known =
            CanonicalCombatRuntimeState.damageAction(event.getSource(), targetId);
        CanonicalActionIdentity action;
        if (known.isPresent()) action = known.get().action();
        else if (arrow != null) {
            action = CanonicalCombatRuntimeState.projectileAction(owner, arrow.getUUID().toString(), nowMillis);
        } else {
            action = CanonicalCombatRuntimeState.claimMeleeForProvider(owner, targetId, nowMillis)
                .orElseGet(() -> CanonicalCombatRuntimeState.newRoot(
                    owner, "neoforge:living_incoming", nowMillis));
        }
        CanonicalCombatRuntimeState.rememberDamageAction(
            event.getSource(), targetId, action, event.getEntity().getHealth());
        boolean critical = CanonicalCombatRuntimeState.criticalDecision(action, nowMillis).orElse(false);
        double healthFraction = event.getEntity().getMaxHealth() <= 0.0F ? 1.0D
            : Math.max(0.0D, Math.min(1.0D, event.getEntity().getHealth() / event.getEntity().getMaxHealth()));
        var modifiers = FrozenCombatRuntimeState.offense().resolve(new FrozenMartialOffenseService.AttackRequest(
            action, true, true, true, true, true, healthFraction,
            BossRewardKeyResolver.isBoss(event.getEntity()), critical, false, false
        ), FrozenCombatRuntimeState.ranks(owner), nowMillis);
        double multiplier = modifiers.damageMultiplier() * modifiers.criticalDamageMultiplier();
        if (Double.compare(multiplier, 1.0D) != 0) {
            event.setAmount((float)(event.getAmount() * multiplier));
        }
        var tactics = FrozenCombatRuntimeState.tactics().resolveAttack(new FrozenMartialTacticsService.AttackRequest(
            action, event.getEntity().getUUID().toString(), true, true, true, true, true, healthFraction,
            EliteTargetResolver.isElite(event.getEntity()), BossRewardKeyResolver.isBoss(event.getEntity()), false,
            owner.isSprinting() && !owner.isPassenger(), FrozenCombatRuntimeState.stationary().state(
                owner.getUUID().toString()).stationary()
        ), FrozenCombatRuntimeState.ranks(owner), nowMillis);
        if (Double.compare(tactics.damageMultiplier(), 1.0D) != 0) {
            event.setAmount((float)(event.getAmount() * tactics.damageMultiplier()));
        }
        if (arrow != null && family == WeaponFamily.CROSSBOW) {
            AbstractArrow correlatedArrow = arrow;
            FrozenCombatRuntimeState.crossbow().claimFirstImpact(
                correlatedArrow.getUUID().toString(), action, nowMillis)
                .filter(effect -> effect.damageBonus() > 0.0D)
                .ifPresent(effect -> event.setAmount((float)(event.getAmount() * (1.0D + effect.damageBonus()))));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCrossbowUseStart(LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)
            || !(event.getItem().getItem() instanceof CrossbowItem)
            || CrossbowItem.isCharged(event.getItem())) return;
        CROSSBOW_RELOADS.put(player.getUUID().toString(), new CrossbowReloadStart(
            FrozenCombatRuntimeState.stackIdentity(player, event.getItem()), now(player)));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCrossbowUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        CrossbowReloadStart start = CROSSBOW_RELOADS.remove(player.getUUID().toString());
        ItemStack result = event.getResultStack();
        if (start == null || !(result.getItem() instanceof CrossbowItem) || !CrossbowItem.isCharged(result)) return;
        String stackIdentity = FrozenCombatRuntimeState.stackIdentity(player, result);
        FrozenCombatPerkRanks ranks = FrozenCombatRuntimeState.ranks(player);
        int mastery = PlayerProgressionRuntime.get(player).mastery().experience("combat:crossbow");
        FrozenCombatRuntimeState.crossbow().completeReload(new CrossbowCadenceService.ReloadRequest(
            player.getUUID().toString(), stackIdentity, true, true,
            start.stackIdentity.equals(stackIdentity), now(player) > start.startedAtMillis,
            ranks.rank("A0052"), ranks.rank("A0054"), mastery
        ), now(player));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTeleport(EntityTeleportEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof ServerPlayer player) {
            FrozenCombatRuntimeState.stationary().invalidate(player.getUUID().toString());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onKnockback(LivingKnockBackEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof ServerPlayer player) {
            FrozenCombatRuntimeState.stationary().invalidate(player.getUUID().toString());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHeal(LivingHealEvent event) {
        CanonicalSustainRuntime.clampHealing(event);
    }

    private static void rememberCanonicalDamageAction(LivingIncomingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof ServerPlayer owner) || !eligible(owner)
            || !validHostileTarget(owner, event.getEntity())) return;
        String targetId = event.getEntity().getUUID().toString();
        if (CanonicalCombatRuntimeState.damageAction(source, targetId).isPresent()) return;

        boolean providerNonWeapon = source.getClass().getName()
            .equals("io.redspace.ironsspellbooks.damage.SpellDamageSource");
        if (providerNonWeapon) CanonicalSustainRuntime.markProviderClassifiedNonWeapon(source);

        boolean periodic = source.is(PERIODIC_SUSTAIN);
        boolean explicitlyMagic = source.is(MAGIC_DIRECT) || source.is(ELEMENTAL_DIRECT);
        boolean directOwner = source.getDirectEntity() == owner;
        Projectile projectile = source.getDirectEntity() instanceof Projectile value ? value : null;
        boolean ownedProjectile = projectile != null && projectile.getOwner() == owner;
        if (!periodic && !directOwner && !ownedProjectile) return;

        long nowMillis = now(owner);
        CanonicalActionIdentity action;
        if (periodic) {
            action = CanonicalCombatRuntimeState.periodicPulseAction(
                owner, source, owner.level().getGameTime());
        } else if (ownedProjectile) {
            action = CanonicalCombatRuntimeState.projectileAction(
                owner, projectile.getUUID().toString(), nowMillis);
        } else if (explicitlyMagic || providerNonWeapon) {
            action = CanonicalCombatRuntimeState.newRoot(
                owner, providerNonWeapon ? "irons:spell_damage" : "neoforge:direct_magic", nowMillis);
        } else {
            ItemStack weapon = owner.getMainHandItem();
            boolean fist = CombatFistPolicy.isFistWeapon(
                weapon.isEmpty(), weapon.is(FIST_WEAPONS), CombatFistPolicy.ProviderCategory.UNKNOWN);
            if (weaponFamily(weapon).isEmpty() && !fist) return;
            action = CanonicalCombatRuntimeState.claimMeleeForProvider(owner, targetId, nowMillis)
                .orElseGet(() -> CanonicalCombatRuntimeState.newRoot(
                    owner, "neoforge:living_incoming", nowMillis));
        }
        CanonicalCombatRuntimeState.rememberDamageAction(
            source, targetId, action, event.getEntity().getHealth());
    }

    private static void resolveFrozenSustain(LivingDamageEvent.Post event) {
        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof ServerPlayer owner) || !eligible(owner)
            || !validHostileTarget(owner, event.getEntity()) || source.is(BLOOD_MAGIC_COST)) return;
        String targetId = event.getEntity().getUUID().toString();
        Optional<CanonicalCombatRuntimeState.DamageActionFacts> known =
            CanonicalCombatRuntimeState.damageAction(source, targetId);
        if (known.isEmpty()) return;

        boolean directOwner = source.getDirectEntity() == owner;
        Projectile projectile = source.getDirectEntity() instanceof Projectile value ? value : null;
        boolean ownedProjectile = projectile != null && projectile.getOwner() == owner;
        boolean periodic = source.is(PERIODIC_SUSTAIN);
        boolean magic = source.is(MAGIC_DIRECT) && !periodic;
        boolean elemental = source.is(ELEMENTAL_DIRECT) && !periodic;
        boolean weapon = false;
        if (!magic && !elemental && !periodic
            && !CanonicalSustainRuntime.isProviderClassifiedNonWeapon(source)) {
            ItemStack weaponStack = projectile instanceof AbstractArrow arrow
                ? arrow.getWeaponItem() : owner.getMainHandItem();
            weapon = weaponFamily(weaponStack).isPresent() || CombatFistPolicy.isFistWeapon(
                weaponStack.isEmpty(), weaponStack.is(FIST_WEAPONS), CombatFistPolicy.ProviderCategory.UNKNOWN);
        }
        boolean ownerProven = periodic ? source.getEntity() == owner : directOwner || ownedProjectile;
        if (!weapon && !magic && !elemental && !periodic || !ownerProven) return;

        CanonicalSustainRuntime.Classification classification = new CanonicalSustainRuntime.Classification(
            weapon, magic, elemental, periodic, true);
        CanonicalSustainRuntime.resolve(
            owner,
            known.get().action(),
            classification,
            event.getNewDamage(),
            known.get().targetHealthBefore(),
            CanonicalSustainRuntime.hasAmbiguousNativeHealing(source)
                ? SustainResolver.NativeCorrelation.AMBIGUOUS
                : SustainResolver.NativeCorrelation.NONE,
            0.0D
        );

        if (weapon && directOwner) {
            FrozenCombatPerkRanks ranks = FrozenCombatRuntimeState.ranks(owner);
            int recoveryRank = ranks.rank("A0081");
            if (recoveryRank > 0) {
                FrozenCombatRuntimeState.recovery().recordDamage(
                    new CombatRecoveryService.DamageRequest(
                        known.get().action(), true, true, true, true,
                        FrozenCombatRuntimeState.rhythm().staminaCostMultiplier(
                            owner.getUUID().toString(), owner.level().getGameTime()) < 1.0D,
                        owner.getMaxHealth(), event.getNewDamage(), known.get().targetHealthBefore(), recoveryRank
                    ),
                    now(owner)
                );
            }
        }
    }

    private static boolean validHostileTarget(ServerPlayer owner, LivingEntity target) {
        return target != owner && !owner.isAlliedTo(target)
            && (target instanceof Enemy || target instanceof Player);
    }

    static Optional<WeaponFamily> weaponFamily(ItemStack stack) {
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

    private static long preparedShotCooldown(ServerPlayer player) {
        int mastery = PlayerProgressionRuntime.get(player).mastery().experience(
            CombatWeaponMasteryPolicy.masteryLane(WeaponFamily.BOW)
        );
        if (mastery >= 100) return 6_000L;
        if (mastery >= 90) return 7_000L;
        return 8_000L;
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }

    private static TagKey<Item> tag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("rpgskilltree", path));
    }

    private static TagKey<DamageType> damageTag(String path) {
        return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("rpgskilltree", path));
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    private static boolean safeDirectPhysical(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof AbstractArrow) return true;
        return event.getSource().getEntity() instanceof LivingEntity attacker
            && event.getSource().getDirectEntity() == attacker;
    }

    private record CrossbowReloadStart(String stackIdentity, long startedAtMillis) {}
}
