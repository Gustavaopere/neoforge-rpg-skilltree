package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0081A0100CombatPolicy;
import dev.gustavopere.rpgskilltree.core.A0081A0100DefenseState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0081A0090ProviderHitRegistry;
import dev.gustavopere.rpgskilltree.runtime.A0081A0090ProviderHitRegistry.PhysicalHitReceipt;
import dev.gustavopere.rpgskilltree.runtime.A0081A0090SustainRuntime;
import dev.gustavopere.rpgskilltree.runtime.A0081A0100RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.compat.A0079ForcedMovementCompat;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Server-authoritative runtime bridge for the canonical Notion batch A0081-A0100.
 *
 * <p>Only provider-proven roots enter sustain. Incoming A0097 reservations are correlated by the
 * concrete DamageSource+target root and commit only after positive post-mitigation damage. A0098
 * and A0099 share the A0079 forced-movement boundary instead of inferring locomotion from client
 * animation or velocity.</p>
 */
public final class A0081A0100CombatEvents {
    private static final long LAUNCH_CORRELATION_MILLIS = 250L;
    private static final TagKey<DamageType> PHYSICAL_DAMAGE = TagKey.create(
        Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "physical")
    );
    private static final WeakHashMap<DamageSource, Map<UUID, OutgoingDamageContext>> OUTGOING = new WeakHashMap<>();
    private static final WeakHashMap<DamageSource, Map<UUID, PendingIncomingDefense>> INCOMING_DEFENSE = new WeakHashMap<>();
    private static final WeakHashMap<DamageSource, String> VANILLA_MELEE_ROOTS = new WeakHashMap<>();
    private static final Map<UUID, PendingProjectileLaunch> PENDING_PROJECTILE_LAUNCHES = new HashMap<>();
    private static final WeakHashMap<AbstractArrow, String> PROJECTILE_ROOTS = new WeakHashMap<>();
    private static final AtomicLong ACTION_SEQUENCE = new AtomicLong();

    private A0081A0100CombatEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onArrowLoose(ArrowLooseEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player) || event.isCanceled()) return;
        ProjectileFamily family = projectileFamily(event.getBow());
        if (family == null) return;
        long now = nowMillis(player);
        PENDING_PROJECTILE_LAUNCHES.put(
            player.getUUID(),
            new PendingProjectileLaunch(
                family,
                "sustain-ranged/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet(),
                Math.addExact(now, LAUNCH_CORRELATION_MILLIS)
            )
        );
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onProjectileJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)) return;
        ProjectileFamily family = projectileFamily(arrow.getWeaponItem());
        if (family == null) return;
        long now = nowMillis(player);
        PendingProjectileLaunch pending = PENDING_PROJECTILE_LAUNCHES.get(player.getUUID());
        if (pending == null || pending.family() != family || pending.expiresAt() < now) return;
        synchronized (PROJECTILE_ROOTS) {
            PROJECTILE_ROOTS.put(arrow, pending.rootActionId());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0.0F) return;
        captureOutgoing(event);

        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        applyIncomingDefense(player, event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        PhysicalHitReceipt providerReceipt = A0081A0090ProviderHitRegistry.take(
            event.getSource(), event.getEntity().getUUID()
        );
        OutgoingDamageContext fallback = takeOutgoing(event.getSource(), event.getEntity().getUUID());
        if (event.getNewDamage() > 0.0F) {
            if (providerReceipt != null && eligible(providerReceipt.player())) {
                A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                    providerReceipt.player(),
                    providerReceipt.rootActionId(),
                    providerReceipt.targetHealthBefore(),
                    event.getNewDamage(),
                    providerReceipt.directMelee(),
                    providerReceipt.weaponStack()
                );
            } else if (fallback != null && eligible(fallback.player())) {
                A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                    fallback.player(),
                    fallback.rootActionId(),
                    fallback.targetHealthBefore(),
                    event.getNewDamage(),
                    fallback.directMelee(),
                    fallback.weaponStack()
                );
            }
        }

        PendingIncomingDefense pendingOpening = takeIncomingDefense(
            event.getSource(), event.getEntity().getUUID()
        );
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;

        String actor = A0081A0100RuntimeState.actorId(player);
        long nowMillis = nowMillis(player);
        A0081A0100DefenseState defense = A0081A0100RuntimeState.defense();
        boolean hostile = hostileSource(player, event.getSource());
        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);

        if (pendingOpening != null) {
            if (event.getNewDamage() > 0.0F && hostile && ranks.rank("A0097") > 0) {
                defense.commitOpeningDefense(actor, pendingOpening.rootActionId(), nowMillis);
            } else {
                defense.rollbackOpeningDefense(actor, pendingOpening.rootActionId());
            }
        }

        if (event.getNewDamage() <= 0.0F || !hostile) return;

        long nowTick = player.level().getGameTime();
        // Every effective eligible hostile hit restarts A0097's timer, independently of whether
        // that particular hit held the opening reservation. Commit above is root-specific and
        // idempotent; recording the same timestamp here is deliberate.
        defense.recordEligibleHostileDamage(actor, nowMillis);

        if (ranks.rank("A0081") > 0) {
            A0081A0100RuntimeState.recovery().recordHostileDamage(actor, true, nowMillis);
        }
        if (ranks.rank("A0087") > 0) {
            A0081A0100RuntimeState.bloodThirst().recordHostileDamage(
                actor,
                event.getNewDamage(),
                player.getMaxHealth(),
                true,
                nowTick
            );
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTick(ServerTickEvent.Post event) {
        long now = event.getServer().overworld().getGameTime() * 50L;
        PENDING_PROJECTILE_LAUNCHES.entrySet().removeIf(entry -> entry.getValue().expiresAt() < now);
        pruneIncomingDefense(now);

        boolean previousBatchSamplesStationary = previousBatchSamplesStationary();
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            if (!eligible(player)) continue;

            String actor = A0081A0100RuntimeState.actorId(player);
            if (!previousBatchSamplesStationary) {
                A0061A0080RuntimeState.stationary().sample(
                    actor,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    A0079ForcedMovementCompat.forcedOrUnclassified(player)
                );
            }

            CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
            if (ranks.rank("A0081") > 0) {
                offerRecoveryInstallment(player);
            } else {
                A0081A0100RuntimeState.recovery().clearActor(actor);
            }

            if (ranks.rank("A0082") <= 0 && ranks.rank("A0083") <= 0
                && ranks.rank("A0084") <= 0 && ranks.rank("A0085") <= 0
                && ranks.rank("A0086") <= 0 && ranks.rank("A0087") <= 0) {
                A0081A0100RuntimeState.sustain().clearActor(actor);
            }
            if (ranks.rank("A0087") <= 0) {
                A0081A0100RuntimeState.bloodThirst().clearActor(actor);
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        A0081A0100RuntimeState.clearAll();
        A0061A0080RuntimeState.clearAll();
        A0079ForcedMovementCompat.clearAll();
        PENDING_PROJECTILE_LAUNCHES.clear();
        synchronized (PROJECTILE_ROOTS) {
            PROJECTILE_ROOTS.clear();
        }
        synchronized (OUTGOING) {
            OUTGOING.clear();
            VANILLA_MELEE_ROOTS.clear();
        }
        synchronized (INCOMING_DEFENSE) {
            INCOMING_DEFENSE.clear();
        }
    }

    private static void captureOutgoing(LivingIncomingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!source.is(PHYSICAL_DAMAGE)) return;
        ServerPlayer player = physicalWeaponOwner(source);
        if (player == null || !eligible(player) || !hostile(player, event.getEntity())) return;

        boolean directMelee = source.getClass() == DamageSource.class
            && source.is(DamageTypes.PLAYER_ATTACK)
            && source.getDirectEntity() == player
            && source.getEntity() == player
            && !player.getMainHandItem().isEmpty();

        AbstractArrow arrow = source.getDirectEntity() instanceof AbstractArrow candidate
            && candidate.getOwner() == player ? candidate : null;
        String projectileRoot = arrow == null ? null : projectileRoot(arrow);
        boolean physicalProjectile = arrow != null && projectileRoot != null;
        if (!directMelee && !physicalProjectile) return;

        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
        if (ranks.rank("A0081") <= 0 && ranks.rank("A0082") <= 0
            && ranks.rank("A0086") <= 0 && ranks.rank("A0087") <= 0) return;

        String root = directMelee ? vanillaMeleeRoot(source, player) : projectileRoot;
        ItemStack weaponStack = directMelee ? player.getMainHandItem() : arrow.getWeaponItem();
        OutgoingDamageContext context = new OutgoingDamageContext(
            player,
            root,
            event.getEntity().getHealth(),
            directMelee,
            weaponStack.copy()
        );
        synchronized (OUTGOING) {
            OUTGOING.computeIfAbsent(source, ignored -> new HashMap<>())
                .put(event.getEntity().getUUID(), context);
        }
    }

    private static void applyIncomingDefense(ServerPlayer player, LivingIncomingDamageEvent event) {
        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
        boolean physical = event.getSource().is(PHYSICAL_DAMAGE);
        boolean hostile = hostileSource(player, event.getSource());
        String actor = A0081A0100RuntimeState.actorId(player);
        long now = nowMillis(player);
        double multiplier = 1.0D;

        if (physical) {
            double preImpactHealthFraction = player.getMaxHealth() <= 0.0F
                ? 0.0D
                : Math.max(0.0D, Math.min(1.0D, player.getHealth() / player.getMaxHealth()));
            // A0092 remains physical-channel based. A0096 is hostile-only; passing 1.0 for a
            // non-hostile physical source suppresses only A0096 without creating a second formula.
            multiplier *= A0081A0100CombatPolicy.physicalDamageMultiplier(
                ranks,
                hostile ? preImpactHealthFraction : 1.0D
            );
        }

        if (hostile) {
            A0081A0100DefenseState defense = A0081A0100RuntimeState.defense();
            double opening = A0081A0100CombatPolicy.openingDefenseMultiplier(actor, ranks, defense, now);
            if (Double.compare(opening, 1.0D) != 0
                && peekIncomingDefense(event.getSource(), player.getUUID()) == null) {
                String root = "incoming-defense/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet();
                if (defense.reserveOpeningDefense(actor, root, now)) {
                    rememberIncomingDefense(
                        event.getSource(),
                        player.getUUID(),
                        new PendingIncomingDefense(root, Math.addExact(now, A0081A0100DefenseState.RESERVATION_RETENTION_MILLIS))
                    );
                    multiplier *= opening;
                }
            }

            multiplier *= A0081A0100CombatPolicy.movingDefenseMultiplier(
                ranks,
                A0079ForcedMovementCompat.selfPropelledSprintEligible(player)
            );
            multiplier *= A0081A0100CombatPolicy.stationaryDefenseMultiplier(
                ranks,
                A0061A0080RuntimeState.stationary().isStationary(actor)
                    && !A0079ForcedMovementCompat.forcedOrUnclassified(player)
            );
        }

        // A0093/A0094/A0100 are masked to rank zero by CombatPerkAvailabilityRuntime. A0095 is
        // provider-native through epicfight:stun_armor and therefore has no damage-event heuristic.
        if (Double.compare(multiplier, 1.0D) != 0) {
            event.setAmount((float) Math.max(0.0D, event.getAmount() * multiplier));
        }
    }

    private static void offerRecoveryInstallment(ServerPlayer player) {
        CombatRecoveryService recovery = A0081A0100RuntimeState.recovery();
        double missingHealth = Math.max(0.0D, player.getMaxHealth() - player.getHealth());
        var offered = recovery.offerInstallment(
            A0081A0100RuntimeState.actorId(player),
            player.getMaxHealth(),
            missingHealth,
            nowMillis(player)
        );
        if (offered.isEmpty()) return;

        CombatRecoveryService.Installment installment = offered.get();
        float before = player.getHealth();
        player.heal((float) installment.attemptedHealing());
        double actual = Math.min(
            installment.attemptedHealing(),
            Math.max(0.0D, player.getHealth() - before)
        );
        recovery.confirmHealed(installment, actual);
    }

    private static ServerPlayer physicalWeaponOwner(DamageSource source) {
        if (source.getDirectEntity() instanceof ServerPlayer player && source.getEntity() == player) {
            return player;
        }
        if (source.getDirectEntity() instanceof AbstractArrow arrow
            && arrow.getOwner() instanceof ServerPlayer player
            && source.getEntity() == player) {
            return player;
        }
        return null;
    }

    private static OutgoingDamageContext takeOutgoing(DamageSource source, UUID targetId) {
        synchronized (OUTGOING) {
            Map<UUID, OutgoingDamageContext> byTarget = OUTGOING.get(source);
            if (byTarget == null) return null;
            OutgoingDamageContext context = byTarget.remove(targetId);
            if (byTarget.isEmpty()) OUTGOING.remove(source);
            return context;
        }
    }

    private static void rememberIncomingDefense(DamageSource source, UUID targetId, PendingIncomingDefense pending) {
        synchronized (INCOMING_DEFENSE) {
            INCOMING_DEFENSE.computeIfAbsent(source, ignored -> new HashMap<>()).put(targetId, pending);
        }
    }

    private static PendingIncomingDefense peekIncomingDefense(DamageSource source, UUID targetId) {
        synchronized (INCOMING_DEFENSE) {
            Map<UUID, PendingIncomingDefense> byTarget = INCOMING_DEFENSE.get(source);
            return byTarget == null ? null : byTarget.get(targetId);
        }
    }

    private static PendingIncomingDefense takeIncomingDefense(DamageSource source, UUID targetId) {
        synchronized (INCOMING_DEFENSE) {
            Map<UUID, PendingIncomingDefense> byTarget = INCOMING_DEFENSE.get(source);
            if (byTarget == null) return null;
            PendingIncomingDefense pending = byTarget.remove(targetId);
            if (byTarget.isEmpty()) INCOMING_DEFENSE.remove(source);
            return pending;
        }
    }

    private static void pruneIncomingDefense(long nowMillis) {
        synchronized (INCOMING_DEFENSE) {
            INCOMING_DEFENSE.values().forEach(byTarget ->
                byTarget.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis() < nowMillis)
            );
            INCOMING_DEFENSE.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        }
    }

    private static String vanillaMeleeRoot(DamageSource source, ServerPlayer player) {
        synchronized (OUTGOING) {
            return VANILLA_MELEE_ROOTS.computeIfAbsent(
                source,
                ignored -> "sustain-melee/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet()
            );
        }
    }

    private static String projectileRoot(AbstractArrow arrow) {
        synchronized (PROJECTILE_ROOTS) {
            return PROJECTILE_ROOTS.get(arrow);
        }
    }

    private static ProjectileFamily projectileFamily(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        if (stack.getItem() instanceof BowItem) return ProjectileFamily.BOW;
        if (stack.getItem() instanceof CrossbowItem) return ProjectileFamily.CROSSBOW;
        return null;
    }

    /** A0096/A0097 authority: any causal non-self, non-allied LivingEntity. */
    private static boolean hostileSource(ServerPlayer player, DamageSource source) {
        return source.getEntity() instanceof LivingEntity attacker
            && attacker != player
            && !player.isAlliedTo(attacker);
    }

    /** Preserve the older outgoing sustain target policy; A0097 does not broaden A0081-A0087. */
    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static boolean previousBatchSamplesStationary() {
        if (!OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT)) return false;
        return previousBatchSamplesStationaryVersion(
            OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT)
        );
    }

    static boolean previousBatchSamplesStationaryVersion(String version) {
        return EpicFightVersionContract.supportsVersion(version);
    }

    private static long nowMillis(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }

    private static void clearPlayer(ServerPlayer player) {
        A0081A0100RuntimeState.clear(player);
        A0061A0080RuntimeState.clear(player);
        A0079ForcedMovementCompat.clearPlayer(player);
        PENDING_PROJECTILE_LAUNCHES.remove(player.getUUID());
        synchronized (PROJECTILE_ROOTS) {
            PROJECTILE_ROOTS.entrySet().removeIf(entry -> entry.getKey().getOwner() == player);
        }
        synchronized (OUTGOING) {
            OUTGOING.values().forEach(byTarget -> byTarget.values().removeIf(context -> context.player() == player));
            OUTGOING.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        }
        synchronized (INCOMING_DEFENSE) {
            INCOMING_DEFENSE.values().forEach(byTarget -> byTarget.remove(player.getUUID()));
            INCOMING_DEFENSE.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        }
    }

    private enum ProjectileFamily { BOW, CROSSBOW }

    private record PendingProjectileLaunch(ProjectileFamily family, String rootActionId, long expiresAt) {}

    private record OutgoingDamageContext(
        ServerPlayer player,
        String rootActionId,
        double targetHealthBefore,
        boolean directMelee,
        ItemStack weaponStack
    ) {}

    private record PendingIncomingDefense(String rootActionId, long expiresAtMillis) {}
}
