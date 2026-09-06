package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class GeologicalDepositTest {
    @Test
    void depositRoundTripsDeterministicallyThroughNbt() {
        GeologicalDeposit deposit = new GeologicalDeposit(
                UUID.fromString("f7fd3be7-59f2-4d17-a9df-816f7fc458f0"),
                ResourceLocation.parse("c:ores/copper"),
                new BlockPos(128, 34, -256),
                22.5,
                0.78,
                DepositOrigin.HYDROTHERMAL);

        CompoundTag first = deposit.toTag();
        CompoundTag second = deposit.toTag();

        assertEquals(first, second, "serializing the same deposit twice must produce identical NBT");
        assertEquals(deposit, GeologicalDeposit.fromTag(first));
    }

    @Test
    void depositRejectsInvalidPhysicalParameters() {
        UUID id = UUID.fromString("f7fd3be7-59f2-4d17-a9df-816f7fc458f0");
        ResourceLocation tag = ResourceLocation.parse("c:ores/iron");
        BlockPos center = new BlockPos(0, 20, 0);

        assertThrows(
                IllegalArgumentException.class,
                () -> new GeologicalDeposit(id, tag, center, 0.0, 0.5, DepositOrigin.MAGMATIC));
        assertThrows(
                IllegalArgumentException.class,
                () -> new GeologicalDeposit(id, tag, center, 8.0, -0.01, DepositOrigin.SEDIMENTARY));
        assertThrows(
                IllegalArgumentException.class,
                () -> new GeologicalDeposit(id, tag, center, 8.0, 1.01, DepositOrigin.GENERIC));
    }
}
