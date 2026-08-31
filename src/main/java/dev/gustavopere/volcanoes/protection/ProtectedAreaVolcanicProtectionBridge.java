package dev.gustavopere.volcanoes.protection;

import dev.gustavopere.volcanoes.volcano.VolcanicProtectionService;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Objects;

/** Bridge from generic protected areas into the Stage-03 volcanic terrain safety contract. */
public final class ProtectedAreaVolcanicProtectionBridge implements VolcanicProtectionService {
    private final ProtectedAreaService protectedAreas;

    public ProtectedAreaVolcanicProtectionBridge(ProtectedAreaService protectedAreas) {
        this.protectedAreas = Objects.requireNonNull(protectedAreas, "protectedAreas");
    }

    @Override
    public boolean isProtected(ServerLevel level, BlockPos pos) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(pos, "pos");
        return protectedAreas.isProtected(level.dimension(), pos);
    }

    @Override
    public boolean allowsTerrainMutation() {
        return protectedAreas.allowsTerrainMutation();
    }
}
