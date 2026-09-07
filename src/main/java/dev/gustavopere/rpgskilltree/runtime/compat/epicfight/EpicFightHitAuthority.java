package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.runtime.AuthoritativeHitAttributionBridge;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/** Provider-specific hit-authority boundary kept free of Minecraft runtime types for focused tests. */
final class EpicFightHitAuthority {
    private static final AtomicLong HIT_SEQUENCE = new AtomicLong();

    private EpicFightHitAuthority() {}

    static AuthoritativeHitAttributionBridge.Attribution publish(
        Object damageSource,
        UUID targetId,
        long gameTime
    ) {
        return AuthoritativeHitAttributionBridge.canonicalize(
            damageSource,
            targetId,
            "epicfight/hit/" + gameTime + "/" + HIT_SEQUENCE.incrementAndGet(),
            "epicfight"
        );
    }

    static void discard(Object damageSource, UUID targetId) {
        AuthoritativeHitAttributionBridge.discard(damageSource, targetId);
    }
}
