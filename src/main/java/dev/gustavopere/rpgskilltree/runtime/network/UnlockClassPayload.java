package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UnlockClassPayload(ResourceLocation classId) implements CustomPacketPayload {
    public static final Type<UnlockClassPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "unlock_class")
    );
    public static final StreamCodec<ByteBuf, UnlockClassPayload> STREAM_CODEC =
        ResourceLocation.STREAM_CODEC.map(UnlockClassPayload::new, UnlockClassPayload::classId);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UnlockClassPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                PlayerProgressionRuntime.unlockPaidClass(player, payload.classId());
            }
        });
    }
}
