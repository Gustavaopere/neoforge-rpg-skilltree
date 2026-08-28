package dev.gustavopere.rpgskilltree.runtime;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Small internal observer boundary for confirmed canonical progression mutations. */
public final class ProgressionMutationEvents {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressionMutationEvents.class);
    private static final CopyOnWriteArrayList<Consumer<ProgressionMutationEvent>> LISTENERS =
        new CopyOnWriteArrayList<>();

    private ProgressionMutationEvents() {}

    public static AutoCloseable subscribe(Consumer<ProgressionMutationEvent> listener) {
        Objects.requireNonNull(listener, "listener");
        LISTENERS.add(listener);
        return () -> LISTENERS.remove(listener);
    }

    static void publish(ProgressionMutationEvent event) {
        Objects.requireNonNull(event, "event");
        for (Consumer<ProgressionMutationEvent> listener : LISTENERS) {
            try {
                listener.accept(event);
            } catch (RuntimeException failure) {
                LOGGER.error("Progression mutation listener failed after committed state change", failure);
            }
        }
    }
}
