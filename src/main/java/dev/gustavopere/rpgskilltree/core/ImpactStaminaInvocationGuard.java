package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/** Per-thread provider invocation stack used only to deduplicate the narrow P-0035 commit callback. */
public final class ImpactStaminaInvocationGuard {
    private static final ThreadLocal<Deque<Scope>> SCOPES = ThreadLocal.withInitial(ArrayDeque::new);

    private ImpactStaminaInvocationGuard() {}

    public static Scope open(Object source, Object victim) {
        Scope scope = new Scope(Objects.requireNonNull(source), Objects.requireNonNull(victim));
        SCOPES.get().push(scope);
        return scope;
    }

    public static boolean claim(Object source, Object victim) {
        Scope current = SCOPES.get().peek();
        if (current == null || current.closed || current.claimed) return false;
        if (current.source != source || current.victim != victim) return false;
        current.claimed = true;
        return true;
    }

    public static final class Scope implements AutoCloseable {
        private final Object source;
        private final Object victim;
        private boolean claimed;
        private boolean closed;

        private Scope(Object source, Object victim) {
            this.source = source;
            this.victim = victim;
        }

        @Override
        public void close() {
            if (closed) return;
            Deque<Scope> scopes = SCOPES.get();
            if (scopes.peek() != this) {
                throw new IllegalStateException("impact-stamina scopes must close in LIFO order");
            }
            scopes.pop();
            closed = true;
            if (scopes.isEmpty()) SCOPES.remove();
        }
    }
}
