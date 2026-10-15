package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.client.ClientAttributeMutationResultState;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.regex.Pattern;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Server verdict for a single attribute transaction; never contains a client-authored balance. */
public record AttributeRankMutationResultPayload(String transactionId, boolean accepted)
    implements CustomPacketPayload {
    private static final int MAX_ID_LENGTH = PurchaseAttributeRanksPayload.MAX_TRANSACTION_ID_LENGTH;
    private static final Pattern VALID_ID = Pattern.compile("[A-Za-z0-9_.:-]+");

    public static final Type<AttributeRankMutationResultPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "attribute_rank_mutation_result")
    );

    public static final StreamCodec<ByteBuf, AttributeRankMutationResultPayload> STREAM_CODEC =
        StreamCodec.composite(
            ByteBufCodecs.stringUtf8(MAX_ID_LENGTH), AttributeRankMutationResultPayload::transactionId,
            ByteBufCodecs.BOOL, AttributeRankMutationResultPayload::accepted,
            AttributeRankMutationResultPayload::new
        );

    public AttributeRankMutationResultPayload {
        Objects.requireNonNull(transactionId, "transactionId");
        if (transactionId.isBlank() || transactionId.length() > MAX_ID_LENGTH
            || !VALID_ID.matcher(transactionId).matches()) {
            throw new IllegalArgumentException("invalid attribute mutation result id");
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(AttributeRankMutationResultPayload result, IPayloadContext context) {
        ClientAttributeMutationResultState.handle(result, context);
    }
}
