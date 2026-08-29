package dev.gustavopere.rpgskilltree.runtime.diagnostics;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.slf4j.Logger;

/** Stable, grep-friendly operational diagnostic taxonomy for runtime logs. */
public final class RuntimeDiagnostics {
    private static final Pattern EVENT_ID = Pattern.compile("[a-z0-9]+(?:_[a-z0-9]+)*");

    public enum Category {
        BOOTSTRAP("bootstrap"),
        COMPAT("compat"),
        PROGRESSION("progression"),
        EFFECTS("effects"),
        COMPENDIUM("compendium");

        private final String id;

        Category(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }

    private RuntimeDiagnostics() {
    }

    public static String prefix(Category category, String event) {
        Objects.requireNonNull(category, "category");
        Objects.requireNonNull(event, "event");
        if (!EVENT_ID.matcher(event).matches()) {
            throw new IllegalArgumentException(
                "diagnostic event id must use lower_snake_case: " + event.toLowerCase(Locale.ROOT)
            );
        }
        return "[rpgskilltree/" + category.id() + "/" + event + "] ";
    }

    public static void info(
        Logger logger,
        Category category,
        String event,
        String message,
        Object... arguments
    ) {
        requireLoggerAndMessage(logger, message);
        logger.info(prefix(category, event) + message, arguments);
    }

    public static void warn(
        Logger logger,
        Category category,
        String event,
        String message,
        Object... arguments
    ) {
        requireLoggerAndMessage(logger, message);
        logger.warn(prefix(category, event) + message, arguments);
    }

    public static void error(
        Logger logger,
        Category category,
        String event,
        String message,
        Throwable failure
    ) {
        requireLoggerAndMessage(logger, message);
        Objects.requireNonNull(failure, "failure");
        logger.error(prefix(category, event) + message, failure);
    }

    private static void requireLoggerAndMessage(Logger logger, String message) {
        Objects.requireNonNull(logger, "logger");
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("diagnostic message must not be blank");
        }
    }
}
