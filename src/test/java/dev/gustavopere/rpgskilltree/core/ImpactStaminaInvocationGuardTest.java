package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ImpactStaminaInvocationGuardTest {
    private ImpactStaminaInvocationGuardTest() {}

    public static void main(String[] args) throws Exception {
        duplicateCallbackClaimsOnlyOnce();
        sourceAndVictimMismatchFailClosed();
        nestedDamageKeepsScopesIndependent();
        twoPlayersDoNotShareClaims();
        exceptionCleanupRestoresEmptyScope();
        System.out.println("ImpactStaminaInvocationGuardTest: PASS");
    }

    private static void duplicateCallbackClaimsOnlyOnce() throws Exception {
        Object source = new Object(); Object victim = new Object();
        try (AutoCloseable ignored = open(source, victim)) {
            require(claim(source, victim), "first callback must claim");
            require(!claim(source, victim), "duplicate callback must be rejected");
        }
        require(!claim(source, victim), "claim outside invocation must fail closed");
    }

    private static void sourceAndVictimMismatchFailClosed() throws Exception {
        Object source = new Object(); Object victim = new Object();
        try (AutoCloseable ignored = open(source, victim)) {
            require(!claim(new Object(), victim), "source mismatch must fail closed");
            require(!claim(source, new Object()), "victim mismatch must fail closed");
            require(claim(source, victim), "mismatch must not consume valid claim");
        }
    }

    private static void nestedDamageKeepsScopesIndependent() throws Exception {
        Object outerSource = new Object(); Object outerVictim = new Object();
        Object innerSource = new Object(); Object innerVictim = new Object();
        try (AutoCloseable outer = open(outerSource, outerVictim)) {
            require(claim(outerSource, outerVictim), "outer claims once");
            try (AutoCloseable inner = open(innerSource, innerVictim)) {
                require(!claim(outerSource, outerVictim), "outer cannot claim through active inner frame");
                require(claim(innerSource, innerVictim), "inner gets independent claim");
            }
            require(!claim(outerSource, outerVictim), "outer remains already claimed after inner cleanup");
        }
    }

    private static void twoPlayersDoNotShareClaims() throws Exception {
        Object sourceA = new Object(); Object sourceB = new Object();
        Object playerA = new Object(); Object playerB = new Object();
        try (AutoCloseable ignored = open(sourceA, playerA)) { require(claim(sourceA, playerA), "player A"); }
        try (AutoCloseable ignored = open(sourceB, playerB)) { require(claim(sourceB, playerB), "player B"); }
    }

    private static void exceptionCleanupRestoresEmptyScope() throws Exception {
        Object source = new Object(); Object victim = new Object();
        try {
            try (AutoCloseable ignored = open(source, victim)) {
                require(claim(source, victim), "claim before synthetic failure");
                throw new IllegalStateException("synthetic failure");
            }
        } catch (IllegalStateException expected) {
            require("synthetic failure".equals(expected.getMessage()), "unexpected synthetic failure");
        }
        require(!claim(source, victim), "exception must leave no stale scope");
        try (AutoCloseable ignored = open(source, victim)) {
            require(claim(source, victim), "identities reusable after cleanup");
        }
    }

    private static AutoCloseable open(Object source, Object victim) throws Exception {
        return (AutoCloseable) invoke("open", new Class<?>[] {Object.class, Object.class}, source, victim);
    }
    private static boolean claim(Object source, Object victim) throws Exception {
        return (boolean) invoke("claim", new Class<?>[] {Object.class, Object.class}, source, victim);
    }
    private static Object invoke(String name, Class<?>[] types, Object... args) throws Exception {
        Class<?> guard;
        try { guard = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaInvocationGuard"); }
        catch (ClassNotFoundException missing) { throw new AssertionError("missing ImpactStaminaInvocationGuard", missing); }
        Method m = guard.getMethod(name, types);
        try { return m.invoke(null, args); }
        catch (InvocationTargetException wrapped) {
            Throwable cause = wrapped.getCause();
            if (cause instanceof Exception e) throw e;
            if (cause instanceof Error e) throw e;
            throw wrapped;
        }
    }

    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
