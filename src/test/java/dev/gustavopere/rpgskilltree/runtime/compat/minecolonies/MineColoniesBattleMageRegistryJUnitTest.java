package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.minecolonies.api.entity.citizen.Skill;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistries;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

class MineColoniesBattleMageRegistryJUnitTest {
    @Test
    void usesMineColoniesSynchronizedRegistryKeys() {
        assertEquals(
            ResourceLocation.fromNamespaceAndPath("minecolonies", "jobs"),
            MineColoniesBattleMageRegistries.JOB_REGISTRY_KEY.location()
        );
        assertEquals(
            ResourceLocation.fromNamespaceAndPath("minecolonies", "guardtypes"),
            MineColoniesBattleMageRegistries.GUARD_TYPE_REGISTRY_KEY.location()
        );
    }

    @Test
    void exposesCanonicalBattleMageIdentityAndSkills() {
        assertEquals(
            ResourceLocation.fromNamespaceAndPath("rpgskilltree", "battle_mage"),
            MineColoniesBattleMageRegistries.BATTLE_MAGE_ID
        );
        assertEquals(Skill.Mana, MineColoniesBattleMageRegistries.PRIMARY_SKILL);
        assertEquals(Skill.Focus, MineColoniesBattleMageRegistries.SECONDARY_SKILL);
        assertEquals("com.minecolonies.job.battle_mage", MineColoniesBattleMageRegistries.JOB_TRANSLATION_KEY);
        assertEquals("com.minecolonies.coremod.gui.workerhuts.battle_mage", MineColoniesBattleMageRegistries.BUTTON_TRANSLATION_KEY);
    }
}