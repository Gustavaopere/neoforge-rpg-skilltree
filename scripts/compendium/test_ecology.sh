#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT"

bash scripts/compendium/test_model_catalog.sh

if [[ -f scripts/compendium/verify_ecology_runtime.py ]]; then
  python3 scripts/compendium/verify_ecology_runtime.py
fi
if [[ -f scripts/compendium/verify_ecology_reload.py ]]; then
  python3 scripts/compendium/verify_ecology_reload.py
fi
