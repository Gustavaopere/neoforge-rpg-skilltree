package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Persisted single player attachment during the legacy-to-Core transition.
 *
 * <p>The compatibility section can exist before uncapped Core rules are loaded.
 * The explicit legacy-source bit preserves the difference between a genuinely new
 * player and an old save whose legacy progression happens to be empty.</p>
 */
public final class CanonicalPlayerAttachmentData {
    private static final CanonicalPlayerAttachmentData EMPTY = new CanonicalPlayerAttachmentData(
        CoreProgressionAttachmentData.uninitialized(),
        ProgressionState.empty(),
        false
    );

    private final CoreProgressionAttachmentData coreProgression;
    private final ProgressionState compatibilityProgression;
    private final boolean legacyMigrationSource;

    public CanonicalPlayerAttachmentData(
        CoreProgressionAttachmentData coreProgression,
        ProgressionState compatibilityProgression,
        boolean legacyMigrationSource
    ) {
        this.coreProgression = Objects.requireNonNull(coreProgression, "coreProgression");
        this.compatibilityProgression = Objects.requireNonNull(
            compatibilityProgression,
            "compatibilityProgression"
        );
        this.legacyMigrationSource = legacyMigrationSource;
    }

    public static CanonicalPlayerAttachmentData empty() {
        return EMPTY;
    }

    public static CanonicalPlayerAttachmentData fromMigrationInputs(
        Optional<CoreProgressionAttachmentData> oldCore,
        Optional<ProgressionState> oldCompatibility
    ) {
        Objects.requireNonNull(oldCore, "oldCore");
        Objects.requireNonNull(oldCompatibility, "oldCompatibility");
        return new CanonicalPlayerAttachmentData(
            oldCore.orElseGet(CoreProgressionAttachmentData::uninitialized),
            oldCompatibility.orElseGet(ProgressionState::empty),
            oldCompatibility.isPresent()
        );
    }

    public CoreProgressionAttachmentData coreProgression() {
        return coreProgression;
    }

    public ProgressionState compatibilityProgression() {
        return compatibilityProgression;
    }

    public boolean hasLegacyMigrationSource() {
        return legacyMigrationSource;
    }

    public CanonicalPlayerAttachmentData withCompatibilityProgression(ProgressionState next) {
        Objects.requireNonNull(next, "next");
        byte[] currentBytes = ProgressionStateCodec.encode(compatibilityProgression);
        byte[] nextBytes = ProgressionStateCodec.encode(next);
        if (Arrays.equals(currentBytes, nextBytes)) return this;

        boolean nextLegacyMigrationSource = legacyMigrationSource || !coreProgression.isInitialized();
        return new CanonicalPlayerAttachmentData(
            coreProgression,
            next,
            nextLegacyMigrationSource
        );
    }

    public CanonicalPlayerAttachmentData withCoreProgression(CoreProgressionAttachmentData next) {
        Objects.requireNonNull(next, "next");
        if (next == coreProgression) return this;
        return new CanonicalPlayerAttachmentData(next, compatibilityProgression, legacyMigrationSource);
    }

    /**
     * Initializes or validates the Core section once authoritative rules are available.
     * No rule snapshot is required merely to persist compatibility progression.
     */
    public CanonicalPlayerAttachmentData initializeCore(ProgressionRulesSnapshot rules) {
        Objects.requireNonNull(rules, "rules");
        if (coreProgression.isInitialized()) {
            CoreProgressionState existing = coreProgression.state().orElseThrow();
            CoreProgressionState resumed = CoreProgressionBootstrap.resume(existing, rules);
            return resumed == existing
                ? this
                : withCoreProgression(CoreProgressionAttachmentData.initialized(resumed));
        }

        CoreProgressionState initialized = legacyMigrationSource
            ? CoreProgressionBootstrap.migrateDecodedLegacy(compatibilityProgression, rules)
            : CoreProgressionBootstrap.newPlayer(rules);
        return withCoreProgression(CoreProgressionAttachmentData.initialized(initialized));
    }

    public Optional<CanonicalPlayerState> initializedState() {
        return coreProgression.state().map(core ->
            new CanonicalPlayerState(core, compatibilityProgression));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CanonicalPlayerAttachmentData that)) return false;
        return legacyMigrationSource == that.legacyMigrationSource
            && Arrays.equals(
                CoreProgressionAttachmentDataCodec.encode(coreProgression),
                CoreProgressionAttachmentDataCodec.encode(that.coreProgression)
            )
            && Arrays.equals(
                ProgressionStateCodec.encode(compatibilityProgression),
                ProgressionStateCodec.encode(that.compatibilityProgression)
            );
    }

    @Override
    public int hashCode() {
        int result = Boolean.hashCode(legacyMigrationSource);
        result = 31 * result + Arrays.hashCode(
            CoreProgressionAttachmentDataCodec.encode(coreProgression));
        result = 31 * result + Arrays.hashCode(
            ProgressionStateCodec.encode(compatibilityProgression));
        return result;
    }
}
