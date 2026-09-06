package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.junit.jupiter.api.Test;

final class CompendiumEditorialResourceManagerLoadJUnitTest {
    private static final CompendiumEntryId WOLF_ID =
        CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf");
    private static final ResourceLocation MINECRAFT_ENTITIES =
        ResourceLocation.parse("rpgskilltree:compendium/editorial/pt_br/minecraft/entities.json");

    @Test
    void providerAwareLoadSkipsAbsentProviderBeforeOpeningResource() throws Exception {
        Resource skipped = mock(Resource.class);
        ResourceManager manager = managerWith(MINECRAFT_ENTITIES, skipped);

        var snapshot = CompendiumEditorialResourceLoader.load(
            manager,
            List.of(wolf()),
            Set.of("alexscaves")
        );

        assertTrue(snapshot.entries().isEmpty());
        verify(skipped, never()).openAsReader();
    }

    @Test
    void providerAwareAndLegacyLoadsReadAndParsePresentResources() throws Exception {
        Resource providerAwareResource = resourceWith(validPackage());
        var providerAware = CompendiumEditorialResourceLoader.load(
            managerWith(MINECRAFT_ENTITIES, providerAwareResource),
            List.of(wolf()),
            Set.of("minecraft")
        );

        assertEquals(WOLF_ID, providerAware.entries().getFirst().entryId());
        assertEquals("Lobo", providerAware.entries().getFirst().title());
        verify(providerAwareResource).openAsReader();

        Resource legacyResource = resourceWith(validPackage());
        var legacy = CompendiumEditorialResourceLoader.load(
            managerWith(MINECRAFT_ENTITIES, legacyResource),
            List.of(wolf())
        );

        assertEquals(WOLF_ID, legacy.entries().getFirst().entryId());
        verify(legacyResource).openAsReader();
    }

    @Test
    void resourceManagerReaderFailureIsWrappedFailClosed() throws Exception {
        Resource broken = mock(Resource.class);
        when(broken.openAsReader()).thenThrow(new IOException("boom"));

        CompendiumEditorialValidationException failure = assertThrows(
            CompendiumEditorialValidationException.class,
            () -> CompendiumEditorialResourceLoader.load(
                managerWith(MINECRAFT_ENTITIES, broken),
                List.of(wolf()),
                Set.of("minecraft")
            )
        );

        assertTrue(failure.getMessage().contains("could not read/parse editorial JSON resource"));
    }

    private static ResourceManager managerWith(ResourceLocation id, Resource resource) {
        ResourceManager manager = mock(ResourceManager.class);
        when(manager.listResources(anyString(), any())).thenReturn(Map.of(id, resource));
        return manager;
    }

    private static Resource resourceWith(String payload) throws IOException {
        Resource resource = mock(Resource.class);
        when(resource.openAsReader()).thenReturn(new BufferedReader(new StringReader(payload)));
        return resource;
    }

    private static String validPackage() {
        return """
            {"schema":1,"language":"pt_br","namespace":"minecraft","kind":"ENTITY","entries":[
              {"entry_id":"ENTITY:minecraft:wolf","title":"Lobo",
               "summary":{"text":"Resumo válido.","sources":[{"type":"RUNTIME","ref":"minecraft:entity_type/minecraft:wolf","note":"fonte direta"}]},
               "sections":{"behavior":{"text":"Comportamento confirmado.","sources":[{"type":"OFFICIAL_CODE","ref":"net/minecraft/world/entity/animal/Wolf.java"}]}},
               "references":[],"review_status":"REVIEWED","availability":"RUNTIME"}
            ]}
            """;
    }

    private static CompendiumEntry wolf() {
        return new CompendiumEntry(
            WOLF_ID,
            "minecraft",
            "entity.minecraft.wolf",
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "test"),
            1
        );
    }
}
