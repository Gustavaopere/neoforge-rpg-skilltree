package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.MalumSpiritClassifier;
import dev.gustavopere.rpgskilltree.core.SpiritPracticeAction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/** Pure normalization of Malum observations into canonical mastery actions. */
final class MalumMasteryLogic {
    private MalumMasteryLogic() {
    }

    static Optional<SpiritPracticeAction> reapingAction(ResourceLocation targetId, SpiritEvidence evidence) {
        if (evidence.totalSpirits() <= 0 || evidence.spiritItemIds().isEmpty()) {
            return Optional.empty();
        }

        Set<String> tags = new HashSet<>();
        tags.add("reaping");
        tags.addAll(MalumSpiritClassifier.spiritTags(evidence.spiritItemIds()));
        return Optional.of(new SpiritPracticeAction(
            new ActionOrigin("malum:reaping", 0),
            "malum",
            "reap:" + targetId,
            tags,
            evidence.totalSpirits()
        ));
    }

    static SpiritPracticeAction collectionAction() {
        return new SpiritPracticeAction(
            new ActionOrigin("malum:collection", 0),
            "malum",
            "natural_spirit",
            Set.of("collection"),
            1
        );
    }

    static SpiritEvidence evidenceFromObservations(Object rawObservations) {
        if (!(rawObservations instanceof List<?> observations)) {
            return SpiritEvidence.EMPTY;
        }

        List<String> ids = new ArrayList<>();
        int total = 0;
        for (Object rawObservation : observations) {
            if (!(rawObservation instanceof SpiritStackObservation observation)) {
                continue;
            }
            ids.add(observation.itemId());
            total += Math.max(1, observation.count());
        }
        return ids.isEmpty() ? SpiritEvidence.EMPTY : new SpiritEvidence(List.copyOf(ids), total);
    }

    record SpiritStackObservation(String itemId, int count) {
        SpiritStackObservation {
            Objects.requireNonNull(itemId, "itemId");
            if (itemId.isBlank()) {
                throw new IllegalArgumentException("blank spirit item id");
            }
        }
    }

    record SpiritEvidence(List<String> spiritItemIds, int totalSpirits) {
        static final SpiritEvidence EMPTY = new SpiritEvidence(List.of(), 0);
    }
}
