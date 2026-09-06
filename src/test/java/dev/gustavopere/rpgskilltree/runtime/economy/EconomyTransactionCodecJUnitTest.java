package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransaction;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransactionKind;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

final class EconomyTransactionCodecJUnitTest {
    @Test
    void roundTripsAuditEntryAndMetadata() {
        EconomyTransaction source = new EconomyTransaction(
            UUID.fromString("00000000-0000-0000-0000-000000001301"),
            new EconomyColonyKey(UUID.fromString("00000000-0000-0000-0000-000000001302")),
            "restart-safe:mint:codec",
            EconomyTransactionKind.MINT,
            25L,
            "monetary_authority",
            "treasury",
            12_345L,
            25L,
            0L,
            25L,
            25L,
            Map.of("reason", "test")
        );

        CompoundTag encoded = EconomyTransactionCodec.encode(source);
        EconomyTransaction decoded = EconomyTransactionCodec.decode(encoded);

        assertEquals(source, decoded);
    }

    @Test
    void unknownKindFailsClosed() {
        EconomyTransaction source = new EconomyTransaction(
            UUID.fromString("00000000-0000-0000-0000-000000001303"),
            new EconomyColonyKey(UUID.fromString("00000000-0000-0000-0000-000000001304")),
            "restart-safe:mint:bad-kind",
            EconomyTransactionKind.MINT,
            1L,
            "monetary_authority",
            "treasury",
            1L,
            1L,
            0L,
            1L,
            1L,
            Map.of()
        );
        CompoundTag encoded = EconomyTransactionCodec.encode(source);
        encoded.putString("kind", "FUTURE_UNKNOWN_KIND");

        assertThrows(EconomyPersistenceException.class, () -> EconomyTransactionCodec.decode(encoded));
    }
}
