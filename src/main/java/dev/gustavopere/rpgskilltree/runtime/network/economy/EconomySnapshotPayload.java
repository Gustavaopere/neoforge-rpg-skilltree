package dev.gustavopere.rpgskilltree.runtime.network.economy;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.client.economy.ClientColonyEconomyState;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record EconomySnapshotPayload(
    EconomyColonyContext colony,
    Balances balances,
    Metrics metrics
) implements CustomPacketPayload {
    public static final Type<EconomySnapshotPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "economy_snapshot")
    );
    public static final StreamCodec<ByteBuf, EconomySnapshotPayload> STREAM_CODEC = StreamCodec.composite(
        EconomyColonyContext.STREAM_CODEC, EconomySnapshotPayload::colony,
        Balances.STREAM_CODEC, EconomySnapshotPayload::balances,
        Metrics.STREAM_CODEC, EconomySnapshotPayload::metrics,
        EconomySnapshotPayload::new
    );

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(EconomySnapshotPayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        ClientColonyEconomyState.handleSnapshot(payload, context);
    }

    public record Balances(long issued, long retired, long treasury, long reserved, long activeCirculation) {
        public static final StreamCodec<ByteBuf, Balances> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG, Balances::issued,
            ByteBufCodecs.VAR_LONG, Balances::retired,
            ByteBufCodecs.VAR_LONG, Balances::treasury,
            ByteBufCodecs.VAR_LONG, Balances::reserved,
            ByteBufCodecs.VAR_LONG, Balances::activeCirculation,
            Balances::new
        );

        public Balances {
            if (issued < 0L || retired < 0L || treasury < 0L || reserved < 0L || activeCirculation < 0L) {
                throw new IllegalArgumentException("economy balances must be non-negative");
            }
            if (retired > issued) throw new IllegalArgumentException("retired supply exceeds issued supply");
        }

        public long effectiveSupply() { return issued - retired; }
    }

    public record Metrics(long economicCapacity, long lastSettlementTick, double priceIndex, double taxRate, boolean initialized) {
        public static final StreamCodec<ByteBuf, Metrics> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG, Metrics::economicCapacity,
            ByteBufCodecs.VAR_LONG, Metrics::lastSettlementTick,
            ByteBufCodecs.DOUBLE, Metrics::priceIndex,
            ByteBufCodecs.DOUBLE, Metrics::taxRate,
            ByteBufCodecs.BOOL, Metrics::initialized,
            Metrics::new
        );

        public Metrics {
            if (economicCapacity < 0L || lastSettlementTick < 0L) throw new IllegalArgumentException("economy metrics must be non-negative");
            if (!Double.isFinite(priceIndex) || priceIndex <= 0.0D) throw new IllegalArgumentException("priceIndex must be finite and positive");
            if (!Double.isFinite(taxRate) || taxRate < 0.0D || taxRate > 1.0D) throw new IllegalArgumentException("taxRate must be in [0,1]");
        }
    }
}
