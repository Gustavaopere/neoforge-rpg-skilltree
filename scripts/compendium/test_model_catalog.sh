#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
MAIN="$ROOT/src/main/java/dev/gustavopere/rpgskilltree/compendium"
TEST="$ROOT/src/test/java/dev/gustavopere/rpgskilltree/compendium"
OUT="$ROOT/build/compendium-model-test-classes"

rm -rf "$OUT"
mkdir -p "$OUT"
mapfile -t SOURCES < <(find "$MAIN" "$TEST" -name '*.java' -print | sort)
test "${#SOURCES[@]}" -gt 0
javac --release 21 -d "$OUT" "${SOURCES[@]}"

java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryIdTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.api.CompendiumFactTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalogBuilderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.provider.ProviderMergeTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.data.CompendiumSchemaTest
