package dev.gustavopere.rpgskilltree.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public record MasteryState(
    Map<String, Integer> experience,
    Map<String, MasteryAwardReceipt> creditedAwards
) {
    /** Technical replay window; this is not a gameplay mastery cap. */
    public static final int RECENT_AWARD_LIMIT = 1_024;

    public MasteryState {
        Objects.requireNonNull(experience, "experience");
        Objects.requireNonNull(creditedAwards, "creditedAwards");

        Map<String, Integer> experienceCopy = new HashMap<>();
        for (Map.Entry<String, Integer> entry : experience.entrySet()) {
            String lane = Objects.requireNonNull(entry.getKey(), "mastery lane");
            Integer amount = Objects.requireNonNull(entry.getValue(), "mastery XP");
            if (lane.isBlank()) throw new IllegalArgumentException("mastery lane must not be blank");
            if (amount < 0) throw new IllegalArgumentException("mastery XP must be >= 0");
            experienceCopy.put(lane, amount);
        }
        experience = Map.copyOf(experienceCopy);

        if (creditedAwards.size() > RECENT_AWARD_LIMIT) {
            throw new IllegalArgumentException("too many recent mastery award receipts");
        }
        LinkedHashMap<String, MasteryAwardReceipt> receiptCopy = new LinkedHashMap<>();
        for (Map.Entry<String, MasteryAwardReceipt> entry : creditedAwards.entrySet()) {
            String sourceId = Objects.requireNonNull(entry.getKey(), "mastery source id");
            MasteryAwardReceipt receipt = Objects.requireNonNull(entry.getValue(), "mastery receipt");
            if (sourceId.isBlank()) throw new IllegalArgumentException("mastery source id must not be blank");
            receiptCopy.put(sourceId, receipt);
        }
        creditedAwards = Collections.unmodifiableMap(receiptCopy);
    }

    public MasteryState(Map<String, Integer> experience) {
        this(experience, Map.of());
    }

    public static MasteryState empty() {
        return new MasteryState(Map.of(), Map.of());
    }

    public static MasteryState of(Map<String, Integer> experience) {
        return new MasteryState(experience, Map.of());
    }

    public static MasteryState of(
        Map<String, Integer> experience,
        Map<String, MasteryAwardReceipt> creditedAwards
    ) {
        return new MasteryState(experience, creditedAwards);
    }

    public int experience(String lane) {
        return experience.getOrDefault(lane, 0);
    }

    /** Legacy/untracked mutation path retained only for compatibility services. */
    public MasteryState award(String lane, int amount) {
        if (lane == null || lane.isBlank()) throw new IllegalArgumentException("mastery lane must not be blank");
        if (amount <= 0) throw new IllegalArgumentException("mastery award must be positive");
        Map<String, Integer> next = new HashMap<>(experience);
        next.merge(lane, amount, Math::addExact);
        return new MasteryState(next, creditedAwards);
    }

    /** Replay-safe mastery mutation keyed by the adapter-provided stable source id. */
    public MasteryState award(MasteryAward award) {
        Objects.requireNonNull(award, "award");
        MasteryAwardReceipt incoming = MasteryAwardReceipt.from(award);
        MasteryAwardReceipt existing = creditedAwards.get(award.sourceId());
        if (existing != null) {
            if (existing.equals(incoming)) return this;
            throw new IllegalArgumentException(
                "mastery source id already used with different payload: " + award.sourceId()
            );
        }

        Map<String, Integer> nextExperience = new HashMap<>(experience);
        nextExperience.merge(award.laneId(), award.experience(), Math::addExact);

        LinkedHashMap<String, MasteryAwardReceipt> nextReceipts = new LinkedHashMap<>(creditedAwards);
        nextReceipts.put(award.sourceId(), incoming);
        if (nextReceipts.size() > RECENT_AWARD_LIMIT) {
            String oldest = nextReceipts.keySet().iterator().next();
            nextReceipts.remove(oldest);
        }
        return new MasteryState(nextExperience, nextReceipts);
    }
}
