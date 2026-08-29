#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
SCREEN = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreen.java"
KEYS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientKeyMappings.java"
LANG = ROOT / "src/main/resources/assets/rpgskilltree/lang/pt_br.json"

errors: list[str] = []


def require_text(text: str, needle: str, label: str) -> None:
    if needle not in text:
        errors.append(f"{label}: missing {needle!r}")


if not SCREEN.exists():
    errors.append("CompendiumScreen.java is missing")
    screen_text = ""
else:
    screen_text = SCREEN.read_text(encoding="utf-8")
    for needle in (
        "extends Screen",
        "CompendiumScreenSession",
        "ClientCompendiumState.get()",
        "CompendiumScreenLayout.calculate",
        "EditBox",
        "setResponder",
        "viewport(",
        "openVisibleRow",
        "scrollRows",
        "mouseClicked",
        "mouseScrolled",
        "backToList",
    ):
        require_text(screen_text, needle, "CompendiumScreen")

if not KEYS.exists():
    errors.append("ClientKeyMappings.java is missing")
else:
    key_text = KEYS.read_text(encoding="utf-8")
    if "InputEvent" in key_text:
        errors.append("ClientKeyMappings must not use InputEvent for in-game key mappings")
    for needle in (
        "RegisterKeyMappingsEvent",
        "ClientTickEvent.Post",
        "OPEN_TREE.consumeClick()",
        "OPEN_COMPENDIUM",
        "OPEN_COMPENDIUM.consumeClick()",
        "event.register(OPEN_COMPENDIUM)",
        "new RpgSkillTreeScreen()",
        "new CompendiumScreen()",
    ):
        require_text(key_text, needle, "ClientKeyMappings")

if not LANG.exists():
    errors.append("pt_br.json is missing")
else:
    payload = json.loads(LANG.read_text(encoding="utf-8"))
    required_keys = {
        "key.rpgskilltree.open_compendium",
        "screen.rpgskilltree.compendium.title",
        "screen.rpgskilltree.compendium.search",
        "screen.rpgskilltree.compendium.empty",
        "screen.rpgskilltree.compendium.results",
        "screen.rpgskilltree.compendium.back",
        "screen.rpgskilltree.compendium.discovered",
        "screen.rpgskilltree.compendium.undiscovered",
        "screen.rpgskilltree.compendium.source_mod",
        "screen.rpgskilltree.compendium.sections",
        "screen.rpgskilltree.compendium.no_details",
    }
    for key in sorted(required_keys):
        value = payload.get(key)
        if not isinstance(value, str) or not value.strip():
            errors.append(f"pt_br.json: missing non-blank translation {key}")

if errors:
    raise SystemExit("Compendium client UI contract: FAIL\n- " + "\n- ".join(errors))

print("Compendium client UI contract: PASS")
