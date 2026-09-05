package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.entity.ai.statemachine.tickratestatemachine.ITickRateStateMachine;
import com.minecolonies.core.colony.buildings.AbstractBuildingGuards;
import com.minecolonies.core.entity.ai.workers.guard.AbstractEntityAIGuard;
import com.minecolonies.core.entity.ai.workers.guard.RangeCombatAI;
import com.minecolonies.core.entity.citizen.EntityCitizen;
import net.minecraft.world.entity.LivingEntity;

/**
 * MineColonies ranged-guard combat state machine with only the attack seam replaced by Iron's
 * Battle Mage casting.
 */
public final class BattleMageCombatAI extends RangeCombatAI {
    /** Performance/decision interval only; this is not an Iron's spell cooldown. */
    private static final int THINK_INTERVAL_TICKS = 10;

    public BattleMageCombatAI(
        EntityCitizen owner,
        ITickRateStateMachine stateMachine,
        AbstractEntityAIGuard<JobBattleMage, AbstractBuildingGuards> parentAI
    ) {
        super(owner, stateMachine, parentAI);
    }

    @Override
    public boolean canAttack() {
        if (IronsCitizenMagicBridge.magicData(user).isCasting()) {
            return true;
        }
        return BattleMageCombatController.hasSupportedSpell(user);
    }

    @Override
    protected void doAttack(LivingEntity target) {
        BattleMageCombatController.tryBeginCast(user, target);
    }

    @Override
    protected double getAttackDistance() {
        return BattleMageCombatController.preferredAttackDistance(user);
    }

    @Override
    protected int getAttackDelay() {
        return THINK_INTERVAL_TICKS;
    }
}
