package dev.gustavopere.rpgskilltree.runtime.economy;

/** Raised when persisted colony-economy state cannot be trusted or decoded safely. */
public class EconomyPersistenceException extends RuntimeException {
    public EconomyPersistenceException(String message) {
        super(message);
    }

    public EconomyPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
