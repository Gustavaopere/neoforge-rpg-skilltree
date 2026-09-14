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

capture_compendium_evidence() {
  local round="$1"
  local log="$2"
  local pid="$3"
  local runtime_report="$RUN_DIR/generated/compendium/runtime-registry-inventory.json"
  local copied_report="$BUILD_DIR/runtime-registry-inventory-round-$round.json"
  local coverage_json="$BUILD_DIR/compendium-coverage-round-$round.json"
  local coverage_md="$BUILD_DIR/compendium-coverage-round-$round.md"

  # The vanilla Done marker can precede Compendium's ServerStartedEvent handler.
  # Wait explicitly for the report while retaining the same fail-fast/liveness
  # behavior as the main dedicated-server smoke test.
  for _ in $(seq 1 60); do
    if grep -Eq 'ModLoadingException|Loading errors encountered|Failed to load datapacks|Exception in server tick loop|Failed to start the minecraft server' "$log" 2>/dev/null; then
      cat "$log"
      return 1
    fi
    if test -s "$runtime_report"; then
      break
    fi
    if ! kill -0 "$pid" 2>/dev/null; then
      echo 'Dedicated server exited before Compendium runtime inventory was published.'
      cat "$log"
      return 1
    fi
    sleep 1
  done

  if ! test -s "$runtime_report"; then
    echo 'Compendium runtime inventory was not published within the post-startup window.'
    cat "$log"
    return 1
  fi

  cp "$runtime_report" "$copied_report"
  python3 "$ROOT/scripts/compendium/inventory_runtime_report.py" \
    "$runtime_report" \
    --json "$coverage_json" \
    --markdown "$coverage_md"
  test -s "$copied_report"
  test -s "$coverage_json"
  test -s "$coverage_md"
}

run_round() {
  local round="$1"
  local log="$BUILD_DIR/server-round-$round.log"
  rm -rf "$RUN_DIR/logs" "$RUN_DIR/crash-reports" "$RUN_DIR/generated/compendium"
  write_server_files

  RPGSKILLTREE_COMPENDIUM_INVENTORY=1 \
    gradle --no-daemon runServer </dev/null >"$log" 2>&1 &
  local pid=$!
  cleanup() {
    kill "$pid" 2>/dev/null || true
    wait "$pid" 2>/dev/null || true
  }
  trap cleanup RETURN

  wait_for_log "$RUN_DIR/logs/latest.log" 'Done \([0-9.]+s\)! For help, type "help"' 300
  capture_compendium_evidence "$round" "$log" "$pid"

  # Exercise the deterministic Stage-01 volcanic owner neighborhood under the bounded
  # Volcanoes compatibility acceptance stack. Despite the legacy workflow name, this
  # is not the complete canonical modpack.
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

rm -rf "$RUN_DIR/world" "$RUN_DIR/logs" "$RUN_DIR/crash-reports" "$RUN_DIR/config" "$RUN_DIR/defaultconfigs" "$RUN_DIR/generated/compendium"
run_round 1
run_round 2
cmp "$BUILD_DIR/server-round-1.digest" "$BUILD_DIR/server-round-2.digest"

python3 - \
  "$BUILD_DIR/runtime-registry-inventory-round-1.json" \
  "$BUILD_DIR/runtime-registry-inventory-round-2.json" <<'PY'
import json
from pathlib import Path
import sys

first = json.loads(Path(sys.argv[1]).read_text(encoding="utf-8"))
second = json.loads(Path(sys.argv[2]).read_text(encoding="utf-8"))

assert first["runtime_fingerprint_sha256"] == second["runtime_fingerprint_sha256"]
assert first["entry_count"] == second["entry_count"]

first_entries = sorted(
    (entry["kind"], entry["resource_location"], entry["namespace"], entry["present_at_runtime"])
    for entry in first["entries"]
)
second_entries = sorted(
    (entry["kind"], entry["resource_location"], entry["namespace"], entry["present_at_runtime"])
    for entry in second["entries"]
)
assert first_entries == second_entries
PY

cp "$BUILD_DIR/runtime-registry-inventory-round-2.json" \
  "$BUILD_DIR/runtime-registry-inventory.json"
cp "$BUILD_DIR/compendium-coverage-round-2.json" \
  "$BUILD_DIR/compendium-coverage.json"
cp "$BUILD_DIR/compendium-coverage-round-2.md" \
  "$BUILD_DIR/compendium-coverage.md"

python3 - \
  "$BUILD_DIR/runtime-registry-inventory.json" \
  "$BUILD_DIR/compendium-inventory-scope.json" <<'PY'
import json
from pathlib import Path
import sys

inventory_path = Path(sys.argv[1])
scope_path = Path(sys.argv[2])
inventory = json.loads(inventory_path.read_text(encoding="utf-8"))

scope = {
    "schema": 1,
    "scope_id": "volcanoes_compatibility_acceptance_stack",
    "workflow_legacy_name": "Volcanoes Full Pack Compatibility Acceptance",
    "complete_modpack_inventory": False,
    "authority": "runtime_presence_within_this_bounded_acceptance_stack",
    "not_authoritative_for": [
        "complete_canonical_modpack_census",
        "absence_from_the_user_modpack",
        "canonical_lore_or_geographic_binding",
    ],
    "loaded_mod_count": len(inventory.get("loaded_mods", [])),
    "entry_count": inventory["entry_count"],
    "runtime_fingerprint_sha256": inventory["runtime_fingerprint_sha256"],
    "inventory_file": inventory_path.name,
    "installer": ".github/scripts/volcanoes/install_full_pack_acceptance.sh",
}
scope_path.write_text(json.dumps(scope, indent=2, sort_keys=True) + "\n", encoding="utf-8")
PY

test -s "$BUILD_DIR/compendium-inventory-scope.json"
printf 'Bounded Volcanoes compatibility-stack startup, save/reload, site persistence and Compendium runtime inventory PASS\n'
