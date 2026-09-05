package dev.gustavopere.rpgskilltree.runtime.network.economy;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyIntegrationState;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyNetworkAuthority;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EconomySnapshotRequestPayload(EconomyColonyContext colony) implements CustomPacketPayload {
    public static final Type<EconomySnapshotRequestPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "economy_snapshot_request")
    );
    public static final StreamCodec<ByteBuf, EconomySnapshotRequestPayload> STREAM_CODEC =
        EconomyColonyContext.STREAM_CODEC.map(EconomySnapshotRequestPayload::new, EconomySnapshotRequestPayload::colony);

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(EconomySnapshotRequestPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player) || !providerActive()) return;
            MineColoniesEconomyNetworkAuthority.sendSnapshot(player, payload.colony());
        });
    }

    static boolean providerActive() {
        boolean loaded = OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.MINECOLONIES);
        String version = OptionalIntegrations.version(OptionalIntegrations.Provider.MINECOLONIES);
        return MineColoniesEconomyIntegrationBootstrap.evaluate(loaded, version)
            == MineColoniesEconomyIntegrationState.ACTIVE;
    }
}
