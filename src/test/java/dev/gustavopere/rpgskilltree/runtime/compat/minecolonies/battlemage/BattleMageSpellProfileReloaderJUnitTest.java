package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonParser;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class BattleMageSpellProfileReloaderJUnitTest {
    private static final ResourceLocation RESOURCE =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "battle_mage_spell_profiles/fireball");

    @Test
    void profileUsesResourceLocationAsItsExtensibleSpellIdentity() {
        assertEquals(ResourceLocation.class, BattleMageSpellProfile.class.getRecordComponents()[0].getType());
    }

    @Test
    void parsesExplicitProfileWithoutInventingProviderValues() {
        BattleMageSpellProfile profile = BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "HOSTILE_AREA",
                  "priority": 80,
                  "min_range": 4.0,
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0,
                  "world_effect": false,
                  "ally_safe": false
                }
                """)
        );

        assertEquals("irons_spellbooks:fireball", profile.spellId());
        assertEquals(BattleMageTargetMode.HOSTILE_AREA, profile.targetMode());
        assertEquals(80, profile.priority());
        assertFalse(profile.worldEffect());
    }

    @Test
    void unknownSpellRemainsUnsupportedUntilCatalogExplicitlyContainsIt() {
        BattleMageSpellProfileCatalog.replace(Map.of());
        assertFalse(BattleMageSpellProfileCatalog.find("irons_spellbooks:unknown").isPresent());
    }

    @Test
    void rejectsInvalidTargetMode() {
        assertThrows(IllegalArgumentException.class, () -> BattleMageSpellProfileReloader.parse(
            RESOURCE,
            JsonParser.parseString("""
                {
                  "spell": "irons_spellbooks:fireball",
                  "target_mode": "GUESS_FROM_NAME",
                  "priority": 80,
                  "min_range": 4.0,
                  "max_range": 28.0,
                  "friendly_fire_radius": 4.0
                }
                """)
        ));
    }
}
