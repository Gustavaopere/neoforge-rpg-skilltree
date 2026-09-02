package dev.gustavopere.volcanoes.compat.minecolonies;

import dev.gustavopere.volcanoes.protection.ProtectedAreaService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MineColoniesCompatTest {
    @Test
    void absentHostEstablishesAuthoritativeZeroProviderWithoutResolvingFactory() {
        AtomicBoolean factoryCalled = new AtomicBoolean(false);

        ProtectedAreaService service = MineColoniesCompat.serviceForState(false, false, () -> {
            factoryCalled.set(true);
            throw new AssertionError("optional provider must not initialize when MineColonies is absent");
        });

        assertTrue(service.allowsTerrainMutation());
        assertTrue(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
        assertFalse(factoryCalled.get());
    }

    @Test
    void wrongVersionFailsClosedWithoutResolvingFactory() {
        AtomicBoolean factoryCalled = new AtomicBoolean(false);

        ProtectedAreaService service = MineColoniesCompat.serviceForState(true, false, () -> {
            factoryCalled.set(true);
            throw new AssertionError("provider must not initialize for an unverified MineColonies version");
        });

        assertFalse(service.allowsTerrainMutation());
        assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
        assertFalse(factoryCalled.get());
    }

    @Test
    void exactVersionConstructionMismatchDoesNotEstablishMutationAuthority() {
        ProtectedAreaService service = MineColoniesCompat.serviceForState(true, true, () -> {
            throw new NoSuchMethodError("simulated MineColonies API drift");
        });

        assertFalse(service.allowsTerrainMutation());
        assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
    }

    @Test
    void exactVersionCompatibleApiEstablishesAuthoritativeProtectionService() {
        ProtectedAreaService.Provider provider = (dimension, pos) -> pos.equals(BlockPos.ZERO);

        ProtectedAreaService service = MineColoniesCompat.serviceForState(true, true, () -> provider);

        assertTrue(service.allowsTerrainMutation());
        assertTrue(service.isProtected(Level.OVERWORLD, BlockPos.ZERO));
        assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO));
        assertTrue(service.mayMutate(Level.OVERWORLD, new BlockPos(16, 64, 16)));
    }
}
