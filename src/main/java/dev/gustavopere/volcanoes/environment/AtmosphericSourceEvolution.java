package dev.gustavopere.volcanoes.environment;

/**
 * Defines who owns the lifetime/evolution of an atmospheric source.
 *
 * <p>{@link #DYNAMIC} sources are Atmosphere-owned parcels evolved by the bounded diffusion/decay
 * queue and may use Atmosphere persistence. {@link #EXTERNAL} sources are stable emitters whose
 * authoritative lifecycle and persistence belong to an upstream producer; Atmosphere therefore
 * keeps them unchanged and non-persistent until that producer explicitly upserts or removes the
 * same source identity.</p>
 */
public enum AtmosphericSourceEvolution {
    DYNAMIC,
    EXTERNAL
}
