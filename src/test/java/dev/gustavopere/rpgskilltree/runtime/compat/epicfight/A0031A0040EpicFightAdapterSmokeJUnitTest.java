package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.lang.reflect.Method;
import java.util.Optional;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0031A0040EpicFightAdapterSmokeJUnitTest {
    @Test
    void adapterLoadsAndKeepsExactVanillaMaceFallbackFailClosed() throws Exception {
        assertTrue(A0021A0040EpicFightHooks.supportsVersion("21.17.3.1"));
        assertFalse(A0021A0040EpicFightHooks.supportsVersion(null));

        Method fallback = A0021A0040EpicFightHooks.class.getDeclaredMethod("vanillaFallbackFamily", ItemStack.class);
        fallback.setAccessible(true);

        @SuppressWarnings("unchecked")
        Optional<WeaponFamily> mace = (Optional<WeaponFamily>) fallback.invoke(null, new ItemStack(Items.MACE));
        @SuppressWarnings("unchecked")
        Optional<WeaponFamily> empty = (Optional<WeaponFamily>) fallback.invoke(null, ItemStack.EMPTY);

        assertEquals(Optional.of(WeaponFamily.MACE), mace);
        assertEquals(Optional.empty(), empty);
    }
}
