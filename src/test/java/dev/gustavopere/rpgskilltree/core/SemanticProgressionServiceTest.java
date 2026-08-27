package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class SemanticProgressionServiceTest {
    public static void main(String[] args) {
        awardedActionMutatesCoreThroughCanonicalBoundary();
        rejectedAuthorshipReturnsExactSameState();
        antiFarmRejectionReturnsExactSameState();
        noAwardReturnsExactSameState();
        acceptedAwardStillEnforcesCoreRulesGate();
        System.out.println("SemanticProgressionServiceTest: PASS");
    }

    static void awardedActionMutatesCoreThroughCanonicalBoundary() {
        ProgressionRulesSnapshot rules = rules(11L);
        CoreProgressionState before = auditedState(rules);
        CharacterXpAward award = new CharacterXpAward(
            "test:semantic/quest",
            250L,
            Set.of(ProgressionDomain.SURVIVAL)
        );

        SemanticProgressionResult result = SemanticProgressionService.apply(
            before,
            action(SemanticActionAuthorship.DIRECT_PLAYER),
            candidate -> AntiFarmDecision.allow(),
            candidate -> Optional.of(award),
            rules
        );

        eq(SemanticXpDecision.AWARDED, result.semanticXp().decision());
        eq(Optional.of(award), result.semanticXp().award());
        eq(5L, result.state().characterProgression().level());
        eq(0L, result.state().characterProgression().xpIntoLevel());
        eq(before.corePoints().checkpoint(), result.state().corePoints().checkpoint());
        auditUnchanged(before, result.state());
        eq(false, before == result.state());
    }

    static void rejectedAuthorshipReturnsExactSameState() {
        ProgressionRulesSnapshot rules = rules(11L);
        CoreProgressionState before = auditedState(rules);

        SemanticProgressionResult result = SemanticProgressionService.apply(
            before,
            action(SemanticActionAuthorship.UNATTRIBUTED_AUTOMATION),
            candidate -> { throw new AssertionError("anti-farm must not run"); },
            candidate -> { throw new AssertionError("XP policy must not run"); },
            rules
        );

        eq(SemanticXpDecision.REJECTED_AUTHORSHIP, result.semanticXp().decision());
        eq(true, before == result.state());
    }

    static void antiFarmRejectionReturnsExactSameState() {
        ProgressionRulesSnapshot rules = rules(11L);
        CoreProgressionState before = auditedState(rules);

        SemanticProgressionResult result = SemanticProgressionService.apply(
            before,
            action(SemanticActionAuthorship.DIRECT_PLAYER),
            candidate -> AntiFarmDecision.reject("test:blocked"),
            candidate -> { throw new AssertionError("XP policy must not run"); },
            rules
        );

        eq(SemanticXpDecision.REJECTED_ANTI_FARM, result.semanticXp().decision());
        eq("test:blocked", result.semanticXp().reason());
        eq(true, before == result.state());
    }

    static void noAwardReturnsExactSameState() {
        ProgressionRulesSnapshot rules = rules(11L);
        CoreProgressionState before = auditedState(rules);

        SemanticProgressionResult result = SemanticProgressionService.apply(
            before,
            action(SemanticActionAuthorship.DIRECT_PLAYER),
            candidate -> AntiFarmDecision.allow(),
            candidate -> Optional.empty(),
            rules
        );

        eq(SemanticXpDecision.NO_AWARD, result.semanticXp().decision());
        eq(true, before == result.state());
    }

    static void acceptedAwardStillEnforcesCoreRulesGate() {
        ProgressionRulesSnapshot original = rules(11L);
        ProgressionRulesSnapshot changed = rules(12L);
        CoreProgressionState before = auditedState(original);

        expect(IllegalStateException.class, () -> SemanticProgressionService.apply(
            before,
            action(SemanticActionAuthorship.DIRECT_PLAYER),
            candidate -> AntiFarmDecision.allow(),
            candidate -> Optional.of(new CharacterXpAward("test:semantic/stale", 1L, Set.of())),
            changed
        ));
    }

    private static SemanticAction action(SemanticActionAuthorship authorship) {
        return new SemanticAction(
            SemanticActionType.QUEST_COMPLETED,
            "test:chapter_one",
            new ActionOrigin("test:quest_adapter", 0),
            authorship,
            new SemanticActionContext(
                java.util.OptionalLong.empty(),
                Map.of("importance", 1.0),
                Set.of("main_quest")
            )
        );
    }

    private static ProgressionRulesSnapshot rules(long version) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:semantic_progression_test",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(3L)
        );
    }

    private static CoreProgressionState auditedState(ProgressionRulesSnapshot rules) {
        CorePointLedger ledger = CorePointLedger.empty().apply(
            CorePointTransaction.credit(
                "migration:semantic_seed",
                CorePointTransactionKind.MIGRATION,
                10L,
                "legacy",
                rules.version()
            )
        );
        return new CoreProgressionState(
            new CharacterProgressionState(2L, 50L),
            ledger,
            rules.version(),
            rules.fingerprint(),
            4,
            123L
        );
    }

    private static void auditUnchanged(CoreProgressionState before, CoreProgressionState after) {
        eq(before.rulesVersion(), after.rulesVersion());
        eq(before.rulesFingerprint(), after.rulesFingerprint());
        eq(before.migrationSourceFormatVersion(), after.migrationSourceFormatVersion());
        eq(before.discardedLegacyCapXp(), after.discardedLegacyCapXp());
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
