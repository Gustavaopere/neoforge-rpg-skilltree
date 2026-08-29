package dev.gustavopere.rpgskilltree.core;

/**
 * Provider-neutral locality and cache policy for relevant-player candidate discovery.
 *
 * <p>Distances are expressed in blocks. The output cap and worst-case cell count are
 * technical safety boundaries; actual balance values remain an explicit installed policy.</p>
 */
public record RelevantPlayerSearchPolicy(
    int cellSizeBlocks,
    int candidateRadiusBlocks,
    int engagementRadiusBlocks,
    int maxCandidates,
    long cacheTtlTicks
) {
    public static final int MAX_OUTPUT_CANDIDATES = 256;
    public static final long MAX_QUERY_CELLS = 4096L;

    public RelevantPlayerSearchPolicy {
        if (cellSizeBlocks <= 0) {
            throw new IllegalArgumentException("cellSizeBlocks must be positive");
        }
        if (candidateRadiusBlocks <= 0) {
            throw new IllegalArgumentException("candidateRadiusBlocks must be positive");
        }
        if (engagementRadiusBlocks < 0 || engagementRadiusBlocks > candidateRadiusBlocks) {
            throw new IllegalArgumentException(
                "engagementRadiusBlocks must be between 0 and candidateRadiusBlocks"
            );
        }
        if (maxCandidates <= 0 || maxCandidates > MAX_OUTPUT_CANDIDATES) {
            throw new IllegalArgumentException(
                "maxCandidates must be between 1 and " + MAX_OUTPUT_CANDIDATES
            );
        }
        if (cacheTtlTicks <= 0L) {
            throw new IllegalArgumentException("cacheTtlTicks must be positive");
        }

        long cellRadius = ceilDiv(candidateRadiusBlocks, cellSizeBlocks);
        long width = Math.addExact(Math.multiplyExact(cellRadius, 2L), 1L);
        long worstCaseCells = Math.multiplyExact(width, width);
        if (worstCaseCells > MAX_QUERY_CELLS) {
            throw new IllegalArgumentException(
                "search policy visits too many cells in the worst case: " + worstCaseCells
            );
        }
    }

    public long candidateRadiusSquared() {
        return square(candidateRadiusBlocks);
    }

    public long engagementRadiusSquared() {
        return square(engagementRadiusBlocks);
    }

    public long worstCaseVisitedCells() {
        long cellRadius = ceilDiv(candidateRadiusBlocks, cellSizeBlocks);
        long width = Math.addExact(Math.multiplyExact(cellRadius, 2L), 1L);
        return Math.multiplyExact(width, width);
    }

    private static long square(int value) {
        return Math.multiplyExact((long) value, (long) value);
    }

    private static long ceilDiv(long numerator, long denominator) {
        return Math.floorDiv(Math.addExact(numerator, denominator - 1L), denominator);
    }
}
