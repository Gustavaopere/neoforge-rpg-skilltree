package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static com.minecolonies.api.entity.ai.statemachine.states.AIWorkerState.IDLE;

import com.minecolonies.api.util.BlockPosUtil;
import com.minecolonies.core.colony.buildings.AbstractBuildingGuards;
import com.minecolonies.core.entity.ai.workers.guard.AbstractEntityAIGuard;
import com.minecolonies.core.entity.citizen.EntityCitizen;
import com.minecolonies.core.entity.pathfinding.navigation.MinecoloniesAdvancedPathNavigate;
import com.minecolonies.core.entity.pathfinding.pathjobs.PathJobWalkRandomEdge;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

/** Battle Mage guard worker that keeps MineColonies' ordinary guard movement and duty lifecycle. */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public final class EntityAIBattleMage extends AbstractEntityAIGuard<JobBattleMage, AbstractBuildingGuards> {
    public EntityAIBattleMage(@NotNull JobBattleMage job) {
        super(job);
        new BattleMageCombatAI((EntityCitizen) worker, getStateAI(), this);
    }

    @Override
    protected void updateRenderMetaData() {
        worker.setRenderMetadata(getState() == IDLE ? "" : RENDER_META_WORKING);
    }

    @Override
    protected void atBuildingActions() {
        // Spellbooks are deliberately player-supplied high-value equipment. The worker must never
        // fabricate or auto-request an RPG-owned substitute through the warehouse.
        super.atBuildingActions();
    }

    @Override
    public void guardMovement() {
        if (!worker.getNavigation().isDone()) {
            return;
        }

        BlockPos guardPos = buildingGuards.getGuardPos(worker);
        if (BlockPosUtil.dist(guardPos, worker.blockPosition()) <= 10 || walkToSafePos(guardPos)) {
            ((MinecoloniesAdvancedPathNavigate) worker.getNavigation()).setPathJob(
                new PathJobWalkRandomEdge(world, guardPos, 12, worker),
                null,
                1.0,
                true
            );
        }
    }
}
