package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class CanonicalSkillInvestmentResourcesJUnitTest {
    @Test
    void everyCanonicalMainSkillPublishesExplicitInvestmentMetadata() throws IOException {
        Path root = Path.of("src/main/resources/data/rpgskilltree/skills/main");
        Map<ResourceLocation, com.google.gson.JsonElement> resources = new LinkedHashMap<>();
        try (var paths = Files.list(root)) {
            for (Path path : paths.filter(p -> p.getFileName().toString().endsWith(".json")).sorted().toList()) {
                resources.put(
                    ResourceLocation.parse("rpgskilltree:skills/main/" + path.getFileName()),
                    JsonParser.parseString(Files.readString(path))
                );
            }
        }

        var metadata = SkillInvestmentMetadataParser.parse(resources);
        assertFalse(resources.isEmpty());
        assertEquals(resources.size(), metadata.size());
        assertFalse(
            metadata.values().stream().allMatch(value -> value.domainWeightsPerRank().isEmpty()),
            "canonical resources must contain domain-bearing skills"
        );
    }
}
