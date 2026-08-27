package dev.gustavopere.rpgskilltree.core;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Immutable uncapped rank state for the six canonical RPG attributes. */
public final class AttributeRanks {
    private static final AttributeRanks EMPTY = new AttributeRanks(new EnumMap<>(AttributeId.class));

    private final Map<AttributeId, Long> ranks;

    private AttributeRanks(EnumMap<AttributeId, Long> ranks) {
        this.ranks = Collections.unmodifiableMap(ranks);
    }

    public static AttributeRanks empty() {
        return EMPTY;
    }

    public static AttributeRanks of(Map<AttributeId, Long> ranks) {
        Objects.requireNonNull(ranks, "ranks");
        if (ranks.isEmpty()) return EMPTY;

        EnumMap<AttributeId, Long> copy = new EnumMap<>(AttributeId.class);
        for (Map.Entry<AttributeId, Long> entry : ranks.entrySet()) {
            AttributeId attribute = Objects.requireNonNull(entry.getKey(), "attribute");
            Long boxedRank = Objects.requireNonNull(entry.getValue(), "rank");
            long rank = boxedRank;
            if (rank < 0L) {
                throw new IllegalArgumentException("attribute rank must be non-negative");
            }
            if (rank != 0L) {
                copy.put(attribute, rank);
            }
        }
        return copy.isEmpty() ? EMPTY : new AttributeRanks(copy);
    }

    public long rank(AttributeId attribute) {
        Objects.requireNonNull(attribute, "attribute");
        return ranks.getOrDefault(attribute, 0L);
    }

    public AttributeRanks increase(AttributeId attribute, long amount) {
        Objects.requireNonNull(attribute, "attribute");
        if (amount < 0L) {
            throw new IllegalArgumentException("increase amount must be non-negative");
        }
        if (amount == 0L) return this;

        long next = Math.addExact(rank(attribute), amount);
        EnumMap<AttributeId, Long> copy = new EnumMap<>(AttributeId.class);
        copy.putAll(ranks);
        copy.put(attribute, next);
        return new AttributeRanks(copy);
    }

    public AttributeRanks decrease(AttributeId attribute, long amount) {
        Objects.requireNonNull(attribute, "attribute");
        if (amount < 0L) {
            throw new IllegalArgumentException("decrease amount must be non-negative");
        }
        if (amount == 0L) return this;

        long current = rank(attribute);
        if (amount > current) {
            throw new IllegalStateException("cannot refund more ranks than allocated");
        }

        long next = current - amount;
        EnumMap<AttributeId, Long> copy = new EnumMap<>(AttributeId.class);
        copy.putAll(ranks);
        if (next == 0L) {
            copy.remove(attribute);
        } else {
            copy.put(attribute, next);
        }
        return copy.isEmpty() ? EMPTY : new AttributeRanks(copy);
    }

    public Map<AttributeId, Long> asMap() {
        return ranks;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof AttributeRanks that && ranks.equals(that.ranks);
    }

    @Override
    public int hashCode() {
        return ranks.hashCode();
    }

    @Override
    public String toString() {
        return "AttributeRanks" + ranks;
    }
}
