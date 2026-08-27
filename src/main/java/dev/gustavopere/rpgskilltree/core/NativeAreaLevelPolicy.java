package dev.gustavopere.rpgskilltree.core;

/** Resolves intrinsic world threat for one stable territory identity. */
@FunctionalInterface
public interface NativeAreaLevelPolicy {
    long levelFor(TerritoryKey territoryKey);
}
