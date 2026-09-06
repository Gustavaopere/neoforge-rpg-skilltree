package dev.gustavopere.volcanoes.tectonics;

/** Pure client signal envelope; intentionally contains no Minecraft client classes. */
public final class SeismicClientShakeState {
    private float amplitude;
    private long startNanos;
    private long endNanos;

    public synchronized void accept(SeismicShakePayload payload, long nowNanos) {
        long durationNanos = Math.multiplyExact((long) payload.durationTicks(), 50_000_000L);
        this.amplitude = payload.amplitude();
        this.startNanos = nowNanos;
        this.endNanos = Math.addExact(nowNanos, durationNanos);
    }

    public synchronized double amplitudeAt(long nowNanos) {
        if (amplitude <= 0.0f || nowNanos >= endNanos) {
            return 0.0;
        }
        if (nowNanos <= startNanos) {
            return amplitude;
        }
        double fractionRemaining = (double) (endNanos - nowNanos) / (double) (endNanos - startNanos);
        return Math.max(0.0, Math.min(1.0, amplitude * fractionRemaining));
    }
}
