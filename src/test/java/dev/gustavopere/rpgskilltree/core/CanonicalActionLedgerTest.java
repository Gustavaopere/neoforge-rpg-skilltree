package dev.gustavopere.rpgskilltree.core;

public final class CanonicalActionLedgerTest {
    public static void main(String[] args) {
        identitySurvivesProviderCallbacksAndChildren();
        rootClaimsAreIdempotentPerConsumer();
        secondaryProcsCannotClaimPrimaryEffects();
        actorCleanupRemovesOnlyThatActorsClaims();
        System.out.println("CanonicalActionLedgerTest: PASS");
    }

    private static void identitySurvivesProviderCallbacksAndChildren() {
        var action = CanonicalActionIdentity.root("player-a", "attack-17", "epicfight:damage_pre");
        var post = action.withSource("epicfight:damage_post");
        var proc = action.child("rpgskilltree:secondary_damage");

        require(action.sameAction(post), "PRE and POST must retain one action identity");
        require(action.sameAction(proc), "derived effects must retain their parent action identity");
        require(post.origin().procDepth() == 0, "provider callback changes must not create a proc");
        require(proc.origin().procDepth() == 1, "derived effects must increment proc depth");
    }

    private static void rootClaimsAreIdempotentPerConsumer() {
        var ledger = new CanonicalEventLedger(64);
        var action = CanonicalActionIdentity.root("player-a", "attack-17", "epicfight:damage_pre");

        require(ledger.claimPrimaryOnce(action, "critical", 1_000L, 30_000L), "first critical claim");
        require(!ledger.claimPrimaryOnce(action.withSource("epicfight:damage_post"), "critical", 1_001L, 30_000L),
            "same action cannot resolve critical twice");
        require(ledger.claimPrimaryOnce(action, "fury-credit", 1_002L, 30_000L),
            "independent consumers have independent claims");
    }

    private static void secondaryProcsCannotClaimPrimaryEffects() {
        var ledger = new CanonicalEventLedger(64);
        var root = CanonicalActionIdentity.root("player-a", "attack-17", "epicfight:damage_pre");

        require(!ledger.claimPrimaryOnce(root.child("rpgskilltree:chain"), "fury-credit", 1_000L, 30_000L),
            "proc-depth actions cannot credit root-only resources");
        require(ledger.claimPrimaryOnce(root, "fury-credit", 1_001L, 30_000L),
            "a rejected proc must not consume the root claim");
    }

    private static void actorCleanupRemovesOnlyThatActorsClaims() {
        var ledger = new CanonicalEventLedger(64);
        var first = CanonicalActionIdentity.root("player-a", "attack-17", "neoforge:attack");
        var second = CanonicalActionIdentity.root("player-b", "attack-17", "neoforge:attack");
        require(ledger.claimPrimaryOnce(first, "critical", 1_000L, 30_000L), "first actor claim");
        require(ledger.claimPrimaryOnce(second, "critical", 1_000L, 30_000L), "second actor claim");

        ledger.clearActor("player-a");

        require(ledger.claimPrimaryOnce(first, "critical", 1_001L, 30_000L), "cleared actor can claim anew");
        require(!ledger.claimPrimaryOnce(second, "critical", 1_001L, 30_000L), "other actor remains claimed");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
