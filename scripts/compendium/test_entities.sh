#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT"

bash scripts/compendium/test_model_catalog.sh
python3 scripts/compendium/verify_entity_runtime.py
python3 scripts/compendium/verify_entity_catalog_publication.py
