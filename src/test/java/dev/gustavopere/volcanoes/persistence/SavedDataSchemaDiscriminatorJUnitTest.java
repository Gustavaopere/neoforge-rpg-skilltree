package dev.gustavopere.volcanoes.persistence;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.tectonics.TectonicRegionState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class SavedDataSchemaDiscriminatorJUnitTest {
    private static final UUID DEPOSIT_ID = UUID.fromString("30000000-0000-0000-0000-000000000003");

    @Test
    void malformedDepositSchemaDiscriminatorIsPreservedReadOnly() {
        CompoundTag malformed = new CompoundTag();
        malformed.putString("schema_version", "not-an-int");
        malformed.putString("opaque_marker", "preserve-me");

        DepositRegistry restored = DepositRegistry.fromTag(malformed);

        assertFalse(restored.isDirty(), "malformed schema discriminator must not schedule a destructive rewrite");
        assertEquals(malformed, restored.toTag(), "malformed schema discriminator must round-trip opaquely");
        assertFalse(restored.register(sampleDeposit()), "malformed schema discriminator must remain read-only/fail-closed");
    }

    @Test
    void unsupportedZeroDepositSchemaIsPreservedReadOnly() {
        CompoundTag unsupported = new CompoundTag();
        unsupported.putInt("schema_version", 0);
        unsupported.putString("opaque_marker", "preserve-me");

        DepositRegistry restored = DepositRegistry.fromTag(unsupported);

        assertFalse(restored.isDirty(), "unsupported schema 0 must not schedule a destructive rewrite");
        assertEquals(unsupported, restored.toTag(), "unsupported schema 0 must round-trip opaquely");
        assertFalse(restored.register(sampleDeposit()), "unsupported schema 0 must remain read-only/fail-closed");
    }

    @Test
    void malformedTectonicSchemaDiscriminatorIsPreservedReadOnly() {
        CompoundTag malformed = new CompoundTag();
        malformed.putString("schema_version", "not-an-int");
        malformed.putString("opaque_marker", "preserve-me");

        TectonicRegionState restored = TectonicRegionState.fromTag(malformed);

        assertFalse(restored.isDirty(), "malformed schema discriminator must not schedule a destructive rewrite");
        assertEquals(malformed, restored.toTag(), "malformed schema discriminator must round-trip opaquely");
        assertFalse(restored.putStress(4L, -2L, 0.75), "malformed schema discriminator must remain read-only/fail-closed");
    }

    @Test
    void unsupportedZeroTectonicSchemaIsPreservedReadOnly() {
        CompoundTag unsupported = new CompoundTag();
        unsupported.putInt("schema_version", 0);
        unsupported.putString("opaque_marker", "preserve-me");

        TectonicRegionState restored = TectonicRegionState.fromTag(unsupported);

        assertFalse(restored.isDirty(), "unsupported schema 0 must not schedule a destructive rewrite");
        assertEquals(unsupported, restored.toTag(), "unsupported schema 0 must round-trip opaquely");
        assertFalse(restored.putStress(4L, -2L, 0.75), "unsupported schema 0 must remain read-only/fail-closed");
    }

    private static GeologicalDeposit sampleDeposit() {
        return new GeologicalDeposit(
                DEPOSIT_ID,
                ResourceLocation.parse("c:ores/copper"),
                new BlockPos(16, 32, 16),
                12.0,
                0.65,
                DepositOrigin.HYDROTHERMAL);
    }
}
