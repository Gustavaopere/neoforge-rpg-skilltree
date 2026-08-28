package dev.gustavopere.rpgskilltree;

import dev.gustavopere.rpgskilltree.core.UnitAttributeRankCostPolicy;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonAlchemyProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonRitualProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightProgressionHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.goety.GoetyProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.identity2.Identity2EcologyEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.identity2.MorphCategoryReloader;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSpellbookProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.malum.MalumProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumDiscoveryEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumEntityCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumInventoryEvents;
import dev.gustavopere.rpgskilltree.runtime.data.ArchetypeReloader;
import dev.gustavopere.rpgskilltree.runtime.data.AttributeRankCostPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.BossRewardReloader;
import dev.gustavopere.rpgskilltree.runtime.data.CanonicalProviderBindingReloader;
import dev.gustavopere.rpgskilltree.runtime.data.ClassChoiceRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.ClassRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.CoreProgressionRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectsReloader;
import dev.gustavopere.rpgskilltree.runtime.data.NodeRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.SpecializationReloader;
import dev.gustavopere.rpgskilltree.runtime.data.TreeArchitectureReloader;
import dev.gustavopere.rpgskilltree.runtime.data.TreeUnlockReloader;
import dev.gustavopere.rpgskilltree.runtime.events.ApothicBossBridgeEvents;
import dev.gustavopere.rpgskilltree.runtime.events.BossProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.CombatProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.EntityScalingEvents;
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
        AttributeRankCostPolicyCatalog.install(UnitAttributeRankCostPolicy.INSTANCE);
        ModAttachments.register(modBus);
        ModNetworking.register(modBus);
        NeoForge.EVENT_BUS.register(PlayerProgressionEvents.class);
        NeoForge.EVENT_BUS.register(NodeRulesReloader.class);
        NeoForge.EVENT_BUS.register(TreeArchitectureReloader.class);
        NeoForge.EVENT_BUS.register(TreeUnlockReloader.class);
        NeoForge.EVENT_BUS.register(ClassRulesReloader.class);
        NeoForge.EVENT_BUS.register(ClassChoiceRulesReloader.class);
        NeoForge.EVENT_BUS.register(ArchetypeReloader.class);
        NeoForge.EVENT_BUS.register(SpecializationReloader.class);
        NeoForge.EVENT_BUS.register(MorphCategoryReloader.class);
        NeoForge.EVENT_BUS.register(NodeEffectsReloader.class);
        NeoForge.EVENT_BUS.register(BossRewardReloader.class);
        NeoForge.EVENT_BUS.register(CoreProgressionRulesReloader.class);
        NeoForge.EVENT_BUS.register(CanonicalProviderBindingReloader.class);
        NeoForge.EVENT_BUS.register(EntityScalingEvents.class);
        NeoForge.EVENT_BUS.register(ApothicBossBridgeEvents.class);
        NeoForge.EVENT_BUS.register(BossProgressionEvents.class);
        NeoForge.EVENT_BUS.register(CombatProgressionEvents.class);
        NeoForge.EVENT_BUS.register(ExplorationProgressionEvents.class);
        NeoForge.EVENT_BUS.register(MiningProgressionEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumEntityCatalogEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumInventoryEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumDiscoveryEvents.class);

        if (ModList.get().isLoaded("irons_spellbooks")) {
            NeoForge.EVENT_BUS.register(IronsSpellbookProgressionEvents.class);
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            NeoForge.EVENT_BUS.register(ArsNouveauProgressionEvents.class);
        }
        if (ModList.get().isLoaded("goety")) {
            NeoForge.EVENT_BUS.register(GoetyProgressionEvents.class);
        }
        if (ModList.get().isLoaded("malum")) {
            NeoForge.EVENT_BUS.register(MalumProgressionEvents.class);
        }
        if (ModList.get().isLoaded("eidolon")) {
            NeoForge.EVENT_BUS.register(EidolonRitualProgressionEvents.class);
            NeoForge.EVENT_BUS.register(EidolonAlchemyProgressionEvents.class);
        }
        if (ModList.get().isLoaded("identity2")) {
            NeoForge.EVENT_BUS.register(Identity2EcologyEvents.class);
        }
        if (ModList.get().isLoaded("epicfight")) {
            EpicFightProgressionHooks.register();
        }
    }
}
