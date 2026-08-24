package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/** Separate physical binding for the frozen second combat batch. */
public final class FrozenCombatPerkNodeBinding {
    private static final String PREFIX = "rpgskilltree:combat/";

    private FrozenCombatPerkNodeBinding() {}

    public static String nodeId(String code) {
        return PREFIX + FrozenA0051A0100Catalog.definition(code)
            .orElseThrow(() -> new IllegalArgumentException("unknown frozen perk: " + code))
            .code().toLowerCase(Locale.ROOT);
    }

    public static String anyBatchNodeId(String code) {
        return FrozenA0051A0100Catalog.definition(code).isPresent()
            ? nodeId(code)
            : CombatPerkNodeBinding.nodeId(code);
    }

    public static Optional<String> catalogCode(String nodeId) {
        if (nodeId == null || !nodeId.startsWith(PREFIX)) return Optional.empty();
        String suffix = nodeId.substring(PREFIX.length());
        if (suffix.length() != 5 || suffix.charAt(0) != 'a') return Optional.empty();
        String code = suffix.toUpperCase(Locale.ROOT);
        return FrozenA0051A0100Catalog.definition(code).map(FrozenCombatPerkDefinition::code);
    }

    public static FrozenCombatPerkRanks ranks(PassiveNodeProgress progress) {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (String nodeId : progress.learnedNodeIds()) {
            catalogCode(nodeId).ifPresent(code -> result.put(code, progress.rank(nodeId)));
        }
        return FrozenCombatPerkRanks.of(result);
    }
}
