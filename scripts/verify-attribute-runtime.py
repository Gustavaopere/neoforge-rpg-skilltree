#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
text = RUNTIME.read_text(encoding="utf-8")

required = [
    "AttributeRankCostPolicy",
    "AttributeRankMutationService",
    "public static CoreProgressionState purchaseAttributeRanks(",
    "AttributeRankMutationService.purchase(",
    "public static CoreProgressionState refundAttributeRanks(",
    "AttributeRankMutationService.refund(",
    "if (next != current)",
    "set(player, next, rules)",
]
for needle in required:
    if needle not in text:
        print(f"ERROR: {RUNTIME.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)

print("Attribute runtime validation: PASS")
