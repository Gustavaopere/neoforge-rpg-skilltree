package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.CanonicalProviderBindingCatalog;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Runtime publication point for the currently loaded canonical provider-binding catalog. */
public final class CanonicalProviderBindingRuntimeCatalog {
    private static final AtomicReference<CanonicalProviderBindingCatalog> CURRENT = new AtomicReference<>();

    private CanonicalProviderBindingRuntimeCatalog() {}

    public static Optional<CanonicalProviderBindingCatalog> current() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static CanonicalProviderBindingCatalog requireCurrent() {
        CanonicalProviderBindingCatalog catalog = CURRENT.get();
        if (catalog == null) {
            throw new IllegalStateException("canonical provider binding catalog is not configured");
        }
        return catalog;
    }

    public static void install(CanonicalProviderBindingCatalog catalog) {
        CURRENT.set(Objects.requireNonNull(catalog, "catalog"));
    }

    public static void clear() {
        CURRENT.set(null);
    }
}
