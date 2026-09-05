package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomySettlementService;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ColonyEconomyIntentJUnitTest {
    @Test
    void protocolAmountValidationIsSeparateFromMonetaryPolicy() {
        assertEquals(EconomyIntentLimits.Validation.INVALID_AMOUNT, EconomyIntentLimits.validateAmount(-1L));
        assertEquals(EconomyIntentLimits.Validation.INVALID_AMOUNT, EconomyIntentLimits.validateAmount(0L));
        assertEquals(EconomyIntentLimits.Validation.ACCEPTED, EconomyIntentLimits.validateAmount(1L));
        assertEquals(
            EconomyIntentLimits.Validation.ACCEPTED,
            EconomyIntentLimits.validateAmount(EconomyIntentLimits.MAX_MUTATION_AMOUNT)
        );
        assertEquals(
            EconomyIntentLimits.Validation.PROTOCOL_LIMIT_EXCEEDED,
            EconomyIntentLimits.validateAmount(EconomyIntentLimits.MAX_MUTATION_AMOUNT + 1L)
        );
    }

    @Test
    void mintPreflightDoesNotMutateSourceState() {
        ColonyEconomyState source = ColonyEconomyState.empty(
            new EconomyColonyKey(UUID.fromString("00000000-0000-0000-0000-000000001601"))
        );

        var preflight = ColonyEconomySettlementService.simulateMint(
            source,
            20L,
            2L,
            EconomyParameters.defaults()
        );

        assertEquals(0L, source.issuedSupply());
        assertEquals(0L, source.treasuryBalance());
        assertEquals(20L, preflight.projectedEffectiveSupply());
        assertEquals(source, preflight.sourceState());
    }
}
