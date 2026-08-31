package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.UUID;

/**
 * One deterministic lifecycle step joining coarse magma evolution with the detailed eruption runtime.
 *
 * <p>The coarse {@link VolcanoManager} remains authoritative for volcano state and chamber physics.
 * Detailed eruption state is advanced only after those values have been persisted, so every emitted
 * signal reflects the same state that will survive a save/reload boundary.</p>
 */
public final class VolcanoLifecycleStep {
    private final VolcanoManager manager;
    private final EruptionEffectRuntime effects;

    public VolcanoLifecycleStep(VolcanoManager manager, EruptionEffectRuntime effects) {
        this.manager = Objects.requireNonNull(manager, "manager");
        this.effects = Objects.requireNonNull(effects, "effects");
    }

    public StepResult advance(
            long worldSeed,
            VolcanoSavedData data,
            UUID persistenceId,
            long gameTick,
            long elapsedTicks,
            double seismicIntensity
    ) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(persistenceId, "persistenceId");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }

        VolcanoState state = manager.advance(worldSeed, persistenceId, elapsedTicks, seismicIntensity);
        VolcanoSite site = data.get(persistenceId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown volcano site " + persistenceId));
        MagmaChamber chamber = data.chamber(persistenceId)
                .orElseThrow(() -> new IllegalStateException("Missing magma chamber for " + persistenceId));
        EruptionEffectRuntime.EmissionResult emission = effects.update(
                data, site, chamber, gameTick, elapsedTicks);
        return new StepResult(state, emission);
    }

    public record StepResult(
            VolcanoState state,
            EruptionEffectRuntime.EmissionResult emission
    ) {
        public StepResult {
            state = Objects.requireNonNull(state, "state");
            emission = Objects.requireNonNull(emission, "emission");
        }
    }
}
