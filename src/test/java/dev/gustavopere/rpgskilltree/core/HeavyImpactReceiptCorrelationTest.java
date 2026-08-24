package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.Method;
import java.util.Optional;

/** RED contract for P-0002 before HeavyImpactReceiptCorrelation exists. */
public final class HeavyImpactReceiptCorrelationTest {
    public static void main(String[] args) throws Exception {
        Class<?> type = Class.forName("dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptCorrelation");
        Object correlation = type.getConstructor().newInstance();
        Class<? extends Enum> impactKind = Class.forName(
            "dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptCorrelation$ImpactKind"
        ).asSubclass(Enum.class);
        Method begin = type.getMethod("begin", String.class, Object.class, Object.class);
        Method record = type.getMethod("recordFinalImpact", Object.class, impactKind);
        Method complete = type.getMethod("complete", Object.class, Object.class);
        Method depth = type.getMethod("depth");
        Method clear = type.getMethod("clearThread");

        Object longStun = Enum.valueOf(impactKind, "LONG_STUN");
        Object knockdown = Enum.valueOf(impactKind, "KNOCKDOWN");
        Object neutralize = Enum.valueOf(impactKind, "NEUTRALIZE");
        Object light = Enum.valueOf(impactKind, "LIGHT");

        Object victim = new Object();
        Object source = new Object();
        begin.invoke(correlation, "player-a", victim, source);
        record.invoke(correlation, victim, longStun);
        Object receipt = optionalValue(complete.invoke(correlation, victim, source));
        require(receipt != null, "final LONG stun must produce a heavy-impact receipt");
        require("player-a".equals(receipt.getClass().getMethod("actorId").invoke(receipt)), "receipt keeps victim actor");
        require(longStun.equals(receipt.getClass().getMethod("kind").invoke(receipt)), "receipt keeps final heavy outcome");
        require((int) depth.invoke(correlation) == 0, "completed scope must be removed");

        assertHeavy(begin, record, complete, correlation, impactKind, knockdown, "player-b");
        assertHeavy(begin, record, complete, correlation, impactKind, neutralize, "player-c");

        victim = new Object();
        source = new Object();
        begin.invoke(correlation, "player-light", victim, source);
        record.invoke(correlation, victim, light);
        require(optionalValue(complete.invoke(correlation, victim, source)) == null, "light stun must not produce receipt");

        victim = new Object();
        source = new Object();
        begin.invoke(correlation, "player-none", victim, source);
        require(optionalValue(complete.invoke(correlation, victim, source)) == null, "damage without APPLY_STUN must fail closed");

        victim = new Object();
        source = new Object();
        begin.invoke(correlation, "player-mismatch", victim, source);
        record.invoke(correlation, victim, longStun);
        require(optionalValue(complete.invoke(correlation, victim, new Object())) == null, "source mismatch must fail closed");
        require((int) depth.invoke(correlation) == 0, "mismatched completion must discard poisoned scope");

        Object outerVictim = new Object();
        Object outerSource = new Object();
        Object innerVictim = new Object();
        Object innerSource = new Object();
        begin.invoke(correlation, "outer", outerVictim, outerSource);
        begin.invoke(correlation, "inner", innerVictim, innerSource);
        record.invoke(correlation, innerVictim, knockdown);
        Object innerReceipt = optionalValue(complete.invoke(correlation, innerVictim, innerSource));
        require(innerReceipt != null, "nested inner damage must correlate to inner source");
        record.invoke(correlation, outerVictim, longStun);
        Object outerReceipt = optionalValue(complete.invoke(correlation, outerVictim, outerSource));
        require(outerReceipt != null, "nested outer damage must resume after inner completion");
        require((int) depth.invoke(correlation) == 0, "nested scopes must fully unwind");

        victim = new Object();
        source = new Object();
        begin.invoke(correlation, "player-ambiguous", victim, source);
        record.invoke(correlation, victim, longStun);
        record.invoke(correlation, victim, knockdown);
        require(optionalValue(complete.invoke(correlation, victim, source)) == null,
            "two physical APPLY_STUN callbacks in one damage scope must fail closed");

        victim = new Object();
        source = new Object();
        begin.invoke(correlation, "player-wrong-victim", victim, source);
        record.invoke(correlation, new Object(), longStun);
        require(optionalValue(complete.invoke(correlation, victim, source)) == null,
            "APPLY_STUN from another victim must not attach to current damage");

        clear.invoke(correlation);
        require((int) depth.invoke(correlation) == 0, "clearThread must remove transient correlation state");

        System.out.println("HeavyImpactReceiptCorrelationTest: PASS");
    }

    private static void assertHeavy(
        Method begin,
        Method record,
        Method complete,
        Object correlation,
        Class<? extends Enum> kindType,
        Object kind,
        String actor
    ) throws Exception {
        Object victim = new Object();
        Object source = new Object();
        begin.invoke(correlation, actor, victim, source);
        record.invoke(correlation, victim, kind);
        Object receipt = optionalValue(complete.invoke(correlation, victim, source));
        require(receipt != null, kind + " must produce a receipt");
        require(kind.equals(receipt.getClass().getMethod("kind").invoke(receipt)), kind + " must be preserved");
    }

    private static Object optionalValue(Object value) {
        return ((Optional<?>) value).orElse(null);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
