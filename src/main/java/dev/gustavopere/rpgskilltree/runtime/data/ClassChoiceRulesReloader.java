package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ClassChoiceDefinition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

public final class ClassChoiceRulesReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public ClassChoiceRulesReloader() {
        super(GSON, "class_choices");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ClassChoiceRulesReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        List<ClassChoiceDefinition> definitions = new ArrayList<>();
        for (JsonElement element : resources.values()) {
            JsonObject root = element.getAsJsonObject();
            definitions.add(new ClassChoiceDefinition(
                root.get("choice_id").getAsString(),
                root.get("required_class_id").getAsString(),
                root.get("group_id").getAsString(),
                root.get("default_group_capacity").getAsInt()
            ));
        }
        ClassChoiceCatalog.replace(definitions);
    }
}
