package dev.gustavopere.rpgskilltree.runtime.mixin;

import dev.gustavopere.rpgskilltree.runtime.compat.identity2.MorphIdentityAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.Gabou.identity2.identity.IdentityProgression", remap = false)
public abstract class IdentityProgressionMixin {
    @Inject(
        method = "morph(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;)Z",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void rpgskilltree$gateMorph(
        ServerPlayer player,
        ResourceLocation identityId,
        CompoundTag variantNbt,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (MorphIdentityAccess.canMorph(player, identityId)) return;
        player.displayClientMessage(Component.translatable("message.rpgskilltree.morph_locked"), true);
        cir.setReturnValue(false);
    }
}
