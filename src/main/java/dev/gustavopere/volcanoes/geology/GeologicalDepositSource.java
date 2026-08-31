package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 * Read-only integration SPI for querying Volcanoes geological deposits.
 *
 * <p>Optional integrations such as external scanners can consume this interface without gaining
 * mutation access to the persistent registry and without introducing their API into the geology
 * core. Implementations should return deterministic snapshot ordering for identical state.</p>
 */
public interface GeologicalDepositSource {
    /** Returns all known deposits as a deterministic read-only snapshot. */
    List<GeologicalDeposit> all();

    /** Returns deposits whose centers are within {@code radius} blocks of {@code center}. */
    List<GeologicalDeposit> nearby(BlockPos center, double radius);
}
