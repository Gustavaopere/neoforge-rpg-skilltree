package dev.gustavopere.rpgskilltree.itemization.blacksmith.persistence;

import com.mojang.serialization.DataResult;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithComposition;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithPartState;

import java.util.Objects;

/** Fail-closed schema gate for persisted Blacksmith physical state. */
public final class BlacksmithSchemaMigrator {
    public static final int CURRENT_SCHEMA_VERSION = 1;

    private BlacksmithSchemaMigrator() {}

    public static BlacksmithPartState requireCurrent(BlacksmithPartState state) {
        Objects.requireNonNull(state, "state");
        requireCurrentVersion(state.schemaVersion(), "part state");
        return state;
    }

    public static BlacksmithComposition requireCurrent(BlacksmithComposition composition) {
        Objects.requireNonNull(composition, "composition");
        requireCurrentVersion(composition.schemaVersion(), "composition");
        return composition;
    }

    static DataResult<BlacksmithPartState> validateForCodec(BlacksmithPartState state) {
        try {
            return DataResult.success(requireCurrent(state));
        } catch (IllegalArgumentException exception) {
            return DataResult.error(exception::getMessage);
        }
    }

    static DataResult<BlacksmithComposition> validateForCodec(BlacksmithComposition composition) {
        try {
            return DataResult.success(requireCurrent(composition));
        } catch (IllegalArgumentException exception) {
            return DataResult.error(exception::getMessage);
        }
    }

    private static void requireCurrentVersion(int schemaVersion, String stateName) {
        if (schemaVersion != CURRENT_SCHEMA_VERSION) {
            throw new IllegalArgumentException(
                "unsupported Blacksmith " + stateName + " schema: " + schemaVersion
                    + " (current=" + CURRENT_SCHEMA_VERSION + ")"
            );
        }
    }
}
