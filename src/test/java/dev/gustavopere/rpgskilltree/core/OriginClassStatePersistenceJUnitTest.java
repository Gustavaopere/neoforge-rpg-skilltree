package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

final class OriginClassStatePersistenceJUnitTest {
    @Test
    void emptyOriginIsExplicitlyUnselected() {
        OriginClassState state = OriginClassState.empty();

        assertFalse(state.selectedClassId().isPresent());
        assertTrue(state.equals(state));
        assertEquals("OriginClassState[unselected]", state.toString());
        assertEquals(state.hashCode(), OriginClassState.empty().hashCode());
    }

    @Test
    void originRequiresNamespacedIdentityAndNormalSelectionIsImmutable() {
        assertThrows(NullPointerException.class, () -> OriginClassState.empty().select(null));
        assertThrows(IllegalArgumentException.class, () -> OriginClassState.empty().select("mage"));
        assertThrows(IllegalArgumentException.class, () -> OriginClassState.empty().select("RPG:mage"));

        OriginClassState selected = OriginClassState.empty().select("rpgskilltree:mage");
        OriginClassState sameValue = OriginClassState.empty().select("rpgskilltree:mage");
        assertEquals(Optional.of("rpgskilltree:mage"), selected.selectedClassId());
        assertSame(selected, selected.select("rpgskilltree:mage"));
        assertEquals(selected, sameValue);
        assertEquals(selected.hashCode(), sameValue.hashCode());
        assertEquals("OriginClassState[rpgskilltree:mage]", selected.toString());
        assertNotEquals(selected, OriginClassState.empty());
        assertFalse(selected.equals("rpgskilltree:mage"));
        assertThrows(IllegalStateException.class, () -> selected.select("rpgskilltree:warrior"));
    }

    @Test
    void canonicalStateMutatorsPreserveIdentityAndPersistedValueSemantics() {
        CanonicalPlayerState base = baseState();

        assertSame(base, base.withCoreProgression(base.coreProgression()));
        assertSame(base, base.withCompatibilityProgression(base.compatibilityProgression()));
        assertSame(base, base.withOriginClass(base.originClass()));
        assertThrows(NullPointerException.class, () -> base.withCoreProgression(null));
        assertThrows(NullPointerException.class, () -> base.withCompatibilityProgression(null));
        assertThrows(NullPointerException.class, () -> base.withOriginClass(null));

        CoreProgressionState copiedCore = CoreProgressionStateCodec.decode(
            CoreProgressionStateCodec.encode(base.coreProgression())
        );
        ProgressionState copiedCompatibility = ProgressionStateCodec.decode(
            ProgressionStateCodec.encode(base.compatibilityProgression())
        );
        CanonicalPlayerState copiedCoreEnvelope = base.withCoreProgression(copiedCore);
        CanonicalPlayerState copiedCompatibilityEnvelope = base.withCompatibilityProgression(copiedCompatibility);
        CanonicalPlayerState selectedOriginEnvelope = base.withOriginClass(
            OriginClassState.empty().select("rpgskilltree:mage")
        );

        assertNotSame(base, copiedCoreEnvelope);
        assertNotSame(base, copiedCompatibilityEnvelope);
        assertNotSame(base, selectedOriginEnvelope);
        assertEquals(base, copiedCoreEnvelope);
        assertEquals(base, copiedCompatibilityEnvelope);
        assertNotEquals(base, selectedOriginEnvelope);

        CanonicalPlayerState decodedCopy = CanonicalPlayerStateCodec.decode(
            CanonicalPlayerStateCodec.encode(base)
        );
        assertTrue(base.equals(base));
        assertEquals(base, decodedCopy);
        assertEquals(base.hashCode(), decodedCopy.hashCode());
        assertFalse(base.equals("not a canonical player state"));
        assertTrue(base.toString().contains("originClass=unselected"));
        assertTrue(selectedOriginEnvelope.toString().contains("originClass=rpgskilltree:mage"));

        CanonicalPlayerState differentCoreSource = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(),
            Optional.empty(),
            rules(161L, "rpgskilltree:origin_class_persistence_test_alt")
        );
        assertNotEquals(
            base,
            new CanonicalPlayerState(
                differentCoreSource.coreProgression(),
                base.compatibilityProgression(),
                base.originClass()
            )
        );

        ProgressionState compatibility = base.compatibilityProgression();
        ProgressionState differentCompatibility = new ProgressionState(
            compatibility.totalCharacterXp() + 1L,
            compatibility.passivePoints(),
            compatibility.bossProgress(),
            compatibility.classProgression(),
            compatibility.mastery(),
            compatibility.classChoices(),
            compatibility.specializations(),
            compatibility.finalTriads(),
            compatibility.passiveNodes(),
            compatibility.discoveries()
        );
        assertNotEquals(
            base,
            new CanonicalPlayerState(
                base.coreProgression(),
                differentCompatibility,
                base.originClass()
            )
        );
    }

    @Test
    void canonicalCodecRoundTripsOriginAsItsOwnAuthoritativeSection() {
        CanonicalPlayerState source = baseState().withOriginClass(
            OriginClassState.empty().select("rpgskilltree:druid")
        );

        CanonicalPlayerState decoded = CanonicalPlayerStateCodec.decode(
            CanonicalPlayerStateCodec.encode(source)
        );

        assertEquals(Optional.of("rpgskilltree:druid"), decoded.originClass().selectedClassId());
        assertEquals(source, decoded);
    }

    @Test
    void canonicalCodecRoundTripsExplicitlyUnselectedOrigin() {
        CanonicalPlayerState source = baseState();

        CanonicalPlayerState decoded = CanonicalPlayerStateCodec.decode(
            CanonicalPlayerStateCodec.encode(source)
        );

        assertTrue(decoded.originClass().selectedClassId().isEmpty());
        assertEquals(source, decoded);
    }

    @Test
    void canonicalLegacyPayloadsMigrateWithoutGuessingAnOrigin() {
        CanonicalPlayerState source = baseState();
        byte[] core = CoreProgressionStateCodec.encode(source.coreProgression());
        byte[] compatibility = ProgressionStateCodec.encode(source.compatibilityProgression());

        CanonicalPlayerState decodedV1 = CanonicalPlayerStateCodec.decode(
            encodeCanonicalLegacy(1, core, compatibility)
        );
        CanonicalPlayerState decodedV2 = CanonicalPlayerStateCodec.decode(
            encodeCanonicalLegacy(2, core, compatibility)
        );

        assertTrue(decodedV1.originClass().selectedClassId().isEmpty());
        assertTrue(decodedV2.originClass().selectedClassId().isEmpty());
    }

    @Test
    void canonicalCodecRejectsOuterEnvelopeCorruptionFailClosed() {
        CanonicalPlayerState source = baseState();
        byte[] core = CoreProgressionStateCodec.encode(source.coreProgression());

        assertThrows(IllegalArgumentException.class, () -> CanonicalPlayerStateCodec.encode(null));
        assertThrows(IllegalArgumentException.class, () -> CanonicalPlayerStateCodec.decode(null));
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(new byte[] {0, 0, 0})
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(encodeIntOnly(0))
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(encodeIntOnly(CanonicalPlayerStateCodec.CURRENT_VERSION + 1))
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(appendByte(CanonicalPlayerStateCodec.encode(source), 0x55))
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(encodeVersionAndSectionLength(3, 0))
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(encodeVersionAndSectionLength(3, 16 * 1024 * 1024 + 1))
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(encodeTruncatedCoreSection(core))
        );
    }

    @Test
    void canonicalCodecRejectsOriginSectionCorruptionFailClosed() {
        CanonicalPlayerState source = baseState();
        byte[] core = CoreProgressionStateCodec.encode(source.coreProgression());
        byte[] compatibility = ProgressionStateCodec.encode(source.compatibilityProgression());

        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(
                encodeCanonicalV3(core, compatibility, new byte[] {1})
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(
                encodeCanonicalV3(core, compatibility, new byte[] {0, 1})
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(
                encodeCanonicalV3(core, compatibility, encodeSelectedOriginWithDeclaredLength(0, new byte[0]))
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(
                encodeCanonicalV3(core, compatibility, encodeSelectedOriginWithDeclaredLength(4_097, new byte[0]))
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(
                encodeCanonicalV3(
                    core,
                    compatibility,
                    encodeSelectedOriginWithDeclaredLength(5, "ab".getBytes(StandardCharsets.UTF_8))
                )
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.decode(
                encodeCanonicalV3(core, compatibility, encodeSelectedOrigin("mage"))
            )
        );

        OriginClassState overlong = OriginClassState.empty().select(
            "rpgskilltree:" + "a".repeat(4_096)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> CanonicalPlayerStateCodec.encode(source.withOriginClass(overlong))
        );
    }

    private static CanonicalPlayerState baseState() {
        return CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(),
            Optional.empty(),
            rules()
        );
    }

    private static ProgressionRulesSnapshot rules() {
        return rules(160L, "rpgskilltree:origin_class_persistence_test");
    }

    private static ProgressionRulesSnapshot rules(long version, String id) {
        return new ProgressionRulesSnapshot(
            version,
            id,
            List.of(new LevelCurveBand(0L, 100L, 2L)),
            new MainPerkBudget(30L)
        );
    }

    private static byte[] encodeCanonicalLegacy(int version, byte[] core, byte[] compatibility) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(version);
                writeSection(out, core);
                writeSection(out, compatibility);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static byte[] encodeCanonicalV3(byte[] core, byte[] compatibility, byte[] origin) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(3);
                writeSection(out, core);
                writeSection(out, compatibility);
                writeSection(out, origin);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static byte[] encodeSelectedOrigin(String id) {
        byte[] idBytes = id.getBytes(StandardCharsets.UTF_8);
        return encodeSelectedOriginWithDeclaredLength(idBytes.length, idBytes);
    }

    private static byte[] encodeSelectedOriginWithDeclaredLength(int declaredLength, byte[] idBytes) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeBoolean(true);
                out.writeInt(declaredLength);
                out.write(idBytes);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static byte[] encodeVersionAndSectionLength(int version, int length) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(version);
                out.writeInt(length);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static byte[] encodeTruncatedCoreSection(byte[] core) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(3);
                out.writeInt(core.length + 1);
                out.write(core);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static byte[] encodeIntOnly(int value) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(value);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static byte[] appendByte(byte[] source, int value) {
        byte[] result = Arrays.copyOf(source, source.length + 1);
        result[source.length] = (byte) value;
        return result;
    }

    private static void writeSection(DataOutputStream out, byte[] section) throws IOException {
        out.writeInt(section.length);
        out.write(section);
    }
}
