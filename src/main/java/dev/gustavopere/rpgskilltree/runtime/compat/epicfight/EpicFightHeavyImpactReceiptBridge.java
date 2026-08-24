package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptCorrelation;
import dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptCorrelation.ImpactKind;
import dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptCorrelation.Receipt;
import dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptWindow;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.ApplyStunEvent;
import yesman.epicfight.api.event.types.entity.TakeDamageEvent;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

/**
 * Server-authoritative Epic Fight adapter for causal heavy-impact receipts.
 *
 * <p>A receipt exists only inside the same synchronous TAKE_DAMAGE_POST dispatch whose operation opened at
 * TAKE_DAMAGE_PRE and observed exactly one APPLY_STUN for the same victim. There is deliberately no temporal
 * lookup, "next hit" lookup, last-stun cache, damage threshold, or impact-number threshold.
 */
public final class EpicFightHeavyImpactReceiptBridge {
    public static final String SUPPORTED_EPIC_FIGHT_RELEASE = "21.17.3.1-mc1.21.1-neoforge";
    public static final String SUPPORTED_EPIC_FIGHT_VERSION = "21.17.3.1";
    public static final String SUPPORTED_EPIC_FIGHT_VERSION_ID = "8HHhJt6i";
    public static final String SUPPORTED_EPIC_FIGHT_SHA256 =
        "8b882554cf10086398340fbdc741819ee72a801a3adce516c7f4768326a39526";

    private static final String PRE_SUBSCRIBER = "rpgskilltree:heavy_impact/pre";
    private static final String APPLY_SUBSCRIBER = "rpgskilltree:heavy_impact/apply_stun";
    private static final String POST_FINALIZE_SUBSCRIBER = "rpgskilltree:heavy_impact/post_finalize";
    private static final String POST_CLEANUP_SUBSCRIBER = "rpgskilltree:heavy_impact/post_cleanup";

    private static final HeavyImpactReceiptCorrelation CORRELATION = new HeavyImpactReceiptCorrelation();
    private static final ThreadLocal<Deque<HeavyImpactReceiptWindow>> POST_WINDOWS =
        ThreadLocal.withInitial(ArrayDeque::new);
    private static boolean registered;

    private EpicFightHeavyImpactReceiptBridge() {}

    public static boolean isSupportedInstalledVersion() {
        return ModList.get().getModContainerById("epicfight")
            .map(container -> SUPPORTED_EPIC_FIGHT_VERSION.equals(container.getModInfo().getVersion().toString()))
            .orElse(false);
    }

    public static synchronized void register() {
        if (registered) return;
        if (!isSupportedInstalledVersion()) {
            throw new IllegalStateException(
                "Heavy-impact receipt bridge only supports Epic Fight release " + SUPPORTED_EPIC_FIGHT_RELEASE
                    + " (packaged mod version " + SUPPORTED_EPIC_FIGHT_VERSION + ')'
            );
        }

        EpicFightEventHooks.Entity.TAKE_DAMAGE_PRE.registerEvent(
            EpicFightHeavyImpactReceiptBridge::onTakeDamagePre,
            PRE_SUBSCRIBER,
            Integer.MAX_VALUE
        );
        EpicFightEventHooks.Entity.APPLY_STUN.registerEvent(
            EpicFightHeavyImpactReceiptBridge::onApplyStun,
            APPLY_SUBSCRIBER,
            Integer.MAX_VALUE
        );
        EpicFightEventHooks.Entity.TAKE_DAMAGE_POST.registerEvent(
            EpicFightHeavyImpactReceiptBridge::onTakeDamagePostFinalize,
            POST_FINALIZE_SUBSCRIBER,
            Integer.MAX_VALUE
        );
        EpicFightEventHooks.Entity.TAKE_DAMAGE_POST.registerEvent(
            EpicFightHeavyImpactReceiptBridge::onTakeDamagePostCleanup,
            POST_CLEANUP_SUBSCRIBER,
            Integer.MIN_VALUE
        );
        registered = true;
    }

    /**
     * Returns confirmed heavy-impact evidence without consuming it, but only during this exact POST dispatch.
     */
    public static Optional<Receipt> peekConfirmedHeavyImpact(TakeDamageEvent.Post event) {
        Objects.requireNonNull(event, "event");
        HeavyImpactReceiptWindow window = currentPostWindow();
        if (window == null) return Optional.empty();
        return window.peek(event.getEntityPatch().getOriginal(), event.getDamageSource());
    }

    /**
     * Claims confirmed evidence at most once per consumer id during this exact POST dispatch.
     * Different consumers remain independent for the same receipt/operation.
     */
    public static Optional<Receipt> claimConfirmedHeavyImpact(TakeDamageEvent.Post event, String consumerId) {
        Objects.requireNonNull(event, "event");
        HeavyImpactReceiptWindow window = currentPostWindow();
        if (window == null) return Optional.empty();
        return window.claim(event.getEntityPatch().getOriginal(), event.getDamageSource(), consumerId);
    }

    /** Clears every transient operation/window on the current logical-server thread. */
    public static void clearTransientState() {
        CORRELATION.clearThread();
        closeAllPostWindows();
    }

    private static void onTakeDamagePre(TakeDamageEvent.Pre event) {
        LivingEntity victim = event.getEntityPatch().getOriginal();
        if (victim.level().isClientSide()) return;
        if (!(event.getDamageSource() instanceof EpicFightDamageSource source)) return;
        if (source.getEntity() == null) return;

        CORRELATION.begin(victim.getUUID().toString(), victim, source);
    }

    private static void onApplyStun(ApplyStunEvent event) {
        LivingEntity victim = event.getEntityPatch().getOriginal();
        if (victim.level().isClientSide()) return;
        CORRELATION.recordFinalImpact(victim, normalizeStunType(event.getStunType()));
    }

    private static void onTakeDamagePostFinalize(TakeDamageEvent.Post event) {
        LivingEntity victim = event.getEntityPatch().getOriginal();
        if (victim.level().isClientSide()) return;
        if (!(event.getDamageSource() instanceof EpicFightDamageSource source)) return;
        if (source.getEntity() == null) return;

        Optional<Receipt> receipt = CORRELATION.complete(victim, source);
        POST_WINDOWS.get().push(new HeavyImpactReceiptWindow(victim, source, receipt));
    }

    private static void onTakeDamagePostCleanup(TakeDamageEvent.Post event) {
        Deque<HeavyImpactReceiptWindow> stack = POST_WINDOWS.get();
        HeavyImpactReceiptWindow window = stack.peek();
        Object victim = event.getEntityPatch().getOriginal();
        Object source = event.getDamageSource();

        if (window != null && window.matches(victim, source)) {
            stack.pop().close();
        } else {
            // Any non-LIFO or identity-mismatched cleanup invalidates the complete dispatch stack. This prevents
            // an outer receipt from being reused if provider nesting ever diverges from the audited contract.
            while (!stack.isEmpty()) stack.pop().close();
        }
        if (stack.isEmpty()) POST_WINDOWS.remove();
    }

    static ImpactKind normalizeStunType(StunType stunType) {
        if (stunType == null) return ImpactKind.LIGHT;
        return switch (stunType) {
            case LONG -> ImpactKind.LONG_STUN;
            case KNOCKDOWN -> ImpactKind.KNOCKDOWN;
            case NEUTRALIZE -> ImpactKind.NEUTRALIZE;
            default -> ImpactKind.LIGHT;
        };
    }

    private static HeavyImpactReceiptWindow currentPostWindow() {
        Deque<HeavyImpactReceiptWindow> stack = POST_WINDOWS.get();
        HeavyImpactReceiptWindow window = stack.peek();
        if (window == null) POST_WINDOWS.remove();
        return window;
    }

    private static void closeAllPostWindows() {
        Deque<HeavyImpactReceiptWindow> stack = POST_WINDOWS.get();
        while (!stack.isEmpty()) stack.pop().close();
        POST_WINDOWS.remove();
    }
}
