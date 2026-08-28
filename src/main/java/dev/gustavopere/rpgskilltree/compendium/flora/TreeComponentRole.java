package dev.gustavopere.rpgskilltree.compendium.flora;

public enum TreeComponentRole {
    SAPLING("sapling"),
    PROPAGULE("propagule"),
    LOG("log"),
    WOOD("wood"),
    STRIPPED_LOG("stripped_log"),
    STRIPPED_WOOD("stripped_wood"),
    LEAVES("leaves"),
    FRUIT("fruit"),
    RESIN("resin"),
    DYNAMIC_TREE_FAMILY("dynamic_tree_family");

    private final String id;

    TreeComponentRole(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
