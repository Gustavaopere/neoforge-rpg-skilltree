package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class BattleMageSpellProfileReloaderJUnitTest {
    private static final ResourceLocation RESOURCE =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "battle_mage_spell_profiles/fireball");
    private static final ResourceLocation SECOND_RESOURCE =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "battle_mage_spell_profiles/fireball_duplicate");
    private static final ResourceLocation FIREBALL =
        ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "fireball");
    private static final ResourceLocation MAGIC_ARROW =
        ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "magic_arrow");

    @AfterEach
    void resetCatalog() {
        BattleMageSpellProfileCatalog.replace(Map.of());
    }

    @Test
    void profileUsesResourceLocationAsItsExtensibleSpellIdentity() {
        assertEquals(ResourceLocation.class, BattleMageSpellProfile.class.getRecordComponents()[0].getType());
        assertFalse(Arrays.stream(BattleMageSpellProfile.class.getConstructors())
            .anyMatch(constructor -> constructor.getParameterCount() > 0
                && constructor.getParameterTypes()[0] == String.class));
    }

    @Test
    void parsesExplicitProfileWithoutInventingProviderValues() {
        BattleMageSpellProfile profile = BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString(fullProfileJson("irons_spellbooks:fireball", "HOSTILE_AREA"))
        );

        assertEquals(FIREBALL, profile.spellId());
        assertEquals(BattleMageTargetMode.HOSTILE_AREA, profile.targetMode());
        assertEquals(80, profile.priority());
        assertEquals(4.0, profile.minRange());
        assertEquals(28.0, profile.maxRange());
        assertEquals(4.0, profile.friendlyFireRadius());
        assertFalse(profile.worldEffect());
        assertFalse(profile.allySafe());
    }

    @Test
    void optionalSafetyFlagsDefaultFalseAndAcceptExplicitTrue() {
        BattleMageSpellProfile defaults = BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": 80,
                  "min_range": 4.0,
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0
                }
                """)
        );
        BattleMageSpellProfile explicit = BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": 80,
                  "min_range": 4.0,
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0,
                  "world_effect": true,
                  "ally_safe": true
                }
                """)
        );

        assertFalse(defaults.worldEffect());
        assertFalse(defaults.allySafe());
        assertTrue(explicit.worldEffect());
        assertTrue(explicit.allySafe());
    }

    @Test
    void profileValidationFailsClosedForNullsPriorityRangesAndInvalidGeometry() {
        assertThrows(NullPointerException.class, () -> profile(null, BattleMageTargetMode.SELF, 0, 0.0, 0.0, 0.0));
        assertThrows(NullPointerException.class, () -> profile(FIREBALL, null, 0, 0.0, 0.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, -1, 0.0, 0.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, 10_001, 0.0, 0.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, 0, Double.NaN, 1.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, 0, 0.0, Double.NaN, 0.0));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, 0, -0.01, 1.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, 0, 2.0, 1.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, 0, 0.0, 1.0, Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> profile(FIREBALL, BattleMageTargetMode.SELF, 0, 0.0, 1.0, -0.01));
    }

    @Test
    void namespacedIdParserRejectsNullUnqualifiedAndMalformedIds() {
        assertEquals(FIREBALL, BattleMageSpellProfile.parseNamespacedId("irons_spellbooks:fireball"));
        assertThrows(NullPointerException.class, () -> BattleMageSpellProfile.parseNamespacedId(null));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfile.parseNamespacedId("fireball"));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfile.parseNamespacedId(":fireball"));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfile.parseNamespacedId("bad namespace:fireball"));
    }

    @Test
    void parserRejectsNonObjectAndMissingOrMalformedRequiredFields() {
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(RESOURCE, null));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("[]")
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("{}")
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString(fullProfileJson("", "HOSTILE_AREA"))
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString(fullProfileJson("irons_spellbooks:fireball", ""))
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "min_range": 4.0,
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0
                }
                """)
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": "not-an-int",
                  "min_range": 4.0,
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0
                }
                """)
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": 80,
                  "min_range": "not-a-number",
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0
                }
                """)
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": 80,
                  "min_range": 4.0,
                  "friendly_fire_radius": 4.0
                }
                """)
        ));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": 80,
                  "min_range": 4.0,
                  "max_range": 28.0
                }
                """)
        ));
    }

    @Test
    void optionalBooleanRejectsNonPrimitiveValues() {
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": 80,
                  "min_range": 4.0,
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0,
                  "world_effect": {}
                }
                """)
        ));
    }

    @Test
    void unknownSpellRemainsUnsupportedUntilCatalogExplicitlyContainsIt() {
        BattleMageSpellProfileCatalog.replace(Map.of());
        assertFalse(BattleMageSpellProfileCatalog.find(
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "unknown")
        ).isPresent());
        assertFalse(BattleMageSpellProfileCatalog.find((ResourceLocation) null).isPresent());
        assertFalse(BattleMageSpellProfileCatalog.find((String) null).isPresent());
        assertFalse(BattleMageSpellProfileCatalog.find("fireball").isPresent());
        assertFalse(BattleMageSpellProfileCatalog.find("bad namespace:fireball").isPresent());
    }

    @Test
    void catalogAcceptsResourceAndStringKeysAndPublishesImmutableStringSnapshot() {
        BattleMageSpellProfile fireball = profile(FIREBALL, BattleMageTargetMode.HOSTILE_AREA, 80, 4.0, 28.0, 4.0);
        BattleMageSpellProfile arrow = profile(MAGIC_ARROW, BattleMageTargetMode.HOSTILE_ENTITY, 70, 2.0, 30.0, 0.0);
        LinkedHashMap<Object, BattleMageSpellProfile> replacement = new LinkedHashMap<>();
        replacement.put(FIREBALL, fireball);
        replacement.put("irons_spellbooks:magic_arrow", arrow);

        BattleMageSpellProfileCatalog.replace(replacement);

        assertEquals(fireball, BattleMageSpellProfileCatalog.find(FIREBALL).orElseThrow());
        assertEquals(arrow, BattleMageSpellProfileCatalog.find("irons_spellbooks:magic_arrow").orElseThrow());
        assertEquals(Map.of(
            "irons_spellbooks:fireball", fireball,
            "irons_spellbooks:magic_arrow", arrow
        ), BattleMageSpellProfileCatalog.snapshot());
        assertThrows(UnsupportedOperationException.class, () ->
            BattleMageSpellProfileCatalog.snapshot().put("irons_spellbooks:other", fireball));
    }

    @Test
    void catalogReplacementRejectsInvalidKeysValuesMismatchesAndNormalizedDuplicates() {
        BattleMageSpellProfile fireball = profile(FIREBALL, BattleMageTargetMode.HOSTILE_AREA, 80, 4.0, 28.0, 4.0);
        BattleMageSpellProfile arrow = profile(MAGIC_ARROW, BattleMageTargetMode.HOSTILE_ENTITY, 70, 2.0, 30.0, 0.0);

        assertThrows(NullPointerException.class, () -> BattleMageSpellProfileCatalog.replace(null));

        LinkedHashMap<Object, BattleMageSpellProfile> nullKey = new LinkedHashMap<>();
        nullKey.put(null, fireball);
        assertThrows(NullPointerException.class, () -> BattleMageSpellProfileCatalog.replace(nullKey));

        LinkedHashMap<Object, BattleMageSpellProfile> nullValue = new LinkedHashMap<>();
        nullValue.put(FIREBALL, null);
        assertThrows(NullPointerException.class, () -> BattleMageSpellProfileCatalog.replace(nullValue));

        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileCatalog.replace(Map.of(42, fireball)));
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileCatalog.replace(Map.of(FIREBALL, arrow)));

        LinkedHashMap<Object, BattleMageSpellProfile> duplicate = new LinkedHashMap<>();
        duplicate.put(FIREBALL, fireball);
        duplicate.put("irons_spellbooks:fireball", fireball);
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileCatalog.replace(duplicate));
    }

    @Test
    void applySortsResourcesPublishesCatalogAndRejectsDuplicateProviderIdentity() {
        BattleMageSpellProfileReloader reloader = new BattleMageSpellProfileReloader();
        LinkedHashMap<ResourceLocation, com.google.gson.JsonElement> resources = new LinkedHashMap<>();
        resources.put(SECOND_RESOURCE, JsonParser.parseString(fullProfileJson("irons_spellbooks:magic_arrow", "HOSTILE_ENTITY")));
        resources.put(RESOURCE, JsonParser.parseString(fullProfileJson("irons_spellbooks:fireball", "HOSTILE_AREA")));

        reloader.apply(resources, null, null);

        assertEquals(FIREBALL, BattleMageSpellProfileCatalog.find(FIREBALL).orElseThrow().spellId());
        assertEquals(MAGIC_ARROW, BattleMageSpellProfileCatalog.find(MAGIC_ARROW).orElseThrow().spellId());

        LinkedHashMap<ResourceLocation, com.google.gson.JsonElement> duplicate = new LinkedHashMap<>();
        duplicate.put(RESOURCE, JsonParser.parseString(fullProfileJson("irons_spellbooks:fireball", "HOSTILE_AREA")));
        duplicate.put(SECOND_RESOURCE, JsonParser.parseString(fullProfileJson("irons_spellbooks:fireball", "HOSTILE_AREA")));
        assertThrows(IllegalArgumentException.class, () -> reloader.apply(duplicate, null, null));
    }

    @Test
    void rejectsUnqualifiedProviderSpellIdInsteadOfDefaultingNamespace() {
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString(fullProfileJson("fireball", "HOSTILE_AREA"))
        ));
    }

    @Test
    void rejectsInvalidTargetMode() {
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString(fullProfileJson("irons_spellbooks:fireball", "GUESS_FROM_NAME"))
        ));
    }

    private static BattleMageSpellProfile profile(
        ResourceLocation spellId,
        BattleMageTargetMode targetMode,
        int priority,
        double minRange,
        double maxRange,
        double friendlyFireRadius
    ) {
        return new BattleMageSpellProfile(
            spellId,
            targetMode,
            priority,
            minRange,
            maxRange,
            friendlyFireRadius,
            false,
            false
        );
    }

    private static String fullProfileJson(String spell, String targetMode) {
        return """
            {
              "spell": "%s",
              "target_mode": "%s",
              "priority": 80,
              "min_range": 4.0,
              "max_range": 28.0,
              "friendly_fire_radius": 4.0,
              "world_effect": false,
              "ally_safe": false
            }
            """.formatted(spell, targetMode);
    }
}
