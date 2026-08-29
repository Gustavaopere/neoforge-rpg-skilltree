package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class InlineBonusAuthorityJUnitTest {
    @Test
    void nonEmptyPassiveTreeBonusesCannotBecomeASecondGameplayAuthority() {
        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                Map.of(RULE_SOURCE, json("""
                    {"treeId":"rpgskilltree:main","nodes":[{
                      "id":"rpgskilltree:test/root","maxRank":1,"costPerRank":1,"startingPoint":true
                    }]}
                    """)),
                Map.of(EFFECT_SOURCE, json("""{"attributes":[]}""")),
                Map.of(SKILL_SOURCE, json("""
                    {
                      "id":"rpgskilltree:test/root",
                      "positionX":0,
                      "positionY":0,
                      "buttonSize":20,
                      "bonuses":[{"type":"attribute","attribute":"minecraft:generic.attack_damage","value":1.0}]
                    }
                    """)),
                List.of()
            )
        );

        assertEquals(SKILL_SOURCE, failure.resourceId());
        assertEquals("rpgskilltree:test/root", failure.entryId());
        assertEquals("bonuses", failure.field());
        assertTrue(failure.getMessage().contains("node_effects"));
    }

    private static final ResourceLocation RULE_SOURCE = ResourceLocation.parse("rpgskilltree:node_rules/inline_authority.json");
    private static final ResourceLocation EFFECT_SOURCE = ResourceLocation.parse("rpgskilltree:node_effects/inline_authority.json");
    private static final ResourceLocation SKILL_SOURCE = ResourceLocation.parse("rpgskilltree:skills/main/inline_authority.json");

    private static JsonElement json(String value) {
        return JsonParser.parseString(value);
    }
}
