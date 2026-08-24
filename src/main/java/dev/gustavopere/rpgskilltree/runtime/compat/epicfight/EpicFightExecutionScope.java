package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.ExactStaminaReceiptCorrelation.ExecutionToken;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

/** Thread-local server execution stack. It never guesses a scope from player timing. */
final class EpicFightExecutionScope {
    private static final ThreadLocal<Deque<Scope>> ACTIVE = ThreadLocal.withInitial(ArrayDeque::new);

    private EpicFightExecutionScope() {}

    static Scope push(ExecutionToken token) {
        Objects.requireNonNull(token);
        Scope scope = new Scope(token);
        ACTIVE.get().push(scope);
        return scope;
    }

    static Optional<Scope> current(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        for (Scope scope : ACTIVE.get()) {
            if (scope.token.actorId().equals(actorId)) return Optional.of(scope);
        }
        return Optional.empty();
    }

    static void pop(Scope expected) {
        Objects.requireNonNull(expected);
        Deque<Scope> stack = ACTIVE.get();
        if (stack.isEmpty() || stack.peek() != expected) {
            stack.remove(expected);
        } else {
            stack.pop();
        }
        if (stack.isEmpty()) ACTIVE.remove();
    }

    static void clearActor(String actorId) {
        Objects.requireNonNull(actorId);
        Deque<Scope> stack = ACTIVE.get();
        Iterator<Scope> iterator = stack.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().token.actorId().equals(actorId)) iterator.remove();
        }
        if (stack.isEmpty()) ACTIVE.remove();
    }

    static final class Scope {
        private final ExecutionToken token;

        private Scope(ExecutionToken token) {
            this.token = token;
        }

        ExecutionToken token() {
            return token;
        }
    }
}
