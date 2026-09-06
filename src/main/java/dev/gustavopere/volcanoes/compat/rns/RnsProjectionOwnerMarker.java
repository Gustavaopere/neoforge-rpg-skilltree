package dev.gustavopere.volcanoes.compat.rns;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/** Durable owner marker mixed into the exact RNS custom-location host object. */
public interface RnsProjectionOwnerMarker {
    @Nullable UUID volcanoes$getOwnerSourceId();

    void volcanoes$setOwnerSourceId(@Nullable UUID sourceId);
}
