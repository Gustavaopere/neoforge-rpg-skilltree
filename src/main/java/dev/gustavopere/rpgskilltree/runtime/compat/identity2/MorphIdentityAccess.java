package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import dev.gustavopere.rpgskilltree.core.MorphAccessPolicy;
import dev.gustavopere.rpgskilltree.core.MorphClassificationPolicy;
import dev.gustavopere.rpgskilltree.core.MorphEcologyPolicy;
import dev.gustavopere.rpgskilltree.core.MorphFactionDisposition;
import dev.gustavopere.rpgskilltree.core.MorphFormDescriptor;
import dev.gustavopere.rpgskilltree.core.MorphPerceivedIdentity;
import dev.gustavopere.rpgskilltree.core.MorphPermissionResolver;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;

/** Optional Identity 2 bridge. Contains no compile-time reference to Identity 2 classes. */
public final class MorphIdentityAccess {
    private static final ResourceLocation BASE_PLAYER = ResourceLocation.fromNamespaceAndPath("minecraft", "player");

    private MorphIdentityAccess() {}

    public static boolean canMorph(ServerPlayer player, ResourceLocation identityId) {
        if (player == null || identityId == null) return false;
        if (BASE_PLAYER.equals(identityId)) return true;
        Optional<MorphFormDescriptor> descriptor = descriptor(identityId);
        if (descriptor.isEmpty()) return false;
        return MorphAccessPolicy.canUse(
            descriptor.get(),
            MorphPermissionResolver.resolve(PlayerProgressionRuntime.get(player))
        );
    }

    /** Central perceived-identity projection used by future AI/faction adapters. */
    public static Optional<MorphPerceivedIdentity> perceivedIdentity(ResourceLocation identityId) {
        if (identityId == null || BASE_PLAYER.equals(identityId)) return Optional.empty();
        return descriptor(identityId).map(form -> MorphEcologyPolicy.perceivedIdentity(
            form,
            MorphCategoryCatalog.factionsByEntity(),
            MorphCategoryCatalog.traitsByEntity()
        ));
    }

    /** Resolves the ecological reaction an observer should have to a supplied Identity 2 form. */
    public static MorphFactionDisposition ecologicalDisposition(
        ServerPlayer player,
        ResourceLocation observerEntityId,
        ResourceLocation identityId
    ) {
        if (player == null || observerEntityId == null || identityId == null) {
            return MorphFactionDisposition.NEUTRAL;
        }
        Optional<MorphPerceivedIdentity> perceived = perceivedIdentity(identityId);
        if (perceived.isEmpty()) return MorphFactionDisposition.NEUTRAL;
        return MorphEcologyPolicy.disposition(
            observerEntityId.toString(),
            MorphEcologyPolicy.factionsFor(observerEntityId.toString(), MorphCategoryCatalog.factionsByEntity()),
            perceived.get(),
            MorphCategoryCatalog.factionRelations(),
            MorphHostilityMemoryRuntime.memory(player),
            System.currentTimeMillis()
        );
    }

    /** Marks explicitly mapped target factions as temporarily aware of the player's hostile action. */
    public static void recordHostilityAgainst(ServerPlayer player, ResourceLocation targetEntityId) {
        if (player == null || targetEntityId == null) return;
        MorphHostilityMemoryRuntime.compromise(
            player,
            MorphEcologyPolicy.factionsFor(targetEntityId.toString(), MorphCategoryCatalog.factionsByEntity())
        );
    }

    private static Optional<MorphFormDescriptor> descriptor(ResourceLocation identityId) {
        if (!BuiltInRegistries.ENTITY_TYPE.containsKey(identityId)) return Optional.empty();
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(identityId);
        if (type == null) return Optional.empty();
        return Optional.of(MorphClassificationPolicy.describe(
            identityId.toString(),
            type.getCategory().getName(),
            MorphCategoryCatalog.overrides(),
            MorphCategoryCatalog.blacklist()
        ));
    }
}
