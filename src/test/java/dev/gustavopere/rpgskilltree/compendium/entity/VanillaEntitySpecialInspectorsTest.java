package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.provider.entity.VanillaEntitySpecialInspectors;
import java.util.Map;

public final class VanillaEntitySpecialInspectorsTest {
    public static void main(String[] args) {
        supportsKnownFamilies();
        unsupportedFamilyFallsBackCleanly();
        System.out.println("VanillaEntitySpecialInspectorsTest: PASS");
    }

    private static void supportsKnownFamilies() {
        EntityVariantSnapshot horse = VanillaEntitySpecialInspectors.inspect(
            "horse",
            Map.of("variant", "black", "markings", "white_field"),
            Map.of(),
            Map.of("tame", true)
        ).orElseThrow();
        check(horse.family().equals("horse"), "horse family");
        check(horse.textFacts().get("variant").equals("black"), "horse variant");

        EntityVariantSnapshot panda = VanillaEntitySpecialInspectors.inspect(
            "panda",
            Map.of("main_gene", "lazy", "hidden_gene", "normal"),
            Map.of(),
            Map.of()
        ).orElseThrow();
        check(panda.textFacts().containsKey("main_gene"), "panda gene");

        EntityVariantSnapshot villager = VanillaEntitySpecialInspectors.inspect(
            "villager",
            Map.of("type", "minecraft:plains", "profession", "minecraft:librarian"),
            Map.of("level", 3L),
            Map.of()
        ).orElseThrow();
        check(villager.numericFacts().get("level") == 3L, "villager level");
    }

    private static void unsupportedFamilyFallsBackCleanly() {
        check(VanillaEntitySpecialInspectors.inspect(
            "unsupported_modded_entity",
            Map.of("anything", "value"),
            Map.of(),
            Map.of()
        ).isEmpty(), "unsupported family must not replace generic inspection");
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }
}
