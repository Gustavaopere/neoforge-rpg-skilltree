package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.environment.VolcanicPollutionRuntime;

/** Installs the verified Destroy pollution authority without linking Volcanoes to Destroy classes. */
public final class DestroyPollutionRuntime {
    private DestroyPollutionRuntime() {
    }

    public static boolean installIfAvailable() {
        return DestroyCompat.installIfAvailable(() -> {
            DestroyNeoForgePollutionWriter writer = DestroyNeoForgePollutionWriter.createVerified();
            VolcanicPollutionRuntime.installAdapterFactory(level ->
                    new DestroyPollutionAdapter((emission, projection) ->
                            writer.publish(level, emission, projection)));
        });
    }
}
