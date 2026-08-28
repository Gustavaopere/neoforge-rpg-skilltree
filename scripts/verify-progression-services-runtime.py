#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java"
CORE_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
CANONICAL_RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentRuntime.java"
MUTATION_EVENT = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionMutationEvent.java"
MUTATION_EVENTS = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionMutationEvents.java"
EIDOLON_RITUAL = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/eidolon/EidolonRitualProgressionEvents.java"
EIDOLON_ALCHEMY = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/eidolon/EidolonAlchemyProgressionEvents.java"


def read(path: Path) -> str:
    if not path.is_file():
        raise SystemExit(f"ERROR: {path.relative_to(ROOT)}: required progression-services runtime file is missing")
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, path: Path) -> None:
    if needle not in text:
        raise SystemExit(f"ERROR: {path.relative_to(ROOT)}: missing {needle!r}")


def forbid(text: str, needle: str, path: Path) -> None:
    if needle in text:
        raise SystemExit(f"ERROR: {path.relative_to(ROOT)}: forbidden {needle!r}")


canonical = read(CANONICAL_RUNTIME)
event = read(MUTATION_EVENT)
events = read(MUTATION_EVENTS)
runtime = read(RUNTIME)
core_runtime = read(CORE_RUNTIME)
ritual = read(EIDOLON_RITUAL)
alchemy = read(EIDOLON_ALCHEMY)

# One canonical persistence boundary owns accepted progression commits.
require(canonical, "static boolean commitMutation(", CANONICAL_RUNTIME)
require(canonical, "if (before.equals(after))", CANONICAL_RUNTIME)
require(canonical, "write(player, after);", CANONICAL_RUNTIME)
require(canonical, "ProgressionMutationEvents.publish(", CANONICAL_RUNTIME)
forbid(canonical, "public static void write(", CANONICAL_RUNTIME)

write_index = canonical.index("write(player, after);")
publish_index = canonical.index("ProgressionMutationEvents.publish(")
if publish_index < write_index:
    raise SystemExit("ERROR: progression mutation event must be published only after canonical persistence")

# Internal observers receive immutable before/after canonical snapshots only for real changes.
require(event, "record ProgressionMutationEvent", MUTATION_EVENT)
require(event, "CanonicalPlayerAttachmentData before", MUTATION_EVENT)
require(event, "CanonicalPlayerAttachmentData after", MUTATION_EVENT)
require(event, "before.equals(after)", MUTATION_EVENT)
require(events, "subscribe(", MUTATION_EVENTS)
require(events, "publish(", MUTATION_EVENTS)

# Compatibility and Core runtimes route accepted mutations through the canonical commit boundary.
require(runtime, "CanonicalPlayerAttachmentRuntime.commitMutation(", RUNTIME)
require(core_runtime, "CanonicalPlayerAttachmentRuntime.commitMutation(", CORE_RUNTIME)
forbid(runtime, "public static void set(ServerPlayer player, ProgressionState state)", RUNTIME)
require(runtime, "awardMasteryAndDiscoveries(", RUNTIME)

# XP removal is a distinct trusted-server rollback path, never a negative ordinary grant.
require(core_runtime, "public static CoreProgressionState rollbackXp(", CORE_RUNTIME)
require(core_runtime, "CoreProgressionMutationService.rollbackXp(", CORE_RUNTIME)
forbid(core_runtime, "grantXp(player, -", CORE_RUNTIME)
forbid(core_runtime, "grantXp(current, -", CORE_RUNTIME)

# Provider adapters may request canonical mutations, but may not write storage themselves.
for path, text in ((EIDOLON_RITUAL, ritual), (EIDOLON_ALCHEMY, alchemy)):
    forbid(text, "PlayerProgressionRuntime.set(", path)
    forbid(text, "CanonicalPlayerAttachmentRuntime.write(", path)
    require(text, "PlayerProgressionRuntime.awardMasteryAndDiscoveries(", path)

print("Progression services runtime validation: PASS")
