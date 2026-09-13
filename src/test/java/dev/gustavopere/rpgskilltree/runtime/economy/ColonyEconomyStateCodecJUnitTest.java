package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

final class ColonyEconomyStateCodecJUnitTest {
    private static final EconomyColonyKey COLONY_KEY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000001101")
    );

    @Test
    void schemaOneRoundTripsEveryPersistentStateField() {
        ColonyEconomyState source = new ColonyEconomyState(
            COLONY_KEY,
            120L,
            20L,
            70L,
            10L,
            20L,
            137.5D,
            0.15D,
            48L,
            88_000L,
            ColonyEconomyStateCodec.CURRENT_SCHEMA
        );

        CompoundTag encoded = ColonyEconomyStateCodec.encode(source);
        ColonyEconomyState decoded = ColonyEconomyStateCodec.decode(encoded);

        assertEquals(ColonyEconomyStateCodec.CURRENT_SCHEMA, encoded.getInt("schema"));
        assertEquals(source, decoded);
    }

    @Test
    void newerSchemaFailsClosedInsteadOfSilentlyResetting() {
        ColonyEconomyState source = ColonyEconomyState.empty(COLONY_KEY);
        CompoundTag encoded = ColonyEconomyStateCodec.encode(source);
        encoded.putInt("schema", ColonyEconomyStateCodec.CURRENT_SCHEMA + 1);

        assertThrows(UnsupportedEconomySchemaException.class, () -> ColonyEconomyStateCodec.decode(encoded));
    }

    @Test
    void missingRequiredFieldFailsClosedInsteadOfDefaultingToZero() {
        ColonyEconomyState source = ColonyEconomyState.empty(COLONY_KEY);
        CompoundTag encoded = ColonyEconomyStateCodec.encode(source);
        encoded.remove("issued_supply");

        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomyStateCodec.decode(encoded));
    }

    @Test
    void invalidConservationFailsClosedDuringDecode() {
        ColonyEconomyState source = ColonyEconomyState.empty(COLONY_KEY);
        CompoundTag encoded = ColonyEconomyStateCodec.encode(source);
        encoded.putLong("issued_supply", 5L);
        encoded.putLong("treasury_balance", 6L);

        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomyStateCodec.decode(encoded));
    }
}
