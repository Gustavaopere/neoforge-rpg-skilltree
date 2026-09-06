package dev.gustavopere.volcanoes.compat;

import net.neoforged.fml.ModList;

import java.util.Objects;
import java.util.Optional;

/** Fail-closed exact-version gate for optional host-mod integrations. */
public final class ExactModVersionGate {
    private ExactModVersionGate() {
    }

    public static boolean isExactlyLoaded(String modId, String expectedVersion) {
        requireText(modId, "modId");
        requireText(expectedVersion, "expectedVersion");

        try {
            Optional<String> actualVersion = ModList.get()
                    .getModContainerById(modId)
                    .map(container -> container.getModInfo().getVersion().toString());
            return matches(actualVersion, expectedVersion);
        } catch (RuntimeException | LinkageError failure) {
            return false;
        }
    }

    static boolean matches(Optional<String> actualVersion, String expectedVersion) {
        Objects.requireNonNull(actualVersion, "actualVersion");
        requireText(expectedVersion, "expectedVersion");
        return actualVersion.filter(expectedVersion::equals).isPresent();
    }

    private static void requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
    }
}
