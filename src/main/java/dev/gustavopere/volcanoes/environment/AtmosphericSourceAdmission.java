package dev.gustavopere.volcanoes.environment;

/**
 * Result of attempting to project one stable source into the bounded active atmosphere index.
 *
 * <p>Capacity rejection is deliberately explicit so fail-isolated upstream lifecycle observers
 * can retain/retry their authoritative source instead of silently losing the atmospheric
 * projection.</p>
 */
public enum AtmosphericSourceAdmission {
    ACCEPTED,
    REJECTED_CAPACITY
}
