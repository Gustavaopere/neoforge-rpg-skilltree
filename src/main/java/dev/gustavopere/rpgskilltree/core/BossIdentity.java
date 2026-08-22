package dev.gustavopere.rpgskilltree.core;

public record BossIdentity(String entityId, String boundedRewardGroup) {
    public BossIdentity {
        if (entityId == null || entityId.isBlank()) throw new IllegalArgumentException("entityId must not be blank");
        if (!BossRewardKeyPolicy.isNamespacedId(entityId)) throw new IllegalArgumentException("entityId must be namespaced: " + entityId);
        if (boundedRewardGroup != null && !boundedRewardGroup.isBlank() && !BossRewardKeyPolicy.isNamespacedId(boundedRewardGroup)) {
            throw new IllegalArgumentException("boundedRewardGroup must be a stable namespaced id: " + boundedRewardGroup);
        }
    }
}
