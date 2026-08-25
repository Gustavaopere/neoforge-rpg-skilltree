#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
OUT="$ROOT/build/p0035-addon-contract"
JARS="$OUT/jars"
CLASSES="$OUT/classes"
rm -rf "$OUT"; mkdir -p "$JARS" "$CLASSES"
fetch() { local out="$1" url="$2"; curl -fL --retry 3 --retry-all-errors -o "$JARS/$out" "$url"; }
fetch wom.jar 'https://edge.forgecdn.net/files/8691/476/WeaponsOfMiracles-2.0.176.jar'
fetch efiscompat.jar 'https://edge.forgecdn.net/files/8372/294/efiscompat-3.1.0.jar'
fetch mcea.jar 'https://api.modrinth.com/maven/maven/modrinth/ZILkvItJ/ZIrDH5cO/ZILkvItJ-ZIrDH5cO.jar'
fetch epicparcool.jar 'https://edge.forgecdn.net/files/7490/217/epic%20x%20parcool-neoforge-21.0.0-1.21.1.jar'
fetch epic_api.jar 'https://edge.forgecdn.net/files/8433/647/epic_api-21.3.1.jar'
fetch epic_colonies.jar 'https://edge.forgecdn.net/files/8485/616/EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar'
fetch epicfightcompat.jar 'https://edge.forgecdn.net/files/8097/273/epicfightcompat-1.1.0-mc1.21.1-neoforge.jar'
fetch curios.jar 'https://edge.forgecdn.net/files/7865/987/Epic%20Fight%20x%20Curios%20Compat%202.2.jar'
fetch punchy.jar 'https://edge.forgecdn.net/files/7789/794/punchy_epicfight_neoforge.jar'

javac --release 21 -d "$CLASSES" \
  "$ROOT/src/test/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightImpactAddonBinaryContractTest.java"
java -cp "$CLASSES" dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightImpactAddonBinaryContractTest \
  wom 2.0.176 eea940044fd1216a4f66dc96d36f3bc4cb6f7a693b269d94d4153bdc651bc8c5 "$JARS/wom.jar" \
  efiscompat 3.1.0 5d6232d7468b005e9dbcaf9c63ad00fde40b54955dc1aa3938daebdddb1a15c5 "$JARS/efiscompat.jar" \
  mcea 21.17.0.1 dbfe733ae03f64fe9812916023a8aa75f8f019acabda6328cb9f2c32e515c73a "$JARS/mcea.jar" \
  epicparcool 21.0.0 cb8cd4f6d95f2a0b6e82beab117e36aa1933bdc4693d686395ccd4640e02929d "$JARS/epicparcool.jar" \
  epic_api 21.3.1 be644e8365287bb90d399e441d8aa6972328c3b95c573ad41e541b65fcb25dd2 "$JARS/epic_api.jar" \
  epic_colonies 21.0.8 bcab85c7c0b46217acdbc6240796079eb77326c75835074a9760d8abb07aeecc "$JARS/epic_colonies.jar" \
  epicfightcompat 1.1.0 df4df816cf8b8faab48bd0e5ca74b5943a8d00d48e8869136626d3e6598b74fa "$JARS/epicfightcompat.jar" \
  epicfight_curios_compat 1.4 6228e8761342cce733e3d3c0d8ca836e9150d8e6487e6ac780c461dc30a18588 "$JARS/curios.jar" \
  punchy_epicfight_compat 1.0.0 5d1c33ded9b1f64a8aa75f1cdb05e163bb4a6199e575e602e4a7e08fd0c367e6 "$JARS/punchy.jar"
