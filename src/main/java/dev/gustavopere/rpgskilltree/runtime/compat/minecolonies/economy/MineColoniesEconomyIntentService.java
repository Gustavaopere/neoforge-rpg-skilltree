package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import com.minecolonies.api.colony.IColony;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomicInputs;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomySettlementService;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyCommand;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMath;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMutationResult;
import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import dev.gustavopere.rpgskilltree.core.economy.EconomyPreflight;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransactionKind;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyRepository;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData;
import dev.gustavopere.rpgskilltree.runtime.economy.EconomyIntentLimits;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;

/** Canonical server authority for MineColonies economy administrative intents. */
public final class MineColoniesEconomyIntentService {
    private MineColoniesEconomyIntentService() {}

    public static MineColoniesEconomyPreflightResult preflightMint(
        ServerPlayer player,
        IColony colony,
        NativeColonyBinding requestedBinding,
        long amount,
        ColonyEconomySavedData data
    ) {
        Validation validation = validate(player, colony, requestedBinding, amount, data, false);
        if (validation.status != MineColoniesEconomyIntentStatus.ACCEPTED) {
            return new MineColoniesEconomyPreflightResult(validation.status, null);
        }

        ColonyEconomicInputs inputs = MineColoniesEconomyAdapter.economicInputs(colony).orElse(null);
        if (inputs == null) {
            return new MineColoniesEconomyPreflightResult(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED, null);
        }

        EconomyParameters parameters = EconomyParameters.defaults();
        long capacity;
        try {
            capacity = EconomyMath.capacity(inputs, parameters);
        } catch (RuntimeException failure) {
            return new MineColoniesEconomyPreflightResult(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED, null);
        }

        EconomyColonyKey existingKey;
        try {
            existingKey = data.binding(requestedBinding).orElse(null);
        } catch (RuntimeException failure) {
            return new MineColoniesEconomyPreflightResult(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED, null);
        }
        ColonyEconomyState state = existingKey == null
            ? ColonyEconomyState.empty(new EconomyColonyKey(new UUID(0L, 1L)))
            : new ColonyEconomyRepository(data).find(existingKey).orElseGet(() -> ColonyEconomyState.empty(existingKey));

        try {
            EconomyPreflight preflight = ColonyEconomySettlementService.simulateMint(state, amount, capacity, parameters);
            return new MineColoniesEconomyPreflightResult(MineColoniesEconomyIntentStatus.ACCEPTED, preflight);
        } catch (ArithmeticException | IllegalArgumentException failure) {
            return new MineColoniesEconomyPreflightResult(MineColoniesEconomyIntentStatus.OVERFLOW, null);
        }
    }

    public static MineColoniesEconomyIntentResult mint(
        ServerPlayer player,
        IColony colony,
        NativeColonyBinding requestedBinding,
        UUID intentId,
        long amount,
        long gameTime,
        ColonyEconomySavedData data
    ) {
        return mutate(player, colony, requestedBinding, intentId, amount, gameTime, data, EconomyTransactionKind.MINT);
    }

    public static MineColoniesEconomyIntentResult retire(
        ServerPlayer player,
        IColony colony,
        NativeColonyBinding requestedBinding,
        UUID intentId,
        long amount,
        long gameTime,
        ColonyEconomySavedData data
    ) {
        return mutate(player, colony, requestedBinding, intentId, amount, gameTime, data, EconomyTransactionKind.RETIRE);
    }

    private static MineColoniesEconomyIntentResult mutate(
        ServerPlayer player,
        IColony colony,
        NativeColonyBinding requestedBinding,
        UUID intentId,
        long amount,
        long gameTime,
        ColonyEconomySavedData data,
        EconomyTransactionKind kind
    ) {
        Objects.requireNonNull(intentId, "intentId");
        Validation validation = validate(player, colony, requestedBinding, amount, data, true);
        if (validation.status != MineColoniesEconomyIntentStatus.ACCEPTED) {
            return new MineColoniesEconomyIntentResult(validation.status, null, null);
        }

        EconomyColonyKey economyKey;
        try {
            economyKey = data.resolveOrCreateBinding(requestedBinding);
        } catch (RuntimeException failure) {
            return new MineColoniesEconomyIntentResult(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED, null, null);
        }
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        EconomyCommand command = new EconomyCommand(
            intentId,
            "network:" + kind.name().toLowerCase(Locale.ROOT) + ":" + intentId,
            kind,
            amount
        );
        EconomyMutationResult result = repository.apply(economyKey, command, gameTime);
        return new MineColoniesEconomyIntentResult(map(result.status()), result.state(), result.status());
    }

    private static Validation validate(
        ServerPlayer player,
        IColony colony,
        NativeColonyBinding requestedBinding,
        long amount,
        ColonyEconomySavedData data,
        boolean mutation
    ) {
        if (player == null || colony == null || requestedBinding == null || data == null) {
            return new Validation(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED);
        }
        EconomyIntentLimits.Validation amountValidation = EconomyIntentLimits.validateAmount(amount);
        if (amountValidation == EconomyIntentLimits.Validation.INVALID_AMOUNT) {
            return new Validation(MineColoniesEconomyIntentStatus.INVALID_AMOUNT);
        }
        if (amountValidation == EconomyIntentLimits.Validation.PROTOCOL_LIMIT_EXCEEDED) {
            return new Validation(MineColoniesEconomyIntentStatus.PROTOCOL_LIMIT_EXCEEDED);
        }

        NativeColonyBinding actualBinding = MineColoniesEconomyAdapter.binding(colony).orElse(null);
        if (actualBinding == null) {
            return new Validation(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED);
        }
        if (!actualBinding.equals(requestedBinding)) {
            return new Validation(MineColoniesEconomyIntentStatus.WRONG_COLONY);
        }
        if (!MineColoniesEconomyAdapter.mayManageEconomy(player, colony)) {
            return new Validation(MineColoniesEconomyIntentStatus.PERMISSION_DENIED);
        }
        if (mutation) {
            try {
                EconomyColonyKey existing = data.binding(requestedBinding).orElse(null);
                if (existing != null && data.isArchived(existing)) {
                    return new Validation(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED);
                }
            } catch (RuntimeException failure) {
                return new Validation(MineColoniesEconomyIntentStatus.PROVIDER_READ_FAILED);
            }
        }
        return new Validation(MineColoniesEconomyIntentStatus.ACCEPTED);
    }

    private static MineColoniesEconomyIntentStatus map(EconomyMutationResult.Status status) {
        return switch (status) {
            case APPLIED -> MineColoniesEconomyIntentStatus.APPLIED;
            case DUPLICATE -> MineColoniesEconomyIntentStatus.DUPLICATE;
            case INVALID_AMOUNT -> MineColoniesEconomyIntentStatus.INVALID_AMOUNT;
            case INSUFFICIENT_TREASURY -> MineColoniesEconomyIntentStatus.INSUFFICIENT_TREASURY;
            case UNSUPPORTED_KIND -> MineColoniesEconomyIntentStatus.UNSUPPORTED_OPERATION;
            case RETENTION_LIMIT_REACHED -> MineColoniesEconomyIntentStatus.RETENTION_LIMIT_REACHED;
            case OVERFLOW -> MineColoniesEconomyIntentStatus.OVERFLOW;
        };
    }

    private record Validation(MineColoniesEconomyIntentStatus status) {}
}
