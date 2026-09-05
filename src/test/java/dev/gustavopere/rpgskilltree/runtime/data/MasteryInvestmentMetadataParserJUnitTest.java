package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class MasteryInvestmentMetadataParserJUnitTest {
    @Test
    void parsesExplicitCanonicalThresholdWithoutInferringFromLaneName() {
        var parsed = MasteryInvestmentMetadataParser.parse(Map.of(
            ResourceLocation.parse("rpgskilltree:mastery_investments/arcane_60"),
            JsonParser.parseString("""
                {
                  "lane": "magic:casting",
                  "minimum_experience": 60,
                  "domain_weights": {"ARCANE": 2},
                  "tags": ["rpgskilltree:mastery/arcane_practice"]
                }
                """)
        ));

        assertEquals(1, parsed.size());
        var metadata = parsed.getFirst();
        assertEquals("magic:casting", metadata.laneId());
        assertEquals(60, metadata.minimumExperience());
        assertEquals(Map.of(ProgressionDomain.ARCANE, 2), metadata.domainWeights());
        assertEquals(java.util.Set.of("rpgskilltree:mastery/arcane_practice"), metadata.tags());
    }

    @Test
    void rejectsLaneThatIsNotCanonical() {
        var resources = Map.of(
            ResourceLocation.parse("rpgskilltree:mastery_investments/unknown"),
            JsonParser.parseString("""
                {
                  "lane": "unknown:casting",
                  "minimum_experience": 60,
                  "domain_weights": {"ARCANE": 1},
                  "tags": []
                }
                """)
        );

        assertThrows(SkillTreeDataValidationException.class, () -> MasteryInvestmentMetadataParser.parse(resources));
    }

    @Test
    void rejectsDuplicateLaneAndThresholdAcrossResources() {
        var resources = Map.of(
            ResourceLocation.parse("rpgskilltree:mastery_investments/one"),
            JsonParser.parseString("""
                {
                  "lane": "magic:casting",
                  "minimum_experience": 60,
                  "domain_weights": {"ARCANE": 1},
                  "tags": []
                }
                """),
            ResourceLocation.parse("rpgskilltree:mastery_investments/two"),
            JsonParser.parseString("""
                {
                  "lane": "magic:casting",
                  "minimum_experience": 60,
                  "domain_weights": {"ARCANE": 2},
                  "tags": ["rpgskilltree:mastery/duplicate"]
                }
                """)
        );

        assertThrows(SkillTreeDataValidationException.class, () -> MasteryInvestmentMetadataParser.parse(resources));
    }

    @Test
    void permitsMultipleExplicitThresholdsForSameCanonicalLane() {
        var parsed = MasteryInvestmentMetadataParser.parse(Map.of(
            ResourceLocation.parse("rpgskilltree:mastery_investments/arcane_80"),
            JsonParser.parseString("""
                {
                  "lane": "magic:casting",
                  "minimum_experience": 80,
                  "domain_weights": {"ARCANE": 1},
                  "tags": ["rpgskilltree:mastery/arcane_advanced"]
                }
                """),
            ResourceLocation.parse("rpgskilltree:mastery_investments/arcane_60"),
            JsonParser.parseString("""
                {
                  "lane": "magic:casting",
                  "minimum_experience": 60,
                  "domain_weights": {"ARCANE": 1},
                  "tags": []
                }
                """)
        ));

        assertEquals(2, parsed.size());
        assertEquals(60, parsed.get(0).minimumExperience());
        assertEquals(80, parsed.get(1).minimumExperience());
    }
}
