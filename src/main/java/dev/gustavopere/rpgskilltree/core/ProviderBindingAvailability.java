package dev.gustavopere.rpgskilltree.core;

/** Reports whether one concrete provider binding can be used in the current runtime. */
@FunctionalInterface
public interface ProviderBindingAvailability {
    boolean isAvailable(CanonicalProviderBinding binding);
}
