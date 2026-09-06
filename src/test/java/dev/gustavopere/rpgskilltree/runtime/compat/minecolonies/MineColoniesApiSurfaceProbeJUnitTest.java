package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import com.minecolonies.api.colony.guardtype.GuardType;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Locks only the public MineColonies 1.1.1375 builder surface used directly by the adapter.
 *
 * <p>The MineColonies test artifact is intentionally non-transitive. Loading IMinecoloniesAPI via
 * reflection would also link unrelated LDTTeam/Structurize types from its method signatures, which
 * turns this focused contract into a dependency-graph test. Registry keys are locked separately by
 * {@link MineColoniesBattleMageRegistryJUnitTest}.</p>
 */
final class MineColoniesApiSurfaceProbeJUnitTest {
    @Test
    void exactSnapshotExposesJobBuilderSurfaceUsedByAdapter() throws Exception {
        assertNotNull(JobEntry.Builder.class.getMethod("setJobProducer", java.util.function.Function.class));
        assertNotNull(JobEntry.Builder.class.getMethod("setJobViewProducer", java.util.function.Supplier.class));
        assertNotNull(JobEntry.Builder.class.getMethod("setRegistryName", net.minecraft.resources.ResourceLocation.class));
        assertNotNull(JobEntry.Builder.class.getMethod("createJobEntry"));
    }

    @Test
    void exactSnapshotExposesGuardTypeBuilderSurfaceUsedByAdapter() throws Exception {
        assertNotNull(GuardType.Builder.class.getMethod("setJobEntry", java.util.function.Supplier.class));
        assertNotNull(GuardType.Builder.class.getMethod("setJobTranslationKey", String.class));
        assertNotNull(GuardType.Builder.class.getMethod("setButtonTranslationKey", String.class));
        assertNotNull(GuardType.Builder.class.getMethod("setPrimarySkill", com.minecolonies.api.entity.citizen.Skill.class));
        assertNotNull(GuardType.Builder.class.getMethod("setSecondarySkill", com.minecolonies.api.entity.citizen.Skill.class));
        assertNotNull(GuardType.Builder.class.getMethod("setWorkerSoundName", String.class));
        assertNotNull(GuardType.Builder.class.getMethod("setClazz", Class.class));
        assertNotNull(GuardType.Builder.class.getMethod("setRegistryName", net.minecraft.resources.ResourceLocation.class));
        assertNotNull(GuardType.Builder.class.getMethod("createGuardType"));
    }
}
