package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RespecNodePayload(ResourceLocation nodeId) implements CustomPacketPayload {
    public static final Type<RespecNodePayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "respec_node")
    );
    public static final StreamCodec<ByteBuf, RespecNodePayload> STREAM_CODEC =
        ResourceLocation.STREAM_CODEC.map(RespecNodePayload::new, RespecNodePayload::nodeId);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RespecNodePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                PlayerProgressionRuntime.respecNode(player, payload.nodeId());
            }
        });
    }
}
