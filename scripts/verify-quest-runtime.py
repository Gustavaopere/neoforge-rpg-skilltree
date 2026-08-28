#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"


def require(text: str, needle: str) -> None:
    if needle not in text:
        print(f"ERROR: {RUNTIME.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)


def forbid(text: str, needle: str, label: str) -> None:
    if needle in text:
        print(f"ERROR: {RUNTIME.relative_to(ROOT)}: {label} contains forbidden {needle!r}")
        raise SystemExit(1)


def between(text: str, start: str, end: str) -> str:
    start_index = text.find(start)
    if start_index < 0:
        require(text, start)
    end_index = text.find(end, start_index + len(start))
    if end_index < 0:
        print(f"ERROR: {RUNTIME.relative_to(ROOT)}: cannot isolate runtime method after {start!r}")
        raise SystemExit(1)
    return text[start_index:end_index]


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

# Quest/provider reads have their own projection and must be observational. Asking a
# question must not materialize the canonical attachment, persist an in-memory legacy
# migration, delete migration inputs, or emit owner sync. Rules still come exclusively
# from the installed server catalog.
require(text, "import dev.gustavopere.rpgskilltree.core.CoreProgressionQuerySnapshot;")
require(text, "import dev.gustavopere.rpgskilltree.core.CoreProgressionQueryService;")
require(text, "import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;")
require(
    compact,
    "public static CoreProgressionQuerySnapshot queryProgression( ServerPlayer player )",
)
require(text, "private static CoreProgressionState readOnlyState(")
require(text, "CoreProgressionQueryService.snapshot(state, rules)")
require(text, "CanonicalPlayerAttachmentRuntime.observe(player)")
require(text, "observed.initializeCore(rules)")

query_method = between(
    text,
    "public static CoreProgressionQuerySnapshot queryProgression(",
    "public static SemanticProgressionResult applySemanticAction(",
)
read_only_helper = between(
    text,
    "private static CoreProgressionState readOnlyState(",
    "public static void set(",
)
forbid(query_method, "bootstrap(", "queryProgression")
forbid(query_method, "setData(", "queryProgression")
forbid(query_method, "syncCoreToOwner(", "queryProgression")
forbid(query_method, "set(player,", "queryProgression")
forbid(query_method, "readOrMigrate(", "queryProgression")
forbid(read_only_helper, "setData(", "readOnlyState")
forbid(read_only_helper, "syncCoreToOwner(", "readOnlyState")
forbid(read_only_helper, "set(player,", "readOnlyState")
forbid(read_only_helper, "readOrMigrate(", "readOnlyState")

print("Quest reward/query runtime validation: PASS")
