package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.colony.guardtype.GuardType;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import net.minecraft.core.Registry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Locks the public MineColonies 1.1.1375 registration surface used by the optional adapter.
 * This replaces the earlier deliberately-failing reflection probe with permanent assertions.
 */
final class MineColoniesApiSurfaceProbeJUnitTest {
    @Test
    void exactSnapshotExposesPublicJobAndGuardRegistries() throws Exception {
        assertEquals(
            Registry.class,
            IMinecoloniesAPI.class.getMethod("getJobRegistry").getReturnType()
        );
        assertEquals(
            Registry.class,
            IMinecoloniesAPI.class.getMethod("getGuardTypeRegistry").getReturnType()
        );
    }

    @Test
    void exactSnapshotExposesBuildersNeededByTheAdapter() throws Exception {
        assertNotNull(JobEntry.Builder.class.getMethod("setJobProducer", java.util.function.Function.class));
        assertNotNull(JobEntry.Builder.class.getMethod("setJobViewProducer", java.util.function.Supplier.class));
        assertNotNull(JobEntry.Builder.class.getMethod("setRegistryName", net.minecraft.resources.ResourceLocation.class));
        assertNotNull(JobEntry.Builder.class.getMethod("createJobEntry"));

        assertNotNull(GuardType.Builder.class.getMethod("setJobEntry", java.util.function.Supplier.class));
        assertNotNull(GuardType.Builder.class.getMethod("setPrimarySkill", com.minecolonies.api.entity.citizen.Skill.class));
        assertNotNull(GuardType.Builder.class.getMethod("setSecondarySkill", com.minecolonies.api.entity.citizen.Skill.class));
        assertNotNull(GuardType.Builder.class.getMethod("setRegistryName", net.minecraft.resources.ResourceLocation.class));
        assertNotNull(GuardType.Builder.class.getMethod("createGuardType"));
    }
}
