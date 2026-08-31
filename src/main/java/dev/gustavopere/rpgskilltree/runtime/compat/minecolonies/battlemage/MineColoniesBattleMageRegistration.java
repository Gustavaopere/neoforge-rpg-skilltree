package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.colony.guardtype.GuardType;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.core.colony.jobs.views.DefaultJobView;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Provider-local builders for the MineColonies Battle Mage registry values.
 *
 * <p>This class is only safe to load after the optional-provider/version gate has accepted the
 * installed MineColonies runtime. It deliberately builds MineColonies-owned {@link JobEntry} and
 * {@link GuardType} values instead of maintaining parallel job or guard metadata.</p>
 */
public final class MineColoniesBattleMageRegistration {
    private MineColoniesBattleMageRegistration() {}

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
