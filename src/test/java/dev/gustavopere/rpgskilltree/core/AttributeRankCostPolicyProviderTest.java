package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class AttributeRankCostPolicyProviderTest {
    public static void main(String[] args) {
        unconfiguredProviderFailsClosed();
        fixedProviderReturnsPolicy();
        installableProviderSwapsAndClearsAtomically();
        nullOptionalIsRejected();
        System.out.println("AttributeRankCostPolicyProviderTest: PASS");
    }

    private static void unconfiguredProviderFailsClosed() {
        AttributeRankCostPolicyProvider provider = AttributeRankCostPolicyProvider.unconfigured();
        if (provider.current().isPresent()) throw new AssertionError("unconfigured provider must be empty");
        expect(IllegalStateException.class, provider::requireCurrent);
    }

    private static void fixedProviderReturnsPolicy() {
        AttributeRankCostPolicy policy = (attribute, startRank, rankCount) -> 17L;
        AttributeRankCostPolicyProvider provider = AttributeRankCostPolicyProvider.fixed(policy);
        eq(policy, provider.requireCurrent());
    }

    private static void installableProviderSwapsAndClearsAtomically() {
        InstallableAttributeRankCostPolicyProvider provider = new InstallableAttributeRankCostPolicyProvider();
        AttributeRankCostPolicy first = (attribute, startRank, rankCount) -> 3L;
        AttributeRankCostPolicy second = (attribute, startRank, rankCount) -> 7L;

        if (provider.install(first).isPresent()) throw new AssertionError("first install must have no previous policy");
        eq(first, provider.requireCurrent());
        eq(first, provider.install(second).orElseThrow());
        eq(second, provider.requireCurrent());
        eq(second, provider.clear().orElseThrow());
        if (provider.current().isPresent()) throw new AssertionError("clear must remove policy");
        expect(IllegalStateException.class, provider::requireCurrent);
    }

    private static void nullOptionalIsRejected() {
        AttributeRankCostPolicyProvider broken = () -> null;
        expect(IllegalStateException.class, broken::requireCurrent);
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
