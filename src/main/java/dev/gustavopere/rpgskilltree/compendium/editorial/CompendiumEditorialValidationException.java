package dev.gustavopere.rpgskilltree.compendium.editorial;

public final class CompendiumEditorialValidationException extends IllegalArgumentException {
    public CompendiumEditorialValidationException(String message) {
        super(message);
    }

    public CompendiumEditorialValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
