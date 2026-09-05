package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;

/** Pure deterministic arithmetic for colony economic capacity and price formation. */
public final class EconomyMath {
    private EconomyMath() {}

    public static long capacity(ColonyEconomicInputs inputs, EconomyParameters parameters) {
        Objects.requireNonNull(inputs, "inputs");
        Objects.requireNonNull(parameters, "parameters");

        long workerCapacity = Math.multiplyExact((long) inputs.adultWorkers(), parameters.workerWeight());
        long buildingCapacity = Math.multiplyExact((long) inputs.builtLevelPoints(), parameters.buildingLevelWeight());
        long rawCapacity = Math.addExact(
            Math.addExact(parameters.baseCapacity(), workerCapacity),
            buildingCapacity
        );

        int boundedWarehouses = Math.min(inputs.warehouseCount(), parameters.warehouseCap());
        double multiplier = 1.0D + parameters.warehouseBonus() * boundedWarehouses;
        double scaledCapacity = rawCapacity * multiplier;
        if (!Double.isFinite(scaledCapacity) || scaledCapacity > Long.MAX_VALUE) {
            throw new ArithmeticException("economic capacity overflow");
        }
        return Math.max(parameters.minimumCapacity(), Math.round(scaledCapacity));
    }

    public static double targetPriceIndex(long activeMoney, long economicCapacity, EconomyParameters parameters) {
        Objects.requireNonNull(parameters, "parameters");
        if (activeMoney < 0L) {
            throw new IllegalArgumentException("activeMoney must be non-negative");
        }
        if (economicCapacity <= 0L) {
            throw new IllegalArgumentException("economicCapacity must be positive");
        }
        if (activeMoney == 0L) {
            return parameters.minPriceIndex();
        }

        double pressure = (double) activeMoney / (double) Math.max(economicCapacity, parameters.minimumCapacity());
        double target = 100.0D * Math.pow(pressure, parameters.beta());
        if (!Double.isFinite(target)) {
            throw new ArithmeticException("price-index target is not finite");
        }
        return clamp(target, parameters.minPriceIndex(), parameters.maxPriceIndex());
    }

    public static double convergePriceIndex(double current, double target, EconomyParameters parameters) {
        Objects.requireNonNull(parameters, "parameters");
        requireFinitePositive(current, "current");
        requireFinitePositive(target, "target");

        double delta = target - current;
        double next;
        if (delta > 0.0D) {
            next = current + Math.min(delta, parameters.maxStepUp());
        } else {
            next = current - Math.min(-delta, parameters.maxStepDown());
        }
        return clamp(next, parameters.minPriceIndex(), parameters.maxPriceIndex());
    }

    public static long nominalPrice(long basePrice, double priceIndex) {
        if (basePrice < 0L) {
            throw new IllegalArgumentException("basePrice must be non-negative");
        }
        requireFinitePositive(priceIndex, "priceIndex");
        if (basePrice == 0L) {
            return 0L;
        }

        double scaled = ((double) basePrice * priceIndex) / 100.0D;
        if (!Double.isFinite(scaled) || scaled > Long.MAX_VALUE) {
            throw new ArithmeticException("nominal price overflow");
        }
        return Math.max(1L, Math.round(scaled));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static void requireFinitePositive(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and positive");
        }
    }
}
