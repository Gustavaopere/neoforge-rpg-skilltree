package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Objects;
import java.util.Set;

/** Strict parser for the datapack-driven pressure exposure thresholds. */
public final class PressureExposureConfigDataLoader {
    private static final Set<String> FIELDS = Set.of(
            "grace_ticks",
            "discomfort_overpressure_atm",
            "impairment_overpressure_atm",
            "barotrauma_overpressure_atm",
            "impaired_movement_multiplier",
            "neurological_penalty",
            "barotrauma_damage_per_update");

    private PressureExposureConfigDataLoader() {
    }

    public static PressureExposureConfig parse(JsonElement root) {
        Objects.requireNonNull(root, "root");
        if (!root.isJsonObject()) {
            throw new IllegalArgumentException("pressure exposure config must be a JSON object");
        }

        JsonObject object = root.getAsJsonObject();
        for (String key : object.keySet()) {
            if (!FIELDS.contains(key)) {
                throw new IllegalArgumentException("unknown pressure exposure field: " + key);
            }
        }
        for (String field : FIELDS) {
            if (!object.has(field)) {
                throw new IllegalArgumentException("missing pressure exposure field: " + field);
            }
        }

        return new PressureExposureConfig(
                requireNonNegativeInt(object, "grace_ticks"),
                requireNumber(object, "discomfort_overpressure_atm"),
                requireNumber(object, "impairment_overpressure_atm"),
                requireNumber(object, "barotrauma_overpressure_atm"),
                requireNumber(object, "impaired_movement_multiplier"),
                requireNumber(object, "neurological_penalty"),
                requireNumber(object, "barotrauma_damage_per_update"));
    }

    private static int requireNonNegativeInt(JsonObject object, String field) {
        double value = requireNumber(object, field);
        if (value < 0.0 || value > Integer.MAX_VALUE || value != Math.rint(value)) {
            throw new IllegalArgumentException(field + " must be a non-negative integer");
        }
        return (int) value;
    }

    private static double requireNumber(JsonObject object, String field) {
        JsonElement element = object.get(field);
        if (!(element instanceof JsonPrimitive primitive) || !primitive.isNumber()) {
            throw new IllegalArgumentException(field + " must be numeric");
        }
        double value = primitive.getAsDouble();
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(field + " must be finite");
        }
        return value;
    }
}
