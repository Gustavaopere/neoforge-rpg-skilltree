#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
LIBRARY = ROOT / "library"
EXPECTED = {
    "minecraft-asset-art-direction", "minecraft-audio-design", "minecraft-blockbench-geckolib",
    "minecraft-ci-release", "minecraft-commands-scripting", "minecraft-datapack",
    "minecraft-dependency-compatibility-graph", "minecraft-essentials-ops",
    "minecraft-imagegen", "minecraft-jar-reverse-engineering", "minecraft-mod-dev",
    "minecraft-modding", "minecraft-modpack-bisect", "minecraft-multiloader",
    "minecraft-neoforge-engineering", "minecraft-neoforge-modpack-debugging",
    "minecraft-plugin-dev", "minecraft-resource-pack", "minecraft-server-admin",
    "minecraft-spell-vfx-engineering", "minecraft-testing", "minecraft-world-generation",
    "minecraft-worldedit-ops", "modpack-inventory-redundancy-audit",
}

actual = {p.parent.name for p in LIBRARY.glob("*/SKILL.md")}
if actual != EXPECTED:
    raise SystemExit(f"skill set mismatch: missing={sorted(EXPECTED-actual)} extra={sorted(actual-EXPECTED)}")

for path in LIBRARY.glob("*/SKILL.md"):
    text = path.read_text(encoding="utf-8")
    match = re.search(r"^name:\s*([^\n]+)$", text, re.MULTILINE)
    if not match or match.group(1).strip() != path.parent.name:
        raise SystemExit(f"invalid name metadata: {path}")

for required in [
    "README.md", "ROUTER.md", "VERSION-AUTHORITY.md", "USER-GUIDED-WORKFLOW.md",
    "SOURCE-AUDIT.md", "SOURCE-MANIFEST.md", "ART-PIPELINE-SOURCES.md",
    "SPELL-VFX-AUDIO-SOURCES.md",
]:
    if not (ROOT / required).is_file():
        raise SystemExit(f"missing governance/source file: {required}")

for required in [
    ROOT / "standards" / "MODEL-ASSET-CONTRACT.md",
    ROOT / "standards" / "VISUAL-QA.md",
    ROOT / "standards" / "SPELL-PRESENTATION-CONTRACT.md",
    ROOT / "standards" / "VFX-QA.md",
    ROOT / "standards" / "AUDIO-QA.md",
    ROOT / "tools" / "blockbench" / "README.md",
    ROOT / "tools" / "blockbench" / "minecraft_asset_validator.js",
]:
    if not required.is_file():
        raise SystemExit(f"missing specialized pipeline file: {required.relative_to(ROOT)}")

router = (ROOT / "ROUTER.md").read_text(encoding="utf-8")
for skill_name in [
    "minecraft-asset-art-direction", "minecraft-blockbench-geckolib",
    "minecraft-spell-vfx-engineering", "minecraft-audio-design",
]:
    if skill_name not in router:
        raise SystemExit(f"router does not expose project skill: {skill_name}")

spell_contract = (ROOT / "standards" / "SPELL-PRESENTATION-CONTRACT.md").read_text(encoding="utf-8")
for token in ["Anticipation", "Release", "Travel / Active", "Impact / Resolve", "Decay", "Causalidade e multiplayer"]:
    if token not in spell_contract:
        raise SystemExit(f"spell presentation contract missing section: {token}")

vfx_skill = (LIBRARY / "minecraft-spell-vfx-engineering" / "SKILL.md").read_text(encoding="utf-8")
for token in ["ANTICIPATION -> RELEASE -> TRAVEL/ACTIVE -> IMPACT/RESOLVE -> DECAY", "provider-native first", "Photon", "AAA Particles / Effekseer"]:
    if token not in vfx_skill:
        raise SystemExit(f"VFX skill contract missing token: {token}")

audio_skill = (LIBRARY / "minecraft-audio-design" / "SKILL.md").read_text(encoding="utf-8")
for token in ["Required audio event map", "Multiplayer causality", "Source and asset provenance"]:
    if token not in audio_skill:
        raise SystemExit(f"audio skill contract missing token: {token}")

plugin = (ROOT / "tools" / "blockbench" / "minecraft_asset_validator.js").read_text(encoding="utf-8")
for token in [
    "Plugin.register('minecraft_asset_validator'",
    "new Action('minecraft_asset_validator_run'",
    "MenuBar.menus.tools.addAction(auditAction)",
    "Blockbench.Project",
    "Blockbench.showMessageBox",
]:
    if token not in plugin:
        raise SystemExit(f"Blockbench validator contract missing token: {token}")

print("OK: 24 canonical skills and specialized art/VFX/audio pipeline files present")
