package dev.gustavopere.rpgskilltree.compendium.client;

/**
 * Deterministic geometry policy for the Compendium screen.
 *
 * <p>The policy is independent from Minecraft rendering so scaled-resolution behavior can be
 * verified without constructing a client. Compact layouts expose one shared body region: the
 * renderer chooses list or detail content. Wide layouts expose a bounded list/detail split.</p>
 */
public record CompendiumScreenLayout(
    Rect content,
    Rect header,
    Rect search,
    Rect personalToolbar,
    Rect toolbar,
    Rect listBody,
    Rect detailBody,
    boolean splitPanes,
    int visibleRows
) {
    public static final int MAX_CONTENT_WIDTH = 1_440;
    public static final int ROW_HEIGHT = 22;
    public static final int MIN_SCREEN_WIDTH = 240;
    public static final int MIN_SCREEN_HEIGHT = 180;

    private static final int WIDE_SPLIT_MIN_WIDTH = 720;
    private static final int HORIZONTAL_MARGIN = 16;
    private static final int VERTICAL_MARGIN = 12;
    private static final int HEADER_HEIGHT = 24;
    private static final int SEARCH_HEIGHT = 20;
    private static final int TOOLBAR_HEIGHT = 20;
    private static final int CONTROL_GAP = 4;
    private static final int PANE_GAP = 8;

    public static CompendiumScreenLayout calculate(int screenWidth, int screenHeight) {
        if (screenWidth < MIN_SCREEN_WIDTH || screenHeight < MIN_SCREEN_HEIGHT) {
            throw new IllegalArgumentException(
                "Compendium screen requires at least " + MIN_SCREEN_WIDTH + "x" + MIN_SCREEN_HEIGHT
            );
        }

        int marginX = Math.min(HORIZONTAL_MARGIN, Math.max(8, screenWidth / 32));
        int marginY = Math.min(VERTICAL_MARGIN, Math.max(6, screenHeight / 30));
        int availableWidth = screenWidth - marginX * 2;
        int contentWidth = Math.min(MAX_CONTENT_WIDTH, availableWidth);
        int contentX = (screenWidth - contentWidth) / 2;
        int contentHeight = screenHeight - marginY * 2;
        Rect content = new Rect(contentX, marginY, contentWidth, contentHeight);

        Rect header = new Rect(content.x(), content.y(), content.width(), HEADER_HEIGHT);
        Rect search = new Rect(
            content.x(),
            header.bottom() + CONTROL_GAP,
            content.width(),
            SEARCH_HEIGHT
        );
        Rect personalToolbar = new Rect(
            content.x(),
            search.bottom() + CONTROL_GAP,
            content.width(),
            TOOLBAR_HEIGHT
        );
        Rect toolbar = new Rect(
            content.x(),
            personalToolbar.bottom() + CONTROL_GAP,
            content.width(),
            TOOLBAR_HEIGHT
        );

        int bodyY = toolbar.bottom() + CONTROL_GAP;
        int bodyHeight = content.bottom() - bodyY;
        if (bodyHeight < ROW_HEIGHT) {
            throw new IllegalArgumentException("screen height leaves no usable Compendium body viewport");
        }

        boolean split = content.width() >= WIDE_SPLIT_MIN_WIDTH;
        Rect listBody;
        Rect detailBody;
        if (split) {
            int usable = content.width() - PANE_GAP;
            int listWidth = Math.max(240, Math.min(480, usable * 3 / 8));
            int detailWidth = usable - listWidth;
            listBody = new Rect(content.x(), bodyY, listWidth, bodyHeight);
            detailBody = new Rect(listBody.right() + PANE_GAP, bodyY, detailWidth, bodyHeight);
        } else {
            listBody = new Rect(content.x(), bodyY, content.width(), bodyHeight);
            detailBody = listBody;
        }

        int visibleRows = Math.max(1, listBody.height() / ROW_HEIGHT);
        return new CompendiumScreenLayout(
            content,
            header,
            search,
            personalToolbar,
            toolbar,
            listBody,
            detailBody,
            split,
            visibleRows
        );
    }

    public record Rect(int x, int y, int width, int height) {
        public Rect {
            if (x < 0 || y < 0) throw new IllegalArgumentException("rect origin must not be negative");
            if (width <= 0 || height <= 0) throw new IllegalArgumentException("rect size must be positive");
        }

        public int right() {
            return x + width;
        }

        public int bottom() {
            return y + height;
        }
    }
}
