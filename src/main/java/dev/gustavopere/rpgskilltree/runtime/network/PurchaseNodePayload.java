package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.NodePurchaseResult;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Minimal serverbound request: node identity plus an idempotency key. */
public record PurchaseNodePayload(ResourceLocation nodeId, String requestId) implements CustomPacketPayload {
    public static final int MAX_REQUEST_ID_LENGTH = 96;
    private static final Pattern REQUEST_ID = Pattern.compile("[A-Za-z0-9_.:-]+");

    public static final Type<PurchaseNodePayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "purchase_node")
    );

    public static final StreamCodec<ByteBuf, PurchaseNodePayload> STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC,
        PurchaseNodePayload::nodeId,
        ByteBufCodecs.stringUtf8(MAX_REQUEST_ID_LENGTH),
        PurchaseNodePayload::requestId,
        PurchaseNodePayload::new
    );

    /** Creates a fresh intent id for a new client click while preserving the legacy call site. */
    public PurchaseNodePayload(ResourceLocation nodeId) {
        this(nodeId, "node:" + UUID.randomUUID());
    }

    public PurchaseNodePayload {
        Objects.requireNonNull(nodeId, "nodeId");
        Objects.requireNonNull(requestId, "requestId");
        if (requestId.isBlank()
            || requestId.length() > MAX_REQUEST_ID_LENGTH
            || !REQUEST_ID.matcher(requestId).matches()) {
            throw new IllegalArgumentException("invalid node purchase request id");
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PurchaseNodePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                NodePurchaseResult result = PlayerProgressionRuntime.purchaseNode(
                    player,
                    payload.nodeId(),
                    payload.requestId()
                );
                if (!result.accepted()) {
                    player.sendSystemMessage(Component.translatable(result.messageKey()));
                }
            }
        });
    }
}
