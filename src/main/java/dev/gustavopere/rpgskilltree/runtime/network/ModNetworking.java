package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionStateCodec;
import dev.gustavopere.rpgskilltree.runtime.client.ClientCoreProgressionState;
import dev.gustavopere.rpgskilltree.runtime.client.ClientProgressionState;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ModNetworking {
    private static final String NETWORK_VERSION = "3";

    private ModNetworking() {}

    public static void register(IEventBus modBus) {
        modBus.addListener(ModNetworking::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(NETWORK_VERSION);
        registrar.playToClient(
            ProgressionSyncPayload.TYPE,
            ProgressionSyncPayload.STREAM_CODEC,
            ClientProgressionState::handleSync
        );
        registrar.playToClient(
            CoreProgressionSyncPayload.TYPE,
            CoreProgressionSyncPayload.STREAM_CODEC,
            ClientCoreProgressionState::handleSync
        );
        registrar.playToServer(
            PurchaseNodePayload.TYPE,
            PurchaseNodePayload.STREAM_CODEC,
            PurchaseNodePayload::handle
        );
        registrar.playToServer(
            RespecNodePayload.TYPE,
            RespecNodePayload.STREAM_CODEC,
            RespecNodePayload::handle
        );
        registrar.playToServer(
            UnlockClassPayload.TYPE,
            UnlockClassPayload.STREAM_CODEC,
            UnlockClassPayload::handle
        );
        registrar.playToServer(
            SelectClassChoicePayload.TYPE,
            SelectClassChoicePayload.STREAM_CODEC,
            SelectClassChoicePayload::handle
        );
        registrar.playToServer(
            ClearClassChoicePayload.TYPE,
            ClearClassChoicePayload.STREAM_CODEC,
            ClearClassChoicePayload::handle
        );
        registrar.playToServer(
            PurchaseAttributeRanksPayload.TYPE,
            PurchaseAttributeRanksPayload.STREAM_CODEC,
            PurchaseAttributeRanksPayload::handle
        );
        registrar.playToServer(
            RefundAttributeRanksPayload.TYPE,
            RefundAttributeRanksPayload.STREAM_CODEC,
            RefundAttributeRanksPayload::handle
        );
    }

    public static void syncToOwner(ServerPlayer player, ProgressionState state) {
        byte[] encoded = ProgressionStateCodec.encode(state);
        PacketDistributor.sendToPlayer(player, new ProgressionSyncPayload(encoded));
    }

    public static void syncCoreToOwner(
        ServerPlayer player,
        CoreProgressionState state,
        ProgressionRulesSnapshot rules
    ) {
        PacketDistributor.sendToPlayer(
            player,
            CoreProgressionSyncPayload.fromState(state, rules)
        );
    }
}
