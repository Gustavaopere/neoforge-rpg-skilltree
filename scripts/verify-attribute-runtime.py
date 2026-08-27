#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/AttributeRankCostPolicyCatalog.java"
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
    "AttributeRankCostPolicyCatalog.provider().requireCurrent()",
    "CoreProgressionRulesCatalog.provider().requireCurrent()",
]
for needle in required:
    if needle not in text:
        print(f"ERROR: {RUNTIME.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)

if not CATALOG.is_file():
    print(f"ERROR: {CATALOG.relative_to(ROOT)}: required attribute cost policy catalog is missing")
    raise SystemExit(1)
catalog_text = CATALOG.read_text(encoding="utf-8")
for needle in [
    "InstallableAttributeRankCostPolicyProvider",
    "public static AttributeRankCostPolicyProvider provider()",
    "public static void install(AttributeRankCostPolicy policy)",
    "PROVIDER.install(policy)",
    "public static void clear()",
    "PROVIDER.clear()",
]:
    if needle not in catalog_text:
        print(f"ERROR: {CATALOG.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)

print("Attribute runtime validation: PASS")
