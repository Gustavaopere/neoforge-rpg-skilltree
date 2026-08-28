package dev.gustavopere.rpgskilltree.compendium.entity;

public enum EntityBaseAttribute {
    MAX_HEALTH(EntityFactKeys.MAX_HEALTH),
    ARMOR(EntityFactKeys.ARMOR),
    ARMOR_TOUGHNESS(EntityFactKeys.ARMOR_TOUGHNESS),
    ATTACK_DAMAGE(EntityFactKeys.ATTACK_DAMAGE),
    MOVEMENT_SPEED(EntityFactKeys.MOVEMENT_SPEED),
    FLYING_SPEED(EntityFactKeys.FLYING_SPEED),
    KNOCKBACK_RESISTANCE(EntityFactKeys.KNOCKBACK_RESISTANCE),
    ATTACK_KNOCKBACK(EntityFactKeys.ATTACK_KNOCKBACK),
    FOLLOW_RANGE(EntityFactKeys.FOLLOW_RANGE),
    JUMP_STRENGTH(EntityFactKeys.JUMP_STRENGTH);

    private final String factKey;

    EntityBaseAttribute(String factKey) {
        this.factKey = factKey;
    }

    public String factKey() {
        return factKey;
    }
}
