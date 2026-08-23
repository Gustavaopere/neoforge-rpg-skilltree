package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SelectClassChoicePayload(ResourceLocation choiceId) implements CustomPacketPayload {
    public static final Type<SelectClassChoicePayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "select_class_choice")
    );
    public static final StreamCodec<ByteBuf, SelectClassChoicePayload> STREAM_CODEC =
        ResourceLocation.STREAM_CODEC.map(SelectClassChoicePayload::new, SelectClassChoicePayload::choiceId);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(SelectClassChoicePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                PlayerProgressionRuntime.selectClassChoice(player, payload.choiceId());
            }
        });
    }
}
