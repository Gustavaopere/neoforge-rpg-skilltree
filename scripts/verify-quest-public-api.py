#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
API = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/api/RpgQuestProgressionApi.java"


def fail(message: str) -> None:
    print(f"ERROR: {message}")
    raise SystemExit(1)


if not API.exists():
    fail(f"{API.relative_to(ROOT)}: required public quest API is missing")

text = API.read_text(encoding="utf-8")
compact = " ".join(text.split())

required = [
    "package dev.gustavopere.rpgskilltree.api;",
    "public final class RpgQuestProgressionApi",
    "public static QuestProgressionSnapshot query( ServerPlayer player )",
    "public static QuestConditionEvaluation evaluate( ServerPlayer player, QuestProgressionCondition condition )",
    "public static QuestProgressionSnapshot applyReward( ServerPlayer player, ProgressionReward reward )",
    "CorePlayerProgressionRuntime.queryProgression(player)",
    "QuestProgressionConditionService.evaluate(query(player), condition)",
    "CorePlayerProgressionRuntime.applyProgressionReward(player, reward)",
    "player.hasData(ModAttachments.PROGRESSION)",
    "ProgressionState.empty()",
]
for needle in required:
    haystack = compact if "(" in needle and needle.startswith("public static") else text
    if needle not in haystack:
        fail(f"{API.relative_to(ROOT)}: missing {needle!r}")

lower = text.lower()
for forbidden in ("ftbquests", "ftb quests", "net.ftb", "codec", "core_progression"):
    if forbidden in lower:
        fail(f"{API.relative_to(ROOT)}: public quest API contains forbidden coupling {forbidden!r}")

query_start = text.find("public static QuestProgressionSnapshot query(")
evaluate_start = text.find("public static QuestConditionEvaluation evaluate(")
apply_start = text.find("public static QuestProgressionSnapshot applyReward(")
if min(query_start, evaluate_start, apply_start) < 0:
    fail(f"{API.relative_to(ROOT)}: cannot isolate public API methods")
query_method = text[query_start:evaluate_start]
evaluate_method = text[evaluate_start:apply_start]
for forbidden in ("setData(", "applyProgressionReward(", "syncCoreToOwner("):
    if forbidden in query_method:
        fail(f"{API.relative_to(ROOT)}: query must remain observational; found {forbidden!r}")
for forbidden in ("setData(", "ModAttachments"):
    if forbidden in evaluate_method:
        fail(f"{API.relative_to(ROOT)}: evaluate must operate through the public snapshot; found {forbidden!r}")

print("Public quest progression API validation: PASS")
