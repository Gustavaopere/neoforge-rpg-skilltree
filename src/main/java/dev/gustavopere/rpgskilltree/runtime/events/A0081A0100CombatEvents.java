package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0081A0100CombatPolicy;
import dev.gustavopere.rpgskilltree.core.A0081A0100DefenseState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0081A0100RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Server-authoritative runtime bridge for the canonical Notion batch A0081-A0100.
 *
 * <p>The bridge only consumes evidence that NeoForge can prove directly. Magic, elemental and
 * periodic sustain remain provider-owned until their adapters provide explicit classification and
 * causal authorship. The same rule keeps guard and generic third-party critical effects closed.</p>
 */
public final class A0081A0100CombatEvents {
    private static final TagKey<DamageType> PHYSICAL_DAMAGE = TagKey.create(
        Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "physical")
    );
    private static final WeakHashMap<DamageSource, Map<UUID, OutgoingDamageContext>> OUTGOING = new WeakHashMap<>();
    private static final AtomicLong ACTION_SEQUENCE = new AtomicLong();

    // These names are part of the runtime contract: no post-refund, animation, knockback or
    // presumed-crit heuristic may silently replace the missing causal provider receipts.
    private static final boolean FAIL_CLOSED_A0093 = true;
    private static final boolean FAIL_CLOSED_A0094 = true;
    private static final boolean FAIL_CLOSED_A0095 = true;
    private static final boolean FAIL_CLOSED_A0100 = true;

    private A0081A0100CombatEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0.0F) return;
        captureOutgoing(event);

        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        applyIncomingDefense(player, event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        OutgoingDamageContext outgoing = takeOutgoing(event.getSource(), event.getEntity().getUUID());
        if (outgoing != null && event.getNewDamage() > 0.0F) {
            resolveOutgoingDamage(outgoing, event.getNewDamage());
        }

        if (!(event.getEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || event.getNewDamage() <= 0.0F
            || !hostileSource(player, event.getSource())) return;

        String actor = A0081A0100RuntimeState.actorId(player);
        long nowMillis = nowMillis(player);
        long nowTick = player.level().getGameTime();
        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
        A0081A0100DefenseState defense = A0081A0100RuntimeState.defense();
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
                    false
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
        // A0099 shares A0079's detector. When this bridge is the fallback sampler it also owns
        // its lifecycle; clearing twice when Epic Fight is present is harmless and deterministic.
        A0061A0080RuntimeState.clearAll();
        synchronized (OUTGOING) {
            OUTGOING.clear();
        }
    }

    private static void captureOutgoing(LivingIncomingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!source.is(PHYSICAL_DAMAGE)) return;
        ServerPlayer player = physicalWeaponOwner(source);
        if (player == null || !eligible(player) || !hostile(player, event.getEntity())) return;

        boolean directMelee = source.getDirectEntity() == player
            && source.getEntity() == player
            && !player.getMainHandItem().isEmpty();
        boolean physicalProjectile = source.getDirectEntity() instanceof AbstractArrow arrow
            && arrow.getOwner() == player;
        boolean weapon = directMelee || physicalProjectile;
        if (!weapon) return;

        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
        if (ranks.rank("A0081") <= 0 && ranks.rank("A0082") <= 0
            && ranks.rank("A0086") <= 0 && ranks.rank("A0087") <= 0) return;

        String actor = A0081A0100RuntimeState.actorId(player);
        long atMillis = nowMillis(player);
        boolean rhythmActive = A0061A0080RuntimeState.state().sustainedRhythmActive(actor, atMillis);
        OutgoingDamageContext context = new OutgoingDamageContext(
            player,
            "sustain/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet(),
            event.getEntity().getHealth(),
            weapon,
            directMelee,
            rhythmActive
        );
        synchronized (OUTGOING) {
            OUTGOING.computeIfAbsent(source, ignored -> new HashMap<>())
                .put(event.getEntity().getUUID(), context);
        }
    }

    private static void resolveOutgoingDamage(OutgoingDamageContext context, double postMitigationDamage) {
        ServerPlayer player = context.player();
        if (!eligible(player) || !player.isAlive()) return;

        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
        String actor = A0081A0100RuntimeState.actorId(player);
        long nowMillis = nowMillis(player);
        long nowTick = player.level().getGameTime();

        if (context.directMelee() && ranks.rank("A0081") > 0) {
            A0081A0100RuntimeState.recovery().recordDamage(
                new CombatRecoveryService.DamageRequest(
                    actor,
                    context.rootActionId(),
                    true,
                    true,
                    true,
                    true,
                    context.rhythmActive(),
                    player.getMaxHealth(),
                    postMitigationDamage,
                    context.targetHealthBefore(),
                    ranks.rank("A0081")
                ),
                nowMillis
            );
        }

        double canonical = A0081A0100CombatPolicy.sustainCoefficient(
            ranks,
            context.weapon(),
            false,
            false,
            false
        );
        List<Double> candidates = new ArrayList<>();
        if (canonical > 0.0D) candidates.add(canonical);
        if (ranks.rank("A0087") > 0 && context.weapon()) {
            double bloodMinimum = A0081A0100RuntimeState.bloodThirst()
                .weaponMinimumCoefficient(actor, nowTick);
            if (bloodMinimum > 0.0D) candidates.add(bloodMinimum);
        }
        if (candidates.isEmpty()) return;

        double healingMultiplier = ranks.rank("A0087") > 0
            ? A0081A0100RuntimeState.bloodThirst().healingReceivedMultiplier(actor, nowTick)
            : 1.0D;
        SustainResolver.Resolution resolution = A0081A0100RuntimeState.sustain().resolve(
            new SustainResolver.Request(
                actor,
                context.rootActionId(),
                true,
                true,
                true,
                postMitigationDamage,
                context.targetHealthBefore(),
                player.getMaxHealth(),
                Math.max(0.0D, player.getMaxHealth() - player.getHealth()),
                healingMultiplier,
                SustainResolver.NativeCorrelation.NONE,
                0.0D,
                candidates
            ),
            nowTick
        );
        if (resolution.skillTreeHealing() > 0.0D) {
            player.heal((float) resolution.skillTreeHealing());
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
            // A0096 is hostile-only. Passing 1.0 for non-hostile physical damage keeps A0092
            // active while suppressing the conditional A0096 branch without inventing a second formula.
            multiplier *= A0081A0100CombatPolicy.physicalDamageMultiplier(
                ranks,
                hostile ? preImpactHealthFraction : 1.0D
            );
        }

        if (hostile) {
            A0081A0100DefenseState defense = A0081A0100RuntimeState.defense();
            double opening = A0081A0100CombatPolicy.openingDefenseMultiplier(actor, ranks, defense, now);
            if (Double.compare(opening, 1.0D) != 0 && defense.consumeOpeningDefense(actor, now)) {
                multiplier *= opening;
            }
            multiplier *= A0081A0100CombatPolicy.movingDefenseMultiplier(ranks, player.isSprinting());
            multiplier *= A0081A0100CombatPolicy.stationaryDefenseMultiplier(
                ranks,
                A0061A0080RuntimeState.stationary().isStationary(actor)
            );
        }

        // FAIL_CLOSED_A0093 / FAIL_CLOSED_A0094 / FAIL_CLOSED_A0095: no safe causal guard or
        // interruption contract is exposed by the audited provider surface. FAIL_CLOSED_A0100:
        // no generic incoming critical decomposition exists here. These constants intentionally
        // keep the unavailable branches explicit rather than approximating them.
        if (FAIL_CLOSED_A0093 && FAIL_CLOSED_A0094 && FAIL_CLOSED_A0095 && FAIL_CLOSED_A0100
            && Double.compare(multiplier, 1.0D) != 0) {
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

    private static boolean hostileSource(ServerPlayer player, DamageSource source) {
        return source.getEntity() instanceof LivingEntity attacker && hostile(player, attacker);
    }

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
        synchronized (OUTGOING) {
            OUTGOING.values().forEach(byTarget -> byTarget.values().removeIf(context -> context.player() == player));
            OUTGOING.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        }
    }

    private record OutgoingDamageContext(
        ServerPlayer player,
        String rootActionId,
        double targetHealthBefore,
        boolean weapon,
        boolean directMelee,
        boolean rhythmActive
    ) {}
}
