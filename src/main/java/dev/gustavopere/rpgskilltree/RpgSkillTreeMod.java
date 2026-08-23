package dev.gustavopere.rpgskilltree;

import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightProgressionHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.identity2.MorphCategoryReloader;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSpellbookProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.data.BossRewardReloader;
import dev.gustavopere.rpgskilltree.runtime.data.ClassChoiceRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.ClassRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectsReloader;
import dev.gustavopere.rpgskilltree.runtime.data.NodeRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.events.ApothicBossBridgeEvents;
import dev.gustavopere.rpgskilltree.runtime.events.BossProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.CombatProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.ExplorationProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.MiningProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.PlayerProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(RpgSkillTreeMod.MOD_ID)
public final class RpgSkillTreeMod {
    public static final String MOD_ID = "rpgskilltree";

    public RpgSkillTreeMod(IEventBus modBus) {
        ModAttachments.register(modBus);
        ModNetworking.register(modBus);
        NeoForge.EVENT_BUS.register(PlayerProgressionEvents.class);
        NeoForge.EVENT_BUS.register(NodeRulesReloader.class);
        NeoForge.EVENT_BUS.register(ClassRulesReloader.class);
        NeoForge.EVENT_BUS.register(ClassChoiceRulesReloader.class);
        NeoForge.EVENT_BUS.register(MorphCategoryReloader.class);
        NeoForge.EVENT_BUS.register(NodeEffectsReloader.class);
        NeoForge.EVENT_BUS.register(BossRewardReloader.class);
        NeoForge.EVENT_BUS.register(ApothicBossBridgeEvents.class);
        NeoForge.EVENT_BUS.register(BossProgressionEvents.class);
        NeoForge.EVENT_BUS.register(CombatProgressionEvents.class);
        NeoForge.EVENT_BUS.register(ExplorationProgressionEvents.class);
        NeoForge.EVENT_BUS.register(MiningProgressionEvents.class);

        if (ModList.get().isLoaded("irons_spellbooks")) {
            NeoForge.EVENT_BUS.register(IronsSpellbookProgressionEvents.class);
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            NeoForge.EVENT_BUS.register(ArsNouveauProgressionEvents.class);
        }
        if (ModList.get().isLoaded("epicfight")) {
            EpicFightProgressionHooks.register();
        }
    }
}
