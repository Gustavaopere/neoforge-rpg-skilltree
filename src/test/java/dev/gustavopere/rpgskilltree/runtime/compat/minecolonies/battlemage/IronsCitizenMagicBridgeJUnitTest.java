package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;

final class IronsCitizenMagicBridgeJUnitTest {
    @Test
    void bridgeUsesProviderMagicDataAndMobCastSource() throws Exception {
        assertEquals(
            MagicData.class,
            IronsCitizenMagicBridge.class.getMethod("magicData", LivingEntity.class).getReturnType()
        );
        assertEquals(CastSource.MOB, IronsCitizenMagicBridge.CAST_SOURCE);
    }

    @Test
    void manaClampNeverCreatesParallelOrOutOfRangeResourceState() {
        assertEquals(0.0f, IronsCitizenMagicBridge.clampMana(-5.0f, 100.0f));
        assertEquals(35.0f, IronsCitizenMagicBridge.clampMana(35.0f, 100.0f));
        assertEquals(100.0f, IronsCitizenMagicBridge.clampMana(140.0f, 100.0f));
        assertEquals(0.0f, IronsCitizenMagicBridge.clampMana(Float.NaN, 100.0f));
        assertEquals(0.0f, IronsCitizenMagicBridge.clampMana(Float.POSITIVE_INFINITY, 100.0f));
        assertEquals(0.0f, IronsCitizenMagicBridge.clampMana(10.0f, Float.NaN));
        assertEquals(0.0f, IronsCitizenMagicBridge.clampMana(10.0f, Float.POSITIVE_INFINITY));
        assertEquals(0.0f, IronsCitizenMagicBridge.clampMana(10.0f, 0.0f));
        assertEquals(0.0f, IronsCitizenMagicBridge.clampMana(10.0f, -1.0f));
    }

    @Test
    void affordabilityIsFailClosed() {
        assertFalse(IronsCitizenMagicBridge.hasMana(9.99f, 10));
        assertTrue(IronsCitizenMagicBridge.hasMana(10.0f, 10));
        assertTrue(IronsCitizenMagicBridge.hasMana(100.0f, 0));
        assertFalse(IronsCitizenMagicBridge.hasMana(Float.NaN, 1));
        assertFalse(IronsCitizenMagicBridge.hasMana(Float.POSITIVE_INFINITY, 1));
        assertFalse(IronsCitizenMagicBridge.hasMana(Float.NEGATIVE_INFINITY, 1));
        assertFalse(IronsCitizenMagicBridge.hasMana(100.0f, -1));
    }
}
