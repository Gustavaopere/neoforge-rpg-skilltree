package dev.gustavopere.rpgskilltree.runtime.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightExactStaminaReceiptBridge;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

/** Owns the cast execution scope and the one audited direct consumer in requestHold. */
@Mixin(value = SkillContainer.class, remap = false)
public abstract class SkillContainerStaminaReceiptMixin {
    private static final String RESOURCE_CONSUMER_TARGET =
        "Lyesman/epicfight/skill/Skill$Resource$ResourceConsumer;consume(" +
        "Lyesman/epicfight/skill/SkillContainer;" +
        "Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;F)V";

    @WrapMethod(method = "requestCasting(Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;Lnet/minecraft/nbt/CompoundTag;)Z")
    private boolean rpgskilltree$scopeCasting(
        ServerPlayerPatch executor,
        CompoundTag args,
        Operation<Boolean> original
    ) {
        SkillContainer self = (SkillContainer)(Object)this;
        var handle = EpicFightExactStaminaReceiptBridge.beginExecution(executor, self);
        try {
            return original.call(executor, args);
        } finally {
            EpicFightExactStaminaReceiptBridge.endExecution(handle, executor);
        }
    }

    @WrapMethod(method = "requestHold(Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;Lnet/minecraft/nbt/CompoundTag;)Z")
    private boolean rpgskilltree$scopeHold(
        ServerPlayerPatch executor,
        CompoundTag args,
        Operation<Boolean> original
    ) {
        SkillContainer self = (SkillContainer)(Object)this;
        var handle = EpicFightExactStaminaReceiptBridge.beginExecution(executor, self);
        try {
            return original.call(executor, args);
        } finally {
            EpicFightExactStaminaReceiptBridge.endExecution(handle, executor);
        }
    }

    @WrapOperation(
        method = "requestHold(Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;Lnet/minecraft/nbt/CompoundTag;)Z",
        at = @At(value = "INVOKE", target = RESOURCE_CONSUMER_TARGET, remap = false),
        require = 1,
        expect = 1,
        allow = 1,
        remap = false
    )
    private void rpgskilltree$exactHoldStaminaReceipt(
        Skill.Resource.ResourceConsumer resourceConsumer,
        SkillContainer skillContainer,
        ServerPlayerPatch executor,
        float amount,
        Operation<Void> original
    ) {
        EpicFightExactStaminaReceiptBridge.consume(
            EpicFightExactStaminaReceiptBridge.CALLSITE_REQUEST_HOLD,
            resourceConsumer,
            skillContainer,
            executor,
            amount,
            () -> original.call(resourceConsumer, skillContainer, executor, amount)
        );
    }
}
