package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;
import java.util.function.DoubleSupplier;

/**
 * Measures a resource consumer by its observed stamina state transition.
 *
 * <p>The attempted amount is audit metadata only. The exact debit is always {@code before - after}.
 * The original consumer is invoked exactly once and any exception is propagated without producing a receipt.
 */
public final class ExactStaminaDebitCapture {
    private ExactStaminaDebitCapture() {}

    public static Optional<Capture> aroundConsumer(
        boolean serverAuthoritative,
        boolean finalResourceIsStamina,
        double staminaBefore,
        double attemptedAmount,
        Operation original,
        DoubleSupplier staminaAfter
    ) {
        Objects.requireNonNull(original);
        Objects.requireNonNull(staminaAfter);

        original.run();
        double after = staminaAfter.getAsDouble();

        if (!serverAuthoritative || !finalResourceIsStamina) return Optional.empty();
        if (!Double.isFinite(staminaBefore) || !Double.isFinite(after)) return Optional.empty();

        double actualDebit = staminaBefore - after;
        if (!Double.isFinite(actualDebit) || actualDebit <= 0.0D) return Optional.empty();

        return Optional.of(new Capture(actualDebit, attemptedAmount, staminaBefore, after));
    }

    @FunctionalInterface
    public interface Operation {
        void run();
    }

    public record Capture(
        double actualDebit,
        double attemptedAmount,
        double staminaBefore,
        double staminaAfter
    ) {
        public Capture {
            if (!Double.isFinite(actualDebit) || actualDebit <= 0.0D) {
                throw new IllegalArgumentException("actualDebit must be finite and positive");
            }
            if (!Double.isFinite(staminaBefore) || !Double.isFinite(staminaAfter)) {
                throw new IllegalArgumentException("observed stamina values must be finite");
            }
        }
    }
}
