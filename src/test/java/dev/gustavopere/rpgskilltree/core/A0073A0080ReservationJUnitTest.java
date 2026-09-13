package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class A0073A0080ReservationJUnitTest {
    @Test void executionReservationRollsBackWithoutConsumingWindow(){A0061A0080CombatState s=new A0061A0080CombatState();assertTrue(s.armExecution("p","t","open",1000));assertTrue(s.reserveExecution("p","t","finish",1100));s.rollbackExecution("p","t","finish");assertTrue(s.reserveExecution("p","t","retry",1200));assertTrue(s.commitExecution("p","t","retry",1250));assertFalse(s.reserveExecution("p","t","third",1300));}
    @Test void executionArmCandidateRequiresMatchingConfirmedRoot(){A0061A0080CombatState s=new A0061A0080CombatState();assertTrue(s.reserveExecutionArmCandidate("p","t","a",1000));assertFalse(s.armExecutionConfirmed("p","t","wrong",1050));assertFalse(s.executionWindowActive("p","t",1100));assertTrue(s.armExecutionConfirmed("p","t","a",1150));assertTrue(s.executionWindowActive("p","t",1200));}
    @Test void firstBloodOpenerAndFinisherCommitOnlyAfterConfirmation(){A0061A0080CombatState s=new A0061A0080CombatState();var o=s.reserveFirstBlood("p","t","a",.90,1000);assertTrue(o==A0061A0080CombatState.FirstBloodReservation.OPENER);s.rollbackFirstBlood("p","t","a");assertFalse(s.firstBloodWindowActive("p","t",1100));o=s.reserveFirstBlood("p","t","b",.90,1200);assertTrue(s.commitFirstBlood("p","t","b",o,1250));var f=s.reserveFirstBlood("p","t","c",.50,1350);assertTrue(f==A0061A0080CombatState.FirstBloodReservation.FINISHER);assertTrue(s.commitFirstBlood("p","t","c",f,1400));assertFalse(s.firstBloodWindowActive("p","t",1450));}
    @Test void opportunityRollbackDoesNotConsumeWindow(){A0061A0080CombatState s=new A0061A0080CombatState();assertTrue(s.armOpportunity("p",1000));assertTrue(s.reserveOpportunity("p","a",1100));s.rollbackOpportunity("p","a");assertTrue(s.reserveOpportunity("p","b",1200));assertTrue(s.commitOpportunity("p","b",1250));assertFalse(s.reserveOpportunity("p","c",1300));}
}
