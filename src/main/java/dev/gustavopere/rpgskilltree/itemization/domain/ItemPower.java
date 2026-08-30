package dev.gustavopere.rpgskilltree.itemization.domain;

/** Non-negative numeric generation scale, independent from {@link ItemRank}. */
public record ItemPower(int value) {
    public ItemPower {
        if (value < 0) {
            throw new IllegalArgumentException("item power must be >= 0");
        }
    }

    public static ItemPower of(int value) {
        return new ItemPower(value);
    }
}
