package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.colony.guardtype.GuardType;
import com.minecolonies.api.colony.jobs.registry.IJobRegistry;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.core.colony.jobs.views.DefaultJobView;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.RegisterEvent;

/**
 * Provider-local registration for the MineColonies Battle Mage values.
 *
 * <p>MineColonies creates its synchronized custom registries during {@code NewRegistryEvent}.
 * Contributions from this optional adapter therefore use {@link RegisterEvent}, after those foreign
 * registries exist, rather than creating eager holders against registries owned by another mod.</p>
 */
public final class MineColoniesBattleMageRegistration {
    private MineColoniesBattleMageRegistration() {}

    /** Registers the foreign-registry listener on the mod event bus. */
    public static void register(IEventBus modBus) {
        Objects.requireNonNull(modBus, "modBus");
        modBus.addListener(MineColoniesBattleMageRegistration::onRegister);
    }

    static boolean targetsRegistry(ResourceKey<? extends Registry<?>> registryKey) {
        return MineColoniesBattleMageRegistries.JOB_REGISTRY_KEY.equals(registryKey)
            || MineColoniesBattleMageRegistries.GUARD_TYPE_REGISTRY_KEY.equals(registryKey);
    }

    private static void onRegister(RegisterEvent event) {
        event.register(
            MineColoniesBattleMageRegistries.JOB_REGISTRY_KEY,
            MineColoniesBattleMageRegistries.BATTLE_MAGE_ID,
            MineColoniesBattleMageRegistration::createJobEntry
        );
        event.register(
            MineColoniesBattleMageRegistries.GUARD_TYPE_REGISTRY_KEY,
            MineColoniesBattleMageRegistries.BATTLE_MAGE_ID,
            () -> createGuardType(MineColoniesBattleMageRegistration::registeredJobEntry)
        );
    }

    private static JobEntry registeredJobEntry() {
        JobEntry entry = IJobRegistry.getInstance().get(MineColoniesBattleMageRegistries.BATTLE_MAGE_ID);
        if (entry == null) {
            throw new IllegalStateException("Battle Mage JobEntry requested before MineColonies job registration completed");
        }
        return entry;
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
