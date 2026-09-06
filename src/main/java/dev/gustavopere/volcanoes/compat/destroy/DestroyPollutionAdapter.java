package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.environment.PollutionAdapter;
import dev.gustavopere.volcanoes.environment.PollutionEmission;
import dev.gustavopere.volcanoes.environment.PollutionLoad;

import java.util.Objects;
import java.util.Optional;

/**
 * Authoritative publication adapter for Destroy 0.4.1.
 *
 * <p>Destroy stores aggregate integer pollution without source identity. Therefore this adapter
 * deliberately does not claim aggregate readback is source-exclusive; doing so would feed
 * Volcanoes-origin pollution back into Atmosphere after Destroy decay/spread and double count it.</p>
 */
public final class DestroyPollutionAdapter implements PollutionAdapter {
    @FunctionalInterface
    public interface Writer {
        void publish(PollutionEmission emission, DestroyPollutionProjection projection);
    }

    private final Writer writer;

    public DestroyPollutionAdapter(Writer writer) {
        this.writer = Objects.requireNonNull(writer, "writer");
    }

    @Override
    public boolean isAuthoritative() {
        return true;
    }

    @Override
    public void publish(PollutionEmission emission) {
        PollutionEmission value = Objects.requireNonNull(emission, "emission");
        DestroyPollutionProjection projection = DestroyPollutionProjection.from(value.load());
        if (projection.hasSupportedLoad()) {
            writer.publish(value, projection);
        }
    }

    @Override
    public Optional<PollutionLoad> sampleExternalOnly(String dimensionId, double x, double y, double z) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        return Optional.empty();
    }
}
