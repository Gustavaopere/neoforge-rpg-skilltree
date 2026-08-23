package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/** Classifies Ars spell recipes into broad mastery lanes using semantic glyph-id tokens. */
public final class ArsCompositionClassifier {
    private ArsCompositionClassifier() {}

    public static Set<String> classify(Collection<String> glyphIds) {
        Set<String> tokens = new HashSet<>();
        for (String glyphId : glyphIds) {
            if (glyphId == null || glyphId.isBlank()) continue;
            for (String token : glyphId.toLowerCase(Locale.ROOT).split("[^a-z0-9]+")) {
                if (!token.isBlank()) tokens.add(token);
            }
        }

        Set<String> lanes = new HashSet<>();
        if (hasAny(tokens, "projectile", "homing")) lanes.add("projectile");
        if (hasAny(tokens, "amplify", "amplification", "empower")) lanes.add("amplification");
        if (hasAny(tokens, "aoe", "radius", "expand", "burst")) lanes.add("aoe");
        if (hasAny(tokens, "duration", "extend", "linger", "lingering", "prolong")) lanes.add("duration");
        if (hasAny(tokens, "summon", "summoning", "construct", "familiar")) lanes.add("summoning");
        if (hasAny(tokens, "control", "snare", "gravity", "pull", "knockback", "root", "freeze", "slow", "charm", "launch", "immobilize")) lanes.add("control");
        return Set.copyOf(lanes);
    }

    private static boolean hasAny(Set<String> tokens, String... candidates) {
        for (String candidate : candidates) {
            if (tokens.contains(candidate)) return true;
        }
        return false;
    }
}
