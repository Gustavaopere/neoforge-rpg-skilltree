#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
COMBAT = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/CombatProgressionEvents.java"
MINING = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/MiningProgressionEvents.java"
ROUTER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/GameplaySemanticXpRuntime.java"


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

for path, text in ((COMBAT, combat), (MINING, mining)):
    forbid(text, "PlayerProgressionRuntime", path)
    forbid(text, "CorePlayerProgressionRuntime", path)
    forbid(text, "GameplayXpPolicy", path)
    forbid(text, "CoreProgressionRulesCatalog", path)
    require(text, "GameplaySemanticXpRuntime.apply(", path)
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

# During convergence there is exactly one backend decision boundary. If the Core
# rules catalog is configured, semantic XP mutates Core. If it is not configured,
# legacy XP remains a compatibility fallback so normal gameplay does not crash or
# silently lose progression. Event adapters never see this decision.
router = read(ROUTER)
require(router, "CoreProgressionRulesCatalog.provider().current()", ROUTER)
require(router, "if (rules.isPresent())", ROUTER)
require(router, "CorePlayerProgressionRuntime.applySemanticAction(", ROUTER)
require(router, "SemanticXpPipeline.evaluate(", ROUTER)
require(router, "PlayerProgressionRuntime.applyXp(", ROUTER)
require(router, "semantic.award().orElseThrow()", ROUTER)

print("Core XP adapter validation: PASS")
