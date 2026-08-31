package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

public record VolcanicSourceProfile(String id, AtmosphereContribution contribution) {
    public VolcanicSourceProfile {
        id = Objects.requireNonNull(id, "id");
        if (id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        contribution = Objects.requireNonNull(contribution, "contribution");
    }
}
