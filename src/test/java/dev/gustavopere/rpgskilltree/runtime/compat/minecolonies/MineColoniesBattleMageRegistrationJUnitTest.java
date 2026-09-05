package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import com.minecolonies.api.colony.guardtype.GuardType;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.JobBattleMage;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

final class MineColoniesBattleMageRegistrationJUnitTest {
    @Test
    void jobEntryUsesCanonicalIdentityAndDefaultClientView() {
        JobEntry entry = MineColoniesBattleMageRegistration.createJobEntry();
        assertEquals(MineColoniesBattleMageRegistries.BATTLE_MAGE_ID, entry.getKey());
        assertEquals(MineColoniesBattleMageRegistries.JOB_TRANSLATION_KEY, entry.getTranslationKey());
        assertFalse(entry.getJobViewProducer() == null);
    }

    @Test
    void guardTypePointsAtBattleMageJobAndSkills() {
        JobEntry entry = MineColoniesBattleMageRegistration.createJobEntry();
        GuardType type = MineColoniesBattleMageRegistration.createGuardType(() -> entry);

        assertSame(entry, type.getJobEntry().get());
        assertEquals(MineColoniesBattleMageRegistries.JOB_TRANSLATION_KEY, type.getJobTranslationKey());
        assertEquals(MineColoniesBattleMageRegistries.BUTTON_TRANSLATION_KEY, type.getButtonTranslationKey());
        assertEquals(MineColoniesBattleMageRegistries.PRIMARY_SKILL, type.getPrimarySkill());
        assertEquals(MineColoniesBattleMageRegistries.SECONDARY_SKILL, type.getSecondarySkill());
        assertEquals(MineColoniesBattleMageRegistries.WORKER_SOUND_NAME, type.getWorkerSoundName());
        assertFalse(type.isInstance(null));
    }

    @Test
    void battleMageJobIsARealMineColoniesGuardJob() {
        assertEquals("JobBattleMage", JobBattleMage.class.getSimpleName());
        assertEquals(
            "com.minecolonies.core.colony.jobs.AbstractJobGuard",
            JobBattleMage.class.getSuperclass().getName()
        );
    }
}
