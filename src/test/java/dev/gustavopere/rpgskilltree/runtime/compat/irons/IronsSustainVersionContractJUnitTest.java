package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

final class IronsSustainVersionContractJUnitTest {
    @Test
    void reflectiveInvocationFailureLatchesTheRuntimeContractUnavailable() throws Exception {
        assertTrue(
            IronsSustainVersionContract.runtimeContractPresent(),
            "the exact Iron's provider test dependency must expose the audited runtime contract"
        );

        Class<?> sourceClass = Class.forName(IronsSustainVersionContract.DAMAGE_SOURCE_CLASS);
        Object brokenSource = Mockito.mock(sourceClass, invocation -> {
            if ("getLifestealPercent".equals(invocation.getMethod().getName())) {
                throw new IllegalStateException("synthetic provider invocation failure");
            }
            return Answers.RETURNS_DEFAULTS.answer(invocation);
        });

        try {
            assertNull(IronsSustainVersionContract.lifestealPercent(brokenSource));
            assertFalse(
                IronsSustainVersionContract.runtimeContractPresent(),
                "an invocation failure must fail closed for subsequent availability checks"
            );
        } finally {
            restoreRuntimeHealthForOtherTests();
        }
    }

    private static void restoreRuntimeHealthForOtherTests() {
        try {
            Field field = IronsSustainVersionContract.class.getDeclaredField("runtimeInvocationHealthy");
            field.setAccessible(true);
            field.setBoolean(null, true);
        } catch (ReflectiveOperationException ignored) {
            // The pre-fix RED state has no latch field yet. Nothing needs restoration there.
        }
    }
}
