package dev.gustavopere.rpgskilltree.itemization.blacksmith.compat.productivemetalworks;

import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductiveMetalworksAdapterContractTest {
    private static final String EXPECTED_VERSION = "1.21.1-1.15.1";
    private static final Path IRON_BLADE_RECIPE = Path.of(
        "src/main/resources/data/rpgskilltree/recipe/blacksmith/productivemetalworks/iron_blade.json"
    );
    private static final Path OPTIONAL_PROVIDER_SMOKE_VERIFIER = Path.of(
        "scripts/verify-optional-provider-smoke.py"
    );

    @Test
    void optionalIntegrationCatalogExposesProductiveMetalworksWithoutProviderTypes() {
        assertTrue(
            Arrays.stream(OptionalIntegrations.Provider.values())
                .anyMatch(provider -> provider.modId().equals("productivemetalworks")),
            "Productive Metalworks must be represented by the provider-neutral optional integration catalog"
        );
    }

    @Test
    void optionalProviderSmokeMatrixTracksProductiveMetalworks() {
        String verifier = assertDoesNotThrow(() -> Files.readString(OPTIONAL_PROVIDER_SMOKE_VERIFIER));
        assertTrue(
            verifier.contains("\"productivemetalworks\","),
            "Dedicated-server smoke must expect Productive Metalworks to be absent in the core-only runtime"
        );
    }

    @Test
    void exactVersionContractAcceptsOnlyTheAudited1151Runtime() {
        Class<?> contract = assertDoesNotThrow(() -> Class.forName(
            "dev.gustavopere.rpgskilltree.itemization.blacksmith.compat.productivemetalworks.ProductiveMetalworksVersionContract"
        ));
        Method supports = assertDoesNotThrow(() -> contract.getMethod("supports", String.class));

        assertEquals(Boolean.TRUE, assertDoesNotThrow(() -> supports.invoke(null, EXPECTED_VERSION)));
        assertEquals(Boolean.FALSE, assertDoesNotThrow(() -> supports.invoke(null, "1.21.1-1.15.0")));
        assertEquals(Boolean.FALSE, assertDoesNotThrow(() -> supports.invoke(null, "1.21.1-1.16.0")));
        assertEquals(Boolean.FALSE, assertDoesNotThrow(() -> supports.invoke(null, "absent")));
        assertEquals(Boolean.FALSE, assertDoesNotThrow(() -> supports.invoke(null, new Object[] { null })));
    }

    @Test
    void proofCastingRecipeIsVersionGatedReusableAndPersistsPhysicalPartIdentity() {
        assertTrue(Files.isRegularFile(IRON_BLADE_RECIPE), "16.B must ship a real Productive Metalworks casting proof");
        String json = assertDoesNotThrow(() -> Files.readString(IRON_BLADE_RECIPE));

        assertTrue(json.contains("\"type\": \"productivemetalworks:item_casting\""));
        assertTrue(json.contains("\"type\": \"rpgskilltree:exact_mod_version\""));
        assertTrue(json.contains("\"modid\": \"productivemetalworks\""));
        assertTrue(json.contains("\"version\": \"" + EXPECTED_VERSION + "\""));
        assertTrue(json.contains("\"item\": \"rpgskilltree:blade_cast\""));
        assertTrue(json.contains("\"tag\": \"c:molten_iron\""));
        assertTrue(json.contains("\"id\": \"rpgskilltree:blade\""));
        assertTrue(json.contains("\"rpgskilltree:blacksmith_part_state\""));
        assertTrue(json.contains("\"provider_id\": \"productivemetalworks:productivemetalworks\""));
        assertTrue(json.contains("\"recipe_id\": \"rpgskilltree:blacksmith/productivemetalworks/iron_blade\""));
        assertTrue(json.contains("\"material_profile_id\": \"rpgskilltree:iron\""));
        assertTrue(json.contains("\"process_state\": \"CAST\""));
        assertTrue(json.contains("\"consume_cast\": false"), "Blacksmith metal casts are reusable by default");
        assertFalse(json.contains("\"consume_cast\": true"));
    }
}
