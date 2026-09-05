package dev.gustavopere.rpgskilltree.runtime.network.economy;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyNetworkAuthority;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EconomyRetirePayload(EconomyColonyContext colony, UUID intentId, long amount) implements CustomPacketPayload {
    public static final Type<EconomyRetirePayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "economy_retire")
    );
    public static final StreamCodec<ByteBuf, EconomyRetirePayload> STREAM_CODEC = StreamCodec.composite(
        EconomyColonyContext.STREAM_CODEC, EconomyRetirePayload::colony,
        UUIDUtil.STREAM_CODEC, EconomyRetirePayload::intentId,
        ByteBufCodecs.VAR_LONG, EconomyRetirePayload::amount,
        EconomyRetirePayload::new
    );

    public EconomyRetirePayload {
        if (intentId == null) throw new IllegalArgumentException("intentId must not be null");
    }

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(EconomyRetirePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player) || !EconomySnapshotRequestPayload.providerActive()) return;
            MineColoniesEconomyNetworkAuthority.applyRetire(player, payload.colony(), payload.intentId(), payload.amount());
        });
    }
}
