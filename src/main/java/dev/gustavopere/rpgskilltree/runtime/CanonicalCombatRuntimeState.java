package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalActionCorrelationService;
import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.CanonicalCriticalRequest;
import dev.gustavopere.rpgskilltree.core.CanonicalCriticalService;
import dev.gustavopere.rpgskilltree.core.CanonicalFocusService;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BowItem;

/** Server-only owner for canonical action identity, critical decisions, and ranged correlation. */
public final class CanonicalCombatRuntimeState {
    /** Vanilla BowItem spawns its projectile synchronously; two ticks cover event-order jitter without a broad reuse window. */
    private static final long CORRELATION_RETENTION_MILLIS = 100L;
    private static final long PROJECTILE_RETENTION_MILLIS = 30_000L;
    private static final long CRITICAL_RETENTION_MILLIS = 30_000L;
    private static final int MAX_TRACKED_ACTIONS = 8_192;

    private static final CanonicalActionCorrelationService CORRELATION =
        new CanonicalActionCorrelationService(
            CORRELATION_RETENTION_MILLIS,
            PROJECTILE_RETENTION_MILLIS,
            MAX_TRACKED_ACTIONS
        );
    private static final CanonicalCriticalService CRITICAL = new CanonicalCriticalService(
        () -> ThreadLocalRandom.current().nextDouble(),
        CRITICAL_RETENTION_MILLIS,
        MAX_TRACKED_ACTIONS
    );
    private static final Map<String, AimSession> AIMS = new HashMap<>();
    private static final Map<String, PendingCancelledDraw> PENDING_CANCELLED_DRAWS = new HashMap<>();
    private static final Map<ActionKey, ShotFacts> SHOTS = new HashMap<>();
    private static final Map<String, ProjectileShotFacts> PROJECTILE_SHOTS = new HashMap<>();

    private CanonicalCombatRuntimeState() {}

    public static synchronized CanonicalActionIdentity newRoot(
        ServerPlayer player,
        String sourceId,
        long nowMillis
    ) {
        Objects.requireNonNull(player);
        return CORRELATION.newRoot(actorId(player), sourceId, nowMillis);
    }

    public static synchronized CanonicalActionIdentity beginMelee(
        ServerPlayer player,
        String targetId,
        long nowMillis
    ) {
        CanonicalActionIdentity action = newRoot(player, "neoforge:critical_hit", nowMillis);
        CORRELATION.recordMeleeDecision(action, targetId, nowMillis);
        return action;
    }

    public static synchronized Optional<CanonicalActionIdentity> claimMeleeForProvider(
        ServerPlayer player,
        String targetId,
        long nowMillis
    ) {
        return CORRELATION.claimMeleeForProvider(actorId(player), targetId, nowMillis);
    }

    public static synchronized void beginAim(ServerPlayer player, long nowMillis) {
        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        boolean focusGeneration = ranks.learned("A0046");
        boolean preparedShot = ranks.learned("A0048");
        if (!focusGeneration && !preparedShot) return;

        CanonicalActionIdentity aimAction = newRoot(player, "neoforge:arrow_nock", nowMillis);
        boolean preparationStarted = false;
        if (preparedShot) {
            CanonicalFocusService.PreparationStatus status = CombatPerkRuntimeState.state().focusService()
                .beginPreparation(aimAction, true, true, nowMillis);
            preparationStarted = status == CanonicalFocusService.PreparationStatus.STARTED;
        }
        if (focusGeneration || preparationStarted) {
            AIMS.put(actorId(player), new AimSession(aimAction, preparationStarted));
        }
    }

    public static synchronized CanonicalFocusService.AimStatus sampleAim(ServerPlayer player, long nowMillis) {
        String actorId = actorId(player);
        AimSession aim = AIMS.get(actorId);
        if (aim == null) return CanonicalFocusService.AimStatus.INELIGIBLE;
        CombatPerkRanks ranks = CombatPerkRuntimeState.ranks(player);
        int rank = ranks.rank("A0046");
        boolean bowInUse = player.isUsingItem() && player.getUseItem().getItem() instanceof BowItem;
        CanonicalFocusService.AimStatus result = CombatPerkRuntimeState.state().focusService().sampleAim(
            new CanonicalFocusService.AimSampleRequest(
                aim.action,
                true,
                true,
                bowInUse,
                player.isSprinting(),
                rank,
                player.getYRot(),
                player.getXRot(),
                1.0D,
                1.0D
            ),
            CombatPerkRuntimeState.state(),
            nowMillis
        );

        if (player.isSprinting() && aim.preparationStarted) {
            CombatPerkRuntimeState.state().focusService().armPreparation(
                aim.action,
                false,
                CombatPerkRuntimeState.state(),
                nowMillis
            );
            AIMS.put(actorId, new AimSession(aim.action, false));
        }
        return result;
    }

    /** Records a possible cancelled draw; a normal ArrowLoose in the same release sequence clears it. */
    public static synchronized void recordUseStop(ServerPlayer player, double drawFraction, long nowMillis) {
        if (!Double.isFinite(drawFraction) || drawFraction < 0.0D) return;
        String actorId = actorId(player);
        if (!AIMS.containsKey(actorId) || CombatPerkRuntimeState.ranks(player).rank("A0046") <= 0) return;
        PENDING_CANCELLED_DRAWS.put(
            actorId,
            new PendingCancelledDraw(drawFraction, Math.addExact(nowMillis, 50L))
        );
    }

    public static synchronized boolean hasPendingCancelledDraw(ServerPlayer player) {
        return PENDING_CANCELLED_DRAWS.containsKey(actorId(player));
    }

    /** Resolves a Stop that was not followed by ArrowLoose, proving an actual cancelled shot. */
    public static synchronized boolean resolvePendingCancelledDraw(ServerPlayer player, long nowMillis) {
        String actorId = actorId(player);
        PendingCancelledDraw pending = PENDING_CANCELLED_DRAWS.get(actorId);
        if (pending == null || pending.resolveAtMillis > nowMillis) return false;
        PENDING_CANCELLED_DRAWS.remove(actorId);
        AimSession aim = AIMS.remove(actorId);
        CombatPerkRuntimeState.state().focusService().endAimTracking(actorId);
        if (aim != null && aim.preparationStarted) {
            CombatPerkRuntimeState.state().focusService().armPreparation(
                aim.action,
                false,
                CombatPerkRuntimeState.state(),
                nowMillis
            );
        }
        return CombatPerkRuntimeState.state().focusService().applyCancelledDrawLoss(
            actorId,
            true,
            true,
            pending.drawFraction,
            CombatPerkRuntimeState.state(),
            nowMillis
        );
    }

    public static synchronized CanonicalActionIdentity recordLoose(
        ServerPlayer player,
        boolean fullyDrawn,
        long cooldownMillis,
        long nowMillis
    ) {
        String actorId = actorId(player);
        PENDING_CANCELLED_DRAWS.remove(actorId);
        AimSession aim = AIMS.remove(actorId);
        CombatPerkRuntimeState.state().focusService().endAimTracking(actorId);
        if (aim != null && aim.preparationStarted) {
            CombatPerkRuntimeState.state().focusService().armPreparation(
                aim.action,
                fullyDrawn && !player.isSprinting(),
                CombatPerkRuntimeState.state(),
                nowMillis
            );
        }

        CanonicalActionIdentity shot = newRoot(player, "neoforge:arrow_loose", nowMillis);
        CORRELATION.recordShot(shot, nowMillis);
        pruneShots(nowMillis);
        SHOTS.put(
            ActionKey.of(shot),
            new ShotFacts(
                fullyDrawn,
                cooldownMillis,
                player.getX(),
                player.getY(),
                player.getZ(),
                Math.addExact(nowMillis, CORRELATION_RETENTION_MILLIS)
            )
        );
        return shot;
    }

    public static synchronized Optional<ShotCorrelation> correlateProjectile(
        ServerPlayer owner,
        String projectileId,
        long nowMillis
    ) {
        pruneShots(nowMillis);
        Optional<CanonicalActionIdentity> action = CORRELATION.correlateProjectile(
            actorId(owner), projectileId, nowMillis);
        if (action.isEmpty()) return Optional.empty();
        ShotFacts facts = SHOTS.get(ActionKey.of(action.get()));
        if (facts != null) {
            PROJECTILE_SHOTS.put(
                projectileId,
                new ProjectileShotFacts(
                    action.get(),
                    facts.shotX,
                    facts.shotY,
                    facts.shotZ,
                    Math.addExact(nowMillis, PROJECTILE_RETENTION_MILLIS)
                )
            );
        }
        return Optional.of(new ShotCorrelation(action.get(), facts));
    }

    public static synchronized Optional<ProjectileShotFacts> projectileShotFacts(
        ServerPlayer owner,
        String projectileId,
        long nowMillis
    ) {
        pruneShots(nowMillis);
        ProjectileShotFacts facts = PROJECTILE_SHOTS.get(projectileId);
        if (facts == null || !facts.action.actorId().equals(actorId(owner))) return Optional.empty();
        return Optional.of(facts);
    }

    public static synchronized CanonicalActionIdentity projectileAction(
        ServerPlayer owner,
        String projectileId,
        long nowMillis
    ) {
        return CORRELATION.projectileAction(projectileId, nowMillis)
            .orElseGet(() -> CanonicalActionIdentity.root(
                actorId(owner),
                "projectile/" + projectileId,
                "neoforge:projectile"
            ));
    }

    public static boolean resolveCritical(
        CanonicalActionIdentity action,
        WeaponFamily family,
        CombatPerkRanks ranks,
        boolean providerCritical,
        long nowMillis
    ) {
        double bonusChance = NotionCombatPerkRules.criticalChanceBonus(family, ranks);
        return CRITICAL.resolve(
            new CanonicalCriticalRequest(action, true, true, true, providerCritical, bonusChance),
            nowMillis
        );
    }

    /** Records a provider boolean without creating a perk roll outside the canonical NeoForge hook. */
    public static boolean resolveProviderCritical(
        CanonicalActionIdentity action,
        boolean providerCritical,
        long nowMillis
    ) {
        return CRITICAL.resolve(
            new CanonicalCriticalRequest(action, true, true, true, providerCritical, 0.0D),
            nowMillis
        );
    }

    public static Optional<Boolean> criticalDecision(CanonicalActionIdentity action, long nowMillis) {
        return CRITICAL.decision(action, nowMillis);
    }

    public static synchronized void invalidateAim(ServerPlayer player, long nowMillis) {
        String actorId = actorId(player);
        PENDING_CANCELLED_DRAWS.remove(actorId);
        AimSession aim = AIMS.remove(actorId);
        CombatPerkRuntimeState.state().focusService().endAimTracking(actorId);
        if (aim != null && aim.preparationStarted) {
            CombatPerkRuntimeState.state().focusService().armPreparation(
                aim.action,
                false,
                CombatPerkRuntimeState.state(),
                nowMillis
            );
        }
    }

    public static synchronized boolean hasAim(ServerPlayer player) {
        return AIMS.containsKey(actorId(player));
    }

    public static synchronized void clear(ServerPlayer player) {
        String actorId = actorId(player);
        AIMS.remove(actorId);
        PENDING_CANCELLED_DRAWS.remove(actorId);
        SHOTS.keySet().removeIf(key -> key.actorId.equals(actorId));
        PROJECTILE_SHOTS.entrySet().removeIf(entry -> entry.getValue().action.actorId().equals(actorId));
        CORRELATION.clearActor(actorId);
        CRITICAL.clearActor(actorId);
    }

    private static void pruneShots(long nowMillis) {
        SHOTS.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
        PROJECTILE_SHOTS.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private static String actorId(ServerPlayer player) {
        return player.getUUID().toString();
    }

    public record ShotCorrelation(CanonicalActionIdentity action, ShotFacts facts) {}

    public record ShotFacts(
        boolean fullyDrawn,
        long cooldownMillis,
        double shotX,
        double shotY,
        double shotZ,
        long expiresAtMillis
    ) {}

    public record ProjectileShotFacts(
        CanonicalActionIdentity action,
        double shotX,
        double shotY,
        double shotZ,
        long expiresAtMillis
    ) {}

    private record AimSession(CanonicalActionIdentity action, boolean preparationStarted) {}

    private record PendingCancelledDraw(double drawFraction, long resolveAtMillis) {}

    private record ActionKey(String actorId, String actionId) {
        static ActionKey of(CanonicalActionIdentity action) {
            return new ActionKey(action.actorId(), action.actionId());
        }
    }
}
