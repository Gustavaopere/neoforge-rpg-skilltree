package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptCorrelation;
import java.lang.reflect.Method;
import java.util.Optional;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import yesman.epicfight.api.event.types.entity.TakeDamageEvent;
import yesman.epicfight.world.damagesource.StunType;

/** Isolated provider contract for P-0002; intentionally does not require the general test suite. */
public final class EpicFightHeavyImpactProviderContractTest {
    public static void main(String[] args) throws Exception {
        Class<?> bridge = Class.forName(
            "dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightHeavyImpactReceiptBridge"
        );
        bridge.getMethod("register");
        bridge.getMethod("peekConfirmedHeavyImpact", TakeDamageEvent.Post.class);
        bridge.getMethod("claimConfirmedHeavyImpact", TakeDamageEvent.Post.class, String.class);
        bridge.getMethod("clearTransientState");

        Method normalize = bridge.getDeclaredMethod("normalizeStunType", StunType.class);
        normalize.setAccessible(true);
        require(normalize.invoke(null, StunType.LONG) == HeavyImpactReceiptCorrelation.ImpactKind.LONG_STUN,
            "LONG must normalize to LONG_STUN");
        require(normalize.invoke(null, StunType.KNOCKDOWN) == HeavyImpactReceiptCorrelation.ImpactKind.KNOCKDOWN,
            "KNOCKDOWN must remain heavy");
        require(normalize.invoke(null, StunType.NEUTRALIZE) == HeavyImpactReceiptCorrelation.ImpactKind.NEUTRALIZE,
            "NEUTRALIZE must remain heavy");
        require(normalize.invoke(null, StunType.SHORT) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "SHORT must fail closed");
        require(normalize.invoke(null, StunType.HOLD) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "raw HOLD must fail closed; provider normally resolves it to SHORT before APPLY_STUN");
        require(normalize.invoke(null, StunType.NONE) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "NONE must fail closed");
        require(normalize.invoke(null, StunType.FALL) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "FALL must not be treated as combat heavy impact");

        require(Optional.class.isAssignableFrom(
                bridge.getMethod("peekConfirmedHeavyImpact", TakeDamageEvent.Post.class).getReturnType()),
            "peek API must return Optional receipt evidence");
        require(Optional.class.isAssignableFrom(
                bridge.getMethod("claimConfirmedHeavyImpact", TakeDamageEvent.Post.class, String.class).getReturnType()),
            "claim API must return Optional receipt evidence");

        Class<?> lifecycle = Class.forName(
            "dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightHeavyImpactReceiptLifecycleEvents"
        );
        lifecycle.getMethod("onLogin", PlayerEvent.PlayerLoggedInEvent.class);
        lifecycle.getMethod("onLogout", PlayerEvent.PlayerLoggedOutEvent.class);
        lifecycle.getMethod("onRespawn", PlayerEvent.PlayerRespawnEvent.class);
        lifecycle.getMethod("onDeath", LivingDeathEvent.class);
        lifecycle.getMethod("onServerTick", ServerTickEvent.Post.class);

        System.out.println("EpicFightHeavyImpactProviderContractTest: PASS");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
