package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/** Pure deterministic sampler for bounded rotating observer work. */
final class GeothermalObserverSampler {
    private GeothermalObserverSampler() {
    }

    static List<Integer> sampleIndices(int totalObservers, long gameTick, int maxObservers) {
        if (totalObservers < 0) {
            throw new IllegalArgumentException("totalObservers must not be negative");
        }
        if (totalObservers == 0 || maxObservers <= 0) {
            return List.of();
        }

        int sampleCount = Math.min(totalObservers, maxObservers);
        long batch = Math.floorMod(gameTick, (long) totalObservers);
        int start = Math.toIntExact((batch * sampleCount) % totalObservers);
        List<Integer> indices = new ArrayList<>(sampleCount);
        for (int offset = 0; offset < sampleCount; offset++) {
            indices.add((start + offset) % totalObservers);
        }
        return List.copyOf(indices);
    }

    static long sweepTicks(int totalObservers, int maxObservers) {
        if (totalObservers < 0) {
            throw new IllegalArgumentException("totalObservers must not be negative");
        }
        if (maxObservers <= 0) {
            throw new IllegalArgumentException("maxObservers must be positive");
        }
        if (totalObservers == 0) {
            return 1L;
        }
        return Math.max(1L, Math.ceilDiv((long) totalObservers, (long) maxObservers));
    }

    static <T, R> List<R> sample(
            List<T> observers,
            long gameTick,
            int maxObservers,
            Function<? super T, ? extends R> mapper
    ) {
        Objects.requireNonNull(observers, "observers");
        Objects.requireNonNull(mapper, "mapper");
        List<Integer> indices = sampleIndices(observers.size(), gameTick, maxObservers);
        if (indices.isEmpty()) {
            return List.of();
        }
        List<R> sampled = new ArrayList<>(indices.size());
        for (int index : indices) {
            sampled.add(mapper.apply(observers.get(index)));
        }
        return List.copyOf(sampled);
    }
}
