package dev.gustavopere.rpgskilltree.core;

import java.util.List;

/** Chooses one available provider binding without embedding precedence into the Core. */
@FunctionalInterface
public interface ProviderBindingSelectionPolicy {
    CanonicalProviderBinding select(
        CanonicalStatKey canonicalStat,
        List<CanonicalProviderBinding> availableBindings
    );
}
