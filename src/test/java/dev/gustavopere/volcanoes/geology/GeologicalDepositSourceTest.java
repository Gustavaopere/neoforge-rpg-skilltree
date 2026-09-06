package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class GeologicalDepositSourceTest {
    @Test
    void registryCanBeConsumedThroughReadOnlyIntegrationSource() {
        GeologicalDeposit copper = deposit(
                "10000000-0000-0000-0000-000000000001",
                "c:ores/copper",
                new BlockPos(10, 20, 10));
        GeologicalDeposit gold = deposit(
                "20000000-0000-0000-0000-000000000002",
                "c:ores/gold",
                new BlockPos(200, 20, 200));

        DepositRegistry registry = new DepositRegistry();
        registry.register(gold);
        registry.register(copper);

        GeologicalDepositSource source = registry;
        assertEquals(List.of(copper, gold), source.all());
        assertEquals(List.of(copper), source.nearby(new BlockPos(0, 20, 0), 32.0));
    }

    private static GeologicalDeposit deposit(String id, String resourceTag, BlockPos center) {
        return new GeologicalDeposit(
                UUID.fromString(id),
                ResourceLocation.parse(resourceTag),
                center,
                12.0,
                0.65,
                DepositOrigin.HYDROTHERMAL);
    }
}
