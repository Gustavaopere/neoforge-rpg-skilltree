package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import java.lang.reflect.Method;

/** Exact audited provider contract for A0083 direct-magic sustain on Iron's 1.21.1-3.16.3. */
public final class IronsSustainVersionContract {
    public static final String SUPPORTED_RELEASE = "1.21.1-3.16.3";
    public static final String SUPPORTED_VERSION = SUPPORTED_RELEASE;
    public static final String DAMAGE_SOURCE_CLASS = "io.redspace.ironsspellbooks.damage.SpellDamageSource";

    private static final RuntimeContract CONTRACT = inspectRuntimeContract();
    private static volatile boolean runtimeInvocationHealthy = true;

    private IronsSustainVersionContract() {}

    /** Exact-match only: provider version drift disables A0083 rather than widening the contract. */
    public static boolean supportsVersion(String version) {
        return SUPPORTED_RELEASE.equals(version);
    }

    /**
     * Verifies the concrete runtime class and methods without linking this optional compat class
     * against Iron's implementation packages at class-load time. Once an audited reflective call
     * fails, the contract stays unavailable for the remainder of this process so purchase/effect
     * gates fail closed instead of continuing to advertise a broken provider binding.
     */
    public static boolean runtimeContractPresent() {
        return runtimeInvocationHealthy
            && CONTRACT.sourceClass() != null
            && CONTRACT.spellMethod() != null
            && CONTRACT.lifestealMethod() != null;
    }

    public static boolean isSpellDamageSource(Object source) {
        return source != null && CONTRACT.sourceClass() != null && CONTRACT.sourceClass().isInstance(source);
    }

    /** Returns null if the audited method cannot be invoked; callers must fail closed in that case. */
    public static Float lifestealPercent(Object source) {
        if (!isSpellDamageSource(source) || CONTRACT.lifestealMethod() == null) return null;
        try {
            Object value = CONTRACT.lifestealMethod().invoke(source);
            if (value instanceof Number number) return number.floatValue();
            runtimeInvocationHealthy = false;
            return null;
        } catch (ReflectiveOperationException | RuntimeException failure) {
            runtimeInvocationHealthy = false;
            return null;
        }
    }

    private static RuntimeContract inspectRuntimeContract() {
        try {
            Class<?> source = Class.forName(DAMAGE_SOURCE_CLASS, false, IronsSustainVersionContract.class.getClassLoader());
            Method spell = source.getMethod("spell");
            Method lifesteal = source.getMethod("getLifestealPercent");
            return new RuntimeContract(source, spell, lifesteal);
        } catch (ClassNotFoundException | NoSuchMethodException | LinkageError failure) {
            return new RuntimeContract(null, null, null);
        }
    }

    private record RuntimeContract(Class<?> sourceClass, Method spellMethod, Method lifestealMethod) {}
}
