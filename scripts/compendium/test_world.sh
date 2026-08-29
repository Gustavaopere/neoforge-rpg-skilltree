#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT"

bash scripts/compendium/test_model_catalog.sh
OUT="$ROOT/build/compendium-model-test-classes"
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.world.BiomeProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.world.StructureProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.world.DimensionProviderTest
