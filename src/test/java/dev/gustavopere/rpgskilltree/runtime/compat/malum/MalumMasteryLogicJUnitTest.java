package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.SpiritPracticeAction;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

    @Test
    void evidenceParserUsesRegistryIdsCountsAndIgnoresNoise() {
        ItemStack soulSand = new ItemStack(Items.SOUL_SAND, 3);

        MalumMasteryLogic.SpiritEvidence evidence = MalumMasteryLogic.evidenceFromStacks(
            List.of("not-an-item-stack", ItemStack.EMPTY, soulSand)
        );

        assertEquals(List.of("minecraft:soul_sand"), evidence.spiritItemIds());
        assertEquals(3, evidence.totalSpirits());
    }

    @Test
    void evidenceParserFailsClosedForUnsupportedContainer() {
        assertEquals(MalumMasteryLogic.SpiritEvidence.EMPTY, MalumMasteryLogic.evidenceFromStacks("not-a-list"));
    }

    @Test
    void reflectionReaderUsesExactPublicContractAndFailsClosed() {
        MalumMasteryLogic.SpiritEvidence evidence = MalumSpiritDataReader.read(
            null,
            FakeSpiritDropData.class.getName()
        );
        assertEquals(List.of("minecraft:soul_sand"), evidence.spiritItemIds());
        assertEquals(2, evidence.totalSpirits());

        assertEquals(
            MalumMasteryLogic.SpiritEvidence.EMPTY,
            MalumSpiritDataReader.read(null, MissingSpiritDropData.class.getName())
        );
        assertEquals(
            MalumMasteryLogic.SpiritEvidence.EMPTY,
            MalumSpiritDataReader.read(null, EmptySpiritDropData.class.getName())
        );
        assertEquals(
            MalumMasteryLogic.SpiritEvidence.EMPTY,
            MalumSpiritDataReader.read(null, InvalidStacksSpiritDropData.class.getName())
        );
    }

    public static final class FakeSpiritDropData {
        public static Optional<FakeSpiritData> getSpiritData(LivingEntity ignored) {
            return Optional.of(new FakeSpiritData());
        }
    }

    public static final class FakeSpiritData {
        public List<?> getSpiritStacks() {
            return List.of(new ItemStack(Items.SOUL_SAND, 2));
        }
    }

    public static final class EmptySpiritDropData {
        public static Optional<FakeSpiritData> getSpiritData(LivingEntity ignored) {
            return Optional.empty();
        }
    }

    public static final class InvalidStacksSpiritDropData {
        public static Optional<InvalidStacksSpiritData> getSpiritData(LivingEntity ignored) {
            return Optional.of(new InvalidStacksSpiritData());
        }
    }

    public static final class InvalidStacksSpiritData {
        public Object getSpiritStacks() {
            return "not-a-list";
        }
    }

    public static final class MissingSpiritDropData {
        private MissingSpiritDropData() {
        }
    }
}
