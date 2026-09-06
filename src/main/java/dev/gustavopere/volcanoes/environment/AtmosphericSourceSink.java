package dev.gustavopere.volcanoes.environment;

import java.util.UUID;

/**
 * Neutral core port for stable atmospheric source lifecycles.
 * External producers may upsert one stable source identity and later remove that same identity
 * without depending on AtmosphereField internals.
 */
public interface AtmosphericSourceSink {
    /**
     * Attempts to admit or replace one source in the bounded active atmosphere field.
     * Implementations that cannot expose admission pressure may retain the legacy fail-closed
     * behavior through the default implementation.
     */
    default AtmosphericSourceAdmission tryUpsert(AtmosphericSource source) {
        upsert(source);
        return AtmosphericSourceAdmission.ACCEPTED;
    }

    /**
     * Legacy fail-closed upsert surface. Implementations should throw when a new source cannot be
     * admitted; cross-stage adapters that need retry/backpressure should prefer {@link #tryUpsert}.
     */
    void upsert(AtmosphericSource source);

    boolean remove(UUID id);
}
