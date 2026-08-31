package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.colony.guardtype.GuardType;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.core.colony.jobs.views.DefaultJobView;
import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import java.util.Objects;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Provider-local registration for the MineColonies Battle Mage values.
 *
 * <p>This class must only be loaded after the optional-provider/version gate has accepted the
 * installed MineColonies runtime. The values registered here are MineColonies-owned
 * {@link JobEntry} and {@link GuardType} objects; this mod does not maintain parallel job or guard
 * registries.</p>
 */
public final class MineColoniesBattleMageRegistration {
    private static final DeferredRegister<JobEntry> JOBS = DeferredRegister.create(
        MineColoniesBattleMageRegistries.JOB_REGISTRY_KEY,
        RpgSkillTreeMod.MOD_ID
    );

    public static final DeferredHolder<JobEntry, JobEntry> BATTLE_MAGE_JOB = JOBS.register(
        "battle_mage",
        MineColoniesBattleMageRegistration::createJobEntry
    );

    private static final DeferredRegister<GuardType> GUARD_TYPES = DeferredRegister.create(
        MineColoniesBattleMageRegistries.GUARD_TYPE_REGISTRY_KEY,
        RpgSkillTreeMod.MOD_ID
    );

    public static final DeferredHolder<GuardType, GuardType> BATTLE_MAGE_GUARD_TYPE = GUARD_TYPES.register(
        "battle_mage",
        () -> createGuardType(BATTLE_MAGE_JOB)
    );

    private MineColoniesBattleMageRegistration() {}

    /** Registers both MineColonies synchronized registry contributions on the mod event bus. */
    public static void register(IEventBus modBus) {
        Objects.requireNonNull(modBus, "modBus");
        JOBS.register(modBus);
        GUARD_TYPES.register(modBus);
    }

    public static JobEntry createJobEntry() {
        return new JobEntry.Builder()
            .setJobProducer(JobBattleMage::new)
            .setJobViewProducer(() -> DefaultJobView::new)
            .setRegistryName(MineColoniesBattleMageRegistries.BATTLE_MAGE_ID)
            .createJobEntry();
    }

    public static GuardType createGuardType(Supplier<JobEntry> jobEntry) {
        Objects.requireNonNull(jobEntry, "jobEntry");
        return new GuardType.Builder()
            .setJobEntry(jobEntry)
            .setJobTranslationKey(MineColoniesBattleMageRegistries.JOB_TRANSLATION_KEY)
            .setButtonTranslationKey(MineColoniesBattleMageRegistries.BUTTON_TRANSLATION_KEY)
            .setPrimarySkill(MineColoniesBattleMageRegistries.PRIMARY_SKILL)
            .setSecondarySkill(MineColoniesBattleMageRegistries.SECONDARY_SKILL)
            .setWorkerSoundName(MineColoniesBattleMageRegistries.WORKER_SOUND_NAME)
            .setClazz(JobBattleMage.class)
            .setRegistryName(MineColoniesBattleMageRegistries.BATTLE_MAGE_ID)
            .createGuardType();
    }
}
