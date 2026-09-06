package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable consumer-facing snapshot of one eruption update.
 *
 * <p>The persistent {@link EruptionEvent} remains compact; this signal joins it with the volcano
 * source and current chamber snapshot only when downstream lava, ash, gas, seismic or integration
 * consumers need to react.</p>
 */
public record EruptionSignal(
        UUID volcanoId,
        BlockPos source,
        EruptionPhase phase,
        EruptionProfile profile,
        MagmaChamber chamber,
        double phaseProgress,
        double intensity
) {
    public EruptionSignal {
        volcanoId = Objects.requireNonNull(volcanoId, "volcanoId");
        source = Objects.requireNonNull(source, "source").immutable();
        phase = Objects.requireNonNull(phase, "phase");
        profile = Objects.requireNonNull(profile, "profile");
        chamber = Objects.requireNonNull(chamber, "chamber");
        if (!Double.isFinite(phaseProgress) || phaseProgress < 0.0 || phaseProgress > 1.0) {
            throw new IllegalArgumentException("phaseProgress must be within [0, 1]");
        }
        if (!Double.isFinite(intensity) || intensity < 0.0 || intensity > profile.peakIntensity()) {
            throw new IllegalArgumentException("intensity must be within [0, peakIntensity]");
        }
    }

    public static EruptionSignal from(VolcanoSite site, MagmaChamber chamber, EruptionEvent event) {
        Objects.requireNonNull(site, "site");
        Objects.requireNonNull(chamber, "chamber");
        Objects.requireNonNull(event, "event");
        if (!site.persistenceId().equals(event.volcanoId())) {
            throw new IllegalArgumentException("eruption event does not belong to volcano site");
        }

        double progress = phaseProgress(event);
        double intensity = intensity(event.phase(), event.profile().peakIntensity(), progress);
        return new EruptionSignal(
                event.volcanoId(),
                site.center(),
                event.phase(),
                event.profile(),
                chamber,
                progress,
                intensity);
    }

    private static double phaseProgress(EruptionEvent event) {
        if (event.phase() == EruptionPhase.DORMANT) {
            return 1.0;
        }
        long phaseStart = switch (event.phase()) {
            case PRECURSORS -> 0L;
            case OPENING -> event.profile().precursorsTicks();
            case SUSTAINED -> event.profile().precursorsTicks() + event.profile().openingTicks();
            case WANING -> event.profile().precursorsTicks()
                    + event.profile().openingTicks()
                    + event.profile().sustainedTicks();
            case DORMANT -> event.profile().totalDurationTicks();
        };
        long duration = event.profile().durationTicks(event.phase());
        long elapsedInPhase = Math.max(0L, event.elapsedTicks() - phaseStart);
        return Math.max(0.0, Math.min(1.0, (double) elapsedInPhase / (double) duration));
    }

    private static double intensity(EruptionPhase phase, double peak, double progress) {
        return switch (phase) {
            case PRECURSORS -> peak * (0.10 + 0.15 * progress);
            case OPENING -> peak * (0.25 + 0.75 * progress);
            case SUSTAINED -> peak;
            case WANING -> peak * (1.0 - progress);
            case DORMANT -> 0.0;
        };
    }
}
