package dev.gustavopere.volcanoes.geology;

import java.util.UUID;

/** Transient observer for persistent geological-deposit registration and removal. */
public interface DepositLifecycleSink {
    void upsert(GeologicalDeposit deposit);

    void remove(UUID persistenceId);
}
