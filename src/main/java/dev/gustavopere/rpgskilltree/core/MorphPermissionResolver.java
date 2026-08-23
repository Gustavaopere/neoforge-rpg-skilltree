package dev.gustavopere.rpgskilltree.core;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/** Derives morph capabilities exclusively from the player's live class/tree state. */
public final class MorphPermissionResolver {
    private static final String DRUID_CLASS = "druid";
    private static final String METAMORPH_CLASS = "metamorph";

    private static final String DRUID_AQUATIC_NODE = "rpgskilltree:druid/aquatic_shape";
    private static final String DRUID_FLYING_NODE = "rpgskilltree:druid/winged_shape";
    private static final String DRUID_MAGICAL_NODE = "rpgskilltree:druid/primal_spirit";
    private static final String METAMORPH_MONSTER_NODE = "rpgskilltree:metamorph/monstrous_flesh";
    private static final String METAMORPH_ABERRATION_NODE = "rpgskilltree:metamorph/aberrant_form";

    private MorphPermissionResolver() {}

    public static Set<MorphPermission> resolve(ProgressionState state) {
        Objects.requireNonNull(state);
        EnumSet<MorphPermission> permissions = EnumSet.noneOf(MorphPermission.class);

        if (state.classProgression().isUnlocked(DRUID_CLASS)) {
            permissions.add(MorphPermission.DRUID_LAND);
            if (state.passiveNodes().learned(DRUID_AQUATIC_NODE)) permissions.add(MorphPermission.DRUID_AQUATIC);
            if (state.passiveNodes().learned(DRUID_FLYING_NODE)) permissions.add(MorphPermission.DRUID_FLYING);
            if (state.passiveNodes().learned(DRUID_MAGICAL_NODE)) permissions.add(MorphPermission.DRUID_MAGICAL_NATURAL);
        }

        if (state.classProgression().isUnlocked(METAMORPH_CLASS)) {
            permissions.add(MorphPermission.METAMORPH_HUMANOID);
            if (state.passiveNodes().learned(METAMORPH_MONSTER_NODE)) permissions.add(MorphPermission.METAMORPH_MONSTER);
            if (state.passiveNodes().learned(METAMORPH_ABERRATION_NODE)) permissions.add(MorphPermission.METAMORPH_ABERRATION);
        }

        return Set.copyOf(permissions);
    }
}
