package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ClassUnlockDefinition;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import dev.gustavopere.rpgskilltree.core.ProviderClassAvailabilityRegistry;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.ReloadDiagnostics;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClassRulesReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRulesReloader.class);

    public ClassRulesReloader() {
        super(GSON, "classes");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ClassRulesReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        ReloadDiagnostics.run(LOGGER, "classes", resources, () -> load(resources));
    }

    private static void load(Map<ResourceLocation, JsonElement> resources) {
        List<ClassUnlockDefinition> definitions = new ArrayList<>();
        Map<String, Boolean> providerAvailability = new HashMap<>();
        for (JsonElement element : resources.values()) {
            JsonObject root = element.getAsJsonObject();
            String classId = root.get("class_id").getAsString();

            Set<String> requiredProviderMods = new HashSet<>();
            if (root.has("required_provider_mods")) {
                root.getAsJsonArray("required_provider_mods")
                    .forEach(value -> requiredProviderMods.add(value.getAsString()));
            }
            boolean providersAvailable = ProviderAvailabilityPolicy.allAvailable(
                requiredProviderMods,
                modId -> ModList.get().isLoaded(modId)
            );
            providerAvailability.put(classId, providersAvailable);
            if (!providersAvailable) {
                LOGGER.debug("Class {} unavailable because a required provider mod is not loaded: {}",
                    classId, requiredProviderMods);
            }

            EnumSet<ProgressionDomain> domains = EnumSet.noneOf(ProgressionDomain.class);
            root.getAsJsonArray("required_completed_domains")
                .forEach(value -> domains.add(ProgressionDomain.valueOf(value.getAsString())));

            Map<String, Integer> mastery = new HashMap<>();
            if (root.has("minimum_mastery_experience")) {
                for (Map.Entry<String, JsonElement> entry : root.getAsJsonObject("minimum_mastery_experience").entrySet()) {
                    mastery.put(entry.getKey(), entry.getValue().getAsInt());
                }
            }

            Set<String> requiredNodes = new HashSet<>();
            if (root.has("required_nodes")) {
                root.getAsJsonArray("required_nodes").forEach(value -> requiredNodes.add(value.getAsString()));
            }

            definitions.add(new ClassUnlockDefinition(
                classId,
                domains,
                root.get("adjacent_confluence").getAsBoolean(),
                root.get("non_adjacent_bridge_cost").getAsInt(),
                mastery,
                requiredNodes
            ));
        }
        ClassRuleCatalog.replace(definitions);
        ProviderClassAvailabilityRegistry.replace(providerAvailability);
    }
}
