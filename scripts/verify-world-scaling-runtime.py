#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ATTACHMENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java"
SERIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingAttachmentSerializer.java"
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingRuntime.java"


def read_required(path: Path) -> str:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required world-scaling runtime file is missing")
        raise SystemExit(1)
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
        raise SystemExit(1)


def forbid(text: str, needle: str, location: str) -> None:
    if needle in text:
        print(f"ERROR: {location}: forbidden {needle!r}")
        raise SystemExit(1)


attachments = read_required(ATTACHMENTS)
serializer = read_required(SERIALIZER)
runtime = read_required(RUNTIME)
location = str(ATTACHMENTS.relative_to(ROOT))
serializer_location = str(SERIALIZER.relative_to(ROOT))
runtime_location = str(RUNTIME.relative_to(ROOT))

require(attachments, "ENTITY_SCALING", location)
require(attachments, '"entity_scaling"', location)
require(attachments, "EntityScalingAttachmentData::uninitialized", location)
require(attachments, "EntityScalingAttachmentSerializer.INSTANCE", location)
require(serializer, "implements IAttachmentSerializer<ByteArrayTag, EntityScalingAttachmentData>", serializer_location)
require(serializer, "EntityScalingStateCodec.decode", serializer_location)
require(serializer, "EntityScalingStateCodec.encode", serializer_location)

require(runtime, "public static EntityScalingSnapshot initializeIfAbsent", runtime_location)
require(runtime, "entity.hasData(ModAttachments.ENTITY_SCALING)", runtime_location)
require(runtime, "return existing.requireSnapshot()", runtime_location)
require(runtime, "entity.setData(", runtime_location)
require(runtime, "EntityScalingAttachmentData.initialized(candidate)", runtime_location)
forbid(runtime, "EntityLevelService", runtime_location)
forbid(runtime, "MobRarityService", runtime_location)

return_index = runtime.find("return existing.requireSnapshot()")
set_index = runtime.find("entity.setData(")
if return_index < 0 or set_index < 0 or return_index > set_index:
    print(f"ERROR: {runtime_location}: persisted snapshot must return before any setData path")
    raise SystemExit(1)

if runtime.count("entity.setData(") != 1:
    print(f"ERROR: {runtime_location}: initializeIfAbsent must have exactly one setData call")
    raise SystemExit(1)

print("World scaling runtime validation: PASS (persistent attachment + idempotent initialization verified)")
