package dev.gustavopere.volcanoes.protection;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Central safety gate for optional block mutation.
 *
 * <p>Provider failures fail closed. Destructive terrain mutation requires an explicitly
 * authoritative service. An authoritative zero-provider service represents successful discovery
 * with no supported protection authority active.</p>
 */
public final class ProtectedAreaService {
    @FunctionalInterface
    public interface Provider {
        boolean isProtected(ResourceKey<Level> dimension, BlockPos pos);
    }

    private final List<Provider> providers;
    private final boolean authoritative;

    private ProtectedAreaService(List<Provider> providers, boolean authoritative) {
        this.providers = List.copyOf(providers);
        this.authoritative = authoritative;
    }

    public static ProtectedAreaService empty() {
        return new ProtectedAreaService(List.of(), false);
    }

    public static ProtectedAreaService of(Provider... providers) {
        return new ProtectedAreaService(copyProviders(providers), false);
    }

    public static ProtectedAreaService authoritative(Provider... providers) {
        return new ProtectedAreaService(copyProviders(providers), true);
    }

    private static List<Provider> copyProviders(Provider... providers) {
        Objects.requireNonNull(providers, "providers");
        return Arrays.stream(providers)
                .map(provider -> Objects.requireNonNull(provider, "provider"))
                .toList();
    }

    public boolean allowsTerrainMutation() {
        return authoritative;
    }

    public boolean isProtected(ResourceKey<Level> dimension, BlockPos pos) {
        Objects.requireNonNull(dimension, "dimension");
        Objects.requireNonNull(pos, "pos");

        for (Provider provider : providers) {
            try {
                if (provider.isProtected(dimension, pos)) {
                    return true;
                }
            } catch (RuntimeException | LinkageError failure) {
                return true;
            }
        }
        return false;
    }

    public boolean mayMutate(ResourceKey<Level> dimension, BlockPos pos) {
        return authoritative && !isProtected(dimension, pos);
    }
}
