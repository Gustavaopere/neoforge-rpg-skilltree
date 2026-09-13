package dev.gustavopere.rpgskilltree.runtime.network.economy;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/** Client-supplied provider lookup only; never a monetary identity. */
public record EconomyColonyContext(ResourceLocation dimensionId, int colonyId) {
    public static final StreamCodec<ByteBuf, EconomyColonyContext> STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC,
        EconomyColonyContext::dimensionId,
        ByteBufCodecs.VAR_INT,
        EconomyColonyContext::colonyId,
        EconomyColonyContext::new
    );

    public EconomyColonyContext {
        if (dimensionId == null) throw new IllegalArgumentException("dimensionId must not be null");
        if (colonyId < 0) throw new IllegalArgumentException("colonyId must be non-negative");
    }
}
