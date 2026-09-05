#!/usr/bin/env bash
set -euo pipefail

CASE_ID="${CASE_ID:?CASE_ID is required}"
WITH_TERRALITH="${WITH_TERRALITH:-false}"
WITH_TECTONIC="${WITH_TECTONIC:-false}"
WITH_BWG="${WITH_BWG:-false}"
WITH_BIOLITH="${WITH_BIOLITH:-false}"
ROOT="$PWD"
RUN_DIR="$ROOT/runs/server"
MODS_DIR="$RUN_DIR/mods"
BUILD_DIR="$ROOT/build/worldgen-matrix/$CASE_ID"
SEED=4242424242
RCON_PORT=25575
RCON_PASSWORD=volcanoes-ci

mkdir -p "$MODS_DIR" "$BUILD_DIR"
rm -f "$MODS_DIR"/*.jar

curl_modrinth() {
  curl --fail --silent --show-error --location \
    --retry 5 --retry-all-errors --retry-delay 2 \
    --connect-timeout 20 --max-time 180 --remove-on-error "$@"
}

modrinth_version() {
  local version_id="$1"
  local metadata="$BUILD_DIR/modrinth-$version_id.json"
  curl_modrinth "https://api.modrinth.com/v2/version/$version_id" -o "$metadata"
  local url filename
  url="$(jq -r '[.files[] | select(.primary == true)] | if length == 1 then .[0].url else empty end' "$metadata")"
  filename="$(jq -r '[.files[] | select(.primary == true)] | if length == 1 then .[0].filename else empty end' "$metadata")"
  test -n "$url" && test -n "$filename"
  curl_modrinth "$url" -o "$MODS_DIR/$filename"
  printf '%s\n' "$filename"
}

modrinth_project_version() {
  local project="$1"
  local version_fragment="$2"
  local metadata="$BUILD_DIR/modrinth-$project.json"
  curl_modrinth \
    "https://api.modrinth.com/v2/project/$project/version?loaders=%5B%22neoforge%22%5D&game_versions=%5B%221.21.1%22%5D" \
    -o "$metadata"
  local count url filename
  count="$(jq --arg fragment "$version_fragment" '[.[] | select(.version_number | contains($fragment))] | length' "$metadata")"
  if [[ "$count" != "1" ]]; then
    echo "Expected exactly one NeoForge 1.21.1 $project version containing $version_fragment; got $count" >&2
    jq -r '.[].version_number' "$metadata" >&2
    return 1
  fi
  url="$(jq -r --arg fragment "$version_fragment" '[.[] | select(.version_number | contains($fragment))][0].files | [.[] | select(.primary == true)] | if length == 1 then .[0].url else empty end' "$metadata")"
  filename="$(jq -r --arg fragment "$version_fragment" '[.[] | select(.version_number | contains($fragment))][0].files | [.[] | select(.primary == true)] | if length == 1 then .[0].filename else empty end' "$metadata")"
  test -n "$url" && test -n "$filename"
  curl_modrinth "$url" -o "$MODS_DIR/$filename"
  printf '%s\n' "$filename"
}

# Exact NeoForge 1.21.1 version IDs verified from public Modrinth release metadata and the pack lock.
if [[ "$WITH_TERRALITH" == "true" || "$WITH_TECTONIC" == "true" ]]; then
  modrinth_version 81DDKTGJ # Lithostitched 1.8.0+beta4-neoforge-21.1
fi
if [[ "$WITH_TERRALITH" == "true" ]]; then
  modrinth_version IY93YaEe # Terralith 2.6.2 NeoForge 1.21.1
fi
if [[ "$WITH_TECTONIC" == "true" ]]; then
  modrinth_version vNrkxC3z # Tectonic 3.0.26-neoforge-21.1
fi
if [[ "$WITH_BWG" == "true" ]]; then
  modrinth_version nqrTa84r # CorgiLib 1.21.1-5.0.0.9-NeoForge
  modrinth_version tPkJmim6 # GeckoLib 4.9.2 NeoForge 1.21.1
  modrinth_project_version oh-the-trees-youll-grow 5.3.2
  modrinth_version 6e8GCrLb # TerraBlender 4.1.0.8 NeoForge 1.21.1
  modrinth_version aPEcdSHb # Oh The Biomes We've Gone 2.6.0-NeoForge
fi
if [[ "$WITH_BIOLITH" == "true" ]]; then
  modrinth_version EAjbdreT # Biolith NeoForge 3.0.14 for Minecraft 1.21.1
fi

for mod in "$MODS_DIR"/*.jar; do
  [[ -e "$mod" ]] || break
  jar tf "$mod" >/dev/null
  printf 'matrix mod: %s\n' "$(basename "$mod")"
done

write_server_files() {
  mkdir -p "$RUN_DIR"
  printf 'eula=true\n' > "$RUN_DIR/eula.txt"
  cat > "$RUN_DIR/server.properties" <<EOF
allow-flight=true
level-name=world
level-seed=$SEED
online-mode=false
spawn-protection=0
view-distance=4
simulation-distance=4
max-tick-time=-1
sync-chunk-writes=true
enable-rcon=true
rcon.port=$RCON_PORT
rcon.password=$RCON_PASSWORD
broadcast-rcon-to-ops=false
EOF
}

dump_file_if_present() {
  local file="$1"
  if [[ -f "$file" ]]; then
    cat "$file"
  fi
}

wait_for_log() {
  local log="$1"
  local pattern="$2"
  local attempts="$3"
  local launcher_status="$4"
  local launcher_log="$5"
  for _ in $(seq 1 "$attempts"); do
    if [[ -f "$launcher_status" ]]; then
      local exit_status
      exit_status="$(cat "$launcher_status")"
      echo "Gradle process exited before server startup for $CASE_ID with status $exit_status" >&2
      dump_file_if_present "$launcher_log"
      if [[ "$exit_status" =~ ^[0-9]+$ ]] && (( exit_status != 0 )); then
        return "$exit_status"
      fi
      return 1
    fi
    if grep -Eq 'ModLoadingException|Loading errors encountered|Failed to load datapacks|Exception in server tick loop' "$log" 2>/dev/null; then
      cat "$log"
      return 1
    fi
    if grep -Eq "$pattern" "$log" 2>/dev/null; then
      return 0
    fi
    sleep 1
  done
  echo "Timed out waiting for server startup log for $CASE_ID" >&2
  dump_file_if_present "$log"
  if [[ "$launcher_log" != "$log" ]]; then
    dump_file_if_present "$launcher_log"
  fi
  return 1
}

rcon() {
  python3 "$ROOT/.github/scripts/volcanoes/minecraft_rcon.py" \
    127.0.0.1 "$RCON_PORT" "$RCON_PASSWORD" "$@"
}

run_once() {
  local round="$1"
  local log="$BUILD_DIR/round-$round.log"
  local digest="$BUILD_DIR/round-$round.digest"
  local launcher_status="$BUILD_DIR/round-$round.launcher-exit"

  rm -rf "$RUN_DIR/world" "$RUN_DIR/logs" "$RUN_DIR/crash-reports" "$RUN_DIR/config" "$RUN_DIR/defaultconfigs"
  rm -f "$launcher_status"
  write_server_files

  (
    set +e
    gradle --no-daemon runServer </dev/null
    status=$?
    printf '%s\n' "$status" > "$launcher_status"
    exit "$status"
  ) >"$log" 2>&1 &
  local pid=$!
  cleanup() {
    kill "$pid" 2>/dev/null || true
    wait "$pid" 2>/dev/null || true
  }
  trap cleanup RETURN

  wait_for_log "$RUN_DIR/logs/latest.log" 'Done \([0-9.]+s\)! For help, type "help"' 240 "$launcher_status" "$log"

  # 3x3 owner neighborhood around the deterministic cell(-1,-4) candidate at (-1015,-15641).
  # Its owner is chunk (-64,-978). This bounded compatibility smoke exercises the owning chunk
  # plus immediate neighbors while avoiding an unrelated 64-chunk load test; larger worldgen-load
  # coverage belongs to Stage 07 Hardening.
  rcon forceload add -1040 -15664 -993 -15617
  sleep 15
  rcon save-all flush
  sleep 5
  rcon stop

  for _ in $(seq 1 120); do
    if ! kill -0 "$pid" 2>/dev/null; then
      break
    fi
    sleep 1
  done
  if kill -0 "$pid" 2>/dev/null; then
    cat "$log"
    return 1
  fi
  wait "$pid"
  trap - RETURN

  grep -Fq 'Loaded 7 Volcanoes rock profile definitions' "$log" || {
    cat "$log"
    return 1
  }
  grep -Fq 'Stopping server' "$log" || {
    echo "Server did not acknowledge the RCON stop command for $CASE_ID round $round" >&2
    cat "$log"
    return 1
  }
  test -f "$RUN_DIR/world/data/volcanoes_sites.dat" || {
    echo "No volcanoes_sites.dat after bounded generation for $CASE_ID round $round" >&2
    cat "$log"
    return 1
  }
  python3 "$ROOT/.github/scripts/volcanoes/worldgen_site_digest.py" \
    "$RUN_DIR/world/data/volcanoes_sites.dat" > "$digest"
  printf '%s round %s digest: %s\n' "$CASE_ID" "$round" "$(cat "$digest")"
}

run_once 1
run_once 2
cmp "$BUILD_DIR/round-1.digest" "$BUILD_DIR/round-2.digest"
printf '%s: startup, bounded worldgen and same-case determinism PASS\n' "$CASE_ID"
