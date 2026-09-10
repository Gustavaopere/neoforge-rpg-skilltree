#!/usr/bin/env python3
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/EntityScalingEvents.java"
INITIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingInitializer.java"
DECISION_FACTORY = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingDecisionRequestFactory.java"
DECISION_INITIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingDecisionInitializer.java"
CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingInitializerCatalog.java"
EFFECTIVE_STATS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityEffectiveStatsRuntime.java"
BEHAVIOR_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityBehaviorRuntime.java"
BEHAVIOR_CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityBehaviorRuntimeCatalog.java"
REWARD_EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/EntityRewardEvents.java"
REWARD_CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityRewardScalingPolicyCatalog.java"
REWARD_EXPERIENCE = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityRewardExperienceRuntime.java"
MOD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"


def read_required(path: Path) -> str:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required entity-scaling lifecycle file is missing")
        raise SystemExit(1)
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
        raise SystemExit(1)


def forbid(text: str, needle: str, location: str) -> None:
    if needle in text:
        print(f"ERROR: {location}: forbidden {needle!r}")
        raise SystemExit(1)


events = read_required(EVENTS)
initializer = read_required(INITIALIZER)
decision_factory = read_required(DECISION_FACTORY)
decision_initializer = read_required(DECISION_INITIALIZER)
catalog = read_required(CATALOG)
effective_stats = read_required(EFFECTIVE_STATS)
behavior_runtime = read_required(BEHAVIOR_RUNTIME)
behavior_catalog = read_required(BEHAVIOR_CATALOG)
reward_events = read_required(REWARD_EVENTS)
reward_catalog = read_required(REWARD_CATALOG)
reward_experience = read_required(REWARD_EXPERIENCE)
mod = read_required(MOD)

events_location = str(EVENTS.relative_to(ROOT))
initializer_location = str(INITIALIZER.relative_to(ROOT))
decision_factory_location = str(DECISION_FACTORY.relative_to(ROOT))
decision_initializer_location = str(DECISION_INITIALIZER.relative_to(ROOT))
catalog_location = str(CATALOG.relative_to(ROOT))
effective_stats_location = str(EFFECTIVE_STATS.relative_to(ROOT))
behavior_runtime_location = str(BEHAVIOR_RUNTIME.relative_to(ROOT))
behavior_catalog_location = str(BEHAVIOR_CATALOG.relative_to(ROOT))
reward_events_location = str(REWARD_EVENTS.relative_to(ROOT))
reward_catalog_location = str(REWARD_CATALOG.relative_to(ROOT))
reward_experience_location = str(REWARD_EXPERIENCE.relative_to(ROOT))
mod_location = str(MOD.relative_to(ROOT))

require(initializer, "EntityScalingState initialize(ServerLevel level, LivingEntity entity)", initializer_location)
require(decision_factory, "EntityScalingDecisionRequest create(ServerLevel level, LivingEntity entity)", decision_factory_location)
require(decision_initializer, "implements EntityScalingInitializer", decision_initializer_location)
require(decision_initializer, "EntityScalingDecisionService.resolve(request).state()", decision_initializer_location)
require(catalog, "Optional<EntityScalingInitializer> current()", catalog_location)
require(catalog, "install(EntityScalingInitializer initializer)", catalog_location)
require(catalog, "installDecisionFactory(EntityScalingDecisionRequestFactory requestFactory)", catalog_location)
require(catalog, "new EntityScalingDecisionInitializer(requestFactory)", catalog_location)
require(catalog, "clear()", catalog_location)

require(effective_stats, "void refresh(LivingEntity entity, EntityScalingState state)", effective_stats_location)
require(effective_stats, "instance.removeModifier(modifierId)", effective_stats_location)
require(effective_stats, "addOrReplacePermanentModifier", effective_stats_location)
require(effective_stats, "AttributeModifier.Operation.ADD_VALUE", effective_stats_location)
require(effective_stats, '"rpgskilltree"', effective_stats_location)
require(effective_stats, '"entity_scaling/" + statKey.path()', effective_stats_location)

require(behavior_runtime, "EntityBehaviorRuntimeResult reconcile(", behavior_runtime_location)
require(behavior_runtime, "state.behaviors().behaviors()", behavior_runtime_location)
require(behavior_runtime, "EntityBehaviorRuntimeCatalog.current(behavior)", behavior_runtime_location)
require(behavior_catalog, "install(Map<EntityBehaviorKey, EntityBehaviorReconciler> reconcilers)", behavior_catalog_location)
require(behavior_catalog, "Optional<EntityBehaviorReconciler> current(EntityBehaviorKey key)", behavior_catalog_location)
require(behavior_catalog, "clear()", behavior_catalog_location)

require(events, "@SubscribeEvent", events_location)
require(events, "EntityJoinLevelEvent", events_location)
require(events, "event.getLevel() instanceof ServerLevel", events_location)
require(events, "event.getEntity() instanceof LivingEntity", events_location)
require(events, "instanceof Player", events_location)
require(events, "EntityScalingRuntime.current", events_location)
require(events, "EntityEffectiveStatsRuntime.refresh(entity, existing.orElseThrow())", events_location)
require(events, "EntityBehaviorRuntime.reconcile(serverLevel, entity, existing.orElseThrow())", events_location)
require(events, "EntityScalingInitializerCatalog.current", events_location)
require(events, "EntityScalingRuntime.getOrInitialize", events_location)
require(events, "EntityEffectiveStatsRuntime.refresh(entity, initialized)", events_location)
require(events, "EntityBehaviorRuntime.reconcile(serverLevel, entity, initialized)", events_location)

# The join event may fire before the underlying chunk reaches FULL. Keep world threat/player scans
# outside this adapter and behind the explicitly installed initializer contract.
for forbidden in ("EntityLevelService", "MobRarityService", ".getChunk(", ".getBiome(", "StructureManager"):
    forbid(events, forbidden, events_location)

require(reward_catalog, "Optional<CappedEntityRewardScalingPolicy> current()", reward_catalog_location)
require(reward_catalog, "install(CappedEntityRewardScalingPolicy policy)", reward_catalog_location)
require(reward_catalog, "clear()", reward_catalog_location)

require(reward_experience, "scaleExperience(int currentExperience, EntityRewardScalingResult scaling)", reward_experience_location)
require(reward_experience, "RoundingMode.DOWN", reward_experience_location)
require(reward_experience, "Integer.MAX_VALUE", reward_experience_location)
require(reward_experience, "currentExperience < 0", reward_experience_location)

require(reward_events, "@SubscribeEvent", reward_events_location)
require(reward_events, "LivingExperienceDropEvent", reward_events_location)
require(reward_events, "event.getEntity().level().isClientSide()", reward_events_location)
require(reward_events, "EntityRewardScalingPolicyCatalog.current()", reward_events_location)
require(reward_events, "EntityScalingRuntime.current(event.getEntity())", reward_events_location)
require(reward_events, "new EntityRewardScalingContext", reward_events_location)
require(reward_events, "event.getDroppedExperience()", reward_events_location)
require(reward_events, "event.setDroppedExperience", reward_events_location)
require(reward_events, "EntityRewardExperienceRuntime.scaleExperience", reward_events_location)
for forbidden in ("getOriginalExperience()", "EntityScalingRuntime.getOrInitialize", "EntityScalingInitializerCatalog"):
    forbid(reward_events, forbidden, reward_events_location)

require(mod, "NeoForge.EVENT_BUS.register(EntityScalingEvents.class);", mod_location)
require(mod, "NeoForge.EVENT_BUS.register(EntityRewardEvents.class);", mod_location)

persisted = events.find("EntityScalingRuntime.current")
persisted_refresh = events.find("EntityEffectiveStatsRuntime.refresh(entity, existing.orElseThrow())")
persisted_behavior = events.find("EntityBehaviorRuntime.reconcile(serverLevel, entity, existing.orElseThrow())")
catalog_lookup = events.find("EntityScalingInitializerCatalog.current")
initialize = events.find("EntityScalingRuntime.getOrInitialize")
initialized_refresh = events.find("EntityEffectiveStatsRuntime.refresh(entity, initialized)")
initialized_behavior = events.find("EntityBehaviorRuntime.reconcile(serverLevel, entity, initialized)")
if (
    persisted < 0
    or persisted_refresh < 0
    or persisted_behavior < 0
    or catalog_lookup < 0
    or initialize < 0
    or initialized_refresh < 0
    or initialized_behavior < 0
    or not (
        persisted
        < persisted_refresh
        < persisted_behavior
        < catalog_lookup
        < initialize
        < initialized_refresh
        < initialized_behavior
    )
):
    print(
        f"ERROR: {events_location}: persisted state must replay Effective Stats and behaviors before initializer lookup, "
        "and newly initialized state must replay both only after persistence"
    )
    raise SystemExit(1)

reward_policy = reward_events.find("EntityRewardScalingPolicyCatalog.current()")
reward_state = reward_events.find("EntityScalingRuntime.current(event.getEntity())")
reward_apply = reward_events.find("event.setDroppedExperience")
if reward_policy < 0 or reward_state < 0 or reward_apply < 0 or not (reward_policy < reward_state < reward_apply):
    print(
        f"ERROR: {reward_events_location}: reward policy and persisted state must be resolved before XP mutation"
    )
    raise SystemExit(1)

print(
    "Entity scaling event validation: PASS "
    "(server-only join boundary + persisted Effective Stats/behavior reconciliation + canonical initialization + "
    "persisted-state-only XP reward adapter verified)"
)

# Keep the entity-reward paths adjacent in the core gate: XP and loot must both consume only
# already-persisted scaling state and the explicitly installed canonical reward policy.
subprocess.run(
    [sys.executable, str(ROOT / "scripts/verify-reward-loot-modifier.py")],
    check=True,
)
