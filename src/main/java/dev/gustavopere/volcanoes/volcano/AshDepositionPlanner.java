package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;

/**
 * Deterministic, server-agnostic planner for bounded ash-deposition column work.
 *
 * <p>The returned positions are horizontal column candidates at the vent Y level. A NeoForge world
 * adapter resolves the actual surface height and must apply {@link AshDepositionPolicy} before any
 * mutation. This planner never loads chunks and never owns block placement.</p>
 */
public final class AshDepositionPlanner {
    private static final int ATTEMPTS_PER_TOKEN = 8;

    public List<BlockPos> candidates(
            AshPlumeEmission emission,
            EruptionScheduler.WorkGrant workGrant,
            long gameTick
    ) {
        Objects.requireNonNull(emission, "emission");
        Objects.requireNonNull(workGrant, "workGrant");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        int budget = workGrant.immediateBlocks();
        if (!emission.active() || budget <= 0 || emission.plumeRadiusBlocks() <= 0.0) {
            return List.of();
        }

        BlockPos source = emission.source();
        double radius = emission.plumeRadiusBlocks();
        double radiusSquared = radius * radius;
        SplittableRandom random = new SplittableRandom(seed(emission, gameTick));
        LinkedHashSet<BlockPos> unique = new LinkedHashSet<>(budget);
        int maxAttempts = Math.max(budget, budget * ATTEMPTS_PER_TOKEN);

        for (int attempt = 0; attempt < maxAttempts && unique.size() < budget; attempt++) {
            double angle = random.nextDouble(0.0, Math.PI * 2.0);
            double distance = Math.sqrt(random.nextDouble()) * radius;
            int dx = (int) Math.round(Math.cos(angle) * distance);
            int dz = (int) Math.round(Math.sin(angle) * distance);
            long distanceSquared = (long) dx * dx + (long) dz * dz;
            if (distanceSquared > radiusSquared) {
                continue;
            }
            unique.add(source.offset(dx, 0, dz).immutable());
        }

        return List.copyOf(new ArrayList<>(unique));
    }

    private static long seed(AshPlumeEmission emission, long gameTick) {
        long sourceBits = emission.sourceId().getMostSignificantBits()
                ^ Long.rotateLeft(emission.sourceId().getLeastSignificantBits(), 23);
        long tickBits = gameTick * 0x9E3779B97F4A7C15L;
        return mix64(sourceBits ^ tickBits);
    }

    private static long mix64(long value) {
        value = (value ^ (value >>> 30)) * 0xBF58476D1CE4E5B9L;
        value = (value ^ (value >>> 27)) * 0x94D049BB133111EBL;
        return value ^ (value >>> 31);
    }
}
