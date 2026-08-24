package dev.gustavopere.rpgskilltree.core;

/** Frozen shared detector contract for A0079 and A0099. */
public final class StationaryStateServiceTest {
    public static void main(String[] args) {
        requiresThirtyConsecutiveServerTicksAndAccumulatedPath();
        resetsImmediatelyWhenPathExceedsCanonicalTolerance();
        lifecycleAndForcedTransitionsInvalidate();
        System.out.println("StationaryStateServiceTest: PASS");
    }

    private static void requiresThirtyConsecutiveServerTicksAndAccumulatedPath() {
        var service = new StationaryStateService();
        StationaryStateService.State state = null;
        double x = 0.0D;
        for (long tick = 1L; tick <= 30L; tick++) {
            x += 0.003D;
            state = service.observe(sample(tick, "overworld", x, false, false, false));
            if (tick < 30L) require(!state.stationary(), "must not arm before 30 ticks");
        }
        require(state.stationary(), "30 consecutive ticks arm the shared state");
        require(close(state.accumulatedPath(), 0.087D), "sum real 3D tick deltas, not net displacement");
    }

    private static void resetsImmediatelyWhenPathExceedsCanonicalTolerance() {
        var service = new StationaryStateService();
        for (long tick = 1L; tick <= 29L; tick++) {
            service.observe(sample(tick, "overworld", (tick - 1L) * 0.0035D, false, false, false));
        }
        var reset = service.observe(sample(30L, "overworld", 0.103D, false, false, false));
        require(!reset.stationary() && reset.consecutiveTicks() == 1, "path > 0.10 resets immediately");
        require(close(reset.accumulatedPath(), 0.0D), "new stationary attempt starts at current position");
        var gap = service.observe(sample(32L, "overworld", 0.103D, false, false, false));
        require(gap.consecutiveTicks() == 1, "tick gap breaks consecutiveness");
    }

    private static void lifecycleAndForcedTransitionsInvalidate() {
        var service = new StationaryStateService();
        for (long tick = 1L; tick <= 30L; tick++) service.observe(sample(tick, "overworld", 0.0D, false, false, false));
        require(service.state("player").stationary(), "precondition");
        require(!service.observe(sample(31L, "overworld", 0.0D, false, true, false)).stationary(), "teleport invalidates");
        service.observe(sample(32L, "overworld", 0.0D, false, false, false));
        require(service.observe(sample(33L, "nether", 0.0D, false, false, false)).consecutiveTicks() == 0, "dimension invalidates");
        service.observe(sample(34L, "nether", 0.0D, false, false, false));
        require(service.observe(sample(35L, "nether", 0.0D, true, false, false)).consecutiveTicks() == 0, "mount transition invalidates");
        service.observe(sample(36L, "nether", 0.0D, true, false, false));
        require(service.observe(sample(37L, "nether", 0.0D, true, false, true)).consecutiveTicks() == 0, "forced displacement invalidates");
        require(!service.observe(new StationaryStateService.Sample("player", 38L, "nether", 0, 0, 0, true, false, false, false)).stationary(), "client sample is fail closed");
    }

    private static StationaryStateService.Sample sample(long tick, String dimension, double x, boolean mounted, boolean teleported, boolean forced) {
        return new StationaryStateService.Sample("player", tick, dimension, x, 64.0D, 0.0D, mounted, teleported, forced, true);
    }

    private static boolean close(double a, double b) { return Math.abs(a - b) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
