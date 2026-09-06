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

public record EconomyMintPayload(EconomyColonyContext colony, UUID intentId, long amount) implements CustomPacketPayload {
    public static final Type<EconomyMintPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "economy_mint")
    );
    public static final StreamCodec<ByteBuf, EconomyMintPayload> STREAM_CODEC = StreamCodec.composite(
        EconomyColonyContext.STREAM_CODEC, EconomyMintPayload::colony,
        UUIDUtil.STREAM_CODEC, EconomyMintPayload::intentId,
        ByteBufCodecs.VAR_LONG, EconomyMintPayload::amount,
        EconomyMintPayload::new
    );

    public EconomyMintPayload {
        if (intentId == null) throw new IllegalArgumentException("intentId must not be null");
    }

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(EconomyMintPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player) || !EconomySnapshotRequestPayload.providerActive()) return;
            MineColoniesEconomyNetworkAuthority.applyMint(player, payload.colony(), payload.intentId(), payload.amount());
        });
    }
}
