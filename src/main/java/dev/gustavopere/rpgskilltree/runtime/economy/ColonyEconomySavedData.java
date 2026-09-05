package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyLedger;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

/** Overworld-scoped persistent store for all colony economies in a server save. */
public final class ColonyEconomySavedData extends SavedData {
    public static final String DATA_NAME = "rpgskilltree_colony_economy";
    private static final int ROOT_SCHEMA = 1;
    private static final String SCHEMA = "schema";
    private static final String ECONOMIES = "economies";
    private static final String STATE = "state";
    private static final String TRANSACTIONS = "transactions";

    private static final SavedData.Factory<ColonyEconomySavedData> FACTORY = new SavedData.Factory<>(
        ColonyEconomySavedData::new,
        ColonyEconomySavedData::load,
        null
    );

    private final Map<EconomyColonyKey, StoredEconomy> economies = new HashMap<>();

    public ColonyEconomySavedData() {}

    public static ColonyEconomySavedData get(MinecraftServer server) {
        if (server == null) {
            throw new IllegalArgumentException("server must not be null");
        }
        return server.overworld().getDataStorage().computeIfAbsent(FACTORY, DATA_NAME);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt(SCHEMA, ROOT_SCHEMA);
        ListTag economyList = new ListTag();
        for (StoredEconomy stored : economies.values()) {
            CompoundTag economyTag = new CompoundTag();
            economyTag.put(STATE, ColonyEconomyStateCodec.encode(stored.state));
            ListTag transactionList = new ListTag();
            for (EconomyTransaction transaction : stored.ledger.transactions()) {
                transactionList.add(EconomyTransactionCodec.encode(transaction));
            }
            economyTag.put(TRANSACTIONS, transactionList);
            economyList.add(economyTag);
        }
        tag.put(ECONOMIES, economyList);
        return tag;
    }

    static ColonyEconomySavedData load(CompoundTag tag, HolderLookup.Provider provider) {
        return decode(tag);
    }

    static ColonyEconomySavedData decodeForTest(CompoundTag tag) {
        return decode(tag);
    }

    CompoundTag encodeForTest() {
        return save(new CompoundTag(), null);
    }

    StoredEconomy get(EconomyColonyKey key) {
        return economies.get(key);
    }

    void put(EconomyColonyKey key, ColonyEconomyState state, ColonyEconomyLedger ledger) {
        validateRecord(key, state, ledger.transactions());
        economies.put(key, new StoredEconomy(state, ledger));
        setDirty();
    }

    void replaceState(EconomyColonyKey key, ColonyEconomyState state) {
        StoredEconomy existing = economies.get(key);
        if (existing == null) {
            put(key, state, new ColonyEconomyLedger());
            return;
        }
        validateRecord(key, state, existing.ledger.transactions());
        if (!existing.state.equals(state)) {
            existing.state = state;
            setDirty();
        }
    }

    private static ColonyEconomySavedData decode(CompoundTag tag) {
        if (tag == null) {
            throw new EconomyPersistenceException("Cannot decode null colony economy SavedData");
        }
        requireType(tag, SCHEMA, Tag.TAG_INT);
        int schema = tag.getInt(SCHEMA);
        if (schema > ROOT_SCHEMA) {
            throw new UnsupportedEconomySchemaException(schema, ROOT_SCHEMA);
        }
        if (schema != ROOT_SCHEMA) {
            throw new EconomyPersistenceException("Unsupported legacy colony economy root schema " + schema);
        }
        requireType(tag, ECONOMIES, Tag.TAG_LIST);

        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ListTag economyList = tag.getList(ECONOMIES, Tag.TAG_COMPOUND);
        for (int i = 0; i < economyList.size(); i++) {
            CompoundTag economyTag = economyList.getCompound(i);
            requireType(economyTag, STATE, Tag.TAG_COMPOUND);
            requireType(economyTag, TRANSACTIONS, Tag.TAG_LIST);

            ColonyEconomyState state = ColonyEconomyStateCodec.decode(economyTag.getCompound(STATE));
            ListTag transactionList = economyTag.getList(TRANSACTIONS, Tag.TAG_COMPOUND);
            List<EconomyTransaction> transactions = new ArrayList<>(transactionList.size());
            for (int txIndex = 0; txIndex < transactionList.size(); txIndex++) {
                transactions.add(EconomyTransactionCodec.decode(transactionList.getCompound(txIndex)));
            }

            try {
                validateRecord(state.colonyKey(), state, transactions);
                StoredEconomy previous = data.economies.put(
                    state.colonyKey(),
                    new StoredEconomy(state, new ColonyEconomyLedger(transactions))
                );
                if (previous != null) {
                    throw new EconomyPersistenceException("Duplicate colony economy key in SavedData: " + state.colonyKey().value());
                }
            } catch (EconomyPersistenceException failure) {
                throw failure;
            } catch (IllegalArgumentException failure) {
                throw new EconomyPersistenceException("Invalid persisted colony economy ledger", failure);
            }
        }
        return data;
    }

    private static void validateRecord(
        EconomyColonyKey key,
        ColonyEconomyState state,
        List<EconomyTransaction> transactions
    ) {
        if (key == null || state == null || transactions == null) {
            throw new EconomyPersistenceException("Economy record fields must not be null");
        }
        if (!key.equals(state.colonyKey())) {
            throw new EconomyPersistenceException("Economy record key/state mismatch");
        }
        for (EconomyTransaction transaction : transactions) {
            if (!key.equals(transaction.colonyKey())) {
                throw new EconomyPersistenceException("Persisted transaction belongs to a different colony economy");
            }
        }
        if (!transactions.isEmpty()) {
            EconomyTransaction last = transactions.get(transactions.size() - 1);
            if (last.resultingIssuedSupply() != state.issuedSupply()
                || last.resultingRetiredSupply() != state.retiredSupply()
                || last.resultingEffectiveSupply() != state.effectiveSupply()
                || last.resultingTreasuryBalance() != state.treasuryBalance()) {
                throw new EconomyPersistenceException("Persisted economy state does not reconcile with ledger tail");
            }
        }
    }

    private static void requireType(CompoundTag tag, String field, int expectedType) {
        if (!tag.contains(field, expectedType)) {
            throw new EconomyPersistenceException("Missing or invalid colony economy SavedData field: " + field);
        }
    }

    static final class StoredEconomy {
        private ColonyEconomyState state;
        private final ColonyEconomyLedger ledger;

        StoredEconomy(ColonyEconomyState state, ColonyEconomyLedger ledger) {
            this.state = state;
            this.ledger = ledger;
        }

        ColonyEconomyState state() {
            return state;
        }

        ColonyEconomyLedger ledger() {
            return ledger;
        }
    }
}
