package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** A0075 semantic rotation; the benefit exists only while its body-cost lease is valid. */
public final class MartialRhythmService {
    private static final long ROTATION_TICKS = 160L;
    private static final CanonicalBodyTradeoffService.LeaseRequest LEASE =
        new CanonicalBodyTradeoffService.LeaseRequest("A0075", 0.15D, 0.10D, 120L, 240L);
    private final CanonicalBodyTradeoffService body;
    private final Map<String, ArrayDeque<ActionSample>> samples = new HashMap<>();

    public MartialRhythmService(CanonicalBodyTradeoffService body) { this.body = Objects.requireNonNull(body); }

    public synchronized boolean observe(String actorId, String semanticAction, boolean server, boolean eligibleActor, long nowTick, int rank) {
        Objects.requireNonNull(actorId);
        Objects.requireNonNull(semanticAction);
        if (actorId.isBlank() || semanticAction.isBlank()) throw new IllegalArgumentException("ids must not be blank");
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick must be non-negative");
        if (!server || !eligibleActor || rank <= 0) return false;
        ArrayDeque<ActionSample> actorSamples = samples.computeIfAbsent(actorId, ignored -> new ArrayDeque<>());
        while (!actorSamples.isEmpty() && nowTick - actorSamples.peekFirst().tick > ROTATION_TICKS) actorSamples.removeFirst();
        for (ActionSample sample : actorSamples) if (sample.semanticAction.equals(semanticAction)) return false;
        actorSamples.addLast(new ActionSample(semanticAction, nowTick));
        Set<String> distinct = new HashSet<>();
        for (ActionSample sample : actorSamples) distinct.add(sample.semanticAction);
        if (distinct.size() < 3) return false;
        boolean active = body.activate(actorId, LEASE, nowTick).active();
        actorSamples.clear();
        return active;
    }

    public double staminaCostMultiplier(String actorId, long nowTick) {
        return body.benefitActive(actorId, "A0075", nowTick) ? 0.90D : 1.0D;
    }

    public synchronized void clearTransient(String actorId, long nowTick) {
        samples.remove(actorId);
        body.clearTransient(actorId, nowTick);
    }

    private record ActionSample(String semanticAction, long tick) {}
}
