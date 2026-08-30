#!/usr/bin/env python3
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]


def require(condition: bool, message: str) -> None:
    if not condition:
        print(f"Node effect runtime validation: FAIL: {message}", file=sys.stderr)
        raise SystemExit(1)


def text(path: str) -> str:
    target = ROOT / path
    require(target.is_file(), f"missing {path}")
    return target.read_text(encoding="utf-8")


facade = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/effects/NodeEffectRuntime.java")
require("AttributeNodeEffectRuntime.refresh(player, state)" in facade,
        "NodeEffectRuntime must refresh attribute effects")
require("NodeEffectCatalog.behaviorEffects()" in facade,
        "NodeEffectRuntime must read authoritative behavior effects")
require("BehaviorNodeEffectRuntime" in facade and ".refresh(player, state, NodeEffectCatalog.behaviorEffects())" in facade,
        "NodeEffectRuntime must refresh behavioral effects")
require("clearPlayer" in facade and "clearAll" in facade,
        "NodeEffectRuntime must expose lifecycle cache invalidation")

progression = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java")
require("NodeEffectRuntime.refresh(player, state);" in progression,
        "PlayerProgressionRuntime mutations must refresh through NodeEffectRuntime")
require("NodeEffectRuntime.refresh(player, reconciled);" in progression,
        "reconciliation fallback must refresh through NodeEffectRuntime")
require("AttributeNodeEffectRuntime.refresh" not in progression,
        "PlayerProgressionRuntime must not bypass the unified effect runtime")

reloader = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/data/SkillTreeDataReloader.java")
require("NodeEffectRuntime.refresh(player, PlayerProgressionRuntime.get(player))" in reloader,
        "authoritative datapack reload must refresh through NodeEffectRuntime")
require("AttributeNodeEffectRuntime.refresh" not in reloader,
        "SkillTreeDataReloader must not bypass the unified effect runtime")

lifecycle = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/events/RelevantPlayerCacheEvents.java")
require("NodeEffectRuntime.clearPlayer(event.getEntity().getUUID());" in lifecycle,
        "logout must invalidate behavioral effect session cache")
require("NodeEffectRuntime.clearAll();" in lifecycle,
        "server stop must invalidate all behavioral effect session caches")

print("Unified node effect runtime validation: PASS")
