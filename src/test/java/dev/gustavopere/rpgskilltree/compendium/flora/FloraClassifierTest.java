package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.FloraClassifier;
import java.util.Set;

public final class FloraClassifierTest {
    public static void main(String[] args) {
        classifiesFlowerFromStableClassEvidence();
        classifiesFungusAsEditorialFloraSubtype();
        classifiesAquaticPlantAsEditorialFloraSubtype();
        classifiesCropAsCropKind();
        classifiesSaplingAsTreeComponent();
        ambiguityFailsClosed();
        decorativeGreenBlockIsNotGuessed();
        explicitIgnoreWinsOverOtherEvidence();
        explicitOverrideWinsOverAutomaticEvidence();
        System.out.println("FloraClassifierTest: PASS");
    }

    private static void classifiesFlowerFromStableClassEvidence() {
        FloraClassification classification = FloraClassifier.classify(evidence(false, false, true, false, false, false, null));
        check(classification.classified(), "flower must classify");
        eq(FloraKind.FLORA, classification.kind());
        eq(CompendiumEntryKind.FLORA, classification.kind().canonicalEntryKind());
        check(classification.categories().contains("flor"), "flower category");
        check(!classification.ambiguous(), "flower must not be ambiguous");
    }

    private static void classifiesFungusAsEditorialFloraSubtype() {
        FloraClassification classification = FloraClassifier.classify(evidence(false, false, false, true, false, false, null));
        eq(FloraKind.FUNGUS, classification.kind());
        eq(CompendiumEntryKind.FLORA, classification.kind().canonicalEntryKind());
        check(classification.categories().contains("fungo"), "fungus editorial category");
    }

    private static void classifiesAquaticPlantAsEditorialFloraSubtype() {
        FloraClassification classification = FloraClassifier.classify(evidence(false, false, false, false, true, false, null));
        eq(FloraKind.AQUATIC_FLORA, classification.kind());
        eq(CompendiumEntryKind.FLORA, classification.kind().canonicalEntryKind());
        check(classification.categories().contains("flora_aquatica"), "aquatic editorial category");
    }

    private static void classifiesCropAsCropKind() {
        FloraClassification classification = FloraClassifier.classify(evidence(true, false, false, false, false, false, null));
        eq(FloraKind.CROP, classification.kind());
        eq(CompendiumEntryKind.CROP, classification.kind().canonicalEntryKind());
    }

    private static void classifiesSaplingAsTreeComponent() {
        FloraClassification classification = FloraClassifier.classify(evidence(false, true, false, false, false, false, null));
        eq(FloraKind.TREE_COMPONENT, classification.kind());
        eq(CompendiumEntryKind.TREE, classification.kind().canonicalEntryKind());
    }

    private static void ambiguityFailsClosed() {
        FloraClassification classification = FloraClassifier.classify(evidence(true, true, false, false, false, false, null));
        check(!classification.classified(), "ambiguous evidence must not guess");
        check(classification.ambiguous(), "ambiguous flag");
        check(!classification.diagnostics().isEmpty(), "ambiguity diagnostic");
    }

    private static void decorativeGreenBlockIsNotGuessed() {
        FloraClassification classification = FloraClassifier.classify(evidence(false, false, false, false, false, true, null));
        check(!classification.classified(), "decorative block must remain unclassified");
        check(!classification.ambiguous(), "decorative-only block is unknown, not conflicting");
    }

    private static void explicitIgnoreWinsOverOtherEvidence() {
        FloraClassificationEvidence evidence = new FloraClassificationEvidence(
            "example:ignored_crop", Set.of("example:crop_tag"), true, false, false, false, false, false,
            FloraKind.CROP, true
        );
        FloraClassification classification = FloraClassifier.classify(evidence);
        check(!classification.classified(), "ignored block must not classify");
        check(classification.ignored(), "ignore status");
    }

    private static void explicitOverrideWinsOverAutomaticEvidence() {
        FloraClassification classification = FloraClassifier.classify(evidence(true, false, false, false, false, false, FloraKind.FLORA));
        eq(FloraKind.FLORA, classification.kind());
        check(classification.diagnostics().stream().anyMatch(value -> value.contains("override")), "override diagnostic");
    }

    private static FloraClassificationEvidence evidence(
        boolean crop,
        boolean sapling,
        boolean flower,
        boolean fungus,
        boolean aquatic,
        boolean decorativeOnly,
        FloraKind explicitOverride
    ) {
        return new FloraClassificationEvidence(
            "minecraft:test", Set.of(), crop, sapling, flower, fungus, aquatic, decorativeOnly,
            explicitOverride, false
        );
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
