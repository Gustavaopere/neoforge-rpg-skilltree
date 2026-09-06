package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minecolonies.core.entity.ai.workers.guard.AbstractEntityAIGuard;
import com.minecolonies.core.entity.ai.workers.guard.RangeCombatAI;
import org.junit.jupiter.api.Test;

final class BattleMageAiSurfaceJUnitTest {
    @Test
    void battleMageKeepsMineColoniesGuardAndRangedCombatStateMachines() {
        assertTrue(AbstractEntityAIGuard.class.isAssignableFrom(EntityAIBattleMage.class));
        assertTrue(RangeCombatAI.class.isAssignableFrom(BattleMageCombatAI.class));
    }
}
