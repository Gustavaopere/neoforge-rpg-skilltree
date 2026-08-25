package dev.gustavopere.rpgskilltree.runtime.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import dev.gustavopere.rpgskilltree.core.ImpactStaminaInvocationGuard;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightImpactStaminaBridge;
import java.util.function.Consumer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.event.impl.VanillaEntityEventHooks;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

/** Scope-only wrapper plus the single approved post-cancel/pre-shield commit hook. */
@Mixin(value = VanillaEntityEventHooks.class, remap = false)
public abstract class VanillaEntityImpactStaminaMixin {
    private static final String PRE_METHOD =
        "onCalculateDamagePre(Lnet/minecraft/world/entity/LivingEntity;" +
        "Lnet/minecraft/world/damagesource/DamageSource;FLjava/util/function/Consumer;)V";
    private static final String DAMAGE_STUN_SHIELD =
        "Lyesman/epicfight/world/capabilities/entitypatch/HurtableEntityPatch;damageStunShield(FF)V";

    @WrapMethod(method = PRE_METHOD)
    private static void rpgskilltree$impactTransactionScope(
        LivingEntity hitEntity,
        DamageSource damageSource,
        float amount,
        Consumer<Float> modifiedDamageApplier,
        Operation<Void> original
    ) {
        try (var ignored = ImpactStaminaInvocationGuard.open(damageSource, hitEntity)) {
            original.call(hitEntity, damageSource, amount, modifiedDamageApplier);
        }
    }

    @Inject(
        method = PRE_METHOD,
        at = @At(value = "INVOKE", target = DAMAGE_STUN_SHIELD, shift = At.Shift.BEFORE, remap = false),
        require = 1,
        expect = 1,
        allow = 1,
        remap = false
    )
    private static void rpgskilltree$commitImpactTransaction(
        LivingEntity hitEntity,
        DamageSource damageSource,
        float amount,
        Consumer<Float> modifiedDamageApplier,
        CallbackInfo callback,
        @Local(name = "hitEntityPatchAsHurtable") HurtableEntityPatch<?> hitPatch,
        @Local(name = "stunType") StunType stunType,
        @Local(name = "stunShield") float stunShield,
        @Local(name = "impact") LocalFloatRef impact
    ) {
        EpicFightImpactStaminaBridge.tryCommit(
            hitEntity, damageSource, hitPatch, stunType, stunShield, impact.get(), impact::set
        );
    }
}
