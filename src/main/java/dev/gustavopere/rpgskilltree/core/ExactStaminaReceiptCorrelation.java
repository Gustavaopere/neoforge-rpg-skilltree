package dev.gustavopere.rpgskilltree.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Pure server-runtime state machine that proves an exact stamina debit belongs to a canonical action.
 *
 * <p>Correlation is explicit: execution -> action animation playback generation -> canonical action.
 * Temporal "next hit" matching is intentionally impossible through this API.
 */
public final class ExactStaminaReceiptCorrelation {
    private final long retentionMillis;
    private final int maxTracked;
    private final Map<String, Long> actorExecutionSequences = new LinkedHashMap<>();
    private final Map<String, Long> actorPlaybackSequences = new LinkedHashMap<>();
    private final LinkedHashMap<String, ExecutionState> executions = new LinkedHashMap<>();
    private final LinkedHashMap<PlaybackKey, PlaybackState> playbacks = new LinkedHashMap<>();
    private final Map<ActorAnimationKey, PlaybackKey> activePlaybacks = new LinkedHashMap<>();

    public ExactStaminaReceiptCorrelation(long retentionMillis, int maxTracked) {
        if (retentionMillis <= 0L) throw new IllegalArgumentException("retentionMillis must be positive");
        if (maxTracked <= 0) throw new IllegalArgumentException("maxTracked must be positive");
        this.retentionMillis = retentionMillis;
        this.maxTracked = maxTracked;
    }

    public synchronized ExecutionToken beginExecution(String actorId, String skillId, long nowMillis) {
        actorId = requireId(actorId, "actorId");
        skillId = requireId(skillId, "skillId");
        requireNow(nowMillis);
        prune(nowMillis);
        makeExecutionRoom();
        long sequence = Math.incrementExact(actorExecutionSequences.getOrDefault(actorId, 0L));
        actorExecutionSequences.put(actorId, sequence);
        String executionId = actorId + '/' + Long.toUnsignedString(nowMillis, 36) + '-' + Long.toUnsignedString(sequence, 36);
        ExecutionToken token = new ExecutionToken(actorId, executionId, skillId);
        executions.put(executionId, new ExecutionState(token, expiresAt(nowMillis)));
        return token;
    }

    public synchronized DebitStatus recordDebit(ExecutionToken token, DebitEvidence evidence, long nowMillis) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(evidence);
        requireNow(nowMillis);
        prune(nowMillis);
        ExecutionState state = executions.get(token.executionId());
        if (state == null || !state.token.equals(token) || state.closed) return DebitStatus.NO_ACTIVE_EXECUTION;

        if (!Double.isFinite(evidence.actualDebit())) return DebitStatus.INVALID_EVIDENCE;
        if (evidence.actualDebit() <= 0.0D) return DebitStatus.IGNORED_NON_POSITIVE;
        if (state.evidenceIds.containsKey(evidence.evidenceId())) return DebitStatus.DUPLICATE_EVIDENCE;

        state.evidenceIds.put(evidence.evidenceId(), Boolean.TRUE);
        if (state.ambiguous) return DebitStatus.AMBIGUOUS_MULTIPLE_DEBITS;
        if (state.debit != null) {
            state.debit = null;
            state.ambiguous = true;
            return DebitStatus.AMBIGUOUS_MULTIPLE_DEBITS;
        }

        state.debit = evidence;
        state.expiresAtMillis = expiresAt(nowMillis);
        return DebitStatus.RECORDED;
    }

    public synchronized Optional<PlaybackKey> startAction(
        ExecutionToken token,
        String animationId,
        long nowMillis
    ) {
        Objects.requireNonNull(token);
        animationId = requireId(animationId, "animationId");
        requireNow(nowMillis);
        prune(nowMillis);
        ExecutionState state = executions.get(token.executionId());
        if (state == null || !state.token.equals(token) || state.closed && state.playback == null) {
            return Optional.empty();
        }

        ActorAnimationKey activeKey = new ActorAnimationKey(token.actorId(), animationId);
        PlaybackKey previous = activePlaybacks.get(activeKey);
        if (previous != null) internalEndAction(previous);

        makePlaybackRoom();
        long generation = Math.incrementExact(actorPlaybackSequences.getOrDefault(token.actorId(), 0L));
        actorPlaybackSequences.put(token.actorId(), generation);
        PlaybackKey playback = new PlaybackKey(token.actorId(), animationId, generation);
        playbacks.put(playback, new PlaybackState(token.executionId(), expiresAt(nowMillis)));
        activePlaybacks.put(activeKey, playback);
        state.playback = playback;
        state.expiresAtMillis = expiresAt(nowMillis);
        return Optional.of(playback);
    }

    public synchronized ExecutionStatus endExecution(ExecutionToken token, long nowMillis) {
        Objects.requireNonNull(token);
        requireNow(nowMillis);
        prune(nowMillis);
        ExecutionState state = executions.get(token.executionId());
        if (state == null || !state.token.equals(token)) return ExecutionStatus.NO_MATCH;
        state.closed = true;
        state.expiresAtMillis = expiresAt(nowMillis);

        if (state.playback != null) {
            if (state.ambiguous) return ExecutionStatus.AMBIGUOUS_MULTIPLE_DEBITS;
            return state.debit == null ? ExecutionStatus.NO_EXACT_DEBIT : ExecutionStatus.PENDING_ACTION;
        }

        executions.remove(token.executionId());
        if (state.ambiguous) return ExecutionStatus.AMBIGUOUS_MULTIPLE_DEBITS;
        if (state.debit != null) return ExecutionStatus.EXACT_DEBIT_UNCORRELATED;
        return ExecutionStatus.NO_EXACT_DEBIT;
    }

    public synchronized Optional<CanonicalActionIdentity> boundAction(
        String actorId,
        String animationId,
        long nowMillis
    ) {
        actorId = requireId(actorId, "actorId");
        animationId = requireId(animationId, "animationId");
        requireNow(nowMillis);
        prune(nowMillis);
        PlaybackKey playback = activePlaybacks.get(new ActorAnimationKey(actorId, animationId));
        if (playback == null) return Optional.empty();
        PlaybackState state = playbacks.get(playback);
        return state == null ? Optional.empty() : Optional.ofNullable(state.action);
    }

    public synchronized BindResult bindCanonicalAction(
        String actorId,
        String animationId,
        CanonicalActionIdentity action,
        long nowMillis
    ) {
        actorId = requireId(actorId, "actorId");
        animationId = requireId(animationId, "animationId");
        Objects.requireNonNull(action);
        requireNow(nowMillis);
        if (!action.actorId().equals(actorId)) {
            throw new IllegalArgumentException("action actor must match actorId");
        }
        prune(nowMillis);

        PlaybackKey playback = activePlaybacks.get(new ActorAnimationKey(actorId, animationId));
        if (playback == null) return BindResult.of(BindStatus.NO_MATCH);
        PlaybackState playbackState = playbacks.get(playback);
        if (playbackState == null) return BindResult.of(BindStatus.NO_MATCH);
        ExecutionState execution = executions.get(playbackState.executionId);
        if (execution == null) return BindResult.of(BindStatus.NO_MATCH);

        if (playbackState.action != null) {
            if (!playbackState.action.sameAction(action)) return BindResult.of(BindStatus.ACTION_CONFLICT);
            CorrelatedDebit existing = correlatedDebit(execution, playbackState, playback);
            return new BindResult(BindStatus.DUPLICATE_ACTION, Optional.ofNullable(existing));
        }
        if (execution.ambiguous) return BindResult.of(BindStatus.AMBIGUOUS_MULTIPLE_DEBITS);
        if (execution.debit == null) return BindResult.of(BindStatus.NO_EXACT_DEBIT);

        playbackState.action = action;
        playbackState.expiresAtMillis = expiresAt(nowMillis);
        execution.expiresAtMillis = expiresAt(nowMillis);
        CorrelatedDebit correlated = correlatedDebit(execution, playbackState, playback);
        return new BindResult(BindStatus.CORRELATED, Optional.of(correlated));
    }

    public synchronized ExecutionStatus endAction(
        String actorId,
        String animationId,
        long generation,
        long nowMillis
    ) {
        actorId = requireId(actorId, "actorId");
        animationId = requireId(animationId, "animationId");
        if (generation <= 0L) throw new IllegalArgumentException("generation must be positive");
        requireNow(nowMillis);
        prune(nowMillis);
        PlaybackKey playback = new PlaybackKey(actorId, animationId, generation);
        PlaybackState state = playbacks.get(playback);
        if (state == null) return ExecutionStatus.NO_MATCH;
        ExecutionState execution = executions.get(state.executionId);
        ExecutionStatus result;
        if (execution == null) {
            result = ExecutionStatus.NO_MATCH;
        } else if (execution.ambiguous) {
            result = ExecutionStatus.AMBIGUOUS_MULTIPLE_DEBITS;
        } else if (execution.debit == null) {
            result = ExecutionStatus.NO_EXACT_DEBIT;
        } else if (state.action == null) {
            result = ExecutionStatus.EXACT_DEBIT_UNCORRELATED;
        } else {
            result = ExecutionStatus.CORRELATED;
        }
        internalEndAction(playback);
        return result;
    }

    public synchronized void clearActor(String actorId) {
        actorId = requireId(actorId, "actorId");
        actorExecutionSequences.remove(actorId);
        actorPlaybackSequences.remove(actorId);
        executions.entrySet().removeIf(entry -> entry.getValue().token.actorId().equals(actorId));
        playbacks.entrySet().removeIf(entry -> entry.getKey().actorId().equals(actorId));
        activePlaybacks.entrySet().removeIf(entry -> entry.getKey().actorId.equals(actorId));
    }

    private CorrelatedDebit correlatedDebit(
        ExecutionState execution,
        PlaybackState playbackState,
        PlaybackKey playback
    ) {
        if (execution.debit == null || playbackState.action == null) return null;
        DebitEvidence debit = execution.debit;
        return new CorrelatedDebit(
            playbackState.action,
            debit.actualDebit(),
            debit.attemptedAmount(),
            debit.evidenceId(),
            debit.callSite(),
            playback,
            execution.token.skillId()
        );
    }

    private void internalEndAction(PlaybackKey playback) {
        PlaybackState playbackState = playbacks.remove(playback);
        activePlaybacks.remove(new ActorAnimationKey(playback.actorId(), playback.animationId()), playback);
        if (playbackState == null) return;
        ExecutionState execution = executions.get(playbackState.executionId);
        if (execution != null && playback.equals(execution.playback)) {
            execution.playback = null;
            if (execution.closed) executions.remove(execution.token.executionId());
        }
    }

    private void prune(long nowMillis) {
        Iterator<Map.Entry<PlaybackKey, PlaybackState>> playbackIterator = playbacks.entrySet().iterator();
        while (playbackIterator.hasNext()) {
            Map.Entry<PlaybackKey, PlaybackState> entry = playbackIterator.next();
            if (entry.getValue().expiresAtMillis <= nowMillis) {
                PlaybackKey key = entry.getKey();
                playbackIterator.remove();
                activePlaybacks.remove(new ActorAnimationKey(key.actorId(), key.animationId()), key);
                ExecutionState execution = executions.get(entry.getValue().executionId);
                if (execution != null && key.equals(execution.playback)) execution.playback = null;
            }
        }
        executions.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private void makeExecutionRoom() {
        while (executions.size() >= maxTracked) {
            Iterator<Map.Entry<String, ExecutionState>> iterator = executions.entrySet().iterator();
            Map.Entry<String, ExecutionState> oldest = iterator.next();
            iterator.remove();
            if (oldest.getValue().playback != null) internalEndAction(oldest.getValue().playback);
        }
    }

    private void makePlaybackRoom() {
        while (playbacks.size() >= maxTracked) {
            PlaybackKey oldest = playbacks.keySet().iterator().next();
            internalEndAction(oldest);
        }
    }

    private long expiresAt(long nowMillis) {
        return Math.addExact(nowMillis, retentionMillis);
    }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    private static String requireId(String value, String name) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }

    public enum DebitStatus {
        RECORDED,
        DUPLICATE_EVIDENCE,
        AMBIGUOUS_MULTIPLE_DEBITS,
        IGNORED_NON_POSITIVE,
        INVALID_EVIDENCE,
        NO_ACTIVE_EXECUTION
    }

    public enum ExecutionStatus {
        PENDING_ACTION,
        CORRELATED,
        EXACT_DEBIT_UNCORRELATED,
        AMBIGUOUS_MULTIPLE_DEBITS,
        NO_EXACT_DEBIT,
        NO_MATCH
    }

    public enum BindStatus {
        CORRELATED,
        DUPLICATE_ACTION,
        AMBIGUOUS_MULTIPLE_DEBITS,
        NO_EXACT_DEBIT,
        NO_MATCH,
        ACTION_CONFLICT
    }

    public record ExecutionToken(String actorId, String executionId, String skillId) {
        public ExecutionToken {
            requireId(actorId, "actorId");
            requireId(executionId, "executionId");
            requireId(skillId, "skillId");
        }
    }

    public record PlaybackKey(String actorId, String animationId, long generation) {
        public PlaybackKey {
            requireId(actorId, "actorId");
            requireId(animationId, "animationId");
            if (generation <= 0L) throw new IllegalArgumentException("generation must be positive");
        }
    }

    public record DebitEvidence(
        String evidenceId,
        double actualDebit,
        double attemptedAmount,
        String callSite
    ) {
        public DebitEvidence {
            requireId(evidenceId, "evidenceId");
            requireId(callSite, "callSite");
        }
    }

    public record CorrelatedDebit(
        CanonicalActionIdentity action,
        double actualDebit,
        double attemptedAmount,
        String evidenceId,
        String callSite,
        PlaybackKey playback,
        String skillId
    ) {
        public CorrelatedDebit {
            Objects.requireNonNull(action);
            Objects.requireNonNull(playback);
            requireId(evidenceId, "evidenceId");
            requireId(callSite, "callSite");
            requireId(skillId, "skillId");
            if (!Double.isFinite(actualDebit) || actualDebit <= 0.0D) {
                throw new IllegalArgumentException("actualDebit must be finite and positive");
            }
        }
    }

    public record BindResult(BindStatus status, Optional<CorrelatedDebit> correlatedDebit) {
        public BindResult {
            Objects.requireNonNull(status);
            Objects.requireNonNull(correlatedDebit);
        }

        static BindResult of(BindStatus status) {
            return new BindResult(status, Optional.empty());
        }
    }

    private record ActorAnimationKey(String actorId, String animationId) {}

    private static final class ExecutionState {
        final ExecutionToken token;
        final Map<String, Boolean> evidenceIds = new LinkedHashMap<>();
        long expiresAtMillis;
        DebitEvidence debit;
        PlaybackKey playback;
        boolean ambiguous;
        boolean closed;

        ExecutionState(ExecutionToken token, long expiresAtMillis) {
            this.token = token;
            this.expiresAtMillis = expiresAtMillis;
        }
    }

    private static final class PlaybackState {
        final String executionId;
        long expiresAtMillis;
        CanonicalActionIdentity action;

        PlaybackState(String executionId, long expiresAtMillis) {
            this.executionId = executionId;
            this.expiresAtMillis = expiresAtMillis;
        }
    }
}
