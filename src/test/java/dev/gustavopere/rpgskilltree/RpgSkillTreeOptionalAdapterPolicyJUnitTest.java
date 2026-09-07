package dev.gustavopere.rpgskilltree;

import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class RpgSkillTreeOptionalAdapterPolicyJUnitTest {
    private static final Method DISABLED_REASON = disabledReasonMethod();

    @Test
    void absentMalumFailsClosedAtTheCommonAdapterBoundary() {
        assertEquals("unsupported_version", disabledReason(OptionalIntegrations.Provider.MALUM));
    }

    private static String disabledReason(OptionalIntegrations.Provider provider) {
        try {
            return (String) DISABLED_REASON.invoke(null, provider);
        } catch (IllegalAccessException exception) {
            throw new AssertionError(exception);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof RuntimeException runtime) throw runtime;
            if (cause instanceof Error error) throw error;
            throw new AssertionError(cause);
        }
    }

    private static Method disabledReasonMethod() {
        try {
            Method method = RpgSkillTreeMod.class.getDeclaredMethod(
                "optionalAdapterDisabledReason",
                OptionalIntegrations.Provider.class
            );
            method.setAccessible(true);
            return method;
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }
}
