#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
API_MAIN="$ROOT/src/main/java/dev/gustavopere/rpgskilltree/compendium/api"
CATALOG_MAIN="$ROOT/src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog"
DISCOVERY_MAIN="$ROOT/src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery"
DISCOVERY_TEST="$ROOT/src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery"
OUT="$ROOT/build/compendium-discovery-test-classes"

rm -rf "$OUT"
mkdir -p "$OUT"
SEARCH_PATHS=("$API_MAIN" "$CATALOG_MAIN" "$DISCOVERY_TEST")
if [[ -d "$DISCOVERY_MAIN" ]]; then
  SEARCH_PATHS+=("$DISCOVERY_MAIN")
fi
mapfile -t SOURCES < <(find "${SEARCH_PATHS[@]}" -name '*.java' -print | sort)
test "${#SOURCES[@]}" -gt 0
javac --release 21 -d "$OUT" "${SOURCES[@]}"

java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryProgressTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryRuntimeTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryIdempotencyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryRewardTest
