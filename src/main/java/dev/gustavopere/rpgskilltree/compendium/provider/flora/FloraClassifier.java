package dev.gustavopere.rpgskilltree.compendium.provider.flora;

import dev.gustavopere.rpgskilltree.compendium.flora.FloraClassification;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraClassificationEvidence;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraKind;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Pure classifier. Runtime adapters gather evidence; this class never inspects Minecraft classes directly. */
public final class FloraClassifier {
    private FloraClassifier() {}

    public static FloraClassification classify(FloraClassificationEvidence evidence) {
        Objects.requireNonNull(evidence, "evidence");
        if (evidence.explicitIgnore()) {
            return FloraClassification.ignored(List.of("explicit ignore: " + evidence.blockId()));
        }
        if (evidence.explicitOverride() != null) {
            return FloraClassification.classified(
                evidence.explicitOverride(),
                List.of("explicit override: " + evidence.explicitOverride().name())
            );
        }

        Set<FloraKind> candidates = new LinkedHashSet<>();
        List<String> reasons = new ArrayList<>();

        if (evidence.cropClass()) {
            candidates.add(FloraKind.CROP);
            reasons.add("stable class: crop");
        }
        if (evidence.saplingClass()) {
            candidates.add(FloraKind.TREE_COMPONENT);
            reasons.add("stable class: sapling");
        }
        if (evidence.flowerClass()) {
            candidates.add(FloraKind.FLORA);
            reasons.add("stable class: flower");
        }
        if (evidence.fungusClass()) {
            candidates.add(FloraKind.FUNGUS);
            reasons.add("stable class: fungus");
        }
        if (evidence.aquaticClass()) {
            candidates.add(FloraKind.AQUATIC_FLORA);
            reasons.add("stable class: aquatic flora");
        }

        for (String tagId : evidence.tagIds()) {
            FloraKind tagKind = kindForTag(tagId);
            if (tagKind != null) {
                candidates.add(tagKind);
                reasons.add("stable tag: " + tagId);
            }
        }

        if (candidates.size() > 1) {
            List<String> diagnostics = new ArrayList<>(reasons);
            diagnostics.add("ambiguous botanical evidence: " + candidates);
            return FloraClassification.ambiguous(diagnostics);
        }
        if (candidates.size() == 1) {
            return FloraClassification.classified(candidates.iterator().next(), reasons);
        }

        if (evidence.decorativeOnly()) {
            return FloraClassification.unknown(List.of("decorative-only evidence is insufficient"));
        }
        return FloraClassification.unknown(List.of("no stable botanical evidence"));
    }

    private static FloraKind kindForTag(String tagId) {
        return switch (tagId) {
            case "minecraft:flowers", "rpgskilltree:compendium/flora" -> FloraKind.FLORA;
            case "minecraft:saplings", "minecraft:logs", "minecraft:leaves", "rpgskilltree:compendium/tree_components" -> FloraKind.TREE_COMPONENT;
            case "rpgskilltree:compendium/crops" -> FloraKind.CROP;
            case "rpgskilltree:compendium/fungi" -> FloraKind.FUNGUS;
            case "rpgskilltree:compendium/aquatic_flora" -> FloraKind.AQUATIC_FLORA;
            case "rpgskilltree:compendium/block_features" -> FloraKind.BLOCK_FEATURE;
            default -> null;
        };
    }
}
