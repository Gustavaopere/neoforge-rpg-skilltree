#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ATTACHMENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java"
SERIALIZER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/EntityScalingAttachmentSerializer.java"


def read_required(path: Path) -> str:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required world-scaling runtime file is missing")
        raise SystemExit(1)
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, location: str) -> None:
    if needle not in text:
        print(f"ERROR: {location}: missing {needle!r}")
        raise SystemExit(1)


attachments = read_required(ATTACHMENTS)
serializer = read_required(SERIALIZER)
location = str(ATTACHMENTS.relative_to(ROOT))
serializer_location = str(SERIALIZER.relative_to(ROOT))

require(attachments, "ENTITY_SCALING", location)
require(attachments, '"entity_scaling"', location)
require(attachments, "EntityScalingAttachmentData::uninitialized", location)
require(attachments, "EntityScalingAttachmentSerializer.INSTANCE", location)
require(serializer, "implements IAttachmentSerializer<ByteArrayTag, EntityScalingAttachmentData>", serializer_location)
require(serializer, "EntityScalingStateCodec.decode", serializer_location)
require(serializer, "EntityScalingStateCodec.encode", serializer_location)

print("World scaling runtime validation: PASS")
