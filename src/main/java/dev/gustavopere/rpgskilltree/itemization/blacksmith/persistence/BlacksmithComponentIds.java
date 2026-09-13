package dev.gustavopere.rpgskilltree.itemization.blacksmith.persistence;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import net.minecraft.resources.ResourceLocation;

/** Stable resource identifiers for Blacksmith data components without initializing NeoForge registries. */
public final class BlacksmithComponentIds {
    public static final ResourceLocation PART_STATE =
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "blacksmith_part_state");
    public static final ResourceLocation COMPOSITION =
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "blacksmith_composition");

    private BlacksmithComponentIds() {}
}
