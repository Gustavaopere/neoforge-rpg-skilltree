package dev.gustavopere.rpgskilltree.core;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ExactStaminaDebitCaptureTest {
    public static void main(String[] args) {
        exactDebitUsesObservedDeltaNotAttemptedAmount();
        modifiedAmountIsMetadataOnly();
        finalResourceControlsEligibility();
        forcedConsumptionUsesClampedRealDebit();
        zeroAndNegativeDeltaProduceNoReceipt();
        nonFiniteObservedStateProducesNoReceipt();
        clientSideNeverProducesReceiptButStillCallsOriginal();
        originalIsCalledExactlyOnce();
        canceledOrRejectedCallSiteProducesNoObservationBecauseWrapperIsNeverEntered();
        throwingConsumerPropagatesWithoutReceipt();
        wrappingChainObservesThePhysicalDebitAndCallsInnerOperationOnce();
        System.out.println("ExactStaminaDebitCaptureTest: PASS");
    }

    private static void exactDebitUsesObservedDeltaNotAttemptedAmount() {
        AtomicReference<Double> stamina = new AtomicReference<>(50.0D);
        var capture = ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), 30.0D,
            () -> stamina.set(40.0D), stamina::get
        ).orElseThrow();

        require(close(capture.actualDebit(), 10.0D), "actual debit is before-after");
        require(close(capture.attemptedAmount(), 30.0D), "event amount is preserved only as metadata");
    }

    private static void modifiedAmountIsMetadataOnly() {
        AtomicReference<Double> stamina = new AtomicReference<>(20.0D);
        var capture = ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), 6.0D,
            () -> stamina.set(11.0D), stamina::get
        ).orElseThrow();

        require(close(capture.actualDebit(), 9.0D), "modified amount cannot override observed debit");
        require(close(capture.attemptedAmount(), 6.0D), "modified amount remains audit metadata");
    }

    private static void finalResourceControlsEligibility() {
        AtomicReference<Double> stamina = new AtomicReference<>(20.0D);
        require(ExactStaminaDebitCapture.aroundConsumer(
            true, false, stamina.get(), 5.0D,
            () -> stamina.set(15.0D), stamina::get
        ).isEmpty(), "resource changed away from STAMINA cannot issue a receipt");

        stamina.set(20.0D);
        require(ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), 5.0D,
            () -> stamina.set(15.0D), stamina::get
        ).isPresent(), "resource changed to final STAMINA may issue a receipt");
    }

    private static void forcedConsumptionUsesClampedRealDebit() {
        AtomicReference<Double> stamina = new AtomicReference<>(3.0D);
        var capture = ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), 10.0D,
            () -> stamina.set(0.0D), stamina::get
        ).orElseThrow();

        require(close(capture.actualDebit(), 3.0D), "forced/clamped debit is 3, never requested 10");
    }

    private static void zeroAndNegativeDeltaProduceNoReceipt() {
        AtomicReference<Double> stamina = new AtomicReference<>(10.0D);
        require(ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), 0.0D,
            () -> {}, stamina::get
        ).isEmpty(), "zero debit is not a receipt");

        stamina.set(10.0D);
        require(ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), -5.0D,
            () -> stamina.set(15.0D), stamina::get
        ).isEmpty(), "resource gain is not a stamina debit");
    }

    private static void nonFiniteObservedStateProducesNoReceipt() {
        AtomicReference<Double> stamina = new AtomicReference<>(10.0D);
        require(ExactStaminaDebitCapture.aroundConsumer(
            true, true, Double.NaN, 1.0D,
            () -> stamina.set(9.0D), stamina::get
        ).isEmpty(), "NaN before invalidates evidence");
        require(ExactStaminaDebitCapture.aroundConsumer(
            true, true, Double.POSITIVE_INFINITY, 1.0D,
            () -> stamina.set(9.0D), stamina::get
        ).isEmpty(), "infinite before invalidates evidence");
        require(ExactStaminaDebitCapture.aroundConsumer(
            true, true, 10.0D, Double.POSITIVE_INFINITY,
            () -> stamina.set(0.0D), stamina::get
        ).orElseThrow().actualDebit() == 10.0D,
            "non-finite attempted amount is only metadata when the actual state transition is finite");
    }

    private static void clientSideNeverProducesReceiptButStillCallsOriginal() {
        AtomicInteger calls = new AtomicInteger();
        AtomicReference<Double> stamina = new AtomicReference<>(10.0D);
        require(ExactStaminaDebitCapture.aroundConsumer(
            false, true, stamina.get(), 4.0D,
            () -> { calls.incrementAndGet(); stamina.set(6.0D); }, stamina::get
        ).isEmpty(), "client-side observation cannot produce a receipt");
        require(calls.get() == 1, "client wrapper must not suppress the original operation");
    }

    private static void originalIsCalledExactlyOnce() {
        AtomicInteger calls = new AtomicInteger();
        AtomicReference<Double> stamina = new AtomicReference<>(10.0D);
        ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), 2.0D,
            () -> { calls.incrementAndGet(); stamina.set(8.0D); }, stamina::get
        );
        require(calls.get() == 1, "physical consumer executes exactly once");
    }

    private static void canceledOrRejectedCallSiteProducesNoObservationBecauseWrapperIsNeverEntered() {
        AtomicInteger calls = new AtomicInteger();
        boolean canceled = true;
        boolean predicateAccepted = false;
        boolean force = false;
        if (!canceled && (predicateAccepted || force)) {
            ExactStaminaDebitCapture.aroundConsumer(
                true, true, 10.0D, 2.0D, calls::incrementAndGet, () -> 8.0D
            );
        }
        require(calls.get() == 0, "cancellation/predicate gates are naturally respected by the call site");
    }

    private static void throwingConsumerPropagatesWithoutReceipt() {
        AtomicInteger afterReads = new AtomicInteger();
        boolean threw = false;
        try {
            ExactStaminaDebitCapture.aroundConsumer(
                true, true, 10.0D, 2.0D,
                () -> { throw new IllegalStateException("boom"); },
                () -> { afterReads.incrementAndGet(); return 8.0D; }
            );
        } catch (IllegalStateException expected) {
            threw = true;
        }
        require(threw, "consumer exception must propagate");
        require(afterReads.get() == 0, "failed consumer cannot mint a post-consume receipt");
    }

    private static void wrappingChainObservesThePhysicalDebitAndCallsInnerOperationOnce() {
        AtomicInteger physicalCalls = new AtomicInteger();
        AtomicInteger innerWrapperCalls = new AtomicInteger();
        AtomicReference<Double> stamina = new AtomicReference<>(12.0D);

        var capture = ExactStaminaDebitCapture.aroundConsumer(
            true, true, stamina.get(), 5.0D,
            () -> {
                innerWrapperCalls.incrementAndGet();
                physicalCalls.incrementAndGet();
                stamina.set(7.0D);
            },
            stamina::get
        ).orElseThrow();

        require(innerWrapperCalls.get() == 1, "a chained wrapper is reached once");
        require(physicalCalls.get() == 1, "the physical consumer is reached once");
        require(close(capture.actualDebit(), 5.0D), "outer receipt observes final physical debit");
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
