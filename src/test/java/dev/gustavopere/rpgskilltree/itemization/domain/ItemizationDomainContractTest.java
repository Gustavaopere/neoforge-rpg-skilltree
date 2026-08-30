package dev.gustavopere.rpgskilltree.itemization.domain;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class ItemizationDomainContractTest {
    @Test
    void vocabularyIsCanonicalAndStable() {
        assertEquals(
            List.of("COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY", "MYTHIC", "UNIQUE"),
            List.of(ItemRank.values()).stream().map(Enum::name).toList()
        );
        assertEquals(
            List.of("PREFIX", "SUFFIX", "INFIX"),
            List.of(ModifierFamily.values()).stream().map(Enum::name).toList()
        );
        assertEquals(
            List.of("CRAFT", "SMITHING", "LOOT", "MOB_EQUIPMENT", "MOB_DROP", "REWARD", "TRADE", "MACHINE", "MIGRATION", "ADMIN", "FALLBACK"),
            List.of(GenerationSource.values()).stream().map(Enum::name).toList()
        );

        UUID raw = UUID.fromString("98ea7d65-995e-4a0d-b589-e68021060436");
        ItemizationIdentity identity = ItemizationIdentity.of(raw, 0x5A17C0DEL, 1);
        assertEquals(raw, identity.instanceId());
        assertEquals(0x5A17C0DEL, identity.deterministicSeed());
        assertEquals(1, identity.schemaVersion());
        assertThrows(IllegalArgumentException.class, () -> ItemizationIdentity.of(raw, 1L, 0));

        assertEquals(420, ItemPower.of(420).value());
        assertThrows(IllegalArgumentException.class, () -> ItemPower.of(-1));
    }

    @Test
    void everyModifierFamilyContainsBetweenOneAndFiveRolls() {
        for (ModifierFamily family : ModifierFamily.values()) {
            Map<ModifierFamily, List<RolledModifier>> emptyFamily = modifiers(1);
            emptyFamily.put(family, List.of());
            assertThrows(IllegalArgumentException.class, () -> state(ItemRank.RARE, emptyFamily));

            Map<ModifierFamily, List<RolledModifier>> tooMany = modifiers(1);
            tooMany.put(family, rolls(6));
            assertThrows(IllegalArgumentException.class, () -> state(ItemRank.RARE, tooMany));

            Map<ModifierFamily, List<RolledModifier>> five = modifiers(1);
            five.put(family, rolls(5));
            assertEquals(5, state(ItemRank.RARE, five).modifiers(family).size());
        }
    }

    @Test
    void rankDoesNotDetermineModifierCount() {
        ItemizationState rareOneEach = state(ItemRank.RARE, modifiers(1));
        ItemizationState rareFiveEach = state(ItemRank.RARE, modifiers(5));
        ItemizationState uniqueOneEach = state(ItemRank.UNIQUE, modifiers(1));

        assertEquals(ItemRank.RARE, rareOneEach.rank());
        assertEquals(ItemRank.RARE, rareFiveEach.rank());
        assertEquals(1, rareOneEach.modifiers(ModifierFamily.PREFIX).size());
        assertEquals(5, rareFiveEach.modifiers(ModifierFamily.PREFIX).size());
        assertEquals(1, uniqueOneEach.modifiers(ModifierFamily.PREFIX).size());
    }

    @Test
    void firstGenerationIsDefinitive() {
        ItemizationState first = state(ItemRank.EPIC, modifiers(2));
        ItemizationState replacement = state(ItemRank.MYTHIC, modifiers(3));

        assertSame(first, ItemizationMutationAuthority.initialize(Optional.empty(), first));
        assertThrows(
            IllegalStateException.class,
            () -> ItemizationMutationAuthority.initialize(Optional.of(first), replacement)
        );
    }

    @Test
    void compatibleEvolutionPreservesIdentityAndTrueCopiesForkIt() {
        ItemizationIdentity original = ItemizationIdentity.of(
            UUID.fromString("b4238e48-ddcc-4860-a0af-4507c5e13857"),
            17L,
            1
        );

        assertSame(original, ItemizationIdentityPolicy.preserveForEvolution(original));

        ItemizationIdentity copied = ItemizationIdentityPolicy.forkForTrueCopy(
            original,
            UUID.fromString("57de3b6a-160c-4737-aa50-dd4052d5711e"),
            99L
        );
        assertNotEquals(original.instanceId(), copied.instanceId());
        assertEquals(99L, copied.deterministicSeed());
        assertEquals(original.schemaVersion(), copied.schemaVersion());
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemizationIdentityPolicy.forkForTrueCopy(original, original.instanceId(), 99L)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemizationIdentityPolicy.forkForTrueCopy(
                original,
                UUID.fromString("81fbc3dc-09dc-4324-b136-28efbceef0d8"),
                original.deterministicSeed()
            )
        );
    }

    @Test
    void queryCreatesImmutableProjectionWithoutMutation() {
        ItemizationState state = state(ItemRank.LEGENDARY, modifiers(2));
        ItemizationSnapshot snapshot = ItemizationQueryService.snapshot(state);

        assertEquals(state.identity(), snapshot.identity());
        assertEquals(state.rank(), snapshot.rank());
        assertEquals(state.itemPower(), snapshot.itemPower());
        assertEquals(state.generationSource(), snapshot.generationSource());
        assertEquals(state.modifiers(), snapshot.modifiers());
        assertNotEquals(state.getClass(), snapshot.getClass());

        assertThrows(
            UnsupportedOperationException.class,
            () -> snapshot.modifiers().get(ModifierFamily.PREFIX).add(roll(99))
        );
        assertEquals(2, state.modifiers(ModifierFamily.PREFIX).size());
    }

    @Test
    void technicalDefinitionIdsRemainResourceLocationsAndTranslationsAreNotPersisted() {
        RolledModifier modifier = new RolledModifier(
            ResourceLocation.fromNamespaceAndPath("rpgskilltree", "critical_precision"),
            Map.of("magnitude", 0.125D)
        );

        assertEquals("rpgskilltree:critical_precision", modifier.definitionId().toString());
        assertFalse(modifier.rolls().containsKey("displayName"));
        assertThrows(UnsupportedOperationException.class, () -> modifier.rolls().put("other", 1.0D));
    }

    @Test
    void copiedCollectionsCannotMutateCanonicalState() {
        EnumMap<ModifierFamily, List<RolledModifier>> source = new EnumMap<>(ModifierFamily.class);
        ArrayList<RolledModifier> prefixes = new ArrayList<>(rolls(1));
        source.put(ModifierFamily.PREFIX, prefixes);
        source.put(ModifierFamily.SUFFIX, new ArrayList<>(rolls(1)));
        source.put(ModifierFamily.INFIX, new ArrayList<>(rolls(1)));

        ItemizationState state = state(ItemRank.COMMON, source);
        prefixes.add(roll(7));
        source.put(ModifierFamily.PREFIX, rolls(5));

        assertEquals(1, state.modifiers(ModifierFamily.PREFIX).size());
        assertThrows(UnsupportedOperationException.class, () -> state.modifiers().put(ModifierFamily.PREFIX, rolls(2)));
    }

    private static ItemizationState state(ItemRank rank, Map<ModifierFamily, List<RolledModifier>> modifiers) {
        return new ItemizationState(
            ItemizationIdentity.of(
                UUID.fromString("17dd4eaf-8bee-4e10-86fe-22cb5c620601"),
                0x17DD4EAFL,
                1
            ),
            rank,
            ItemPower.of(100),
            GenerationSource.LOOT,
            modifiers
        );
    }

    private static Map<ModifierFamily, List<RolledModifier>> modifiers(int count) {
        EnumMap<ModifierFamily, List<RolledModifier>> modifiers = new EnumMap<>(ModifierFamily.class);
        for (ModifierFamily family : ModifierFamily.values()) {
            modifiers.put(family, rolls(count));
        }
        return modifiers;
    }

    private static List<RolledModifier> rolls(int count) {
        ArrayList<RolledModifier> rolls = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            rolls.add(roll(index));
        }
        return rolls;
    }

    private static RolledModifier roll(int index) {
        return new RolledModifier(
            ResourceLocation.fromNamespaceAndPath("rpgskilltree", "test_modifier_" + index),
            Map.of("roll", index / 10.0D)
        );
    }
}