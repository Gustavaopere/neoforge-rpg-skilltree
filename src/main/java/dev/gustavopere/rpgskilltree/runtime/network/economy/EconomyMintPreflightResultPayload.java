package dev.gustavopere.rpgskilltree.runtime.network.economy;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.client.economy.ClientColonyEconomyState;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EconomyMintPreflightResultPayload(
    EconomyColonyContext colony,
    String status,
    Projection projection
) implements CustomPacketPayload {
    public static final Type<EconomyMintPreflightResultPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "economy_mint_preflight_result")
    );
    public static final StreamCodec<ByteBuf, EconomyMintPreflightResultPayload> STREAM_CODEC = StreamCodec.composite(
        EconomyColonyContext.STREAM_CODEC, EconomyMintPreflightResultPayload::colony,
        ByteBufCodecs.stringUtf8(64), EconomyMintPreflightResultPayload::status,
        Projection.STREAM_CODEC, EconomyMintPreflightResultPayload::projection,
        EconomyMintPreflightResultPayload::new
    );

    public EconomyMintPreflightResultPayload {
        if (status == null || status.isBlank() || status.length() > 64) throw new IllegalArgumentException("invalid preflight status");
        if (projection == null) throw new IllegalArgumentException("projection must not be null");
    }

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(EconomyMintPreflightResultPayload payload, IPayloadContext context) {
        ClientColonyEconomyState.handlePreflight(payload, context);
    }

    public record Projection(
        long currentEffectiveSupply,
        long projectedEffectiveSupply,
        long economicCapacity,
        double currentPriceIndex,
        double projectedTargetPriceIndex
    ) {
        public static final StreamCodec<ByteBuf, Projection> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG, Projection::currentEffectiveSupply,
            ByteBufCodecs.VAR_LONG, Projection::projectedEffectiveSupply,
            ByteBufCodecs.VAR_LONG, Projection::economicCapacity,
            ByteBufCodecs.DOUBLE, Projection::currentPriceIndex,
            ByteBufCodecs.DOUBLE, Projection::projectedTargetPriceIndex,
            Projection::new
        );

        public Projection {
            if (currentEffectiveSupply < 0L || projectedEffectiveSupply < currentEffectiveSupply || economicCapacity < 0L) {
                throw new IllegalArgumentException("invalid preflight supply/capacity projection");
            }
            if (!Double.isFinite(currentPriceIndex) || currentPriceIndex <= 0.0D
                || !Double.isFinite(projectedTargetPriceIndex) || projectedTargetPriceIndex <= 0.0D) {
                throw new IllegalArgumentException("invalid preflight price indexes");
            }
        }

        public static Projection unavailable() {
            return new Projection(0L, 0L, 0L, 100.0D, 100.0D);
        }
    }
}
