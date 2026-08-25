package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Server-side boundary that creates body-cost events only from an exact committed provider debit. */
public final class BodyCostEventProducer {
    private BodyCostEventProducer() {}

    public static Optional<BodyCostResolver.Request> produce(
        CanonicalActionIdentity action,
        BodyCostResolver.Channel channel,
        BodyCostResolver.Cause cause,
        double confirmedDebit,
        Facts facts
    ) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(channel);
        Objects.requireNonNull(cause);
        Objects.requireNonNull(facts);
        if (!Double.isFinite(confirmedDebit) || confirmedDebit < 0.0D) {
            throw new IllegalArgumentException("confirmedDebit must be finite and non-negative");
        }
        if (!facts.serverAuthoritative()
            || !facts.realPlayerOwner()
            || !facts.eligibleAction()
            || !facts.causalProviderPresent()
            || !facts.exactDebitCommitted()
            || confirmedDebit == 0.0D
            || cause == BodyCostResolver.Cause.UNATTRIBUTED
            || !ProcGuard.mayTriggerSecondaryEffect(action.origin())) return Optional.empty();
        return Optional.of(new BodyCostResolver.Request(
            action, channel, cause, confirmedDebit, BodyCostResolver.Attribution.EXACT));
    }

    public record Facts(
        boolean serverAuthoritative,
        boolean realPlayerOwner,
        boolean eligibleAction,
        boolean causalProviderPresent,
        boolean exactDebitCommitted
    ) {}
}
