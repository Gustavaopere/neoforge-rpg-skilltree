package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Exact native-stamina debit primitive used before committing a captured impact mutation. */
public final class ImpactStaminaTransaction {
    private ImpactStaminaTransaction() {}

    public interface NativeStaminaAccess {
        float getStamina();
        boolean hasStamina(float amount);
        void setStamina(float value);
        void resetActionTick();
    }

    public static boolean tryDebitExactNativeStamina(
        float cost,
        NativeStaminaAccess stamina,
        Runnable mutateImpact
    ) {
        Objects.requireNonNull(stamina);
        Objects.requireNonNull(mutateImpact);

        if (!Float.isFinite(cost) || cost <= 0.0F) return false;

        float before = stamina.getStamina();
        if (!Float.isFinite(before) || before < 0.0F) return false;
        if (!stamina.hasStamina(cost) || before < cost) return false;

        stamina.resetActionTick();
        stamina.setStamina(before - cost);
        mutateImpact.run();
        return true;
    }
}
