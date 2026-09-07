package dev.gustavopere.rpgskilltree.itemization.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.itemization.CuriosTagEquipmentClassificationAdapter;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CuriosTagEquipmentClassificationAdapterTest {
    @Test
    void curioSlotTagsClassifyNonDurableWearablesWhenCuriosIsAvailable() {
        CuriosTagEquipmentClassificationAdapter adapter = new CuriosTagEquipmentClassificationAdapter(() -> true);

        EquipmentAdapterContribution ring = adapter.classify(probe(Set.of(tag("ring")))).orElseThrow();
        EquipmentAdapterContribution necklace = adapter.classify(probe(Set.of(tag("necklace")))).orElseThrow();
        EquipmentAdapterContribution amulet = adapter.classify(probe(Set.of(tag("amulet")))).orElseThrow();
        EquipmentAdapterContribution other = adapter.classify(probe(Set.of(tag("charm")))).orElseThrow();

        assertEquals(EligibilityOverride.WHITELIST, ring.eligibility());
        assertEquals(Set.of(EquipmentCategory.WEARABLE_RING), ring.categories());
        assertEquals(Set.of(EquipmentCategory.WEARABLE_NECKLACE), necklace.categories());
        assertEquals(Set.of(EquipmentCategory.WEARABLE_AMULET), amulet.categories());
        assertEquals(Set.of(EquipmentCategory.WEARABLE_OTHER), other.categories());
    }

    @Test
    void multipleCurioSlotTagsRemainComposable() {
        CuriosTagEquipmentClassificationAdapter adapter = new CuriosTagEquipmentClassificationAdapter(() -> true);

        EquipmentAdapterContribution contribution = adapter
            .classify(probe(Set.of(tag("ring"), tag("necklace"))))
            .orElseThrow();

        assertEquals(
            Set.of(EquipmentCategory.WEARABLE_RING, EquipmentCategory.WEARABLE_NECKLACE),
            contribution.categories()
        );
    }

    @Test
    void adapterFailsClosedWhenCuriosIsAbsent() {
        CuriosTagEquipmentClassificationAdapter adapter = new CuriosTagEquipmentClassificationAdapter(() -> false);

        Optional<EquipmentAdapterContribution> contribution = adapter.classify(probe(Set.of(tag("ring"))));

        assertTrue(contribution.isEmpty());
    }

    @Test
    void foreignTagsDoNotBecomeCuriosWearables() {
        CuriosTagEquipmentClassificationAdapter adapter = new CuriosTagEquipmentClassificationAdapter(() -> true);

        Optional<EquipmentAdapterContribution> contribution = adapter.classify(
            probe(Set.of(ResourceLocation.parse("example:ring")))
        );

        assertFalse(contribution.isPresent());
    }

    private static EquipmentProbe probe(Set<ResourceLocation> tags) {
        return new EquipmentProbe(
            ResourceLocation.parse("example:wearable"),
            tags,
            false,
            false,
            false,
            false,
            true,
            Set.of()
        );
    }

    private static ResourceLocation tag(String path) {
        return ResourceLocation.fromNamespaceAndPath("curios", path);
    }
}
