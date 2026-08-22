package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record ProgressionState(
    long totalCharacterXp,
    PassivePointLedger passivePoints,
    BossProgress bossProgress,
    ClassProgressionState classProgression,
    MasteryState mastery,
    ClassChoiceState classChoices,
    SpecializationProgressionState specializations,
    FinalTriadProgress finalTriads,
    PassiveNodeProgress passiveNodes,
    DiscoveryProgress discoveries
) {
    public ProgressionState {
        if (totalCharacterXp < 0) throw new IllegalArgumentException("totalCharacterXp must be >= 0");
        Objects.requireNonNull(passivePoints);
        Objects.requireNonNull(bossProgress);
        Objects.requireNonNull(classProgression);
        Objects.requireNonNull(mastery);
        Objects.requireNonNull(classChoices);
        Objects.requireNonNull(specializations);
        Objects.requireNonNull(finalTriads);
        Objects.requireNonNull(passiveNodes);
        Objects.requireNonNull(discoveries);
    }

    public static ProgressionState empty() {
        return new ProgressionState(
            0,
            PassivePointLedger.empty(),
            BossProgress.empty(),
            ClassProgressionState.empty(),
            MasteryState.empty(),
            ClassChoiceState.empty(),
            SpecializationProgressionState.empty(),
            FinalTriadProgress.empty(),
            PassiveNodeProgress.empty(),
            DiscoveryProgress.empty()
        );
    }

    public CharacterProgress characterProgress(CharacterLevelCurve curve) {
        return CharacterProgress.fromTotalXp(curve, totalCharacterXp);
    }

    public ProgressionState withPassivePoints(PassivePointLedger ledger) {
        return new ProgressionState(totalCharacterXp, ledger, bossProgress, classProgression, mastery, classChoices, specializations, finalTriads, passiveNodes, discoveries);
    }

    public ProgressionState withBossProgress(BossProgress progress) {
        return new ProgressionState(totalCharacterXp, passivePoints, progress, classProgression, mastery, classChoices, specializations, finalTriads, passiveNodes, discoveries);
    }

    public ProgressionState withClassProgression(ClassProgressionState classes) {
        return new ProgressionState(totalCharacterXp, passivePoints, bossProgress, classes, mastery, classChoices, specializations, finalTriads, passiveNodes, discoveries);
    }

    public ProgressionState withMastery(MasteryState masteryState) {
        return new ProgressionState(totalCharacterXp, passivePoints, bossProgress, classProgression, masteryState, classChoices, specializations, finalTriads, passiveNodes, discoveries);
    }

    public ProgressionState withClassChoices(ClassChoiceState choices) {
        return new ProgressionState(totalCharacterXp, passivePoints, bossProgress, classProgression, mastery, choices, specializations, finalTriads, passiveNodes, discoveries);
    }

    public ProgressionState withSpecializations(SpecializationProgressionState specializationState) {
        return new ProgressionState(totalCharacterXp, passivePoints, bossProgress, classProgression, mastery, classChoices, specializationState, finalTriads, passiveNodes, discoveries);
    }

    public ProgressionState withFinalTriads(FinalTriadProgress triads) {
        return new ProgressionState(totalCharacterXp, passivePoints, bossProgress, classProgression, mastery, classChoices, specializations, triads, passiveNodes, discoveries);
    }

    public ProgressionState withPassiveNodes(PassiveNodeProgress nodes) {
        return new ProgressionState(totalCharacterXp, passivePoints, bossProgress, classProgression, mastery, classChoices, specializations, finalTriads, nodes, discoveries);
    }
    public ProgressionState withDiscoveries(DiscoveryProgress progress) {
        return new ProgressionState(totalCharacterXp, passivePoints, bossProgress, classProgression, mastery, classChoices, specializations, finalTriads, passiveNodes, progress);
    }

}
