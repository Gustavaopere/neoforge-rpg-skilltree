#!/usr/bin/env python3
from pathlib import Path
import re
import shutil
import subprocess

ROOT = Path(__file__).resolve().parents[1]
REPO_ROOT = ROOT.parents[1]
LIBRARY = ROOT / "library"
GOLDEN = ROOT / "golden-samples"

EXPECTED = {
    "minecraft-asset-art-direction", "minecraft-audio-design", "minecraft-blockbench-geckolib",
    "minecraft-ci-release", "minecraft-commands-scripting", "minecraft-datapack",
    "minecraft-dependency-compatibility-graph", "minecraft-essentials-ops",
    "minecraft-imagegen", "minecraft-jar-reverse-engineering", "minecraft-mod-dev",
    "minecraft-modding", "minecraft-modpack-bisect", "minecraft-multiloader",
    "minecraft-neoforge-engineering", "minecraft-neoforge-modpack-debugging",
    "minecraft-plugin-dev", "minecraft-resource-pack", "minecraft-server-admin",
    "minecraft-spell-production", "minecraft-spell-vfx-engineering", "minecraft-testing",
    "minecraft-vfx-engineering", "minecraft-visual-qa", "minecraft-world-generation",
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

for skill_name in ["minecraft-vfx-engineering", "minecraft-spell-production", "minecraft-visual-qa"]:
    path = LIBRARY / skill_name / "SKILL.md"
    text = path.read_text(encoding="utf-8")
    description = re.search(r"^description:\s*([^\n]+)$", text, re.MULTILINE)
    if not description or not description.group(1).strip().startswith("Use when"):
        raise SystemExit(f"new project skill description must start with 'Use when': {path}")

for required in [
    "README.md", "ROUTER.md", "VERSION-AUTHORITY.md", "USER-GUIDED-WORKFLOW.md",
    "SOURCE-AUDIT.md", "SOURCE-MANIFEST.md", "ART-PIPELINE-SOURCES.md",
    "VISUAL-STYLE-SOURCES.md", "SPELL-VFX-AUDIO-SOURCES.md",
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
    ROOT / "tools" / "blockbench" / "rpg-asset-toolkit" / "README.md",
    ROOT / "tools" / "blockbench" / "rpg-asset-toolkit" / "rpg_asset_toolkit.js",
    ROOT / "tools" / "blockbench" / "rpg-asset-toolkit" / "rpg_asset_toolkit.test.js",
    REPO_ROOT / "docs" / "art" / "VISUAL-STYLE-BIBLE.md",
]:
    if not required.is_file():
        try: label = required.relative_to(REPO_ROOT)
        except ValueError: label = required
        raise SystemExit(f"missing specialized pipeline file: {label}")

for template in ["ASSET-BRIEF.md", "MODEL-BRIEF.md", "ANIMATION-BRIEF.md", "VFX-BRIEF.md", "SPELL-BRIEF.md", "AUDIO-CUE-SHEET.md", "VISUAL-QA.md"]:
    if not (ROOT / "templates" / template).is_file():
        raise SystemExit(f"missing art-pipeline template: templates/{template}")

golden_required = [
    "README.md",
    "model-asset/ASSET-BRIEF.md",
    "model-asset/MODEL-CONTRACT.md",
    "model-asset/ANIMATION-BRIEF.md",
    "model-asset/TOOLKIT-PROFILE.json",
    "model-asset/validator-project-fixture.json",
    "spell/SPELL-BRIEF.md",
    "spell/SPELL-PRESENTATION.md",
    "spell/VFX-BRIEF.md",
    "spell/AUDIO-CUE-SHEET.md",
    "spell/VISUAL-QA.md",
    "spell/VFX-QA.md",
    "spell/AUDIO-QA.md",
    "validate_golden_samples.js",
    "validate_reference_evidence.js",
]
for relative in golden_required:
    if not (GOLDEN / relative).is_file():
        raise SystemExit(f"missing Golden Sample file: golden-samples/{relative}")

router = (ROOT / "ROUTER.md").read_text(encoding="utf-8")
for skill_name in ["minecraft-asset-art-direction", "minecraft-blockbench-geckolib", "minecraft-vfx-engineering", "minecraft-spell-production", "minecraft-spell-vfx-engineering", "minecraft-audio-design", "minecraft-visual-qa"]:
    if skill_name not in router: raise SystemExit(f"router does not expose project skill: {skill_name}")

style_bible = (REPO_ROOT / "docs" / "art" / "VISUAL-STYLE-BIBLE.md").read_text(encoding="utf-8")
for token in ["Excalibur", "Fresh Animations", "Complementary", "Black Arcana", "Enshrouded Shroud", "Volcanoes", "texel density", "Proveniência"]:
    if token not in style_bible: raise SystemExit(f"Visual Style Bible missing contract token: {token}")

spell_contract = (ROOT / "standards" / "SPELL-PRESENTATION-CONTRACT.md").read_text(encoding="utf-8")
for token in ["Anticipation", "Release", "Travel / Active", "Impact / Resolve", "Decay", "Causalidade e multiplayer"]:
    if token not in spell_contract: raise SystemExit(f"spell presentation contract missing section: {token}")

spell_vfx = (LIBRARY / "minecraft-spell-vfx-engineering" / "SKILL.md").read_text(encoding="utf-8")
for token in ["ANTICIPATION -> RELEASE -> TRAVEL/ACTIVE -> IMPACT/RESOLVE -> DECAY", "provider-native first", "Photon", "AAA Particles / Effekseer"]:
    if token not in spell_vfx: raise SystemExit(f"spell VFX skill contract missing token: {token}")

general_vfx = (LIBRARY / "minecraft-vfx-engineering" / "SKILL.md").read_text(encoding="utf-8")
for token in ["ANTICIPATION -> CHARGE -> RELEASE -> TRAVEL/ACTIVE -> IMPACT -> LINGER -> DECAY", "provider-native", "Photon", "AAA Particles/Effekseer"]:
    if token not in general_vfx: raise SystemExit(f"general VFX skill contract missing token: {token}")

spell_production = (LIBRARY / "minecraft-spell-production" / "SKILL.md").read_text(encoding="utf-8")
for token in ["Start with authority", "Server/client boundary", "minecraft-vfx-engineering", "minecraft-audio-design", "minecraft-visual-qa"]:
    if token not in spell_production: raise SystemExit(f"spell production skill missing token: {token}")

audio_skill = (LIBRARY / "minecraft-audio-design" / "SKILL.md").read_text(encoding="utf-8")
for token in ["Required audio event map", "Multiplayer causality", "Source and asset provenance"]:
    if token not in audio_skill: raise SystemExit(f"audio skill contract missing token: {token}")

visual_qa = (LIBRARY / "minecraft-visual-qa" / "SKILL.md").read_text(encoding="utf-8")
for token in ["Build success and editor preview are not visual acceptance", "PASS", "FAIL", "PENDING"]:
    if token not in visual_qa: raise SystemExit(f"visual QA skill missing token: {token}")

legacy_plugin = (ROOT / "tools" / "blockbench" / "minecraft_asset_validator.js").read_text(encoding="utf-8")
for token in ["Plugin.register('minecraft_asset_validator'", "Blockbench.showMessageBox"]:
    if token not in legacy_plugin: raise SystemExit(f"legacy Blockbench validator contract missing token: {token}")

toolkit = (ROOT / "tools" / "blockbench" / "rpg-asset-toolkit" / "rpg_asset_toolkit.js").read_text(encoding="utf-8")
for token in ["Plugin.register('rpg_asset_toolkit'", "rpg_asset_toolkit_validate_profile", "BONE_PARENT_CYCLE", "MISSING_FACE_TEXTURE", "MISSING_REQUIRED_BONE", "MISSING_REQUIRED_ANIMATION", "MODEL_SPAN_EXCEEDED", "Blockbench.textPrompt"]:
    if token not in toolkit: raise SystemExit(f"RPG Asset Toolkit contract missing token: {token}")

node = shutil.which("node")
if not node: raise SystemExit("Node.js is required to validate the Blockbench RPG Asset Toolkit")
toolkit_dir = ROOT / "tools" / "blockbench" / "rpg-asset-toolkit"
subprocess.run([node, "--check", str(toolkit_dir / "rpg_asset_toolkit.js")], check=True)
subprocess.run([node, "--test", str(toolkit_dir / "rpg_asset_toolkit.test.js")], check=True)

golden_validator = GOLDEN / "validate_golden_samples.js"
subprocess.run([node, "--check", str(golden_validator)], check=True)
subprocess.run([node, str(golden_validator)], check=True)

golden_evidence_validator = GOLDEN / "validate_reference_evidence.js"
subprocess.run([node, "--check", str(golden_evidence_validator)], check=True)
subprocess.run([node, str(golden_evidence_validator)], check=True)

print("OK: 27 canonical skills, art/VFX/audio standards, templates, Visual Style Bible, Blockbench Toolkit, and Golden Samples present")
