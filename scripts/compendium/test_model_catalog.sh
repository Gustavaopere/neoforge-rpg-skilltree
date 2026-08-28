#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
MAIN="$ROOT/src/main/java/dev/gustavopere/rpgskilltree/compendium"
TEST="$ROOT/src/test/java/dev/gustavopere/rpgskilltree/compendium"
OUT="$ROOT/build/compendium-model-test-classes"
DATA="$ROOT/src/main/resources/data/rpgskilltree/compendium"

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
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.EntitySpeciesEntryFactoryTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.EntityRegistryProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.LivingEntityAttributeProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.EntityInstanceInspectorTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.EntityVariantProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.VanillaEntitySpecialInspectorsTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.RpgScalingFactsTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.entity.EntityCatalogCoverageTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.flora.FloraClassifierTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.flora.CropProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.flora.TreeGroupingTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.compendium.flora.FloraCatalogCoverageTest

python3 - "$DATA" <<'PY'
import json
from pathlib import Path
import sys

root = Path(sys.argv[1])
required = {
    "entries": {"schema_version", "id", "source_mod_id", "translation_key", "content_version"},
    "categories": {"schema_version", "id", "translation_key"},
    "relations": {"schema_version", "type", "from", "to", "source"},
    "discovery": {"schema_version", "id", "entry", "trigger"},
}
for directory, fields in required.items():
    path = root / directory
    files = sorted(path.glob("*.json"))
    if not files:
        raise SystemExit(f"missing Stage 10.03 data fixture in {path}")
    for file in files:
        payload = json.loads(file.read_text(encoding="utf-8"))
        if not isinstance(payload, dict):
            raise SystemExit(f"{file}: root must be an object")
        if payload.get("schema_version") != 1:
            raise SystemExit(f"{file}: schema_version must be 1")
        missing = sorted(fields - payload.keys())
        if missing:
            raise SystemExit(f"{file}: missing fields {missing}")
print("Compendium model resources: PASS")
PY
