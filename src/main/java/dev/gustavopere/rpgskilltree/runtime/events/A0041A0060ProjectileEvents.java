package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy.BowShot;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy.CombatResult;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import dev.gustavopere.rpgskilltree.runtime.A0001A0020RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0041A0060RuntimeState;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/** Server-authoritative vanilla/NeoForge projectile bridge for bow and crossbow perks A0043-A0054. */
public final class A0041A0060ProjectileEvents {
    private static final long LAUNCH_CORRELATION_MILLIS = 250L;
    private static final long ABRUPT_AIM_BLOCK_MILLIS = 250L;
    private static final long ABRUPT_AIM_COOLDOWN_MILLIS = 500L;
    private static final Map<UUID, PendingLaunch> PENDING = new HashMap<>();
    private static final Map<UUID, AimTrack> AIM = new HashMap<>();
    private static final Map<UUID, CrossbowTrack> CROSSBOW = new HashMap<>();
    private static final WeakHashMap<AbstractArrow, ProjectileMeta> PROJECTILES = new WeakHashMap<>();
    private static final AtomicLong ACTION_SEQUENCE = new AtomicLong();

    private A0041A0060ProjectileEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onArrowLoose(ArrowLooseEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player) || event.isCanceled()) return;
        WeaponFamily family = family(event.getBow());
        if (family == null) return;

        long now = now(player);
        String actor = actor(player);
        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        String root = "ranged/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet();
        AimTrack aim = AIM.get(player.getUUID());
        long stableAim = aim == null || player.isSprinting() || now < aim.blockedUntil
            ? 0L : Math.max(0L, now - aim.stableSince);
        if (aim != null) aim.lastReleaseAt = now;

        if (family == WeaponFamily.BOW) {
            boolean fullyDrawn = event.hasAmmo() && BowItem.getPowerForTime(event.getCharge()) >= 0.999F;
            BowShot shot = A0041A0060CombatPolicy.tryPreparedShot(
                actor, root, ranks, A0041A0060RuntimeState.state(),
                A0041A0060RuntimeState.mastery(player, "epicfight:bow"), fullyDrawn, stableAim, now
            );
            if (!shot.active()) {
                shot = A0041A0060CombatPolicy.tryDominatedShot(
                    actor, root, ranks, A0041A0060RuntimeState.state(), fullyDrawn, stableAim,
                    true, true, now
                );
            }
            PENDING.put(player.getUUID(), PendingLaunch.bow(root, now, shot));
            return;
        }

        CombatResult adjusted = A0041A0060CombatPolicy.tryAdjustedCrossbowShot(
            actor, root, ranks, A0041A0060RuntimeState.state(), now
        );
        CombatResult piercing = adjusted.applied() ? CombatResult.neutral()
            : A0041A0060CombatPolicy.tryPiercingBolt(
                actor, root, ranks, A0041A0060RuntimeState.state(), true, true, false, now
            );
        PENDING.put(player.getUUID(), PendingLaunch.crossbow(root, now, adjusted, piercing));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)) return;
        ItemStack weapon = arrow.getWeaponItem();
        WeaponFamily family = family(weapon);
        if (family != WeaponFamily.BOW && family != WeaponFamily.CROSSBOW) return;

        long now = now(player);
        PendingLaunch pending = PENDING.get(player.getUUID());
        if (pending == null || pending.family != family || pending.expiresAt < now) {
            pending = PendingLaunch.neutral(
                family, "projectile/" + arrow.getUUID(), now + LAUNCH_CORRELATION_MILLIS
            );
        }

        if (pending.critical == null) {
            boolean providerCritical = arrow.isCritArrow();
            boolean critical = A0001A0020RuntimeState.critical().resolve(
                actor(player), pending.rootActionId, providerCritical,
                NotionCombatPerkRules.criticalChanceBonus(family, A0041A0060RuntimeState.ranks(player)), now
            );
            pending.critical = critical;
            pending.criticalMultiplierNeeded = critical && !providerCritical;
        }

        boolean special = !pending.specialProjectileClaimed;
        if (special && pending.hasSpecial()) pending.specialProjectileClaimed = true;
        BowShot bowShot = special ? pending.bowShot : BowShot.neutral();
        CombatResult crossbowShot = special ? pending.crossbowShot() : CombatResult.neutral();
        if (bowShot.active() && bowShot.launchSpeedMultiplier() != 1.0D) {
            arrow.setDeltaMovement(arrow.getDeltaMovement().scale(bowShot.launchSpeedMultiplier()));
        }

        ProjectileMeta meta = new ProjectileMeta(
            family, actor(player), pending.rootActionId, arrow.position(),
            NotionCombatPerkRules.baseDamageMultiplier(family, A0041A0060RuntimeState.ranks(player)),
            pending.criticalMultiplierNeeded, bowShot, crossbowShot
        );
        synchronized (PROJECTILES) {
            PROJECTILES.put(arrow, meta);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)
            || !hostile(player, event.getEntity())) return;
        ProjectileMeta meta = metadata(arrow);
        if (meta == null || !meta.actorId.equals(actor(player))) return;

        double multiplier = meta.baseDamageMultiplier * (meta.criticalMultiplierNeeded ? 1.5D : 1.0D);
        double penetration = 0.0D;
        if (!meta.specialImpactClaimed) {
            double distance = meta.origin.distanceTo(event.getEntity().position());
            CombatResult result;
            if (meta.family == WeaponFamily.BOW) {
                result = A0041A0060CombatPolicy.resolveBowHit(meta.bowShot, distance, true);
            } else {
                result = meta.crossbowShot;
            }
            if (result.applied()) {
                meta.specialImpactClaimed = true;
                multiplier *= result.damageMultiplier();
                penetration = result.penetrationFraction();
            }
        }

        if (Double.compare(multiplier, 1.0D) != 0) {
            event.setAmount((float) (event.getAmount() * multiplier));
        }
        if (penetration > 0.0D) {
            final float retainedReduction = (float) Math.max(0.0D, 1.0D - penetration);
            event.addReductionModifier(
                DamageContainer.Reduction.ARMOR,
                (container, reductionIn) -> reductionIn * retainedReduction
            );
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)
            || event.getNewDamage() <= 0.0F
            || !hostile(player, event.getEntity())) return;
        ProjectileMeta meta = metadata(arrow);
        if (meta == null) return;
        long now = now(player);
        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);

        if (meta.family == WeaponFamily.BOW) {
            int rank = ranks.rank("A0046");
            double distance = meta.origin.distanceTo(event.getEntity().position());
            if (rank > 0 && distance >= NotionCombatPerkRules.A0046_DISTANT_HIT_DISTANCE
                && !meta.focusHitCredited
                && A0041A0060RuntimeState.state().claimOnce(actor(player), arrow.getUUID().toString(), "A0046:distant", now)) {
                meta.focusHitCredited = true;
                A0041A0060RuntimeState.state().addFocus(
                    actor(player), A0041A0060CombatPolicy.focusDistantHitGain(rank)
                );
            }
        } else {
            A0041A0060CombatPolicy.recordCrossbowHit(
                actor(player), meta.rootActionId, ranks, A0041A0060RuntimeState.state(), now
            );
            meta.confirmedHit = true;
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)
            || event.getRayTraceResult() instanceof EntityHitResult
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)) return;
        ProjectileMeta meta = metadata(arrow);
        if (meta == null || meta.family != WeaponFamily.CROSSBOW || meta.confirmedHit || meta.failureRecorded) return;
        meta.failureRecorded = true;
        A0041A0060CombatPolicy.onCrossbowFailure(actor(player), A0041A0060RuntimeState.state());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTick(ServerTickEvent.Post event) {
        long now = event.getServer().overworld().getGameTime() * 50L;
        PENDING.entrySet().removeIf(entry -> entry.getValue().expiresAt < now);
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            if (!eligible(player)) continue;
            tickAim(player, now);
            tickCrossbow(player, now);
        }
    }

    private static void tickAim(ServerPlayer player, long now) {
        UUID id = player.getUUID();
        AimTrack track = AIM.computeIfAbsent(id, ignored -> new AimTrack());
        ItemStack using = player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY;
        boolean bow = using.getItem() instanceof BowItem;
        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        int rank = ranks.rank("A0046");

        if (!bow) {
            if (track.usingBow && track.lastDrawPower >= 0.80F && track.lastReleaseAt != now && rank > 0) {
                A0041A0060CombatPolicy.loseFocusForHighDrawCancel(actor(player), A0041A0060RuntimeState.state());
            }
            track.stop();
            return;
        }

        float yaw = player.getYRot();
        float pitch = player.getXRot();
        if (!track.usingBow) {
            track.start(now, yaw, pitch);
        } else {
            double dyaw = Math.abs(Mth.wrapDegrees(yaw - track.lastYaw));
            double dpitch = Math.abs(pitch - track.lastPitch);
            double angular = Math.hypot(dyaw, dpitch);
            track.pushAngular(angular);
            if (track.angularSum > 45.0D && now >= track.abruptCooldownUntil && rank > 0) {
                A0041A0060CombatPolicy.loseFocusForAbruptAim(actor(player), A0041A0060RuntimeState.state());
                track.blockedUntil = Math.addExact(now, ABRUPT_AIM_BLOCK_MILLIS);
                track.abruptCooldownUntil = Math.addExact(now, ABRUPT_AIM_COOLDOWN_MILLIS);
                track.stableSince = track.blockedUntil;
                track.clearAngular();
            }
            track.lastYaw = yaw;
            track.lastPitch = pitch;
        }
        track.lastDrawPower = BowItem.getPowerForTime(player.getTicksUsingItem());

        if (rank <= 0) return;
        if (player.isSprinting()) {
            A0041A0060CombatPolicy.drainFocusWhileSprinting(actor(player), A0041A0060RuntimeState.state(), 50L);
            track.stableSince = now;
            return;
        }
        if (now >= track.blockedUntil && player.getTicksUsingItem() > 0) {
            A0041A0060RuntimeState.state().addFocus(
                actor(player), A0041A0060CombatPolicy.focusStableGain(rank, 50L)
            );
        }
    }

    private static void tickCrossbow(ServerPlayer player, long now) {
        UUID id = player.getUUID();
        ItemStack stack = heldCrossbow(player);
        CrossbowTrack previous = CROSSBOW.get(id);
        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        boolean relevant = ranks.rank("A0052") > 0 || ranks.learned("A0054");

        if (stack.isEmpty()) {
            if (previous != null && relevant && A0041A0060RuntimeState.state().cadence(actor(player)) > 0) {
                A0041A0060CombatPolicy.onCrossbowFailure(actor(player), A0041A0060RuntimeState.state());
            }
            CROSSBOW.remove(id);
            return;
        }

        boolean same = previous != null && previous.stack == stack;
        if (!same) {
            if (previous != null && relevant && A0041A0060RuntimeState.state().cadence(actor(player)) > 0) {
                A0041A0060CombatPolicy.onCrossbowFailure(actor(player), A0041A0060RuntimeState.state());
            }
            previous = new CrossbowTrack(stack);
            CROSSBOW.put(id, previous);
        }

        boolean charged = CrossbowItem.isCharged(stack);
        boolean using = player.isUsingItem() && player.getUseItem() == stack;
        double progress = 0.0D;
        if (using) {
            int duration = Math.max(1, CrossbowItem.getChargeDuration(stack, player));
            progress = Math.min(1.0D, player.getTicksUsingItem() / (double) duration);
        }

        if (previous.using && !using && !previous.charged && !charged && previous.progress >= 0.50D && relevant) {
            A0041A0060CombatPolicy.onCrossbowFailure(actor(player), A0041A0060RuntimeState.state());
        }
        if (!previous.charged && charged && previous.using && relevant) {
            boolean completed = A0041A0060CombatPolicy.onCrossbowReloadComplete(
                actor(player), Integer.toHexString(System.identityHashCode(stack)), ranks,
                A0041A0060RuntimeState.state(), true, now
            );
            if (completed || A0041A0060RuntimeState.state().cadence(actor(player)) >= NotionCombatPerkRules.CADENCE_CAP) {
                A0041A0060CombatPolicy.armAdjustedMechanismOnReload(
                    actor(player), ranks, A0041A0060RuntimeState.state(),
                    A0041A0060RuntimeState.mastery(player, "epicfight:crossbow"), true, now
                );
            }
        }
        previous.charged = charged;
        previous.using = using;
        previous.progress = progress;
    }

    private static ItemStack heldCrossbow(ServerPlayer player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof CrossbowItem) return main;
        ItemStack off = player.getOffhandItem();
        return off.getItem() instanceof CrossbowItem ? off : ItemStack.EMPTY;
    }

    private static WeaponFamily family(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        if (stack.getItem() instanceof BowItem) return WeaponFamily.BOW;
        if (stack.getItem() instanceof CrossbowItem) return WeaponFamily.CROSSBOW;
        return null;
    }

    private static ProjectileMeta metadata(AbstractArrow arrow) {
        synchronized (PROJECTILES) {
            return PROJECTILES.get(arrow);
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clear(player);
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clear(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clear(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        PENDING.clear(); AIM.clear(); CROSSBOW.clear();
        synchronized (PROJECTILES) { PROJECTILES.clear(); }
        A0041A0060RuntimeState.clearAll();
    }

    private static void clear(ServerPlayer player) {
        PENDING.remove(player.getUUID());
        AIM.remove(player.getUUID());
        CROSSBOW.remove(player.getUUID());
        A0041A0060RuntimeState.clear(player);
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide() && !player.isCreative() && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player && target.isAlive() && !player.isAlliedTo(target) && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static String actor(ServerPlayer player) { return player.getUUID().toString(); }
    private static long now(ServerPlayer player) { return player.level().getGameTime() * 50L; }

    private static final class PendingLaunch {
        final WeaponFamily family;
        final String rootActionId;
        final long expiresAt;
        final BowShot bowShot;
        final CombatResult adjusted;
        final CombatResult piercing;
        Boolean critical;
        boolean criticalMultiplierNeeded;
        boolean specialProjectileClaimed;

        private PendingLaunch(WeaponFamily family, String rootActionId, long expiresAt, BowShot bowShot,
                              CombatResult adjusted, CombatResult piercing) {
            this.family = family; this.rootActionId = rootActionId; this.expiresAt = expiresAt;
            this.bowShot = bowShot; this.adjusted = adjusted; this.piercing = piercing;
        }

        static PendingLaunch bow(String root, long now, BowShot shot) {
            return new PendingLaunch(WeaponFamily.BOW, root, now + LAUNCH_CORRELATION_MILLIS,
                shot, CombatResult.neutral(), CombatResult.neutral());
        }

        static PendingLaunch crossbow(String root, long now, CombatResult adjusted, CombatResult piercing) {
            return new PendingLaunch(WeaponFamily.CROSSBOW, root, now + LAUNCH_CORRELATION_MILLIS,
                BowShot.neutral(), adjusted, piercing);
        }

        static PendingLaunch neutral(WeaponFamily family, String root, long expiresAt) {
            return new PendingLaunch(family, root, expiresAt, BowShot.neutral(), CombatResult.neutral(), CombatResult.neutral());
        }

        boolean hasSpecial() { return bowShot.active() || adjusted.applied() || piercing.applied(); }
        CombatResult crossbowShot() { return adjusted.applied() ? adjusted : piercing; }
    }

    private static final class ProjectileMeta {
        final WeaponFamily family;
        final String actorId;
        final String rootActionId;
        final Vec3 origin;
        final double baseDamageMultiplier;
        final boolean criticalMultiplierNeeded;
        final BowShot bowShot;
        final CombatResult crossbowShot;
        boolean specialImpactClaimed;
        boolean focusHitCredited;
        boolean confirmedHit;
        boolean failureRecorded;

        ProjectileMeta(WeaponFamily family, String actorId, String rootActionId, Vec3 origin,
                       double baseDamageMultiplier, boolean criticalMultiplierNeeded,
                       BowShot bowShot, CombatResult crossbowShot) {
            this.family = family; this.actorId = actorId; this.rootActionId = rootActionId;
            this.origin = origin; this.baseDamageMultiplier = baseDamageMultiplier;
            this.criticalMultiplierNeeded = criticalMultiplierNeeded;
            this.bowShot = bowShot; this.crossbowShot = crossbowShot;
        }
    }

    private static final class AimTrack {
        boolean usingBow;
        long stableSince;
        long blockedUntil;
        long abruptCooldownUntil;
        long lastReleaseAt;
        float lastYaw;
        float lastPitch;
        float lastDrawPower;
        final double[] angular = new double[5];
        int angularIndex;
        int angularCount;
        double angularSum;

        void start(long now, float yaw, float pitch) {
            usingBow = true; stableSince = now; lastYaw = yaw; lastPitch = pitch; lastDrawPower = 0.0F;
            blockedUntil = 0L; clearAngular();
        }

        void stop() {
            usingBow = false; stableSince = 0L; blockedUntil = 0L; lastDrawPower = 0.0F; clearAngular();
        }

        void pushAngular(double value) {
            if (angularCount < angular.length) angularCount++;
            else angularSum -= angular[angularIndex];
            angular[angularIndex] = value;
            angularSum += value;
            angularIndex = (angularIndex + 1) % angular.length;
        }

        void clearAngular() {
            for (int i = 0; i < angular.length; i++) angular[i] = 0.0D;
            angularIndex = 0; angularCount = 0; angularSum = 0.0D;
        }
    }

    private static final class CrossbowTrack {
        final ItemStack stack;
        boolean charged;
        boolean using;
        double progress;
        CrossbowTrack(ItemStack stack) {
            this.stack = stack;
            this.charged = CrossbowItem.isCharged(stack);
        }
    }
}
