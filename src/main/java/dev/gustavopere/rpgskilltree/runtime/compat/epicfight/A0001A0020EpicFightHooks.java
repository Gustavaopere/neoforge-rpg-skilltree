package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.A0001A0020CombatPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.A0001A0020RuntimeState;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.animation.AttackPhaseEndEvent;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.event.types.entity.DodgeEvent;
import yesman.epicfight.api.event.types.entity.ModifyAttackSpeedEvent;
import yesman.epicfight.api.event.types.player.TickPlayerEpicFightModeEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Provider-native Epic Fight 21.17.3.1 bridge for exactly A0001-A0020. */
public final class A0001A0020EpicFightHooks {
    public static final String SUPPORTED_VERSION_PREFIX = "21.17.3.1";
    private static final long CRITICAL_CORRELATION_MILLIS = 100L;

    private static final String PRE_ID = "rpgskilltree:a0001_a0020/pre";
    private static final String POST_ID = "rpgskilltree:a0001_a0020/post";
    private static final String SPEED_ID = "rpgskilltree:a0001_a0020/speed";
    private static final String DODGE_ID = "rpgskilltree:a0001_a0020/dodge";
    private static final String MISS_ID = "rpgskilltree:a0001_a0020/miss";
    private static final String TICK_ID = "rpgskilltree:a0001_a0020/tick";

    private static final WeakHashMap<EpicFightDamageSource, Map<String, PendingHit>> PENDING = new WeakHashMap<>();
    private static final Map<String, RecentCriticalRoot> RECENT_CRITICAL_ROOTS = new HashMap<>();
    private static final AtomicLong ACTION_SEQUENCE = new AtomicLong();
    private static boolean registered;

    private A0001A0020EpicFightHooks() {}

    public static boolean supportsVersion(String version) {
        return version != null && version.startsWith(SUPPORTED_VERSION_PREFIX);
    }

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE.registerEvent(A0001A0020EpicFightHooks::onDamagePre, PRE_ID);
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(A0001A0020EpicFightHooks::onDamagePost, POST_ID);
        EpicFightEventHooks.Entity.MODIFY_ATTACK_SPEED.registerEvent(A0001A0020EpicFightHooks::onAttackSpeed, SPEED_ID);
        EpicFightEventHooks.Entity.ON_DODGE.registerEvent(A0001A0020EpicFightHooks::onDodge, DODGE_ID);
        EpicFightEventHooks.Animation.ATTACK_PHASE_END.registerEvent(A0001A0020EpicFightHooks::onAttackPhaseEnd, MISS_ID);
        EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(A0001A0020EpicFightHooks::onEpicFightTick, TICK_ID);

        // The cumulative A0021-A0040 adapter shares this exact Epic Fight version contract.
        // Chaining it here leaves the global optional-provider bootstrap owned by main unchanged.
        A0021A0040EpicFightHooks.register();
        A0021A0040MasteryHooks.register();
        NeoForge.EVENT_BUS.register(A0021A0040EpicFightHooks.class);
        NeoForge.EVENT_BUS.register(A0021A0040MasteryHooks.class);
        registered = true;
    }

    /** Canonical NeoForge critical stage; Epic Fight PRE reuses this root when both callbacks exist. */
    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        if (!(event.getTarget() instanceof LivingEntity target)) return;
        if (!hostile(player, target)) return;
        Optional<WeaponFamily> family = family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem()));
        if (family.isEmpty()) return;
        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        double bonusChance = NotionCombatPerkRules.criticalChanceBonus(family.get(), ranks);
        if (bonusChance <= 0.0D && !event.isCriticalHit()) return;

        long now = now(player);
        String actorId = A0001A0020RuntimeState.actorId(player);
        String targetId = target.getUUID().toString();
        String rootActionId = "neoforge-critical/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet();
        boolean providerCritical = event.isCriticalHit();
        boolean critical = A0001A0020RuntimeState.critical().resolve(
            actorId, rootActionId, providerCritical, bonusChance, now);
        if (critical && !providerCritical) {
            event.setDamageMultiplier(Math.max(1.5F, event.getDamageMultiplier()));
        }
        event.setCriticalHit(critical);
        rememberRecentCriticalRoot(actorId, targetId, rootActionId, critical, now);
    }

    private static void onDamagePre(DealDamageEvent.Pre event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        LivingEntity target = event.getTarget();
        EpicFightDamageSource source = event.getDamageSource();
        if (source.getDirectEntity() != player || !hostile(player, target)) return;

        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(source.getUsedItem());
        Optional<WeaponFamily> family = family(capability);
        if (family.isEmpty()) return;

        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        if (ranks.ranks().isEmpty()) return;

        String targetId = target.getUUID().toString();
        long now = now(player);
        RootResolution root = rootAction(player, source, targetId, now);
        boolean critical = A0001A0020RuntimeState.critical().resolve(
            A0001A0020RuntimeState.actorId(player),
            root.rootActionId,
            root.providerCritical,
            NotionCombatPerkRules.criticalChanceBonus(family.get(), ranks),
            now
        );

        HurtableEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, HurtableEntityPatch.class);
        boolean defended = target.isBlocking() || targetPatch != null && targetPatch.getStunShield() > 0.0F;
        double effectiveReach = player.entityInteractionRange() + Math.max(0.0D, capability.getReach());
        boolean idealSpearRange = family.get() == WeaponFamily.SPEAR
            && A0001A0020CombatPolicy.isIdealSpearRange(player.distanceTo(target), effectiveReach);

        A0001A0020CombatPolicy.HitFacts facts = new A0001A0020CombatPolicy.HitFacts(
            A0001A0020RuntimeState.actorId(player),
            targetId,
            root.rootActionId,
            family.get(),
            true,
            true,
            true,
            defended,
            target.getArmorValue() > 0,
            source.shouldChargeWeapon(),
            idealSpearRange,
            critical,
            true,
            true,
            now
        );
        var modifiers = A0001A0020CombatPolicy.beforeHit(facts, ranks, A0001A0020RuntimeState.state());

        double damageMultiplier = modifiers.damageMultiplier();
        if (critical && !root.criticalMultiplierAlreadyApplied) damageMultiplier *= 1.5D;
        if (Double.compare(damageMultiplier, 1.0D) != 0) {
            source.attachDamageModifier(ValueModifier.multiplier((float)damageMultiplier));
        }
        if (modifiers.physicalPenetrationFraction() > 0.0D) {
            source.attachArmorNegationModifier(ValueModifier.adder((float)(modifiers.physicalPenetrationFraction() * 100.0D)));
        }
        double impact = defended
            ? Math.max(modifiers.impactMultiplier(), modifiers.guardPressureMultiplier())
            : modifiers.impactMultiplier();
        if (Double.compare(impact, 1.0D) != 0) {
            source.attachImpactModifier(ValueModifier.multiplier((float)impact));
        }

        remember(source, targetId, new PendingHit(
            root.rootActionId, family.get(), idealSpearRange, critical,
            root.criticalMultiplierAlreadyApplied, modifiers.suppressMomentumGain()));
    }

    private static void onDamagePost(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        if (event.getModifiedDamage() <= 0.0F) {
            forget(event.getDamageSource(), event.getTarget().getUUID().toString());
            return;
        }
        String targetId = event.getTarget().getUUID().toString();
        PendingHit pending = forget(event.getDamageSource(), targetId);
        if (pending == null || !hostile(player, event.getTarget())) return;
        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        A0001A0020CombatPolicy.HitFacts facts = new A0001A0020CombatPolicy.HitFacts(
            A0001A0020RuntimeState.actorId(player), targetId, pending.rootActionId, pending.family,
            true, true, true, false, event.getTarget().getArmorValue() > 0,
            event.getDamageSource().shouldChargeWeapon(), pending.idealSpearRange, pending.critical,
            true, true, now(player)
        );
        A0001A0020CombatPolicy.afterConfirmedHit(
            facts, ranks, A0001A0020RuntimeState.state(), pending.suppressMomentumGain);
    }

    private static void onAttackSpeed(ModifyAttackSpeedEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof Player player)) return;
        ProgressionState progression;
        if (player instanceof ServerPlayer serverPlayer) {
            if (!eligible(serverPlayer)) return;
            progression = PlayerProgressionRuntime.get(serverPlayer);
        } else {
            if (!player.isLocalPlayer()) return;
            progression = ClientProgressionState.get();
        }
        Optional<WeaponFamily> family = family(event.getItemCapability());
        if (family.isEmpty()) return;
        double bonus = NotionCombatPerkRules.rhythmBonus(family.get(), CombatPerkNodeBinding.ranks(progression.passiveNodes()));
        if (bonus > 0.0D) event.setAttackSpeed((float)(event.getAttackSpeed() * (1.0D + bonus)));
    }

    private static void onDodge(DodgeEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(player.getMainHandItem());
        if (family(capability).orElse(null) != WeaponFamily.SWORD) return;
        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        long now = now(player);
        String defenseEventId = "dodge/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet();
        A0001A0020CombatPolicy.onConfirmedTechnicalDefense(
            A0001A0020RuntimeState.actorId(player), defenseEventId, WeaponFamily.SWORD, ranks,
            A0001A0020RuntimeState.state(),
            PlayerProgressionRuntime.get(player).mastery().experience("epicfight:sword"),
            now
        );
    }

    /** Epic Fight clears the actual-hit list per attack phase, making an empty phase-end list a confirmed miss. */
    private static void onAttackPhaseEnd(AttackPhaseEndEvent event) {
        if (!(event.getEntityPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
        if (!eligible(player) || !patch.getCurrentlyActuallyHitEntities().isEmpty()) return;
        var hand = event.getPhase().effectiveHand(patch);
        Optional<WeaponFamily> family = family(patch.getHoldingItemCapability(hand));
        if (family.isEmpty() || (family.get() != WeaponFamily.SWORD && family.get() != WeaponFamily.SPEAR)) return;
        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        A0001A0020CombatPolicy.onConfirmedMiss(
            A0001A0020RuntimeState.actorId(player), family.get(), ranks, A0001A0020RuntimeState.state(), now(player));
    }

    private static void onEpicFightTick(TickPlayerEpicFightModeEvent event) {
        if (!(event.getPlayerPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
        if (!eligible(player)) return;
        long now = now(player);
        String actorId = A0001A0020RuntimeState.actorId(player);
        A0001A0020CombatPolicy.tick(actorId, A0001A0020RuntimeState.state(), now);

        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        if (ranks.rank("A0017") <= 0 && !ranks.learned("A0018")) return;
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(player.getMainHandItem());
        if (family(capability).orElse(null) != WeaponFamily.SPEAR) return;
        LivingEntity target = patch.getTarget();
        if (target == null || !target.isAlive() || !hostile(player, target)) return;

        double effectiveReach = player.entityInteractionRange() + Math.max(0.0D, capability.getReach());
        boolean insideIdealRange = A0001A0020CombatPolicy.isIdealSpearRange(player.distanceTo(target), effectiveReach);
        var motion = target.getDeltaMovement();
        boolean advancing = A0001A0020CombatPolicy.isAdvancingToward(
            player.getX(), player.getZ(), target.getX(), target.getZ(), motion.x, motion.z);
        A0001A0020CombatPolicy.onSpearRangeSample(
            actorId, target.getUUID().toString(), insideIdealRange, advancing, ranks,
            A0001A0020RuntimeState.state(),
            PlayerProgressionRuntime.get(player).mastery().experience("epicfight:spear"),
            now
        );
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
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        A0001A0020RuntimeState.clearAll();
        synchronized (A0001A0020EpicFightHooks.class) {
            PENDING.clear();
            RECENT_CRITICAL_ROOTS.clear();
        }
    }

    private static Optional<WeaponFamily> family(CapabilityItem capability) {
        if (capability == null || capability.isEmpty()) return Optional.empty();
        String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
        int slash = category.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
        return switch (category) {
            case "sword", "uchigatana", "tachi", "longsword" -> Optional.of(WeaponFamily.SWORD);
            case "axe" -> Optional.of(WeaponFamily.AXE);
            case "spear" -> Optional.of(WeaponFamily.SPEAR);
            case "dagger" -> Optional.of(WeaponFamily.DAGGER);
            default -> Optional.empty();
        };
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        if (target == player || player.isAlliedTo(target) || target.isInvulnerable()) return false;
        return target instanceof Enemy || target instanceof Player;
    }

    private static synchronized RootResolution rootAction(
        ServerPlayer player,
        EpicFightDamageSource source,
        String targetId,
        long nowMillis
    ) {
        Map<String, PendingHit> byTarget = PENDING.computeIfAbsent(source, ignored -> new HashMap<>());
        PendingHit existing = byTarget.get(targetId);
        if (existing != null) {
            return new RootResolution(existing.rootActionId, existing.critical, existing.criticalMultiplierAlreadyApplied);
        }
        String actorId = A0001A0020RuntimeState.actorId(player);
        RecentCriticalRoot correlated = claimRecentCriticalRoot(actorId, targetId, nowMillis);
        if (correlated != null) {
            return new RootResolution(correlated.rootActionId, correlated.critical, true);
        }
        return new RootResolution(
            "epicfight/" + nowMillis + "/" + ACTION_SEQUENCE.incrementAndGet(), false, false);
    }

    private static synchronized void rememberRecentCriticalRoot(
        String actorId,
        String targetId,
        String rootActionId,
        boolean critical,
        long nowMillis
    ) {
        pruneRecentCriticalRoots(nowMillis);
        RECENT_CRITICAL_ROOTS.put(
            criticalKey(actorId, targetId),
            new RecentCriticalRoot(rootActionId, critical, Math.addExact(nowMillis, CRITICAL_CORRELATION_MILLIS))
        );
    }

    private static synchronized RecentCriticalRoot claimRecentCriticalRoot(String actorId, String targetId, long nowMillis) {
        pruneRecentCriticalRoots(nowMillis);
        return RECENT_CRITICAL_ROOTS.remove(criticalKey(actorId, targetId));
    }

    private static void pruneRecentCriticalRoots(long nowMillis) {
        RECENT_CRITICAL_ROOTS.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private static String criticalKey(String actorId, String targetId) {
        return actorId + '\u0000' + targetId;
    }

    private static synchronized void remember(EpicFightDamageSource source, String targetId, PendingHit pending) {
        PENDING.computeIfAbsent(source, ignored -> new HashMap<>()).put(targetId, pending);
    }

    private static synchronized PendingHit forget(EpicFightDamageSource source, String targetId) {
        Map<String, PendingHit> byTarget = PENDING.get(source);
        if (byTarget == null) return null;
        PendingHit pending = byTarget.remove(targetId);
        if (byTarget.isEmpty()) PENDING.remove(source);
        return pending;
    }

    private static synchronized void clearPlayer(ServerPlayer player) {
        String actorId = A0001A0020RuntimeState.actorId(player);
        A0001A0020RuntimeState.clear(player);
        String prefix = actorId + '\u0000';
        RECENT_CRITICAL_ROOTS.keySet().removeIf(key -> key.startsWith(prefix));
        PENDING.entrySet().removeIf(entry -> entry.getKey().getEntity() == player);
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    private record RootResolution(
        String rootActionId,
        boolean providerCritical,
        boolean criticalMultiplierAlreadyApplied
    ) {}

    private record RecentCriticalRoot(
        String rootActionId,
        boolean critical,
        long expiresAtMillis
    ) {}

    private record PendingHit(
        String rootActionId,
        WeaponFamily family,
        boolean idealSpearRange,
        boolean critical,
        boolean criticalMultiplierAlreadyApplied,
        boolean suppressMomentumGain
    ) {}
}
