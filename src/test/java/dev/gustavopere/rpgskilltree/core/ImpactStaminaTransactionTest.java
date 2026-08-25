package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class ImpactStaminaTransactionTest {
    private ImpactStaminaTransactionTest() {}

    public static void main(String[] args) throws Exception {
        sufficientStaminaDebitsEntireCostBeforeImpactMutation();
        insufficientStaminaLeavesStaminaAndImpactUntouched();
        invalidCostFailsWithoutSideEffects();
        nonFiniteCurrentStaminaFailsWithoutSideEffects();
        nativeResetPrecedesWriteAndMutation();
        System.out.println("ImpactStaminaTransactionTest: PASS");
    }

    private static void sufficientStaminaDebitsEntireCostBeforeImpactMutation() throws Exception {
        AtomicReference<Float> stamina = new AtomicReference<>(10.0F);
        AtomicReference<Float> impact = new AtomicReference<>(10.0F);
        List<String> order = new ArrayList<>();
        Object access = nativeAccess(stamina, order);

        boolean committed = invoke(3.5F, access, () -> {
            order.add("mutate");
            impact.set(6.5F);
        });

        require(committed, "sufficient stamina must commit");
        require(close(stamina.get(), 6.5F), "full cost must be debited");
        require(close(impact.get(), 6.5F), "impact must mutate after debit");
        require(order.indexOf("set") >= 0 && order.indexOf("mutate") > order.indexOf("set"),
            "impact mutation must happen after stamina write");
    }

    private static void insufficientStaminaLeavesStaminaAndImpactUntouched() throws Exception {
        AtomicReference<Float> stamina = new AtomicReference<>(3.0F);
        AtomicReference<Float> impact = new AtomicReference<>(10.0F);
        List<String> order = new ArrayList<>();
        Object access = nativeAccess(stamina, order);

        boolean committed = invoke(3.5F, access, () -> {
            order.add("mutate");
            impact.set(6.5F);
        });

        require(!committed, "insufficient stamina must fail closed");
        require(close(stamina.get(), 3.0F), "insufficient stamina cannot be partially debited");
        require(close(impact.get(), 10.0F), "impact must remain original");
        require(!order.contains("set") && !order.contains("reset") && !order.contains("mutate"),
            "failed transaction cannot write stamina, reset action tick, or mutate impact");
    }

    private static void invalidCostFailsWithoutSideEffects() throws Exception {
        for (float cost : new float[] {0.0F, -1.0F, Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY}) {
            AtomicReference<Float> stamina = new AtomicReference<>(10.0F);
            AtomicReference<Float> impact = new AtomicReference<>(10.0F);
            List<String> order = new ArrayList<>();
            boolean committed = invoke(cost, nativeAccess(stamina, order), () -> {
                order.add("mutate");
                impact.set(1.0F);
            });
            require(!committed, "invalid cost must fail closed: " + cost);
            require(close(stamina.get(), 10.0F), "invalid cost cannot change stamina: " + cost);
            require(close(impact.get(), 10.0F), "invalid cost cannot change impact: " + cost);
            require(!order.contains("reset") && !order.contains("set") && !order.contains("mutate"),
                "invalid cost cannot perform transactional writes: " + cost);
        }
    }

    private static void nonFiniteCurrentStaminaFailsWithoutSideEffects() throws Exception {
        for (float current : new float[] {Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY}) {
            AtomicReference<Float> stamina = new AtomicReference<>(current);
            AtomicReference<Float> impact = new AtomicReference<>(10.0F);
            List<String> order = new ArrayList<>();
            boolean committed = invoke(1.0F, nativeAccess(stamina, order), () -> {
                order.add("mutate");
                impact.set(9.0F);
            });
            require(!committed, "non-finite current stamina must fail closed: " + current);
            require(close(impact.get(), 10.0F), "non-finite current stamina cannot mutate impact");
            require(!order.contains("reset") && !order.contains("set") && !order.contains("mutate"),
                "non-finite current stamina cannot perform writes");
        }
    }

    private static void nativeResetPrecedesWriteAndMutation() throws Exception {
        AtomicReference<Float> stamina = new AtomicReference<>(5.0F);
        List<String> order = new ArrayList<>();
        boolean committed = invoke(2.0F, nativeAccess(stamina, order), () -> order.add("mutate"));
        require(committed, "valid debit must commit");
        int reset = order.indexOf("reset");
        int set = order.indexOf("set");
        int mutate = order.indexOf("mutate");
        require(reset >= 0 && set > reset && mutate > set,
            "native semantic order must be resetActionTick -> setStamina -> mutateImpact: " + order);
    }

    private static Object nativeAccess(AtomicReference<Float> stamina, List<String> order) throws Exception {
        Class<?> accessType;
        try {
            accessType = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaTransaction$NativeStaminaAccess");
        } catch (ClassNotFoundException missing) {
            throw new AssertionError("missing ImpactStaminaTransaction production primitive", missing);
        }
        return Proxy.newProxyInstance(
            ImpactStaminaTransactionTest.class.getClassLoader(),
            new Class<?>[] {accessType},
            (proxy, method, args) -> switch (method.getName()) {
                case "getStamina" -> stamina.get();
                case "hasStamina" -> { order.add("has"); yield stamina.get() >= (Float) args[0]; }
                case "setStamina" -> { order.add("set"); stamina.set((Float) args[0]); yield null; }
                case "resetActionTick" -> { order.add("reset"); yield null; }
                case "toString" -> "test-native-stamina";
                case "hashCode" -> System.identityHashCode(proxy);
                case "equals" -> proxy == args[0];
                default -> throw new UnsupportedOperationException(method.getName());
            }
        );
    }

    private static boolean invoke(float cost, Object access, Runnable mutation) throws Exception {
        Class<?> transaction;
        try {
            transaction = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaTransaction");
        } catch (ClassNotFoundException missing) {
            throw new AssertionError("missing ImpactStaminaTransaction production primitive", missing);
        }
        Class<?> accessType = access.getClass().getInterfaces()[0];
        Method method = transaction.getMethod("tryDebitExactNativeStamina", float.class, accessType, Runnable.class);
        try {
            return (boolean) method.invoke(null, cost, access, mutation);
        } catch (InvocationTargetException wrapped) {
            Throwable cause = wrapped.getCause();
            if (cause instanceof Exception exception) throw exception;
            if (cause instanceof Error error) throw error;
            throw wrapped;
        }
    }

    private static boolean close(float a, float b) { return Math.abs(a - b) < 0.000001F; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
