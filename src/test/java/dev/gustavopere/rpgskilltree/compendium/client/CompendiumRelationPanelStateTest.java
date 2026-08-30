package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Objects;

public final class CompendiumRelationPanelStateTest {
    public static void main(String[] args) {
        panelOpensOnlyForAvailableRelations();
        changingEntryClosesPanelAndMissingRelationsClosePanel();
        viewportAndScrollStayBounded();
        System.out.println("CompendiumRelationPanelStateTest: PASS");
    }

    private static void panelOpensOnlyForAvailableRelations() {
        CompendiumRelationPanelState state = new CompendiumRelationPanelState();
        CompendiumEntryId wolf = id("minecraft:wolf");

        isFalse(state.isOpen());
        state.toggle(wolf, 0);
        isFalse(state.isOpen());

        state.toggle(wolf, 3);
        isTrue(state.isOpen());
        eq(wolf, state.owner().orElseThrow());

        state.toggle(wolf, 3);
        isFalse(state.isOpen());
    }

    private static void changingEntryClosesPanelAndMissingRelationsClosePanel() {
        CompendiumRelationPanelState state = new CompendiumRelationPanelState();
        CompendiumEntryId wolf = id("minecraft:wolf");
        CompendiumEntryId fox = id("minecraft:fox");

        state.toggle(wolf, 10);
        state.scrollRows(5, 10, 3);
        eq(5, state.firstVisibleRow());

        state.sync(fox, 4);
        isFalse(state.isOpen());
        eq(0, state.firstVisibleRow());
        isTrue(state.owner().isEmpty());

        state.toggle(fox, 4);
        isTrue(state.isOpen());
        state.sync(fox, 0);
        isFalse(state.isOpen());
        eq(0, state.firstVisibleRow());
        isTrue(state.owner().isEmpty());
    }

    private static void viewportAndScrollStayBounded() {
        CompendiumRelationPanelState state = new CompendiumRelationPanelState();
        CompendiumEntryId wolf = id("minecraft:wolf");
        state.toggle(wolf, 8);

        eq(new CompendiumRelationPanelState.Viewport(0, 3, false, true), state.viewport(8, 3));
        state.scrollRows(100, 8, 3);
        eq(new CompendiumRelationPanelState.Viewport(5, 3, true, false), state.viewport(8, 3));
        state.scrollRows(-100, 8, 3);
        eq(new CompendiumRelationPanelState.Viewport(0, 3, false, true), state.viewport(8, 3));
        throwsIllegal(() -> state.viewport(8, 0));
    }

    private static CompendiumEntryId id(String value) {
        return CompendiumEntryId.of(CompendiumEntryKind.ENTITY, value);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void isTrue(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void isFalse(boolean value) {
        if (value) throw new AssertionError("expected false");
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
