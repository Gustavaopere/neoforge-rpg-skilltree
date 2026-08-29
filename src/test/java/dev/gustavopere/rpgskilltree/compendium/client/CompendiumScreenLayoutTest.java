package dev.gustavopere.rpgskilltree.compendium.client;

import java.util.Objects;

public final class CompendiumScreenLayoutTest {
    public static void main(String[] args) {
        rejectsInvalidScreenSizes();
        compactLayoutFitsSmallScaledResolution();
        wideLayoutUsesNonOverlappingSplitPanes();
        ultrawideLayoutStaysCenteredAndBounded();
        rowCapacityTracksAvailableBodyHeight();
        personalToolbarGetsDedicatedResponsiveRow();
        System.out.println("CompendiumScreenLayoutTest: PASS");
    }

    private static void rejectsInvalidScreenSizes() {
        throwsIllegal(() -> CompendiumScreenLayout.calculate(0, 480));
        throwsIllegal(() -> CompendiumScreenLayout.calculate(854, 0));
        throwsIllegal(() -> CompendiumScreenLayout.calculate(-1, 480));
    }

    private static void compactLayoutFitsSmallScaledResolution() {
        CompendiumScreenLayout layout = CompendiumScreenLayout.calculate(320, 240);

        isFalse(layout.splitPanes());
        eq(layout.listBody(), layout.detailBody());
        isTrue(layout.visibleRows() >= 5);
        within(layout.content(), 320, 240);
        within(layout.header(), 320, 240);
        within(layout.search(), 320, 240);
        within(layout.personalToolbar(), 320, 240);
        within(layout.toolbar(), 320, 240);
        within(layout.listBody(), 320, 240);
        isTrue(layout.header().bottom() <= layout.search().y());
        isTrue(layout.search().bottom() <= layout.personalToolbar().y());
        isTrue(layout.personalToolbar().bottom() <= layout.toolbar().y());
        isTrue(layout.toolbar().bottom() <= layout.listBody().y());
    }

    private static void wideLayoutUsesNonOverlappingSplitPanes() {
        CompendiumScreenLayout layout = CompendiumScreenLayout.calculate(854, 480);

        isTrue(layout.splitPanes());
        isTrue(layout.visibleRows() >= 12);
        isTrue(layout.listBody().right() <= layout.detailBody().x());
        isTrue(layout.listBody().width() < layout.detailBody().width());
        eq(layout.listBody().y(), layout.detailBody().y());
        eq(layout.listBody().height(), layout.detailBody().height());
        within(layout.personalToolbar(), 854, 480);
        within(layout.listBody(), 854, 480);
        within(layout.detailBody(), 854, 480);
    }

    private static void ultrawideLayoutStaysCenteredAndBounded() {
        CompendiumScreenLayout layout = CompendiumScreenLayout.calculate(3440, 1440);

        eq(CompendiumScreenLayout.MAX_CONTENT_WIDTH, layout.content().width());
        eq((3440 - CompendiumScreenLayout.MAX_CONTENT_WIDTH) / 2, layout.content().x());
        isTrue(layout.splitPanes());
        within(layout.content(), 3440, 1440);
        isTrue(layout.detailBody().width() > layout.listBody().width());
    }

    private static void rowCapacityTracksAvailableBodyHeight() {
        CompendiumScreenLayout shortLayout = CompendiumScreenLayout.calculate(854, 360);
        CompendiumScreenLayout tallLayout = CompendiumScreenLayout.calculate(854, 720);

        eq(shortLayout.listBody().height() / CompendiumScreenLayout.ROW_HEIGHT, shortLayout.visibleRows());
        eq(tallLayout.listBody().height() / CompendiumScreenLayout.ROW_HEIGHT, tallLayout.visibleRows());
        isTrue(tallLayout.visibleRows() > shortLayout.visibleRows());
    }

    private static void personalToolbarGetsDedicatedResponsiveRow() {
        CompendiumScreenLayout compact = CompendiumScreenLayout.calculate(240, 180);
        CompendiumScreenLayout wide = CompendiumScreenLayout.calculate(1280, 720);

        eq(compact.content().width(), compact.personalToolbar().width());
        eq(wide.content().width(), wide.personalToolbar().width());
        eq(compact.toolbar().height(), compact.personalToolbar().height());
        eq(wide.toolbar().height(), wide.personalToolbar().height());
    }

    private static void within(CompendiumScreenLayout.Rect rect, int screenWidth, int screenHeight) {
        isTrue(rect.x() >= 0);
        isTrue(rect.y() >= 0);
        isTrue(rect.right() <= screenWidth);
        isTrue(rect.bottom() <= screenHeight);
        isTrue(rect.width() > 0);
        isTrue(rect.height() > 0);
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
