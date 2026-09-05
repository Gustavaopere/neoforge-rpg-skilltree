package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyLedger;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransaction;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
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
    private static final String NATIVE_BINDINGS = "native_bindings";
    private static final String NATIVE_FINGERPRINTS = "native_fingerprints";
    private static final String ARCHIVED_ECONOMIES = "archived_economies";
    private static final String DIMENSION = "dimension";
    private static final String COLONY_ID = "colony_id";
    private static final String OWNER_UUID = "owner_uuid";
    private static final String TOWN_HALL_X = "town_hall_x";
    private static final String TOWN_HALL_Y = "town_hall_y";
    private static final String TOWN_HALL_Z = "town_hall_z";

    private static final SavedData.Factory<ColonyEconomySavedData> FACTORY = new SavedData.Factory<>(
        ColonyEconomySavedData::new,
        ColonyEconomySavedData::load,
        null
    );

    private final Map<EconomyColonyKey, StoredEconomy> economies = new HashMap<>();
    private final Map<String, EconomyColonyKey> nativeBindings = new HashMap<>();
    private final Map<String, NativeColonyBinding> nativeFingerprints = new HashMap<>();
    private final Set<EconomyColonyKey> archivedEconomies = new HashSet<>();

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

        CompoundTag bindingsTag = new CompoundTag();
        nativeBindings.forEach((binding, economyKey) -> bindingsTag.putString(binding, economyKey.value().toString()));
        tag.put(NATIVE_BINDINGS, bindingsTag);

        CompoundTag fingerprintsTag = new CompoundTag();
        nativeFingerprints.forEach((key, binding) -> fingerprintsTag.put(key, encodeFingerprint(binding)));
        tag.put(NATIVE_FINGERPRINTS, fingerprintsTag);

        ListTag archivedTag = new ListTag();
        archivedEconomies.stream()
            .map(EconomyColonyKey::value)
            .map(UUID::toString)
            .sorted()
            .map(StringTag::valueOf)
            .forEach(archivedTag::add);
        tag.put(ARCHIVED_ECONOMIES, archivedTag);
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

    public Optional<EconomyColonyKey> binding(NativeColonyBinding binding) {
        if (binding == null) {
            throw new IllegalArgumentException("binding must not be null");
        }
        String key = binding.persistentKey();
        EconomyColonyKey economyKey = nativeBindings.get(key);
        if (economyKey == null) {
            return Optional.empty();
        }
        requireMatchingFingerprint(binding, nativeFingerprints.get(key));
        return Optional.of(economyKey);
    }

    /** Resolves a live provider binding, assigning a fresh immutable monetary identity once. */
    public EconomyColonyKey resolveOrCreateBinding(NativeColonyBinding binding) {
        if (binding == null) {
            throw new IllegalArgumentException("binding must not be null");
        }
        String key = binding.persistentKey();
        EconomyColonyKey existing = nativeBindings.get(key);
        if (existing != null) {
            requireMatchingFingerprint(binding, nativeFingerprints.get(key));
            if (archivedEconomies.contains(existing)) {
                throw new EconomyPersistenceException("Live native binding points to archived economy identity");
            }
            return existing;
        }

        EconomyColonyKey created;
        do {
            created = new EconomyColonyKey(UUID.randomUUID());
        } while (economies.containsKey(created) || archivedEconomies.contains(created) || nativeBindings.containsValue(created));
        nativeBindings.put(key, created);
        nativeFingerprints.put(key, binding);
        setDirty();
        return created;
    }

    /** Detaches a deleted native colony and permanently prevents its monetary UUID from being rebound. */
    public Optional<EconomyColonyKey> archiveBinding(NativeColonyBinding binding) {
        if (binding == null) {
            throw new IllegalArgumentException("binding must not be null");
        }
        String key = binding.persistentKey();
        EconomyColonyKey existing = nativeBindings.get(key);
        if (existing == null) {
            return Optional.empty();
        }
        requireMatchingFingerprint(binding, nativeFingerprints.get(key));
        nativeBindings.remove(key);
        nativeFingerprints.remove(key);
        archivedEconomies.add(existing);
        setDirty();
        return Optional.of(existing);
    }

    public boolean isArchived(EconomyColonyKey key) {
        return archivedEconomies.contains(key);
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
        requireType(tag, NATIVE_BINDINGS, Tag.TAG_COMPOUND);
        requireType(tag, NATIVE_FINGERPRINTS, Tag.TAG_COMPOUND);
        requireType(tag, ARCHIVED_ECONOMIES, Tag.TAG_LIST);

        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ListTag economyList = requireListElementType(tag, ECONOMIES, Tag.TAG_COMPOUND);
        for (int i = 0; i < economyList.size(); i++) {
            CompoundTag economyTag = economyList.getCompound(i);
            requireType(economyTag, STATE, Tag.TAG_COMPOUND);
            requireType(economyTag, TRANSACTIONS, Tag.TAG_LIST);

            ColonyEconomyState state = ColonyEconomyStateCodec.decode(economyTag.getCompound(STATE));
            ListTag transactionList = requireListElementType(economyTag, TRANSACTIONS, Tag.TAG_COMPOUND);
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

        CompoundTag bindingsTag = tag.getCompound(NATIVE_BINDINGS);
        for (String nativeKey : bindingsTag.getAllKeys()) {
            if (!bindingsTag.contains(nativeKey, Tag.TAG_STRING)) {
                throw new EconomyPersistenceException("Invalid native colony binding payload: " + nativeKey);
            }
            try {
                EconomyColonyKey economyKey = new EconomyColonyKey(UUID.fromString(bindingsTag.getString(nativeKey)));
                if (data.nativeBindings.put(nativeKey, economyKey) != null) {
                    throw new EconomyPersistenceException("Duplicate native colony binding: " + nativeKey);
                }
            } catch (IllegalArgumentException failure) {
                throw new EconomyPersistenceException("Invalid economy UUID for native colony binding: " + nativeKey, failure);
            }
        }

        CompoundTag fingerprintsTag = tag.getCompound(NATIVE_FINGERPRINTS);
        for (String nativeKey : fingerprintsTag.getAllKeys()) {
            if (!fingerprintsTag.contains(nativeKey, Tag.TAG_COMPOUND)) {
                throw new EconomyPersistenceException("Invalid native colony fingerprint payload: " + nativeKey);
            }
            NativeColonyBinding binding = decodeFingerprint(fingerprintsTag.getCompound(nativeKey));
            if (!nativeKey.equals(binding.persistentKey())) {
                throw new EconomyPersistenceException("Native colony fingerprint key mismatch: " + nativeKey);
            }
            if (data.nativeFingerprints.put(nativeKey, binding) != null) {
                throw new EconomyPersistenceException("Duplicate native colony fingerprint: " + nativeKey);
            }
        }
        if (!data.nativeBindings.keySet().equals(data.nativeFingerprints.keySet())) {
            throw new EconomyPersistenceException("Native colony binding/fingerprint index mismatch");
        }

        ListTag archivedTag = requireListElementType(tag, ARCHIVED_ECONOMIES, Tag.TAG_STRING);
        for (int i = 0; i < archivedTag.size(); i++) {
            try {
                data.archivedEconomies.add(new EconomyColonyKey(UUID.fromString(archivedTag.getString(i))));
            } catch (IllegalArgumentException failure) {
                throw new EconomyPersistenceException("Invalid archived economy UUID", failure);
            }
        }

        for (Map.Entry<String, EconomyColonyKey> entry : data.nativeBindings.entrySet()) {
            if (data.archivedEconomies.contains(entry.getValue())) {
                throw new EconomyPersistenceException("Native colony binding points to archived economy: " + entry.getKey());
            }
        }
        if (new HashSet<>(data.nativeBindings.values()).size() != data.nativeBindings.size()) {
            throw new EconomyPersistenceException("Multiple live native colonies share one economy UUID");
        }
        return data;
    }

    private static CompoundTag encodeFingerprint(NativeColonyBinding binding) {
        CompoundTag tag = new CompoundTag();
        tag.putString(DIMENSION, binding.dimensionId().toString());
        tag.putInt(COLONY_ID, binding.colonyId());
        tag.putString(OWNER_UUID, binding.ownerUuid().toString());
        tag.putInt(TOWN_HALL_X, binding.townHallPos().getX());
        tag.putInt(TOWN_HALL_Y, binding.townHallPos().getY());
        tag.putInt(TOWN_HALL_Z, binding.townHallPos().getZ());
        return tag;
    }

    private static NativeColonyBinding decodeFingerprint(CompoundTag tag) {
        requireType(tag, DIMENSION, Tag.TAG_STRING);
        requireType(tag, COLONY_ID, Tag.TAG_INT);
        requireType(tag, OWNER_UUID, Tag.TAG_STRING);
        requireType(tag, TOWN_HALL_X, Tag.TAG_INT);
        requireType(tag, TOWN_HALL_Y, Tag.TAG_INT);
        requireType(tag, TOWN_HALL_Z, Tag.TAG_INT);
        try {
            ResourceLocation dimension = ResourceLocation.tryParse(tag.getString(DIMENSION));
            if (dimension == null) {
                throw new IllegalArgumentException("invalid dimension id");
            }
            return new NativeColonyBinding(
                dimension,
                tag.getInt(COLONY_ID),
                UUID.fromString(tag.getString(OWNER_UUID)),
                new BlockPos(tag.getInt(TOWN_HALL_X), tag.getInt(TOWN_HALL_Y), tag.getInt(TOWN_HALL_Z))
            );
        } catch (IllegalArgumentException failure) {
            throw new EconomyPersistenceException("Invalid native colony fingerprint", failure);
        }
    }

    private static void requireMatchingFingerprint(NativeColonyBinding requested, NativeColonyBinding persisted) {
        if (persisted == null) {
            throw new EconomyPersistenceException("Native colony binding is missing its persisted fingerprint");
        }
        if (!persisted.equals(requested)) {
            throw new EconomyPersistenceException(
                "Native colony binding fingerprint changed for " + requested.persistentKey()
            );
        }
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

    private static ListTag requireListElementType(CompoundTag tag, String field, int expectedElementType) {
        requireType(tag, field, Tag.TAG_LIST);
        ListTag list = (ListTag) tag.get(field);
        if (list == null) {
            throw new EconomyPersistenceException("Missing colony economy list field: " + field);
        }
        if (!list.isEmpty() && list.getElementType() != expectedElementType) {
            throw new EconomyPersistenceException("Invalid element type for colony economy list field: " + field);
        }
        return list;
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
