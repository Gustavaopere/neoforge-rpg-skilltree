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
        // Chaining it here keeps the global mod bootstrap identical to main's optional-provider boundary.
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
        if (critical && !root.providerCritical) {
            source.attachDamageModifier(ValueModifier.multiplier(1.5F));
        }

        boolean targetDefending = target.isBlocking();
        boolean providerGuard = EpicFightCapabilities.getEntityPatch(target, LivingEntity.class)
            .filter(HurtableEntityPatch.class::isInstance)
            .map(HurtableEntityPatch.class::cast)
            .map(patch -> patch.getStunShield() > 0.0F)
            .orElse(false);
        boolean relevantDefense = providerGuard || targetDefending;
        boolean armor = target.getArmorValue() > 0;

        A0001A0020CombatPolicy.BeforeHitResult result = A0001A0020CombatPolicy.beforeHit(
            new A0001A0020CombatPolicy.HitFacts(
                A0001A0020RuntimeState.actorId(player),
                targetId,
                root.rootActionId,
                family.get(),
                true,
                true,
                true,
                critical,
                relevantDefense,
                providerGuard,
                armor,
                true,
                true,
                now
            ),
            ranks,
            A0001A0020RuntimeState.state(),
            mastery(player, family.get())
        );

        double damage = NotionCombatPerkRules.baseDamageMultiplier(family.get(), ranks) * result.damageMultiplier();
        if (Double.compare(damage, 1.0D) != 0) source.attachDamageModifier(ValueModifier.multiplier((float) damage));
        if (Double.compare(result.impactMultiplier(), 1.0D) != 0) {
            source.attachImpactModifier(ValueModifier.multiplier((float) result.impactMultiplier()));
        }
        if (result.physicalPenetrationFraction() > 0.0D) {
            source.attachArmorNegationModifier(ValueModifier.adder((float) (result.physicalPenetrationFraction() * 100.0D)));
        }

        remember(source, targetId, new PendingHit(root.rootActionId, family.get(), critical, result));
    }

    private static void onDamagePost(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        String targetId = event.getTarget().getUUID().toString();
        PendingHit pending = forget(event.getDamageSource(), targetId);
        if (pending == null || event.getModifiedDamage() <= 0.0F || !hostile(player, event.getTarget())) return;
        long now = now(player);
        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        A0001A0020CombatPolicy.afterConfirmedHit(
            new A0001A0020CombatPolicy.HitFacts(
                A0001A0020RuntimeState.actorId(player),
                targetId,
                pending.rootActionId,
                pending.family,
                true,
                true,
                true,
                pending.critical,
                false,
                false,
                event.getTarget().getArmorValue() > 0,
                true,
                true,
                now
            ),
            ranks,
            A0001A0020RuntimeState.state()
        );
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
        if (bonus > 0.0D) event.setAttackSpeed((float) (event.getAttackSpeed() * (1.0D + bonus)));
    }

    private static void onDodge(DodgeEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        if (family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem())).orElse(null) != WeaponFamily.SWORD) return;
        A0001A0020CombatPolicy.onConfirmedDodge(
            A0001A0020RuntimeState.actorId(player), A0001A0020RuntimeState.ranks(player), A0001A0020RuntimeState.state(), now(player));
    }

    private static void onAttackPhaseEnd(AttackPhaseEndEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        Optional<WeaponFamily> family = family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem()));
        if (family.isEmpty()) return;
        CombatPerkRanks ranks = A0001A0020RuntimeState.ranks(player);
        // Epic Fight's actual hit list is the provider-native miss receipt; no distance/cosmetic heuristic.
        if (event.getHitEntities().isEmpty()) {
            A0001A0020CombatPolicy.onConfirmedMiss(
                A0001A0020RuntimeState.actorId(player), family.get(), ranks, A0001A0020RuntimeState.state(), now(player));
        }
    }

    private static void onEpicFightTick(TickPlayerEpicFightModeEvent event) {
        if (!(event.getPlayerPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
        if (!eligible(player)) return;
        if (family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem())).orElse(null) != WeaponFamily.SPEAR) return;
        LivingEntity target = patch.getTarget();
        if (target == null || !hostile(player, target)) return;
        double distance = player.distanceTo(target);
        float reach = Math.max(1.0F, patch.getArmature().searchJointByName("Tool_R").isPresent() ? 4.5F : 3.0F);
        boolean ideal = NotionCombatPerkRules.isSpearIdealRange(distance, reach);
        A0001A0020CombatPolicy.onSpearTargetRangeSample(
            A0001A0020RuntimeState.actorId(player),
            target.getUUID().toString(),
            ideal,
            true,
            target.getDeltaMovement().subtract(player.getDeltaMovement()).lengthSqr() > 1.0E-6D,
            A0001A0020RuntimeState.ranks(player),
            A0001A0020RuntimeState.state(),
            mastery(player, WeaponFamily.SPEAR),
            now(player)
        );
    }

    private static int mastery(ServerPlayer player, WeaponFamily family) {
        String key = switch (family) {
            case SWORD -> "epicfight:sword";
            case AXE -> "epicfight:axe";
            case SPEAR -> "epicfight:spear";
            case DAGGER -> "epicfight:dagger";
            default -> "";
        };
        return key.isEmpty() ? 0 : PlayerProgressionRuntime.get(player).mastery().experience(key);
    }

    private static Optional<WeaponFamily> family(CapabilityItem capability) {
        if (capability == null || capability.isEmpty()) return Optional.empty();
        String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
        int slash = category.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
        return switch (category) {
            case "sword" -> Optional.of(WeaponFamily.SWORD);
            case "axe" -> Optional.of(WeaponFamily.AXE);
            case "spear" -> Optional.of(WeaponFamily.SPEAR);
            case "dagger" -> Optional.of(WeaponFamily.DAGGER);
            default -> Optional.empty();
        };
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide() && !player.isSpectator() && !(player instanceof FakePlayer);
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }

    private static RootResolution rootAction(ServerPlayer player, EpicFightDamageSource source, String targetId, long now) {
        String actorId = A0001A0020RuntimeState.actorId(player);
        RecentCriticalRoot recent = claimRecentCriticalRoot(actorId, targetId, now);
        if (recent != null) return new RootResolution(recent.rootActionId, recent.critical);
        return new RootResolution("epicfight/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet(), false);
    }

    private static synchronized void remember(EpicFightDamageSource source, String targetId, PendingHit pending) {
        PENDING.computeIfAbsent(source, ignored -> new HashMap<>()).put(targetId, pending);
    }

    private static synchronized PendingHit forget(EpicFightDamageSource source, String targetId) {
        Map<String, PendingHit> map = PENDING.get(source);
        if (map == null) return null;
        PendingHit pending = map.remove(targetId);
        if (map.isEmpty()) PENDING.remove(source);
        return pending;
    }

    private static synchronized void rememberRecentCriticalRoot(
        String actorId, String targetId, String rootActionId, boolean critical, long now) {
        pruneRecentCriticalRoots(now);
        RECENT_CRITICAL_ROOTS.put(
            actorId + '\u0000' + targetId,
            new RecentCriticalRoot(rootActionId, critical, Math.addExact(now, CRITICAL_CORRELATION_MILLIS))
        );
    }

    private static synchronized RecentCriticalRoot claimRecentCriticalRoot(String actorId, String targetId, long now) {
        pruneRecentCriticalRoots(now);
        return RECENT_CRITICAL_ROOTS.remove(actorId + '\u0000' + targetId);
    }

    private static void pruneRecentCriticalRoots(long now) {
        RECENT_CRITICAL_ROOTS.entrySet().removeIf(entry -> entry.getValue().expiresAt <= now);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) A0001A0020RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) A0001A0020RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) A0001A0020RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) A0001A0020RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        A0001A0020RuntimeState.clearAll();
        synchronized (A0001A0020EpicFightHooks.class) {
            PENDING.clear();
            RECENT_CRITICAL_ROOTS.clear();
        }
    }

    private record RootResolution(String rootActionId, boolean providerCritical) {}
    private record PendingHit(String rootActionId, WeaponFamily family, boolean critical, A0001A0020CombatPolicy.BeforeHitResult result) {}
    private record RecentCriticalRoot(String rootActionId, boolean critical, long expiresAt) {}
}
