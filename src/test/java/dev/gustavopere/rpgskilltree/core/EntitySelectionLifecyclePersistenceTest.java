package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public final class EntitySelectionLifecyclePersistenceTest {
    public static void main(String[] args) {
        schemaV3RoundTripsAffixesAndBehaviors();
        schemaV2LoadsAffixesWithEmptyBehaviors();
        legacyConstructorsRemainSourceCompatible();
        initializationPersistsAlreadyResolvedSelections();
        System.out.println("EntitySelectionLifecyclePersistenceTest: PASS");
    }

    private static void schemaV3RoundTripsAffixesAndBehaviors() {
        eq(3, EntityScalingStateCodec.CURRENT_VERSION);
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 7L, -4L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                23L,
                OptionalLong.of(30L),
                30L,
                37L,
                37L
            ),
            2L,
            Optional.of(new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 5L)),
            0x123456789ABCDEFL,
            new MobAffixSelection(List.of(
                MobAffixKey.of("rpgskilltree:swift"),
                MobAffixKey.of("rpgskilltree:armored")
            )),
            new EntityBehaviorSelection(List.of(
                EntityBehaviorKey.of("rpgskilltree:flanker"),
                EntityBehaviorKey.of("rpgskilltree:aggressive")
            ))
        );

        EntityScalingState decoded = EntityScalingStateCodec.decodeState(EntityScalingStateCodec.encode(state));
        eq(state, decoded);
        eq(List.of(
            MobAffixKey.of("rpgskilltree:armored"),
            MobAffixKey.of("rpgskilltree:swift")
        ), decoded.affixes().affixes());
        eq(List.of(
            EntityBehaviorKey.of("rpgskilltree:aggressive"),
            EntityBehaviorKey.of("rpgskilltree:flanker")
        ), decoded.behaviors().behaviors());
    }

    private static void schemaV2LoadsAffixesWithEmptyBehaviors() {
        EntityScalingState decoded = EntityScalingStateCodec.decodeState(legacyV2Payload());
        eq(List.of(
            MobAffixKey.of("rpgskilltree:armored"),
            MobAffixKey.of("rpgskilltree:swift")
        ), decoded.affixes().affixes());
        eq(EntityBehaviorSelection.empty(), decoded.behaviors());
    }

    private static void legacyConstructorsRemainSourceCompatible() {
        TerritoryKey territory = TerritoryKey.of("minecraft:overworld", 0L, 0L);
        EntityLevelResolution level = new EntityLevelResolution(
            EntityArchetype.PASSIVE,
            1L,
            OptionalLong.empty(),
            1L,
            1L,
            1L
        );
        EntityScalingState oldest = new EntityScalingState(
            territory, level, 0L, Optional.empty(), 42L
        );
        eq(MobAffixSelection.empty(), oldest.affixes());
        eq(EntityBehaviorSelection.empty(), oldest.behaviors());

        MobAffixSelection affixes = new MobAffixSelection(List.of(MobAffixKey.of("rpgskilltree:armored")));
        EntityScalingState affixAware = new EntityScalingState(
            territory, level, 0L, Optional.empty(), 42L, affixes
        );
        eq(affixes, affixAware.affixes());
        eq(EntityBehaviorSelection.empty(), affixAware.behaviors());
    }

    private static void initializationPersistsAlreadyResolvedSelections() {
        MobAffixSelection affixes = new MobAffixSelection(List.of(MobAffixKey.of("rpgskilltree:swift")));
        EntityBehaviorSelection behaviors = new EntityBehaviorSelection(List.of(
            EntityBehaviorKey.of("rpgskilltree:flanker")
        ));
        EntityScalingState state = EntityScalingInitializationService.resolve(
            new EntityScalingInitializationInput(
                TerritoryKey.of("minecraft:overworld", 1L, 2L),
                10L,
                OptionalLong.of(12L),
                EntityArchetype.HOSTILE,
                1L,
                Optional.empty(),
                77L,
                affixes,
                behaviors
            )
        );
        eq(affixes, state.affixes());
        eq(behaviors, state.behaviors());

        EntityScalingState legacyInput = EntityScalingInitializationService.resolve(
            new EntityScalingInitializationInput(
                TerritoryKey.of("minecraft:overworld", 1L, 2L),
                10L,
                OptionalLong.empty(),
                EntityArchetype.PASSIVE,
                0L,
                Optional.empty(),
                88L
            )
        );
        eq(MobAffixSelection.empty(), legacyInput.affixes());
        eq(EntityBehaviorSelection.empty(), legacyInput.behaviors());
    }

    private static byte[] legacyV2Payload() {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(buffer)) {
                out.writeInt(2);
                out.writeBoolean(true);
                writeString(out, "minecraft:overworld");
                out.writeLong(2L);
                out.writeLong(-1L);
                writeString(out, "HOSTILE");
                out.writeLong(20L);
                out.writeBoolean(true);
                out.writeLong(35L);
                out.writeLong(35L);
                out.writeLong(40L);
                out.writeLong(40L);
                out.writeLong(1L);
                out.writeBoolean(true);
                writeString(out, "rpgskilltree:veteran");
                out.writeLong(4L);
                out.writeLong(99L);
                out.writeInt(2);
                writeString(out, "rpgskilltree:swift");
                writeString(out, "rpgskilltree:armored");
            }
            return buffer.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
