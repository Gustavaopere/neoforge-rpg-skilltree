package dev.gustavopere.rpgskilltree.core;

import java.util.Optional;

@FunctionalInterface
public interface XpPolicy {
    Optional<CharacterXpAward> resolve(SemanticAction action);
}
