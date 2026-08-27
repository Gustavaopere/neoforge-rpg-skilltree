package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.core.CoreProgressionSyncState;
import dev.gustavopere.rpgskilltree.core.CoreProgressionSyncStateCodec;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Compact clientbound snapshot of the uncapped Core progression projection. */
public record CoreProgressionSyncPayload(byte[] snapshot) implements CustomPacketPayload {
    private static final int MAX_SNAPSHOT_BYTES = 512;

    public CoreProgressionSyncPayload {
        Objects.requireNonNull(snapshot);
        if (snapshot.length == 0 || snapshot.length > MAX_SNAPSHOT_BYTES) {
            throw new IllegalArgumentException("invalid Core progression sync snapshot size: " + snapshot.length);
        }
        snapshot = snapshot.clone();
    }

    public static final Type<CoreProgressionSyncPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "core_progression_sync")
    );

    public static final StreamCodec<ByteBuf, CoreProgressionSyncPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.byteArray(MAX_SNAPSHOT_BYTES),
        CoreProgressionSyncPayload::snapshot,
        CoreProgressionSyncPayload::new
    );

    public static CoreProgressionSyncPayload fromState(
        CoreProgressionState state,
        ProgressionRulesSnapshot rules
    ) {
        CoreProgressionSyncState projection = CoreProgressionSyncState.from(state, rules);
        return new CoreProgressionSyncPayload(CoreProgressionSyncStateCodec.encode(projection));
    }

    @Override
    public byte[] snapshot() {
        return snapshot.clone();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
