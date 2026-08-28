package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Pure migration/bootstrap policy for the single canonical player-state envelope. */
public final class CanonicalPlayerStateBootstrap {
    private CanonicalPlayerStateBootstrap() {}

    public static CanonicalPlayerState bootstrap(
        Optional<CoreProgressionState> existingCore,
        Optional<ProgressionState> existingCompatibility,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(existingCore, "existingCore");
        Objects.requireNonNull(existingCompatibility, "existingCompatibility");
        Objects.requireNonNull(rules, "rules");

        ProgressionState compatibility = existingCompatibility.orElseGet(ProgressionState::empty);
        CoreProgressionState core;
        if (existingCore.isPresent()) {
            core = CoreProgressionBootstrap.resume(existingCore.orElseThrow(), rules);
        } else if (existingCompatibility.isPresent()) {
            core = CoreProgressionBootstrap.migrateDecodedLegacy(compatibility, rules);
        } else {
            core = CoreProgressionBootstrap.newPlayer(rules);
        }
        return new CanonicalPlayerState(core, compatibility);
    }

    public static CanonicalPlayerState resume(
        CanonicalPlayerState persisted,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(persisted, "persisted");
        Objects.requireNonNull(rules, "rules");
        CoreProgressionState resumed = CoreProgressionBootstrap.resume(
            persisted.coreProgression(),
            rules
        );
        return resumed == persisted.coreProgression()
            ? persisted
            : persisted.withCoreProgression(resumed);
    }
}
