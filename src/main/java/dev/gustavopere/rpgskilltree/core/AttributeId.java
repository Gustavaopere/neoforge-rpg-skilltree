package dev.gustavopere.rpgskilltree.core;

/** Canonical identities for the six uncapped RPG attributes. */
public enum AttributeId {
    STRENGTH("strength"),
    CONSTITUTION("constitution"),
    AGILITY("agility"),
    INTELLIGENCE("intelligence"),
    DETERMINATION("determination"),
    CHARISMA("charisma");

    private final String serializedId;

    AttributeId(String serializedId) {
        this.serializedId = serializedId;
    }

    public String serializedId() {
        return serializedId;
    }
}
