package dev.gustavopere.volcanoes.mixin.rns;

import dev.gustavopere.volcanoes.compat.rns.RnsProjectionOwnerMarker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

/**
 * Adds a Volcanoes-only durable owner token to RNS custom deposit locations.
 *
 * <p>The target class otherwise persists only deposit id plus BlockPos. Without a marker stored in
 * that host record, an exact-value foreign replacement is information-theoretically indistinguishable
 * after restart. This mixin is passive unless the exact-version Volcanoes RNS writer sets a marker.</p>
 */
@Mixin(targets = "com.bmaster.createrns.content.deposit.info.CustomServerDepositLocation", remap = false)
abstract class CustomServerDepositLocationMixin implements RnsProjectionOwnerMarker {
    @Unique
    private static final String VOLCANOES_OWNER_SOURCE_ID = "volcanoes_owner_source_id";

    @Unique
    private @Nullable UUID volcanoes$ownerSourceId;

    @Override
    public @Nullable UUID volcanoes$getOwnerSourceId() {
        return volcanoes$ownerSourceId;
    }

    @Override
    public void volcanoes$setOwnerSourceId(@Nullable UUID sourceId) {
        volcanoes$ownerSourceId = sourceId;
    }

    @Inject(method = "serialize", at = @At("RETURN"), remap = false)
    private void volcanoes$serializeOwner(CallbackInfoReturnable<CompoundTag> callback) {
        UUID owner = volcanoes$ownerSourceId;
        if (owner != null) {
            callback.getReturnValue().putUUID(VOLCANOES_OWNER_SOURCE_ID, owner);
        }
    }

    @Inject(method = "of", at = @At("RETURN"), remap = false)
    private static void volcanoes$restoreOwner(
            ServerLevel level,
            CompoundTag nbt,
            CallbackInfoReturnable<Object> callback
    ) {
        if (!nbt.hasUUID(VOLCANOES_OWNER_SOURCE_ID)) {
            return;
        }
        Object location = callback.getReturnValue();
        if (location instanceof RnsProjectionOwnerMarker marker) {
            marker.volcanoes$setOwnerSourceId(nbt.getUUID(VOLCANOES_OWNER_SOURCE_ID));
        }
    }
}
