package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ProgressionStateCodec {
    /** Legacy compatibility payload version. New normal saves are versioned by CanonicalPlayerStateCodec. */
    public static final int CURRENT_VERSION = 4;
    private static final int MAX_COLLECTION_SIZE = 16_384;
    private static final int MAX_STRING_BYTES = 4_096;

    private ProgressionStateCodec() {}

    public static byte[] encode(ProgressionState state) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);
                out.writeLong(state.totalCharacterXp());
                writeLedger(out, state.passivePoints());
                writeStringSet(out, state.bossProgress().creditedRewardKeys());
                writeStringSet(out, state.classProgression().unlockedClassIds());
                writeStringIntMap(out, state.mastery().experience());
                writeStringSetMap(out, state.classChoices().selections());
                writeStringSet(out, state.specializations().unlockedSpecializationIds());
                writeFinalTriads(out, state.finalTriads());
                writeStringIntMap(out, state.passiveNodes().ranks());
                writeStringSet(out, state.discoveries().discoveredKeys());
                // Canonical-player schema v2 extends compatibility v4 with bounded optional tails.
                writeMasteryReceipts(out, state.mastery().creditedAwards());
                writeStringSet(out, state.classProgression().paidBridgeClassIds());
            }
            return bytes.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static ProgressionState decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded state must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(encoded))) {
            int version = in.readInt();
            if (version < 1 || version > CURRENT_VERSION) throw new IllegalArgumentException("unsupported progression state version: " + version);
            long totalXp = in.readLong();
            PassivePointLedger ledger = readLedger(in);
            BossProgress bosses = BossProgress.of(readStringSet(in));
            Set<String> unlockedClasses = readStringSet(in);
            Map<String, Integer> masteryExperience = readStringIntMap(in);
            ClassChoiceState choices = ClassChoiceState.of(readStringSetMap(in));
            SpecializationProgressionState specializations = SpecializationProgressionState.of(readStringSet(in));
            FinalTriadProgress finalTriads = version >= 2 ? readFinalTriads(in) : FinalTriadProgress.empty();
            PassiveNodeProgress passiveNodes = version >= 3 ? PassiveNodeProgress.of(readStringIntMap(in)) : PassiveNodeProgress.empty();
            DiscoveryProgress discoveries = version >= 4 ? DiscoveryProgress.of(readStringSet(in)) : DiscoveryProgress.empty();
            Map<String, MasteryAwardReceipt> masteryReceipts = version >= 4 && in.available() > 0
                ? readMasteryReceipts(in)
                : Map.of();
            Set<String> paidBridgeClasses = version >= 4 && in.available() > 0
                ? readStringSet(in)
                : Set.of();
            if (in.available() != 0) throw new IllegalArgumentException("progression state contains trailing bytes");
            ClassProgressionState classes = ClassProgressionState.of(unlockedClasses, paidBridgeClasses);
            ProgressionState decoded = new ProgressionState(
                totalXp,
                ledger,
                bosses,
                classes,
                MasteryState.of(masteryExperience, masteryReceipts),
                choices,
                specializations,
                finalTriads,
                passiveNodes,
                discoveries
            );
            return ProgressionStateMigrations.migrate(decoded, version);
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid progression state payload", e);
        }
    }

    private static void writeLedger(DataOutputStream out, PassivePointLedger ledger) throws IOException {
        List<PassivePointSource> sources = new ArrayList<>(ledger.earnedBySource().keySet());
        sources.sort(Comparator.comparing(Enum::name));
        out.writeInt(sources.size());
        for (PassivePointSource source : sources) {
            writeString(out, source.name());
            out.writeInt(ledger.earned(source));
        }
        out.writeInt(ledger.spent());
    }

    private static PassivePointLedger readLedger(DataInputStream in) throws IOException {
        int count = readCollectionSize(in);
        EnumMap<PassivePointSource, Integer> earned = new EnumMap<>(PassivePointSource.class);
        for (int i = 0; i < count; i++) {
            String sourceName = readString(in);
            int points = in.readInt();
            if (points < 0) throw new IllegalArgumentException("negative earned passive points");
            PassivePointSource source;
            try {
                source = PassivePointSource.valueOf(sourceName);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("unknown passive point source: " + sourceName, e);
            }
            if (earned.put(source, points) != null) throw new IllegalArgumentException("duplicate passive point source: " + sourceName);
        }
        int spent = in.readInt();
        return PassivePointLedger.of(earned, spent);
    }

    private static void writeStringSet(DataOutputStream out, Set<String> values) throws IOException {
        List<String> sorted = values.stream().sorted().toList();
        out.writeInt(sorted.size());
        for (String value : sorted) writeString(out, value);
    }

    private static Set<String> readStringSet(DataInputStream in) throws IOException {
        int count = readCollectionSize(in);
        Set<String> values = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String value = readString(in);
            if (!values.add(value)) throw new IllegalArgumentException("duplicate string value: " + value);
        }
        return Set.copyOf(values);
    }

    private static void writeStringIntMap(DataOutputStream out, Map<String, Integer> values) throws IOException {
        List<String> keys = values.keySet().stream().sorted().toList();
        out.writeInt(keys.size());
        for (String key : keys) {
            writeString(out, key);
            out.writeInt(values.get(key));
        }
    }

    private static Map<String, Integer> readStringIntMap(DataInputStream in) throws IOException {
        int count = readCollectionSize(in);
        Map<String, Integer> values = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String key = readString(in);
            int value = in.readInt();
            if (value < 0) throw new IllegalArgumentException("negative integer map value");
            if (values.put(key, value) != null) throw new IllegalArgumentException("duplicate map key: " + key);
        }
        return Map.copyOf(values);
    }

    private static void writeStringSetMap(DataOutputStream out, Map<String, Set<String>> values) throws IOException {
        List<String> keys = values.keySet().stream().sorted().toList();
        out.writeInt(keys.size());
        for (String key : keys) {
            writeString(out, key);
            writeStringSet(out, values.get(key));
        }
    }

    private static Map<String, Set<String>> readStringSetMap(DataInputStream in) throws IOException {
        int count = readCollectionSize(in);
        Map<String, Set<String>> values = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String key = readString(in);
            Set<String> selected = readStringSet(in);
            if (values.put(key, selected) != null) throw new IllegalArgumentException("duplicate choice group: " + key);
        }
        return Map.copyOf(values);
    }

    private static void writeFinalTriads(DataOutputStream out, FinalTriadProgress triads) throws IOException {
        List<ProgressionDomain> domains = triads.allRanks().keySet().stream()
            .sorted(Comparator.comparing(Enum::name))
            .toList();
        out.writeInt(domains.size());
        for (ProgressionDomain domain : domains) {
            writeString(out, domain.name());
            List<Integer> ranks = triads.ranks(domain);
            for (int rank : ranks) out.writeInt(rank);
        }
    }

    private static FinalTriadProgress readFinalTriads(DataInputStream in) throws IOException {
        int count = readCollectionSize(in);
        EnumMap<ProgressionDomain, List<Integer>> ranks = new EnumMap<>(ProgressionDomain.class);
        for (int i = 0; i < count; i++) {
            String domainName = readString(in);
            ProgressionDomain domain;
            try {
                domain = ProgressionDomain.valueOf(domainName);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("unknown final triad domain: " + domainName, e);
            }
            List<Integer> values = List.of(in.readInt(), in.readInt(), in.readInt());
            if (ranks.put(domain, values) != null) throw new IllegalArgumentException("duplicate final triad domain: " + domainName);
        }
        return FinalTriadProgress.of(ranks);
    }

    private static void writeMasteryReceipts(
        DataOutputStream out,
        Map<String, MasteryAwardReceipt> receipts
    ) throws IOException {
        if (receipts.size() > MasteryState.RECENT_AWARD_LIMIT) {
            throw new IllegalArgumentException("too many recent mastery award receipts");
        }
        out.writeInt(receipts.size());
        for (Map.Entry<String, MasteryAwardReceipt> entry : receipts.entrySet()) {
            writeString(out, entry.getKey());
            writeString(out, entry.getValue().laneId());
            out.writeInt(entry.getValue().experience());
        }
    }

    private static Map<String, MasteryAwardReceipt> readMasteryReceipts(DataInputStream in) throws IOException {
        int count = readCollectionSize(in);
        if (count > MasteryState.RECENT_AWARD_LIMIT) {
            throw new IllegalArgumentException("too many recent mastery award receipts");
        }
        LinkedHashMap<String, MasteryAwardReceipt> receipts = new LinkedHashMap<>();
        for (int i = 0; i < count; i++) {
            String sourceId = readString(in);
            MasteryAwardReceipt receipt = new MasteryAwardReceipt(readString(in), in.readInt());
            if (receipts.put(sourceId, receipt) != null) {
                throw new IllegalArgumentException("duplicate mastery source id: " + sourceId);
            }
        }
        return receipts;
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("serialized ids must not be blank");
        byte[] bytes = value.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        if (bytes.length > MAX_STRING_BYTES) throw new IllegalArgumentException("serialized id too long");
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_STRING_BYTES) throw new IllegalArgumentException("invalid string length: " + length);
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) throw new IllegalArgumentException("truncated string payload");
        String value = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        if (value.isBlank()) throw new IllegalArgumentException("serialized ids must not be blank");
        return value;
    }

    private static int readCollectionSize(DataInputStream in) throws IOException {
        int count = in.readInt();
        if (count < 0 || count > MAX_COLLECTION_SIZE) throw new IllegalArgumentException("invalid collection size: " + count);
        return count;
    }
}
