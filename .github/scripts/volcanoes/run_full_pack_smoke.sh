#!/usr/bin/env bash
set -euo pipefail

ROOT="$PWD"
RUN_DIR="$ROOT/runs/server"
BUILD_DIR="$ROOT/build/full-pack-acceptance"
SEED=4242424242
RCON_PORT=25575
RCON_PASSWORD=volcanoes-full-pack-ci
mkdir -p "$RUN_DIR" "$BUILD_DIR"

write_server_files() {
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

wait_for_log() {
  local log="$1"
  local pattern="$2"
  local attempts="$3"
  for _ in $(seq 1 "$attempts"); do
    if grep -Eq 'ModLoadingException|Loading errors encountered|Failed to load datapacks|Exception in server tick loop|Failed to start the minecraft server' "$log" 2>/dev/null; then
      cat "$log"
      return 1
    fi
    if grep -Eq "$pattern" "$log" 2>/dev/null; then
      return 0
    fi
    sleep 1
  done
  cat "$log"
  return 1
}

rcon() {
  python3 "$ROOT/.github/scripts/volcanoes/minecraft_rcon.py" \
    127.0.0.1 "$RCON_PORT" "$RCON_PASSWORD" "$@"
}

run_round() {
  local round="$1"
  local log="$BUILD_DIR/server-round-$round.log"
  rm -rf "$RUN_DIR/logs" "$RUN_DIR/crash-reports"
  write_server_files

  gradle --no-daemon runServer </dev/null >"$log" 2>&1 &
  local pid=$!
  cleanup() {
    kill "$pid" 2>/dev/null || true
    wait "$pid" 2>/dev/null || true
  }
  trap cleanup RETURN

  wait_for_log "$RUN_DIR/logs/latest.log" 'Done \([0-9.]+s\)! For help, type "help"' 300

  # Exercise the deterministic Stage-01 volcanic owner neighborhood under the full provider stack.
  rcon forceload add -1040 -15664 -993 -15617
  sleep 15
  rcon save-all flush
  sleep 5
  rcon stop

  for _ in $(seq 1 180); do
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

  grep -Fq 'Loaded 7 Volcanoes rock profile definitions' "$log"
  grep -Fq 'Stopping server' "$log"
  test -f "$RUN_DIR/world/data/volcanoes_sites.dat"
  python3 "$ROOT/.github/scripts/volcanoes/worldgen_site_digest.py" \
    "$RUN_DIR/world/data/volcanoes_sites.dat" > "$BUILD_DIR/server-round-$round.digest"
}

rm -rf "$RUN_DIR/world" "$RUN_DIR/logs" "$RUN_DIR/crash-reports" "$RUN_DIR/config" "$RUN_DIR/defaultconfigs"
run_round 1
run_round 2
cmp "$BUILD_DIR/server-round-1.digest" "$BUILD_DIR/server-round-2.digest"
printf 'Full-pack dedicated-server startup, save/reload and Volcanoes site persistence PASS\n'
