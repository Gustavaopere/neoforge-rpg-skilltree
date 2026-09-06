package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.SpiritPracticeAction;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class MalumMasteryLogicJUnitTest {
    @Test
    void collectionActionUsesStableCanonicalShape() {
        SpiritPracticeAction action = MalumMasteryLogic.collectionAction();

        assertEquals("malum:collection", action.origin().sourceId());
        assertEquals(0, action.origin().procDepth());
        assertEquals("malum", action.provider());
        assertEquals("natural_spirit", action.actionId());
        assertEquals("malum:natural_spirit", action.stableActionId());
        assertEquals(java.util.Set.of("collection"), action.tags());
        assertEquals(1, action.magnitude());
    }

    @Test
    void reapingRequiresConfirmedSpiritEvidence() {
        ResourceLocation targetId = ResourceLocation.fromNamespaceAndPath("minecraft", "zombie");

        assertTrue(MalumMasteryLogic.reapingAction(targetId, MalumMasteryLogic.SpiritEvidence.EMPTY).isEmpty());
        assertTrue(MalumMasteryLogic.reapingAction(
            targetId,
            new MalumMasteryLogic.SpiritEvidence(List.of("malum:aqueous_spirit"), 0)
        ).isEmpty());
        assertTrue(MalumMasteryLogic.reapingAction(
            targetId,
            new MalumMasteryLogic.SpiritEvidence(List.of(), 2)
        ).isEmpty());
    }

    @Test
    void reapingCarriesTargetAffinityTagsAndObservedMagnitude() {
        ResourceLocation targetId = ResourceLocation.fromNamespaceAndPath("minecraft", "zombie");
        MalumMasteryLogic.SpiritEvidence evidence = new MalumMasteryLogic.SpiritEvidence(
            List.of("malum:aqueous_spirit", "malum:wicked_spirit"),
            3
        );

        Optional<SpiritPracticeAction> result = MalumMasteryLogic.reapingAction(targetId, evidence);

        assertTrue(result.isPresent());
        SpiritPracticeAction action = result.orElseThrow();
        assertEquals("malum:reaping", action.origin().sourceId());
        assertEquals("malum", action.provider());
        assertEquals("reap:minecraft:zombie", action.actionId());
        assertTrue(action.tags().contains("reaping"));
        assertTrue(action.tags().contains("spirit:malum/aqueous"));
        assertTrue(action.tags().contains("spirit:malum/wicked"));
        assertEquals(3, action.magnitude());
    }
}
