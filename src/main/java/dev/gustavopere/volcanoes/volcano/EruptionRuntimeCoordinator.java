package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.Optional;

/**
 * Stateless bridge between persisted volcano lifecycle state and the detailed eruption state machine.
 *
 * <p>An existing persisted eruption always resumes. A completed detailed event remains persisted while
 * the coarse volcano is still {@link VolcanoState#ERUPTING}, preventing an immediate restart; it is
 * retired only after the coarse lifecycle leaves that state.</p>
 */
public final class EruptionRuntimeCoordinator {
    private final EruptionController controller = new EruptionController();

    public Optional<EruptionSignal> update(
            VolcanoSavedData data,
            VolcanoSite site,
            MagmaChamber chamber,
            long gameTick,
            long elapsedTicks
    ) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(site, "site");
        Objects.requireNonNull(chamber, "chamber");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (elapsedTicks < 0L) {
            throw new IllegalArgumentException("elapsedTicks must be non-negative");
        }

        if (site.state() != VolcanoState.ERUPTING) {
            data.clearEruption(site.persistenceId());
            return Optional.empty();
        }

        Optional<EruptionEvent> existing = data.eruption(site.persistenceId());
        if (existing.isEmpty()) {
            EruptionEvent started = controller.begin(site.persistenceId(), chamber, gameTick);
            data.updateEruption(started);
            return Optional.of(EruptionSignal.from(site, chamber, started));
        }

        EruptionEvent current = existing.orElseThrow();
        if (current.isComplete() || elapsedTicks == 0L) {
            return Optional.empty();
        }

        EruptionEvent advanced = controller.advance(current, elapsedTicks);
        if (advanced.equals(current)) {
            return Optional.empty();
        }
        data.updateEruption(advanced);
        return Optional.of(EruptionSignal.from(site, chamber, advanced));
    }
}
