package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;

public final class ApothicIntegrationPolicy {
    public static final int APOTHEOSIS_SOCKET_LIMIT = 16;

    private ApothicIntegrationPolicy() {}

    public static int resolveSockets(int baseSockets, Collection<GemSocketModifier> modifiers) {
        if (baseSockets < 0) throw new IllegalArgumentException("baseSockets must be >= 0");
        int extra = modifiers.stream().mapToInt(GemSocketModifier::additionalSockets).sum();
        return Math.min(APOTHEOSIS_SOCKET_LIMIT, Math.addExact(baseSockets, extra));
    }

    public static double resolveGemPowerMultiplier(Collection<GemPowerModifier> modifiers) {
        double result = 1.0;
        for (GemPowerModifier modifier : modifiers) result *= 1.0 + modifier.moreMultiplier();
        return result;
    }
}
