package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SkillInvestmentMetadataParserJUnitTest {
    @Test
    void explicitDomainTagContributesOnePointPerRankAndPreservesTags() {
        var metadata = SkillInvestmentMetadataParser.parse(Map.of(
            ResourceLocation.parse("rpgskilltree:skills/main/arcane_001.json"),
            JsonParser.parseString("""
                {
                  "id": "rpgskilltree:arcane_001",
                  "tags": ["rpgskilltree:domain/arcane", "rpgskilltree:kind/domain"]
                }
                """)
        )).get("rpgskilltree:arcane_001");

        assertEquals(Map.of(ProgressionDomain.ARCANE, 1), metadata.domainWeightsPerRank());
        assertTrue(metadata.tags().contains("rpgskilltree:domain/arcane"));
        assertTrue(metadata.tags().contains("rpgskilltree:kind/domain"));
    }

    @Test
    void coreDomainIsExplicitlyNeutral() {
        var metadata = SkillInvestmentMetadataParser.parse(Map.of(
            ResourceLocation.parse("rpgskilltree:skills/main/core_00.json"),
            JsonParser.parseString("""
                {"id":"rpgskilltree:core_00","tags":["rpgskilltree:domain/core"]}
                """)
        )).get("rpgskilltree:core_00");

        assertEquals(Map.of(), metadata.domainWeightsPerRank());
        assertTrue(metadata.tags().contains("rpgskilltree:domain/core"));
    }

    @Test
    void unknownDomainTagFailsClosedInsteadOfGuessing() {
        assertThrows(SkillTreeDataValidationException.class, () ->
            SkillInvestmentMetadataParser.parse(Map.of(
                ResourceLocation.parse("rpgskilltree:skills/main/bad.json"),
                JsonParser.parseString("""
                    {"id":"rpgskilltree:bad","tags":["rpgskilltree:domain/not_a_real_domain"]}
                    """)
            ))
        );
    }
}
