package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CompendiumEditorialAbsentProviderJUnitTest {
    private static final ResourceLocation RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/alexscaves/biomes-batch1.json"
    );

    @Test
    void absentProviderPackageIsSkippedButLoadedProviderStillFailsClosed() {
        Map<ResourceLocation, JsonElement> resources = Map.of(RESOURCE, packageJson());

        var standalone = CompendiumEditorialResourceLoader.prepareForLoadedProviders(
            resources,
            List.of(),
            Set.of("minecraft", "rpgskilltree")
        );
        assertEquals(0, standalone.entries().size());

        CompendiumEditorialValidationException failure = assertThrows(
            CompendiumEditorialValidationException.class,
            () -> CompendiumEditorialResourceLoader.prepareForLoadedProviders(
                resources,
                List.of(),
                Set.of("minecraft", "rpgskilltree", "alexscaves")
            )
        );
        assertEquals(true, failure.getMessage().contains("absent from the current technical catalog"));
    }

    @Test
    void providerFilteringRejectsNullInputsBeforePreparingTheSnapshot() {
        Set<String> providers = Set.of("minecraft");
        assertThrows(
            NullPointerException.class,
            () -> CompendiumEditorialResourceLoader.prepareForLoadedProviders(null, List.of(), providers)
        );
        assertThrows(
            NullPointerException.class,
            () -> CompendiumEditorialResourceLoader.prepareForLoadedProviders(Map.of(), null, providers)
        );
        assertThrows(
            NullPointerException.class,
            () -> CompendiumEditorialResourceLoader.prepareForLoadedProviders(Map.of(), List.of(), null)
        );
    }

    private static JsonElement packageJson() {
        return JsonParser.parseString("""
            {
              "schema": 1,
              "language": "pt_br",
              "namespace": "alexscaves",
              "kind": "BIOME",
              "entries": [
                {
                  "entry_id": "BIOME:alexscaves:abyssal_chasm",
                  "title": "Fendas Abissais",
                  "summary": {
                    "text": "Bioma de teste para validar provider ausente.",
                    "sources": [
                      {"type": "RUNTIME", "ref": "alexscaves:biome/alexscaves:abyssal_chasm"}
                    ]
                  },
                  "sections": {},
                  "references": [],
                  "review_status": "REVIEWED",
                  "availability": "RUNTIME"
                }
              ]
            }
            """);
    }
}
