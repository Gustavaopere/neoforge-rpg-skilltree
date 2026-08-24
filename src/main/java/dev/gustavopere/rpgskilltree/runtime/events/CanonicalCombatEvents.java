package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.CanonicalFocusService;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatWeaponFamilyPolicy;
import dev.gustavopere.rpgskilltree.core.CombatWeaponMasteryPolicy;
import dev.gustavopere.rpgskilltree.runtime.CanonicalCombatRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
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
    private static final ResourceLocation A0036_DESYNC_MOVEMENT =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "a0036_desync_movement");

    private CanonicalCombatEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCriticalHit(CriticalHitEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        Optional<WeaponFamily> family = weaponFamily(player.getMainHandItem());
        if (family.isEmpty() || family.get() == WeaponFamily.BOW || family.get() == WeaponFamily.CROSSBOW) return;

        long nowMillis = now(player);
        CanonicalActionIdentity action = CanonicalCombatRuntimeState.beginMelee(
            player,
            event.getTarget().getUUID().toString(),
            nowMillis
        );
        boolean critical = CanonicalCombatRuntimeState.resolveCritical(
            action,
            family.get(),
            CombatPerkRuntimeState.ranks(player),
            event.isCriticalHit(),
            nowMillis
        );
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
        if (weaponFamily(weapon).orElse(null) != WeaponFamily.BOW) return;
        long nowMillis = now(owner);
        String projectileId = arrow.getUUID().toString();
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
            nowMillis
        );
        arrow.setCritArrow(critical);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        long nowMillis = now(player);

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

        if (event.getEntity() instanceof ServerPlayer player) {
            CanonicalCombatRuntimeState.invalidateAim(player, now(player));
        }
    }

    static Optional<WeaponFamily> weaponFamily(ItemStack stack) {
        Set<WeaponFamily> explicitFamilies = new HashSet<>();
        if (stack.is(SWORDS)) explicitFamilies.add(WeaponFamily.SWORD);
        if (stack.is(AXES)) explicitFamilies.add(WeaponFamily.AXE);
        if (stack.is(SPEARS)) explicitFamilies.add(WeaponFamily.SPEAR);
        if (stack.is(DAGGERS)) explicitFamilies.add(WeaponFamily.DAGGER);
        if (stack.is(HAMMERS)) explicitFamilies.add(WeaponFamily.HAMMER);
        if (stack.is(MACES)) explicitFamilies.add(WeaponFamily.MACE);
        if (stack.is(SCYTHES)) explicitFamilies.add(WeaponFamily.SCYTHE);
        if (stack.is(BOWS)) explicitFamilies.add(WeaponFamily.BOW);
        if (stack.is(CROSSBOWS)) explicitFamilies.add(WeaponFamily.CROSSBOW);

        Optional<WeaponFamily> vanillaFallback = Optional.empty();
        if (stack.getItem() instanceof BowItem) vanillaFallback = Optional.of(WeaponFamily.BOW);
        else if (stack.getItem() instanceof CrossbowItem) vanillaFallback = Optional.of(WeaponFamily.CROSSBOW);
        return CombatWeaponFamilyPolicy.resolve(explicitFamilies, vanillaFallback);
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

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }
}
