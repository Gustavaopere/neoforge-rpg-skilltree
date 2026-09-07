package dev.gustavopere.rpgskilltree;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.rpgskilltree.core.UnitAttributeRankCostPolicy;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.compat.productivemetalworks.ProductiveMetalworksVersionContract;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.persistence.BlacksmithDataComponents;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.registry.BlacksmithItems;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import dev.gustavopere.rpgskilltree.runtime.ProgressionOwnerSyncRuntime;
import dev.gustavopere.rpgskilltree.runtime.RelevantPlayerCandidateRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.IntegrationAdapterRegistry;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrationAdapterRegistry;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.coldsweat.ColdSweatFrenzyBridge;
import dev.gustavopere.rpgskilltree.runtime.compat.create.CreateIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.create.CreateIntegrationState;
import dev.gustavopere.rpgskilltree.runtime.compat.create.CreateProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.create.CreateVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonAlchemyProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonIntegrationState;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonRitualProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.eidolon.EidolonVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0001A0020EpicFightHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0022RuntimeHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0041A0060EpicFightHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0041ScytheCommitHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0042ScytheKillHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.A0061A0080EpicFightHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightProgressionHooks;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.goety.GoetyIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.goety.GoetyIntegrationState;
import dev.gustavopere.rpgskilltree.runtime.compat.goety.GoetyProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.goety.GoetyVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.identity2.Identity2EcologyEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.identity2.MorphCategoryReloader;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSpellbookProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.malum.MalumIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.malum.MalumProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.malum.MalumVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.BattleMageIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.BattleMageIntegrationState;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.MineColoniesVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLifecycleEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageSpellProfileReloader;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyIntegrationState;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyLifecycleEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumDiscoveryEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumEditorialCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumEntityCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumFloraCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumInventoryEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumLootResourceReloader;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumWorldCatalogEvents;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumWorldCatalogReloader;
import dev.gustavopere.rpgskilltree.runtime.compendium.CompendiumWorldDiscoveryEvents;
import dev.gustavopere.rpgskilltree.runtime.conditions.ModConditions;
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
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyServerConfig;
import dev.gustavopere.rpgskilltree.runtime.events.A0041A0060ProjectileEvents;
import dev.gustavopere.rpgskilltree.runtime.events.A0081A0100CombatEvents;
import dev.gustavopere.rpgskilltree.runtime.events.ApothicBossBridgeEvents;
import dev.gustavopere.rpgskilltree.runtime.events.BossProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.CombatProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.EntityRewardEvents;
import dev.gustavopere.rpgskilltree.runtime.events.EntityScalingEvents;
import dev.gustavopere.rpgskilltree.runtime.events.ExplorationProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.MiningProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.PhysicalProjectileMasteryEvents;
import dev.gustavopere.rpgskilltree.runtime.events.PlayerProgressionEvents;
import dev.gustavopere.rpgskilltree.runtime.events.ProgressionOwnerSyncEvents;
import dev.gustavopere.rpgskilltree.runtime.events.RelevantPlayerCacheEvents;
import dev.gustavopere.rpgskilltree.runtime.loot.ModLootModifiers;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(RpgSkillTreeMod.MOD_ID)
public final class RpgSkillTreeMod {
    public static final String MOD_ID = "rpgskilltree";
    private static final Logger LOGGER = LoggerFactory.getLogger(RpgSkillTreeMod.class);

    public RpgSkillTreeMod(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER, ColonyEconomyServerConfig.SPEC);
        VolcanoesMod.initialize(modBus, container);
        AttributeRankCostPolicyCatalog.install(UnitAttributeRankCostPolicy.INSTANCE);
        ModAttachments.register(modBus);
        BlacksmithDataComponents.register(modBus);
        BlacksmithItems.register(modBus);
        ModConditions.register(modBus);
        ModLootModifiers.register(modBus);
        ModNetworking.register(modBus);
        ProgressionOwnerSyncRuntime.initialize();
        RelevantPlayerCandidateRuntime.initialize();
        NeoForge.EVENT_BUS.register(ProgressionOwnerSyncEvents.class);
        NeoForge.EVENT_BUS.register(PlayerProgressionEvents.class);
        NeoForge.EVENT_BUS.register(RelevantPlayerCacheEvents.class);
        // NodeRulesReloader.class and NodeEffectsReloader.class are retained as legacy source
        // compatibility markers only; their independent registrations are intentionally retired.
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
        NeoForge.EVENT_BUS.register(PhysicalProjectileMasteryEvents.class);
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
        RuntimeDiagnostics.info(LOGGER, Category.COMPAT, "optional_providers", "Optional integrations: {}", OptionalIntegrations.summary());
        IntegrationAdapterRegistry integrationAdapters = OptionalIntegrationAdapterRegistry.create(
            OptionalIntegrations::isLoaded,
            RpgSkillTreeMod::optionalAdapterDisabledReason
        );
        RuntimeDiagnostics.info(
            LOGGER,
            Category.COMPAT,
            "optional_adapter_registry",
            "Optional adapter registry: {}",
            integrationAdapters.summary()
        );
        ColdSweatFrenzyBridge.initializeDiagnostics();

        boolean productiveMetalworksProviderLoaded = OptionalIntegrations.isLoaded(
            OptionalIntegrations.Provider.PRODUCTIVE_METALWORKS
        );
        boolean productiveMetalworksLoaded = OptionalIntegrationAdapterRegistry.isActive(
            integrationAdapters,
            OptionalIntegrations.Provider.PRODUCTIVE_METALWORKS
        );
        String productiveMetalworksVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.PRODUCTIVE_METALWORKS);
        if (productiveMetalworksLoaded) {
            RuntimeDiagnostics.info(
                LOGGER,
                Category.COMPAT,
                "productivemetalworks_blacksmith_active",
                "Blacksmith Productive Metalworks adapter active: Productive Metalworks {}",
                productiveMetalworksVersion
            );
        } else if (productiveMetalworksProviderLoaded) {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.COMPAT,
                "productivemetalworks_blacksmith_disabled",
                "Blacksmith Productive Metalworks adapter fail-closed: expected {}, found {}",
                ProductiveMetalworksVersionContract.SUPPORTED_ARTIFACT_VERSION,
                productiveMetalworksVersion
            );
        }

        boolean createProviderLoaded = OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.CREATE);
        boolean createAdapterActive = OptionalIntegrationAdapterRegistry.isActive(
            integrationAdapters,
            OptionalIntegrations.Provider.CREATE
        );
        String createVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.CREATE);
        CreateIntegrationState createState = createAdapterActive
            ? CreateIntegrationBootstrap.install(
                true,
                createVersion,
                () -> NeoForge.EVENT_BUS.register(CreateProgressionEvents.class)
            )
            : CreateIntegrationBootstrap.evaluate(createProviderLoaded, createVersion);
        if (createState == CreateIntegrationState.ACTIVE) {
            RuntimeDiagnostics.info(
                LOGGER,
                Category.COMPAT,
                "create_mastery_active",
                "Create engineering Mastery integration active: Create {}",
                createVersion
            );
        } else if (createState != CreateIntegrationState.ABSENT_PROVIDER) {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.COMPAT,
                "create_mastery_disabled",
                "Create engineering Mastery integration disabled: state={}, expected={}, found={}",
                createState,
                CreateVersionContract.SUPPORTED_VERSION,
                createVersion
            );
        }

        boolean goetyProviderLoaded = OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.GOETY);
        boolean goetyAdapterActive = OptionalIntegrationAdapterRegistry.isActive(
            integrationAdapters,
            OptionalIntegrations.Provider.GOETY
        );
        String goetyVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.GOETY);
        GoetyIntegrationState goetyState = goetyAdapterActive
            ? GoetyIntegrationBootstrap.install(
                true,
                goetyVersion,
                () -> NeoForge.EVENT_BUS.register(GoetyProgressionEvents.class)
            )
            : GoetyIntegrationBootstrap.evaluate(goetyProviderLoaded, goetyVersion);
        if (goetyState == GoetyIntegrationState.ACTIVE) {
            RuntimeDiagnostics.info(
                LOGGER,
                Category.COMPAT,
                "goety_mastery_active",
                "Goety Mastery integration active: Goety {}",
                goetyVersion
            );
        } else if (goetyState != GoetyIntegrationState.ABSENT_PROVIDER) {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.COMPAT,
                "goety_mastery_disabled",
                "Goety Mastery integration disabled: state={}, expected={}, found={}",
                goetyState,
                GoetyVersionContract.SUPPORTED_VERSION,
                goetyVersion
            );
        }

        boolean mineColoniesLoaded = OptionalIntegrationAdapterRegistry.isActive(
            integrationAdapters,
            OptionalIntegrations.Provider.MINECOLONIES
        );
        boolean ironsSpellbooksProviderLoaded = OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS);
        boolean ironsSpellbooksLoaded = ironsSpellbooksProviderLoaded && OptionalIntegrationAdapterRegistry.isActive(
            integrationAdapters,
            OptionalIntegrations.Provider.IRONS_SPELLBOOKS
        );
        String mineColoniesVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.MINECOLONIES);
        String ironsSpellbooksVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS);

        MineColoniesEconomyIntegrationState economyState = MineColoniesEconomyIntegrationBootstrap.evaluate(
            mineColoniesLoaded,
            mineColoniesVersion
        );
        if (economyState == MineColoniesEconomyIntegrationState.ACTIVE) {
            modBus.addListener((FMLCommonSetupEvent event) -> event.enqueueWork(() -> {
                MineColoniesEconomyIntegrationState installedState = MineColoniesEconomyIntegrationBootstrap.install(
                    true,
                    mineColoniesVersion,
                    MineColoniesEconomyLifecycleEvents::install
                );
                if (installedState == MineColoniesEconomyIntegrationState.ACTIVE) {
                    RuntimeDiagnostics.info(
                        LOGGER,
                        Category.COMPAT,
                        "minecolonies_economy_active",
                        "MineColonies Economy integration active: MineColonies {}",
                        mineColoniesVersion
                    );
                } else {
                    RuntimeDiagnostics.warn(
                        LOGGER,
                        Category.COMPAT,
                        "minecolonies_economy_disabled",
                        "MineColonies Economy integration disabled during common setup: state={}, MineColonies={}",
                        installedState,
                        mineColoniesVersion
                    );
                }
            }));
        } else if (economyState != MineColoniesEconomyIntegrationState.ABSENT_PROVIDER) {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.COMPAT,
                "minecolonies_economy_disabled",
                "MineColonies Economy integration disabled before common setup: state={}, MineColonies={}",
                economyState,
                mineColoniesVersion
            );
        }

        BattleMageIntegrationState battleMageState = BattleMageIntegrationBootstrap.evaluate(
            mineColoniesLoaded,
            ironsSpellbooksLoaded,
            mineColoniesVersion,
            ironsSpellbooksVersion
        );
        if (battleMageState == BattleMageIntegrationState.ACTIVE) {
            battleMageState = BattleMageIntegrationBootstrap.install(
                true,
                true,
                mineColoniesVersion,
                ironsSpellbooksVersion,
                () -> {
                    MineColoniesBattleMageRegistration.register(modBus);
                    NeoForge.EVENT_BUS.register(BattleMageSpellProfileReloader.class);
                    NeoForge.EVENT_BUS.register(BattleMageLifecycleEvents.class);
                }
            );
        }
        if (battleMageState == BattleMageIntegrationState.ACTIVE) {
            RuntimeDiagnostics.info(
                LOGGER,
                Category.COMPAT,
                "minecolonies_battle_mage_active",
                "MineColonies Battle Mage integration active: MineColonies {}, Iron's {}",
                mineColoniesVersion,
                ironsSpellbooksVersion
            );
        } else if (battleMageState != BattleMageIntegrationState.ABSENT_PROVIDER) {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.COMPAT,
                "minecolonies_battle_mage_disabled",
                "MineColonies Battle Mage integration disabled: state={}, MineColonies={}, Iron's={}",
                battleMageState,
                mineColoniesVersion,
                ironsSpellbooksVersion
            );
        }

        if (ironsSpellbooksLoaded) NeoForge.EVENT_BUS.register(IronsSpellbookProgressionEvents.class);
        if (OptionalIntegrationAdapterRegistry.isActive(integrationAdapters, OptionalIntegrations.Provider.ARS_NOUVEAU)) {
            NeoForge.EVENT_BUS.register(ArsNouveauProgressionEvents.class);
        }
        MalumIntegrationBootstrap.install(
            OptionalIntegrationAdapterRegistry.isActive(integrationAdapters, OptionalIntegrations.Provider.MALUM),
            OptionalIntegrations.version(OptionalIntegrations.Provider.MALUM),
            () -> NeoForge.EVENT_BUS.register(MalumProgressionEvents.class)
        );
        boolean eidolonProviderLoaded = OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EIDOLON);
        boolean eidolonAdapterActive = OptionalIntegrationAdapterRegistry.isActive(
            integrationAdapters,
            OptionalIntegrations.Provider.EIDOLON
        );
        String eidolonVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.EIDOLON);
        EidolonIntegrationState eidolonState = eidolonAdapterActive
            ? EidolonIntegrationBootstrap.install(
                true,
                eidolonVersion,
                () -> {
                    NeoForge.EVENT_BUS.register(EidolonRitualProgressionEvents.class);
                    NeoForge.EVENT_BUS.register(EidolonAlchemyProgressionEvents.class);
                }
            )
            : EidolonIntegrationBootstrap.evaluate(eidolonProviderLoaded, eidolonVersion);
        if (eidolonState == EidolonIntegrationState.ACTIVE) {
            RuntimeDiagnostics.info(
                LOGGER,
                Category.COMPAT,
                "eidolon_mastery_active",
                "Eidolon Mastery integration active: Eidolon: Repraised {}",
                eidolonVersion
            );
        } else if (eidolonState != EidolonIntegrationState.ABSENT_PROVIDER) {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.COMPAT,
                "eidolon_mastery_disabled",
                "Eidolon Mastery integration disabled: state={}, expected={}, found={}",
                eidolonState,
                EidolonVersionContract.SUPPORTED_VERSION,
                eidolonVersion
            );
        }
        if (OptionalIntegrationAdapterRegistry.isActive(integrationAdapters, OptionalIntegrations.Provider.IDENTITY2)) {
            NeoForge.EVENT_BUS.register(Identity2EcologyEvents.class);
        }
        if (OptionalIntegrationAdapterRegistry.isActive(integrationAdapters, OptionalIntegrations.Provider.EPIC_FIGHT)) {
            EpicFightProgressionHooks.register();
            A0001A0020EpicFightHooks.register();
            A0022RuntimeHooks.register();
            A0042ScytheKillHooks.register();
            A0041A0060EpicFightHooks.register();
            A0041ScytheCommitHooks.register();
            A0061A0080EpicFightHooks.register();
            NeoForge.EVENT_BUS.register(A0001A0020EpicFightHooks.class);
            NeoForge.EVENT_BUS.register(A0022RuntimeHooks.class);
            NeoForge.EVENT_BUS.register(A0042ScytheKillHooks.class);
            NeoForge.EVENT_BUS.register(A0041A0060EpicFightHooks.class);
            NeoForge.EVENT_BUS.register(A0061A0080EpicFightHooks.class);
        } else if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT)) {
            String version = OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT);
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

    private static String optionalAdapterDisabledReason(OptionalIntegrations.Provider provider) {
        return optionalAdapterDisabledReason(provider, OptionalIntegrations.version(provider));
    }

    static String optionalAdapterDisabledReason(OptionalIntegrations.Provider provider, String version) {
        return switch (provider) {
            case EPIC_FIGHT -> EpicFightVersionContract.supportsVersion(version) ? "" : "unsupported_version";
            case COLD_SWEAT -> ColdSweatFrenzyBridge.supportsVersion(version) ? "" : "unsupported_version";
            case CREATE -> CreateVersionContract.supportsVersion(version) ? "" : "unsupported_version";
            case GOETY -> GoetyVersionContract.supportsVersion(version) ? "" : "unsupported_version";
            case MALUM -> MalumVersionContract.supports(version) ? "" : "unsupported_version";
            case EIDOLON -> EidolonVersionContract.supports(version) ? "" : "unsupported_version";
            case MINECOLONIES -> (MineColoniesVersionContract.supports(version)
                || MineColoniesEconomyVersionContract.supports(version)) ? "" : "unsupported_version";
            case PRODUCTIVE_METALWORKS -> ProductiveMetalworksVersionContract.supports(version) ? "" : "unsupported_version";
            default -> "";
        };
    }
}
