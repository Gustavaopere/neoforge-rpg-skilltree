#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"


def require(text: str, needle: str) -> None:
    if needle not in text:
        print(f"ERROR: {RUNTIME.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)


text = RUNTIME.read_text(encoding="utf-8")
compact = " ".join(text.split())

# Quest/boss/milestone adapters get one server-authoritative reward entry point.
# They may choose the reward identity/type/amount, but they do not supply balance rules.
require(text, "import dev.gustavopere.rpgskilltree.core.ProgressionReward;")
require(text, "import dev.gustavopere.rpgskilltree.core.ProgressionRewardService;")
require(
    compact,
    "public static CoreProgressionState applyProgressionReward( ServerPlayer player, ProgressionReward reward )",
)
require(text, "CoreProgressionRulesCatalog.provider().requireCurrent()")
require(text, "ProgressionRewardService.apply(")
require(text, "if (next != current)")
require(text, "set(player, next, rules)")

print("Quest reward runtime validation: PASS")
