#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
MOD_ATTACHMENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java"
SERIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingAttachmentSerializer.java"
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingRuntime.java"
ENVELOPE = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/core/EntityScalingAttachmentData.java"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


require(ENVELOPE.exists(), "EntityScalingAttachmentData.java: required entity scaling attachment envelope is missing")
require(SERIALIZER.exists(), "EntityScalingAttachmentSerializer.java: required persistent serializer is missing")
require(RUNTIME.exists(), "EntityScalingRuntime.java: required entity scaling runtime boundary is missing")

attachments = MOD_ATTACHMENTS.read_text(encoding="utf-8")
require("ENTITY_SCALING" in attachments, "ModAttachments: ENTITY_SCALING attachment is missing")
require('"entity_scaling"' in attachments, "ModAttachments: entity_scaling registry id is missing")
require("EntityScalingAttachmentSerializer.INSTANCE" in attachments,
        "ModAttachments: entity scaling attachment must use its persistent serializer")

serializer = SERIALIZER.read_text(encoding="utf-8")
require("EntityScalingStateCodec.encode" in serializer,
        "EntityScalingAttachmentSerializer: must delegate persisted state encoding to EntityScalingStateCodec")
require("EntityScalingStateCodec.decode" in serializer,
        "EntityScalingAttachmentSerializer: must delegate persisted state decoding to EntityScalingStateCodec")
require("ByteArrayTag" in serializer,
        "EntityScalingAttachmentSerializer: expected compact ByteArrayTag persistence")

runtime = RUNTIME.read_text(encoding="utf-8")
require("public static EntityScalingState getOrInitialize(" in runtime,
        "EntityScalingRuntime: getOrInitialize boundary is missing")
require("LivingEntity entity" in runtime and "Supplier<EntityScalingState> initializer" in runtime,
        "EntityScalingRuntime: boundary must accept LivingEntity and initializer")
require("entity.hasData(ModAttachments.ENTITY_SCALING)" in runtime,
        "EntityScalingRuntime: must check hasData before reading the scaling attachment")
require("entity.getData(ModAttachments.ENTITY_SCALING)" in runtime,
        "EntityScalingRuntime: must resume persisted scaling metadata")
require("EntityScalingBootstrap.resumeOrInitialize" in runtime,
        "EntityScalingRuntime: must delegate idempotent selection to EntityScalingBootstrap")
require("entity.setData(" in runtime and "ModAttachments.ENTITY_SCALING" in runtime,
        "EntityScalingRuntime: missing-state initialization must persist the result")
require(runtime.index("entity.hasData(ModAttachments.ENTITY_SCALING)") <
        runtime.index("entity.getData(ModAttachments.ENTITY_SCALING)"),
        "EntityScalingRuntime: getData occurs before hasData and may materialize a default attachment")

print("Entity scaling runtime validation: PASS")
