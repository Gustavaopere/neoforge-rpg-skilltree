package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalPersistenceTurnBudgetTest {
    private static final int MAX_ATTEMPTS_PER_TICK = 16;
    private static final int MAX_DURABLE_HANDOFFS_PER_CHUNK = 8;

    @Test
    void transientOnlyMayUseTheWholePersistenceEnvelope() {
        GeothermalPersistenceTurnBudget.Allocation allocation =
                GeothermalPersistenceTurnBudget.allocate(true, false);
        assertEquals(16, allocation.transientAttempts());
        assertEquals(0, allocation.recoveryChunks());
        assertWithinGlobalAttemptBudget(allocation);
    }

    @Test
    void recoveryOnlyMayVisitTwoEightReceiptChunks() {
        GeothermalPersistenceTurnBudget.Allocation allocation =
                GeothermalPersistenceTurnBudget.allocate(false, true);
        assertEquals(0, allocation.transientAttempts());
        assertEquals(2, allocation.recoveryChunks());
        assertWithinGlobalAttemptBudget(allocation);
    }

    @Test
    void mixedWorkReservesProgressForBothAuthoritiesInsideOneSixteenAttemptEnvelope() {
        GeothermalPersistenceTurnBudget.Allocation allocation =
                GeothermalPersistenceTurnBudget.allocate(true, true);
        assertEquals(8, allocation.transientAttempts());
        assertEquals(1, allocation.recoveryChunks());
        assertWithinGlobalAttemptBudget(allocation);
    }

    @Test
    void noWorkAllocatesNoPersistenceAttempts() {
        GeothermalPersistenceTurnBudget.Allocation allocation =
                GeothermalPersistenceTurnBudget.allocate(false, false);
        assertEquals(0, allocation.transientAttempts());
        assertEquals(0, allocation.recoveryChunks());
        assertWithinGlobalAttemptBudget(allocation);
    }

    @Test
    void runtimeConsumesBothSharesFromOneAllocationInsteadOfIndependentFullDrains() throws IOException {
        String runtime = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenRuntime.java"));

        assertTrue(runtime.contains("GeothermalPersistenceTurnBudget.allocate("),
                "runtime must make one explicit allocation decision for transient and durable recovery work");
        assertTrue(runtime.contains("allocation.transientAttempts()"),
                "transient persistence must consume only the attempt share returned by the shared allocator");
        assertTrue(runtime.contains("allocation.recoveryChunks()"),
                "durable recovery must consume only the chunk share returned by the same allocation");
    }

    private static void assertWithinGlobalAttemptBudget(GeothermalPersistenceTurnBudget.Allocation allocation) {
        int worstCaseAttempts = allocation.transientAttempts()
                + allocation.recoveryChunks() * MAX_DURABLE_HANDOFFS_PER_CHUNK;
        assertTrue(worstCaseAttempts <= MAX_ATTEMPTS_PER_TICK,
                "transient and durable recovery work must share the same hard 16-attempt tick budget");
    }
}
