package dev.gustavopere.volcanoes.tectonics;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Small client-bound signal that drives local camera shake. */
public record SeismicShakePayload(float amplitude, int durationTicks) implements CustomPacketPayload {
    public static final Type<SeismicShakePayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("volcanoes", "seismic_shake"));
    public static final StreamCodec<ByteBuf, SeismicShakePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            SeismicShakePayload::amplitude,
            ByteBufCodecs.VAR_INT,
            SeismicShakePayload::durationTicks,
            SeismicShakePayload::new);

    public SeismicShakePayload {
        if (!Float.isFinite(amplitude) || amplitude < 0.0f || amplitude > 1.0f) {
            throw new IllegalArgumentException("amplitude must be within [0, 1]");
        }
        if (durationTicks <= 0 || durationTicks > 1_200) {
            throw new IllegalArgumentException("durationTicks must be within 1..1200");
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
