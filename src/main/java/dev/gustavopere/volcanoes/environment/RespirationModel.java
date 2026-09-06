package dev.gustavopere.volcanoes.environment;

import java.util.EnumSet;
import java.util.Objects;

public final class RespirationModel {
    private final RespirationThresholds thresholds;

    public RespirationModel(RespirationThresholds thresholds) {
        this.thresholds = Objects.requireNonNull(thresholds, "thresholds");
    }

    public RespirationOutcome evaluate(AtmosphereState state, RespirationProtection protection) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(protection, "protection");

        EnumSet<AtmosphericHazard> hazards = EnumSet.noneOf(AtmosphericHazard.class);
        double oxygenPartialPressure = Math.max(
                state.oxygenPartialPressureAtm(),
                protection.oxygenSupplyPartialPressureAtm());
        double particulates = state.particulatesMgM3() * (1.0 - protection.particulateFilterEfficiency());
        double smoke = state.smokeMgM3() * (1.0 - protection.particulateFilterEfficiency());
        double acidGas = state.sulfurDioxidePpm() * (1.0 - protection.acidGasFilterEfficiency());
        double toxicGas = state.toxicGasPpm() * (1.0 - protection.toxicGasFilterEfficiency());

        if (oxygenPartialPressure < thresholds.minimumOxygenPartialPressureAtm()) {
            hazards.add(AtmosphericHazard.HYPOXIA);
        }
        if (state.carbonDioxideFraction() >= thresholds.carbonDioxideFraction()) {
            hazards.add(AtmosphericHazard.CARBON_DIOXIDE);
        }
        if (acidGas >= thresholds.acidGasPpm()) {
            hazards.add(AtmosphericHazard.ACID_GAS);
        }
        if (toxicGas >= thresholds.toxicGasPpm()) {
            hazards.add(AtmosphericHazard.TOXIC_GAS);
        }
        if (particulates >= thresholds.particulatesMgM3()) {
            hazards.add(AtmosphericHazard.PARTICULATES);
        }
        if (smoke >= thresholds.smokeMgM3()) {
            hazards.add(AtmosphericHazard.SMOKE);
        }

        if (hazards.isEmpty()) {
            return new RespirationOutcome(true, 0, thresholds.refillAirAmount(), hazards);
        }

        boolean severe = oxygenPartialPressure <= thresholds.severeOxygenPartialPressureAtm()
                || state.carbonDioxideFraction() >= thresholds.severeCarbonDioxideFraction()
                || acidGas >= thresholds.acidGasPpm() * 4.0
                || toxicGas >= thresholds.toxicGasPpm() * 4.0
                || particulates >= thresholds.particulatesMgM3() * 4.0
                || smoke >= thresholds.smokeMgM3() * 4.0;
        return new RespirationOutcome(
                false,
                severe ? thresholds.severeConsumeAirAmount() : thresholds.consumeAirAmount(),
                0,
                hazards);
    }
}
