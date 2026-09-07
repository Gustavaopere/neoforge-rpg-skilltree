package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.runtime.compat.create.A0079CreateTransportCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.junit.jupiter.api.Test;

final class A0071A0080EpicFightCreateTransportCoverageJUnitTest {
    @Test
    void createTransportProbeFailsClosedWhenNeitherFeetBlockIsABelt() {
        ServerLevel level = mock(ServerLevel.class);
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.blockPosition()).thenReturn(BlockPos.ZERO);
        when(player.level()).thenReturn(level);

        assertFalse(A0079CreateTransportCompat.onActiveBelt(player));

        verify(level).getBlockEntity(BlockPos.ZERO);
        verify(level).getBlockEntity(BlockPos.ZERO.below());
    }
}
