package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransaction;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransactionKind;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

/** Strict NBT codec for persisted monetary audit entries. */
public final class EconomyTransactionCodec {
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String COLONY_KEY = "colony_key";
    private static final String CAUSAL_KEY = "causal_key";
    private static final String KIND = "kind";
    private static final String AMOUNT = "amount";
    private static final String SOURCE = "source";
    private static final String COUNTERPARTY = "counterparty";
    private static final String GAME_TIME = "game_time";
    private static final String RESULTING_ISSUED = "resulting_issued_supply";
    private static final String RESULTING_RETIRED = "resulting_retired_supply";
    private static final String RESULTING_EFFECTIVE = "resulting_effective_supply";
    private static final String RESULTING_TREASURY = "resulting_treasury_balance";
    private static final String METADATA = "metadata";

    private EconomyTransactionCodec() {}

    public static CompoundTag encode(EconomyTransaction transaction) {
        if (transaction == null) {
            throw new EconomyPersistenceException("Cannot encode null economy transaction");
        }
        CompoundTag tag = new CompoundTag();
        tag.putString(TRANSACTION_ID, transaction.transactionId().toString());
        tag.putString(COLONY_KEY, transaction.colonyKey().value().toString());
        tag.putString(CAUSAL_KEY, transaction.causalKey());
        tag.putString(KIND, transaction.kind().name());
        tag.putLong(AMOUNT, transaction.amount());
        tag.putString(SOURCE, transaction.source());
        tag.putString(COUNTERPARTY, transaction.counterparty());
        tag.putLong(GAME_TIME, transaction.gameTime());
        tag.putLong(RESULTING_ISSUED, transaction.resultingIssuedSupply());
        tag.putLong(RESULTING_RETIRED, transaction.resultingRetiredSupply());
        tag.putLong(RESULTING_EFFECTIVE, transaction.resultingEffectiveSupply());
        tag.putLong(RESULTING_TREASURY, transaction.resultingTreasuryBalance());

        CompoundTag metadata = new CompoundTag();
        transaction.metadata().forEach(metadata::putString);
        tag.put(METADATA, metadata);
        return tag;
    }

    public static EconomyTransaction decode(CompoundTag tag) {
        if (tag == null) {
            throw new EconomyPersistenceException("Cannot decode null economy transaction tag");
        }
        requireType(tag, TRANSACTION_ID, Tag.TAG_STRING);
        requireType(tag, COLONY_KEY, Tag.TAG_STRING);
        requireType(tag, CAUSAL_KEY, Tag.TAG_STRING);
        requireType(tag, KIND, Tag.TAG_STRING);
        requireType(tag, AMOUNT, Tag.TAG_LONG);
        requireType(tag, SOURCE, Tag.TAG_STRING);
        requireType(tag, COUNTERPARTY, Tag.TAG_STRING);
        requireType(tag, GAME_TIME, Tag.TAG_LONG);
        requireType(tag, RESULTING_ISSUED, Tag.TAG_LONG);
        requireType(tag, RESULTING_RETIRED, Tag.TAG_LONG);
        requireType(tag, RESULTING_EFFECTIVE, Tag.TAG_LONG);
        requireType(tag, RESULTING_TREASURY, Tag.TAG_LONG);
        requireType(tag, METADATA, Tag.TAG_COMPOUND);

        try {
            CompoundTag metadataTag = tag.getCompound(METADATA);
            Map<String, String> metadata = new HashMap<>();
            for (String key : metadataTag.getAllKeys()) {
                if (!metadataTag.contains(key, Tag.TAG_STRING)) {
                    throw new EconomyPersistenceException("Invalid economy transaction metadata value for key: " + key);
                }
                metadata.put(key, metadataTag.getString(key));
            }

            return new EconomyTransaction(
                UUID.fromString(tag.getString(TRANSACTION_ID)),
                new EconomyColonyKey(UUID.fromString(tag.getString(COLONY_KEY))),
                tag.getString(CAUSAL_KEY),
                EconomyTransactionKind.valueOf(tag.getString(KIND)),
                tag.getLong(AMOUNT),
                tag.getString(SOURCE),
                tag.getString(COUNTERPARTY),
                tag.getLong(GAME_TIME),
                tag.getLong(RESULTING_ISSUED),
                tag.getLong(RESULTING_RETIRED),
                tag.getLong(RESULTING_EFFECTIVE),
                tag.getLong(RESULTING_TREASURY),
                metadata
            );
        } catch (EconomyPersistenceException failure) {
            throw failure;
        } catch (IllegalArgumentException | ArithmeticException failure) {
            throw new EconomyPersistenceException("Invalid economy transaction payload", failure);
        }
    }

    private static void requireType(CompoundTag tag, String field, int expectedType) {
        if (!tag.contains(field, expectedType)) {
            throw new EconomyPersistenceException("Missing or invalid economy transaction field: " + field);
        }
    }
}
