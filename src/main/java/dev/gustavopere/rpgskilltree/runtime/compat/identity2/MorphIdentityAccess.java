package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import dev.gustavopere.rpgskilltree.core.MorphAccessPolicy;
import dev.gustavopere.rpgskilltree.core.MorphClassificationPolicy;
import dev.gustavopere.rpgskilltree.core.MorphPermissionResolver;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
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
        if (!BuiltInRegistries.ENTITY_TYPE.containsKey(identityId)) return false;

        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(identityId);
        if (type == null) return false;
        var descriptor = MorphClassificationPolicy.describe(
            identityId.toString(),
            type.getCategory().getName(),
            MorphCategoryCatalog.overrides(),
            MorphCategoryCatalog.blacklist()
        );
        return MorphAccessPolicy.canUse(
            descriptor,
            MorphPermissionResolver.resolve(PlayerProgressionRuntime.get(player))
        );
    }
}
