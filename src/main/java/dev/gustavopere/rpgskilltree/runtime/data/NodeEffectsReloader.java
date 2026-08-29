package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.ReloadDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeEffectDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeNodeEffectRuntime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NodeEffectsReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeEffectsReloader.class);

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
        ReloadDiagnostics.run(LOGGER, "node_effects", resources, () -> {
            try {
                SkillTreeDataReloadTransaction.stageEffects(load(resources));
                SkillTreeDataReloadTransaction.commit();
            } catch (RuntimeException failure) {
                SkillTreeDataReloadTransaction.abort();
                throw failure;
            }
        });

        AttributeEffectDiagnostics.clear();
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            server.getPlayerList().getPlayers().forEach(player ->
                AttributeNodeEffectRuntime.refresh(player, PlayerProgressionRuntime.get(player))
            );
        }
    }

    private static List<SkillTreeDataReloadTransaction.EffectEntry> load(Map<ResourceLocation, JsonElement> resources) {
        List<SkillTreeDataReloadTransaction.EffectEntry> effects = new ArrayList<>();
        for (Map.Entry<ResourceLocation, JsonElement> resource : resources.entrySet()) {
            ResourceLocation source = resource.getKey();
            JsonObject root = object(source, "<root>", "root", resource.getValue());
            var attributes = root.getAsJsonArray("attributes");
            if (attributes == null) continue;
            for (JsonElement element : attributes) {
                JsonObject effect = object(source, "<unknown>", "effect", element);
                String effectId = string(source, "<unknown>", "effectId", required(effect, source, "<unknown>", "effectId"));
                String nodeId = string(source, effectId, "nodeId", required(effect, source, effectId, "nodeId"));
                String attributeId = string(source, effectId, "attributeId", required(effect, source, effectId, "attributeId"));
                ModifierOperation operation;
                try {
                    operation = ModifierOperation.valueOf(
                        string(source, effectId, "operation", required(effect, source, effectId, "operation"))
                    );
                } catch (SkillTreeDataValidationException failure) {
                    throw failure;
                } catch (RuntimeException failure) {
                    throw SkillTreeDataValidationException.wrap(source, effectId, "operation", failure);
                }
                double amountPerRank = number(source, effectId, "amountPerRank", required(effect, source, effectId, "amountPerRank"));
                if (!Double.isFinite(amountPerRank)) {
                    throw error(source, effectId, "amountPerRank", "must be finite");
                }

                NodeAttributeEffect parsed;
                try {
                    parsed = new NodeAttributeEffect(effectId, nodeId, attributeId, operation, amountPerRank);
                } catch (RuntimeException failure) {
                    throw SkillTreeDataValidationException.wrap(source, effectId, "effect", failure);
                }
                effects.add(new SkillTreeDataReloadTransaction.EffectEntry(source, parsed));
            }
        }
        return List.copyOf(effects);
    }

    private static JsonElement required(JsonObject object, ResourceLocation source, String subject, String field) {
        JsonElement value = object.get(field);
        if (value == null || value.isJsonNull()) throw error(source, subject, field, "field is required");
        return value;
    }

    private static JsonObject object(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            return value.getAsJsonObject();
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static String string(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            String result = value.getAsString();
            if (result.isBlank()) throw new IllegalArgumentException("must not be blank");
            return result;
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static double number(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            return value.getAsDouble();
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static SkillTreeDataValidationException error(
        ResourceLocation source,
        String subject,
        String field,
        String detail
    ) {
        return new SkillTreeDataValidationException(source, subject, field, detail);
    }
}
