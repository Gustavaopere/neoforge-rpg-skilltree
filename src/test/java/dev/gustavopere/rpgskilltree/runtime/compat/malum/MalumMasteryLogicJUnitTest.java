package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.SpiritPracticeAction;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
    void evidenceParserUsesResolvedIdsCountsAndIgnoresNoise() {
        ItemStack emptyStack = mock(ItemStack.class);
        when(emptyStack.isEmpty()).thenReturn(true);

        ItemStack soulSand = mock(ItemStack.class);
        when(soulSand.isEmpty()).thenReturn(false);
        when(soulSand.getCount()).thenReturn(3);
        ResourceLocation soulSandId = ResourceLocation.fromNamespaceAndPath("minecraft", "soul_sand");

        MalumMasteryLogic.SpiritEvidence evidence = MalumMasteryLogic.evidenceFromStacks(
            List.of("not-an-item-stack", emptyStack, soulSand),
            stack -> stack == soulSand ? soulSandId : null
        );

        assertEquals(List.of("minecraft:soul_sand"), evidence.spiritItemIds());
        assertEquals(3, evidence.totalSpirits());
    }

    @Test
    void evidenceParserFailsClosedForUnsupportedOrUnresolvedStacks() {
        assertEquals(
            MalumMasteryLogic.SpiritEvidence.EMPTY,
            MalumMasteryLogic.evidenceFromStacks("not-a-list", ignored -> null)
        );

        ItemStack unresolved = mock(ItemStack.class);
        when(unresolved.isEmpty()).thenReturn(false);
        assertEquals(
            MalumMasteryLogic.SpiritEvidence.EMPTY,
            MalumMasteryLogic.evidenceFromStacks(List.of(unresolved), ignored -> null)
        );
    }

    @Test
    void reflectionReaderUsesExactPublicContractAndFailsClosed() {
        MalumMasteryLogic.SpiritEvidence expected = new MalumMasteryLogic.SpiritEvidence(
            List.of("minecraft:soul_sand"),
            2
        );
        MalumMasteryLogic.SpiritEvidence evidence = MalumSpiritDataReader.read(
            null,
            FakeSpiritDropData.class.getName(),
            rawStacks -> {
                assertEquals(List.of("provider-stack-token"), rawStacks);
                return expected;
            }
        );
        assertEquals(expected, evidence);

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
            MalumSpiritDataReader.read(null, InvalidOptionalSpiritDropData.class.getName())
        );
        assertEquals(
            MalumMasteryLogic.SpiritEvidence.EMPTY,
            MalumSpiritDataReader.read(
                null,
                FakeSpiritDropData.class.getName(),
                rawStacks -> {
                    throw new IllegalStateException("simulated decoder failure");
                }
            )
        );
    }

    public static final class FakeSpiritDropData {
        public static Optional<FakeSpiritData> getSpiritData(LivingEntity ignored) {
            return Optional.of(new FakeSpiritData());
        }
    }

    public static final class FakeSpiritData {
        public List<?> getSpiritStacks() {
            return List.of("provider-stack-token");
        }
    }

    public static final class EmptySpiritDropData {
        public static Optional<FakeSpiritData> getSpiritData(LivingEntity ignored) {
            return Optional.empty();
        }
    }

    public static final class InvalidOptionalSpiritDropData {
        public static Object getSpiritData(LivingEntity ignored) {
            return "not-an-optional";
        }
    }

    public static final class MissingSpiritDropData {
        private MissingSpiritDropData() {
        }
    }
}
