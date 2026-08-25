package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.ImpactStaminaConversionPolicy;
import dev.gustavopere.rpgskilltree.core.ImpactStaminaConversionPolicy.PressureClass;
import dev.gustavopere.rpgskilltree.core.ImpactStaminaInvocationGuard;
import dev.gustavopere.rpgskilltree.core.ImpactStaminaTransaction;
import java.util.Objects;
import java.util.OptionalDouble;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

/** Version-gated generic infrastructure; inert until a future consumer explicitly registers a request source. */
public final class EpicFightImpactStaminaBridge {
    private static RegisteredRequestSource registeredRequestSource;

    private EpicFightImpactStaminaBridge() {}

    @FunctionalInterface
    public interface RequestSource {
        OptionalDouble requestedFraction(
            ServerPlayer victim,
            EpicFightDamageSource source,
            StunType effectiveStunType,
            float impact,
            float stunShieldSnapshot
        );
    }

    @FunctionalInterface
    public interface ImpactSetter {
        void set(float reducedImpact);
    }

    public static synchronized Registration registerRequestSource(String ownerId, RequestSource source) {
        Objects.requireNonNull(ownerId);
        Objects.requireNonNull(source);
        if (ownerId.isBlank()) throw new IllegalArgumentException("ownerId must not be blank");
        if (registeredRequestSource != null) {
            throw new IllegalStateException("impact-stamina request source already registered by " + registeredRequestSource.ownerId());
        }
        registeredRequestSource = new RegisteredRequestSource(ownerId, source);
        return new Registration(ownerId);
    }

    public static boolean tryCommit(
        LivingEntity hitEntity,
        DamageSource damageSource,
        HurtableEntityPatch<?> hitPatch,
        StunType effectiveStunType,
        float stunShieldSnapshot,
        float impact,
        ImpactSetter impactSetter
    ) {
        Objects.requireNonNull(hitEntity);
        Objects.requireNonNull(damageSource);
        Objects.requireNonNull(hitPatch);
        Objects.requireNonNull(impactSetter);

        RegisteredRequestSource registration;
        synchronized (EpicFightImpactStaminaBridge.class) {
            registration = registeredRequestSource;
        }
        if (registration == null) return false;
        if (!(damageSource instanceof EpicFightDamageSource epicFightSource)) return false;
        if (!(hitPatch instanceof ServerPlayerPatch victimPatch)) return false;
        if (!(hitEntity instanceof ServerPlayer victim)) return false;
        if (victimPatch.getOriginal() != victim || victimPatch.isLogicalClient()) return false;
        if (!ImpactStaminaInvocationGuard.claim(damageSource, hitEntity)) return false;

        OptionalDouble requested;
        try {
            requested = registration.source().requestedFraction(
                victim, epicFightSource, effectiveStunType, impact, stunShieldSnapshot
            );
        } catch (RuntimeException rejected) {
            return false;
        }
        if (requested.isEmpty()) return false;

        var quote = ImpactStaminaConversionPolicy.quote(
            impact, stunShieldSnapshot, pressureClass(effectiveStunType), requested.getAsDouble()
        );
        if (quote.isEmpty()) return false;

        float reducedImpact = (float) quote.orElseThrow().reducedImpact();
        if (!Float.isFinite(reducedImpact) || reducedImpact < 0.0F || reducedImpact >= impact) return false;
        if ((effectiveStunType == StunType.SHORT || effectiveStunType == StunType.LONG)
            && reducedImpact < stunShieldSnapshot) return false;

        // Charge the exact pressure that will actually be removed after float conversion.
        float nativeCost = impact - reducedImpact;
        ImpactStaminaTransaction.NativeStaminaAccess nativeStamina = new ImpactStaminaTransaction.NativeStaminaAccess() {
            @Override public float getStamina() { return victimPatch.getStamina(); }
            @Override public boolean hasStamina(float amount) { return victimPatch.hasStamina(amount); }
            @Override public void setStamina(float value) { victimPatch.setStamina(value); }
            @Override public void resetActionTick() { victimPatch.resetActionTick(); }
        };

        return ImpactStaminaTransaction.tryDebitExactNativeStamina(
            nativeCost,
            nativeStamina,
            () -> impactSetter.set(reducedImpact)
        );
    }

    private static PressureClass pressureClass(StunType stunType) {
        if (stunType == null) return null;
        return switch (stunType) {
            case NONE -> PressureClass.NONE;
            case SHORT -> PressureClass.SHORT;
            case LONG -> PressureClass.LONG;
            case HOLD -> PressureClass.HOLD;
            case KNOCKDOWN -> PressureClass.KNOCKDOWN;
            case NEUTRALIZE -> PressureClass.NEUTRALIZE;
            case FALL -> PressureClass.FALL;
        };
    }

    private static synchronized void unregister(String ownerId) {
        if (registeredRequestSource != null && registeredRequestSource.ownerId().equals(ownerId)) {
            registeredRequestSource = null;
        }
    }

    public static final class Registration implements AutoCloseable {
        private final String ownerId;
        private boolean closed;
        private Registration(String ownerId) { this.ownerId = ownerId; }
        @Override public void close() {
            if (closed) return;
            closed = true;
            EpicFightImpactStaminaBridge.unregister(ownerId);
        }
    }

    private record RegisteredRequestSource(String ownerId, RequestSource source) {}
}
