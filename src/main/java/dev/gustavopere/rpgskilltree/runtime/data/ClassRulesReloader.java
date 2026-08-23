package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ClassUnlockDefinition;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

public final class ClassRulesReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

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
        List<ClassUnlockDefinition> definitions = new ArrayList<>();
        for (JsonElement element : resources.values()) {
            JsonObject root = element.getAsJsonObject();
            String classId = root.get("class_id").getAsString();
            EnumSet<ProgressionDomain> domains = EnumSet.noneOf(ProgressionDomain.class);
            root.getAsJsonArray("required_completed_domains")
                .forEach(value -> domains.add(ProgressionDomain.valueOf(value.getAsString())));
            definitions.add(new ClassUnlockDefinition(
                classId,
                domains,
                root.get("adjacent_confluence").getAsBoolean(),
                root.get("non_adjacent_bridge_cost").getAsInt()
            ));
        }
        ClassRuleCatalog.replace(definitions);
    }
}
