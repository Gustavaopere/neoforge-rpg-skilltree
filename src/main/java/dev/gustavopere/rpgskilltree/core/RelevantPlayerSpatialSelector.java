package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Pure spatial candidate selector used by the NeoForge world-scaling boundary.
 *
 * <p>Runtime code may cache {@link Snapshot snapshots}; exact encounter distance is
 * recomputed here so cached section scans do not leak a distant player into a nearby
 * encounter merely because both entities share one cache cell.</p>
 */
public final class RelevantPlayerSpatialSelector {
    private static final Comparator<RelevantPlayerCandidate> CANONICAL_ORDER = Comparator
        .comparing((RelevantPlayerCandidate candidate) -> !candidate.engaged())
        .thenComparingLong(RelevantPlayerCandidate::distanceSquared)
        .thenComparing(RelevantPlayerCandidate::playerId);

    private RelevantPlayerSpatialSelector() {}

    public static List<RelevantPlayerCandidate> select(
        List<Snapshot> snapshots,
        double encounterX,
        double encounterY,
        double encounterZ,
        double radius,
        int maxCandidates
    ) {
        Objects.requireNonNull(snapshots, "snapshots");
        requireFinite(encounterX, "encounterX");
        requireFinite(encounterY, "encounterY");
        requireFinite(encounterZ, "encounterZ");
        requireFinite(radius, "radius");
        if (radius <= 0.0D) throw new IllegalArgumentException("radius must be positive");
        if (maxCandidates <= 0) throw new IllegalArgumentException("maxCandidates must be positive");

        double radiusSquared = radius * radius;
        HashSet<String> seen = new HashSet<>();
        ArrayList<RelevantPlayerCandidate> selected = new ArrayList<>();
        for (Snapshot snapshot : snapshots) {
            Snapshot checked = Objects.requireNonNull(snapshot, "snapshot");
            if (!seen.add(checked.playerId())) {
                throw new IllegalArgumentException("duplicate spatial snapshot id: " + checked.playerId());
            }
            double distanceSquared = distanceSquared(
                checked.x(), checked.y(), checked.z(), encounterX, encounterY, encounterZ
            );
            if (distanceSquared <= radiusSquared) {
                selected.add(new RelevantPlayerCandidate(
                    checked.playerId(),
                    checked.level(),
                    toLongDistanceSquared(distanceSquared),
                    true,
                    false
                ));
            }
        }
        selected.sort(CANONICAL_ORDER);
        if (selected.size() > maxCandidates) {
            selected.subList(maxCandidates, selected.size()).clear();
        }
        return List.copyOf(selected);
    }

    /**
     * Merges spatial evidence with an optional party adapter result.
     *
     * <p>Spatial candidates are ordered before party-only candidates, so a remote party
     * member cannot evict a local participant when the bounded candidate budget is full.
     * Duplicate IDs must agree on level; conflicting provider data fails closed.</p>
     */
    public static List<RelevantPlayerCandidate> mergeParty(
        List<RelevantPlayerCandidate> spatialCandidates,
        List<RelevantPlayerCandidate> partyCandidates,
        int maxCandidates
    ) {
        Objects.requireNonNull(spatialCandidates, "spatialCandidates");
        Objects.requireNonNull(partyCandidates, "partyCandidates");
        if (maxCandidates <= 0) throw new IllegalArgumentException("maxCandidates must be positive");

        Map<String, RelevantPlayerCandidate> merged = new HashMap<>();
        for (RelevantPlayerCandidate candidate : spatialCandidates) {
            mergeOne(merged, Objects.requireNonNull(candidate, "spatial candidate"), false);
        }
        for (RelevantPlayerCandidate candidate : partyCandidates) {
            mergeOne(merged, Objects.requireNonNull(candidate, "party candidate"), true);
        }

        ArrayList<RelevantPlayerCandidate> ordered = new ArrayList<>(merged.values());
        ordered.sort(CANONICAL_ORDER);
        if (ordered.size() > maxCandidates) {
            ordered.subList(maxCandidates, ordered.size()).clear();
        }
        return List.copyOf(ordered);
    }

    private static void mergeOne(
        Map<String, RelevantPlayerCandidate> merged,
        RelevantPlayerCandidate incoming,
        boolean requirePartyFlag
    ) {
        if (requirePartyFlag && !incoming.partyMember()) {
            throw new IllegalArgumentException("party adapter candidate must set partyMember=true");
        }
        RelevantPlayerCandidate existing = merged.get(incoming.playerId());
        if (existing == null) {
            merged.put(incoming.playerId(), incoming);
            return;
        }
        if (existing.level() != incoming.level()) {
            throw new IllegalArgumentException("conflicting relevant-player level for " + incoming.playerId());
        }
        merged.put(incoming.playerId(), new RelevantPlayerCandidate(
            incoming.playerId(),
            incoming.level(),
            Math.min(existing.distanceSquared(), incoming.distanceSquared()),
            existing.engaged() || incoming.engaged(),
            existing.partyMember() || incoming.partyMember()
        ));
    }

    private static double distanceSquared(
        double x,
        double y,
        double z,
        double otherX,
        double otherY,
        double otherZ
    ) {
        double dx = x - otherX;
        double dy = y - otherY;
        double dz = z - otherZ;
        return dx * dx + dy * dy + dz * dz;
    }

    private static long toLongDistanceSquared(double distanceSquared) {
        if (!Double.isFinite(distanceSquared) || distanceSquared >= Long.MAX_VALUE) {
            return Long.MAX_VALUE;
        }
        return (long) Math.floor(distanceSquared);
    }

    private static void requireFinite(double value, String name) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException(name + " must be finite");
    }

    /** Immutable cached player position/level sample. */
    public record Snapshot(String playerId, long level, double x, double y, double z) {
        public Snapshot {
            Objects.requireNonNull(playerId, "playerId");
            if (playerId.isBlank() || !playerId.equals(playerId.trim())) {
                throw new IllegalArgumentException("playerId must be non-blank and trimmed");
            }
            if (level < 0L) throw new IllegalArgumentException("level must be non-negative");
            requireFinite(x, "x");
            requireFinite(y, "y");
            requireFinite(z, "z");
        }
    }
}
