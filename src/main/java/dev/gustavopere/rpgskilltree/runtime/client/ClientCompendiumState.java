package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.client.CompendiumBrowserModel;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientSnapshot;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/** Client-local holder for the latest visibility-filtered Compendium snapshot. */
public final class ClientCompendiumState {
    private static final CompendiumClientSnapshot EMPTY = new CompendiumClientSnapshot(List.of(), List.of());
    private static final AtomicReference<CompendiumClientSnapshot> CURRENT = new AtomicReference<>(EMPTY);

    private ClientCompendiumState() {}

    public static CompendiumClientSnapshot get() {
        return CURRENT.get();
    }

    public static void install(CompendiumClientSnapshot snapshot) {
        CURRENT.set(Objects.requireNonNull(snapshot, "snapshot"));
    }

    public static void clear() {
        CURRENT.set(EMPTY);
    }

    public static CompendiumBrowserModel newBrowserModel() {
        return CURRENT.get().newBrowserModel();
    }
}
