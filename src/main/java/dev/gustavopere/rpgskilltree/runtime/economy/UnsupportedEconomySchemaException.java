package dev.gustavopere.rpgskilltree.runtime.economy;

/** Raised when persisted economy data was written by a newer unsupported schema. */
public final class UnsupportedEconomySchemaException extends EconomyPersistenceException {
    public UnsupportedEconomySchemaException(int schema, int supportedSchema) {
        super("Unsupported colony-economy schema " + schema + "; current schema is " + supportedSchema);
    }
}
