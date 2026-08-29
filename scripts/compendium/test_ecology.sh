#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT"

bash scripts/compendium/test_model_catalog.sh
OUT="$ROOT/build/compendium-model-test-classes"
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.loot.LootSummaryTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.loot.LootSnapshotTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.loot.CompendiumLootEnricherTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.ecology.FoodRelationProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.ecology.BreedingProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.ecology.EcologyRelationTest

if [[ -f scripts/compendium/verify_ecology_runtime.py ]]; then
  python3 scripts/compendium/verify_ecology_runtime.py
fi
if [[ -f scripts/compendium/verify_ecology_reload.py ]]; then
  python3 scripts/compendium/verify_ecology_reload.py
fi
