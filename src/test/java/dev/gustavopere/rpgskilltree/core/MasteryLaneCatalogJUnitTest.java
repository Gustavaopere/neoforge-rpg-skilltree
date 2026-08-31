package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

final class MasteryLaneCatalogJUnitTest {
    @Test
    void fixedLanesAreCanonicalAndStable() {
        assertEquals("magic:casting", MasteryLaneCatalog.MAGIC_CASTING);
        assertEquals("irons:casting", MasteryLaneCatalog.IRONS_CASTING);
        assertEquals("ars:casting", MasteryLaneCatalog.ARS_CASTING);
        assertEquals("create:engineering", MasteryLaneCatalog.CREATE_ENGINEERING);
        assertEquals("epicfight:weapon", MasteryLaneCatalog.EPICFIGHT_WEAPON);
        assertTrue(MasteryLaneCatalog.isCanonical(MasteryLaneCatalog.OCCULT_PRACTICE));
        assertTrue(MasteryLaneCatalog.isCanonical(MasteryLaneCatalog.SUMMONING_PRACTICE));
        assertFalse(MasteryLaneCatalog.isCanonical("unknown:practice"));
    }

    @Test
    void dynamicFamiliesUseCanonicalFactories() {
        assertEquals("irons:fire", MasteryLaneCatalog.ironsDiscipline("fire"));
        assertEquals("ars:projectile", MasteryLaneCatalog.ars("projectile"));
        assertEquals("goety:necromancy", MasteryLaneCatalog.goety("necromancy"));
        assertEquals("malum:spirit/arcane", MasteryLaneCatalog.malumSpirit("arcane"));
        assertEquals("create:kinetics", MasteryLaneCatalog.create("kinetics"));
        assertEquals("epicfight:greatsword", MasteryLaneCatalog.epicFightWeaponCategory("greatsword"));

        for (String lane : Set.of(
            MasteryLaneCatalog.ironsDiscipline("fire"),
            MasteryLaneCatalog.ars("control"),
            MasteryLaneCatalog.goety("storm"),
            MasteryLaneCatalog.malumSpirit("sacred"),
            MasteryLaneCatalog.create("automation"),
            MasteryLaneCatalog.epicFightWeaponCategory("bow")
        )) {
            assertTrue(MasteryLaneCatalog.isCanonical(lane), lane);
        }
    }

    @Test
    void boundedFamiliesFailClosedOnUnknownMembers() {
        assertThrows(IllegalArgumentException.class, () -> MasteryLaneCatalog.ars("teleport"));
        assertThrows(IllegalArgumentException.class, () -> MasteryLaneCatalog.goety("alchemy"));
        assertThrows(IllegalArgumentException.class, () -> MasteryLaneCatalog.create("throughput"));
    }

    @Test
    void malformedDynamicMembersFailClosed() {
        assertThrows(IllegalArgumentException.class, () -> MasteryLaneCatalog.ironsDiscipline(""));
        assertThrows(IllegalArgumentException.class, () -> MasteryLaneCatalog.malumSpirit("spirit:arcane"));
        assertThrows(IllegalArgumentException.class, () -> MasteryLaneCatalog.epicFightWeaponCategory("great sword"));
    }
}
