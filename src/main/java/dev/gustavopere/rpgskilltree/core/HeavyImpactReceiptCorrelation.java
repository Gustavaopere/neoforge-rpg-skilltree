package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

/**
 * Correlates one synchronous incoming-damage operation with the final impact outcome reported by a provider.
 *
 * <p>The correlation is deliberately operation-scoped. It never searches by time, by the next hit, or by the
 * last observed stun. Nested damage is represented by a stack on the current thread. Any identity mismatch or
 * more than one physical final-impact observation poisons only the active scope and fails closed.
 */
public final class HeavyImpactReceiptCorrelation {
    public enum ImpactKind {
        LONG_STUN(true),
        KNOCKDOWN(true),
        NEUTRALIZE(true),
        LIGHT(false);

        private final boolean heavy;

        ImpactKind(boolean heavy) {
            this.heavy = heavy;
        }

        public boolean heavy() {
            return this.heavy;
        }
    }

    public record Receipt(String actorId, ImpactKind kind) {
        public Receipt {
            actorId = requireText(actorId, "actorId");
            kind = Objects.requireNonNull(kind, "kind");
            if (!kind.heavy()) {
                throw new IllegalArgumentException("receipt kind must be heavy");
            }
        }
    }

    private final ThreadLocal<Deque<Scope>> scopes = ThreadLocal.withInitial(ArrayDeque::new);

    public void begin(String actorId, Object victimIdentity, Object damageSourceIdentity) {
        scopes.get().push(new Scope(
            requireText(actorId, "actorId"),
            Objects.requireNonNull(victimIdentity, "victimIdentity"),
            Objects.requireNonNull(damageSourceIdentity, "damageSourceIdentity")
        ));
    }

    /**
     * Records the provider's final applied impact classification for the currently active operation.
     * A second physical observation or a victim mismatch makes that operation ambiguous.
     */
    public void recordFinalImpact(Object victimIdentity, ImpactKind kind) {
        Objects.requireNonNull(victimIdentity, "victimIdentity");
        Objects.requireNonNull(kind, "kind");

        Deque<Scope> stack = scopes.get();
        Scope scope = stack.peek();
        if (scope == null) {
            cleanupEmpty(stack);
            return;
        }
        if (scope.victimIdentity != victimIdentity || scope.observed) {
            scope.ambiguous = true;
            return;
        }
        scope.observed = true;
        scope.kind = kind;
    }

    /**
     * Completes exactly the active operation. The scope is consumed regardless of success so a poisoned or
     * mismatched operation cannot leak evidence into a later hit.
     */
    public Optional<Receipt> complete(Object victimIdentity, Object damageSourceIdentity) {
        Objects.requireNonNull(victimIdentity, "victimIdentity");
        Objects.requireNonNull(damageSourceIdentity, "damageSourceIdentity");

        Deque<Scope> stack = scopes.get();
        Scope scope = stack.poll();
        cleanupEmpty(stack);
        if (scope == null) return Optional.empty();
        if (scope.victimIdentity != victimIdentity || scope.damageSourceIdentity != damageSourceIdentity) {
            return Optional.empty();
        }
        if (scope.ambiguous || !scope.observed || scope.kind == null || !scope.kind.heavy()) {
            return Optional.empty();
        }
        return Optional.of(new Receipt(scope.actorId, scope.kind));
    }

    public int depth() {
        Deque<Scope> stack = scopes.get();
        int depth = stack.size();
        cleanupEmpty(stack);
        return depth;
    }

    /** Clears all transient operation state owned by the current thread. */
    public void clearThread() {
        scopes.remove();
    }

    private void cleanupEmpty(Deque<Scope> stack) {
        if (stack.isEmpty()) scopes.remove();
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }

    private static final class Scope {
        private final String actorId;
        private final Object victimIdentity;
        private final Object damageSourceIdentity;
        private boolean observed;
        private boolean ambiguous;
        private ImpactKind kind;

        private Scope(String actorId, Object victimIdentity, Object damageSourceIdentity) {
            this.actorId = actorId;
            this.victimIdentity = victimIdentity;
            this.damageSourceIdentity = damageSourceIdentity;
        }
    }
}
