package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class TerritoryGridTest {
    public static void main(String[] args) {
        configurableCellSizeDefinesStableTerritories();
        negativeCoordinatesUseMathematicalFloorDivision();
        dimensionRemainsPartOfTerritoryIdentity();
        cellSizeIsPolicyNotHardcoded();
        extremeCoordinatesRemainDeterministic();
        invalidCellSizesAreRejected();
        System.out.println("TerritoryGridTest: PASS");
    }

    private static void configurableCellSizeDefinesStableTerritories() {
        TerritoryGrid grid = new TerritoryGrid(512L);
        eq(512L, grid.cellSizeBlocks());
        eq(TerritoryKey.of("minecraft:overworld", 0L, 0L), grid.key("minecraft:overworld", 0L, 0L));
        eq(TerritoryKey.of("minecraft:overworld", 0L, 0L), grid.key("minecraft:overworld", 511L, 511L));
        eq(TerritoryKey.of("minecraft:overworld", 1L, 0L), grid.key("minecraft:overworld", 512L, 511L));
        eq(TerritoryKey.of("minecraft:overworld", 1L, 1L), grid.key("minecraft:overworld", 1023L, 1023L));
        eq(TerritoryKey.of("minecraft:overworld", 2L, 2L), grid.key("minecraft:overworld", 1024L, 1024L));
    }

    private static void negativeCoordinatesUseMathematicalFloorDivision() {
        TerritoryGrid grid = new TerritoryGrid(512L);
        eq(TerritoryKey.of("minecraft:overworld", -1L, -1L), grid.key("minecraft:overworld", -1L, -1L));
        eq(TerritoryKey.of("minecraft:overworld", -1L, -1L), grid.key("minecraft:overworld", -512L, -512L));
        eq(TerritoryKey.of("minecraft:overworld", -2L, -2L), grid.key("minecraft:overworld", -513L, -513L));
        eq(TerritoryKey.of("minecraft:overworld", -1L, 0L), grid.key("minecraft:overworld", -1L, 511L));
    }

    private static void dimensionRemainsPartOfTerritoryIdentity() {
        TerritoryGrid grid = new TerritoryGrid(512L);
        TerritoryKey overworld = grid.key("minecraft:overworld", 700L, -700L);
        TerritoryKey nether = grid.key("minecraft:the_nether", 700L, -700L);
        neq(overworld, nether);
        eq(overworld.cellX(), nether.cellX());
        eq(overworld.cellZ(), nether.cellZ());
    }

    private static void cellSizeIsPolicyNotHardcoded() {
        TerritoryGrid compact = new TerritoryGrid(256L);
        TerritoryGrid broad = new TerritoryGrid(1024L);
        TerritoryKey compactKey = compact.key("minecraft:overworld", 700L, 700L);
        TerritoryKey broadKey = broad.key("minecraft:overworld", 700L, 700L);
        eq(TerritoryKey.of("minecraft:overworld", 2L, 2L), compactKey);
        eq(TerritoryKey.of("minecraft:overworld", 0L, 0L), broadKey);
        neq(compactKey, broadKey);
    }

    private static void extremeCoordinatesRemainDeterministic() {
        TerritoryGrid grid = new TerritoryGrid(512L);
        TerritoryKey minimum = grid.key("minecraft:overworld", Long.MIN_VALUE, Long.MIN_VALUE);
        TerritoryKey maximum = grid.key("minecraft:overworld", Long.MAX_VALUE, Long.MAX_VALUE);
        eq(Math.floorDiv(Long.MIN_VALUE, 512L), minimum.cellX());
        eq(Math.floorDiv(Long.MIN_VALUE, 512L), minimum.cellZ());
        eq(Math.floorDiv(Long.MAX_VALUE, 512L), maximum.cellX());
        eq(Math.floorDiv(Long.MAX_VALUE, 512L), maximum.cellZ());
    }

    private static void invalidCellSizesAreRejected() {
        expect(IllegalArgumentException.class, () -> new TerritoryGrid(0L));
        expect(IllegalArgumentException.class, () -> new TerritoryGrid(-1L));
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void neq(Object left, Object right) {
        if (Objects.equals(left, right)) throw new AssertionError("expected distinct values: " + left);
    }
}
