package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.A0001A0020RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0041A0060RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.client.ClientProgressionState;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.animation.AttackPhaseEndEvent;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.event.types.entity.ModifyAttackSpeedEvent;
import yesman.epicfight.api.event.types.player.TickPlayerEpicFightModeEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Epic Fight 21.17.3.1 bridge for the scythe completion and fist lane in A0041-A0060. */
public final class A0041A0060EpicFightHooks {
    public static final String SUPPORTED_VERSION_PREFIX = A0001A0020EpicFightHooks.SUPPORTED_VERSION_PREFIX;
    private static final String PRE_ID = "rpgskilltree:a0041_a0060/pre";
    private static final String POST_ID = "rpgskilltree:a0041_a0060/post";
    private static final String SPEED_ID = "rpgskilltree:a0041_a0060/speed";
    private static final String MISS_ID = "rpgskilltree:a0041_a0060/miss";
    private static final String TICK_ID = "rpgskilltree:a0041_a0060/tick";
    private static final long CRITICAL_CORRELATION_MILLIS = 100L;
    private static final long MATURE_KILL_CORRELATION_MILLIS = 1_000L;

    private static final WeakHashMap<EpicFightDamageSource, Map<String, PendingHit>> PENDING = new WeakHashMap<>();
    private static final Map<String, RecentCritical> RECENT_CRITICAL = new HashMap<>();
    private static final Map<String, MatureScytheHit> RECENT_MATURE_SCYTHE = new HashMap<>();
    private static final AtomicLong ACTION_SEQUENCE = new AtomicLong();
    private static boolean registered;

    private A0041A0060EpicFightHooks() {}

    public static boolean supportsVersion(String version) {
        return version != null && version.startsWith(SUPPORTED_VERSION_PREFIX);
    }

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE.registerEvent(A0041A0060EpicFightHooks::onDamagePre, PRE_ID);
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(A0041A0060EpicFightHooks::onDamagePost, POST_ID);
        EpicFightEventHooks.Entity.MODIFY_ATTACK_SPEED.registerEvent(A0041A0060EpicFightHooks::onAttackSpeed, SPEED_ID);
        EpicFightEventHooks.Animation.ATTACK_PHASE_END.registerEvent(A0041A0060EpicFightHooks::onAttackPhaseEnd, MISS_ID);
        EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(A0041A0060EpicFightHooks::onEpicFightTick, TICK_ID);
        registered = true;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCriticalHit(CriticalHitEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || !(event.getTarget() instanceof LivingEntity target)
            || !hostile(player, target)
            || family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem())).orElse(null) != WeaponFamily.FIST) return;

        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        double bonus = NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.FIST, ranks);
        boolean providerCritical = event.isCriticalHit();
        if (bonus <= 0.0D && !providerCritical) return;

        long now = now(player);
        String actor = actor(player);
        String targetId = target.getUUID().toString();
        String root = "fist-critical/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet();
        boolean critical = A0001A0020RuntimeState.critical().resolve(actor, root, providerCritical, bonus, now);
        boolean multiplierApplied = providerCritical;
        if (critical && !providerCritical) {
            event.setDamageMultiplier(Math.max(1.5F, event.getDamageMultiplier()));
            multiplierApplied = true;
        }
        event.setCriticalHit(critical);
        rememberCritical(actor, targetId, root, critical, multiplierApplied, now);
    }

    private static void onDamagePre(DealDamageEvent.Pre event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        LivingEntity target = event.getTarget();
        EpicFightDamageSource source = event.getDamageSource();
        if (source.getDirectEntity() != player || !hostile(player, target)) return;

        Optional<WeaponFamily> family = family(EpicFightCapabilities.getItemStackCapability(source.getUsedItem()));
        if (family.isEmpty() || (family.get() != WeaponFamily.SCYTHE && family.get() != WeaponFamily.FIST)) return;

        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        if (ranks.ranks().isEmpty()) return;
        long now = now(player);
        String actor = actor(player);
        String targetId = target.getUUID().toString();
        Root root = rootAction(source, actor, targetId, family.get(), ranks, now);

        double damage = 1.0D;
        double impact = 1.0D;
        if (family.get() == WeaponFamily.FIST) {
            damage *= NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.FIST, ranks);
            damage *= dev.gustavopere.rpgskilltree.core.A0061A0080CombatPolicy.criticalDamageMultiplier(ranks, root.critical);
            if (root.critical && !root.criticalMultiplierApplied) damage *= 1.5D;
            // A0059/A0060 intentionally remain fail-closed until Epic Fight exposes an
            // unambiguous server-authoritative heavy/finalizer receipt for the concrete action.
        } else {
            double healthFraction = healthFraction(target);
            boolean matureBefore = A0021A0040RuntimeState.state().reapMature(actor, targetId, healthFraction, now);
            if (matureBefore) rememberMatureScythe(actor, targetId, root.id, now);
            A0041A0060CombatPolicy.CombatResult cut = A0041A0060CombatPolicy.scytheCut(
                actor, targetId, root.id, ranks, A0021A0040RuntimeState.state(),
                A0041A0060RuntimeState.state(), healthFraction, true, now
            );
            damage *= cut.damageMultiplier();
            impact *= cut.impactMultiplier();
        }

        if (Double.compare(damage, 1.0D) != 0) {
            source.attachDamageModifier(ValueModifier.multiplier((float) damage));
        }
        if (Double.compare(impact, 1.0D) != 0) {
            source.attachImpactModifier(ValueModifier.multiplier((float) impact));
        }
        remember(source, targetId, new PendingHit(root.id, family.get()));
    }

    private static void onDamagePost(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        String targetId = event.getTarget().getUUID().toString();
        PendingHit pending = forget(event.getDamageSource(), targetId);
        if (pending == null || event.getModifiedDamage() <= 0.0F || !hostile(player, event.getTarget())) return;

        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        long now = now(player);
        String actor = actor(player);
        if (pending.family == WeaponFamily.FIST) {
            A0041A0060CombatPolicy.afterConfirmedFistHit(
                actor, pending.rootActionId, ranks, A0041A0060RuntimeState.state(), now
            );
            return;
        }

        if (A0041A0060CombatPolicy.consumeBattleHarvestOnHit(
            actor, targetId, ranks, A0041A0060RuntimeState.state(), now
        )) {
            int markRank = ranks.rank("A0040");
            if (markRank > 0) {
                A0021A0040RuntimeState.state().applyReapingMark(
                    actor, targetId, markRank, healthFraction(event.getTarget()), now
                );
            }
        }
    }

    private static void onAttackSpeed(ModifyAttackSpeedEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof Player player)
            || family(event.getItemCapability()).orElse(null) != WeaponFamily.FIST) return;

        ProgressionState progression;
        if (player instanceof ServerPlayer serverPlayer) {
            if (!eligible(serverPlayer)) return;
            progression = PlayerProgressionRuntime.get(serverPlayer);
        } else {
            if (!player.isLocalPlayer()) return;
            progression = ClientProgressionState.get();
        }
        double bonus = NotionCombatPerkRules.rhythmBonus(
            WeaponFamily.FIST, CombatPerkNodeBinding.ranks(progression.passiveNodes())
        );
        if (bonus > 0.0D) event.setAttackSpeed((float) (event.getAttackSpeed() * (1.0D + bonus)));
    }

    private static void onAttackPhaseEnd(AttackPhaseEndEvent event) {
        if (!(event.getEntityPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
        if (!eligible(player) || !patch.getCurrentlyActuallyHitEntities().isEmpty()) return;
        var hand = event.getPhase().effectiveHand(patch);
        if (family(patch.getHoldingItemCapability(hand)).orElse(null) != WeaponFamily.FIST) return;
        if (A0041A0060RuntimeState.ranks(player).rank("A0058") > 0) {
            A0041A0060CombatPolicy.breakFistSequence(actor(player), A0041A0060RuntimeState.state());
        }
    }

    private static void onEpicFightTick(TickPlayerEpicFightModeEvent event) {
        if (!(event.getPlayerPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
        if (!eligible(player)) return;
        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        if (ranks.rank("A0058") <= 0) return;
        if (family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem())).orElse(null) != WeaponFamily.FIST) {
            A0041A0060CombatPolicy.breakFistSequence(actor(player), A0041A0060RuntimeState.state());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity target = event.getEntity();
        if (!(event.getSource() instanceof EpicFightDamageSource source)
            || !(source.getDirectEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || !hostile(player, target)
            || family(EpicFightCapabilities.getItemStackCapability(source.getUsedItem())).orElse(null) != WeaponFamily.SCYTHE) return;

        long now = now(player);
        String actor = actor(player);
        String targetId = target.getUUID().toString();
        MatureScytheHit mature = claimMatureScythe(actor, targetId, now);
        if (mature == null) return;
        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        A0041A0060CombatPolicy.armBattleHarvestOnKill(
            actor, targetId, ranks, A0021A0040RuntimeState.state(), A0041A0060RuntimeState.state(),
            A0041A0060RuntimeState.mastery(player, "combat:scythe"), true, now
        );
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
        A0041A0060RuntimeState.clearAll();
        synchronized (A0041A0060EpicFightHooks.class) {
            PENDING.clear();
            RECENT_CRITICAL.clear();
            RECENT_MATURE_SCYTHE.clear();
        }
    }

    private static Optional<WeaponFamily> family(CapabilityItem capability) {
        if (capability == null || capability.isEmpty()) return Optional.empty();
        String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
        int slash = category.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
        return switch (category) {
            case "scythe" -> Optional.of(WeaponFamily.SCYTHE);
            case "fist", "knuckle" -> Optional.of(WeaponFamily.FIST);
            default -> Optional.empty();
        };
    }

    private static Root rootAction(
        EpicFightDamageSource source, String actor, String target, WeaponFamily family,
        CombatPerkRanks ranks, long now
    ) {
        synchronized (A0041A0060EpicFightHooks.class) {
            Map<String, PendingHit> map = PENDING.get(source);
            PendingHit pending = map == null ? null : map.get(target);
            if (pending != null) return new Root(pending.rootActionId, false, true);
        }
        if (family == WeaponFamily.FIST) {
            RecentCritical recent = claimCritical(actor, target, now);
            if (recent != null) return new Root(recent.rootActionId, recent.critical, recent.multiplierApplied);
            String id = "fist/" + now + "/" + ACTION_SEQUENCE.incrementAndGet();
            boolean critical = A0001A0020RuntimeState.critical().resolve(
                actor, id, false, NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.FIST, ranks), now
            );
            return new Root(id, critical, false);
        }
        return new Root("scythe/" + now + "/" + ACTION_SEQUENCE.incrementAndGet(), false, true);
    }

    private static synchronized void remember(EpicFightDamageSource source, String target, PendingHit pending) {
        PENDING.computeIfAbsent(source, ignored -> new HashMap<>()).put(target, pending);
    }

    private static synchronized PendingHit forget(EpicFightDamageSource source, String target) {
        Map<String, PendingHit> map = PENDING.get(source);
        if (map == null) return null;
        PendingHit pending = map.remove(target);
        if (map.isEmpty()) PENDING.remove(source);
        return pending;
    }

    private static synchronized void rememberCritical(
        String actor, String target, String root, boolean critical, boolean multiplierApplied, long now
    ) {
        pruneCritical(now);
        RECENT_CRITICAL.put(actor + '\0' + target,
            new RecentCritical(root, critical, multiplierApplied, Math.addExact(now, CRITICAL_CORRELATION_MILLIS)));
    }

    private static synchronized RecentCritical claimCritical(String actor, String target, long now) {
        pruneCritical(now);
        return RECENT_CRITICAL.remove(actor + '\0' + target);
    }

    private static void pruneCritical(long now) {
        RECENT_CRITICAL.entrySet().removeIf(entry -> entry.getValue().expiresAt <= now);
    }

    private static synchronized void rememberMatureScythe(String actor, String target, String root, long now) {
        RECENT_MATURE_SCYTHE.entrySet().removeIf(entry -> entry.getValue().expiresAt <= now);
        RECENT_MATURE_SCYTHE.put(actor + '\0' + target,
            new MatureScytheHit(root, Math.addExact(now, MATURE_KILL_CORRELATION_MILLIS)));
    }

    private static synchronized MatureScytheHit claimMatureScythe(String actor, String target, long now) {
        RECENT_MATURE_SCYTHE.entrySet().removeIf(entry -> entry.getValue().expiresAt <= now);
        return RECENT_MATURE_SCYTHE.remove(actor + '\0' + target);
    }

    private static void clearPlayer(ServerPlayer player) {
        String actor = actor(player);
        A0041A0060RuntimeState.clear(player);
        synchronized (A0041A0060EpicFightHooks.class) {
            RECENT_CRITICAL.keySet().removeIf(key -> key.startsWith(actor + '\0'));
            RECENT_MATURE_SCYTHE.keySet().removeIf(key -> key.startsWith(actor + '\0'));
        }
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide() && !player.isCreative() && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player && target.isAlive() && !player.isAlliedTo(target) && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static double healthFraction(LivingEntity target) {
        return target.getMaxHealth() <= 0.0F ? 0.0D
            : Math.max(0.0D, Math.min(1.0D, target.getHealth() / target.getMaxHealth()));
    }

    private static long now(ServerPlayer player) {
        return player.level().getGameTime() * 50L;
    }

    private static String actor(ServerPlayer player) {
        return player.getUUID().toString();
    }

    private record PendingHit(String rootActionId, WeaponFamily family) {}
    private record Root(String id, boolean critical, boolean criticalMultiplierApplied) {}
    private record RecentCritical(String rootActionId, boolean critical, boolean multiplierApplied, long expiresAt) {}
    private record MatureScytheHit(String rootActionId, long expiresAt) {}
}
