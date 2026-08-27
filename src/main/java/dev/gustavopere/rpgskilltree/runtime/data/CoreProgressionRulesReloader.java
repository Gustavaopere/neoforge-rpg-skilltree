package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.gustavopere.rpgskilltree.core.LevelCurveBand;
import dev.gustavopere.rpgskilltree.core.MainPerkBudget;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import java.math.BigDecimal;
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

/** Loads the single authoritative uncapped Core progression rules snapshot. */
public final class CoreProgressionRulesReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Set<String> ROOT_FIELDS = Set.of(
        "rules_version",
        "rules_id",
        "main_perk_budget",
        "level_curve"
    );
    private static final Set<String> BAND_FIELDS = Set.of(
        "start_level",
        "base_xp",
        "growth_per_level"
    );

    public CoreProgressionRulesReloader() {
        super(GSON, "core_progression_rules");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new CoreProgressionRulesReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        if (resources.isEmpty()) {
            CoreProgressionRulesCatalog.clear();
            return;
        }
        if (resources.size() != 1) {
            throw new IllegalArgumentException(
                "expected exactly one Core progression rules resource, found " + resources.size()
            );
        }

        Map.Entry<ResourceLocation, JsonElement> entry = resources.entrySet().iterator().next();
        ProgressionRulesSnapshot snapshot = parse(entry.getKey(), entry.getValue());
        CoreProgressionRulesCatalog.install(snapshot);
    }

    private static ProgressionRulesSnapshot parse(ResourceLocation resourceId, JsonElement element) {
        if (!element.isJsonObject()) {
            throw invalid(resourceId, "root must be a JSON object");
        }
        JsonObject root = element.getAsJsonObject();
        rejectUnknown(root, ROOT_FIELDS, resourceId, "root");

        long version = exactLong(root, "rules_version", resourceId);
        String rulesId = requiredString(root, "rules_id", resourceId);
        long mainPerkBudget = exactLong(root, "main_perk_budget", resourceId);
        JsonArray curve = requiredArray(root, "level_curve", resourceId);
        if (curve.isEmpty()) {
            throw invalid(resourceId, "level_curve must not be empty");
        }

        ArrayList<LevelCurveBand> bands = new ArrayList<>(curve.size());
        for (int index = 0; index < curve.size(); index++) {
            JsonElement rawBand = curve.get(index);
            if (!rawBand.isJsonObject()) {
                throw invalid(resourceId, "level_curve[" + index + "] must be an object");
            }
            JsonObject band = rawBand.getAsJsonObject();
            rejectUnknown(band, BAND_FIELDS, resourceId, "level_curve[" + index + "]");
            bands.add(new LevelCurveBand(
                exactLong(band, "start_level", resourceId),
                exactLong(band, "base_xp", resourceId),
                exactLong(band, "growth_per_level", resourceId)
            ));
        }

        return new ProgressionRulesSnapshot(
            version,
            rulesId,
            bands,
            new MainPerkBudget(mainPerkBudget)
        );
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

    private static long exactLong(JsonObject object, String field, ResourceLocation resourceId) {
        JsonElement value = required(object, field, resourceId);
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
            throw invalid(resourceId, field + " must be an integer number");
        }
        try {
            return new BigDecimal(value.getAsJsonPrimitive().getAsString()).longValueExact();
        } catch (NumberFormatException | ArithmeticException invalidNumber) {
            throw new IllegalArgumentException(
                "invalid Core progression rules " + resourceId + ": " + field
                    + " must be an exact signed 64-bit integer",
                invalidNumber
            );
        }
    }

    private static IllegalArgumentException invalid(ResourceLocation resourceId, String message) {
        return new IllegalArgumentException(
            "invalid Core progression rules " + resourceId + ": " + message
        );
    }
}
