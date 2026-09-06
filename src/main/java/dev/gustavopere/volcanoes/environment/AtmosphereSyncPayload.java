package dev.gustavopere.volcanoes.environment;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/** Exactly one quantized player-relevant atmosphere snapshot (1 int + 8 shorts / 20 bytes). */
public record AtmosphereSyncPayload(AtmosphereSnapshot snapshot) implements CustomPacketPayload {
    public static final Type<AtmosphereSyncPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("volcanoes", "atmosphere_sync"));

    public static final StreamCodec<ByteBuf, AtmosphereSyncPayload> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public AtmosphereSyncPayload decode(ByteBuf buffer) {
            return new AtmosphereSyncPayload(new AtmosphereSnapshot(
                    buffer.readInt(),
                    buffer.readShort(),
                    buffer.readShort(),
                    buffer.readShort(),
                    buffer.readShort(),
                    buffer.readShort(),
                    buffer.readShort(),
                    buffer.readShort(),
                    buffer.readShort()));
        }

        @Override
        public void encode(ByteBuf buffer, AtmosphereSyncPayload payload) {
            AtmosphereSnapshot value = payload.snapshot();
            buffer.writeInt(value.totalPressureMilliAtm());
            buffer.writeShort(value.oxygenTenThousandths());
            buffer.writeShort(value.carbonDioxideTenThousandths());
            buffer.writeShort(value.sulfurDioxideTenthsPpm());
            buffer.writeShort(value.toxicGasTenthsPpm());
            buffer.writeShort(value.particulatesTwentiethsMgM3());
            buffer.writeShort(value.smokeTwentiethsMgM3());
            buffer.writeShort(value.humidityTwoHundredths());
            buffer.writeShort(value.thermalTenthsC());
        }
    };

    public AtmosphereSyncPayload {
        snapshot = Objects.requireNonNull(snapshot, "snapshot");
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
