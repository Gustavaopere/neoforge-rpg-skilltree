package dev.gustavopere.volcanoes.geology;

@FunctionalInterface
public interface RockProfileResolver {
    RockProfile resolve(long worldSeed, int x, int y, int z);

    static RockProfileResolver fallback() {
        return (worldSeed, x, y, z) -> RockProfile.GENERIC;
    }
}
