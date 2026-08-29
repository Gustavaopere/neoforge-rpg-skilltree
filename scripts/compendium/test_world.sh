#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT"

bash scripts/compendium/test_model_catalog.sh
OUT="$ROOT/build/compendium-model-test-classes"
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.world.BiomeProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.world.StructureProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.world.DimensionProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.world.WorldDiscoveryTest

python3 scripts/compendium/verify_world_runtime.py
python3 scripts/compendium/verify_world_catalog_publication.py
python3 scripts/compendium/verify_world_reload.py
python3 scripts/compendium/verify_world_discovery.py
