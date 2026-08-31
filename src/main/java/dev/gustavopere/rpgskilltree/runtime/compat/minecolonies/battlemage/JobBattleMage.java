package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.client.render.modeltype.ModModelTypes;
import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.core.colony.buildings.AbstractBuildingGuards;
import com.minecolonies.core.colony.jobs.AbstractJobGuard;
import com.minecolonies.core.entity.ai.workers.guard.AbstractEntityAIGuard;
import net.minecraft.resources.ResourceLocation;

/**
 * MineColonies-owned guard job for the Battle Mage.
 *
 * <p>The combat AI is deliberately introduced in the following TDD slice. Registration is not
 * activated until the job has a functional provider-native combat implementation.</p>
 */
public final class JobBattleMage extends AbstractJobGuard<JobBattleMage> {
    public JobBattleMage(ICitizenData citizen) {
        super(citizen);
    }

    @Override
    protected AbstractEntityAIGuard<JobBattleMage, ? extends AbstractBuildingGuards> generateGuardAI() {
        throw new IllegalStateException("Battle Mage combat AI not installed yet");
    }

    @Override
    public ResourceLocation getModel() {
        return ModModelTypes.DRUID_ID;
    }
}
