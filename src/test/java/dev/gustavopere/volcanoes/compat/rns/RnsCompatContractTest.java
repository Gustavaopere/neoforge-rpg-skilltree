package dev.gustavopere.volcanoes.compat.rns;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RnsCompatContractTest {
    private static final ResourceLocation COPPER = id("deposit_copper");
    private static final ResourceLocation IRON = id("deposit_iron");
    private static final ResourceLocation GOLD = id("deposit_gold");
    private static final ResourceLocation TIN = id("deposit_tin");
    private static final ResourceLocation NICKEL = id("deposit_nickel");
    private static final ResourceLocation ZINC = id("deposit_zinc");
    private static final ResourceLocation SILVER = id("deposit_silver");
    private static final ResourceLocation COAL = id("deposit_coal");

    private static final Set<ResourceLocation> VOLCANOES_PROJECTED_FAMILIES = Set.of(
            COPPER, IRON, GOLD);
    private static final Set<ResourceLocation> RNS_NATIVE_METAL_FAMILIES = Set.of(
            COPPER, IRON, GOLD, TIN, NICKEL, ZINC, SILVER);

    @Test
    void exactVersionAndProjectionReadinessAreBothRequiredBeforeHostProjectionActivates() {
        assertEquals(RnsCompat.InstallDecision.ABSENT,
                RnsCompat.decide(false, null, false));
        assertEquals(RnsCompat.InstallDecision.VERSION_MISMATCH,
                RnsCompat.decide(true, "1.3.0-1.21.1-5", true));
        assertEquals(RnsCompat.InstallDecision.OWNERSHIP_NOT_READY,
                RnsCompat.decide(true, RnsCompat.SUPPORTED_VERSION, false));
        assertEquals(RnsCompat.InstallDecision.ACTIVE,
                RnsCompat.decide(true, RnsCompat.SUPPORTED_VERSION, true));
    }

    @Test
    void liveProjectionRequiresSelectionWhileEveryProtectedRnsMetalKeepsNativeWorldgen() {
        Set<ResourceLocation> selected = Set.of(
                COPPER, IRON, GOLD, TIN, NICKEL, ZINC, SILVER, COAL);
        Set<ResourceLocation> nativeWorldgen = selected;

        assertEquals(VOLCANOES_PROJECTED_FAMILIES, RnsCompat.projectedDepositIds());
        assertTrue(RnsCompat.projectionReady(true, selected, nativeWorldgen));
        assertFalse(RnsCompat.projectionReady(false, selected, nativeWorldgen));
        assertFalse(RnsCompat.projectionReady(true,
                Set.of(COPPER, IRON, TIN, NICKEL, ZINC, SILVER, COAL), nativeWorldgen));

        for (ResourceLocation protectedNative : RNS_NATIVE_METAL_FAMILIES) {
            Set<ResourceLocation> withoutProtectedNative = nativeWorldgen.stream()
                    .filter(id -> !id.equals(protectedNative))
                    .collect(java.util.stream.Collectors.toUnmodifiableSet());
            assertFalse(RnsCompat.projectionReady(true, selected, withoutProtectedNative),
                    () -> "safe coexistence must preserve native RNS worldgen for " + protectedNative);
        }
    }

    @Test
    void nonProducedTfcFamiliesRemainOutsideVolcanoesProjectionOwnership() {
        Set<ResourceLocation> projected = RnsCompat.projectedDepositIds();
        assertFalse(projected.contains(TIN));
        assertFalse(projected.contains(NICKEL));
        assertFalse(projected.contains(ZINC));
        assertFalse(projected.contains(SILVER));
    }

    @Test
    void canonicalHostIdentityIsPinnedToTheInstalledRnsArtifact() {
        assertEquals("create_rns", RnsCompat.MOD_ID);
        assertEquals("1.3.1-1.21.1-6", RnsCompat.SUPPORTED_VERSION);
        assertEquals("kubejs", RnsCompat.KUBEJS_MOD_ID);
        assertEquals("2101.7.2-build.374", RnsCompat.SUPPORTED_KUBEJS_VERSION);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("create_rns", path);
    }
}
