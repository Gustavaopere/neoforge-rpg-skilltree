package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** A0087 hostile-loss trigger coupled to the canonical body tradeoff lease. */
public final class BloodThirstService {
    private static final CanonicalBodyTradeoffService.LeaseRequest LEASE =
        new CanonicalBodyTradeoffService.LeaseRequest("A0087", 0.20D, 0.15D, 0.15D, 120L, 900L);
    private final CanonicalBodyTradeoffService body;
    private final Map<String, ArrayDeque<DamageSample>> damage = new HashMap<>();

    public BloodThirstService(CanonicalBodyTradeoffService body) { this.body = Objects.requireNonNull(body); }

    public synchronized boolean recordHostileDamage(String actorId, double actualDamage, double maxHealth, boolean eligible, long nowTick) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank() || nowTick < 0L || !Double.isFinite(actualDamage) || actualDamage < 0.0D
            || !Double.isFinite(maxHealth) || maxHealth <= 0.0D) throw new IllegalArgumentException("invalid blood thirst sample");
        if (!eligible || actualDamage <= 0.0D) return false;
        ArrayDeque<DamageSample> samples = damage.computeIfAbsent(actorId, ignored -> new ArrayDeque<>());
        while (!samples.isEmpty() && nowTick - samples.peekFirst().tick > 120L) samples.removeFirst();
        samples.addLast(new DamageSample(nowTick, actualDamage));
        double total = samples.stream().mapToDouble(DamageSample::amount).sum();
        if (total < maxHealth * 0.25D) return false;
        boolean active = body.activate(actorId, LEASE, nowTick).active();
        if (active) samples.clear();
        return active;
    }

    public boolean active(String actorId, long nowTick) { return body.benefitActive(actorId, "A0087", nowTick); }
    public double weaponMinimumCoefficient(String actorId, long nowTick) { return active(actorId, nowTick) ? 0.03D : 0.0D; }
    public double healingMultiplier(String actorId, long nowTick) { return active(actorId, nowTick) ? 1.08D : 1.0D; }
    public synchronized void clearTransient(String actorId, long nowTick) { damage.remove(actorId); body.clearTransient(actorId, nowTick); }
    private record DamageSample(long tick, double amount) {}
}
