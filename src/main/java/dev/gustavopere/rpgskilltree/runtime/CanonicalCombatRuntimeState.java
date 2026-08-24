package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalActionCorrelationService;
import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.CanonicalCriticalRequest;
import dev.gustavopere.rpgskilltree.core.CanonicalCriticalService;
import dev.gustavopere.rpgskilltree.core.CanonicalFocusService;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.server.level.ServerPlayer;

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
    private static final Map<ActionKey, ShotFacts> SHOTS = new HashMap<>();

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
        if (!ranks.learned("A0048")) return;
        CanonicalActionIdentity preparation = newRoot(player, "neoforge:arrow_nock", nowMillis);
        CanonicalFocusService.PreparationStatus status = CombatPerkRuntimeState.state().focusService()
            .beginPreparation(preparation, true, true, nowMillis);
        if (status == CanonicalFocusService.PreparationStatus.STARTED) {
            AIMS.put(actorId(player), new AimSession(preparation));
        }
    }

    public static synchronized CanonicalActionIdentity recordLoose(
        ServerPlayer player,
        boolean fullyDrawn,
        long cooldownMillis,
        long nowMillis
    ) {
        String actorId = actorId(player);
        AimSession aim = AIMS.remove(actorId);
        if (aim != null) {
            CombatPerkRuntimeState.state().focusService().armPreparation(
                aim.preparation,
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
            new ShotFacts(fullyDrawn, cooldownMillis, Math.addExact(nowMillis, CORRELATION_RETENTION_MILLIS))
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
        return Optional.of(new ShotCorrelation(action.get(), facts));
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
        AimSession aim = AIMS.remove(actorId(player));
        if (aim != null) {
            CombatPerkRuntimeState.state().focusService().armPreparation(
                aim.preparation,
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
        SHOTS.keySet().removeIf(key -> key.actorId.equals(actorId));
        CORRELATION.clearActor(actorId);
        CRITICAL.clearActor(actorId);
    }

    private static void pruneShots(long nowMillis) {
        SHOTS.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private static String actorId(ServerPlayer player) {
        return player.getUUID().toString();
    }

    public record ShotCorrelation(CanonicalActionIdentity action, ShotFacts facts) {}

    public record ShotFacts(boolean fullyDrawn, long cooldownMillis, long expiresAtMillis) {}

    private record AimSession(CanonicalActionIdentity preparation) {}

    private record ActionKey(String actorId, String actionId) {
        static ActionKey of(CanonicalActionIdentity action) {
            return new ActionKey(action.actorId(), action.actionId());
        }
    }
}
