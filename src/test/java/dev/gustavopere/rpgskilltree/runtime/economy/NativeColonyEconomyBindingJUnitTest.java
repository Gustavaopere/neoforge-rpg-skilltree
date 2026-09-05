package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class NativeColonyEconomyBindingJUnitTest {
    private static final NativeColonyBinding NATIVE = new NativeColonyBinding(
        ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"),
        7
    );

    @Test
    void sameLiveNativeBindingResolvesSameEconomyUuidAcrossReload() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        EconomyColonyKey first = data.resolveOrCreateBinding(NATIVE);

        ColonyEconomySavedData loaded = ColonyEconomySavedData.decodeForTest(data.encodeForTest());
        EconomyColonyKey restored = loaded.resolveOrCreateBinding(NATIVE);

        assertEquals(first, restored);
    }

    @Test
    void deletingBindingArchivesOldEconomyAndRecycledNativeIdGetsNewUuid() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        EconomyColonyKey first = data.resolveOrCreateBinding(NATIVE);

        data.archiveBinding(NATIVE);
        assertTrue(data.binding(NATIVE).isEmpty());
        assertTrue(data.isArchived(first));

        EconomyColonyKey recycled = data.resolveOrCreateBinding(NATIVE);
        assertNotEquals(first, recycled);
        assertTrue(data.isArchived(first));
    }
}
