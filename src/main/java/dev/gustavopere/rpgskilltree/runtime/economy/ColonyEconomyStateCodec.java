package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

/** Strict schema-1 NBT codec for server-authoritative colony economy state. */
public final class ColonyEconomyStateCodec {
    public static final int CURRENT_SCHEMA = 1;

    private static final String SCHEMA = "schema";
    private static final String COLONY_KEY = "colony_key";
    private static final String ISSUED_SUPPLY = "issued_supply";
    private static final String RETIRED_SUPPLY = "retired_supply";
    private static final String TREASURY_BALANCE = "treasury_balance";
    private static final String RESERVED_BALANCE = "reserved_balance";
    private static final String ACTIVE_CIRCULATION = "active_circulation";
    private static final String PRICE_INDEX = "price_index";
    private static final String TAX_RATE = "tax_rate";
    private static final String CURRENT_ECONOMIC_CAPACITY = "current_economic_capacity";
    private static final String LAST_SETTLEMENT_TICK = "last_settlement_tick";

    private ColonyEconomyStateCodec() {}

    public static CompoundTag encode(ColonyEconomyState state) {
        if (state == null) {
            throw new EconomyPersistenceException("Cannot encode null colony economy state");
        }
        if (state.schemaVersion() != CURRENT_SCHEMA) {
            throw new EconomyPersistenceException(
                "Cannot encode colony economy schema " + state.schemaVersion() + "; current schema is " + CURRENT_SCHEMA
            );
        }

        CompoundTag tag = new CompoundTag();
        tag.putInt(SCHEMA, CURRENT_SCHEMA);
        tag.putString(COLONY_KEY, state.colonyKey().value().toString());
        tag.putLong(ISSUED_SUPPLY, state.issuedSupply());
        tag.putLong(RETIRED_SUPPLY, state.retiredSupply());
        tag.putLong(TREASURY_BALANCE, state.treasuryBalance());
        tag.putLong(RESERVED_BALANCE, state.reservedBalance());
        tag.putLong(ACTIVE_CIRCULATION, state.activeCirculation());
        tag.putDouble(PRICE_INDEX, state.priceIndex());
        tag.putDouble(TAX_RATE, state.taxRate());
        tag.putLong(CURRENT_ECONOMIC_CAPACITY, state.currentEconomicCapacity());
        tag.putLong(LAST_SETTLEMENT_TICK, state.lastSettlementTick());
        return tag;
    }

    public static ColonyEconomyState decode(CompoundTag tag) {
        if (tag == null) {
            throw new EconomyPersistenceException("Cannot decode null colony economy tag");
        }

        requireType(tag, SCHEMA, Tag.TAG_INT);
        int schema = tag.getInt(SCHEMA);
        if (schema > CURRENT_SCHEMA) {
            throw new UnsupportedEconomySchemaException(schema, CURRENT_SCHEMA);
        }
        if (schema != CURRENT_SCHEMA) {
            throw new EconomyPersistenceException("Unsupported legacy colony-economy schema " + schema);
        }

        requireType(tag, COLONY_KEY, Tag.TAG_STRING);
        requireType(tag, ISSUED_SUPPLY, Tag.TAG_LONG);
        requireType(tag, RETIRED_SUPPLY, Tag.TAG_LONG);
        requireType(tag, TREASURY_BALANCE, Tag.TAG_LONG);
        requireType(tag, RESERVED_BALANCE, Tag.TAG_LONG);
        requireType(tag, ACTIVE_CIRCULATION, Tag.TAG_LONG);
        requireType(tag, PRICE_INDEX, Tag.TAG_DOUBLE);
        requireType(tag, TAX_RATE, Tag.TAG_DOUBLE);
        requireType(tag, CURRENT_ECONOMIC_CAPACITY, Tag.TAG_LONG);
        requireType(tag, LAST_SETTLEMENT_TICK, Tag.TAG_LONG);

        try {
            EconomyColonyKey colonyKey = new EconomyColonyKey(UUID.fromString(tag.getString(COLONY_KEY)));
            return new ColonyEconomyState(
                colonyKey,
                tag.getLong(ISSUED_SUPPLY),
                tag.getLong(RETIRED_SUPPLY),
                tag.getLong(TREASURY_BALANCE),
                tag.getLong(RESERVED_BALANCE),
                tag.getLong(ACTIVE_CIRCULATION),
                tag.getDouble(PRICE_INDEX),
                tag.getDouble(TAX_RATE),
                tag.getLong(CURRENT_ECONOMIC_CAPACITY),
                tag.getLong(LAST_SETTLEMENT_TICK),
                schema
            );
        } catch (IllegalArgumentException | ArithmeticException failure) {
            throw new EconomyPersistenceException("Invalid colony-economy state payload", failure);
        }
    }

    private static void requireType(CompoundTag tag, String field, int expectedType) {
        if (!tag.contains(field, expectedType)) {
            throw new EconomyPersistenceException("Missing or invalid colony-economy field: " + field);
        }
    }
}
