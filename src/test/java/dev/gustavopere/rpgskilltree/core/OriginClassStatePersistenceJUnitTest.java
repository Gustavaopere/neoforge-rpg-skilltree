package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

final class OriginClassStatePersistenceJUnitTest {
    @Test
    void emptyOriginIsExplicitlyUnselected() {
        OriginClassState state = OriginClassState.empty();

        assertFalse(state.selectedClassId().isPresent());
    }

    @Test
    void originRequiresNamespacedIdentityAndNormalSelectionIsImmutable() {
        assertThrows(IllegalArgumentException.class, () -> OriginClassState.empty().select("mage"));
        assertThrows(IllegalArgumentException.class, () -> OriginClassState.empty().select("RPG:mage"));

        OriginClassState selected = OriginClassState.empty().select("rpgskilltree:mage");
        assertEquals(Optional.of("rpgskilltree:mage"), selected.selectedClassId());
        assertSame(selected, selected.select("rpgskilltree:mage"));
        assertThrows(IllegalStateException.class, () -> selected.select("rpgskilltree:warrior"));
    }

    @Test
    void canonicalCodecRoundTripsOriginAsItsOwnAuthoritativeSection() {
        CanonicalPlayerState base = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(),
            Optional.empty(),
            rules()
        );
        CanonicalPlayerState source = base.withOriginClass(
            OriginClassState.empty().select("rpgskilltree:druid")
        );

        CanonicalPlayerState decoded = CanonicalPlayerStateCodec.decode(
            CanonicalPlayerStateCodec.encode(source)
        );

        assertEquals(Optional.of("rpgskilltree:druid"), decoded.originClass().selectedClassId());
        assertEquals(source, decoded);
    }

    @Test
    void canonicalV2PayloadMigratesWithoutGuessingAnOrigin() {
        CanonicalPlayerState source = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(),
            Optional.empty(),
            rules()
        );

        byte[] legacyV2 = encodeCanonicalV2(
            CoreProgressionStateCodec.encode(source.coreProgression()),
            ProgressionStateCodec.encode(source.compatibilityProgression())
        );
        CanonicalPlayerState decoded = CanonicalPlayerStateCodec.decode(legacyV2);

        assertTrue(decoded.originClass().selectedClassId().isEmpty());
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            160L,
            "rpgskilltree:origin_class_persistence_test",
            List.of(new LevelCurveBand(0L, 100L, 2L)),
            new MainPerkBudget(30L)
        );
    }

    private static byte[] encodeCanonicalV2(byte[] core, byte[] compatibility) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(2);
                writeSection(out, core);
                writeSection(out, compatibility);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static void writeSection(DataOutputStream out, byte[] section) throws IOException {
        out.writeInt(section.length);
        out.write(section);
    }
}
