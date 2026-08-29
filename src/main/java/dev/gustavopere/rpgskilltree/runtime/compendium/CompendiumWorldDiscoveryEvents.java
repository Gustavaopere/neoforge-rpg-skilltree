package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryCriterion;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryOrigin;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoverySignal;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryState;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryTriggerType;
import dev.gustavopere.rpgskilltree.compendium.world.WorldDiscoveryPolicy;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureManager;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Bounded, server-observed structure discovery for Stage 10.08. */
public final class CompendiumWorldDiscoveryEvents {
    private static final int STRUCTURE_SAMPLE_INTERVAL_TICKS = 100;

    private CompendiumWorldDiscoveryEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % 100 != 0) return;
        discoverCurrentStructure(player);
    }

    private static void discoverCurrentStructure(ServerPlayer player) {
        ServerLevel level = (ServerLevel) player.level();
        StructureManager structureManager = level.structureManager();
        BlockPos pos = player.blockPosition();
        StructureStart start = structureManager.getStructureWithPieceAt(pos, holder -> true);
        if (start == StructureStart.INVALID_START || !start.isValid()) return;
        if (!structureManager.structureHasPieceAt(pos, start)) return;

        Registry<Structure> structureRegistry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
        ResourceLocation structureId = structureRegistry.getKey(start.getStructure());
        if (structureId == null) return;

        Optional<CompendiumEntryId> confirmed = WorldDiscoveryPolicy.confirmStructure(structureId.toString(), true);
        if (confirmed.isEmpty()) return;
        CompendiumEntryId entryId = confirmed.get();
        if (CompendiumDiscoveryRuntime.progress(player).record(entryId)
            .filter(record -> record.completedObjectiveIds().contains("structure_entry"))
            .isPresent()) {
            return;
        }

        DiscoveryCriterion criterion = new DiscoveryCriterion(
            "builtin:structure_entry:" + entryId.serializedId(),
            entryId,
            DiscoveryTriggerType.STRUCTURE_ENTRY,
            DiscoveryState.SEEN,
            Optional.of("structure_entry"),
            List.of()
        );
        DiscoverySignal signal = new DiscoverySignal(
            entryId,
            DiscoveryTriggerType.STRUCTURE_ENTRY,
            level.getGameTime(),
            Optional.of(origin(level, pos)),
            Optional.empty()
        );
        CompendiumDiscoveryRuntime.apply(player, criterion, signal);
    }

    private static DiscoveryOrigin origin(ServerLevel level, BlockPos pos) {
        return new DiscoveryOrigin(
            level.dimension().location().toString(),
            pos.getX() >> 4,
            pos.getZ() >> 4
        );
    }
}
