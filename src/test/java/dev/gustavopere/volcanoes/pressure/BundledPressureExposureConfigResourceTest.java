package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class BundledPressureExposureConfigResourceTest {
    @Test
    void bundledDefaultResourceMatchesBuiltInFallback() throws Exception {
        Path path = Path.of("src/main/resources/data/volcanoes/pressure_exposure/default.json");
        assertTrue(Files.isRegularFile(path));

        PressureExposureConfig parsed = PressureExposureConfigDataLoader.parse(
                JsonParser.parseString(Files.readString(path)));
        assertEquals(PressureExposureConfig.defaults(), parsed);
    }
}
