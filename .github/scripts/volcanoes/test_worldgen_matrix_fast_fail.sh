#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd)"
HARNESS="$ROOT/.github/scripts/volcanoes/run_worldgen_matrix_case.sh"
TMP_DIR="$(mktemp -d)"
trap 'rm -rf "$TMP_DIR"' EXIT

mkdir -p "$TMP_DIR/bin" "$TMP_DIR/work"
cat > "$TMP_DIR/bin/gradle" <<'EOF'
#!/usr/bin/env bash
echo 'FAKE_GRADLE_STARTUP_FAILURE' >&2
exit 42
EOF
chmod +x "$TMP_DIR/bin/gradle"

set +e
(
  cd "$TMP_DIR/work"
  PATH="$TMP_DIR/bin:$PATH" \
    CASE_ID='WG-FAST-FAIL-TEST' \
    WITH_TERRALITH='false' \
    WITH_TECTONIC='false' \
    WITH_BWG='false' \
    WITH_BIOLITH='false' \
    timeout 8s bash "$HARNESS"
) >"$TMP_DIR/output.log" 2>&1
status=$?
set -e

cat "$TMP_DIR/output.log"

if [[ "$status" -eq 124 ]]; then
  echo 'Worldgen harness waited for latest.log instead of detecting the exited Gradle launcher.' >&2
  exit 1
fi

if [[ "$status" -ne 42 ]]; then
  echo "Expected launcher exit status 42 to propagate, got $status." >&2
  exit 1
fi

grep -Fq 'FAKE_GRADLE_STARTUP_FAILURE' "$TMP_DIR/output.log" || {
  echo 'Launcher diagnostics were not surfaced by the worldgen harness.' >&2
  exit 1
}

if grep -Fq 'No such file or directory' "$TMP_DIR/output.log"; then
  echo 'Worldgen harness attempted to read a startup log that did not exist.' >&2
  exit 1
fi

echo 'Worldgen harness fast-fail diagnostics PASS'
