package dev.gustavopere.volcanoes.environment;

import java.util.Optional;

/**
 * Optional pollution authority bridge.
 *
 * <p>Publication authority and source-attributed external readback are separate capabilities. A
 * host may safely accept Volcanoes pollution while exposing only aggregate storage that cannot
 * distinguish Volcanoes-origin units from third-party pollution. Such an adapter remains
 * authoritative for publication but must leave {@link #supportsExternalReadback()} false, causing
 * Atmosphere to skip readback rather than guess provenance or double-count its own emission.</p>
 */
public interface PollutionAdapter {
    boolean isAuthoritative();

    void publish(PollutionEmission emission);

    /**
     * Whether {@link #sampleExternalOnly(String, double, double, double)} can prove that returned
     * pollution excludes Volcanoes' own already-published contributions.
     */
    default boolean supportsExternalReadback() {
        return false;
    }

    /**
     * Returns only pollution external to Volcanoes when that provenance is actually supported.
     * Callers must check {@link #supportsExternalReadback()} first.
     */
    Optional<PollutionLoad> sampleExternalOnly(String dimensionId, double x, double y, double z);

    static PollutionAdapter none() {
        return NoPollutionAdapter.INSTANCE;
    }

    enum NoPollutionAdapter implements PollutionAdapter {
        INSTANCE;

        @Override
        public boolean isAuthoritative() {
            return false;
        }

        @Override
        public void publish(PollutionEmission emission) {
        }

        @Override
        public Optional<PollutionLoad> sampleExternalOnly(String dimensionId, double x, double y, double z) {
            return Optional.empty();
        }
    }
}
