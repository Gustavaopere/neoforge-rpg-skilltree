#!/usr/bin/env python3
"""Compatibility entry point for consolidated Volcanoes release readiness."""

from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
TARGET = ROOT / "scripts" / "verify-volcanoes-release-readiness.py"

raise SystemExit(subprocess.call([sys.executable, str(TARGET)], cwd=ROOT))
