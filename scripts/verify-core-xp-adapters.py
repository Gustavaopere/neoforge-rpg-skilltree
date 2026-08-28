#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
COMBAT = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/CombatProgressionEvents.java"
MINING = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/MiningProgressionEvents.java"
CORE_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"


def read(path: Path) -> str:
    if not path.is_file():
        raise SystemExit(f"ERROR: missing {path.relative_to(ROOT)}")
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, path: Path) -> None:
    if needle not in text:
        raise SystemExit(f"ERROR: {path.relative_to(ROOT)}: missing {needle!r}")


def forbid(text: str, needle: str, path: Path) -> None:
    if needle in text:
        raise SystemExit(f"ERROR: {path.relative_to(ROOT)}: forbidden {needle!r}")


combat = read(COMBAT)
mining = read(MINING)
runtime = read(CORE_RUNTIME)

for path, text in ((COMBAT, combat), (MINING, mining)):
    forbid(text, "PlayerProgressionRuntime", path)
    forbid(text, "GameplayXpPolicy", path)
    forbid(text, "CoreProgressionRulesCatalog", path)
    require(text, "CorePlayerProgressionRuntime.applySemanticAction(", path)
    require(text, "GameplaySemanticXpPolicy.INSTANCE", path)
    require(text, "SemanticActionAuthorship.DIRECT_PLAYER", path)
    require(text, "new ActionOrigin(", path)

require(combat, "SemanticActionType.HOSTILE_KILLED", COMBAT)
require(combat, "SemanticActionType.BOSS_DEFEATED", COMBAT)
require(combat, "GameplaySemanticXpPolicy.MAX_HEALTH_METRIC", COMBAT)

require(mining, "SemanticActionType.ORE_MINED", MINING)
require(mining, "GameplaySemanticXpPolicy.RARE_ORE_TAG", MINING)
require(mining, "OptionalLong.of(event.getPos().asLong())", MINING)
require(mining, "oreData.antiFarmService()", MINING)
require(mining, "oreData.consume(event.getPos())", MINING)

# Event adapters never choose the active rules snapshot. The runtime resolves it
# from the authoritative server catalog and delegates to the explicit-rules overload.
require(runtime, "ProgressionRulesSnapshot rules = CoreProgressionRulesCatalog.provider().requireCurrent();", CORE_RUNTIME)
require(runtime, "return applySemanticAction(player, action, antiFarmService, xpPolicy, rules);", CORE_RUNTIME)

print("Core XP adapter validation: PASS")
