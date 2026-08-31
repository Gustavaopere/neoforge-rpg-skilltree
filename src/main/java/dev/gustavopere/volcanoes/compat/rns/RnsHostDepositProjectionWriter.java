package dev.gustavopere.volcanoes.compat.rns;

import com.bmaster.createrns.RNSMisc;
import com.bmaster.createrns.content.deposit.info.CustomServerDepositLocation;
import com.bmaster.createrns.content.deposit.info.LevelDepositData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Objects;

/** Exact-host writer for Create: Rock & Stone 1.3.1-1.21.1-6 custom deposits. */
final class RnsHostDepositProjectionWriter implements RnsDepositProjectionWriter {
    private final ServerLevel level;
    private final LevelDepositData depositData;
    private final RnsProjectionOwnershipData ownership;

    RnsHostDepositProjectionWriter(ServerLevel level) {
        this.level = Objects.requireNonNull(level, "level");
        this.depositData = level.getData(RNSMisc.LEVEL_DEPOSIT_DATA.get());
        this.ownership = RnsProjectionOwnershipData.get(level);
    }

    @Override
    public boolean ensurePresent(RnsDepositProjectionPlanner.Projection projection) {
        Objects.requireNonNull(projection, "projection");
        CustomServerDepositLocation desired = location(projection);
        if (!markOwned(desired, projection)) {
            return false;
        }

        CustomServerDepositLocation existing = matchingIdentity(desired);
        if (existing != null) {
            if (!isOwnedBy(existing, projection)) {
                // Occupied identity without the durable host marker is foreign/unknown. A stale
                // side ledger must never authorize adoption or destructive removal.
                ownership.clearIdentity(projection);
                return false;
            }

            // The marker persisted in the RNS host record is authoritative. Repair the auxiliary
            // attribution from that proof instead of deriving host authority from the side ledger.
            ownership.clearIdentity(projection);
            return ownership.claim(projection);
        }

        // No host record exists, so any historical side attribution for this identity is stale.
        ownership.clearIdentity(projection);

        if (!depositData.addCustomDeposit(desired)) {
            return false;
        }

        CustomServerDepositLocation inserted = matchingIdentity(desired);
        if (inserted != desired || !isOwnedBy(inserted, projection)) {
            // Never roll back by value. A concurrent foreign replacement must be left untouched.
            return false;
        }

        return ownership.claim(projection);
    }

    @Override
    public boolean ensureAbsent(RnsDepositProjectionPlanner.Projection projection) {
        Objects.requireNonNull(projection, "projection");
        CustomServerDepositLocation desired = location(projection);
        CustomServerDepositLocation existing = matchingIdentity(desired);
        if (existing == null) {
            ownership.clearIdentity(projection);
            return true;
        }
        if (!isOwnedBy(existing, projection)) {
            // Only the owner marker stored in the RNS host record grants destructive authority.
            ownership.clearIdentity(projection);
            return true;
        }

        depositData.removeCustomDeposit(existing);
        CustomServerDepositLocation afterRemoval = matchingIdentity(desired);
        if (afterRemoval != null && isOwnedBy(afterRemoval, projection)) {
            return false;
        }

        ownership.clearIdentity(projection);
        return true;
    }

    private CustomServerDepositLocation matchingIdentity(CustomServerDepositLocation desired) {
        CustomServerDepositLocation existing = CustomServerDepositLocation.getNearestCustom(
                level,
                desired.getKey(),
                desired.getLocation(),
                true,
                0);
        return existing != null
                && existing.getKey().equals(desired.getKey())
                && new ChunkPos(existing.getLocation()).equals(new ChunkPos(desired.getLocation()))
                ? existing
                : null;
    }

    private static boolean markOwned(
            CustomServerDepositLocation location,
            RnsDepositProjectionPlanner.Projection projection
    ) {
        if (!(location instanceof RnsProjectionOwnerMarker marker)) {
            return false;
        }
        marker.volcanoes$setOwnerSourceId(projection.sourceId());
        return true;
    }

    private static boolean isOwnedBy(
            CustomServerDepositLocation location,
            RnsDepositProjectionPlanner.Projection projection
    ) {
        if (!samePreciseLocation(location, projection)
                || !(location instanceof RnsProjectionOwnerMarker marker)) {
            return false;
        }
        return projection.sourceId().equals(marker.volcanoes$getOwnerSourceId());
    }

    private static boolean samePreciseLocation(
            CustomServerDepositLocation existing,
            RnsDepositProjectionPlanner.Projection projection
    ) {
        return existing.getKey().location().equals(projection.rnsDepositId())
                && existing.getLocation().equals(projection.center());
    }

    private static CustomServerDepositLocation location(RnsDepositProjectionPlanner.Projection projection) {
        ResourceKey<Structure> structureKey = ResourceKey.create(
                Registries.STRUCTURE,
                projection.rnsDepositId());
        return new CustomServerDepositLocation(structureKey, projection.center());
    }
}
