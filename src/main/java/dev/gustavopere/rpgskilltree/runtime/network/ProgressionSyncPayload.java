package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionStateCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ProgressionSyncPayload(byte[] snapshot) implements CustomPacketPayload {
    private static final int MAX_SNAPSHOT_BYTES = 1 << 20;

    public static final Type<ProgressionSyncPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "progression_sync")
    );

    public static final StreamCodec<ByteBuf, ProgressionSyncPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.byteArray(MAX_SNAPSHOT_BYTES),
        ProgressionSyncPayload::snapshot,
        ProgressionSyncPayload::new
    );

    public static ProgressionSyncPayload fromState(ProgressionState state) {
        return new ProgressionSyncPayload(ProgressionStateCodec.encode(state));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
