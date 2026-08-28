#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]

def text(path: str) -> str:
    return (ROOT / path).read_text(encoding="utf-8")

def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)

attachments = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java")
legacy_runtime = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java")
core_runtime = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java")

require("CANONICAL_PLAYER" in attachments, "ModAttachments must register CANONICAL_PLAYER")
require("CanonicalPlayerAttachmentSerializer.INSTANCE" in attachments,
        "CANONICAL_PLAYER must use CanonicalPlayerAttachmentSerializer")
require((ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentSerializer.java").exists(),
        "CanonicalPlayerAttachmentSerializer.java is required")
require((ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentRuntime.java").exists(),
        "CanonicalPlayerAttachmentRuntime.java is required")

require("CanonicalPlayerAttachmentRuntime.readOrMigrate" in legacy_runtime,
        "PlayerProgressionRuntime must read through the canonical attachment runtime")
require("CanonicalPlayerAttachmentRuntime.write" in legacy_runtime,
        "PlayerProgressionRuntime must write through the canonical attachment runtime")
require("setData(ModAttachments.PROGRESSION" not in legacy_runtime,
        "PlayerProgressionRuntime must not write legacy PROGRESSION")

require("CanonicalPlayerAttachmentRuntime.readOrMigrate" in core_runtime,
        "CorePlayerProgressionRuntime must read through the canonical attachment runtime")
require("CanonicalPlayerAttachmentRuntime.observe" in core_runtime,
        "Core read-only query must observe canonical/migration inputs without persisting")
require("CanonicalPlayerAttachmentRuntime.write" in core_runtime,
        "CorePlayerProgressionRuntime must write through the canonical attachment runtime")
require("setData(ModAttachments.CORE_PROGRESSION" not in core_runtime,
        "CorePlayerProgressionRuntime must not write legacy CORE_PROGRESSION")
require("setData(ModAttachments.PROGRESSION" not in core_runtime,
        "CorePlayerProgressionRuntime must not write legacy PROGRESSION")

helper = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentRuntime.java")
require("ModAttachments.CANONICAL_PLAYER" in helper,
        "canonical attachment runtime must use CANONICAL_PLAYER")
require("ModAttachments.PROGRESSION" in helper and "ModAttachments.CORE_PROGRESSION" in helper,
        "canonical attachment runtime must retain old attachments only as migration inputs")
require("setData(ModAttachments.PROGRESSION" not in helper,
        "canonical attachment runtime must never write legacy PROGRESSION")
require("setData(ModAttachments.CORE_PROGRESSION" not in helper,
        "canonical attachment runtime must never write legacy CORE_PROGRESSION")

print("Canonical player runtime validation: PASS")
