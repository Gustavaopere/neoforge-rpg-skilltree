package dev.gustavopere.rpgskilltree.runtime.effects;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.ResolvedNodeBehaviorEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.junit.jupiter.api.Test;

final class NodeBehaviorHandlerRegistryJUnitTest {
    @Test
    void missingHandlerIsUnavailableWithoutThrowing() {
        var registry = new NodeBehaviorHandlerRegistry();
        assertTrue(registry.resolveAvailable(ResourceLocation.parse("optionalmod:missing")).isEmpty());
    }

    @Test
    void registeredButUnavailableOptionalProviderIsSkipped() {
        var registry = new NodeBehaviorHandlerRegistry();
        var handler = new TestHandler(false);
        registry.register(ResourceLocation.parse("optionalmod:handler"), handler);

        assertTrue(registry.resolveAvailable(ResourceLocation.parse("optionalmod:handler")).isEmpty());
    }

    @Test
    void availableHandlerResolvesAndDuplicateRegistrationFailsClosed() {
        var registry = new NodeBehaviorHandlerRegistry();
        var id = ResourceLocation.parse("rpgskilltree:test_handler");
        var handler = new TestHandler(true);
        registry.register(id, handler);

        assertSame(handler, registry.resolveAvailable(id).orElseThrow());
        assertThrows(IllegalArgumentException.class, () -> registry.register(id, new TestHandler(true)));
    }

    private record TestHandler(boolean available) implements NodeBehaviorHandler {
        @Override
        public void apply(ServerPlayer player, ResolvedNodeBehaviorEffect effect) {
            throw new AssertionError("not executed by registry tests");
        }

        @Override
        public void remove(ServerPlayer player, ResolvedNodeBehaviorEffect effect) {
            throw new AssertionError("not executed by registry tests");
        }
    }
}
