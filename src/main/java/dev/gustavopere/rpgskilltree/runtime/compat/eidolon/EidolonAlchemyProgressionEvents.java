package dev.gustavopere.rpgskilltree.runtime.compat.eidolon;

import alexthw.eidolon_repraised.common.tile.CrucibleTileEntity;
import alexthw.eidolon_repraised.recipe.CrucibleHelper;
import alexthw.eidolon_repraised.recipe.CrucibleRecipe;
import alexthw.eidolon_repraised.registries.EidolonRecipes;
import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.EidolonAlchemyAction;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * Optional Eidolon crucible adapter.
 *
 * Player interactions/tosses only establish authorship. Mastery is confirmed
 * from Eidolon's own successful result spawn while the Crucible still exposes
 * the completed recipe steps. No ingredient stack is modified by this bridge.
 */
public final class EidolonAlchemyProgressionEvents {
    private static final Map<CrucibleKey, PendingContributor> CONTRIBUTORS = new HashMap<>();
    private static final long MAX_CONTRIBUTION_AGE_TICKS = 20L * 60L * 2L;
    private static final int TOSS_SEARCH_RADIUS = 2;

    private EidolonAlchemyProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCrucibleInteraction(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        if (!(player.level().getBlockEntity(event.getPos()) instanceof CrucibleTileEntity)) return;
        remember(player, event.getPos());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIngredientToss(ItemTossEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player) || !eligible(player)) return;
        ItemEntity tossed = event.getEntity();
        BlockPos cruciblePos = nearestActiveCrucible(player.serverLevel(), tossed.blockPosition());
        if (cruciblePos != null) remember(player, cruciblePos);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level) || !(event.getEntity() instanceof ItemEntity item) || event.loadedFromDisk()) return;

        BlockPos pos = item.blockPosition();
        if (!(level.getBlockEntity(pos) instanceof CrucibleTileEntity crucible)) return;

        CrucibleRecipe recipe = CrucibleHelper.find(level, crucible.getSteps());
        if (recipe == null || !matchesResult(item.getItem(), recipe.getResult())) return;

        String recipeId = stableRecipeId(level, recipe);
        if (recipeId == null) return;

        CrucibleKey key = new CrucibleKey(level.dimension(), pos.immutable());
        PendingContributor pending = CONTRIBUTORS.remove(key);
        if (pending == null) return;
        long age = level.getGameTime() - pending.gameTime();
        if (age < 0L || age > MAX_CONTRIBUTION_AGE_TICKS) return;

        ServerPlayer player = level.getServer().getPlayerList().getPlayer(pending.playerId());
        if (player == null || !eligible(player) || !player.level().dimension().equals(level.dimension())) return;

        confirm(player, recipeId);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        CONTRIBUTORS.entrySet().removeIf(entry -> entry.getValue().playerId().equals(playerId));
    }

    private static void confirm(ServerPlayer player, String recipeId) {
        String discoveryKey = "eidolon:alchemy:first/" + recipeId;
        var before = PlayerProgressionRuntime.get(player);
        boolean firstCompletion = !before.discoveries().contains(discoveryKey);

        EidolonAlchemyAction action = new EidolonAlchemyAction(
            new ActionOrigin("eidolon:alchemy", 0),
            recipeId,
            true,
            firstCompletion
        );
        var afterMastery = PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forEidolonAlchemy(action));
        if (firstCompletion) {
            PlayerProgressionRuntime.set(player, afterMastery.withDiscoveries(afterMastery.discoveries().add(discoveryKey)));
        }
    }

    private static void remember(ServerPlayer player, BlockPos pos) {
        CONTRIBUTORS.put(
            new CrucibleKey(player.level().dimension(), pos.immutable()),
            new PendingContributor(player.getUUID(), player.level().getGameTime())
        );
    }

    private static BlockPos nearestActiveCrucible(ServerLevel level, BlockPos origin) {
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (int dx = -TOSS_SEARCH_RADIUS; dx <= TOSS_SEARCH_RADIUS; dx++) {
            for (int dy = -TOSS_SEARCH_RADIUS; dy <= TOSS_SEARCH_RADIUS; dy++) {
                for (int dz = -TOSS_SEARCH_RADIUS; dz <= TOSS_SEARCH_RADIUS; dz++) {
                    BlockPos candidate = origin.offset(dx, dy, dz);
                    if (!(level.getBlockEntity(candidate) instanceof CrucibleTileEntity crucible)) continue;
                    var state = crucible.saveWithoutMetadata(level.registryAccess());
                    if (!state.getBoolean("boiling") || !state.getBoolean("hasWater")) continue;
                    double distance = candidate.distSqr(origin);
                    if (distance < bestDistance) {
                        best = candidate.immutable();
                        bestDistance = distance;
                    }
                }
            }
        }
        return best;
    }

    private static String stableRecipeId(ServerLevel level, CrucibleRecipe recipe) {
        return level.getRecipeManager().getAllRecipesFor(EidolonRecipes.CRUCIBLE_TYPE.get()).stream()
            .filter(holder -> holder.value() == recipe)
            .map(holder -> holder.id().toString())
            .findFirst()
            .orElse(null);
    }

    private static boolean matchesResult(ItemStack actual, ItemStack expected) {
        return actual.getCount() == expected.getCount() && ItemStack.isSameItemSameComponents(actual, expected);
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    private record CrucibleKey(ResourceKey<Level> dimension, BlockPos pos) {}
    private record PendingContributor(UUID playerId, long gameTime) {}
}
