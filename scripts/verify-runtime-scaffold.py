#!/usr/bin/env python3
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
errors = []

def require_file(path: str):
    p = ROOT / path
    if not p.is_file():
        errors.append(f"missing file: {path}")
        return None
    return p.read_text(encoding='utf-8')

def require_contains(path: str, *needles: str):
    text = require_file(path)
    if text is None:
        return
    for needle in needles:
        if needle not in text:
            errors.append(f"{path}: missing {needle!r}")

require_contains('gradle.properties',
                 'minecraft_version=1.21.1',
                 'neo_version=21.1.248',
                 'mod_id=rpgskilltree')
require_contains('build.gradle',
                 "id 'net.neoforged.gradle.userdev' version '7.1.26'",
                 'JavaLanguageVersion.of(21)',
                 'net.neoforged:neoforge:${neo_version}')
require_contains('src/main/resources/META-INF/neoforge.mods.toml',
                 'modId="${mod_id}"',
                 'modId="neoforge"',
                 'modId="minecraft"')
require_contains('src/main/resources/pack.mcmeta', 'pack_format')
require_file('settings.gradle')

require_contains('src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java',
                 '@Mod(RpgSkillTreeMod.MOD_ID)',
                 'ModAttachments.register(modBus)',
                 'ModNetworking.register(modBus)',
                 'NeoForge.EVENT_BUS.register(PlayerProgressionEvents.class)',
                 'NeoForge.EVENT_BUS.register(BossProgressionEvents.class)')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java',
                 'NeoForgeRegistries.ATTACHMENT_TYPES',
                 'AttachmentType.builder(ProgressionState::empty)',
                 '.copyOnDeath()',
                 'ProgressionAttachmentSerializer.INSTANCE')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionAttachmentSerializer.java',
                 'implements IAttachmentSerializer<ByteArrayTag, ProgressionState>',
                 'ProgressionStateCodec.encode',
                 'ProgressionStateCodec.decode')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java',
                 'player.getData(ModAttachments.PROGRESSION)',
                 'player.setData(ModAttachments.PROGRESSION, state)')

require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ProgressionSyncPayload.java',
                 'implements CustomPacketPayload',
                 'ByteBufCodecs.byteArray(',
                 'ProgressionStateCodec.encode')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java',
                 'RegisterPayloadHandlersEvent',
                 'playToClient(',
                 'PacketDistributor.sendToPlayer(player',
                 'ProgressionStateCodec.encode(state)')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientProgressionState.java',
                 'ProgressionStateCodec.decode(payload.snapshot())',
                 'AtomicReference<ProgressionState>')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java',
                 'PlayerEvent.PlayerLoggedInEvent',
                 'PlayerEvent.PlayerRespawnEvent',
                 'ModNetworking.syncToOwner')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java',
                 'ModNetworking.syncToOwner(player, state)')

require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/BossRewardKeyResolver.java',
                 'TagKey.create(',
                 'Registries.ENTITY_TYPE',
                 'BuiltInRegistries.ENTITY_TYPE.getKey',
                 'BossRewardKeyPolicy.resolve')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/events/BossProgressionEvents.java',
                 'LivingDeathEvent',
                 'BossRewardKeyResolver.isBoss',
                 'event.getSource().getEntity() instanceof ServerPlayer',
                 'PlayerProgressionRuntime.creditBoss')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/events/ApothicBossBridgeEvents.java',
                 'EntityJoinLevelEvent',
                 'EventPriority.LOW',
                 'apoth.miniboss',
                 'CAPTURED_APOTH_ELITE_ID')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/BossRewardKeyResolver.java',
                 'apoth.boss',
                 'apoth.boss.rarity',
                 'ApothicBossRewardKeyPolicy.elite',
                 'ApothicBossRewardKeyPolicy.invader')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java',
                 'ApothicBossBridgeEvents.class')

require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/events/CombatProgressionEvents.java',
                 'LivingDeathEvent',
                 'instanceof Enemy',
                 'GameplayXpPolicy.combatKill',
                 'PlayerProgressionRuntime.applyXp')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java',
                 'CombatProgressionEvents.class')

require_contains('src/main/resources/data/rpgskilltree/tags/entity_type/bosses.json',
                 'minecraft:ender_dragon',
                 'minecraft:wither',
                 'cataclysm:ignis',
                 'cataclysm:scylla',
                 'cataclysm:maledictus')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java',
                 'ProgressionService.creditBoss',
                 'ProgressionService.applyXp')


require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/NodeRulesReloader.java',
                 'SimpleJsonResourceReloadListener',
                 'super(GSON, "node_rules")',
                 'AddReloadListenerEvent',
                 'TreeRuleCatalog.replace',
                 'requiredClasses',
                 'requiredMastery',
                 'requiredSpecializations',
                 'requiredClassChoices',
                 'grantsSpecialization')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/TreeRuleCatalog.java',
                 'NodePurchaseDefinition',
                 'SkillGraph',
                 'definition(',
                 'requirement(',
                 'NodeAccessRequirement',
                 'graph()',
                 'specializationGrants()',
                 'requirements()')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/ClassChoiceRulesReloader.java',
                 'SimpleJsonResourceReloadListener',
                 'super(GSON, "class_choices")',
                 'ClassChoiceCatalog.replace')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/ClassChoiceCatalog.java',
                 'ClassChoiceDefinition',
                 'definition(')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/ClassRulesReloader.java',
                 'SimpleJsonResourceReloadListener',
                 'super(GSON, "classes")',
                 'ClassRuleCatalog.replace')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/ClassRuleCatalog.java',
                 'ClassUnlockDefinition',
                 'definitions()')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/NodeEffectsReloader.java',
                 'SimpleJsonResourceReloadListener',
                 'super(GSON, "node_effects")',
                 'NodeEffectCatalog.replace')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/data/NodeEffectCatalog.java',
                 'NodeAttributeEffect',
                 'attributeEffects()')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/effects/AttributeNodeEffectRuntime.java',
                 'NodeEffectResolver.resolveAttributes',
                 'BuiltInRegistries.ATTRIBUTE',
                 'addOrUpdateTransientModifier',
                 'removeModifier')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java',
                 'AttributeNodeEffectRuntime.refresh')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/PurchaseNodePayload.java',
                 'implements CustomPacketPayload',
                 'ResourceLocation.STREAM_CODEC',
                 'PlayerProgressionRuntime.purchaseNode')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/RespecNodePayload.java',
                 'implements CustomPacketPayload',
                 'ResourceLocation.STREAM_CODEC',
                 'PlayerProgressionRuntime.respecNode')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/SelectClassChoicePayload.java',
                 'implements CustomPacketPayload',
                 'PlayerProgressionRuntime.selectClassChoice')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ClearClassChoicePayload.java',
                 'implements CustomPacketPayload',
                 'PlayerProgressionRuntime.clearClassChoice')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/UnlockClassPayload.java',
                 'implements CustomPacketPayload',
                 'ResourceLocation.STREAM_CODEC',
                 'PlayerProgressionRuntime.unlockPaidClass')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java',
                 'playToServer(',
                 'PurchaseNodePayload.TYPE',
                 'PurchaseNodePayload.STREAM_CODEC',
                 'RespecNodePayload.TYPE',
                 'RespecNodePayload.STREAM_CODEC',
                 'UnlockClassPayload.TYPE',
                 'UnlockClassPayload.STREAM_CODEC',
                 'SelectClassChoicePayload.TYPE',
                 'ClearClassChoicePayload.TYPE')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java',
                 'ProgressionService.purchaseNode',
                 'ProgressionService.respecNode',
                 'reconcileDerivedState',
                 'ProgressionService.reconcileAutomaticClasses',
                 'ProgressionService.reconcileInvalidNodes',
                 'AttributeNodeEffectRuntime.refresh',
                 'ClassRuleCatalog.definitions',
                 'TreeRuleCatalog.definition',
                 'TreeRuleCatalog.requirement',
                 'ProgressionService.reconcileNodeSpecializations',
                 'NodeAccessResolver.satisfied',
                 'TreeRuleCatalog.definitions',
                 'TreeRuleCatalog.graph',
                 'unlockPaidClass',
                 'ClassRuleCatalog.definition',
                 'selectClassChoice',
                 'clearClassChoice',
                 'ClassChoiceCatalog.definition')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java',
                 'NodeRulesReloader.class',
                 'ClassRulesReloader.class',
                 'ClassChoiceRulesReloader.class',
                 'NodeEffectsReloader.class')




require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/events/MiningProgressionEvents.java',
                 'BlockEvent.BreakEvent',
                 'Tags.Blocks.ORES',
                 'Tags.Blocks.ORES_DIAMOND',
                 'GameplayXpPolicy.oreMined',
                 'PlayerProgressionRuntime.applyXp')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java',
                 'MiningProgressionEvents.class')

require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/events/ExplorationProgressionEvents.java',
                 'PlayerTickEvent.Post',
                 'PlayerEvent.PlayerChangedDimensionEvent',
                 'PlayerEvent.PlayerLoggedInEvent',
                 'GameplayXpPolicy.biomeDiscovery',
                 'GameplayXpPolicy.dimensionDiscovery',
                 'PlayerProgressionRuntime.creditDiscovery')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java',
                 'ProgressionService.creditDiscovery')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java',
                 'ExplorationProgressionEvents.class')

require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientChoiceCatalog.java',
                 'class_choices.json',
                 'requiredClassId',
                 'selectedInGroup')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientClassCatalog.java',
                 'paid_classes.json',
                 'ClassUnlockDefinition',
                 'ClassUnlockResolver.evaluate')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientTreeLayout.java',
                 'assets/rpgskilltree/tree/',
                 'technomancer',
                 'warlock',
                 'availableFor',
                 'NodeAccessRequirement',
                 'requiredClassChoices',
                 'getResourceAsStream',
                 'SkillGraph.undirected',
                 'NodePurchaseDefinition')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/client/RpgSkillTreeScreen.java',
                 'extends Screen',
                 'TreeDisplayProjector.project',
                 'ClientProgressionState.get()',
                 'CharacterLevelCurve.defaultCurve()',
                 'classProgression().unlockedClassIds()',
                 'screen.rpgskilltree.level',
                 'screen.rpgskilltree.classes',
                 'PacketDistributor.sendToServer(new PurchaseNodePayload',
                 'PacketDistributor.sendToServer(new RespecNodePayload',
                 'PacketDistributor.sendToServer(new UnlockClassPayload',
                 'PacketDistributor.sendToServer(new SelectClassChoicePayload',
                 'PacketDistributor.sendToServer(new ClearClassChoicePayload',
                 'mouseDragged(',
                 'mouseScrolled(',
                 'GLFW.GLFW_KEY_TAB',
                 'layout.requirements()')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientKeyMappings.java',
                 'RegisterKeyMappingsEvent',
                 'InputEvent.Key',
                 'Dist.CLIENT',
                 'minecraft.setScreen(new RpgSkillTreeScreen())')
require_contains('src/main/resources/assets/rpgskilltree/lang/en_us.json',
                 'key.rpgskilltree.open_tree',
                 'key.categories.rpgskilltree')
require_contains('src/main/resources/assets/rpgskilltree/lang/pt_br.json',
                 'key.rpgskilltree.open_tree',
                 'key.categories.rpgskilltree')


require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/identity2/MorphCategoryReloader.java',
                 'SimpleJsonResourceReloadListener',
                 'super(GSON, "morph_categories")',
                 'MorphCategoryCatalog.replace')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/identity2/MorphIdentityAccess.java',
                 'MorphClassificationPolicy.describe',
                 'MorphPermissionResolver.resolve',
                 'MorphAccessPolicy.canUse',
                 'minecraft", "player')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/runtime/mixin/IdentityProgressionMixin.java',
                 '@Pseudo',
                 'net.Gabou.identity2.identity.IdentityProgression',
                 'MorphIdentityAccess.canMorph',
                 'CallbackInfoReturnable<Boolean>')
require_contains('src/main/resources/rpgskilltree.mixins.json',
                 'IdentityProgressionMixin')
require_contains('src/main/resources/META-INF/neoforge.mods.toml',
                 'rpgskilltree.mixins.json')
require_contains('src/main/resources/data/rpgskilltree/morph_categories/defaults.json',
                 'NATURAL_FLYING',
                 'MAGICAL_NATURAL',
                 'HUMANOID',
                 'minecraft:ender_dragon')
require_contains('src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java',
                 'MorphCategoryReloader.class')

if errors:
    for error in errors:
        print(f'ERROR: {error}')
    sys.exit(1)
print('Runtime scaffold validation: PASS')
