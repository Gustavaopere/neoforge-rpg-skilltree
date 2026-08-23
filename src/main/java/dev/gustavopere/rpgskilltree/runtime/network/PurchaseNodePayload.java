package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PurchaseNodePayload(ResourceLocation nodeId) implements CustomPacketPayload {
    public static final Type<PurchaseNodePayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "purchase_node")
    );
    public static final StreamCodec<ByteBuf, PurchaseNodePayload> STREAM_CODEC =
        ResourceLocation.STREAM_CODEC.map(PurchaseNodePayload::new, PurchaseNodePayload::nodeId);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PurchaseNodePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                PlayerProgressionRuntime.purchaseNode(player, payload.nodeId());
            }
        });
    }
}
