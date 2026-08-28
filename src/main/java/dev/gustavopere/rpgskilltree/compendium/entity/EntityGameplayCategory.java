package dev.gustavopere.rpgskilltree.compendium.entity;

public enum EntityGameplayCategory {
    PASSIVO("passivo"),
    NEUTRO("neutro"),
    HOSTIL("hostil"),
    BOSS("boss"),
    NPC("npc"),
    ANIMAL_DOMESTICAVEL("animal_domesticavel"),
    ANIMAL_CRIAVEL("animal_criavel"),
    AQUATICO("aquatico"),
    VOADOR("voador"),
    ARTROPODE("artropode"),
    MORTO_VIVO("morto_vivo"),
    FANTASTICO("fantastico"),
    CONSTRUTO("construto"),
    OUTRO("outro");

    private final String id;

    EntityGameplayCategory(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
