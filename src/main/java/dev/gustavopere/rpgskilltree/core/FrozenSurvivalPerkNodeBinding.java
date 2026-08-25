package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/** Stable passive-node projection for the frozen A0101-A0150 batch. */
public final class FrozenSurvivalPerkNodeBinding {
    private static final String PREFIX = "rpgskilltree:frozen/";

    private FrozenSurvivalPerkNodeBinding() {}

    public static String nodeId(String code) {
        return PREFIX + FrozenA0101A0150Catalog.definition(code)
            .orElseThrow(() -> new IllegalArgumentException("unknown frozen survival perk: " + code))
            .code().toLowerCase(Locale.ROOT);
    }

    public static String anyBatchNodeId(String code) {
        return FrozenA0101A0150Catalog.definition(code).isPresent()
            ? nodeId(code)
            : FrozenCombatPerkNodeBinding.anyBatchNodeId(code);
    }

    public static Optional<String> catalogCode(String nodeId) {
        if (nodeId == null || !nodeId.startsWith(PREFIX)) return Optional.empty();
        String suffix = nodeId.substring(PREFIX.length());
        if (suffix.length() != 5 || suffix.charAt(0) != 'a') return Optional.empty();
        String code = suffix.toUpperCase(Locale.ROOT);
        return FrozenA0101A0150Catalog.definition(code).map(FrozenSurvivalPerkDefinition::code);
    }

    public static FrozenSurvivalPerkRanks ranks(PassiveNodeProgress progress) {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (String learnedNodeId : progress.learnedNodeIds()) {
            catalogCode(learnedNodeId).ifPresent(code -> result.put(code, progress.rank(learnedNodeId)));
        }
        return FrozenSurvivalPerkRanks.of(result);
    }
}
