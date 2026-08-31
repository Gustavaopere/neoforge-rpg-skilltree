package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Registers custom feature types used by Volcanoes datapack worldgen entries. */
public final class VolcanoWorldgenRegistry {
    public static final ResourceLocation VOLCANO_SITES_ID =
            ResourceLocation.fromNamespaceAndPath(VolcanoesMod.MOD_ID, "volcano_sites");
    public static final ResourceLocation GEOTHERMAL_FEATURES_ID =
            ResourceLocation.fromNamespaceAndPath(VolcanoesMod.MOD_ID, "geothermal_features");

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, VolcanoesMod.MOD_ID);

    public static final DeferredHolder<Feature<?>, VolcanoWorldgenFeature> VOLCANO_SITES =
            FEATURES.register(
                    "volcano_sites",
                    () -> new VolcanoWorldgenFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, GeothermalWorldgenFeature> GEOTHERMAL_FEATURES =
            FEATURES.register(
                    "geothermal_features",
                    () -> new GeothermalWorldgenFeature(NoneFeatureConfiguration.CODEC));

    private VolcanoWorldgenRegistry() {
    }

    public static void register(IEventBus modBus) {
        FEATURES.register(modBus);
    }
}
