package dev.gustavopere.volcanoes.tectonics;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Deterministic low-frequency tectonic field built from jittered Voronoi-like plate centers.
 *
 * <p>Sampling is independent of chunks and terrain. Only coarse generated plate metadata is cached;
 * no per-block or per-coordinate state is persisted.</p>
 */
public final class VoronoiPlateField implements PlateField {
    static final double PLATE_CELL_SIZE_BLOCKS = 16_384.0;
    private static final double CENTER_MARGIN = 0.15;
    private static final double CENTER_SPAN = 0.70;
    private static final int DEFAULT_CACHE_CAPACITY = 512;

    private static final double HOTSPOT_CELL_SIZE_BLOCKS = 65_536.0;
    private static final double HOTSPOT_RADIUS_BLOCKS = 18_000.0;

    private final int cacheCapacity;
    private final Map<CellKey, PlateMetadata> plateMetadataCache;

    public VoronoiPlateField() {
        this(DEFAULT_CACHE_CAPACITY);
    }

    public VoronoiPlateField(int cacheCapacity) {
        if (cacheCapacity <= 0) {
            throw new IllegalArgumentException("cacheCapacity must be positive");
        }
        this.cacheCapacity = cacheCapacity;
        this.plateMetadataCache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<CellKey, PlateMetadata> eldest) {
                return size() > VoronoiPlateField.this.cacheCapacity;
            }
        };
    }

    @Override
    public PlateSample sample(long worldSeed, double x, double z) {
        requireFiniteCoordinate("x", x);
        requireFiniteCoordinate("z", z);

        long cellX = floorCell(x, PLATE_CELL_SIZE_BLOCKS);
        long cellZ = floorCell(z, PLATE_CELL_SIZE_BLOCKS);

        Candidate nearest = null;
        Candidate secondNearest = null;
        for (int offsetX = -2; offsetX <= 2; offsetX++) {
            for (int offsetZ = -2; offsetZ <= 2; offsetZ++) {
                PlateMetadata metadata = plateMetadata(worldSeed, cellX + offsetX, cellZ + offsetZ);
                double distanceSquared = squaredDistance(x, z, metadata.centerX(), metadata.centerZ());
                Candidate candidate = new Candidate(metadata, distanceSquared);
                if (nearest == null || distanceSquared < nearest.distanceSquared()) {
                    secondNearest = nearest;
                    nearest = candidate;
                } else if (secondNearest == null || distanceSquared < secondNearest.distanceSquared()) {
                    secondNearest = candidate;
                }
            }
        }

        if (nearest == null || secondNearest == null) {
            throw new IllegalStateException("plate field failed to produce neighboring centers");
        }

        PlateMetadata selected = nearest.metadata();
        PlateMetadata neighbor = secondNearest.metadata();
        double centerDeltaX = neighbor.centerX() - selected.centerX();
        double centerDeltaZ = neighbor.centerZ() - selected.centerZ();
        double centerDistance = Math.hypot(centerDeltaX, centerDeltaZ);
        if (!Double.isFinite(centerDistance) || centerDistance <= 0.0) {
            throw new IllegalStateException("plate field produced invalid neighboring centers");
        }
        double boundaryDistance = Math.max(
                0.0,
                (secondNearest.distanceSquared() - nearest.distanceSquared()) / (2.0 * centerDistance));
        PlateVector boundaryNormal = new PlateVector(
                centerDeltaX / centerDistance,
                centerDeltaZ / centerDistance);

        return new PlateSample(
                selected.id(),
                selected.centerX(),
                selected.centerZ(),
                selected.motion(),
                neighbor.id(),
                neighbor.motion(),
                boundaryNormal,
                boundaryDistance,
                hotspotIntensity(worldSeed, x, z));
    }

    int cachedPlateMetadataCount() {
        synchronized (plateMetadataCache) {
            return plateMetadataCache.size();
        }
    }

    private PlateMetadata plateMetadata(long worldSeed, long cellX, long cellZ) {
        CellKey key = new CellKey(worldSeed, cellX, cellZ);
        synchronized (plateMetadataCache) {
            PlateMetadata existing = plateMetadataCache.get(key);
            if (existing != null) {
                return existing;
            }
            PlateMetadata generated = generatePlateMetadata(worldSeed, cellX, cellZ);
            plateMetadataCache.put(key, generated);
            return generated;
        }
    }

    private static PlateMetadata generatePlateMetadata(long worldSeed, long cellX, long cellZ) {
        long baseHash = mix64(
                worldSeed
                        ^ cellX * 0x9E3779B97F4A7C15L
                        ^ cellZ * 0xC2B2AE3D27D4EB4FL
                        ^ 0x7A4D3B2C1F0E9D85L);
        long xHash = mix64(baseHash ^ 0x6A09E667F3BCC909L);
        long zHash = mix64(baseHash ^ 0xBB67AE8584CAA73BL);
        long idHash = mix64(baseHash ^ 0x3C6EF372FE94F82BL);
        long motionHash = mix64(baseHash ^ 0xA54FF53A5F1D36F1L);

        double centerX = (cellX + CENTER_MARGIN + CENTER_SPAN * unitDouble(xHash))
                * PLATE_CELL_SIZE_BLOCKS;
        double centerZ = (cellZ + CENTER_MARGIN + CENTER_SPAN * unitDouble(zHash))
                * PLATE_CELL_SIZE_BLOCKS;
        double motionAngle = unitDouble(motionHash) * Math.PI * 2.0;

        return new PlateMetadata(
                new PlateId(idHash),
                centerX,
                centerZ,
                PlateVector.fromAngle(motionAngle));
    }

    private static double hotspotIntensity(long worldSeed, double x, double z) {
        long cellX = floorCell(x, HOTSPOT_CELL_SIZE_BLOCKS);
        long cellZ = floorCell(z, HOTSPOT_CELL_SIZE_BLOCKS);
        double strongest = 0.0;

        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetZ = -1; offsetZ <= 1; offsetZ++) {
                long hotspotCellX = cellX + offsetX;
                long hotspotCellZ = cellZ + offsetZ;
                long baseHash = mix64(
                        worldSeed
                                ^ hotspotCellX * 0xD6E8FEB86659FD93L
                                ^ hotspotCellZ * 0xA5A3564E27F8862DL
                                ^ 0x94D049BB133111EBL);
                if (Math.floorMod(baseHash, 4L) != 0L) {
                    continue;
                }

                long xHash = mix64(baseHash ^ 0x243F6A8885A308D3L);
                long zHash = mix64(baseHash ^ 0x13198A2E03707344L);
                double hotspotX = (hotspotCellX + CENTER_MARGIN + CENTER_SPAN * unitDouble(xHash))
                        * HOTSPOT_CELL_SIZE_BLOCKS;
                double hotspotZ = (hotspotCellZ + CENTER_MARGIN + CENTER_SPAN * unitDouble(zHash))
                        * HOTSPOT_CELL_SIZE_BLOCKS;
                double distance = Math.hypot(x - hotspotX, z - hotspotZ);
                if (distance < HOTSPOT_RADIUS_BLOCKS) {
                    strongest = Math.max(strongest, 1.0 - distance / HOTSPOT_RADIUS_BLOCKS);
                }
            }
        }

        return strongest;
    }

    private static long floorCell(double coordinate, double cellSize) {
        return (long) Math.floor(coordinate / cellSize);
    }

    private static double squaredDistance(double x1, double z1, double x2, double z2) {
        double dx = x1 - x2;
        double dz = z1 - z2;
        return dx * dx + dz * dz;
    }

    private static void requireFiniteCoordinate(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
    }

    private static double unitDouble(long hash) {
        return (hash >>> 11) * 0x1.0p-53;
    }

    private static long mix64(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        value ^= value >>> 33;
        return value;
    }

    private record CellKey(long worldSeed, long cellX, long cellZ) {
    }

    private record PlateMetadata(PlateId id, double centerX, double centerZ, PlateVector motion) {
    }

    private record Candidate(PlateMetadata metadata, double distanceSquared) {
    }
}
