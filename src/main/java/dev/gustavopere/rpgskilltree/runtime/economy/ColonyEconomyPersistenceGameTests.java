package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyCommand;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMutationResult;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransactionKind;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Server-context persistence contract for economy state, binding fingerprint and replay identity. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class ColonyEconomyPersistenceGameTests {
    private ColonyEconomyPersistenceGameTests() {}

    @GameTest(template = "foundation_empty", timeoutTicks = 100)
    public static void saveReloadPreservesTransactionIdentity(GameTestHelper helper) {
        NativeColonyBinding nativeBinding = new NativeColonyBinding(
            ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"),
            77,
            UUID.fromString("00000000-0000-0000-0000-000000001701"),
            new BlockPos(16, 64, 16)
        );
        UUID transactionId = UUID.fromString("00000000-0000-0000-0000-000000001702");
        EconomyCommand command = new EconomyCommand(
            transactionId,
            "gametest:reload-mint",
            EconomyTransactionKind.MINT,
            40L
        );

        ColonyEconomySavedData originalData = new ColonyEconomySavedData();
        EconomyColonyKey economyKey = originalData.resolveOrCreateBinding(nativeBinding);
        ColonyEconomyRepository originalRepository = new ColonyEconomyRepository(originalData);
        EconomyMutationResult applied = originalRepository.apply(economyKey, command, 1_000L);
        helper.assertTrue(applied.status() == EconomyMutationResult.Status.APPLIED,
            "initial mint must be applied before persistence round-trip");

        ColonyEconomySavedData reloadedData = ColonyEconomySavedData.decodeForTest(originalData.encodeForTest());
        EconomyColonyKey rebound = reloadedData.resolveOrCreateBinding(nativeBinding);
        helper.assertTrue(economyKey.equals(rebound), "same fingerprint must recover the same economy UUID");

        ColonyEconomyRepository reloadedRepository = new ColonyEconomyRepository(reloadedData);
        EconomyMutationResult replay = reloadedRepository.apply(rebound, command, 1_001L);
        helper.assertTrue(replay.status() == EconomyMutationResult.Status.DUPLICATE,
            "persisted transaction identity must reject replay after reload");
        helper.assertTrue(reloadedRepository.find(rebound).orElseThrow().issuedSupply() == 40L,
            "replay after reload must not mint supply twice");
        helper.assertTrue(reloadedRepository.transactions(rebound).size() == 1,
            "reload must preserve exactly one canonical ledger entry");
        helper.succeed();
    }
}
