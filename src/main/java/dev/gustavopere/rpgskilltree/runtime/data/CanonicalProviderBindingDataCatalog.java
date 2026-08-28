package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.CanonicalProviderBindingCatalog;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/** Atomically published datapack-defined canonical provider bindings. */
public final class CanonicalProviderBindingDataCatalog {
    private static final AtomicReference<CanonicalProviderBindingCatalog> CURRENT =
        new AtomicReference<>(CanonicalProviderBindingCatalog.empty());

    private CanonicalProviderBindingDataCatalog() {}

    public static CanonicalProviderBindingCatalog current() {
        return CURRENT.get();
    }

    public static void install(CanonicalProviderBindingCatalog catalog) {
        CURRENT.set(Objects.requireNonNull(catalog, "catalog"));
    }

    public static void clear() {
        CURRENT.set(CanonicalProviderBindingCatalog.empty());
    }
}
