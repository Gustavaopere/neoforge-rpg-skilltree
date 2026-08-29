package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.DoubleSupplier;

/** One canonical critical decision per A0001-A0020 root action. */
public final class A0001A0020CriticalService {
    private final DoubleSupplier randomUnit;
    private final long retentionMillis;
    private final int maxEntries;
    private final Map<Key, Decision> decisions = new HashMap<>();

    public A0001A0020CriticalService(DoubleSupplier randomUnit, long retentionMillis, int maxEntries) {
        this.randomUnit = Objects.requireNonNull(randomUnit);
        if (retentionMillis <= 0L || maxEntries <= 0) throw new IllegalArgumentException("invalid critical service bounds");
        this.retentionMillis = retentionMillis;
        this.maxEntries = maxEntries;
    }

    public synchronized boolean resolve(String actorId, String rootActionId, boolean providerCritical,
                                        double bonusChance, long nowMillis) {
        require(actorId, "actorId");
        require(rootActionId, "rootActionId");
        if (!Double.isFinite(bonusChance) || bonusChance < 0.0D || bonusChance > 1.0D || nowMillis < 0L) {
            throw new IllegalArgumentException("invalid critical request");
        }
        prune(nowMillis);
        Key key = new Key(actorId, rootActionId);
        Decision existing = decisions.get(key);
        if (existing != null) return existing.critical;

        boolean critical = providerCritical;
        if (!critical && bonusChance > 0.0D) {
            double roll = randomUnit.getAsDouble();
            if (!Double.isFinite(roll) || roll < 0.0D || roll >= 1.0D) {
                throw new IllegalStateException("critical RNG must return [0,1)");
            }
            critical = roll < bonusChance;
        }
        if (decisions.size() >= maxEntries) {
            Iterator<Key> iterator = decisions.keySet().iterator();
            if (iterator.hasNext()) decisions.remove(iterator.next());
        }
        decisions.put(key, new Decision(critical, Math.addExact(nowMillis, retentionMillis)));
        return critical;
    }

    public synchronized void clearActor(String actorId) {
        require(actorId, "actorId");
        decisions.keySet().removeIf(key -> key.actorId.equals(actorId));
    }

    public synchronized void clear() { decisions.clear(); }

    private void prune(long nowMillis) {
        decisions.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private static void require(String value, String name) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
    }

    private record Key(String actorId, String rootActionId) {}
    private record Decision(boolean critical, long expiresAtMillis) {}
}
