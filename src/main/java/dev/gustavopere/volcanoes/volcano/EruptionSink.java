package dev.gustavopere.volcanoes.volcano;

/** Consumer boundary for optional eruption effects and integration adapters. */
@FunctionalInterface
public interface EruptionSink {
    void onEruption(EruptionSignal signal, EruptionScheduler.WorkGrant workGrant);
}
