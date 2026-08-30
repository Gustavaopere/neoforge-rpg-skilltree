package dev.gustavopere.rpgskilltree;

import dev.gustavopere.rpgskilltree.core.UnitAttributeRankCostPolicy;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import dev.gustavopere.rpgskilltree.runtime.ProgressionOwnerSyncRuntime;
import dev.gustavopere.rpgskilltree.runtime.RelevantPlayerCandidateRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.coldsweat.ColdSweatFrenzyBridge;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonAlchemyProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonRitualProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0001A0020EpicFightHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0022RuntimeHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0041A0060EpicFightHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0042ScytheKillHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0061A0080EpicFightHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightProgressionHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.goety.GoetyProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.identity2.Identity2EcologyEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.identity2.MorphCategoryReloader;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSpellbookProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.malum.MalumProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumDiscoveryEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumEditorialCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumEntityCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumFloraCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumInventoryEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumLootResourceReloader;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumWorldCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumWorldCatalogReloader;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumWorldDiscoveryEvents;
import dev.gustavopere.rpgskilltree.runtime.data.ArchetypeReloader;
import dev.gustavopere.rpgskilltree.runtime.data.AttributeRankCostPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.BossRewardReloader;
import dev.gustavopere.rpgskilltree.runtime.data.CanonicalProviderBindingReloader;
import dev.gustavopere.rpgskilltree.runtime.data.ClassChoiceRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.ClassRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.CoreProgressionRulesReloader;
import dev.gustavopere.rpgskilltree.runtime.data.SkillTreeDataReloader;
import dev.gustavopere.rpgskilltree.runtime.data.SpecializationReloader;
import dev.gustavopere.rpgskilltree.runtime.data.TreeArchitectureReloader;
import dev.gustavopere.rpgskilltree.runtime.data.TreeUnlockReloader;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import dev.gustavopere.rpgskilltree.runtime.events.A0041A0060ProjectileEvents;
import dev.gustavopere.rpgskilltree.runtime.events.A0081A0100CombatEvents;
import dev.gustavopere.rpgskilltree.runtime.events.ApothicBossBridgeEvents;
import dev.gustavopere.rpgskilltree.runtime.events.BossProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.CombatProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.EntityRewardEvents;
import dev.gustavopere.rpgskilltree.runtime.events.EntityScalingEvents;
import dev.gustavopere.rpgskilltree.runtime.events.ExplorationProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.MiningProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.PlayerProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.ProgressionOwnerSyncEvents;
import dev.gustavopere.rpgskilltree.runtime.events.RelevantPlayerCacheEvents;
import dev.gustavopere.rpgskilltree.runtime.loot.ModLootModifiers;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(RpgSkillTreeMod.MOD_ID)
public final class RpgSkillTreeMod {
    public static final String MOD_ID = "rpgskilltree";
    private static final Logger LOGGER = LoggerFactory.getLogger(RpgSkillTreeMod.class);

    public RpgSkillTreeMod(IEventBus modBus) {
        AttributeRankCostPolicyCatalog.install(UnitAttributeRankCostPolicy.INSTANCE);
        ModAttachments.register(modBus);
        ModLootModifiers.register(modBus);
        ModNetworking.register(modBus);
        ProgressionOwnerSyncRuntime.initialize();
        RelevantPlayerCandidateRuntime.initialize();
        NeoForge.EVENT_BUS.register(ProgressionOwnerSyncEvents.class);
        NeoForge.EVENT_BUS.register(PlayerProgressionEvents.class);
        NeoForge.EVENT_BUS.register(RelevantPlayerCacheEvents.class);
        // NodeRulesReloader.class and NodeEffectsReloader.class are retained as legacy source
        // compatibility only; their independent registrations are intentionally retired.
        NeoForge.EVENT_BUS.register(SkillTreeDataReloader.class);
        NeoForge.EVENT_BUS.register(TreeArchitectureReloader.class);
        NeoForge.EVENT_BUS.register(TreeUnlockReloader.class);
        NeoForge.EVENT_BUS.register(ClassRulesReloader.class);
        NeoForge.EVENT_BUS.register(ClassChoiceRulesReloader.class);
        NeoForge.EVENT_BUS.register(ArchetypeReloader.class);
        NeoForge.EVENT_BUS.register(SpecializationReloader.class);
        NeoForge.EVENT_BUS.register(MorphCategoryReloader.class);
        NeoForge.EVENT_BUS.register(BossRewardReloader.class);
        NeoForge.EVENT_BUS.register(CoreProgressionRulesReloader.class);
        NeoForge.EVENT_BUS.register(CanonicalProviderBindingReloader.class);
        NeoForge.EVENT_BUS.register(EntityScalingEvents.class);
        NeoForge.EVENT_BUS.register(EntityRewardEvents.class);
        NeoForge.EVENT_BUS.register(ApothicBossBridgeEvents.class);
        NeoForge.EVENT_BUS.register(BossProgressionEvents.class);
        NeoForge.EVENT_BUS.register(CombatProgressionEvents.class);
        NeoForge.EVENT_BUS.register(ExplorationProgressionEvents.class);
        NeoForge.EVENT_BUS.register(MiningProgressionEvents.class);
        NeoForge.EVENT_BUS.register(A0041A0060ProjectileEvents.class);
        NeoForge.EVENT_BUS.register(A0081A0100CombatEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumEntityCatalogEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumFloraCatalogEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumInventoryEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumLootResourceReloader.class);
        NeoForge.EVENT_BUS.register(CompendiumWorldCatalogEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumEditorialCatalogEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumWorldCatalogReloader.class);
        NeoForge.EVENT_BUS.register(CompendiumWorldDiscoveryEvents.class);
        NeoForge.EVENT_BUS.register(CompendiumDiscoveryEvents.class);

        RuntimeDiagnostics.info(
            LOGGER,
            Category.COMPAT,
            "optional_providers",
            "Optional integrations: {}",
            OptionalIntegrations.summary()
        );
        ColdSweatFrenzyBridge.initializeDiagnostics();

        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)) {
            NeoForge.EVENT_BUS.register(IronsSpellbookProgressionEvents.class);
        }
        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.ARS_NOUVEAU)) {
            NeoForge.EVENT_BUS.register(ArsNouveauProgressionEvents.class);
        }
        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.GOETY)) {
            NeoForge.EVENT_BUS.register(GoetyProgressionEvents.class);
        }
        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.MALUM)) {
            NeoForge.EVENT_BUS.register(MalumProgressionEvents.class);
        }
        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EIDOLON)) {
            NeoForge.EVENT_BUS.register(EidolonRitualProgressionEvents.class);
            NeoForge.EVENT_BUS.register(EidolonAlchemyProgressionEvents.class);
        }
        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IDENTITY2)) {
            NeoForge.EVENT_BUS.register(Identity2EcologyEvents.class);
        }
        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT)) {
            String version = OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT);
            if (EpicFightVersionContract.supportsVersion(version)) {
                EpicFightProgressionHooks.register();
                A0001A0020EpicFightHooks.register();
                A0022RuntimeHooks.register();
                A0042ScytheKillHooks.register();
                A0041A0060EpicFightHooks.register();
                A0061A0080EpicFightHooks.register();
                NeoForge.EVENT_BUS.register(A0001A0020EpicFightHooks.class);
                NeoForge.EVENT_BUS.register(A0022RuntimeHooks.class);
                NeoForge.EVENT_BUS.register(A0042ScytheKillHooks.class);
                NeoForge.EVENT_BUS.register(A0041A0060EpicFightHooks.class);
                NeoForge.EVENT_BUS.register(A0061A0080EpicFightHooks.class);
            } else {
                RuntimeDiagnostics.warn(
                    LOGGER,
                    Category.COMPAT,
                    "epicfight_version_unsupported",
                    "A0001-A0080 Epic Fight integration disabled: expected {}, found {}",
                    EpicFightVersionContract.SUPPORTED_VERSION,
                    version
                );
            }
        }
    }
}
