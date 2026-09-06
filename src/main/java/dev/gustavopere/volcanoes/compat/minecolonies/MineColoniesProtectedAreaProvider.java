package dev.gustavopere.volcanoes.compat.minecolonies;

import com.minecolonies.api.colony.IColonyManager;
import dev.gustavopere.volcanoes.protection.ProtectedAreaService;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.Objects;

/** MineColonies claim-boundary adapter for the verified NeoForge 1.21.1 API line. */
final class MineColoniesProtectedAreaProvider implements ProtectedAreaService.Provider {
    @Override
    public boolean isProtected(ResourceKey<Level> dimension, BlockPos pos) {
        Objects.requireNonNull(dimension, "dimension");
        Objects.requireNonNull(pos, "pos");
        return IColonyManager.getInstance().getClaimData(dimension, new ChunkPos(pos)) != null;
    }
}
