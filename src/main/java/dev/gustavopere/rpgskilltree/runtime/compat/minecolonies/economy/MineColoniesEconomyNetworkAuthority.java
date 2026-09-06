package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.IColonyManager;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomicInputs;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMath;
import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import dev.gustavopere.rpgskilltree.core.economy.EconomyPreflight;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyConfigSnapshot;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyRepository;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyServerConfig;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomyColonyContext;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomyMintPreflightResultPayload;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomySnapshotPayload;
import java.util.UUID;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

/** Server-only resolution and authorization boundary for economy network intents. */
public final class MineColoniesEconomyNetworkAuthority {
    private MineColoniesEconomyNetworkAuthority() {}

    public static void sendSnapshot(ServerPlayer player, EconomyColonyContext context) {
        Resolved resolved = resolve(player, context);
        if (resolved == null) return;
        PacketDistributor.sendToPlayer(player, snapshot(resolved, context));
    }

    public static void sendMintPreflight(ServerPlayer player, EconomyColonyContext context, long amount) {
        Resolved resolved = resolve(player, context);
        if (resolved == null) return;
        ColonyEconomyConfigSnapshot config = ColonyEconomyServerConfig.snapshot();

        MineColoniesEconomyPreflightResult result = MineColoniesEconomyIntentService.preflightMint(
            player, resolved.colony(), resolved.binding(), amount, resolved.data(), config
        );
        EconomyMintPreflightResultPayload.Projection projection = result.preflight()
            .map(MineColoniesEconomyNetworkAuthority::projection)
            .orElseGet(EconomyMintPreflightResultPayload.Projection::unavailable);
        PacketDistributor.sendToPlayer(
            player,
            new EconomyMintPreflightResultPayload(context, result.status().name(), projection)
        );
    }

    public static void applyMint(ServerPlayer player, EconomyColonyContext context, UUID intentId, long amount) {
        Resolved resolved = resolve(player, context);
        if (resolved == null) return;
        long gameTime = resolved.server().overworld().getGameTime();
        ColonyEconomyConfigSnapshot config = ColonyEconomyServerConfig.snapshot();
        MineColoniesEconomyIntentResult result = MineColoniesEconomyIntentService.mint(
            player, resolved.colony(), resolved.binding(), intentId, amount, gameTime, resolved.data(), config
        );
        finishMutation(player, context, resolved, result);
    }

    public static void applyRetire(ServerPlayer player, EconomyColonyContext context, UUID intentId, long amount) {
        Resolved resolved = resolve(player, context);
        if (resolved == null) return;
        long gameTime = resolved.server().overworld().getGameTime();
        ColonyEconomyConfigSnapshot config = ColonyEconomyServerConfig.snapshot();
        MineColoniesEconomyIntentResult result = MineColoniesEconomyIntentService.retire(
            player, resolved.colony(), resolved.binding(), intentId, amount, gameTime, resolved.data(), config
        );
        finishMutation(player, context, resolved, result);
    }

    private static void finishMutation(
        ServerPlayer player,
        EconomyColonyContext context,
        Resolved resolved,
        MineColoniesEconomyIntentResult result
    ) {
        if (result.status() != MineColoniesEconomyIntentStatus.APPLIED
            && result.status() != MineColoniesEconomyIntentStatus.DUPLICATE) {
            player.sendSystemMessage(Component.literal("Economy action rejected: " + result.status().name()));
        }
        PacketDistributor.sendToPlayer(player, snapshot(resolved, context));
    }

    private static EconomySnapshotPayload snapshot(Resolved resolved, EconomyColonyContext context) {
        ColonyEconomyRepository repository = new ColonyEconomyRepository(resolved.data());
        ColonyEconomyState state = null;
        boolean initialized = false;
        try {
            var key = resolved.data().binding(resolved.binding()).orElse(null);
            if (key != null) {
                state = repository.find(key).orElse(null);
                initialized = state != null;
            }
        } catch (RuntimeException failure) {
            return unavailableSnapshot(context);
        }

        long capacity = currentCapacity(resolved.colony(), ColonyEconomyServerConfig.snapshot().parameters());
        if (state == null) {
            return new EconomySnapshotPayload(
                context,
                new EconomySnapshotPayload.Balances(0L, 0L, 0L, 0L, 0L),
                new EconomySnapshotPayload.Metrics(capacity, 0L, 100.0D, 0.10D, false)
            );
        }
        return new EconomySnapshotPayload(
            context,
            new EconomySnapshotPayload.Balances(
                state.issuedSupply(),
                state.retiredSupply(),
                state.treasuryBalance(),
                state.reservedBalance(),
                state.activeCirculation()
            ),
            new EconomySnapshotPayload.Metrics(
                state.currentEconomicCapacity() > 0L ? state.currentEconomicCapacity() : capacity,
                state.lastSettlementTick(),
                state.priceIndex(),
                state.taxRate(),
                initialized
            )
        );
    }

    private static long currentCapacity(IColony colony, EconomyParameters parameters) {
        ColonyEconomicInputs inputs = MineColoniesEconomyAdapter.economicInputs(colony).orElse(null);
        if (inputs == null) return 0L;
        try {
            return EconomyMath.capacity(inputs, parameters);
        } catch (RuntimeException failure) {
            return 0L;
        }
    }

    private static EconomySnapshotPayload unavailableSnapshot(EconomyColonyContext context) {
        return new EconomySnapshotPayload(
            context,
            new EconomySnapshotPayload.Balances(0L, 0L, 0L, 0L, 0L),
            new EconomySnapshotPayload.Metrics(0L, 0L, 100.0D, 0.10D, false)
        );
    }

    private static EconomyMintPreflightResultPayload.Projection projection(EconomyPreflight value) {
        return new EconomyMintPreflightResultPayload.Projection(
            value.currentEffectiveSupply(),
            value.projectedEffectiveSupply(),
            value.economicCapacity(),
            value.currentPriceIndex(),
            value.projectedTargetPriceIndex()
        );
    }

    private static Resolved resolve(ServerPlayer player, EconomyColonyContext context) {
        if (player == null || context == null) return null;
        MinecraftServer server = player.serverLevel().getServer();
        ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, context.dimensionId());
        IColony colony;
        try {
            colony = IColonyManager.getInstance().getColonyByDimension(context.colonyId(), dimension);
        } catch (RuntimeException | LinkageError failure) {
            return null;
        }
        if (colony == null) {
            player.sendSystemMessage(Component.literal("Economy colony context not found."));
            return null;
        }
        NativeColonyBinding binding = MineColoniesEconomyAdapter.binding(colony).orElse(null);
        if (binding == null
            || binding.colonyId() != context.colonyId()
            || !binding.dimensionId().equals(context.dimensionId())) {
            player.sendSystemMessage(Component.literal("Economy colony context mismatch."));
            return null;
        }
        if (!MineColoniesEconomyAdapter.mayManageEconomy(player, colony)) {
            player.sendSystemMessage(Component.literal("You do not have permission to manage this colony economy."));
            return null;
        }
        return new Resolved(server, colony, binding, ColonyEconomySavedData.get(server));
    }

    private record Resolved(
        MinecraftServer server,
        IColony colony,
        NativeColonyBinding binding,
        ColonyEconomySavedData data
    ) {}
}
