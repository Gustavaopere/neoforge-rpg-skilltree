#!/usr/bin/env python3
"""Compatibility entry point for the consolidated Volcanoes provenance gate."""

from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
TARGET = ROOT / "scripts" / "verify-volcanoes-import-provenance.py"

raise SystemExit(subprocess.call([sys.executable, str(TARGET)], cwd=ROOT))
