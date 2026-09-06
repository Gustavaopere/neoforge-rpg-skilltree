package dev.gustavopere.rpgskilltree.runtime.network.economy;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyNetworkAuthority;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EconomyMintPreflightPayload(EconomyColonyContext colony, long amount) implements CustomPacketPayload {
    public static final Type<EconomyMintPreflightPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "economy_mint_preflight")
    );
    public static final StreamCodec<ByteBuf, EconomyMintPreflightPayload> STREAM_CODEC = StreamCodec.composite(
        EconomyColonyContext.STREAM_CODEC, EconomyMintPreflightPayload::colony,
        ByteBufCodecs.VAR_LONG, EconomyMintPreflightPayload::amount,
        EconomyMintPreflightPayload::new
    );

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(EconomyMintPreflightPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player) || !EconomySnapshotRequestPayload.providerActive()) return;
            MineColoniesEconomyNetworkAuthority.sendMintPreflight(player, payload.colony(), payload.amount());
        });
    }
}
