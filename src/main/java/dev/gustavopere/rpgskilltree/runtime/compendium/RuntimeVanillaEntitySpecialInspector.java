package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.entity.EntityVariantSnapshot;
import dev.gustavopere.rpgskilltree.compendium.provider.entity.VanillaEntitySpecialInspectors;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.WanderingTrader;

/** Narrow vanilla special-case facts layered on top of the generic entity inspection. */
public final class RuntimeVanillaEntitySpecialInspector {
    private RuntimeVanillaEntitySpecialInspector() {}

    public static Optional<EntityVariantSnapshot> inspect(Entity entity) {
        if (entity instanceof Horse horse) {
            return emit(
                "horse",
                Map.of(
                    "variant", horse.getVariant().name().toLowerCase(Locale.ROOT),
                    "markings", horse.getMarkings().name().toLowerCase(Locale.ROOT)
                ),
                Map.of(),
                Map.of()
            );
        }
        if (entity instanceof Panda panda) {
            return emit(
                "panda",
                Map.of(
                    "main_gene", panda.getMainGene().name().toLowerCase(Locale.ROOT),
                    "hidden_gene", panda.getHiddenGene().name().toLowerCase(Locale.ROOT)
                ),
                Map.of(),
                Map.of()
            );
        }
        if (entity instanceof Villager villager) {
            VillagerData data = villager.getVillagerData();
            LinkedHashMap<String, String> text = new LinkedHashMap<>();
            ResourceLocation typeId = BuiltInRegistries.VILLAGER_TYPE.getKey(data.getType());
            ResourceLocation professionId = BuiltInRegistries.VILLAGER_PROFESSION.getKey(data.getProfession());
            if (typeId != null) text.put("type", typeId.toString());
            if (professionId != null) text.put("profession", professionId.toString());
            return emit("villager", text, Map.of("level", (long) data.getLevel()), Map.of());
        }
        if (entity instanceof Bee bee) {
            LinkedHashMap<String, String> text = new LinkedHashMap<>();
            if (bee.getHivePos() != null) text.put("hive_pos", bee.getHivePos().toShortString());
            return emit(
                "bee",
                text,
                Map.of(),
                Map.of(
                    "has_hive", bee.hasHive(),
                    "has_nectar", bee.hasNectar(),
                    "has_stung", bee.hasStung()
                )
            );
        }
        if (entity instanceof Dolphin dolphin) {
            return emit(
                "dolphin",
                Map.of(),
                Map.of("moistness", (long) dolphin.getMoistnessLevel()),
                Map.of("got_fish", dolphin.gotFish())
            );
        }
        if (entity instanceof Goat goat) {
            return emit(
                "goat",
                Map.of(),
                Map.of(),
                Map.of(
                    "screaming", goat.isScreamingGoat(),
                    "left_horn", goat.hasLeftHorn(),
                    "right_horn", goat.hasRightHorn()
                )
            );
        }
        if (entity instanceof WanderingTrader trader) {
            return emit(
                "wandering_trader",
                Map.of(),
                Map.of("despawn_delay", (long) trader.getDespawnDelay()),
                Map.of()
            );
        }
        return Optional.empty();
    }

    private static Optional<EntityVariantSnapshot> emit(
        String family,
        Map<String, String> textFacts,
        Map<String, Long> numericFacts,
        Map<String, Boolean> booleanFacts
    ) {
        return VanillaEntitySpecialInspectors.inspect(family, textFacts, numericFacts, booleanFacts);
    }
}
