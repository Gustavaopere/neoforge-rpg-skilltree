package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.CanonicalStaminaService;
import dev.gustavopere.rpgskilltree.core.ExactStaminaDebitCapture;
import dev.gustavopere.rpgskilltree.core.ExactStaminaReceiptCorrelation;
import dev.gustavopere.rpgskilltree.core.ExactStaminaReceiptCorrelation.BindStatus;
import dev.gustavopere.rpgskilltree.core.ExactStaminaReceiptCorrelation.PlaybackKey;
import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayer;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.animation.AnimationEndEvent;
import yesman.epicfight.api.event.types.animation.StartActionEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/**
 * Exact, causal stamina receipt bridge for the one Epic Fight build whose bytecode is audited in CI.
 *
 * <p>This class never pairs a debit with a future hit by time. A usable receipt requires the same
 * server execution scope to start an action animation; the active playback generation must then be
 * the animation carried by the Epic Fight damage source that owns the canonical action.
 */
public final class EpicFightExactStaminaReceiptBridge {
    /** Distribution release pinned by Modrinth version id and SHA-256 in the build contract. */
    public static final String SUPPORTED_EPIC_FIGHT_RELEASE = "21.17.3.1-mc1.21.1-neoforge";
    /** Version exposed by Epic Fight's packaged NeoForge mod metadata at runtime. */
    public static final String SUPPORTED_EPIC_FIGHT_VERSION = "21.17.3.1";
    public static final String SUPPORTED_EPIC_FIGHT_VERSION_ID = "8HHhJt6i";
    public static final String SUPPORTED_EPIC_FIGHT_SHA256 =
        "8b882554cf10086398340fbdc741819ee72a801a3adce516c7f4768326a39526";

    public static final String CALLSITE_PLAYER_PATCH = "epicfight:PlayerPatch.consumeForSkill";
    public static final String CALLSITE_REQUEST_HOLD = "epicfight:SkillContainer.requestHold";
    public static final String CALLSITE_COMBO_ATTACK = "epicfight:ComboAttacks.executeOnServer";

    private static final long RETENTION_MILLIS = 30_000L;
    private static final int MAX_TRACKED = 4_096;
    private static final String START_ACTION_SUBSCRIBER = "rpgskilltree:exact_stamina/start_action";
    private static final String END_ACTION_SUBSCRIBER = "rpgskilltree:exact_stamina/end_action";

    private static final ExactStaminaReceiptCorrelation CORRELATION =
        new ExactStaminaReceiptCorrelation(RETENTION_MILLIS, MAX_TRACKED);
    private static final AtomicLong EVIDENCE_SEQUENCE = new AtomicLong();
    private static final Map<ActorAnimationKey, PlaybackKey> ACTIVE_PLAYBACKS = new HashMap<>();
    private static boolean registered;

    private EpicFightExactStaminaReceiptBridge() {}

    public static boolean isSupportedInstalledVersion() {
        return ModList.get().getModContainerById("epicfight")
            .map(container -> SUPPORTED_EPIC_FIGHT_VERSION.equals(container.getModInfo().getVersion().toString()))
            .orElse(false);
    }

    public static synchronized void register() {
        if (registered) return;
        if (!isSupportedInstalledVersion()) {
            throw new IllegalStateException(
                "Exact stamina receipt bridge only supports Epic Fight release " + SUPPORTED_EPIC_FIGHT_RELEASE
                    + " (packaged mod version " + SUPPORTED_EPIC_FIGHT_VERSION + ')'
            );
        }
        EpicFightEventHooks.Animation.START_ACTION.registerEvent(
            EpicFightExactStaminaReceiptBridge::onStartAction,
            START_ACTION_SUBSCRIBER
        );
        EpicFightEventHooks.Animation.END.registerEvent(
            EpicFightExactStaminaReceiptBridge::onAnimationEnd,
            END_ACTION_SUBSCRIBER
        );
        registered = true;
    }

    /** Opens the only context in which a physical debit may later become a usable action receipt. */
    public static ExecutionHandle beginExecution(ServerPlayerPatch executor, SkillContainer container) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(container);
        String actorId = actorId(executor);
        Skill skill = container.getSkill();
        String skillId = skill == null
            ? "epicfight:empty_slot/" + container.getSlotId()
            : skill.getRegistryName().toString();
        var token = CORRELATION.beginExecution(actorId, skillId, now(executor));
        return new ExecutionHandle(EpicFightExecutionScope.push(token));
    }

    /** Always close in a finally block around requestCasting/requestHold. */
    public static void endExecution(ExecutionHandle handle, ServerPlayerPatch executor) {
        if (handle == null) return;
        Objects.requireNonNull(executor);
        try {
            CORRELATION.endExecution(handle.scope.token(), now(executor));
        } finally {
            EpicFightExecutionScope.pop(handle.scope);
        }
    }

    /**
     * Runs one ResourceConsumer exactly once, then records the observed stamina delta in the current scope.
     * The receiver identity proves which final Resource was selected after SkillConsumeEvent listeners.
     */
    public static void consume(
        String callSite,
        Skill.Resource.ResourceConsumer resourceConsumer,
        SkillContainer container,
        ServerPlayerPatch executor,
        float attemptedAmount,
        ExactStaminaDebitCapture.Operation original
    ) {
        Objects.requireNonNull(callSite);
        Objects.requireNonNull(resourceConsumer);
        Objects.requireNonNull(container);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(original);

        double before = executor.getStamina();
        boolean serverAuthoritative = !executor.isLogicalClient() && executor.getOriginal() instanceof ServerPlayer;
        boolean finalResourceIsStamina = resourceConsumer == Skill.Resource.STAMINA.consumer;

        Optional<ExactStaminaDebitCapture.Capture> captured = ExactStaminaDebitCapture.aroundConsumer(
            serverAuthoritative,
            finalResourceIsStamina,
            before,
            attemptedAmount,
            original,
            executor::getStamina
        );
        if (captured.isEmpty()) return;

        String actorId = actorId(executor);
        Optional<EpicFightExecutionScope.Scope> current = EpicFightExecutionScope.current(actorId);
        if (current.isEmpty()) {
            // Exact physical debit was observed, but causality is unprovable. It is deliberately discarded.
            return;
        }

        ExactStaminaDebitCapture.Capture debit = captured.get();
        long evidenceSequence = EVIDENCE_SEQUENCE.incrementAndGet();
        String evidenceId = "epicfight:stamina/" + current.get().token().executionId()
            + '/' + Long.toUnsignedString(evidenceSequence, 36);
        CORRELATION.recordDebit(
            current.get().token(),
            new ExactStaminaReceiptCorrelation.DebitEvidence(
                evidenceId,
                debit.actualDebit(),
                debit.attemptedAmount(),
                callSite
            ),
            now(executor)
        );
    }

    /** Existing combat correlation may reuse this action for another target of the same playback. */
    public static Optional<CanonicalActionIdentity> boundActionForDamage(
        ServerPlayer player,
        EpicFightDamageSource source,
        long nowMillis
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(source);
        Optional<String> animationId = animationId(source.getAnimation());
        if (animationId.isEmpty()) return Optional.empty();
        return CORRELATION.boundAction(player.getUUID().toString(), animationId.get(), nowMillis);
    }

    /**
     * Binds the first canonical damage action to the active playback. Only a CORRELATED result is
     * published into CanonicalStaminaService; ambiguity and uncorrelated states remain fail-closed.
     */
    public static void bindDamageAction(
        ServerPlayer player,
        EpicFightDamageSource source,
        CanonicalActionIdentity action,
        long nowMillis
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(source);
        Objects.requireNonNull(action);
        Optional<String> animationId = animationId(source.getAnimation());
        if (animationId.isEmpty()) return;

        var result = CORRELATION.bindCanonicalAction(
            player.getUUID().toString(),
            animationId.get(),
            action,
            nowMillis
        );
        if (result.status() != BindStatus.CORRELATED) return;

        var debit = result.correlatedDebit().orElseThrow();
        CombatPerkRuntimeState.state().staminaService().observe(
            new CanonicalStaminaService.CostObservation(
                action,
                true,
                eligible(player),
                debit.actualDebit(),
                debit.evidenceId(),
                CanonicalStaminaService.ObservationStage.POST_CONSUME_CONFIRMED
            ),
            nowMillis
        );
    }

    /** Public read-only API for PR #8: inspect the exact receipt for this canonical action. */
    public static Optional<CanonicalStaminaService.ExactCostReceipt> receipt(
        CanonicalActionIdentity action,
        long nowMillis
    ) {
        return CombatPerkRuntimeState.state().staminaService().receipt(action, nowMillis);
    }

    /** Public claiming API for PR #8: canonical once-per-action/consumer refund calculation. */
    public static OptionalDouble claimRefundAmount(
        CanonicalActionIdentity action,
        String consumerId,
        double fraction,
        long nowMillis
    ) {
        return CombatPerkRuntimeState.state().staminaService()
            .refundAmount(action, consumerId, fraction, nowMillis);
    }

    public static synchronized void clear(ServerPlayer player) {
        Objects.requireNonNull(player);
        String actorId = player.getUUID().toString();
        EpicFightExecutionScope.clearActor(actorId);
        CORRELATION.clearActor(actorId);
        ACTIVE_PLAYBACKS.keySet().removeIf(key -> key.actorId.equals(actorId));
        CombatPerkRuntimeState.state().staminaService().clearActor(actorId);
    }

    private static synchronized void onStartAction(StartActionEvent event) {
        if (!(event.getEntityPatch() instanceof ServerPlayerPatch executor)) return;
        String actorId = actorId(executor);
        Optional<EpicFightExecutionScope.Scope> current = EpicFightExecutionScope.current(actorId);
        if (current.isEmpty()) return;
        Optional<String> animationId = animationId(event.getAnimation());
        if (animationId.isEmpty()) return;

        CORRELATION.startAction(current.get().token(), animationId.get(), now(executor)).ifPresent(playback ->
            ACTIVE_PLAYBACKS.put(new ActorAnimationKey(actorId, animationId.get()), playback)
        );
    }

    private static synchronized void onAnimationEnd(AnimationEndEvent event) {
        if (!(event.getEntityPatch() instanceof ServerPlayerPatch executor)) return;
        String actorId = actorId(executor);
        Optional<String> animationId = animationId(event.getAnimation());
        if (animationId.isEmpty()) return;
        ActorAnimationKey key = new ActorAnimationKey(actorId, animationId.get());
        PlaybackKey playback = ACTIVE_PLAYBACKS.remove(key);
        if (playback == null) return;
        CORRELATION.endAction(actorId, animationId.get(), playback.generation(), now(executor));
    }

    private static Optional<String> animationId(AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null || animation.isEmpty() || animation.registryName() == null) return Optional.empty();
        return Optional.of(animation.registryName().toString());
    }

    private static String actorId(ServerPlayerPatch executor) {
        return executor.getOriginal().getUUID().toString();
    }

    private static long now(ServerPlayerPatch executor) {
        return Math.multiplyExact(executor.getOriginal().level().getGameTime(), 50L);
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    public static final class ExecutionHandle {
        private final EpicFightExecutionScope.Scope scope;

        private ExecutionHandle(EpicFightExecutionScope.Scope scope) {
            this.scope = scope;
        }
    }

    private record ActorAnimationKey(String actorId, String animationId) {}
}
