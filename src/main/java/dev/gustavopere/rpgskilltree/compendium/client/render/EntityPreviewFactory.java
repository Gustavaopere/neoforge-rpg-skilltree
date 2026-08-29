package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

/**
 * Conservative factory for Compendium-only entity previews.
 *
 * <p>Vanilla living entities are allowed by default. Modded entities are fail-closed and require an
 * explicit adapter because arbitrary third-party constructors may assume real-world lifecycle or
 * side effects. Preview instances are created against the current client level only to satisfy the
 * vanilla entity constructor contract; this factory never adds them to that level and never ticks
 * them.</p>
 */
public final class EntityPreviewFactory {
    private static final String VANILLA_NAMESPACE = "minecraft";
    private static final Map<ResourceLocation, PreviewAdapter> ADAPTERS = new ConcurrentHashMap<>();
    private static final Set<ResourceLocation> BLACKLIST = ConcurrentHashMap.newKeySet();
    private static final Set<ResourceLocation> QUARANTINED = ConcurrentHashMap.newKeySet();

    private EntityPreviewFactory() {}

    public enum Policy {
        VANILLA_DEFAULT,
        ADAPTER,
        BLOCKED
    }

    public enum Failure {
        NONE,
        NOT_ENTITY,
        NO_LEVEL,
        INVALID_ID,
        BLOCKED,
        UNKNOWN_TYPE,
        NOT_LIVING,
        CONSTRUCTION_FAILED
    }

    @FunctionalInterface
    public interface PreviewAdapter {
        LivingEntity create(ClientLevel level);
    }

    public record Result(LivingEntity entity, Failure failure) {
        public Result {
            Objects.requireNonNull(failure, "failure");
            if ((entity == null) == (failure == Failure.NONE)) {
                throw new IllegalArgumentException("ready preview results require an entity and no failure");
            }
        }

        public static Result ready(LivingEntity entity) {
            return new Result(Objects.requireNonNull(entity, "entity"), Failure.NONE);
        }

        public static Result failed(Failure failure) {
            if (failure == Failure.NONE) throw new IllegalArgumentException("failure must not be NONE");
            return new Result(null, failure);
        }

        public boolean ready() {
            return entity != null;
        }
    }

    public static Policy policyFor(CompendiumEntryId entryId) {
        Objects.requireNonNull(entryId, "entryId");
        if (entryId.kind() != CompendiumEntryKind.ENTITY) return Policy.BLOCKED;

        ResourceLocation key = ResourceLocation.tryParse(entryId.resourceLocation());
        if (key == null || BLACKLIST.contains(key) || QUARANTINED.contains(key)) return Policy.BLOCKED;
        if (ADAPTERS.containsKey(key)) return Policy.ADAPTER;
        return VANILLA_NAMESPACE.equals(key.getNamespace()) ? Policy.VANILLA_DEFAULT : Policy.BLOCKED;
    }

    public static Result create(CompendiumEntryId entryId, ClientLevel level) {
        Objects.requireNonNull(entryId, "entryId");
        if (entryId.kind() != CompendiumEntryKind.ENTITY) return Result.failed(Failure.NOT_ENTITY);
        if (level == null) return Result.failed(Failure.NO_LEVEL);

        ResourceLocation key = ResourceLocation.tryParse(entryId.resourceLocation());
        if (key == null) return Result.failed(Failure.INVALID_ID);

        Policy policy = policyFor(entryId);
        if (policy == Policy.BLOCKED) return Result.failed(Failure.BLOCKED);

        try {
            LivingEntity preview;
            if (policy == Policy.ADAPTER) {
                preview = ADAPTERS.get(key).create(level);
            } else {
                if (!BuiltInRegistries.ENTITY_TYPE.containsKey(key)) return Result.failed(Failure.UNKNOWN_TYPE);
                EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(key);
                Entity created = type.create(level);
                if (!(created instanceof LivingEntity living)) return Result.failed(Failure.NOT_LIVING);
                preview = living;
            }
            if (preview == null) {
                quarantine(key);
                return Result.failed(Failure.CONSTRUCTION_FAILED);
            }
            return Result.ready(preview);
        } catch (RuntimeException | LinkageError exception) {
            quarantine(key);
            return Result.failed(Failure.CONSTRUCTION_FAILED);
        }
    }

    /**
     * Opts a modded entity into preview construction. The adapter must return a detached living
     * entity and must not add/tick it in the client level.
     */
    public static void registerAdapter(ResourceLocation entityId, PreviewAdapter adapter) {
        ResourceLocation key = Objects.requireNonNull(entityId, "entityId");
        ADAPTERS.put(key, Objects.requireNonNull(adapter, "adapter"));
        QUARANTINED.remove(key);
    }

    /** Explicitly blocks preview construction even when an adapter exists. */
    public static void blacklist(ResourceLocation entityId) {
        ResourceLocation key = Objects.requireNonNull(entityId, "entityId");
        BLACKLIST.add(key);
        QUARANTINED.remove(key);
    }

    /** Removes an explicit blacklist entry without changing adapter registration. */
    public static void removeBlacklist(ResourceLocation entityId) {
        BLACKLIST.remove(Objects.requireNonNull(entityId, "entityId"));
    }

    /** Quarantines an entry after a construction or renderer failure for the rest of the client run. */
    public static void quarantine(CompendiumEntryId entryId) {
        if (entryId == null || entryId.kind() != CompendiumEntryKind.ENTITY) return;
        ResourceLocation key = ResourceLocation.tryParse(entryId.resourceLocation());
        if (key != null) quarantine(key);
    }

    static boolean quarantined(ResourceLocation entityId) {
        return QUARANTINED.contains(Objects.requireNonNull(entityId, "entityId"));
    }

    private static void quarantine(ResourceLocation entityId) {
        QUARANTINED.add(entityId);
    }
}
