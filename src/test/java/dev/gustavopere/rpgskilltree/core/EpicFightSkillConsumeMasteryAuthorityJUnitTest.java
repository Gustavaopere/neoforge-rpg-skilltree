package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

final class EpicFightSkillConsumeMasteryAuthorityJUnitTest {
    @Test
    void preConsumeSkillEventCannotAwardMastery() {
        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:skill_consume", 0),
            "epicfight",
            "skill",
            "epicfight:test_guard",
            Set.of("skill", "stamina", "guard", "milestone"),
            2.0D
        );

        assertTrue(MasteryPolicies.forEpicFight(action).isEmpty());
    }

    @Test
    void derivedProcCannotAwardMastery() {
        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:damage_post", 1),
            "epicfight",
            "sword",
            "derived_hit",
            Set.of("hit", "milestone"),
            8.0D
        );

        assertTrue(MasteryPolicies.forEpicFight(action).isEmpty());
    }

    @Test
    void nonMilestoneActionCannotAwardMastery() {
        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:damage_post", 0),
            "epicfight",
            "sword",
            "ordinary_hit",
            Set.of("hit"),
            8.0D
        );

        assertTrue(MasteryPolicies.forEpicFight(action).isEmpty());
    }

    @Test
    void confirmedProviderPostResultStillAwardsMastery() {
        CombatAction action = new CombatAction(
            new ActionOrigin("epicfight:dodge_success", 0),
            "epicfight",
            "dodge",
            "successful_dodge",
            Set.of("dodge_success", "milestone"),
            0.0D
        );

        assertFalse(MasteryPolicies.forEpicFight(action).isEmpty());
    }
}