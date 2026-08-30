package dev.gustavopere.rpgskilltree.runtime.effects;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

/** Explicit registry for behavioral node-effect providers, including optional-mod handlers. */
public final class NodeBehaviorHandlerRegistry {
    private final Map<ResourceLocation, NodeBehaviorHandler> handlers = new LinkedHashMap<>();

    public synchronized void register(ResourceLocation id, NodeBehaviorHandler handler) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(handler, "handler");
        if (handlers.putIfAbsent(id, handler) != null) {
            throw new IllegalArgumentException("duplicate behavior handler id: " + id);
        }
    }

    public synchronized Optional<NodeBehaviorHandler> resolveAvailable(ResourceLocation id) {
        Objects.requireNonNull(id, "id");
        NodeBehaviorHandler handler = handlers.get(id);
        if (handler == null || !handler.available()) return Optional.empty();
        return Optional.of(handler);
    }
}
