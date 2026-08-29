#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
CORE = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/core"


def read(path: str) -> str:
    target = ROOT / path
    if not target.is_file():
        raise SystemExit(f"missing Core API contract file: {path}")
    return target.read_text(encoding="utf-8")


def require(text: str, token: str, label: str) -> None:
    if token not in text:
        raise SystemExit(f"{label}: missing Core API contract token {token!r}")


if not CORE.is_dir():
    raise SystemExit("missing core source package")

# The pure Core model may depend only on the JDK or on types in its own package.
# Minecraft/NeoForge, runtime/client/UI/network code and optional-provider APIs belong
# behind adapters outside this package.
import_re = re.compile(r"^import\s+(?:static\s+)?([^;]+);", re.MULTILINE)
violations: list[str] = []
for source_path in sorted(CORE.rglob("*.java")):
    source = source_path.read_text(encoding="utf-8")
    for imported in import_re.findall(source):
        if imported.startswith("java.") or imported.startswith("dev.gustavopere.rpgskilltree.core."):
            continue
        violations.append(f"{source_path.relative_to(ROOT)} imports {imported}")

if violations:
    raise SystemExit(
        "core package crossed the platform/provider boundary:\n  " + "\n  ".join(violations)
    )

# Queries are explicit projections. Their contracts are distinct from mutation services
# and from persistence/network representations.
core_query = read("src/main/java/dev/gustavopere/rpgskilltree/core/CoreProgressionQueryService.java")
canonical_query = read("src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalPlayerQueryService.java")
core_snapshot = read("src/main/java/dev/gustavopere/rpgskilltree/core/CoreProgressionQuerySnapshot.java")
canonical_snapshot = read("src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalPlayerSnapshot.java")
query_test = read("src/test/java/dev/gustavopere/rpgskilltree/core/CanonicalPlayerQueryServiceTest.java")

require(core_query, "public static CoreProgressionQuerySnapshot snapshot(", "CoreProgressionQueryService")
require(canonical_query, "public static CanonicalPlayerSnapshot snapshot(", "CanonicalPlayerQueryService")
require(core_snapshot, "public record CoreProgressionQuerySnapshot(", "CoreProgressionQuerySnapshot")
require(canonical_snapshot, "public record CanonicalPlayerSnapshot(", "CanonicalPlayerSnapshot")
require(query_test, "snapshotDoesNotExposeLegacyProgressionAuthorities()", "CanonicalPlayerQueryServiceTest")
require(query_test, "component.getType() == ProgressionState.class", "CanonicalPlayerQueryServiceTest")
require(query_test, "component.getType() == PassivePointLedger.class", "CanonicalPlayerQueryServiceTest")

# Mutations have their own explicit pure services. Consumers should not mutate snapshot
# representations or storage envelopes directly.
core_mutation = read("src/main/java/dev/gustavopere/rpgskilltree/core/CoreProgressionMutationService.java")
attribute_mutation = read("src/main/java/dev/gustavopere/rpgskilltree/core/AttributeRankMutationService.java")
for token in [
    "public static CoreProgressionState grantXp(",
    "public static CoreProgressionState rollbackXp(",
    "public static CoreProgressionState applyCorePointTransaction(",
]:
    require(core_mutation, token, "CoreProgressionMutationService")
for token in [
    "public static CoreProgressionState purchase(",
    "public static CoreProgressionState refund(",
]:
    require(attribute_mutation, token, "AttributeRankMutationService")

# The consolidated invariant test must remain in the core-only suite so stable IDs and
# defensive-copy/immutability guarantees cannot silently regress.
invariant_test = read("src/test/java/dev/gustavopere/rpgskilltree/core/CoreApiInvariantTest.java")
for token in [
    "identityBoundariesFailClosed()",
    "publicCollectionsAreImmutableDefensiveCopies()",
    "technicalLimitsFailClosed()",
]:
    require(invariant_test, token, "CoreApiInvariantTest")

test_core = read("scripts/test-core.sh")
require(test_core, "dev.gustavopere.rpgskilltree.core.CoreApiInvariantTest", "scripts/test-core.sh")
require(test_core, 'python3 "$ROOT/scripts/verify-core-api-boundary.py"', "scripts/test-core.sh")

print("Core API boundary and invariants: PASS")
