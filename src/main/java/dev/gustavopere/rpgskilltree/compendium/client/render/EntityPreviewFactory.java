package dev.gustavopere.rpgskilltree.compendium.client.render;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Platform-agnostic safety policy for Compendium entity previews.
 *
 * <p>The physical Minecraft client bridge lives under {@code runtime/client}. Keeping the policy in
 * the Compendium model layer lets the lightweight catalog contract suite compile it without a
 * Minecraft/NeoForge classpath.</p>
 */
public final class EntityPreviewFactory {
    private static final String VANILLA_NAMESPACE = "minecraft";
    private static final Set<String> ADAPTERS = ConcurrentHashMap.newKeySet();
    private static final Set<String> BLACKLIST = ConcurrentHashMap.newKeySet();
    private static final Set<String> QUARANTINED = ConcurrentHashMap.newKeySet();

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
        BLOCKED,
        UNKNOWN_TYPE,
        NOT_LIVING,
        CONSTRUCTION_FAILED,
        RENDER_FAILED
    }

    public static Policy policyFor(CompendiumEntryId entryId) {
        Objects.requireNonNull(entryId, "entryId");
        if (entryId.kind() != CompendiumEntryKind.ENTITY) return Policy.BLOCKED;

        String key = entryId.resourceLocation();
        if (BLACKLIST.contains(key) || QUARANTINED.contains(key)) return Policy.BLOCKED;
        if (ADAPTERS.contains(key)) return Policy.ADAPTER;
        return VANILLA_NAMESPACE.equals(entryId.namespace()) ? Policy.VANILLA_DEFAULT : Policy.BLOCKED;
    }

    /** Records that the physical client runtime has a safe constructor adapter for this entity. */
    public static void registerAdapter(CompendiumEntryId entryId) {
        String key = requireEntity(entryId).resourceLocation();
        ADAPTERS.add(key);
        QUARANTINED.remove(key);
    }

    /** Explicitly blocks preview construction even when a runtime adapter exists. */
    public static void blacklist(CompendiumEntryId entryId) {
        String key = requireEntity(entryId).resourceLocation();
        BLACKLIST.add(key);
        QUARANTINED.remove(key);
    }

    /** Removes an explicit blacklist entry without changing adapter registration. */
    public static void removeBlacklist(CompendiumEntryId entryId) {
        BLACKLIST.remove(requireEntity(entryId).resourceLocation());
    }

    /** Quarantines an entry after a construction or renderer failure for the rest of the client run. */
    public static void quarantine(CompendiumEntryId entryId) {
        if (entryId == null || entryId.kind() != CompendiumEntryKind.ENTITY) return;
        QUARANTINED.add(entryId.resourceLocation());
    }

    static boolean quarantined(CompendiumEntryId entryId) {
        return QUARANTINED.contains(requireEntity(entryId).resourceLocation());
    }

    private static CompendiumEntryId requireEntity(CompendiumEntryId entryId) {
        Objects.requireNonNull(entryId, "entryId");
        if (entryId.kind() != CompendiumEntryKind.ENTITY) {
            throw new IllegalArgumentException("preview policy only accepts ENTITY entries");
        }
        return entryId;
    }
}
