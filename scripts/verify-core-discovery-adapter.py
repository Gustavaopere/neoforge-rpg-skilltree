#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
EXPLORATION = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/events/ExplorationProgressionEvents.java"
ROUTER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/GameplaySemanticXpRuntime.java"
CORE_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
BOOTSTRAP = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/core/CoreProgressionBootstrap.java"
CLAIMS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/core/ProgressionRewardClaims.java"


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


exploration = read(EXPLORATION)
for needle in (
    "PlayerProgressionRuntime",
    "CorePlayerProgressionRuntime",
    "GameplayXpPolicy",
    "CoreProgressionRulesCatalog",
):
    forbid(exploration, needle, EXPLORATION)
for needle in (
    "GameplaySemanticXpRuntime.applyFirstCompletion(",
    "GameplaySemanticXpPolicy.INSTANCE",
    "SemanticActionAuthorship.DIRECT_PLAYER",
    "new ActionOrigin(",
    "SemanticActionType.BIOME_DISCOVERED",
    "SemanticActionType.DIMENSION_DISCOVERED",
    '"biome:" + biomeId',
    '"dimension:" + dimensionId',
):
    require(exploration, needle, EXPLORATION)

router = read(ROUTER)
for needle in (
    "public static SemanticXpResult applyFirstCompletion(",
    "CoreProgressionRulesCatalog.provider().current()",
    "CorePlayerProgressionRuntime.applyFirstCompletionXp(",
    "SemanticXpPipeline.evaluate(",
    "PlayerProgressionRuntime.creditDiscovery(",
):
    require(router, needle, ROUTER)

core_runtime = read(CORE_RUNTIME)
for needle in (
    "public static SemanticProgressionResult applyFirstCompletionXp(",
    "SemanticProgressionService.applyFirstCompletion(",
    "set(player, result.state(), rules)",
):
    require(core_runtime, needle, CORE_RUNTIME)

bootstrap = read(BOOTSTRAP)
require(bootstrap, "legacy.discoveries().discoveredKeys()", BOOTSTRAP)
require(bootstrap, "claimCompletion(discoveryKey)", BOOTSTRAP)

claims = read(CLAIMS)
for needle in (
    'COMPLETION_PREFIX = "completion:"',
    'COMPLETION_PAYLOAD = "completion:v1"',
    "public boolean isCompletionClaimed(String completionKey)",
    "public ProgressionRewardClaims claimCompletion(String completionKey)",
):
    require(claims, needle, CLAIMS)

print("Core discovery adapter validation: PASS")
