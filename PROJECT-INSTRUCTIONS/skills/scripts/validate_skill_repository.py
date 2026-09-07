#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
LIBRARY = ROOT / "library"
EXPECTED = {
    "minecraft-ci-release", "minecraft-commands-scripting", "minecraft-datapack",
    "minecraft-dependency-compatibility-graph", "minecraft-essentials-ops",
    "minecraft-imagegen", "minecraft-jar-reverse-engineering", "minecraft-mod-dev",
    "minecraft-modding", "minecraft-modpack-bisect", "minecraft-multiloader",
    "minecraft-neoforge-engineering", "minecraft-neoforge-modpack-debugging",
    "minecraft-plugin-dev", "minecraft-resource-pack", "minecraft-server-admin",
    "minecraft-testing", "minecraft-world-generation", "minecraft-worldedit-ops",
    "modpack-inventory-redundancy-audit",
}

actual = {p.parent.name for p in LIBRARY.glob("*/SKILL.md")}
if actual != EXPECTED:
    raise SystemExit(f"skill set mismatch: missing={sorted(EXPECTED-actual)} extra={sorted(actual-EXPECTED)}")
for path in LIBRARY.glob("*/SKILL.md"):
    text = path.read_text(encoding="utf-8")
    match = re.search(r"^name:\s*([^\n]+)$", text, re.MULTILINE)
    if not match or match.group(1).strip() != path.parent.name:
        raise SystemExit(f"invalid name metadata: {path}")
for required in ["README.md", "ROUTER.md", "VERSION-AUTHORITY.md", "USER-GUIDED-WORKFLOW.md", "SOURCE-AUDIT.md", "SOURCE-MANIFEST.md"]:
    if not (ROOT / required).is_file():
        raise SystemExit(f"missing governance file: {required}")
print("OK: 20 audited library skills and governance files present")
