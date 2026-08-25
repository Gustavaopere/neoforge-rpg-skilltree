package dev.gustavopere.rpgskilltree.runtime.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightExactStaminaReceiptBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

/** Strict wrapper for the two audited ResourceConsumer invokes in PlayerPatch.consumeForSkill. */
@Mixin(value = PlayerPatch.class, remap = false)
public abstract class PlayerPatchStaminaReceiptMixin {
    private static final String RESOURCE_CONSUMER_TARGET =
        "Lyesman/epicfight/skill/Skill$Resource$ResourceConsumer;consume(" +
        "Lyesman/epicfight/skill/SkillContainer;" +
        "Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;F)V";

    @WrapOperation(
        method = "consumeForSkill(Lyesman/epicfight/skill/Skill;Lyesman/epicfight/skill/Skill$Resource;FZLnet/minecraft/nbt/CompoundTag;)Z",
        at = @At(value = "INVOKE", target = RESOURCE_CONSUMER_TARGET, remap = false),
        require = 2,
        expect = 2,
        allow = 2,
        remap = false
    )
    private void rpgskilltree$exactStaminaReceipt(
        Skill.Resource.ResourceConsumer resourceConsumer,
        SkillContainer skillContainer,
        ServerPlayerPatch executor,
        float amount,
        Operation<Void> original
    ) {
        EpicFightExactStaminaReceiptBridge.consume(
            EpicFightExactStaminaReceiptBridge.CALLSITE_PLAYER_PATCH,
            resourceConsumer,
            skillContainer,
            executor,
            amount,
            () -> original.call(resourceConsumer, skillContainer, executor, amount)
        );
    }
}
