package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.gustavopere.rpgskilltree.core.CanonicalProviderBinding;
import dev.gustavopere.rpgskilltree.core.CanonicalProviderBindingCatalog;
import dev.gustavopere.rpgskilltree.core.CanonicalStatKey;
import java.util.ArrayList;
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

/** Loads provider-binding definitions without choosing provider precedence. */
public final class CanonicalProviderBindingReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Set<String> ROOT_FIELDS = Set.of(
        "binding_id",
        "canonical_stat",
        "provider_target"
    );

    public CanonicalProviderBindingReloader() {
        super(GSON, "canonical_provider_bindings");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new CanonicalProviderBindingReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        if (resources.isEmpty()) {
            CanonicalProviderBindingRuntimeCatalog.clear();
            return;
        }

        ArrayList<CanonicalProviderBinding> bindings = new ArrayList<>(resources.size());
        resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> bindings.add(parse(entry.getKey(), entry.getValue())));

        CanonicalProviderBindingCatalog compiled = CanonicalProviderBindingCatalog.of(bindings);
        CanonicalProviderBindingRuntimeCatalog.install(compiled);
    }

    private static CanonicalProviderBinding parse(ResourceLocation resourceId, JsonElement element) {
        if (!element.isJsonObject()) {
            throw invalid(resourceId, "root must be a JSON object");
        }
        JsonObject root = element.getAsJsonObject();
        rejectUnknown(root, ROOT_FIELDS, resourceId);

        return CanonicalProviderBinding.of(
            requiredString(root, "binding_id", resourceId),
            CanonicalStatKey.of(requiredString(root, "canonical_stat", resourceId)),
            requiredString(root, "provider_target", resourceId)
        );
    }

    private static void rejectUnknown(
        JsonObject object,
        Set<String> allowed,
        ResourceLocation resourceId
    ) {
        TreeSet<String> unknown = new TreeSet<>(object.keySet());
        unknown.removeAll(allowed);
        if (!unknown.isEmpty()) {
            throw invalid(resourceId, "root contains unknown fields " + unknown);
        }
    }

    private static String requiredString(JsonObject object, String field, ResourceLocation resourceId) {
        if (!object.has(field)) {
            throw invalid(resourceId, "missing required field " + field);
        }
        JsonElement value = object.get(field);
        if (value == null || value.isJsonNull() || !value.isJsonPrimitive()) {
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

    private static IllegalArgumentException invalid(ResourceLocation resourceId, String message) {
        return new IllegalArgumentException(
            "invalid canonical provider binding " + resourceId + ": " + message
        );
    }
}
