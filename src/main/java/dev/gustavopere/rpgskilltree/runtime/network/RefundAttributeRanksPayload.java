package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.runtime.CorePlayerProgressionRuntime;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.regex.Pattern;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Minimal client request for refunding uncapped attribute ranks. */
public record RefundAttributeRanksPayload(AttributeId attribute, long rankCount, String transactionId)
    implements CustomPacketPayload {

    /** Technical batching limit only; it does not cap an attribute's total rank. */
    public static final long MAX_RANKS_PER_REQUEST = 1_024L;
    public static final int MAX_TRANSACTION_ID_LENGTH = 96;
    private static final Pattern TRANSACTION_ID = Pattern.compile("[A-Za-z0-9_.:-]+");

    private static final StreamCodec<ByteBuf, AttributeId> ATTRIBUTE_CODEC =
        ByteBufCodecs.stringUtf8(32).map(RefundAttributeRanksPayload::parseAttribute, AttributeId::serializedId);

    public static final Type<RefundAttributeRanksPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "refund_attribute_ranks")
    );

    public static final StreamCodec<ByteBuf, RefundAttributeRanksPayload> STREAM_CODEC = StreamCodec.composite(
        ATTRIBUTE_CODEC,
        RefundAttributeRanksPayload::attribute,
        ByteBufCodecs.VAR_LONG,
        RefundAttributeRanksPayload::rankCount,
        ByteBufCodecs.stringUtf8(MAX_TRANSACTION_ID_LENGTH),
        RefundAttributeRanksPayload::transactionId,
        RefundAttributeRanksPayload::new
    );

    public RefundAttributeRanksPayload {
        Objects.requireNonNull(attribute, "attribute");
        Objects.requireNonNull(transactionId, "transactionId");
        if (rankCount <= 0L || rankCount > MAX_RANKS_PER_REQUEST) {
            throw new IllegalArgumentException("rankCount outside allowed request range: " + rankCount);
        }
        if (transactionId.isBlank()
            || transactionId.length() > MAX_TRANSACTION_ID_LENGTH
            || !TRANSACTION_ID.matcher(transactionId).matches()) {
            throw new IllegalArgumentException("invalid attribute transaction id");
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RefundAttributeRanksPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                CorePlayerProgressionRuntime.refundAttributeRanks(
                    player,
                    payload.attribute(),
                    payload.rankCount(),
                    payload.transactionId(),
                    "network:attribute_refund"
                );
            }
        });
    }

    private static AttributeId parseAttribute(String serializedId) {
        for (AttributeId attribute : AttributeId.values()) {
            if (attribute.serializedId().equals(serializedId)) return attribute;
        }
        throw new IllegalArgumentException("unknown attribute id: " + serializedId);
    }
}
