package dev.gustavopere.rpgskilltree.core;

import java.util.Optional;

public final class HeavyImpactReceiptWindowTest {
    public static void main(String[] args) {
        exactIdentityCanPeekRepeatedlyAndClaimOncePerConsumer();
        mismatchedIdentityFailsClosed();
        closedWindowCannotLeakReceiptPastPostDispatch();
        nestedWindowsRemainIndependent();
        ambiguousOperationProducesEmptyWindow();
        emptyWindowNeverClaims();
        System.out.println("HeavyImpactReceiptWindowTest: PASS");
    }

    private static void exactIdentityCanPeekRepeatedlyAndClaimOncePerConsumer() {
        Object victim = new Object();
        Object source = new Object();
        var receipt = new HeavyImpactReceiptCorrelation.Receipt(
            "actor", HeavyImpactReceiptCorrelation.ImpactKind.KNOCKDOWN
        );
        var window = new HeavyImpactReceiptWindow(victim, source, Optional.of(receipt));

        require(window.peek(victim, source).orElseThrow() == receipt, "first exact peek succeeds");
        require(window.peek(victim, source).orElseThrow() == receipt, "repeated peek must not consume receipt");
        require(window.claim(victim, source, "consumer-a").orElseThrow() == receipt,
            "first claim by consumer A succeeds");
        require(window.claim(victim, source, "consumer-a").isEmpty(),
            "second claim by the same consumer A is rejected");
        require(window.claim(victim, source, "consumer-b").orElseThrow() == receipt,
            "consumer B has an independent claim identity for the same operation receipt");
        require(window.peek(victim, source).orElseThrow() == receipt,
            "claims must not make peek consume the receipt while the POST window is still open");
    }

    private static void mismatchedIdentityFailsClosed() {
        Object victim = new Object();
        Object source = new Object();
        var receipt = new HeavyImpactReceiptCorrelation.Receipt(
            "actor", HeavyImpactReceiptCorrelation.ImpactKind.LONG_STUN
        );
        var window = new HeavyImpactReceiptWindow(victim, source, Optional.of(receipt));

        require(window.peek(new Object(), source).isEmpty(), "victim mismatch cannot peek");
        require(window.peek(victim, new Object()).isEmpty(), "source mismatch cannot peek");
        require(window.claim(new Object(), source, "consumer-a").isEmpty(), "victim mismatch cannot claim");
        require(window.claim(victim, new Object(), "consumer-a").isEmpty(), "source mismatch cannot claim");
        require(window.claim(victim, source, "consumer-a").orElseThrow() == receipt,
            "mismatched attempts must not consume the valid consumer claim");
    }

    private static void closedWindowCannotLeakReceiptPastPostDispatch() {
        Object victim = new Object();
        Object source = new Object();
        var receipt = new HeavyImpactReceiptCorrelation.Receipt(
            "actor", HeavyImpactReceiptCorrelation.ImpactKind.NEUTRALIZE
        );
        var window = new HeavyImpactReceiptWindow(victim, source, Optional.of(receipt));

        require(window.peek(victim, source).isPresent(), "open window exposes its exact receipt");
        window.close();
        require(window.peek(victim, source).isEmpty(), "receipt must not survive the end of TAKE_DAMAGE_POST");
        require(window.claim(victim, source, "consumer-a").isEmpty(),
            "closed POST window cannot be claimed after dispatch cleanup");
        window.close();
        require(window.peek(victim, source).isEmpty(), "closing twice remains idempotently closed");
    }

    private static void nestedWindowsRemainIndependent() {
        Object outerVictim = new Object();
        Object outerSource = new Object();
        var outerReceipt = new HeavyImpactReceiptCorrelation.Receipt(
            "outer", HeavyImpactReceiptCorrelation.ImpactKind.LONG_STUN
        );
        var outer = new HeavyImpactReceiptWindow(outerVictim, outerSource, Optional.of(outerReceipt));

        Object innerVictim = new Object();
        Object innerSource = new Object();
        var innerReceipt = new HeavyImpactReceiptCorrelation.Receipt(
            "inner", HeavyImpactReceiptCorrelation.ImpactKind.KNOCKDOWN
        );
        var inner = new HeavyImpactReceiptWindow(innerVictim, innerSource, Optional.of(innerReceipt));

        require(inner.claim(innerVictim, innerSource, "consumer-a").orElseThrow() == innerReceipt,
            "nested inner damage claims only its own receipt");
        require(inner.peek(outerVictim, outerSource).isEmpty(),
            "nested inner window cannot expose the outer operation receipt");
        inner.close();
        require(outer.peek(outerVictim, outerSource).orElseThrow() == outerReceipt,
            "closing nested damage must not steal the still-active outer receipt");
        require(outer.claim(outerVictim, outerSource, "consumer-a").orElseThrow() == outerReceipt,
            "same consumer id may independently claim the outer operation after nested damage unwinds");
    }

    private static void ambiguousOperationProducesEmptyWindow() {
        Object victim = new Object();
        Object source = new Object();
        var correlation = new HeavyImpactReceiptCorrelation();
        correlation.begin("actor", victim, source);
        correlation.recordFinalImpact(victim, HeavyImpactReceiptCorrelation.ImpactKind.LONG_STUN);
        correlation.recordFinalImpact(victim, HeavyImpactReceiptCorrelation.ImpactKind.KNOCKDOWN);
        Optional<HeavyImpactReceiptCorrelation.Receipt> ambiguous = correlation.complete(victim, source);

        require(ambiguous.isEmpty(), "ambiguous operation must produce no receipt");
        var window = new HeavyImpactReceiptWindow(victim, source, ambiguous);
        require(window.peek(victim, source).isEmpty(), "ambiguous operation window remains fail-closed");
        require(window.claim(victim, source, "consumer-a").isEmpty(),
            "ambiguous operation can never be consumed as heavy-impact evidence");
    }

    private static void emptyWindowNeverClaims() {
        Object victim = new Object();
        Object source = new Object();
        var window = new HeavyImpactReceiptWindow(victim, source, Optional.empty());
        require(window.peek(victim, source).isEmpty(), "empty window has no evidence");
        require(window.claim(victim, source, "consumer-a").isEmpty(), "empty window cannot be claimed");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
