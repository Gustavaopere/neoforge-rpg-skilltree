package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CompendiumEditorialUnicodePlaceholderJUnitTest {
    private static final ResourceLocation RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/minecraft/unicode-placeholder-test.json"
    );

    @Test
    void portugueseWordsContainingTodoSequenceAreNotPlaceholders() {
        var snapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(RESOURCE, JsonParser.parseString(packageJson("O método de obtenção é confirmado pelo runtime."))),
            List.of(wolf())
        );

        assertEquals(1, snapshot.entries().size());
    }

    @Test
    void standaloneTodoRemainsForbidden() {
        assertThrows(
            CompendiumEditorialValidationException.class,
            () -> CompendiumEditorialResourceLoader.prepare(
                Map.of(RESOURCE, JsonParser.parseString(packageJson("TODO"))),
                List.of(wolf())
            )
        );
    }

    private static String packageJson(String summary) {
        return """
            {"schema":1,"language":"pt_br","namespace":"minecraft","kind":"ENTITY","entries":[
              {"entry_id":"ENTITY:minecraft:wolf","title":"Lobo",
               "summary":{"text":"%s","sources":[{"type":"RUNTIME","ref":"minecraft:entity_type/minecraft:wolf"}]},
               "sections":{},"references":[],"review_status":"REVIEWED","availability":"RUNTIME"}
            ]}
            """.formatted(summary);
    }

    private static CompendiumEntry wolf() {
        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf"),
            "minecraft",
            "entity.minecraft.wolf",
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "unicode-placeholder-regression-test"),
            1
        );
    }
}
