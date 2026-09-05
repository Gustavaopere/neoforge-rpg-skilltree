package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.client.render.modeltype.ModModelTypes;
import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.core.colony.buildings.AbstractBuildingGuards;
import com.minecolonies.core.colony.jobs.AbstractJobGuard;
import com.minecolonies.core.entity.ai.workers.guard.AbstractEntityAIGuard;
import net.minecraft.resources.ResourceLocation;

/** MineColonies-owned guard job for the Battle Mage. */
public final class JobBattleMage extends AbstractJobGuard<JobBattleMage> {
    public JobBattleMage(ICitizenData citizen) {
        super(citizen);
    }

    @Override
    protected AbstractEntityAIGuard<JobBattleMage, ? extends AbstractBuildingGuards> generateGuardAI() {
        return new EntityAIBattleMage(this);
    }

    @Override
    public ResourceLocation getModel() {
        // Reuse a native MineColonies humanoid caster-compatible guard model until an optional
        // presentation layer supplies dedicated visuals. Model choice is not cast authority.
        return ModModelTypes.DRUID_ID;
    }
}
