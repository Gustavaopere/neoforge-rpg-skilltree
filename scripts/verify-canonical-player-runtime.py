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

query_runtime_path = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerQueryRuntime.java"
require(query_runtime_path.exists(), "CanonicalPlayerQueryRuntime.java is required")
query_runtime = query_runtime_path.read_text(encoding="utf-8")
require("CanonicalPlayerSnapshot" in query_runtime and " query(" in query_runtime,
        "canonical player query runtime must expose a CanonicalPlayerSnapshot query")
require("CoreProgressionRulesCatalog.provider().requireCurrent()" in query_runtime,
        "canonical player query runtime must resolve authoritative server rules")
require("CanonicalPlayerAttachmentRuntime.observe(player)" in query_runtime,
        "canonical player query runtime must observe without persisting migration")
require("CanonicalPlayerQueryService.snapshot(" in query_runtime,
        "canonical player query runtime must delegate projection to CanonicalPlayerQueryService")
require("readOrMigrate" not in query_runtime,
        "canonical player query runtime must not materialize migration during observation")
require("CanonicalPlayerAttachmentRuntime.write" not in query_runtime,
        "canonical player query runtime must not persist as a query side effect")
require("setData(" not in query_runtime,
        "canonical player query runtime must not write attachments directly")
require("ModNetworking" not in query_runtime,
        "canonical player query runtime must not synchronize as a query side effect")

compat_root = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat"
legacy_reads = []
for path in sorted(compat_root.rglob("*.java")):
    source = path.read_text(encoding="utf-8")
    if "PlayerProgressionRuntime.get(" in source:
        legacy_reads.append(path.relative_to(ROOT).as_posix())
require(not legacy_reads,
        "compat adapters must read through CanonicalPlayerQueryRuntime, legacy direct reads found in: "
        + ", ".join(legacy_reads))

print("Canonical player runtime validation: PASS")
