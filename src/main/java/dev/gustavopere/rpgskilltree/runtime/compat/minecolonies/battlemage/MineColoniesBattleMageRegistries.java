package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.colony.guardtype.GuardType;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.api.entity.citizen.Skill;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Canonical MineColonies registry identities for the Battle Mage integration.
 *
 * <p>This class contains only stable registry/identity data. Deferred registration is deliberately
 * kept in a separate provider-specific bootstrap so optional-provider absence remains fail-closed.</p>
 */
public final class MineColoniesBattleMageRegistries {
    public static final ResourceLocation BATTLE_MAGE_ID =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "battle_mage");

    public static final ResourceKey<Registry<JobEntry>> JOB_REGISTRY_KEY =
        ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecolonies", "jobs"));

    public static final ResourceKey<Registry<GuardType>> GUARD_TYPE_REGISTRY_KEY =
        ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecolonies", "guardtypes"));

    public static final Skill PRIMARY_SKILL = Skill.Mana;
    public static final Skill SECONDARY_SKILL = Skill.Focus;

    public static final String JOB_TRANSLATION_KEY = "com.rpgskilltree.job.battle_mage";
    public static final String BUTTON_TRANSLATION_KEY =
        "com.rpgskilltree.coremod.gui.workerhuts.battle_mage";
    public static final String WORKER_SOUND_NAME = "battle_mage";

    private MineColoniesBattleMageRegistries() {}
}
