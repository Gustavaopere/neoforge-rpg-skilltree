#!/usr/bin/env python3
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/loot/EntityRewardLootRuntime.java"
MODIFIER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/loot/RewardRiskLootModifier.java"
REGISTRY = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/loot/ModLootModifiers.java"
MOD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"
GLOBAL = ROOT / "src/main/resources/data/neoforge/loot_modifiers/global_loot_modifiers.json"
CONFIG = ROOT / "src/main/resources/data/rpgskilltree/loot_modifiers/reward_risk.json"


def read_required(path: Path) -> str:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required reward-loot file is missing")
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


runtime = read_required(RUNTIME)
modifier = read_required(MODIFIER)
registry = read_required(REGISTRY)
mod = read_required(MOD)

global_text = read_required(GLOBAL)
config_text = read_required(CONFIG)

runtime_location = str(RUNTIME.relative_to(ROOT))
modifier_location = str(MODIFIER.relative_to(ROOT))
registry_location = str(REGISTRY.relative_to(ROOT))
mod_location = str(MOD.relative_to(ROOT))

for needle in (
    "scaleGeneratedLoot(",
    "scaling.finalMultiplier()",
    "deterministicSeed",
    "maxExtraStacksPerInput",
    "maxStackSize <= 1",
    "original.copy()",
    "original.copyWithCount(count)",
    "Math.multiplyExact",
    "Math.addExact",
):
    require(runtime, needle, runtime_location)

for needle in (
    "extends LootModifier",
    "LootModifier.codecStart(instance)",
    "LootContextParams.THIS_ENTITY",
    "context.getParamOrNull",
    "EntityScalingRuntime.current(livingEntity)",
    "EntityRewardScalingPolicyCatalog.current()",
    "new EntityRewardScalingContext(state.levelResolution(), state.rarity())",
    "state.deterministicSeed()",
    "EntityRewardLootRuntime.scaleGeneratedLoot",
):
    require(modifier, needle, modifier_location)

for forbidden in (
    "EntityScalingRuntime.getOrInitialize",
    "EntityScalingInitializerCatalog",
    "LivingDropsEvent",
    "ItemEntity",
    "context.getRandom()",
    "context.getRandom(",
):
    forbid(modifier, forbidden, modifier_location)

for needle in (
    "DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS",
    'register(\n        "reward_risk"',
    "RewardRiskLootModifier.CODEC",
    "LOOT_MODIFIER_SERIALIZERS.register(modBus)",
):
    require(registry, needle, registry_location)

require(mod, "ModLootModifiers.register(modBus);", mod_location)

try:
    global_config = json.loads(global_text)
except json.JSONDecodeError as exc:
    print(f"ERROR: {GLOBAL.relative_to(ROOT)}: invalid JSON: {exc}")
    raise SystemExit(1)

if global_config.get("replace") is not False:
    print(f"ERROR: {GLOBAL.relative_to(ROOT)}: replace must be false")
    raise SystemExit(1)
entries = global_config.get("entries")
if not isinstance(entries, list) or "rpgskilltree:reward_risk" not in entries:
    print(f"ERROR: {GLOBAL.relative_to(ROOT)}: reward-risk entry is missing")
    raise SystemExit(1)

try:
    modifier_config = json.loads(config_text)
except json.JSONDecodeError as exc:
    print(f"ERROR: {CONFIG.relative_to(ROOT)}: invalid JSON: {exc}")
    raise SystemExit(1)

if modifier_config.get("type") != "rpgskilltree:reward_risk":
    print(f"ERROR: {CONFIG.relative_to(ROOT)}: wrong modifier type")
    raise SystemExit(1)
if not isinstance(modifier_config.get("conditions"), list):
    print(f"ERROR: {CONFIG.relative_to(ROOT)}: conditions must be a list")
    raise SystemExit(1)
limit = modifier_config.get("max_extra_stacks_per_input")
if isinstance(limit, bool) or not isinstance(limit, int) or not 0 <= limit <= 64:
    print(f"ERROR: {CONFIG.relative_to(ROOT)}: max_extra_stacks_per_input must be an integer in [0, 64]")
    raise SystemExit(1)

print(
    "Reward loot modifier validation: PASS "
    "(NeoForge GLM + persisted-state-only reward policy + deterministic bounded stack expansion verified)"
)
