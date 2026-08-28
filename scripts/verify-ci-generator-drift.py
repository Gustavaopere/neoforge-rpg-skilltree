#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github" / "workflows" / "alpha2-build.yml"

text = WORKFLOW.read_text(encoding="utf-8")
regen_marker = "- name: Regenerate derived skill-tree data"
drift_command = "git diff --exit-code"

if regen_marker not in text:
    raise SystemExit("CI generator drift contract: regeneration step is missing")
if drift_command not in text:
    raise SystemExit("CI generator drift contract: workflow must fail on generated-data drift with git diff --exit-code")
if text.index(drift_command) < text.index(regen_marker):
    raise SystemExit("CI generator drift contract: drift gate must run after regeneration")

print("CI generator drift contract: PASS")
