package dev.gustavopere.volcanoes.environment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Data-oriented baseline selector. It chooses configured states by dimension and minimum altitude,
 * but deliberately does not calculate pressure physics; Stage 05 can later supply different states.
 */
public final class LayeredAtmosphereBaselineProvider implements AtmosphereBaselineProvider {
    private final AtmosphereState fallback;
    private final Map<String, List<AtmosphereBaselineLayer>> layersByDimension;

    public LayeredAtmosphereBaselineProvider(
            AtmosphereState fallback,
            Map<String, List<AtmosphereBaselineLayer>> layersByDimension
    ) {
        this.fallback = Objects.requireNonNull(fallback, "fallback");
        Objects.requireNonNull(layersByDimension, "layersByDimension");

        Map<String, List<AtmosphereBaselineLayer>> normalized = new LinkedHashMap<>();
        for (Map.Entry<String, List<AtmosphereBaselineLayer>> entry : layersByDimension.entrySet()) {
            String dimensionId = Objects.requireNonNull(entry.getKey(), "dimensionId");
            if (dimensionId.isBlank()) {
                throw new IllegalArgumentException("dimensionId must not be blank");
            }
            List<AtmosphereBaselineLayer> layers = new ArrayList<>(
                    Objects.requireNonNull(entry.getValue(), "layers"));
            layers.sort(Comparator.comparingDouble(AtmosphereBaselineLayer::minimumY));
            for (int i = 1; i < layers.size(); i++) {
                if (layers.get(i - 1).minimumY() == layers.get(i).minimumY()) {
                    throw new IllegalArgumentException(
                            "duplicate atmosphere baseline floor for " + dimensionId + ": "
                                    + layers.get(i).minimumY());
                }
            }
            normalized.put(dimensionId, List.copyOf(layers));
        }
        this.layersByDimension = Map.copyOf(normalized);
    }

    public static LayeredAtmosphereBaselineProvider standard() {
        AtmosphereState standard = AtmosphereState.standardOverworld();
        return new LayeredAtmosphereBaselineProvider(
                standard,
                Map.of("minecraft:overworld", List.of(new AtmosphereBaselineLayer(-64.0, standard))));
    }

    @Override
    public AtmosphereState sample(String dimensionId, double y) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("y must be finite");
        }
        List<AtmosphereBaselineLayer> layers = layersByDimension.get(dimensionId);
        if (layers == null || layers.isEmpty()) {
            return fallback;
        }

        AtmosphereState selected = fallback;
        for (AtmosphereBaselineLayer layer : layers) {
            if (y < layer.minimumY()) {
                break;
            }
            selected = layer.state();
        }
        return selected;
    }
}
