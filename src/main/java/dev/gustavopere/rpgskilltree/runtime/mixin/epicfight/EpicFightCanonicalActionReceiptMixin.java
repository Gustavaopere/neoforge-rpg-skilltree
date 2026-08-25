package dev.gustavopere.rpgskilltree.runtime.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightCombatPerkHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightExactStaminaReceiptBridge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Binds the exact stamina playback to the same canonical action already used by combat hooks. */
@Mixin(value = EpicFightCombatPerkHooks.class, remap = false)
public abstract class EpicFightCanonicalActionReceiptMixin {
    @WrapMethod(method = "actionForPre")
    private static CanonicalActionIdentity rpgskilltree$bindReceiptToCanonicalAction(
        ServerPlayer player,
        LivingEntity target,
        EpicFightDamageSource source,
        long nowMillis,
        Operation<CanonicalActionIdentity> original
    ) {
        var alreadyBound = EpicFightExactStaminaReceiptBridge.boundActionForDamage(player, source, nowMillis);
        if (alreadyBound.isPresent()) {
            return alreadyBound.get().withSource("epicfight:damage_pre");
        }

        CanonicalActionIdentity action = original.call(player, target, source, nowMillis);
        EpicFightExactStaminaReceiptBridge.bindDamageAction(player, source, action, nowMillis);
        return action;
    }
}
