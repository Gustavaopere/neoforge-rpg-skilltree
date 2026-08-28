package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeEffectDiagnostics;
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

public final class NodeEffectsReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public NodeEffectsReloader() {
        super(GSON, "node_effects");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new NodeEffectsReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        List<NodeAttributeEffect> effects = new ArrayList<>();
        for (JsonElement resource : resources.values()) {
            JsonObject root = resource.getAsJsonObject();
            var attributes = root.getAsJsonArray("attributes");
            if (attributes == null) continue;
            attributes.forEach(element -> {
                JsonObject effect = element.getAsJsonObject();
                effects.add(new NodeAttributeEffect(
                    effect.get("effectId").getAsString(),
                    effect.get("nodeId").getAsString(),
                    effect.get("attributeId").getAsString(),
                    ModifierOperation.valueOf(effect.get("operation").getAsString()),
                    effect.get("amountPerRank").getAsDouble()
                ));
            });
        }
        NodeEffectCatalog.replace(effects);
        AttributeEffectDiagnostics.clear();
    }
}
