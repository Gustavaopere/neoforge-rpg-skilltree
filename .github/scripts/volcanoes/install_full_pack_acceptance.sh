#!/usr/bin/env bash
set -euo pipefail

TARGET_MODS="${1:?target mods directory is required}"
META_DIR="${2:-$PWD/build/full-pack-acceptance/metadata}"
mkdir -p "$TARGET_MODS" "$META_DIR"
rm -f "$TARGET_MODS"/*.jar

curl_jar() {
  local url="$1"
  local filename="$2"
  curl --fail --silent --show-error --location --retry 3 "$url" -o "$TARGET_MODS/$filename"
  jar tf "$TARGET_MODS/$filename" >/dev/null
}

modrinth_version() {
  local version_id="$1"
  local expected_version="${2:-}"
  local metadata="$META_DIR/modrinth-$version_id.json"
  curl --fail --silent --show-error --location --retry 3 \
    "https://api.modrinth.com/v2/version/$version_id" -o "$metadata"
  if [[ -n "$expected_version" ]]; then
    test "$(jq -r '.version_number' "$metadata")" = "$expected_version"
  fi
  local count url filename
  count="$(jq '[.files[] | select(.primary == true)] | length' "$metadata")"
  [[ "$count" == "1" ]]
  url="$(jq -r '[.files[] | select(.primary == true)][0].url' "$metadata")"
  filename="$(jq -r '[.files[] | select(.primary == true)][0].filename' "$metadata")"
  test -n "$url" && test -n "$filename"
  curl_jar "$url" "$filename"
}

modrinth_project_exact() {
  local project="$1"
  local expected_version="$2"
  local metadata="$META_DIR/modrinth-$project.json"
  curl --fail --silent --show-error --location --retry 3 \
    "https://api.modrinth.com/v2/project/$project/version?loaders=%5B%22neoforge%22%5D&game_versions=%5B%221.21.1%22%5D" \
    -o "$metadata"
  local count url filename
  count="$(jq --arg version "$expected_version" '[.[] | select(.version_number == $version)] | length' "$metadata")"
  if [[ "$count" != "1" ]]; then
    echo "Expected exactly one NeoForge 1.21.1 $project release $expected_version; got $count" >&2
    jq -r '.[].version_number' "$metadata" >&2
    return 1
  fi
  url="$(jq -r --arg version "$expected_version" '[.[] | select(.version_number == $version)][0].files | [.[] | select(.primary == true)] | if length == 1 then .[0].url else empty end' "$metadata")"
  filename="$(jq -r --arg version "$expected_version" '[.[] | select(.version_number == $version)][0].files | [.[] | select(.primary == true)] | if length == 1 then .[0].filename else empty end' "$metadata")"
  test -n "$url" && test -n "$filename"
  curl_jar "$url" "$filename"
}

# WG-07: exact worldgen stack already accepted independently by Worldgen Compatibility Matrix.
modrinth_version 81DDKTGJ # Lithostitched 1.8.0+beta4-neoforge-21.1
modrinth_version IY93YaEe # Terralith 2.6.2 NeoForge 1.21.1
modrinth_version vNrkxC3z # Tectonic 3.0.26-neoforge-21.1
modrinth_version nqrTa84r # CorgiLib 1.21.1-5.0.0.9-NeoForge
modrinth_version tPkJmim6 # GeckoLib 4.9.2 NeoForge 1.21.1
modrinth_project_exact oh-the-trees-youll-grow '1.21.1-5.3.2-NeoForge'
modrinth_version 6e8GCrLb # TerraBlender 4.1.0.8 NeoForge 1.21.1
modrinth_version aPEcdSHb # Oh The Biomes We've Gone 2.6.0-NeoForge
modrinth_version EAjbdreT # Biolith 3.0.14 NeoForge 1.21.1

# Create 6.0.10 and its verified Ponder runtime.
curl_jar \
  'https://maven.createmod.net/com/simibubi/create/create-1.21.1/6.0.10-280/create-1.21.1-6.0.10-280.jar' \
  'create-1.21.1-6.0.10-280.jar'
curl_jar \
  'https://maven.createmod.net/net/createmod/ponder/ponder-neoforge/1.0.82+mc1.21.1/ponder-neoforge-1.0.82+mc1.21.1.jar' \
  'ponder-neoforge-1.0.82+mc1.21.1.jar'

# Sable / Aeronautics exact Stage-06 host contract.
modrinth_version U678xqle '2.0.5+mc1.21.1'
modrinth_version Vzp221Un '1.3.1+mc1.21.1'

# Destroy exact installed pack release and the canonical Petrolpark 1.5.0 release it expects.
curl_jar \
  'https://github.com/NHblock714/Destroy/releases/download/v0.4.1/destroy-1.21.1-0.4.1.jar' \
  'destroy-1.21.1-0.4.1.jar'
echo 'ba20bd69fd69e94671060665f08249f782e5526e1fd4223995c681a23361d351  '"$TARGET_MODS/destroy-1.21.1-0.4.1.jar" | sha256sum --check --strict
# The original Petrolpark Maven host is no longer DNS-resolvable. Pin the immutable public
# Modrinth 1.21.1-1.5.0 release instead and verify the exact ABI Destroy links against before boot.
modrinth_version 3A7Utwm4 '1.21.1-1.5.0'
[[ -f "$TARGET_MODS/petrolpark-1.21.1-1.5.0.jar" ]]
jar tf "$TARGET_MODS/petrolpark-1.21.1-1.5.0.jar" \
  | grep -Fxq 'petrolpark/mc/library/mixin/plugin/PetrolparkMixinPlugin.class'
modrinth_version TMNM8nwH '19.39.0.371' # JEI NeoForge 1.21.1

# Cold Sweat exact Stage-06 host.
curl_jar \
  'https://www.cursemaven.com/curse/maven/cold-sweat-506194/8302211/cold-sweat-506194-8302211.jar' \
  'cold-sweat-2.4.2-neoforge-1.21.1.jar'

# RNS + KubeJS exact coexistence runtime. Native RNS worldgen remains enabled in full-pack acceptance.
curl_jar \
  'https://maven.latvian.dev/releases/dev/latvian/mods/rhino/2101.2.7-build.81/rhino-2101.2.7-build.81.jar' \
  'rhino-2101.2.7-build.81.jar'
curl_jar \
  'https://maven.latvian.dev/releases/dev/latvian/mods/kubejs-neoforge/2101.7.2-build.368/kubejs-neoforge-2101.7.2-build.368.jar' \
  'kubejs-neoforge-2101.7.2-build.368.jar'
curl_jar \
  'https://www.cursemaven.com/curse/maven/create-rns-1370563/8729955/create-rns-1370563-8729955.jar' \
  'create_rns-1.3.1-1.21.1-6.jar'

# MineColonies exact protected-area acceptance stack. Keep this identical to the Battle Mage provider runtime.
curl_jar \
  'https://www.cursemaven.com/curse/maven/minecolonies-245506/8765939/minecolonies-245506-8765939.jar' \
  'minecolonies-1.1.1375-1.21.1-snapshot.jar'
curl_jar \
  'https://www.cursemaven.com/curse/maven/structurize-298744/8610535/structurize-298744-8610535.jar' \
  'structurize-1.0.832-1.21.1.jar'
curl_jar \
  'https://ldtteam.jfrog.io/ldtteam/modding/com/ldtteam/multipiston/1.2.51-1.21.1-snapshot/multipiston-1.2.51-1.21.1-snapshot.jar' \
  'multipiston-1.2.51-1.21.1-snapshot.jar'
curl_jar \
  'https://www.cursemaven.com/curse/maven/blockui-522992/6367809/blockui-522992-6367809.jar' \
  'blockui-1.0.199-1.21.1-snapshot.jar'
curl_jar \
  'https://www.cursemaven.com/curse/maven/domum-ornamentum-527361/7231908/domum-ornamentum-527361-7231908.jar' \
  'domum-ornamentum-1.0.223-snapshot.jar'

printf 'Full-pack acceptance jars (%s):\n' "$(find "$TARGET_MODS" -maxdepth 1 -type f -name '*.jar' | wc -l)"
find "$TARGET_MODS" -maxdepth 1 -type f -name '*.jar' -printf '%f\n' | sort
