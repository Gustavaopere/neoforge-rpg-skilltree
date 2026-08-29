package dev.gustavopere.rpgskilltree.compendium.client;

/**
 * Client-only list scope for personal Compendium navigation.
 *
 * <p>The scope changes which already-visible snapshot entries are listed. It never changes
 * discovery state and defines no persistence or network contract.</p>
 */
public enum CompendiumPersonalView {
    ALL,
    FAVORITES,
    RECENT
}
