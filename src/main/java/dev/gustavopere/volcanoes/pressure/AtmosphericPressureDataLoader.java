package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Strict datapack parser for atmospheric pressure profiles. */
public final class AtmosphericPressureDataLoader {
    private static final Set<String> PROFILE_FIELDS = Set.of(
            "dimensions",
            "baseline_y",
            "baseline_atm",
            "control_points");
    private static final Set<String> CONTROL_POINT_FIELDS = Set.of("y", "pressure_atm");

    private AtmosphericPressureDataLoader() {
    }

    public static AtmosphericPressureRegistry load(Map<ResourceLocation, JsonElement> definitions) {
        Objects.requireNonNull(definitions, "definitions");
        Map<String, AtmosphericPressureProfile> byDimension = new LinkedHashMap<>();

        definitions.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
                .forEach(entry -> addDefinition(byDimension, entry.getKey(), entry.getValue()));

        return new AtmosphericPressureRegistry(byDimension);
    }

    private static void addDefinition(
            Map<String, AtmosphericPressureProfile> byDimension,
            ResourceLocation definitionId,
            JsonElement element
    ) {
        if (element == null || !element.isJsonObject()) {
            throw invalid(definitionId, "definition must be a JSON object");
        }

        JsonObject json = element.getAsJsonObject();
        rejectUnknownFields(json, PROFILE_FIELDS, definitionId, "profile");
        double baselineY = requiredDouble(json, "baseline_y", definitionId);
        double baselineAtm = requiredDouble(json, "baseline_atm", definitionId);
        PressureCurve curve = new PressureCurve(controlPoints(json, definitionId));
        AtmosphericPressureProfile profile =
                new AtmosphericPressureProfile(definitionId, baselineY, baselineAtm, curve);

        for (String dimension : dimensions(json, definitionId)) {
            AtmosphericPressureProfile previous = byDimension.putIfAbsent(dimension, profile);
            if (previous != null) {
                throw invalid(definitionId, "dimension '" + dimension + "' is already bound by " + previous.id());
            }
        }
    }

    private static List<String> dimensions(JsonObject json, ResourceLocation definitionId) {
        JsonElement element = json.get("dimensions");
        if (element == null || !element.isJsonArray()) {
            throw invalid(definitionId, "field 'dimensions' must be a non-empty array");
        }
        JsonArray array = element.getAsJsonArray();
        if (array.isEmpty()) {
            throw invalid(definitionId, "field 'dimensions' must be a non-empty array");
        }

        List<String> dimensions = new ArrayList<>(array.size());
        for (JsonElement item : array) {
            if (!(item instanceof JsonPrimitive primitive) || !primitive.isString()) {
                throw invalid(definitionId, "field 'dimensions' must contain resource-location strings");
            }
            try {
                dimensions.add(ResourceLocation.parse(primitive.getAsString()).toString());
            } catch (RuntimeException exception) {
                throw invalid(definitionId, "invalid dimension resource location", exception);
            }
        }
        return List.copyOf(dimensions);
    }

    private static List<PressureControlPoint> controlPoints(JsonObject json, ResourceLocation definitionId) {
        JsonElement element = json.get("control_points");
        if (element == null || !element.isJsonArray()) {
            throw invalid(definitionId, "field 'control_points' must be a non-empty array");
        }
        JsonArray array = element.getAsJsonArray();
        if (array.isEmpty()) {
            throw invalid(definitionId, "field 'control_points' must be a non-empty array");
        }

        List<PressureControlPoint> points = new ArrayList<>(array.size());
        for (JsonElement item : array) {
            if (!item.isJsonObject()) {
                throw invalid(definitionId, "field 'control_points' must contain objects");
            }
            JsonObject point = item.getAsJsonObject();
            rejectUnknownFields(point, CONTROL_POINT_FIELDS, definitionId, "control point");
            points.add(new PressureControlPoint(
                    requiredDouble(point, "y", definitionId),
                    requiredDouble(point, "pressure_atm", definitionId)));
        }
        return List.copyOf(points);
    }

    private static void rejectUnknownFields(
            JsonObject object,
            Set<String> allowed,
            ResourceLocation definitionId,
            String context
    ) {
        for (String field : object.keySet()) {
            if (!allowed.contains(field)) {
                throw invalid(definitionId, "unknown " + context + " field '" + field + "'");
            }
        }
    }

    private static double requiredDouble(JsonObject json, String field, ResourceLocation definitionId) {
        JsonElement element = json.get(field);
        if (!(element instanceof JsonPrimitive primitive) || !primitive.isNumber()) {
            throw invalid(definitionId, "missing or invalid numeric field '" + field + "'");
        }
        try {
            double value = primitive.getAsDouble();
            if (!Double.isFinite(value)) {
                throw invalid(definitionId, "field '" + field + "' must be finite");
            }
            return value;
        } catch (RuntimeException exception) {
            if (exception instanceof IllegalArgumentException illegalArgumentException
                    && illegalArgumentException.getMessage() != null
                    && illegalArgumentException.getMessage().startsWith("Invalid pressure profile")) {
                throw illegalArgumentException;
            }
            throw invalid(definitionId, "invalid numeric field '" + field + "'", exception);
        }
    }

    private static IllegalArgumentException invalid(ResourceLocation id, String message) {
        return new IllegalArgumentException("Invalid pressure profile " + id + ": " + message);
    }

    private static IllegalArgumentException invalid(ResourceLocation id, String message, Throwable cause) {
        return new IllegalArgumentException("Invalid pressure profile " + id + ": " + message, cause);
    }
}
