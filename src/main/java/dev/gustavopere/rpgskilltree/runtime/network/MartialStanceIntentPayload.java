package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.MartialStanceRuntime;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Client intent only. The server derives availability, current stance and the next transition. */
public record MartialStanceIntentPayload(boolean cycle) implements CustomPacketPayload {
    public static final Type<MartialStanceIntentPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "martial_stance_intent")
    );

    public static final StreamCodec<ByteBuf, MartialStanceIntentPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        MartialStanceIntentPayload::cycle,
        MartialStanceIntentPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MartialStanceIntentPayload payload, IPayloadContext context) {
        if (!payload.cycle()) return;
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                MartialStanceRuntime.cycle(player);
            }
        });
    }
}
