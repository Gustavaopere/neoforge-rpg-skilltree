package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;

final class VolcanoWorldgenTerrainHintAvailabilityTest {
    @Test
    void unavailableWorldgenChunkMustNotTriggerBiomeLookup() {
        AtomicBoolean biomeRead = new AtomicBoolean(false);
        LevelReader level = (LevelReader) Proxy.newProxyInstance(
                LevelReader.class.getClassLoader(),
                new Class<?>[]{LevelReader.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "getMinBuildHeight" -> -64;
                    case "getMaxBuildHeight" -> 320;
                    case "getSeaLevel" -> 63;
                    case "hasChunkAt" -> false;
                    case "getBiome" -> {
                        biomeRead.set(true);
                        throw new AssertionError("biome lookup must not touch an unavailable worldgen chunk");
                    }
                    case "toString" -> "UnavailableChunkLevelReader";
                    case "hashCode" -> System.identityHashCode(proxy);
                    case "equals" -> proxy == args[0];
                    default -> throw new UnsupportedOperationException(method.toString());
                });

        boolean volcanic = VolcanoWorldgenTerrainHints.forLevel(level)
                .isVolcanic(new BlockPos(-1_015, 0, -15_641));

        assertFalse(volcanic, "unavailable terrain data must fail closed to no positive terrain hint");
        assertFalse(biomeRead.get(), "availability must be checked before biome sampling");
    }
}
