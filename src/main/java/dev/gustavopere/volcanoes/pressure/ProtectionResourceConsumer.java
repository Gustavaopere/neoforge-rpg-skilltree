package dev.gustavopere.volcanoes.pressure;

/** Commits one unit/update of a consumable protection source. */
@FunctionalInterface
public interface ProtectionResourceConsumer {
    boolean consume();
}
