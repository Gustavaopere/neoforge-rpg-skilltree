package dev.gustavopere.volcanoes.volcano;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Server-memory preflight state for explicit existing-world volcano metadata registration.
 *
 * <p>Preview is read-only. Apply requires the exact one-shot preview token, matching world identity
 * and matching freshly resolved site candidate. The session never shapes terrain; its only mutation
 * is delegating one accepted site record to {@link VolcanoSavedData#register(VolcanoSite)}.</p>
 */
public final class ExistingWorldVolcanoAdminSession {
    private static final int MAX_PENDING_PREVIEWS = 128;

    private final long ttlTicks;
    private final Map<String, PendingPreview> pending = new LinkedHashMap<>();

    public ExistingWorldVolcanoAdminSession(long ttlTicks) {
        if (ttlTicks <= 0L) {
            throw new IllegalArgumentException("ttlTicks must be positive");
        }
        this.ttlTicks = ttlTicks;
    }

    public synchronized Preview preview(
            ResourceKey<Level> dimension,
            long worldSeed,
            long gameTime,
            VolcanoSite site
    ) {
        Objects.requireNonNull(dimension, "dimension");
        Objects.requireNonNull(site, "site");
        validateGameTime(gameTime);
        pruneExpired(gameTime);
        while (pending.size() >= MAX_PENDING_PREVIEWS) {
            Iterator<String> iterator = pending.keySet().iterator();
            if (!iterator.hasNext()) {
                break;
            }
            iterator.next();
            iterator.remove();
        }

        String token = UUID.randomUUID().toString();
        long expiresAtTick = saturatedAdd(gameTime, ttlTicks);
        pending.put(token, new PendingPreview(dimension, worldSeed, site, expiresAtTick));
        return new Preview(token, site, expiresAtTick);
    }

    public synchronized Optional<Preview> pending(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        PendingPreview preview = pending.get(token);
        return preview == null
                ? Optional.empty()
                : Optional.of(new Preview(token, preview.site(), preview.expiresAtTick()));
    }

    public synchronized boolean cancel(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }
        return pending.remove(token) != null;
    }

    public synchronized ApplyResult apply(
            String token,
            ResourceKey<Level> dimension,
            long worldSeed,
            long gameTime,
            VolcanoSite currentCandidate,
            VolcanoSavedData data
    ) {
        Objects.requireNonNull(dimension, "dimension");
        Objects.requireNonNull(currentCandidate, "currentCandidate");
        Objects.requireNonNull(data, "data");
        validateGameTime(gameTime);
        if (token == null || token.isBlank()) {
            return ApplyResult.NO_PREVIEW;
        }

        PendingPreview preview = pending.remove(token);
        if (preview == null) {
            return ApplyResult.NO_PREVIEW;
        }
        if (gameTime > preview.expiresAtTick()) {
            return ApplyResult.EXPIRED;
        }
        if (!preview.dimension().equals(dimension)
                || preview.worldSeed() != worldSeed
                || !preview.site().equals(currentCandidate)) {
            return ApplyResult.CONTEXT_CHANGED;
        }

        VolcanoSite existing = data.get(currentCandidate.persistenceId()).orElse(null);
        if (existing != null) {
            return existing.equals(currentCandidate)
                    ? ApplyResult.ALREADY_REGISTERED
                    : ApplyResult.CONFLICT;
        }
        if (!data.nearby(
                currentCandidate.center(),
                VolcanoWorldgenResolver.DEFAULT_PERSISTED_SPACING_BLOCKS).isEmpty()) {
            return ApplyResult.SPACING_CONFLICT;
        }

        try {
            return data.register(currentCandidate)
                    ? ApplyResult.REGISTERED
                    : ApplyResult.ALREADY_REGISTERED;
        } catch (IllegalStateException exception) {
            return ApplyResult.CONFLICT;
        }
    }

    private void pruneExpired(long gameTime) {
        pending.entrySet().removeIf(entry -> gameTime > entry.getValue().expiresAtTick());
    }

    private static void validateGameTime(long gameTime) {
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }
    }

    private static long saturatedAdd(long left, long right) {
        if (Long.MAX_VALUE - left < right) {
            return Long.MAX_VALUE;
        }
        return left + right;
    }

    public record Preview(String token, VolcanoSite site, long expiresAtTick) {
        public Preview {
            Objects.requireNonNull(token, "token");
            Objects.requireNonNull(site, "site");
        }
    }

    public enum ApplyResult {
        REGISTERED,
        ALREADY_REGISTERED,
        NO_PREVIEW,
        EXPIRED,
        CONTEXT_CHANGED,
        SPACING_CONFLICT,
        CONFLICT
    }

    private record PendingPreview(
            ResourceKey<Level> dimension,
            long worldSeed,
            VolcanoSite site,
            long expiresAtTick
    ) {
    }
}
