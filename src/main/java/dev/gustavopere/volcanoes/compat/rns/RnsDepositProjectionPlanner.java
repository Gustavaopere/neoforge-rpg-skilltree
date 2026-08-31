package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Pure projection policy from Volcanoes geological deposits into verified RNS identities. */
public final class RnsDepositProjectionPlanner {
    private static final Map<ResourceLocation, ResourceLocation> VERIFIED_RNS_DEPOSIT_IDS = Map.of(
            id("c", "ores/copper"), id("create_rns", "deposit_copper"),
            id("c", "ores/iron"), id("create_rns", "deposit_iron"),
            id("c", "ores/gold"), id("create_rns", "deposit_gold")
    );

    private static final EnumSet<DepositOrigin> ELIGIBLE_ORIGINS =
            EnumSet.of(DepositOrigin.MAGMATIC, DepositOrigin.HYDROTHERMAL);

    private static final Comparator<Projection> PROJECTION_ORDER = Comparator
            .comparing((Projection projection) -> projection.sourceId().toString())
            .thenComparing(projection -> projection.rnsDepositId().toString())
            .thenComparingInt(projection -> projection.center().getX())
            .thenComparingInt(projection -> projection.center().getY())
            .thenComparingInt(projection -> projection.center().getZ());

    private static final Comparator<Collision> COLLISION_ORDER = Comparator
            .comparing((Collision collision) -> collision.rnsDepositId().toString())
            .thenComparingInt(collision -> collision.chunk().x)
            .thenComparingInt(collision -> collision.chunk().z);

    private RnsDepositProjectionPlanner() {
    }

    public static Plan plan(List<GeologicalDeposit> deposits) {
        Objects.requireNonNull(deposits, "deposits");

        List<Projection> candidates = deposits.stream()
                .map(deposit -> Objects.requireNonNull(deposit, "deposit"))
                .filter(deposit -> ELIGIBLE_ORIGINS.contains(deposit.origin()))
                .map(RnsDepositProjectionPlanner::projectIfSupported)
                .filter(Objects::nonNull)
                .sorted(PROJECTION_ORDER)
                .toList();

        Map<RnsIdentity, List<Projection>> byIdentity = new LinkedHashMap<>();
        for (Projection projection : candidates) {
            RnsIdentity identity = identity(projection);
            byIdentity.computeIfAbsent(identity, ignored -> new ArrayList<>()).add(projection);
        }

        List<Projection> accepted = new ArrayList<>();
        List<Collision> collisions = new ArrayList<>();
        for (Map.Entry<RnsIdentity, List<Projection>> entry : byIdentity.entrySet()) {
            List<Projection> grouped = entry.getValue();
            if (grouped.size() == 1) {
                accepted.add(grouped.getFirst());
                continue;
            }

            List<UUID> sourceIds = grouped.stream()
                    .map(Projection::sourceId)
                    .sorted(Comparator.comparing(UUID::toString))
                    .toList();
            collisions.add(new Collision(entry.getKey().rnsDepositId(), entry.getKey().chunk(), sourceIds));
        }

        accepted.sort(PROJECTION_ORDER);
        collisions.sort(COLLISION_ORDER);
        return new Plan(accepted, collisions);
    }

    static RnsIdentity identity(Projection projection) {
        Objects.requireNonNull(projection, "projection");
        return new RnsIdentity(projection.rnsDepositId(), new ChunkPos(projection.center()));
    }

    private static Projection projectIfSupported(GeologicalDeposit deposit) {
        ResourceLocation rnsDepositId = VERIFIED_RNS_DEPOSIT_IDS.get(deposit.resourceTag());
        if (rnsDepositId == null) {
            return null;
        }
        return new Projection(deposit.persistenceId(), rnsDepositId, deposit.center().immutable());
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    record RnsIdentity(ResourceLocation rnsDepositId, ChunkPos chunk) {
        RnsIdentity {
            Objects.requireNonNull(rnsDepositId, "rnsDepositId");
            Objects.requireNonNull(chunk, "chunk");
        }
    }

    public record Projection(UUID sourceId, ResourceLocation rnsDepositId, BlockPos center) {
        public Projection {
            Objects.requireNonNull(sourceId, "sourceId");
            Objects.requireNonNull(rnsDepositId, "rnsDepositId");
            center = Objects.requireNonNull(center, "center").immutable();
        }
    }

    public record Collision(ResourceLocation rnsDepositId, ChunkPos chunk, List<UUID> sourceIds) {
        public Collision {
            Objects.requireNonNull(rnsDepositId, "rnsDepositId");
            Objects.requireNonNull(chunk, "chunk");
            sourceIds = List.copyOf(Objects.requireNonNull(sourceIds, "sourceIds"));
            if (sourceIds.size() < 2) {
                throw new IllegalArgumentException("RNS collision requires at least two source deposits");
            }
        }
    }

    public record Plan(List<Projection> projections, List<Collision> collisions) {
        public Plan {
            projections = List.copyOf(Objects.requireNonNull(projections, "projections"));
            collisions = List.copyOf(Objects.requireNonNull(collisions, "collisions"));
        }
    }
}
