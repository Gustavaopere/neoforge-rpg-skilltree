#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
CATALOG_MAIN="$ROOT/src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog"
API_MAIN="$ROOT/src/main/java/dev/gustavopere/rpgskilltree/compendium/api"
TEST="$ROOT/src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog"
OUT="$ROOT/build/compendium-inventory-test-classes"

test -d "$CATALOG_MAIN" || { echo "missing Stage 10.02 catalog implementation: $CATALOG_MAIN"; exit 1; }
test -d "$TEST" || { echo "missing Stage 10.02 catalog tests: $TEST"; exit 1; }

rm -rf "$OUT"
mkdir -p "$OUT"
SEARCH_PATHS=("$CATALOG_MAIN" "$TEST")
if [[ -d "$API_MAIN" ]]; then
  SEARCH_PATHS+=("$API_MAIN")
fi
mapfile -t SOURCES < <(find "${SEARCH_PATHS[@]}" -name '*.java' -print | sort)
test "${#SOURCES[@]}" -gt 0
javac --release 21 -d "$OUT" "${SOURCES[@]}"
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.catalog.ModpackInventoryTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.catalog.CoverageClassifierTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.catalog.RegistryInventoryTest
