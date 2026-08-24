package dev.gustavopere.rpgskilltree.runtime.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightExactStaminaReceiptBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.common.ComboAttacks;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

/** Strict wrapper for the one audited direct ResourceConsumer invoke in ComboAttacks.executeOnServer. */
@Mixin(value = ComboAttacks.class, remap = false)
public abstract class ComboAttacksStaminaReceiptMixin {
    private static final String RESOURCE_CONSUMER_TARGET =
        "Lyesman/epicfight/skill/Skill$Resource$ResourceConsumer;consume(" +
        "Lyesman/epicfight/skill/SkillContainer;" +
        "Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;F)V";

    @WrapOperation(
        method = "executeOnServer(Lyesman/epicfight/skill/SkillContainer;Lnet/minecraft/nbt/CompoundTag;)V",
        at = @At(value = "INVOKE", target = RESOURCE_CONSUMER_TARGET, remap = false),
        require = 1,
        expect = 1,
        allow = 1,
        remap = false
    )
    private void rpgskilltree$exactComboStaminaReceipt(
        Skill.Resource.ResourceConsumer resourceConsumer,
        SkillContainer skillContainer,
        ServerPlayerPatch executor,
        float amount,
        Operation<Void> original
    ) {
        EpicFightExactStaminaReceiptBridge.consume(
            EpicFightExactStaminaReceiptBridge.CALLSITE_COMBO_ATTACK,
            resourceConsumer,
            skillContainer,
            executor,
            amount,
            () -> original.call(resourceConsumer, skillContainer, executor, amount)
        );
    }
}
