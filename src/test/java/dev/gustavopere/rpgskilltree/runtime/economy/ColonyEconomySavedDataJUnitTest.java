package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyCommand;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMutationResult;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransactionKind;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

final class ColonyEconomySavedDataJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000001401")
    );

    @Test
    void saveReloadPreservesStateAndReplayProtection() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        EconomyCommand mint = new EconomyCommand(
            UUID.fromString("00000000-0000-0000-0000-000000001402"),
            "restart-safe:mint:saved-data",
            EconomyTransactionKind.MINT,
            20L
        );

        EconomyMutationResult first = repository.apply(COLONY, mint, 5_000L);
        assertEquals(EconomyMutationResult.Status.APPLIED, first.status());

        CompoundTag encoded = data.encodeForTest();
        ColonyEconomySavedData loaded = ColonyEconomySavedData.decodeForTest(encoded);
        ColonyEconomyRepository restored = new ColonyEconomyRepository(loaded);

        assertEquals(first.state(), restored.find(COLONY).orElseThrow());
        assertEquals(1, restored.transactions(COLONY).size());

        EconomyMutationResult duplicate = restored.apply(COLONY, mint, 6_000L);
        assertEquals(EconomyMutationResult.Status.DUPLICATE, duplicate.status());
        assertEquals(first.state(), duplicate.state());
        assertEquals(1, restored.transactions(COLONY).size());
    }

    @Test
    void rejectedMutationDoesNotCreatePersistentEconomyEntry() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        EconomyCommand retire = new EconomyCommand(
            UUID.fromString("00000000-0000-0000-0000-000000001403"),
            "retire:without-funds",
            EconomyTransactionKind.RETIRE,
            1L
        );

        EconomyMutationResult result = repository.apply(COLONY, retire, 10L);

        assertEquals(EconomyMutationResult.Status.INSUFFICIENT_TREASURY, result.status());
        assertTrue(repository.find(COLONY).isEmpty());
        assertTrue(repository.transactions(COLONY).isEmpty());
    }

    @Test
    void settlementStateCanBeStoredWithoutForgingLedgerTransaction() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        ColonyEconomyState settled = new ColonyEconomyState(
            COLONY,
            0L,
            0L,
            0L,
            0L,
            0L,
            95.0D,
            0.10D,
            25L,
            12_000L,
            ColonyEconomyStateCodec.CURRENT_SCHEMA
        );

        repository.storeSettledState(settled);

        assertEquals(settled, repository.find(COLONY).orElseThrow());
        assertTrue(repository.transactions(COLONY).isEmpty());
    }
}
