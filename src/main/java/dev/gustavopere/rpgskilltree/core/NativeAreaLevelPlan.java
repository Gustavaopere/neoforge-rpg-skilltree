package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;

/** Data-driven plan for composing one Native Area Level without embedding balance constants. */
public record NativeAreaLevelPlan(
    long baseLevel,
    List<NativeAreaLevelContribution> contributions,
    OptionalLong overrideLevel
) {
    public NativeAreaLevelPlan {
        if (baseLevel < 0L) {
            throw new IllegalArgumentException("baseLevel must be non-negative");
        }
        Objects.requireNonNull(contributions, "contributions");
        Objects.requireNonNull(overrideLevel, "overrideLevel");
        if (overrideLevel.isPresent() && overrideLevel.getAsLong() < 0L) {
            throw new IllegalArgumentException("overrideLevel must be non-negative");
        }

        ArrayList<NativeAreaLevelContribution> ordered = new ArrayList<>(contributions.size());
        HashSet<String> sources = new HashSet<>();
        for (NativeAreaLevelContribution contribution : contributions) {
            NativeAreaLevelContribution checked = Objects.requireNonNull(contribution, "contribution");
            if (!sources.add(checked.sourceId())) {
                throw new IllegalArgumentException("duplicate native area contribution source: " + checked.sourceId());
            }
            ordered.add(checked);
        }
        ordered.sort((left, right) -> left.sourceId().compareTo(right.sourceId()));
        contributions = List.copyOf(ordered);
    }

    public static NativeAreaLevelPlan of(long baseLevel, List<NativeAreaLevelContribution> contributions) {
        return new NativeAreaLevelPlan(baseLevel, contributions, OptionalLong.empty());
    }

    public static NativeAreaLevelPlan withOverride(
        long baseLevel,
        List<NativeAreaLevelContribution> contributions,
        long overrideLevel
    ) {
        return new NativeAreaLevelPlan(baseLevel, contributions, OptionalLong.of(overrideLevel));
    }
}
