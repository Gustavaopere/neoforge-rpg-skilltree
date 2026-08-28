package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.gustavopere.rpgskilltree.core.CanonicalProviderBinding;
import dev.gustavopere.rpgskilltree.core.CanonicalProviderBindingCatalog;
import dev.gustavopere.rpgskilltree.core.CanonicalStatKey;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

/** Loads additive canonical-stat to provider-target bindings from datapacks. */
public final class CanonicalProviderBindingsReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Set<String> ROOT_FIELDS = Set.of("canonical_stat", "bindings");
    private static final Set<String> BINDING_FIELDS = Set.of("binding_id", "provider_target");

    public CanonicalProviderBindingsReloader() {
        super(GSON, "canonical_provider_bindings");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new CanonicalProviderBindingsReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        if (resources.isEmpty()) {
            CanonicalProviderBindingDataCatalog.clear();
            return;
        }

        ArrayList<CanonicalProviderBinding> bindings = new ArrayList<>();
        resources.entrySet().stream()
            .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
            .forEach(entry -> parse(entry.getKey(), entry.getValue(), bindings));

        CanonicalProviderBindingCatalog catalog = CanonicalProviderBindingCatalog.of(bindings);
        CanonicalProviderBindingDataCatalog.install(catalog);
    }

    private static void parse(
        ResourceLocation resourceId,
        JsonElement element,
        ArrayList<CanonicalProviderBinding> output
    ) {
        if (!element.isJsonObject()) {
            throw invalid(resourceId, "root must be a JSON object");
        }
        JsonObject root = element.getAsJsonObject();
        rejectUnknown(root, ROOT_FIELDS, resourceId, "root");

        CanonicalStatKey canonicalStat;
        try {
            canonicalStat = CanonicalStatKey.of(requiredString(root, "canonical_stat", resourceId));
        } catch (IllegalArgumentException invalidId) {
            throw invalid(resourceId, "canonical_stat is invalid", invalidId);
        }

        JsonArray rawBindings = requiredArray(root, "bindings", resourceId);
        if (rawBindings.isEmpty()) {
            throw invalid(resourceId, "bindings must not be empty");
        }
        for (int index = 0; index < rawBindings.size(); index++) {
            JsonElement raw = rawBindings.get(index);
            if (!raw.isJsonObject()) {
                throw invalid(resourceId, "bindings[" + index + "] must be an object");
            }
            JsonObject binding = raw.getAsJsonObject();
            rejectUnknown(binding, BINDING_FIELDS, resourceId, "bindings[" + index + "]");
            try {
                output.add(CanonicalProviderBinding.of(
                    requiredString(binding, "binding_id", resourceId),
                    canonicalStat,
                    requiredString(binding, "provider_target", resourceId)
                ));
            } catch (IllegalArgumentException invalidBinding) {
                throw invalid(resourceId, "bindings[" + index + "] is invalid", invalidBinding);
            }
        }
    }

    private static void rejectUnknown(
        JsonObject object,
        Set<String> allowed,
        ResourceLocation resourceId,
        String context
    ) {
        TreeSet<String> unknown = new TreeSet<>(object.keySet());
        unknown.removeAll(allowed);
        if (!unknown.isEmpty()) {
            throw invalid(resourceId, context + " contains unknown fields " + unknown);
        }
    }

    private static JsonElement required(JsonObject object, String field, ResourceLocation resourceId) {
        if (!object.has(field)) {
            throw invalid(resourceId, "missing required field " + field);
        }
        JsonElement value = object.get(field);
        if (value == null || value.isJsonNull()) {
            throw invalid(resourceId, field + " must not be null");
        }
        return value;
    }

    private static String requiredString(JsonObject object, String field, ResourceLocation resourceId) {
        JsonElement value = required(object, field, resourceId);
        if (!value.isJsonPrimitive()) {
            throw invalid(resourceId, field + " must be a string");
        }
        JsonPrimitive primitive = value.getAsJsonPrimitive();
        if (!primitive.isString()) {
            throw invalid(resourceId, field + " must be a string");
        }
        String result = primitive.getAsString();
        if (result.isBlank()) {
            throw invalid(resourceId, field + " must not be blank");
        }
        return result;
    }

    private static JsonArray requiredArray(JsonObject object, String field, ResourceLocation resourceId) {
        JsonElement value = required(object, field, resourceId);
        if (!value.isJsonArray()) {
            throw invalid(resourceId, field + " must be an array");
        }
        return value.getAsJsonArray();
    }

    private static IllegalArgumentException invalid(ResourceLocation resourceId, String message) {
        return new IllegalArgumentException(
            "invalid canonical provider bindings " + resourceId + ": " + message
        );
    }

    private static IllegalArgumentException invalid(
        ResourceLocation resourceId,
        String message,
        Throwable cause
    ) {
        return new IllegalArgumentException(
            "invalid canonical provider bindings " + resourceId + ": " + message,
            cause
        );
    }
}
