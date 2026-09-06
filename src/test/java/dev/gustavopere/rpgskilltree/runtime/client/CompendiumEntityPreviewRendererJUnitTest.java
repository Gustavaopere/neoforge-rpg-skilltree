package dev.gustavopere.rpgskilltree.runtime.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

final class CompendiumEntityPreviewRendererJUnitTest {
    @Test
    void physicalRendererFailureBecomesFallbackInsteadOfEscaping() {
        assertEquals(
            CompendiumEntityPreviewRenderer.RenderResult.FALLBACK,
            CompendiumEntityPreviewRenderer.runRenderAttempt(() -> {
                throw new IllegalStateException("synthetic renderer failure");
            })
        );
        assertEquals(
            CompendiumEntityPreviewRenderer.RenderResult.FALLBACK,
            CompendiumEntityPreviewRenderer.runRenderAttempt(() -> {
                throw new LinkageError("synthetic renderer linkage failure");
            })
        );
    }

    @Test
    void successfulPhysicalRendererReportsRendered() {
        AtomicBoolean invoked = new AtomicBoolean();

        assertEquals(
            CompendiumEntityPreviewRenderer.RenderResult.RENDERED,
            CompendiumEntityPreviewRenderer.runRenderAttempt(() -> invoked.set(true))
        );
        assertTrue(invoked.get());
    }
}
