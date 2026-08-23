package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class ArcaneAccessPolicyTest {
    public static void main(String[] args) {
        require(!ArcaneAccessPolicy.canCast(PassiveNodeProgress.empty()), "locked before awakening");
        require(
            ArcaneAccessPolicy.canCast(PassiveNodeProgress.of(Map.of("rpgskilltree:arcane_000", 1))),
            "unlocked by awakening node"
        );
        require(
            !ArcaneAccessPolicy.canCast(PassiveNodeProgress.of(Map.of("rpgskilltree:arcane_001", 1))),
            "nearby arcane node must not bypass gate"
        );
        System.out.println("ArcaneAccessPolicyTest PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
