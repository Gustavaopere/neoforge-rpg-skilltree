package dev.gustavopere.rpgskilltree.core;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public final class CanonicalPlayerAttachmentMigrationChainTest {
    public static void main(String[] args) {
        sequentialMigrationIsBoundedAndDefensive();
        invalidChainsAndOutputsFailClosed();
        canonicalRegistryNormalizesCurrentAndRejectsFuture();
        System.out.println("CanonicalPlayerAttachmentMigrationChainTest: PASS");
    }

    private static void sequentialMigrationIsBoundedAndDefensive() {
        CanonicalPlayerAttachmentMigrationChain chain = new CanonicalPlayerAttachmentMigrationChain(
            3,
            64,
            List.of(
                new CanonicalPlayerAttachmentMigrationStep(
                    1, 2, encoded -> upgradeVersion(encoded, 1, 2, (byte) 0x22)
                ),
                new CanonicalPlayerAttachmentMigrationStep(
                    2, 3, encoded -> upgradeVersion(encoded, 2, 3, (byte) 0x33)
                )
            )
        );

        byte[] v1 = payload(1, (byte) 0x11);
        byte[] v3 = chain.migrateToCurrent(v1);
        arrayEq(payload(3, (byte) 0x11, (byte) 0x22, (byte) 0x33), v3);
        eq(3, encodedVersion(v3));

        byte[] current = chain.migrateToCurrent(v3);
        arrayEq(v3, current);
        if (current == v3) throw new AssertionError("current payload must be defensively copied");

        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(payload(4, (byte) 1)));
        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(payload(0, (byte) 1)));
        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(new byte[3]));
        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(new byte[65]));
    }

    private static void invalidChainsAndOutputsFailClosed() {
        byte[] v1 = payload(1, (byte) 0x11);
        CanonicalPlayerAttachmentMigrationChain gap = new CanonicalPlayerAttachmentMigrationChain(
            3,
            64,
            List.of(new CanonicalPlayerAttachmentMigrationStep(
                1, 2, encoded -> upgradeVersion(encoded, 1, 2, (byte) 1)
            ))
        );
        expect(IllegalArgumentException.class, () -> gap.migrateToCurrent(v1));

        expect(IllegalArgumentException.class, () -> new CanonicalPlayerAttachmentMigrationStep(
            1, 3, encoded -> encoded
        ));
        expect(IllegalArgumentException.class, () -> new CanonicalPlayerAttachmentMigrationChain(
            3,
            64,
            List.of(
                new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> payload(2)),
                new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> payload(2))
            )
        ));

        CanonicalPlayerAttachmentMigrationChain wrongHeader = new CanonicalPlayerAttachmentMigrationChain(
            2,
            64,
            List.of(new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> encoded.clone()))
        );
        expect(IllegalArgumentException.class, () -> wrongHeader.migrateToCurrent(v1));

        CanonicalPlayerAttachmentMigrationChain emptyOutput = new CanonicalPlayerAttachmentMigrationChain(
            2,
            64,
            List.of(new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> new byte[0]))
        );
        expect(IllegalArgumentException.class, () -> emptyOutput.migrateToCurrent(v1));
    }

    private static void canonicalRegistryNormalizesCurrentAndRejectsFuture() {
        byte[] current = CanonicalPlayerAttachmentDataCodec.encode(CanonicalPlayerAttachmentData.empty());
        byte[] normalized = CanonicalPlayerAttachmentMigrations.toCurrent(current);
        arrayEq(current, normalized);
        if (normalized == current) throw new AssertionError("registry normalization must defensively copy");
        eq(CanonicalPlayerAttachmentData.empty(), CanonicalPlayerAttachmentDataCodec.decode(current));

        byte[] future = current.clone();
        ByteBuffer.wrap(future).putInt(CanonicalPlayerAttachmentDataCodec.CURRENT_VERSION + 1);
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentMigrations.toCurrent(future));
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentDataCodec.decode(future));
    }

    private static byte[] upgradeVersion(
        byte[] encoded,
        int expectedVersion,
        int nextVersion,
        byte marker
    ) {
        if (encodedVersion(encoded) != expectedVersion) {
            throw new IllegalArgumentException("unexpected migration source version");
        }
        byte[] upgraded = Arrays.copyOf(encoded, encoded.length + 1);
        ByteBuffer.wrap(upgraded).putInt(nextVersion);
        upgraded[upgraded.length - 1] = marker;
        return upgraded;
    }

    private static byte[] payload(int version, byte... body) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + body.length);
        buffer.putInt(version);
        buffer.put(body);
        return buffer.array();
    }

    private static int encodedVersion(byte[] encoded) {
        if (encoded.length < Integer.BYTES) throw new IllegalArgumentException("payload too short");
        return ByteBuffer.wrap(encoded).getInt();
    }

    private static void arrayEq(byte[] expected, byte[] actual) {
        if (!Arrays.equals(expected, actual)) throw new AssertionError("byte arrays differ");
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
