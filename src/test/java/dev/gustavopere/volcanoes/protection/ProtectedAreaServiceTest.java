package dev.gustavopere.volcanoes.protection;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProtectedAreaServiceTest {
    @Test
    void noProvidersFailClosedForDestructiveTerrainMutation() {
        ProtectedAreaService service = ProtectedAreaService.empty();

        assertFalse(service.isProtected(Level.OVERWORLD, BlockPos.ZERO));
        assertFalse(service.allowsTerrainMutation());
        assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
    }

    @Test
    void nonAuthoritativeProvidersCanProtectButCannotAuthorizeMutation() {
        ProtectedAreaService service = ProtectedAreaService.of((dimension, pos) -> false);

        assertFalse(service.isProtected(Level.OVERWORLD, BlockPos.ZERO));
        assertFalse(service.allowsTerrainMutation());
        assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
    }

    @Test
    void authoritativeEmptyProviderSetRepresentsConfirmedNoActiveClaims() {
        ProtectedAreaService service = ProtectedAreaService.authoritative();

        assertTrue(service.allowsTerrainMutation());
        assertFalse(service.isProtected(Level.OVERWORLD, BlockPos.ZERO));
        assertTrue(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
    }

    @Test
    void authoritativeProvidersPermitOnlyUnprotectedTerrain() {
        ProtectedAreaService service = ProtectedAreaService.authoritative(
                (dimension, pos) -> false,
                (dimension, pos) -> pos.equals(new BlockPos(12, 64, -8)));

        BlockPos protectedPos = new BlockPos(12, 64, -8);
        BlockPos ordinaryPos = BlockPos.ZERO;

        assertTrue(service.allowsTerrainMutation());
        assertTrue(service.isProtected(Level.OVERWORLD, protectedPos));
        assertFalse(service.mayMutate(Level.OVERWORLD, protectedPos));
        assertFalse(service.isProtected(Level.OVERWORLD, ordinaryPos));
        assertTrue(service.mayMutate(Level.OVERWORLD, ordinaryPos));
    }

    @Test
    void authoritativeProviderRuntimeFailureFailsClosed() {
        ProtectedAreaService service = ProtectedAreaService.authoritative((dimension, pos) -> {
            throw new IllegalStateException("simulated provider mismatch");
        });

        assertTrue(service.isProtected(Level.OVERWORLD, BlockPos.ZERO));
        assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
    }

    @Test
    void authoritativeProviderLinkageFailureFailsClosed() {
        ProtectedAreaService service = ProtectedAreaService.authoritative((dimension, pos) -> {
            throw new NoSuchMethodError("simulated optional-mod API drift");
        });

        assertTrue(service.isProtected(Level.OVERWORLD, BlockPos.ZERO));
        assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
    }
}
